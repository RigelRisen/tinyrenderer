package name.imatsko.tinyrenderer;

import java.io.*;

public class TestTGAFiles {

    public static void main(String[] args) throws IOException {
        ImageBuffer newImage = new ImageBuffer(100,100);
        newImage.setRawPixel(50, 50, 255, 0, 0);

        try (OutputStream outFile = new FileOutputStream("test.tga")) {
//            TGAFormat.write(outFile, newImage, TGAFormat.UNCOMPRESSED_RGB);
            TGAFormat.write(outFile, newImage, TGAFormat.RUNLENGTH_ENC_RGB);
            System.out.println("new file written");
        }

        try (InputStream inFile = new FileInputStream("test.tga")) {
            ImageBuffer readBuffer = TGAFormat.read(inFile);
            readBuffer.setRawPixel(55, 55, 255, 0, 0);
            readBuffer.setRawPixel(45, 55, 255, 0, 0);
            readBuffer.setRawPixel(55, 45, 255, 0, 0);
            readBuffer.setRawPixel(45, 45, 255, 0, 0);

            try(OutputStream outFile2 = new FileOutputStream("test2.tga")) {
//                TGAFormat.write(outFile2, readBuffer, TGAFormat.UNCOMPRESSED_RGB);
                TGAFormat.write(outFile2, readBuffer, TGAFormat.RUNLENGTH_ENC_RGB);
                System.out.println("new file written 2");
            }
        }


    }
}
