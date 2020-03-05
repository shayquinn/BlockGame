
package blockgame;

import java.awt.Shape;

 class Cube{
        private final int x;
        private final int y;      
        private final int w;
        private final int h;
        private final Shape s;
        private int ns;

        public Cube(int x, int y, int w, int h, Shape s, int ns) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.s = s;
            this.ns = ns;
        }
        
        @Override
    public String toString() {
        return String.format(
                 "Cube .............................\n"
                + "x: " + x + ",\n"
                + "y: " + y + ",\n"
                + "w: " + w + ",\n"
                + "h: " + h + ",\n"
                + "s: " + s + ",\n"
                + "ns: " + ns + ",\n"
                + ".................................."
        );
    }//end toString
        
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
        
        public Shape getS() {
            return s;
        }

        public int getNs() {
            return ns;
        }

         public void setNs(int ns) {
            this.ns = ns;
        }
        


       
    }// end Cube