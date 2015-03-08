package name.imatsko.tinyrenderer.linal;

import java.util.Arrays;

/**
 * Created by rigel92 on 03.03.15.
 */
public class Vec2d {

    private final double[] vec = new double[2];

    public Vec2d(double a, double b) {
        vec[0] = a;
        vec[1] = b;
    }


    public Vec2d add(Vec2d otherVec) {
        return new Vec2d(vec[0]+otherVec.vec[0], vec[1]+otherVec.vec[1]);
    }

    public Vec2d add(double num) {
        return new Vec2d(vec[0]+num, vec[1]+num);
    }

    public Vec2d sub(Vec2d otherVec) {
        return new Vec2d(vec[0]-otherVec.vec[0], vec[1]-otherVec.vec[1]);
    }

    public Vec2d sub(double num) {
        return new Vec2d(vec[0]+num, vec[1]+num);
    }

    public Vec2d mult(Vec2d otherVec) {
        return new Vec2d(vec[0]*otherVec.vec[0], vec[1]*otherVec.vec[1]);
    }


    public Vec2d mult(double num) {
        return new Vec2d(vec[0]*num, vec[1]*num);
    }


    public double get(int i) {
        return vec[i];
    }

    public Vec2d set(int i, double val) {
        if(i == 0) {
            return new Vec2d(val, vec[1]);
        } else if(i == 1) {
            return new Vec2d(vec[0], val);
        }
        throw new IndexOutOfBoundsException("Index out of [0..1]");
    }

    @Override
    public String toString() {
        return "Vec2d{" +
                "vec=" + Arrays.toString(vec) +
                '}';
    }
}
