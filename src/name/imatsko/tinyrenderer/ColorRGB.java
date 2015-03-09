package name.imatsko.tinyrenderer;

/**
 * Created by rigel92 on 08.03.15.
 */

public class ColorRGB implements Color {

    private int red, green, blue;

    public static ColorRGB RED = new ColorRGB(255,0,0);
    public static ColorRGB GREEN = new ColorRGB(0,255,0);
    public static ColorRGB BLUE = new ColorRGB(0,0,255);
    public static ColorRGB BLACK = new ColorRGB(0,0,0);
    public static ColorRGB WHITE = new ColorRGB(255,255,255);

    public ColorRGB() {
        this(0, 0, 0);
    }

    public ColorRGB(double red, double green, double blue) {
        this(
            (int) (red   * 255),
            (int) (green * 255),
            (int) (blue  * 255)
        );

    }

    public ColorRGB(int red, int green, int blue) {
        this.red    = red;
        this.green  = green;
        this.blue   = blue;
    }

    @Override
    public int[] toRGB() {

        int[] color = {
                red,
                green,
                blue
        };

        return color;
    }

    @Override
    public String toString() {
        return "ColorRGB("+red+", "+green+", "+blue+")";
    }

}
