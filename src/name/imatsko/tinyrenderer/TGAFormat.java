package name.imatsko.tinyrenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Created by rigel92 on 01.03.15.
 */
public final class TGAFormat {
    public static final int NO_IMAGE = 0;
    public static final int UNCOMPRESSED_CM = 1;
    public static final int UNCOMPRESSED_RGB = 2;
    public static final int UNCOMPRESSED_BW = 3;
    public static final int RUNLENGTH_ENC_CM = 9;
    public static final int RUNLENGTH_ENC_RGB = 10;
    public static final int COMPRESSED_BW = 11;
    public static final int COMPRESSED_CM_HUFFMAN_DELTA_RUNLENGTH = 32;
    public static final int COMPRESSED_CM_HUFFMAN_DELTA_RUNLENGTH_4_PASS = 33;



    private TGAFormat() { // private constructor
    }

    public static ImageBuffer read(InputStream input) throws IOException {
        byte[] header_array = new byte[TGAHeader.HEADER_SIZE];
        guaranteed_read(input, header_array, 0, TGAHeader.HEADER_SIZE);

        TGAHeader header = new TGAHeader(header_array);

        ImageBuffer imageBuffer;

        switch(header.data_type_code) {
            case NO_IMAGE:
                imageBuffer = new ImageBuffer(0,0);
                break;
            case UNCOMPRESSED_RGB:
                imageBuffer = uncompressed_rgb_read(header, input);
                break;
            case RUNLENGTH_ENC_RGB:
                imageBuffer = runlength_rgb_read(header, input);
                break;

            case UNCOMPRESSED_CM:
            case RUNLENGTH_ENC_CM:
            case UNCOMPRESSED_BW:
            case COMPRESSED_BW:
            case COMPRESSED_CM_HUFFMAN_DELTA_RUNLENGTH:
            case COMPRESSED_CM_HUFFMAN_DELTA_RUNLENGTH_4_PASS:
            default:
                throw new UnsupportedOperationException("File type not supported");
        }
        return  imageBuffer;
    }

    public static void write(OutputStream output, ImageBuffer image, int type) throws IOException {

        switch(type) {
            case NO_IMAGE:
                ImageBuffer blank_image = new ImageBuffer(0,0);
                uncompressed_rgb_write(output, blank_image);
                break;
            case UNCOMPRESSED_RGB:
                uncompressed_rgb_write(output, image);

                break;
            case RUNLENGTH_ENC_RGB:
                runlength_rgb_write(output, image);
                break;

            case UNCOMPRESSED_CM:
            case RUNLENGTH_ENC_CM:
            case UNCOMPRESSED_BW:
            case COMPRESSED_BW:
            case COMPRESSED_CM_HUFFMAN_DELTA_RUNLENGTH:
            case COMPRESSED_CM_HUFFMAN_DELTA_RUNLENGTH_4_PASS:
            default:
                throw new UnsupportedOperationException("File type not supported");
        }
    }


    private static void uncompressed_rgb_write(OutputStream output, ImageBuffer image) throws IOException {
        TGAHeader header = new TGAHeader();
        header.data_type_code = UNCOMPRESSED_RGB;
        header.width = image.getWidth();
        header.height = image.getHeight();
        header.image_descriptor = 0b100000;
        header.bits_per_pixel = 24;
        header.write(output);

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                int[] buff_pixel = image.getRawPixel(x, y);
                byte[] file_pixel = new byte[3];
                file_pixel[0] = (byte) buff_pixel[2];
                file_pixel[1] = (byte) buff_pixel[1];
                file_pixel[2] = (byte) buff_pixel[0];
                output.write(file_pixel);
            }

    }

    private static void runlength_rgb_write(OutputStream output, ImageBuffer image) throws IOException {
        TGAHeader header = new TGAHeader();
        header.data_type_code = RUNLENGTH_ENC_RGB;
        header.width = image.getWidth();
        header.height = image.getHeight();
        header.image_descriptor = 0b100000;
        header.bits_per_pixel = 24;

        header.write(output);


        int pix_count = 0;
        Deque<byte[]> packet_data = new ArrayDeque<>(128);
        boolean raw_packet = true;

        while (pix_count < header.width*header.height) {
            int x = (pix_count) % header.width;
            int y = (pix_count) / header.width;
            int[] buff_pixel = image.getRawPixel(x, y);
            byte[] file_pixel = new byte[3];
            file_pixel[0] = (byte) buff_pixel[2];
            file_pixel[1] = (byte) buff_pixel[1];
            file_pixel[2] = (byte) buff_pixel[0];

            if(packet_data.size() == 0) {
                raw_packet = true;
                packet_data.addLast(file_pixel);
                pix_count++;
            } else if (packet_data.size() == 128) {
                write_package(packet_data, packet_data.size(), raw_packet, output);
            } else {
                if(!raw_packet && Arrays.equals(file_pixel, packet_data.getLast())) {
                    packet_data.addLast(file_pixel);
                    pix_count++;
                } else if (!raw_packet && !Arrays.equals(file_pixel, packet_data.getLast())) {
                    write_package(packet_data, packet_data.size(), raw_packet, output);
                    raw_packet = true;
                } else if (raw_packet && !Arrays.equals(file_pixel, packet_data.getLast())) {
                    packet_data.addLast(file_pixel);
                    pix_count++;
                } else if (raw_packet && Arrays.equals(file_pixel, packet_data.getLast())) {
                    byte[] pop = packet_data.removeLast();
                    if(packet_data.size() != 0) {
                        write_package(packet_data, packet_data.size(), raw_packet, output);
                    }
                    packet_data.addLast(pop);
                    packet_data.addLast(file_pixel);
                    raw_packet = false;
                    pix_count++;
                }
            }
        }
        if(packet_data.size() != 0) {
            write_package(packet_data, packet_data.size(), raw_packet, output);
        }
    }

    private static void  write_package(Deque<byte[]> packet_data, int len, boolean raw_packet, OutputStream out) throws IOException {
        byte[] package_header = new byte[1];
        package_header[0] = (byte) (((raw_packet)?0:0b10000000) | (len-1));
        out.write(package_header);
        if(!raw_packet) {
            byte[] file_pixel = packet_data.removeFirst();
            out.write(file_pixel);
            packet_data.clear();
        } else {
            while (packet_data.size() != 0) {
                byte[] file_pixel = packet_data.removeFirst();
                out.write(file_pixel);
            }
        }
    }


    private static ImageBuffer uncompressed_rgb_read(TGAHeader header, InputStream input) throws IOException {
        int to_skip = header.id_length;
        if(header.color_map_type != 0) {
            to_skip += (header.color_map_depth/8) * header.color_map_length;
        }
        byte[] skip_buff = new byte[to_skip];
        guaranteed_read(input,skip_buff,0, to_skip);

        ImageBuffer buffer = new ImageBuffer(header.width, header.height);

        int pixels_read = 0;
        byte[] pix2 = new byte[2];
        byte[] pix3 = new byte[3];
        byte[] pix4 = new byte[4];

        while (pixels_read < header.width*header.height) {
            int x,y;
            if ((header.image_descriptor & 0b0100000) != 0) {
                x = (pixels_read)%header.width;
                y = (pixels_read)/header.width;
            } else {
                x = (pixels_read)%header.width;
                y = header.height-1-(pixels_read)/header.width;
            }

            switch (header.bits_per_pixel/8) {
                case 2:
                    guaranteed_read(input, pix2, 0, 2);
                    int blue  = pix2[0]&0b11111;
                    int red  = pix2[1]&0b1111100;
                    int green = (pix2[0]&0b11100000 >> 5) | (pix2[1]&0b11 << 5);
                    buffer.setRawPixel(x, y, red, green, blue);
                case 3:
                    guaranteed_read(input, pix3, 0, 3);
                    buffer.setRawPixel(x, y, pix3[2] & 0xFF, pix3[1] & 0xFF, pix3[0] & 0xFF);
                    break;
                case 4:
                    guaranteed_read(input, pix4, 0, 4);
                    buffer.setRawPixel(x, y, pix4[2] & 0xFF, pix4[1] & 0xFF, pix4[0] & 0xFF);
                    break;
            }
            pixels_read++;
        }
        return buffer;
    }

    private static ImageBuffer runlength_rgb_read(TGAHeader header, InputStream input) throws IOException {
        int to_skip = header.id_length;
        if(header.color_map_type != 0) {
            to_skip += (header.color_map_depth/8) * header.color_map_length;
        }
        byte[] skip_buff = new byte[to_skip];
        guaranteed_read(input,skip_buff,0, to_skip);

        ImageBuffer buffer = new ImageBuffer(header.width, header.height);

        int pixels_read = 0;
        byte[] pix2 = new byte[2];
        byte[] pix3 = new byte[3];
        byte[] pix4 = new byte[4];

        while (pixels_read < header.width*header.height) {
            byte[] runlength_header = new byte[1];
            guaranteed_read(input, runlength_header, 0, 1);

            if((runlength_header[0] & 0b10000000) != 0 ) {
//                runlength packet
                int rep_count = (runlength_header[0] & 0b1111111) +1;
                int red = 0, green = 0, blue = 0;
                switch (header.bits_per_pixel / 8) {
                    case 2:
                        guaranteed_read(input, pix2, 0, 2);
                        blue = pix2[0] & 0b11111;
                        red = pix2[1] & 0b1111100;
                        green = (pix2[0] & 0b11100000 >> 5) | (pix2[1] & 0b11 << 5);
                        break;
                    case 3:
                        guaranteed_read(input, pix3, 0, 3);
                        red = pix3[2];
                        green = pix3[1];
                        blue = pix3[0];
                        break;
                    case 4:
                        guaranteed_read(input, pix4, 0, 4);
                        red = pix3[2];
                        green = pix3[1];
                        blue = pix3[0];
                        break;
                }

                for (int pix_pack = 0; pix_pack < rep_count; pix_pack++) {
                    int x,y;
                    if ((header.image_descriptor & 0b0100000) != 0) {
                        x = (pixels_read)%header.width;
                        y = (pixels_read)/header.width;
                    } else {
                        x = (pixels_read)%header.width;
                        y = header.height-1-(pixels_read)/header.width;
                    }
                    buffer.setRawPixel(x, y, red, green, blue);
                    pixels_read++;
                }
            } else {
//                Raw packet
                int rep_count = (runlength_header[0] & 0b1111111) +1;
                for (int pix_pack = 0; pix_pack < rep_count; pix_pack++) {
                    int x, y;
                    if ((header.image_descriptor & 0b0100000) != 0) {
                        x = (pixels_read) % header.width;
                        y = (pixels_read) / header.width;
                    } else {
                        x = (pixels_read) % header.width;
                        y = header.height - 1 - (pixels_read) / header.width;
                    }

                    switch (header.bits_per_pixel / 8) {
                        case 2:
                            guaranteed_read(input, pix2, 0, 2);
                            int blue = pix2[0] & 0b11111;
                            int red = pix2[1] & 0b1111100;
                            int green = (pix2[0] & 0b11100000 >> 5) | (pix2[1] & 0b11 << 5);
                            buffer.setRawPixel(x, y, red, green, blue);
                        case 3:
                            guaranteed_read(input, pix3, 0, 3);
                            buffer.setRawPixel(x, y, pix3[2] & 0xFF, pix3[1] & 0xFF, pix3[0] & 0xFF);
                            break;
                        case 4:
                            guaranteed_read(input, pix4, 0, 4);
                            buffer.setRawPixel(x, y, pix4[2] & 0xFF, pix4[1] & 0xFF, pix4[0] & 0xFF);
                            break;
                    }
                    pixels_read++;
                }
            }

        }
        return buffer;
    }


    private static int guaranteed_read(InputStream input, byte[] dest, int off, int len) throws IOException {
        int num_bytes_read = 0;
        int total_read = 0;
        do {
            num_bytes_read = input.read(dest, off+num_bytes_read, len - total_read);
            total_read += num_bytes_read;
        } while (total_read != len && num_bytes_read != -1);
        return total_read;
    }


}
