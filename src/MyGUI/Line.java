package MyGUI;

import java.awt.*;

public class Line {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int[] coords;
    private Color color;
    private Color fillColor;

    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        coords = new int[] {x1,y1,x2,y2};
        this.color = color;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int[] getCoords() { return (coords);}

    public Color getColor() {
        return color;
    }


}
