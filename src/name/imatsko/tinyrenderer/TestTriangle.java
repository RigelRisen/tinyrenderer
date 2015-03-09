package name.imatsko.tinyrenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestTriangle {

    public static void main(String[] args) throws IOException {
        Canvas newImage = new Canvas(200,200);
        newImage.setCoordinateOrigin(Canvas.LEFT_BOTTOM);

        newImage.drawTriangle(10 , 70 , 50 , 160, 70 , 80 , ColorRGB.RED);
        newImage.drawTriangle(180, 50 , 150, 1  , 70 , 180, ColorRGB.WHITE);
        newImage.drawTriangle(180, 150, 120, 160, 130, 180, ColorRGB.GREEN);

        newImage.drawTriangle(180, 150, 150, 150, 130, 130, ColorRGB.GREEN);


        try(OutputStream outFile = new FileOutputStream("test_triangle.tga")) {
            TGAFormat.write(outFile, newImage, TGAFormat.RUNLENGTH_ENC_RGB);
            System.out.println("triangle file written");
        }
    }
}
