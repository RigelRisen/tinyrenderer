package name.imatsko.tinyrenderer;

import name.imatsko.tinyrenderer.linal.Vec3d;
import name.imatsko.tinyrenderer.linal.Vec3i;

/**
 * Created by rigel92 on 09.03.15.
 */
public class Renderer extends Canvas {
    private ZBuffer zBuffer;

    public Vec3d light_dir = new Vec3d(0,0,0);
    public int depth = 0;

    public Renderer(ImageBuffer imgBuffer) {
        super(imgBuffer);
        zBuffer = new ZBuffer(this.width, this.height);
    }

    public Renderer(int width, int height, int depth) {
        super(width, height);
        this.depth = depth;
        zBuffer = new ZBuffer(this.width, this.height);
    }

    public void renderTriangle(Vec3i v1, Vec3i v2, Vec3i v3, Color color) {

        if(v1.y == v2.y && v2.y == v3.y) return ;

        Vec3i[] vert = {v1, v2, v3};

        int len = 3;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len-1; j++) {
                if(vert[j].y < vert[j+1].y) {
                    Vec3i bufvert = vert[j];
                    vert[j] = vert[j+1];
                    vert[j+1] = bufvert;
                }
            }
        }

        v1 = vert[0];
        v2 = vert[1];
        v3 = vert[2];



        int total_height = v1.y - v3.y;
        int first_half_height = v1.y - v2.y;
        int second_half_height = v2.y - v3.y;

        for (int i = 0; i < total_height; i++) {
            boolean second_half = i > first_half_height || v1.y == v2.y;

            double length_a = (i*1.)/total_height;
            double length_b = second_half ? ((i-first_half_height)*1.)/second_half_height : (i*1.)/first_half_height;

            Vec3i vec_a = v1.add(v3.sub(v1).mult(length_a).toVec3i());
            Vec3i vec_b = second_half ? v2.add(v3.sub(v2).mult(length_b).toVec3i()) : v1.add(v2.sub(v1).mult(length_b).toVec3i());

            if(vec_a.x > vec_b.x) {
                Vec3i buf = vec_a;
                vec_a = vec_b;
                vec_b = buf;
            }

            for (int j = vec_a.x; j <= vec_b.x; j++) {
                try {
                    double lineCoord = (vec_a.x == vec_b.x) ? 1. : ((double)j - vec_a.x)/((double)vec_b.x - vec_a.x);
                    Vec3i point = vec_a.add(vec_b.sub(vec_a).mult(lineCoord).toVec3i());
                    if(zBuffer.draw(j, v1.y-i, point.z))
                        setPixel(j, v1.y-i, color);
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }
    }

    public void renderModel(Model model) {
        for(int faceNum = 0; faceNum < model.getFacesNum(); faceNum++) {
            Model.Face face = model.getFace(faceNum);

            Model.Vert v1 = model.getVert(face.vert[0]-1);
            Model.Vert v2 = model.getVert(face.vert[1]-1);
            Model.Vert v3 = model.getVert(face.vert[2]-1);


            Vec3i screen_v1 = (new Vec3d((v1.x+1.)*width/2, (v1.y+1.)*height/2, (v1.z+1.)*depth/2)).toVec3i();
            Vec3i screen_v2 = (new Vec3d((v2.x+1.)*width/2, (v2.y+1.)*height/2, (v2.z+1.)*depth/2)).toVec3i();
            Vec3i screen_v3 = (new Vec3d((v3.x+1.)*width/2, (v3.y+1.)*height/2, (v3.z+1.)*depth/2)).toVec3i();


            Vec3d normal = v3.sub(v1).multScal(v2.sub(v1)).normalize();

            double light_intensity = normal.dotProduct(light_dir)/(normal.norm()*light_dir.norm());
            if(light_intensity > 0) {
                renderTriangle(
                        screen_v1,
                        screen_v2,
                        screen_v3,
                        new ColorRGB(
                                light_intensity,
                                light_intensity,
                                light_intensity));
            }
        }
    }







}
