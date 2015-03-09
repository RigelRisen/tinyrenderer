package name.imatsko.tinyrenderer.linal;

/**
 * Created by rigel92 on 03.03.15.
 */
public class Vec3i {

    public final int x, y, z;

    public Vec3i(int a, int b, int c) {
        x = a;
        y = b;
        z = c;
    }


    public Vec3i add(Vec3i otherVec) {
        return new Vec3i(x+otherVec.x, y+otherVec.y, z+otherVec.z);
    }

    public Vec3i add(int num) {
        return new Vec3i(x+num, y+num, z+num);
    }

    public Vec3i sub(Vec3i otherVec) {
        return new Vec3i(x-otherVec.x, y-otherVec.y, z-otherVec.z);
    }

    public Vec3i sub(int num) {
        return new Vec3i(x+num, y+num, z+num);
    }

    public Vec3i multScal(Vec3i otherVec) {
        return new Vec3i(
                y*otherVec.z - z*otherVec.y,
                z*otherVec.x - x*otherVec.z,
                x*otherVec.y - y*otherVec.x);
    }

    public int dotProduct(Vec3i otherVec) {
        return x*otherVec.x + y*otherVec.y + z*otherVec.z;
    }

    public Vec3i mult(Vec3i otherVec) {
        return new Vec3i(x*otherVec.x, y*otherVec.y, z*otherVec.z);
    }

    public Vec3d mult(double num) {
        return new Vec3d(x*num, y*num, z*num);
    }

    public Vec3i mult(int num) {
        return new Vec3i(x*num, y*num, z*num);
    }


    public double norm() {
        return Math.sqrt(x*x + y*y + z*z);
    }


    public Vec3d normalize() {
        return normalize(1);
    }

    public Vec3d normalize(int l) {
        return this.mult(l/this.norm());
    }

    public Vec3i setX(int val) {
        return new Vec3i(val, y, z);
    }

    public Vec3i setY(int val) {
        return new Vec3i(x, val, z);
    }
    public Vec3i setZ(int val) {
        return new Vec3i(x, y, val);
    }

    @Override
    public String toString() {
        return "Vec3i{" +
                "vec=(" + x + ", " + y + ", " + z +
        ")}";
    }
}
