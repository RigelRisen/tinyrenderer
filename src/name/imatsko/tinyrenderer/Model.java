package name.imatsko.tinyrenderer;

import name.imatsko.tinyrenderer.linal.Vec3d;

import java.util.ArrayList;

/**
 * Created by rigel92 on 03.03.15.
 */
public class Model {

    public static class Vert extends Vec3d {
        public Vert(double a, double b, double c) {
            super(a, b, c);
        }
    }

    public static class Face {
        public int[] vert = new int[3];

        public Face(int a, int b, int c) {
            vert[0] = a;
            vert[1] = b;
            vert[2] = c;
        }


    }

    ArrayList<Vert> verts;
    ArrayList<Face> faces;

    public Model() {
        verts = new ArrayList<>();
        faces = new ArrayList<>();
    }

    public int getVertsNum() {
        return verts.size();
    }

    public int getFacesNum() {
        return faces.size();
    }

    public Vert getVert(int i) {
        return verts.get(i);
    }

    public Face getFace(int i) {
        return faces.get(i);
    }

    public void addVert(Vert vert) {
        verts.add(vert);
    }

    public void addFace(Face face) {
        faces.add(face);
    }
}
