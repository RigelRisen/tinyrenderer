package name.imatsko.tinyrenderer.linal;

/**
 * Created by rigel92 on 03.03.15.
 */
public class Vec3d {

    public final double x, y, z;

    public Vec3d(double a, double b, double c) {
        x = a;
        y = b;
        z = c;
    }


    public Vec3d add(Vec3d otherVec) {
        return new Vec3d(x+otherVec.x, y+otherVec.y, z+otherVec.z);
    }

    public Vec3d add(double num) {
        return new Vec3d(x+num, y+num, z+num);
    }

    public Vec3d sub(Vec3d otherVec) {
        return new Vec3d(x-otherVec.x, y-otherVec.y, z-otherVec.z);
    }

    public Vec3d sub(double num) {
        return new Vec3d(x+num, y+num, z+num);
    }

    public Vec3d multScal(Vec3d otherVec) {
        return new Vec3d(
                y*otherVec.z - z*otherVec.y,
                z*otherVec.x - x*otherVec.z,
                x*otherVec.y - y*otherVec.x);
    }

    public double dotProduct(Vec3d otherVec) {
        return x*otherVec.x + y*otherVec.y + z*otherVec.z;
    }

    public Vec3d mult(Vec3d otherVec) {
        return new Vec3d(x*otherVec.x, y*otherVec.y, z*otherVec.z);
    }


    public Vec3d mult(double num) {
        return new Vec3d(x*num, y*num, z*num);
    }


    public double norm() {
        return Math.sqrt(x*x + y*y + z*z);
    }


    public Vec3d normalize() {
        return normalize(1);
    }

    public Vec3d normalize(double l) {
        return this.mult(l/this.norm());
    }

    public Vec3d setX(double val) {
        return new Vec3d(val, y, z);
    }

    public Vec3d setY(double val) {
        return new Vec3d(x, val, z);
    }
    public Vec3d setZ(double val) {
        return new Vec3d(x, y, val);
    }

    public Vec3i toVec3i() {
        return new Vec3i((int)x, (int)y, (int)z);
    }

    @Override
    public String toString() {
        return "Vec3d{" +
                "vec=(" + x + ", " + y + ", " + z +
        ")}";
    }
}
