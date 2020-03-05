
package blockgame;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.BoundingBox;

 class CubeObject{
     private int h;
     private int w;
     private int x;
     private int y;
     private int row;
     private int col;
     private final int cellW;
     private final int cellH;
     private Color gc;
     private ArrayList<Cube> cubes;

        public CubeObject( int x, int y, int w, int h, int row, int col, Color gc) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;            
            this.row = row;
            this.col = col;
            this.gc = gc;
            this.cellW = w/row;
            this.cellH = h/col;
            
            craeteBlockArray();
        }
        
    @Override
    public String toString() {
        return String.format(
                "CubeObject ........................\n"
                + "x: " + x + ",\n"
                + "y: " + y + ",\n"
                + "w: " + w + ",\n"
                + "h: " + h + ",\n"
                + "row: " + row + ",\n"
                + "col: " + col + ",\n"
                + "gc: " + gc + ",\n"
                + "cellW: " + cellW + ",\n"
                + "cellH: " + cellH + ",\n"
                + "cubes: " + cubes + ",\n"           
                + ".................................."
        );
    }
        
    private void craeteBlockArray(){
        cubes = new ArrayList<>();
        for(int i = 0; i < w; i += cellW){
            for(int j = 0; j < h; j += cellH){
                Shape shape = makeRectangle(i+x, j+y, (i+x)+cellW, (j+y)+cellH);   
                cubes.add(new Cube(i+x, j+y, cellW, cellH, shape, 1));
            }
        }
    }//end craeteBlockArray
 
    	private int getRandomNumberInRange(int min, int max) {
            //https://mkyong.com/java/java-generate-random-integers-in-a-range/
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
        
            private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }//end makeRectangle

        public int getCellW() {
            return cellW;
        }

        public int getCellH() {
            return cellH;
        }
        
        

        public int getH() {
            return h;
        }

        public int getW() {
            return w;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public Color getGc() {
            return gc;
        }

        public ArrayList<Cube> getCubes() {
            return cubes;
        }
 
 
 }//end CubeObject