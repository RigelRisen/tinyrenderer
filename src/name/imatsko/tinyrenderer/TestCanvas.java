package name.imatsko.tinyrenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestCanvas {

    public static void main(String[] args) throws IOException {
        Canvas newImage = new Canvas(100,100);
        newImage.setRawPixel(50, 50, 255, 0, 0);
        newImage.setCoordinateOrigin(Canvas.LEFT_BOTTOM);
//        newImage.drawLine(20,20, 30,30, 255, 0, 0);
//        newImage.drawLine(80,80, 70,70, 255, 0, 0);
//        newImage.drawLine(20,80, 30,70, 255, 0, 0);
//        newImage.drawLine(80,20, 70,30, 255, 0, 0);
//        newImage.drawLine(80,20, 70,30, 255, 0, 0);
//
//        newImage.drawLine(0,0, 0,99, 255, 0, 0);
//        newImage.drawLine(0,0, 99,0, 255, 0, 0);
//        newImage.drawLine(0,99, 99,99, 255, 0, 0);
//        newImage.drawLine(99,99, 99,0, 255, 0, 0);
//
//        newImage.drawLine(10,10, 10,10, 255, 0, 0);
//
        newImage.drawLine(50,50, 70,30, ColorRGB.RED);
        newImage.drawLine(50,50, 70,33, ColorRGB.RED);
        newImage.drawLine(50,50, 70,36, ColorRGB.RED);
        newImage.drawLine(50,50, 70,39, ColorRGB.RED);
        newImage.drawLine(50,50, 70,43, ColorRGB.RED);
        newImage.drawLine(50,50, 70,46, ColorRGB.RED);
        newImage.drawLine(50,50, 70,49, ColorRGB.RED);
        newImage.drawLine(50,50, 70,53, ColorRGB.RED);
        newImage.drawLine(50,50, 70,56, ColorRGB.RED);
        newImage.drawLine(50,50, 70,59, ColorRGB.RED);
        newImage.drawLine(50,50, 70,63, ColorRGB.RED);
        newImage.drawLine(50,50, 70,66, ColorRGB.RED);

        newImage.drawLine(50,50, 30,30, ColorRGB.RED);
        newImage.drawLine(50,50, 30,33, ColorRGB.RED);
        newImage.drawLine(50,50, 30,36, ColorRGB.RED);
        newImage.drawLine(50,50, 30,39, ColorRGB.RED);
        newImage.drawLine(50,50, 30,43, ColorRGB.RED);
        newImage.drawLine(50,50, 30,46, ColorRGB.RED);
        newImage.drawLine(50,50, 30,49, ColorRGB.RED);
        newImage.drawLine(50,50, 30,53, ColorRGB.RED);
        newImage.drawLine(50,50, 30,56, ColorRGB.RED);
        newImage.drawLine(50,50, 30,59, ColorRGB.RED);
        newImage.drawLine(50,50, 30,63, ColorRGB.RED);
        newImage.drawLine(50,50, 30,66, ColorRGB.RED);
        newImage.drawLine(50,50, 30,69, ColorRGB.RED);
        newImage.drawLine(50,50, 30,70, ColorRGB.RED);
        newImage.drawLine(50,50, 33,70, ColorRGB.RED);
        newImage.drawLine(50,50, 36,70, ColorRGB.RED);
        newImage.drawLine(50,50, 39,70, ColorRGB.RED);
        newImage.drawLine(50,50, 43,70, ColorRGB.RED);
        newImage.drawLine(50,50, 46,70, ColorRGB.RED);
        newImage.drawLine(50,50, 49,70, ColorRGB.RED);
        newImage.drawLine(50,50, 53,70, ColorRGB.RED);
        newImage.drawLine(50,50, 56,70, ColorRGB.RED);
        newImage.drawLine(50,50, 59,70, ColorRGB.RED);
        newImage.drawLine(50,50, 63,70, ColorRGB.RED);
        newImage.drawLine(50,50, 66,70, ColorRGB.RED);
        newImage.drawLine(50,50, 69,70, ColorRGB.RED);
        newImage.drawLine(50,50, 30,30, ColorRGB.RED);
        newImage.drawLine(50,50, 33,30, ColorRGB.RED);
        newImage.drawLine(50,50, 36,30, ColorRGB.RED);
        newImage.drawLine(50,50, 39,30, ColorRGB.RED);
        newImage.drawLine(50,50, 43,30, ColorRGB.RED);
        newImage.drawLine(50,50, 46,30, ColorRGB.RED);
        newImage.drawLine(50,50, 49,30, ColorRGB.RED);
        newImage.drawLine(50,50, 53,30, ColorRGB.RED);
        newImage.drawLine(50,50, 56,30, ColorRGB.RED);
        newImage.drawLine(50,50, 59,30, ColorRGB.RED);
        newImage.drawLine(50,50, 63,30, ColorRGB.RED);
        newImage.drawLine(50,50, 66,30, ColorRGB.RED);
        newImage.drawLine(50,50, 69,30, ColorRGB.RED);


        try(OutputStream outFile = new FileOutputStream("test2.tga")) {
//                TGAFormat.write(outFile2, readBuffer, TGAFormat.UNCOMPRESSED_RGB);
            TGAFormat.write(outFile, newImage, TGAFormat.RUNLENGTH_ENC_RGB);
            System.out.println("line file written");
        }
    }
}
