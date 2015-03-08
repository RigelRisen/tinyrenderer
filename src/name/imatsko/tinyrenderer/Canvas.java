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

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {

        int[] x_coord = {x1, x2, x3};
        int[] y_coord = {y1, y2, y3};

        int len = 3;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len-1; j++) {
                if(y_coord[j] < y_coord[j+1]) {
                    int bufy = y_coord[j];
                    y_coord[j] = y_coord[j+1];
                    y_coord[j+1] = bufy;

                    int bufx = x_coord[j];
                    x_coord[j] = x_coord[j+1];
                    x_coord[j+1] = bufx;
                }
            }
        }

        y1 = y_coord[0];
        y2 = y_coord[1];
        y3 = y_coord[2];

        x1 = x_coord[0];
        x2 = x_coord[1];
        x3 = x_coord[2];

        int dx1 = Math.abs(x1 - x2);
        int dx2 = Math.abs(x1 - x3);
        int dy1 = y1 - y2;
        int dy2 = y1 - y3;


        int error1 = dy1/2;
        int error2 = dy2/2;

        int step_x1 = (x1 < x2) ? 1 : -1;
        int step_x2 = (x1 < x3) ? 1 : -1;

        int x_i1 = x1;
        int x_i2 = x1;

        for(int y = y1; y >= y2; y += -1) {
            swipeLine(x_i1, x_i2, y, color);

            error1 -= dx1;
            error2 -= dx2;
            while(error1 <= 0) {
                x_i1 += step_x1;
                error1 += dy1;
            }

            while(error2 <= 0) {
                x_i2 += step_x2;
                error2 += dy2;
            }
        }

        dx1 = Math.abs(x3 - x_i1);
        dx2 = Math.abs(x3 - x_i2);
        dy1 = y2 - y3;
        dy2 = y2 - y3;

        error1 = dy1/2;
        error2 = dy2/2;

        step_x1 = (x3 > x_i1) ? 1 : -1;
        step_x2 = (x3 > x_i2) ? 1 : -1;

        for(int y = y2; y >= y3; y += -1) {
            swipeLine(x_i1, x_i2, y, color);
            error1 -= dx1;
            error2 -= dx2;
            while(error1 <= 0) {
                x_i1 += step_x1;
                error1 += dy1;
            }

            while(error2 <= 0) {
                x_i2 += step_x2;
                error2 += dy2;
            }
        }


    }

    private void swipeLine(int x1, int x2, int y, Color color) {
        if (x1 > x2) {
            int buf = x1;
            x1 = x2;
            x2 = buf;
        }
        for (int x_j = x1; x_j <= x2; x_j++) {
            setPixel(x_j, y, color);
        }
    }









}
