package name.imatsko.tinyrenderer;


import org.apache.log4j.Logger;


/**
 * Created by rigel92 on 03.03.15.
 */
public class Canvas extends ImageBuffer {
    public static final Logger LOG=Logger.getLogger(Canvas.class);

    public static final int LEFT_TOP = 1;
    public static final int LEFT_BOTTOM = 2;

    private int coord_origin = LEFT_TOP;

    public Canvas(ImageBuffer imgBuf) {
        super(imgBuf);
    }

    public Canvas(int w, int h) {
        super(w, h);
    }

    public int getCoordinateOrigin() {
        return coord_origin;
    }

    public void setCoordinateOrigin(int coord_origin) {
        if(coord_origin != LEFT_TOP && coord_origin != LEFT_BOTTOM) {
            throw new IllegalArgumentException("Illegal coordinate origin");
        }
        this.coord_origin = coord_origin;
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        if(coord_origin == LEFT_TOP) {
            super.setPixel(x, y, color);
        } else if(coord_origin == LEFT_BOTTOM) {
            super.setPixel(x, height - 1 - y, color);
        } else {
            throw new IllegalArgumentException("Illegal coordinate origin");
        }
    }

    @Override
    public int [] getPixel(int x, int y) {
        if(coord_origin == LEFT_TOP) {
            return super.getRawPixel(x, y);
        } else if(coord_origin == LEFT_BOTTOM) {
            return super.getRawPixel(x, height-1 - y);
        } else {
            throw new IllegalArgumentException("Illegal coordinate origin");
        }

    }

    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        checkCoordinateBoundaries(x1, y1);
        checkCoordinateBoundaries(x2, y2);
        int deltaX = Math.abs(x1-x2);
        int deltaY = Math.abs(y1-y2);

        boolean steep = false;
        if (deltaX < deltaY) {
            int buf;

            buf = x1;
            x1 = y1;
            y1 = buf;

            buf = y2;
            y2 = x2;
            x2 = buf;
            steep = true;
        }

        if (x1 > x2) {
            int buf;

            buf = x1;
            x1 = x2;
            x2 = buf;

            buf = y1;
            y1 = y2;
            y2 = buf;
        }

        int dx = x2 - x1;
        int dy = Math.abs(y2 - y1);

        int error = dx / 2; // Здесь используется оптимизация с умножением на dx, чтобы избавиться от лишних дробей
        int ystep = (y1 < y2) ? 1 : -1; // Выбираем направление роста координаты y
        int y = y1;
        for (int x = x1; x <= x2; x++)
        {
            setPixel(steep ? y : x, steep ? x : y, color); // Не забываем вернуть координаты на место
            error -= dy;
            if (error < 0)
            {
                y += ystep;
                error += dx;
            }
        }
    }


//    public void drawTriange(int x1, int y1, int x2, int y2, int x3, int y3)









}
