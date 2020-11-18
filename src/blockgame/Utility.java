package blockgame;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Utility {

    ArrayList<int[]> checkCompleet(int[] ca) {
        int[] passArray;
        int t = 4;
        ArrayList<int[]> iapa = new ArrayList<>();
        //vertical
        for (int i = 0, m = 0; i < ca.length; i += 9, m++) {
            boolean bull1 = true;
            passArray = new int[9];
            for (int j = 0; j < 9; j++) {
                passArray[j] = i + j;
                if (ca[i + j] != t) {
                    bull1 = false;
                }
            }
            if (bull1) {
                iapa.add(passArray);
            }
        }
        //horazontal
        for (int i = 0; i < 9; i++) {
            boolean bull2 = true;
            passArray = new int[9];
            for (int j = 0; j < ca.length; j += 9) {
                passArray[j / 9] = i + j;
                if (ca[i + j] != t) {
                    bull2 = false;
                }
            }
            if (bull2) {
                iapa.add(passArray);
            }
        }

        //cube
        //int[][] inf1 = new int[9][9];
        for (int i = 0, l = 0; i < ca.length; i += ca.length / 3, l++) {
            for (int j = 0, m = 0; j < 9; j += 3, m++) {
                boolean bull3 = true;
                passArray = new int[9];
                for (int n = 0; n < 3; n++) {
                    for (int o = 0; o < 3; o++) {
                        //inf1[(3*l)+m][(3*n)+o] = (i+j)+(9*n)+o; 
                        passArray[(3 * n) + o] = (i + j) + (9 * n) + o;
                        if (ca[(i + j) + (9 * n) + o] != t) {
                            bull3 = false;
                        }
                    }
                }
                if (bull3) {
                    iapa.add(passArray);
                }
            }
        }
        return iapa;
        
    }//end checkCompleet
    
 

    boolean cons(int sx, int sy, int sw, int sh, int x, int y) {
        return x >= sx && y >= sy && x <= sx + sw && y <= sy + sh;
    }//end cons

    Line2D.Float makeLine(int x1, int y1, int x2, int y2) {
        return new Line2D.Float(x1, y1, x2, y2);
    }//end makeLine

    Ellipse2D.Float drawEllipse(int x1, int y1, int size) {
        return new Ellipse2D.Float(x1 - (size / 2), y1 - (size / 2), size, size);
    }//end drawEllipse

    Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }//end makeRectangle

    double[] convert(double ang, double x, double y, double cx, double cy) {
        return new double[]{
            cx + Math.cos(ang * Math.PI / 180) * (x - cx) - Math.sin(ang * Math.PI / 180) * (y - cy),
            cy + Math.sin(ang * Math.PI / 180) * (x - cx) + Math.cos(ang * Math.PI / 180) * (y - cy)
        };
    }//end convert

    int[] convertIntegers(ArrayList<Integer> integers) {
        //https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i);
        }
        return ret;
    }//end convertIntegers

    //check is shape fits on the bord
    boolean[] checkShape(ArrayList<Point[]> peaces, ArrayList<Cube> cubes) {
        boolean[] patternsPosable = {false, false, false};
        for (int i = 0; i < peaces.size(); i++) {
            boolean cbull = false;
            Point[] pa = peaces.get(i);
             for (int j = 0; j < cubes.size(); j++) {
                 if(cubes.get(j).getNs() != 4){
                     if(checkPatternOnBlock(new Point(cubes.get(j).getX()+25, cubes.get(j).getY()+25), pa, cubes)){
                         cbull = true;
                         break;
                    }
                 }
             }
                
            patternsPosable[i] = cbull;
        }
        return patternsPosable;
    }//end checkShape

    boolean checkPatternOnBlock(Point point, Point[] pattern, ArrayList<Cube> cubes) {
        int counter = 0;
        boolean bull = true;
        boolean t = false;
        for (int i = 0; i < cubes.size(); i++) {
            for (Point pattern1 : pattern) {
                if (cons((cubes.get(i).getX() - pattern1.x) + 1, (cubes.get(i).getY() - pattern1.y) + 1, cubes.get(i).getW() - 1, cubes.get(i).getH() - 1, point.x, point.y)) {
                    t = true;
                }
            }
            if (t) {
                if (cubes.get(i).getNs() != 4) {
                    counter++;
                }
            }
            t = false;
        }
        if (counter != pattern.length) {
            bull = false;
        }
        return bull;
    }//end checkPatternOnBlock
}
