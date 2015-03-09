package name.imatsko.tinyrenderer;

import name.imatsko.tinyrenderer.linal.Vec3i;
import name.imatsko.tinyrenderer.linal.Vec3d;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestRenderer {

    public static void main(String[] args) throws IOException {
        Renderer renderer = new Renderer(1000,1000, 255);
        renderer.setCoordinateOrigin(Canvas.LEFT_BOTTOM);
//        renderer.renderTriangle(new Vec3i(10 , 70 , 0), new Vec3i(50 , 160, 0), new Vec3i(70 , 80 , 0), ColorRGB.RED);
//        renderer.renderTriangle(new Vec3i(180, 50 , 0), new Vec3i(150, 1  , 0), new Vec3i(70 , 180, 0), ColorRGB.WHITE);
//        renderer.renderTriangle(new Vec3i(180, 150, 0), new Vec3i(120, 160, 0), new Vec3i(130, 180, 0), ColorRGB.GREEN);


        Model model = ObjModelLoader.loadFromFile("african_head.obj");
        renderer.light_dir = new Vec3d(0,0,-1);
        renderer.renderModel(model);




        try(OutputStream outFile = new FileOutputStream("test_renderer_triangle.tga")) {
            TGAFormat.write(outFile, renderer, TGAFormat.RUNLENGTH_ENC_RGB);
            System.out.println("triangle file written");
        }
    }
}
