
package blockgame;

import java.awt.Point;
import java.awt.Shape;
    
   
public class PuzzleMenu {
    private Utility U = new Utility();
    private int x, y, w, h;
    private int cellW, cellH;
    private Point[] dragZones = new Point[3];
    private Point[] dragZonesC = new Point[3];
    private Shape[] zoneShapes = new Shape[3];
    
    
    
    public PuzzleMenu(int x, int y,int w, int h){
        this.x  = x;
        this.y  = y;
        this.w  = w;
        this.h  = h;
        this.cellW = w / 3;
        this.cellH = h / 3;
        for (int i = 0; i < 3; i++) {
            zoneShapes[i] = U.makeRectangle(x, y + (cellW * i), x + cellH, y + ((cellW * i) + cellH));
            dragZones[i] = new Point(x, y + (cellH * i));
            dragZonesC[i] = new Point(x+(cellW/2), y + (cellH * i)+(cellH/2));
        }
    }//end constructer

    public Point[] getDragZonesC() {
        return dragZonesC;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getCellW() {
        return cellW;
    }

    public int getCellH() {
        return cellH;
    }

    public Point[] getDragZones() {
        return dragZones;
    }

    public Shape[] getZoneShapes() {
        return zoneShapes;
    }
    
}//end class
