package name.imatsko.tinyrenderer;

/**
 * Created by rigel92 on 01.03.15.
 */
public class ImageBuffer {
    protected int width;
    protected int height;
    protected byte[] image;

    public ImageBuffer(ImageBuffer imgBuf) {
        this.width = imgBuf.width;
        this.height = imgBuf.height;
        this.image = new byte[width*height*3];
        System.arraycopy(imgBuf, 0, this.image, 0, width*height*3);
    }

    public ImageBuffer(int w, int h) {
        this.width = w;
        this.height = h;
        this.image = new byte[w*h*3];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getImage() {
        return image;
    }

    public void setPixel(int x, int y, int r, int g, int b) {
        setRawPixel(x, y, r, g, b);
    }

    public void setRawPixel(int x, int y, int r, int g, int b) {
        checkCoordinateBoundaries(x, y);
        byte[] color = {
                (byte) r,
                (byte) g,
                (byte) b,
        };
        setRawPixel(x, y, color);
    }


    private void setRawPixel(int x, int y, byte[] color) {
        System.arraycopy(color, 0, this.image, y*width*3 + x*3, 3);
    }

//    public int getPixelR(int x, int y) { return getRawPixel(x, y)[0]; }
//    public int getPixelG(int x, int y) { return getRawPixel(x, y)[1]; }
//    public int getPixelB(int x, int y) { return getRawPixel(x, y)[2]; }

    public int [] getPixel(int x, int y) {
        return getRawPixel(x, y);
    }

    public int [] getRawPixel(int x, int y) {
        checkCoordinateBoundaries(x, y);
        byte[] imgPixel = new byte[3];
        System.arraycopy(this.image, y*width*3 + x*3, imgPixel, 0, 3);

        int[] color = {
                imgPixel[0] & 0xFF,
                imgPixel[1] & 0xFF,
                imgPixel[2] & 0xFF
        };

        return color;
    }

    protected void checkCoordinateBoundaries(int x, int y) {
        if(x < 0 || x >= width)
            throw new IndexOutOfBoundsException("Coordinates out of boundaries");
        if(y < 0 || y >= height)
            throw new IndexOutOfBoundsException("Coordinates out of boundaries");
    }
}
