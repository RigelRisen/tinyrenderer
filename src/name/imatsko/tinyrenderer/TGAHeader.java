package name.imatsko.tinyrenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rigel92 on 01.03.15.
 */

public class TGAHeader {
    public int id_length;
    public int color_map_type;
    public int data_type_code;
    public int color_map_origin;
    public int color_map_length;
    public int color_map_depth;
    public int x_origin;
    public int y_origin;
    public int width;
    public int height;
    public int bits_per_pixel;
    public int image_descriptor;

    public static final int HEADER_SIZE = 18;

    TGAHeader() {}


    TGAHeader(byte[] header_array) {
        read(header_array);
    }


    public void read(InputStream input) throws IOException {
         byte[] header_array = new byte[HEADER_SIZE];
        int read_bytes = input.read(header_array, 0, HEADER_SIZE);
        if(read_bytes != HEADER_SIZE)
            throw new IOException("Not enough data for header");
        read(header_array);
    }

    public void read(byte[] header_array) {
        id_length =         header_array[0] & 0xFF;
        color_map_type =    header_array[1] & 0xFF;
        data_type_code =    header_array[2] & 0xFF;
        color_map_origin =  header_array[3] | (header_array[4] << 8);
        color_map_length =  header_array[5] | (header_array[6] << 8);
        color_map_depth =   header_array[7] & 0xFF;
        x_origin =          header_array[8] | (header_array[9] << 8);
        y_origin =          header_array[10] | (header_array[11] << 8);
        width =             header_array[12] | (header_array[13] << 8);
        height =            header_array[14] | (header_array[15] << 8);
        bits_per_pixel =    header_array[16] & 0xFF;
        image_descriptor =  header_array[17] & 0xFF;
    }

    public void write(OutputStream output) throws IOException {
        byte[] header_array = new byte[HEADER_SIZE];
        write(header_array);
        output.write(header_array, 0, HEADER_SIZE);
    }

    public void write(byte[] header_array) {
        header_array[0] =  (byte) (id_length & 0xFF);
        header_array[1] =  (byte) (color_map_type & 0xFF);
        header_array[2] =  (byte) (data_type_code & 0xFF);
        header_array[3] =  (byte) (color_map_origin & 0xFF);
        header_array[4] =  (byte) ((color_map_origin & 0xFF00) >> 8);
        header_array[5] =  (byte) (color_map_length & 0xFF);
        header_array[6] =  (byte) ((color_map_length & 0xFF00) >> 8);
        header_array[7] =  (byte) (color_map_depth & 0xFF);
        header_array[8] =  (byte) (x_origin & 0xFF);
        header_array[9] =  (byte) ((x_origin & 0xFF00) >> 8);
        header_array[10] = (byte) (y_origin & 0xFF);
        header_array[11] = (byte) ((y_origin & 0xFF00) >> 8);
        header_array[12] = (byte) (width & 0xFF);
        header_array[13] = (byte) ((width & 0xFF00) >> 8);
        header_array[14] = (byte) (height & 0xFF);
        header_array[15] = (byte) ((height & 0xFF00) >> 8);
        header_array[16] =  (byte) (bits_per_pixel& 0xFF);
        header_array[17] =  (byte) (image_descriptor & 0xFF);
    }
}
