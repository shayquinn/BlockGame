
package blockgame;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author shays
 */
public class BlockGame extends JPanel implements ActionListener {

    Timer timer = new Timer(50, this);

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenW = (int) screenSize.getWidth();
    private final int screenH = (int) screenSize.getHeight();
    private final PaintSurface paintSurface;

    private final CubeObject cubeObject;
    private ArrayList<Cube> cubes;

    private final int cellSizeW;
    private final int cellSizeH;
    private final int row;
    private final int col;
    private final int w;
    private final int h;
    private final int startX;
    private final int startY;
    private Point mouse = new Point(0, 0);
    private Point snapMouse = new Point(0, 0);

    private boolean broken;
    private boolean[] patternsDispay = {true, true, true};
    
    
    private boolean dispalyRestart = true;
    private int displayCounter = 0;
    
    private boolean dispalyReturn = false;
    
    
    private int cubeUM;
    
    private boolean[] patternsPosable = {true, true, true};

    boolean testing = false;
    boolean testing1 = false;
    boolean testing2 = false;
    boolean testing3 = false;

    boolean mouseDown = false;
    private boolean selected = false;

    private int patternSelect = -1;
    private int snapPatternSelect = -1;

    int tempx = 185, tempy = 185;

    private ArrayList<Point[]> allPeaces;
    private ArrayList<Point[]> peaces;
    private ArrayList<Point[]> staticPeaces;
    
    private int[] blues;
    private int[] bluehightLight;
    
    
    

    private Point[] dragZones;

    private int counter1 = 0;

    private static Point xy = new Point(0, 0);

    public static Point getXy() {
        return xy;
    }

    public static void setXy(Point xy) {
        BlockGame.xy = xy;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.add(new BlockGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public BlockGame() {
        this.setSize(screenW, screenH);
        paintSurface = new PaintSurface();
        this.add(paintSurface, BorderLayout.CENTER);
        this.setVisible(true);

        this.cellSizeW = 50;
        this.cellSizeH = 50;
        this.row = 9;
        this.col = 9;
        this.w = cellSizeW * row;//getSize().width;
        this.h = cellSizeH * col;//getSize().height;

        this.startX = 750;
        this.startY = 150;

        cubeObject = new CubeObject(startX, startY, w, h, row, col, Color.black);
        createPeaces();
        timer.start();
    }//end Spiral constructer

    private void createPeaces() {

        allPeaces = new ArrayList<>();

        Point[] p1 = {
            new Point(0, 0),
            new Point(50, 50),
            new Point(-50, -50),};
        allPeaces.add(p1);

        Point[] p2 = {
            new Point(0, 0),
            new Point(0, 50),
            new Point(0, -50),};
        allPeaces.add(p2);

        Point[] p3 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),};
        allPeaces.add(p3);

        Point[] p4 = {
            new Point(0, 0)
        };
        allPeaces.add(p4);

        Point[] p5 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, 50),
            new Point(-50, 50)
        };
        allPeaces.add(p5);
        Point[] p6 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, -50),
            new Point(-50, -50)
        };
        allPeaces.add(p6);

        Point[] p7 = {
            new Point(0, 0),
            new Point(50, -50),
            new Point(-50, 50),};
        allPeaces.add(p7);

        Point[] p8 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, -50),
            new Point(-50, 50)
        };
        allPeaces.add(p8);

        Point[] p9 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, 50),
            new Point(-50, -50)
        };
        allPeaces.add(p9);

        /*
         Point[] p1 = {
             new Point(0, 0),
             new Point(50, 50),
             new Point(-50, -50),
             new Point(-50, 50),
             new Point(50, -50),
             new Point(100, 100),
             new Point(-100, -100),
             new Point(-100, 100),
             new Point(100, -100)
         };
         
         
         
         
         peaces.add(p1); 
         
          Point[] p2 = {
             new Point(0, 0),
             new Point(50, 50),
             new Point(-50, -50),
             new Point(-50, 50),
             new Point(50, -50),
         };
           peaces.add(p2); 
           
         */
        randomPeaces();
    }//end createPeaces

    private void randomPeaces() {
        peaces = new ArrayList<>();
        int[] temp = new int[3];
        int count = 0;
        do {
            int rand = (int) (Math.random() * ((allPeaces.size() - 1 - 0) + 1)) + 0;
            if (peaces.isEmpty()) {
                peaces.add(allPeaces.get(rand));
                count++;
            } else {
                if (!peaces.contains(allPeaces.get(rand))) {
                    peaces.add(allPeaces.get(rand));
                    count++;
                }
            }
        } while (count < 3);
    }//end randomPeaces

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
        }
    }

    public class PaintSurface extends JComponent {

        public PaintSurface() {
            this.setPreferredSize(new Dimension(screenW, screenH));
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                }//end mouseWheelMoved

                @Override
                public void mouseClicked(MouseEvent e) {

                }//end mouseClicked

                @Override
                public void mouseEntered(MouseEvent e) {
                }//end mouseEntered

                @Override
                public void mouseExited(MouseEvent e) {
                }//end mouseExited

                @Override
                public void mousePressed(MouseEvent e) {

                    if (e.getButton() == MouseEvent.BUTTON1) {
                        mouseDown = true;
                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                    }//System.out.println("Middle Click!");
                    if (e.getButton() == MouseEvent.BUTTON3) {
                    }
                    // pressCon();
                }//end mouseExited

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        pressCon();

                        mouseDown = false;
                        selected = false;
                        snapPatternSelect = patternSelect;
                        snapMouse = mouse;
                        patternSelect = -1;

                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                    }//System.out.println("Middle Click!");
                    if (e.getButton() == MouseEvent.BUTTON3) {
                    }

                }//end mouseReleased    
            });//end addMouseListener 

            this.addMouseMotionListener(new MouseMotionAdapter() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    mouse = new Point(e.getX(), e.getY());
                    contanes();
                    //checkShape();
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    mouse = new Point(e.getX(), e.getY());
                    contanes();
                    //checkShape();
                }

            });//end addMouseMotionListener                
        }//end PaintSurface constructer
    }//end PaintSurface

    private void contanes() {
        //testing1 = false;
        //testing2 = false;
        //testing3 = false;

        boolean[] ba = new boolean[cubes.size()];
        boolean t = false;
        int count = 0;
        broken = false;
        int brokenCounter = 0;

        testing = cons(cubeObject.getX(), cubeObject.getY(), cubeObject.getW(), cubeObject.getH(), mouse.x, mouse.y);

        for (int i = 0; i < dragZones.length; i++) {
            if (cons(dragZones[i].x, dragZones[i].y, (cubeObject.getCellW() * 3), (cubeObject.getCellH() * 3), mouse.x, mouse.y)) {
                if (!selected) {
                    if (mouseDown) {
                        selected = true;
                        patternSelect = i;
                    } else {

                    }
                }
                /*
                switch (i) {
                    case 0:
                        testing1 = true;
                        testing2 = false;
                        testing3 = false;
                        break;
                    case 1:
                        testing1 = false;
                        testing2 = true;
                        testing3 = false;
                        break;
                    case 2:
                        testing1 = false;
                        testing2 = false;
                        testing3 = true;
                        break;
                    default:
                        System.out.println("Error in switch contanes");
                }
                 */
            }
        }
        
       
                
        for (int i=0;i<cubes.size();i++) {
            if (cons(cubes.get(i).getX() + 1, cubes.get(i).getY() + 1, cubes.get(i).getW() - 1, cubes.get(i).getH() - 1, mouse.x, mouse.y)) {
                cubeUM = i;
                //System.out.println("cubeUM: "+cubeUM);
            }
        }
        
        
       
        if (patternSelect >= 0) {
             Point[] pa = peaces.get(patternSelect);
             int[] intpat = new int[pa.length];
             
             
           
            
            for (int i=0;i<cubes.size();i++) {
                for (int j=0;j<pa.length;j++) {
                    if (cons((cubes.get(i).getX() - pa[j].x) + 1, (cubes.get(i).getY() - pa[j].y) + 1, cubes.get(i).getW() - 1, cubes.get(i).getH() - 1, mouse.x, mouse.y)) {
                        t = true;
                        intpat[j] = i;
                    }
                }
                if (t) {
                    if (cubes.get(i).getNs() != 4) {
                        brokenCounter++;
                    }
                    ba[count] = true;
                } else {
                    ba[count] = false;
                }
                count++;
                t = false;
            }
            if (brokenCounter != pa.length) {
                broken = true;
            }

            int cc = 0;
            for (Cube c : cubes) {
                if (ba[cc]) {
                    if (c.getNs() != 4) {
                        if (broken) {
                            c.setNs(2);
                        } else {
                            c.setNs(3);

                        }
                    }
                } else {
                    if (c.getNs() != 4 && c.getNs() != 6) {
                        c.setNs(1);
                    }
                }
                cc++;
            }
           
            
            if(!broken){
            int[] temInt1 = new int[cubes.size()];
            for(int i=0;i<cubes.size();i++){
                for(int j=0;j < intpat.length;j++){
                    if(i == intpat[j]){
                        temInt1[i] = 4;
                        
                    }else{
                        temInt1[i] = cubes.get(i).getNs();
                    }
                }
            }
            //System.out.println(Arrays.toString(intpat));
            // System.out.println(Arrays.toString(temInt1));
            
            ArrayList<int[]> posable = checkCompleet(temInt1);
            System.out.println(posable.size());
            if (posable.size() > 0) {
                posable.forEach((ai) -> {
                for (int i : ai) {
                    cubes.get(i).setNs(6);
                    //if(cubes.get(i).getNs() == 4){
                    //    cubes.get(i).setNs(6);
                    //}     
                }
            });    
            }else{
                posable = null;
                for(Cube c: cubes){
                    if(c.getNs() == 6){
                       // c.setNs(4);
                    }
                }
            }
            
        }
            
        }
 
        //checkCompleet();
    }//end contanes

    private void pressCon() {
        if (patternSelect >= 0) {
            if (!broken) {
                Point[] pa = peaces.get(patternSelect);
                cubes.forEach((c) -> {
                    for (Point pa1 : pa) {
                        if (cons(c.getX(), c.getY(), c.getW(), c.getH(), mouse.x + pa1.x, mouse.y + pa1.y)) {
                            c.setNs(4);
                        }
                    }
                });

                patternsDispay[patternSelect] = false;
                if (patternsDispay[0] == false && patternsDispay[1] == false && patternsDispay[2] == false) {
                    randomPeaces();
                    for (int i = 0; i < patternsDispay.length; i++) {
                        patternsDispay[i] = true;
                    }
                    dispalyRestart = true;
                }
            }else{
                dispalyReturn = true;
            }
        }
        
        cubes.forEach((c) -> {
            if (c.getNs() != 4) {
                c.setNs(4);
            }else{
                c.setNs(1);
            }
        });
        int[] temInt2 = new int[cubes.size()];
        for(int i=0;i<cubes.size();i++){
            if(cubes.get(i).getNs() == 6){
                cubes.get(i).setNs(4);
            }
            temInt2[i] = cubes.get(i).getNs();
        }
      
       ArrayList<int[]> nullList = checkCompleet(temInt2);
       
            if (nullList.size() > 0) {
            counter1 = cubes.get(0).getW();
            nullList.forEach((ai) -> {
                for (int i : ai) {
                    cubes.get(i).setNs(5);
                }
            });
        }
        //checkShape();
    }//end pressCon

    
    // 
    private ArrayList<int[]> checkCompleet(int[] ca) {
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

    //check is shape fits on the bord
    private void checkShape() {
        
        int count2 = 0;
        broken = false;
        int brokenCounter = 0;
        
        for (int i=0; i<peaces.size();i++) {
            Point[] pa = peaces.get(i);
            boolean t = false;
            for (int j=0; j<cubes.size();j++) {
                Point targ = new Point(cubes.get(j).getX()+cubes.get(j).getW()/2, cubes.get(j).getY()+cubes.get(j).getH()/2);
                for (Cube c : cubes) {              
                    if(c.getNs() != 4){
                        for (Point pa1 : pa) {        
                            if (cons((c.getX() - pa1.x) + 1, (c.getY() - pa1.y) + 1, c.getW() - 1, c.getH() - 1, targ.x, targ.y)) {
                                t = true;
                            }
                        }
                    }
                    if (t) {
                        if (c.getNs() != 4) {
                            brokenCounter++;
                        }
                    } 
            }
            if (brokenCounter != pa.length) {
                patternsPosable[i] = true;
            }else{
                patternsPosable[i] = false;
            }
            }
        }
    }//end checkShape

    private boolean cons(int sx, int sy, int sw, int sh, int x, int y) {
        return x >= sx && y >= sy && x <= sx + sw && y <= sy + sh;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight());
        grid(g2);

    }//end paint

    private void pointershape(Graphics2D g2) {
    }

    private void grid(Graphics2D g2) {

        g2.setPaint(Color.black);
        cubes = cubeObject.getCubes();
        int clock = 0;
        for (Cube c : cubes) {
            switch (c.getNs()) {
                case 1:
                    g2.setPaint(Color.white);
                    g2.fill(c.getS());
                    g2.setPaint(Color.gray);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());

                    
                    Font font = new Font("Serif", Font.PLAIN, 12);
                    g2.setFont(font);
                    g2.drawString("" + clock + "", c.getX() + c.getW() / 2, c.getY() + c.getH() / 2);
                     
                    break;
                case 2:
                    g2.setPaint(new Color(200, 200, 200));
                    g2.fill(c.getS());
                    g2.setPaint(Color.gray);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());
                    break;
                case 3:
                    g2.setPaint(new Color(120, 120, 120));
                    g2.fill(c.getS());
                    g2.setPaint(Color.gray);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());
                    break;
                case 4:
                    g2.setPaint(Color.blue);
                    g2.fill(c.getS());
                    g2.setPaint(Color.gray);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());
                    break;
                case 5:
                    g2.setPaint(Color.white);
                    g2.fill(c.getS());
                    g2.setPaint(Color.gray);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());

                    if (counter1 >= 0) {
                        Shape pat = makeRectangle(
                                (c.getX() + c.getW() / 2) - (counter1 / 2),
                                (c.getY() + c.getH() / 2) - (counter1 / 2),
                                ((c.getX() + c.getW() / 2) - (counter1 / 2)) + counter1,
                                ((c.getY() + c.getH() / 2) - (counter1 / 2)) + counter1
                        );
                        g2.setPaint(new Color(0, 0, 0));
                        g2.fill(pat);
                        g2.draw(pat);
                        counter1--;
                    }
                     break;
                case 6:
                    g2.setPaint(new Color(100, 100, 230));
                    g2.fill(c.getS());
                    g2.setPaint(Color.gray);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());
                    break;
                default:
                    System.out.println("Color Switch not working!");
            }
            clock++;
        }

        g2.setPaint(cubeObject.getGc());

        g2.setStroke(new BasicStroke(1));
        int lGCW = cubeObject.getW()/3, lGCH = cubeObject.getH()/3;
        int dzgOffsetX = 200, dzgOffsetY = 200;
        int x = cubeObject.getX(), y = cubeObject.getY();
        int r = cubeObject.getRow(), c = cubeObject.getCol();
        int cellW = cubeObject.getCellW(), cellH = cubeObject.getCellH();

        //six grid        
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < cubes.size() / 27; i++) {
            for (int j = 0; j < cubes.size() / 27; j++) {
                Shape shape = makeRectangle(x + (lGCW * j), y + (lGCH * i), x + ((lGCW * j) + lGCW), y + ((lGCH * i) + lGCH));
                g2.draw(shape);
            }
        }

        ///// drag zones
        g2.setStroke(new BasicStroke(3));
        dragZones = new Point[3];
        for (int i = 0; i < 3; i++) {
            Shape shape = makeRectangle(dzgOffsetX, y + (lGCW * i), dzgOffsetY + lGCH, y + ((lGCW * i) + lGCH));
            dragZones[i] = new Point(dzgOffsetX, y + (lGCH * i));
            g2.setPaint(Color.white);
            g2.fill(shape);
            /*
            if (i == 0 && testing1) {
                g2.setPaint(Color.green);
            } else if (i == 1 && testing2) {
                g2.setPaint(Color.green);
            } else if (i == 2 && testing3) {
                g2.setPaint(Color.green);
            } else {
                g2.setPaint(Color.black);
            }
             */
            g2.setPaint(Color.black);
            g2.setStroke(new BasicStroke(3));
            g2.draw(shape);

        }
        ////

        //box border
        g2.setStroke(new BasicStroke(4));
        /*
        if (testing) {
            g2.setPaint(Color.green);
        } else {
            g2.setPaint(Color.black);
        }
         */
        g2.setPaint(Color.black);
        Shape shape = makeRectangle(cubeObject.getX(), cubeObject.getY(), cubeObject.getX() + cubeObject.getW(), cubeObject.getY() + cubeObject.getH());
        g2.draw(shape);

        //pattern
        for (int i = 0; i < peaces.size(); i++) {
            int startSize1 = 40;
            int startSize2 = 30;
            if (patternsDispay[i]) {
                if(dispalyRestart){
                    Shape pat;
                        for (Point get : peaces.get(i)) {
                            int nx = 0, ny = 0;
                            if (get.x > 0) {
                                nx = get.x + -20;
                            } else if (get.x < 0) {
                                nx = get.x - -20;
                            }
                            if (get.y > 0) {
                                ny = get.y + -20;
                            } else if (get.y < 0) {
                                ny = get.y - -20;
                            }
                            Point p = new Point(nx, ny);
                            pat = makeRectangle(
                                (dzgOffsetX + (lGCW / 2)) + (p.x-displayCounter/2),
                                (y+((lGCH * i)+(lGCH/2))) + (p.y-displayCounter/2),
                                (dzgOffsetX + (lGCW / 2)) + (p.x-displayCounter/2) + displayCounter,
                                (y+((lGCH * i)+(lGCH/2))) + (p.y-displayCounter/2) + displayCounter
                            );
                            if(patternsPosable[i]){
                               g2.setPaint(new Color(0, 0, 200));
                            }else{
                               g2.setPaint(new Color(150, 0, 150));

                            }
                            g2.fill(pat);
                            g2.setPaint(Color.gray);
                            g2.setStroke(new BasicStroke(1));
                            g2.draw(pat);
                        }
                        if(displayCounter<startSize2){
                            displayCounter++;
                        }else{
                            dispalyRestart = false;
                        }
                    
                }else{
                    displayCounter = 0;
                    Shape pat;
                    for (Point get : peaces.get(i)) {}
                if (i == patternSelect && mouseDown) {
                    for (Point get : peaces.get(i)) {
                        int nx = get.x;
                        int ny = get.y;
                        Point p = new Point(nx, ny);
                        pat = makeRectangle(
                                mouse.x + (p.x - (startSize1 / 2)),
                                mouse.y + (p.y - (startSize1 / 2)),
                                mouse.x + (p.x + (startSize1 - (startSize1 / 2))),
                                mouse.y + (p.y + (startSize1 - (startSize1 / 2)))
                        );
                        g2.setPaint(new Color(100, 100, 230));
                        g2.fill(pat);
                        g2.setPaint(Color.gray);
                        g2.setStroke(new BasicStroke(1));
                        g2.draw(pat);
                    }
              
                    if (tempx > mouse.x) {
                        tempx--;
                    } else if (tempx < mouse.x) {
                        tempx++;
                    }
                    if (tempy > mouse.y) {
                        tempy--;
                    } else if (tempy < mouse.y) {
                        tempy++;
                    }
                } else {
                    for (Point get : peaces.get(i)) {
                        int nx = 0, ny = 0;
                        if (get.x > 0) {
                            nx = get.x + -20;
                        } else if (get.x < 0) {
                            nx = get.x - -20;
                        }
                        if (get.y > 0) {
                            ny = get.y + -20;
                        } else if (get.y < 0) {
                            ny = get.y - -20;
                        }
                        Point p = new Point(nx, ny);
                        pat = makeRectangle(
                            (dzgOffsetX + (lGCW / 2)) + (p.x-startSize2/2),
                            (y+((lGCH * i)+(lGCH/2))) + (p.y-startSize2/2),
                            (dzgOffsetX + (lGCW / 2)) + (p.x-startSize2/2) + startSize2,
                            (y+((lGCH * i)+(lGCH/2))) + (p.y-startSize2/2) + startSize2
                        );
                        if(patternsPosable[i]){
                           g2.setPaint(new Color(0, 0, 200));
                        }else{
                           g2.setPaint(new Color(150, 0, 150));
                            
                        }
                        g2.fill(pat);
                        g2.setPaint(Color.gray);
                        g2.setStroke(new BasicStroke(1));
                        g2.draw(pat);
                    }
                    
                }
                }

            }
        }

        ///
        /*
        Point[] pa = peaces.get(patternSelect); 
         
         Shape pattern;
            int cs = 35;
         
        for (Point pa1 : pa) {
            pattern = makeRectangle(mouse.x + pa1.x - (cs/2), mouse.y + pa1.y - (cs/2), ((mouse.x + pa1.x) - (cs/2)) + cs, ((mouse.y + pa1.y) - (cs/2)) + cs);
            g2.setPaint(Color.blue);
            g2.fill(pattern);
            g2.setPaint(new Color(0,0,110));
            g2.setStroke(new BasicStroke(3));
            g2.draw(pattern);
        }
        
         */
    }//end paintBackground

    private Line2D.Float makeLine(int x1, int y1, int x2, int y2) {
        return new Line2D.Float(x1, y1, x2, y2);
    }//end makeLine

    private Ellipse2D.Float drawEllipse(int x1, int y1, int size) {
        return new Ellipse2D.Float(x1 - (size / 2), y1 - (size / 2), size, size);
    }//end drawEllipse

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }//end makeRectangle

    private double[] convert(double ang, double x, double y, double cx, double cy) {
        return new double[]{
            cx + Math.cos(ang * Math.PI / 180) * (x - cx) - Math.sin(ang * Math.PI / 180) * (y - cy),
            cy + Math.sin(ang * Math.PI / 180) * (x - cx) + Math.cos(ang * Math.PI / 180) * (y - cy)
        };
    }//end convert

    /*
    int[] array1 = {0, 1, 2, 9, 10, 11, 18, 19, 20};
    int[] array2 = {3, 4, 5, 12, 13, 14, 21, 22, 23};
    int[] array3 = {6, 7, 8, 15, 16, 17, 24, 25, 26};
    int[] array4 = {27, 28, 29, 36, 37, 38, 45, 46, 47};
    int[] array5 = {30, 31, 32, 39, 40, 41, 48, 49, 50};
    int[] array6 = {33, 34, 35, 42, 43, 44, 51, 52, 53};
    int[] array7 = {54, 55, 56, 63, 64, 65, 72, 73, 74};
    int[] array8 = {57, 58, 59, 66, 67, 68, 75, 76, 77};
    int[] array9 = {60, 61, 62, 69, 70, 71, 78, 79, 80};

    int[] aa1 = {0, 9, 18, 27, 36, 45, 54, 63, 72};
    int[] aa2 = {1, 10, 19, 28, 37, 46, 55, 64, 73};
    int[] aa3 = {2, 11, 20, 29, 38, 47, 56, 65, 74};
    int[] aa4 = {3, 12, 21, 30, 39, 48, 57, 66, 75};
    int[] aa5 = {4, 13, 22, 31, 40, 49, 58, 67, 76};
    int[] aa6 = {5, 14, 23, 32, 41, 50, 59, 68, 77};
    int[] aa7 = {6, 15, 24, 33, 42, 51, 60, 69, 78};
    int[] aa8 = {7, 16, 25, 34, 43, 52, 61, 70, 79};
    int[] aa9 = {8, 17, 26, 35, 44, 53, 62, 71, 80};

    int[] ab1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    int[] ab2 = {9, 10, 11, 12, 13, 14, 15, 16, 17};
    int[] ab3 = {18, 19, 20, 21, 22, 23, 24, 25, 26};
    int[] ab4 = {27, 28, 29, 30, 31, 32, 33, 34, 35};
    int[] ab5 = {36, 37, 38, 39, 40, 41, 42, 43, 44};
    int[] ab6 = {45, 46, 47, 48, 49, 50, 51, 52, 53};
    int[] ab7 = {54, 55, 56, 57, 58, 59, 60, 61, 62};
    int[] ab8 = {63, 64, 65, 66, 67, 68, 69, 70, 71};
    int[] ab9 = {72, 73, 74, 75, 76, 77, 78, 79, 80};
     */
}//end Spiral

