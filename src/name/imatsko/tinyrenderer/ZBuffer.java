package name.imatsko.tinyrenderer;

import name.imatsko.tinyrenderer.linal.Vec3i;

/**
 * Created by rigel92 on 09.03.15.
 */
public class ZBuffer {

    private int[] z_buffer;
    private int width, height;

    public ZBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        z_buffer = new int[width*height];
        for (int i = 0; i < width*height; i++) {
            z_buffer[i] = Integer.MIN_VALUE;
        }
    }

    public boolean draw(Vec3i vert) {
        return draw(vert.x, vert.y, vert.z);
    }

    public boolean draw(int x, int y, int z) {
        if(z >= z_buffer[y*width + x]) {
            z_buffer[y*width + x] = z;
            return true;
        }
        return false;
    }

    public int get(int x, int y) {
        return z_buffer[y*width + x];
    }
}
