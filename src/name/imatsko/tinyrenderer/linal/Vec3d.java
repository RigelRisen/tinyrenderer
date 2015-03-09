package name.imatsko.tinyrenderer.linal;

import java.util.Arrays;

/**
 * Created by rigel92 on 03.03.15.
 */
public class Vec3d {

    private final double[] vec = new double[3];

    public Vec3d(double a, double b, double c) {
        vec[0] = a;
        vec[1] = b;
        vec[2] = c;
    }


    public Vec3d add(Vec3d otherVec) {
        return new Vec3d(vec[0]+otherVec.vec[0], vec[1]+otherVec.vec[1], vec[2]+otherVec.vec[2]);
    }

    public Vec3d add(double num) {
        return new Vec3d(vec[0]+num, vec[1]+num, vec[2]+num);
    }

    public Vec3d sub(Vec3d otherVec) {
        return new Vec3d(vec[0]-otherVec.vec[0], vec[1]-otherVec.vec[1], vec[2]-otherVec.vec[2]);
    }

    public Vec3d sub(double num) {
        return new Vec3d(vec[0]+num, vec[1]+num, vec[2]+num);
    }

    public Vec3d multScal(Vec3d otherVec) {
        return new Vec3d(
                vec[1]*otherVec.vec[2] - vec[2]*otherVec.vec[1],
                vec[2]*otherVec.vec[0] - vec[0]*otherVec.vec[2],
                vec[0]*otherVec.vec[1] - vec[1]*otherVec.vec[0]);
    }


    public double dotProduct(Vec3d otherVec) {
        return vec[0]*otherVec.vec[0] + vec[1]*otherVec.vec[1] + vec[2]*otherVec.vec[2];
    }

    public Vec3d mult(Vec3d otherVec) {
        return new Vec3d(vec[0]*otherVec.vec[0], vec[1]*otherVec.vec[1], vec[2]*otherVec.vec[2]);
    }


    public Vec3d mult(double num) {
        return new Vec3d(vec[0]*num, vec[1]*num, vec[2]*num);
    }


    public double norm() {
        return Math.sqrt(vec[0]*vec[0] + vec[1]*vec[1] + vec[2]*vec[2]);
    }


    public Vec3d normalize() {
        return normalize(1);
    }

    public Vec3d normalize(double l) {
        return this.mult(l/this.norm());
    }


    public double get(int i) {
        return vec[i];
    }

    public Vec3d set(int i, double val) {
        if(i == 0) {
            return new Vec3d(val, vec[1], vec[2]);
        } else if(i == 1) {
            return new Vec3d(vec[0], val, vec[2]);
        } else if(i == 2) {
            return new Vec3d(vec[0], vec[1], val);
        }
        throw new IndexOutOfBoundsException("Index out of [0..1]");
    }

    @Override
    public String toString() {
        return "Vec3d{" +
                "vec=" + Arrays.toString(vec) +
                '}';
    }
}
