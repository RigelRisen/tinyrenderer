package name.imatsko.tinyrenderer;

import name.imatsko.tinyrenderer.linal.Vec3d;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestModel {

    public static void main(String[] args) throws IOException {
        int height = 1000;
        int width = 1000;

        Canvas newImage = new Canvas(width,height);
        newImage.setCoordinateOrigin(Canvas.LEFT_BOTTOM);
//        newImage.setRawPixel(50, 50, 255, 0, 0);
//        newImage.drawLine(20, 20, 30, 30, 255, 0, 0);
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


        Model model = ObjModelLoader.loadFromFile("african_head.obj");

        Vec3d light_dir = new Vec3d(0,0,-1);

        for(int faceNum = 0; faceNum < model.getFacesNum(); faceNum++) {
            Model.Face face = model.getFace(faceNum);

            Model.Vert v1 = model.getVert(face.vert[0]-1);
            Model.Vert v2 = model.getVert(face.vert[1]-1);
            Model.Vert v3 = model.getVert(face.vert[2]-1);

            int x1 = (int) ((v1.get(0)+1)*width/2);
            int y1 = (int) ((v1.get(1)+1)*height/2);

            int x2 = (int) ((v2.get(0)+1)*width/2);
            int y2 = (int) ((v2.get(1)+1)*height/2);

            int x3 = (int) ((v3.get(0)+1)*width/2);
            int y3 = (int) ((v3.get(1)+1)*height/2);

            Vec3d norm = v3.sub(v1).multScal(v2.sub(v1)).normalize();

            double light_intensity = norm.dotProduct(light_dir)/(norm.norm()*light_dir.norm());
            if(((int) (light_intensity*255)) > 1) {
                System.out.println(light_intensity);
                newImage.drawTriangle(x1, y1, x2, y2, x3, y3, new ColorRGB((int) (light_intensity * 255), (int) (light_intensity * 255), (int) (light_intensity * 255)));
            }
        }

        try(OutputStream outFile = new FileOutputStream("head.tga")) {
//                TGAFormat.write(outFile2, readBuffer, TGAFormat.UNCOMPRESSED_RGB);
            TGAFormat.write(outFile, newImage, TGAFormat.RUNLENGTH_ENC_RGB);
            System.out.println("head file written");
        }
    }
}
