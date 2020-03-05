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
    private Utility U = new Utility();
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

    private ArrayList<Integer> tempBlueArray;
    private ArrayList<Integer> blueArray;
    private ArrayList<int[]> lightBlueArray;
    private ArrayList<Integer> grayArray;
    private ArrayList<Integer> lightGrayArray;

    private boolean[] noFit = {true, true, true};

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
        tempBlueArray = new ArrayList<>();
        //testing1 = false;
        //testing2 = false;
        //testing3 = false;

        boolean[] ba = new boolean[cubes.size()];
        boolean t = false;
        int count = 0;
        broken = false;
        int brokenCounter = 0;

        testing = U.cons(cubeObject.getX(), cubeObject.getY(), cubeObject.getW(), cubeObject.getH(), mouse.x, mouse.y);

        for (int i = 0; i < dragZones.length; i++) {
            if (U.cons(dragZones[i].x, dragZones[i].y, (cubeObject.getCellW() * 3), (cubeObject.getCellH() * 3), mouse.x, mouse.y)) {
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

        for (int i = 0; i < cubes.size(); i++) {
            if (U.cons(cubes.get(i).getX() + 1, cubes.get(i).getY() + 1, cubes.get(i).getW() - 1, cubes.get(i).getH() - 1, mouse.x, mouse.y)) {
                cubeUM = i;
                //System.out.println("cubeUM: "+cubeUM);
            }
        }

        if (patternSelect >= 0) {
            Point[] pa = peaces.get(patternSelect);
            int[] intpat = new int[pa.length];

            for (int i = 0; i < cubes.size(); i++) {
                tempBlueArray.add(cubes.get(i).getNs());

                for (int j = 0; j < pa.length; j++) {
                    if (U.cons((cubes.get(i).getX() - pa[j].x) + 1, (cubes.get(i).getY() - pa[j].y) + 1, cubes.get(i).getW() - 1, cubes.get(i).getH() - 1, mouse.x, mouse.y)) {
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

            lightGrayArray = new ArrayList<>();
            grayArray = new ArrayList<>();
            for (int i = 0; i < cubes.size(); i++) {
                if (ba[i]) {
                    if (cubes.get(i).getNs() != 4) {
                        if (broken) {
                            cubes.get(i).setNs(2);
                            lightGrayArray.add(i);
                        } else {
                            tempBlueArray.set(i, 4);
                            cubes.get(i).setNs(3);
                            grayArray.add(i);

                        }
                    }
                } else {
                    if (cubes.get(i).getNs() != 4) {
                        cubes.get(i).setNs(1);
                    }
                }
            }

            if (!broken) {
                int[] temInt1 = new int[cubes.size()];
                for (int i = 0; i < cubes.size(); i++) {
                    for (int j = 0; j < intpat.length; j++) {
                        if (i == intpat[j]) {
                            temInt1[i] = 4;
                        } else {
                            temInt1[i] = cubes.get(i).getNs();
                        }
                    }
                }

                lightBlueArray = new ArrayList<>();
                lightBlueArray = U.checkCompleet(U.convertIntegers(tempBlueArray));
                //System.out.println(Arrays.toString(intpat));
                // System.out.println(Arrays.toString(temInt1));
                /*
                
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
                } else {
                    posable = null;
                    for (Cube c : cubes) {
                        if (c.getNs() == 6) {
                            // c.setNs(4);
                        }
                    }
                }*/

            }

        }
        //if (patternSelect >= 0) {
        //    System.out.println(U.checkPatternOnBlock(new Point(mouse.x, mouse.y), peaces.get(patternSelect), cubes));
        //}
        //checkCompleet();
        
        patternsPosable = U.checkShape(peaces, cubes);
    }//end contanes

    private void pressCon() {
        if (patternSelect >= 0) {
            if (!broken) {
                Point[] pa = peaces.get(patternSelect);
                for (int i = 0; i < cubes.size(); i++) {
                    for (Point pa1 : pa) {
                        if (U.cons(cubes.get(i).getX(), cubes.get(i).getY(), cubes.get(i).getW(), cubes.get(i).getH(), mouse.x + pa1.x, mouse.y + pa1.y)) {
                            cubes.get(i).setNs(4);
                        }
                    }
                }

                patternsDispay[patternSelect] = false;
                if (patternsDispay[0] == false && patternsDispay[1] == false && patternsDispay[2] == false) {
                    randomPeaces();
                    for (int i = 0; i < patternsDispay.length; i++) {
                        patternsDispay[i] = true;
                    }
                    dispalyRestart = true;
                }
            } else {
                dispalyReturn = true;
            }
        }

        cubes.forEach((c) -> {
            if (c.getNs() == 4) {
                c.setNs(4);
            } else {
                c.setNs(1);
            }
        });
        int[] temInt2 = new int[cubes.size()];
        for (int i = 0; i < cubes.size(); i++) {
            temInt2[i] = cubes.get(i).getNs();
        }

        ArrayList<int[]> nullList = U.checkCompleet(temInt2);

        if (nullList.size() > 0) {
            counter1 = cubes.get(0).getW();
            nullList.forEach((ai) -> {
                for (int i : ai) {
                    cubes.get(i).setNs(5);
                }
            });
        }
        noFit = U.checkShape(peaces, cubes);
        System.out.println(noFit[0] + ", " + noFit[1] + ", " + noFit[2] + ",");

    }//end pressCon

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

                    /*
                    Font font = new Font("Serif", Font.PLAIN, 12);
                    g2.setFont(font);
                    g2.drawString("" + clock + "", c.getX() + c.getW() / 2, c.getY() + c.getH() / 2);
                     */
                    break;
                case 2:
                    lightBlueArray = null;
                    g2.setPaint(new Color(200, 200, 200));
                    g2.fill(c.getS());
                    g2.setPaint(Color.black);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());
                    break;
                case 3:
                    g2.setPaint(new Color(120, 120, 120));
                    g2.fill(c.getS());
                    g2.setPaint(Color.black);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());
                    break;
                case 4:
                    g2.setPaint(Color.blue);
                    g2.fill(c.getS());
                    g2.setPaint(Color.black);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());
                    break;
                case 5:
                    g2.setPaint(Color.white);
                    g2.fill(c.getS());
                    g2.setPaint(Color.black);
                    g2.setStroke(new BasicStroke(1));
                    g2.draw(c.getS());

                    if (counter1 >= 0) {
                        lightBlueArray = null;
                        Shape pat = U.makeRectangle(
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
                default:
                    System.out.println("Color Switch not working!");
            }
            clock++;
        }

        if (lightBlueArray != null) {
            if (lightBlueArray.size() > 0) {
                for (int i = 0; i < cubes.size(); i++) {
                    for (int j = 0; j < lightBlueArray.size(); j++) {
                        for (int l = 0; l < lightBlueArray.get(j).length; l++) {
                            if (i == lightBlueArray.get(j)[l]) {
                                boolean trig = true;
                                for (int m = 0; m < grayArray.size(); m++) {
                                    if (i == grayArray.get(m)) {
                                        trig = false;
                                    }
                                }
                                if (trig) {
                                    g2.setPaint(new Color(100, 100, 230));
                                    g2.fill(cubes.get(i).getS());
                                    g2.setPaint(Color.black);
                                    g2.setStroke(new BasicStroke(1));
                                    g2.draw(cubes.get(i).getS());
                                }
                            }
                        }
                    }
                }
            }
        }

        g2.setPaint(cubeObject.getGc());

        g2.setStroke(new BasicStroke(1));
        int lGCW = cubeObject.getW() / 3, lGCH = cubeObject.getH() / 3;
        int dzgOffsetX = 200, dzgOffsetY = 200;
        int x = cubeObject.getX(), y = cubeObject.getY();
        int r = cubeObject.getRow(), c = cubeObject.getCol();
        int cellW = cubeObject.getCellW(), cellH = cubeObject.getCellH();

        //six grid        
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < cubes.size() / 27; i++) {
            for (int j = 0; j < cubes.size() / 27; j++) {
                Shape shape = U.makeRectangle(x + (lGCW * j), y + (lGCH * i), x + ((lGCW * j) + lGCW), y + ((lGCH * i) + lGCH));
                g2.draw(shape);
            }
        }

        ///// drag zones
        g2.setStroke(new BasicStroke(3));
        dragZones = new Point[3];
        for (int i = 0; i < 3; i++) {
            Shape shape = U.makeRectangle(dzgOffsetX, y + (lGCW * i), dzgOffsetY + lGCH, y + ((lGCW * i) + lGCH));
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
        Shape shape = U.makeRectangle(cubeObject.getX(), cubeObject.getY(), cubeObject.getX() + cubeObject.getW(), cubeObject.getY() + cubeObject.getH());
        g2.draw(shape);

        //pattern
        for (int i = 0; i < peaces.size(); i++) {
            int startSize1 = 40;
            int startSize2 = 30;
            if (patternsDispay[i]) {
                if (dispalyRestart) {
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
                        pat = U.makeRectangle(
                                (dzgOffsetX + (lGCW / 2)) + (p.x - displayCounter / 2),
                                (y + ((lGCH * i) + (lGCH / 2))) + (p.y - displayCounter / 2),
                                (dzgOffsetX + (lGCW / 2)) + (p.x - displayCounter / 2) + displayCounter,
                                (y + ((lGCH * i) + (lGCH / 2))) + (p.y - displayCounter / 2) + displayCounter
                        );
                        if (patternsPosable[i]) {
                            g2.setPaint(new Color(0, 0, 200));
                        } else {
                            g2.setPaint(new Color(150, 0, 150));

                        }
                        g2.fill(pat);
                        g2.setPaint(Color.gray);
                        g2.setStroke(new BasicStroke(1));
                        g2.draw(pat);
                    }
                    if (displayCounter < startSize2) {
                        displayCounter++;
                    } else {
                        dispalyRestart = false;
                    }

                } else {
                    displayCounter = 0;
                    Shape pat;
                    for (Point get : peaces.get(i)) {
                    }
                    if (i == patternSelect && mouseDown) {
                        for (Point get : peaces.get(i)) {
                            int nx = get.x;
                            int ny = get.y;
                            Point p = new Point(nx, ny);
                            pat = U.makeRectangle(
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
                            pat = U.makeRectangle(
                                    (dzgOffsetX + (lGCW / 2)) + (p.x - startSize2 / 2),
                                    (y + ((lGCH * i) + (lGCH / 2))) + (p.y - startSize2 / 2),
                                    (dzgOffsetX + (lGCW / 2)) + (p.x - startSize2 / 2) + startSize2,
                                    (y + ((lGCH * i) + (lGCH / 2))) + (p.y - startSize2 / 2) + startSize2
                            );
                            if (patternsPosable[i]) {
                                g2.setPaint(new Color(0, 0, 200));
                            } else {
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

    }//end paintBackground

}//end Spiral

