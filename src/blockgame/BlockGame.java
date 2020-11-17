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
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author shays
 */
public class BlockGame extends JPanel implements ActionListener {

    Timer timer = new Timer(40, this);

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenW = (int) screenSize.getWidth();
    private final int screenH = (int) screenSize.getHeight();
    private final PaintSurface paintSurface;
    private Utility U = new Utility();
    private PuzzleMenu PM;
    private Score SC;
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
    private int snapCubeUM;

    private boolean[] patternsPosable = {true, true, true};

    boolean mouseDown = false;
    private boolean selected = false;

    private int patternSelect = -1;
    private int tempPatternSelect = -1;

    private int tempx = 185, tempy = 185;
    private int anR = 100, anG = 100, anB = 230;

    private int newx = 0, newy = 0;

    private int startSize1 = 40;
    private int tempStartSize1 = startSize1;
    private int startSize2 = 30;
    private int tempStartSize2 = startSize2;
    private int tempGap = 0;
    private double an2R = 100.0, an2G = 100.0, an2B = 230.0;

    private double animationReturnX = 0, animationReturnY = 0;
    
    private int multaplayer = 0;
    private int scoreCount = 0;

    private Point[] tempPattern;

    private ArrayList<Point[]> allPeaces;
    private ArrayList<Point[]> peaces;

    private ArrayList<Integer> tempBlueArray;
    private ArrayList<int[]> lightBlueArray;
    private ArrayList<Integer> grayArray;
    private ArrayList<Integer> lightGrayArray;

    private boolean[] noFit = {true, true, true};

    private boolean setAnimation = false;
    private boolean setAnimationReturn = false;
    private int setAnimationCounter = 0;

    private int counter1 = 0;
    private int animationReturnCount = 0;

    private int animationReturnTimer = 0;

    
    private boolean end = false;
    private final int menux1 = 400;
    private final int menuy1 = 200;
    private final int menux2 = 400;
    private final int menuy2 = 400;
    private final int menuw = 200;
    private final int menuh = 100;
    boolean menu1hov = false, menu2hov = false;

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
        PM = new PuzzleMenu(200, 150, cubeObject.getW(), cubeObject.getH());
        SC = new Score(200, 100, 40, "Helvetica", Color.BLACK);
        timer.start();
    }//end Spiral constructer

    private void createPeaces() {

        allPeaces = new ArrayList<>();

        Point[] p1 = {
            new Point(0, 0)
        };
        allPeaces.add(p1);

        Point[] p2 = {
            new Point(0, 0),
            new Point(0, 50)
        };
        allPeaces.add(p2);

        Point[] p3 = {
            new Point(0, 0),
            new Point(50, 0)
        };
        allPeaces.add(p3);

        Point[] p6 = {
            new Point(0, 0),
            new Point(-50, -50)
        };
        allPeaces.add(p6);

        Point[] p7 = {
            new Point(0, 0),
            new Point(50, 50)
        };
        allPeaces.add(p7);

        Point[] p10 = {
            new Point(0, 0),
            new Point(50, 50),
            new Point(-50, -50),};
        allPeaces.add(p10);

        Point[] p11 = {
            new Point(0, 0),
            new Point(0, 50),
            new Point(0, -50),};
        allPeaces.add(p11);

        Point[] p12 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),};
        allPeaces.add(p12);

        Point[] p13 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, 50),
            new Point(-50, 50)
        };
        allPeaces.add(p13);

        Point[] p14 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, -50),
            new Point(-50, -50)
        };
        allPeaces.add(p14);

        Point[] p15 = {
            new Point(0, 0),
            new Point(50, -50),
            new Point(-50, 50),};
        allPeaces.add(p15);

        Point[] p16 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, -50),
            new Point(-50, 50)
        };
        allPeaces.add(p16);

        Point[] p17 = {
            new Point(0, 0),
            new Point(50, 0),
            new Point(-50, 0),
            new Point(50, 50),
            new Point(-50, -50)
        };
        allPeaces.add(p17);

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
                        if (end) {
                            if (menu1hov) {
                                end = false;
                                for (Cube c : cubes) {
                                    c.setNs(1);
                                }
                                
                                randomPeaces();
                                SC.setTempScore(0);
                                SC.setScore(0);
                                Sound re = new Sound("Pickup_Coin4.wav");
                                re.start();
                                for (int i = 0; i < noFit.length; i++) {
                                    noFit[i] = true;
                                    patternsDispay[i] = true;
                                }
                                

                            }
                            if (menu2hov) {
                                System.exit(1);
                            }
                        } else {
                            pressCon();
                        }

                        mouseDown = false;
                        selected = false;

                        snapMouse = mouse;

                        newx = snapMouse.x;
                        newy = snapMouse.y;
                        tempPatternSelect = patternSelect;
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
                    if (!end) {
                        contanes();
                    } else {
                        menu();
                    }

                    //checkShape();
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    mouse = new Point(e.getX(), e.getY());
                    if (!end) {
                        contanes();
                    } else {
                        menu();
                    }
                    //checkShape();
                }

            });//end addMouseMotionListener                
        }//end PaintSurface constructer
    }//end PaintSurface

    private void menu() {
        if (U.cons(menux1, menuy1, menuw, menuh, mouse.x, mouse.y)) {
            menu1hov = true;
        } else {
            menu1hov = false;
        }
        if (U.cons(menux2, menuy2, menuw, menuh, mouse.x, mouse.y)) {
            menu2hov = true;
        } else {
            menu2hov = false;
        }

    }//end menu

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

        for (int i = 0; i < PM.getDragZones().length; i++) {
            if (U.cons(PM.getDragZones()[i].x, PM.getDragZones()[i].y, (cubeObject.getCellW() * 3), (cubeObject.getCellH() * 3), mouse.x, mouse.y)) {
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
                /*
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
                */

                //lightBlueArray = new ArrayList<>();
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
                Sound s = new Sound("drop4.wav");
                s.start();
                setAnimation = true;
                setAnimationCounter = 20;

                for (int i = 0; i < cubes.size(); i++) {
                    if (U.cons(cubes.get(i).getX() + 1, cubes.get(i).getY() + 1, cubes.get(i).getW() - 1, cubes.get(i).getH() - 1, mouse.x, mouse.y)) {
                        snapCubeUM = i;
                        //System.out.println("cubeUM: "+cubeUM);
                    }
                }
                tempPattern = peaces.get(patternSelect);

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
                setAnimationReturn = true;
                Sound s = new Sound("return.wav");
                s.start();
                /*try {
                    new Thread() {
                        public void run() {
                            
                        }
                    }.start();
                } catch (Throwable e) {
                    e.printStackTrace();
                }*/

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

    }//end pressCon

    private void checkForCompleetNull() {
        int[] temInt2 = new int[cubes.size()];
        for (int i = 0; i < cubes.size(); i++) {
            temInt2[i] = cubes.get(i).getNs();
        }
        ArrayList<int[]> nullList = U.checkCompleet(temInt2);

        multaplayer = nullList.size();
        
        scoreCount = 0;
        if(nullList.isEmpty()){
           scoreCount = peaces.get(tempPatternSelect).length;
           SC.setTempScore(SC.getScore()+scoreCount);    
        }else if(nullList.size()==1){
            scoreCount = nullList.get(0).length;
            SC.setTempScore(SC.getScore()+scoreCount);
            counter1 = cubes.get(0).getW();
            Sound s2 = new Sound("351543__richerlandtv__programme-complete.wav");
            s2.start();
            nullList.forEach((ai) -> {
            for (int i : ai) {
                cubes.get(i).setNs(5);
                }
            });
        }else  if(nullList.size()>1){
            int count = 0;
            for(int i=0;i<nullList.size();i++){
                count += nullList.get(i).length;
            }
            scoreCount = count*nullList.size();
            SC.setTempScore(SC.getScore()+scoreCount);
            
            counter1 = cubes.get(0).getW();
            Sound s2 = new Sound("Randomize3.wav");
            s2.start();
            nullList.forEach((ai) -> {
            for (int i : ai) {
                cubes.get(i).setNs(5);
                }
            });
        }

        noFit = U.checkShape(peaces, cubes);
        boolean[] tembull = {true, true, true};
        if (patternsDispay[0] == false && patternsDispay[1] == false && patternsDispay[2] == false) {
        } else {
            if (!patternsDispay[0]) {
                tembull[0] = false;
            } else {
                tembull[0] = noFit[0];
            }

            if (!patternsDispay[1]) {
                tembull[1] = false;
            } else {
                tembull[1] = noFit[1];
            }

            if (!patternsDispay[2]) {
                tembull[2] = false;
            } else {
                tembull[2] = noFit[2];
            }

            if (tembull[0] == false && tembull[1] == false && tembull[2] == false) {
                end = true;
            }
        }
    }//end checkForCompleetNull

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight());
        grid(g2);
        puzzleMenu(g2);
        
        score(g2);
        
        if (setAnimation) {
            animPlace(g2);
        }

        if (setAnimationReturn) {
            animationReturn(g2);
        }
        if (end) {
            menu(g2);
        }

    }//end paint

    private void score(Graphics2D g2) {       
        FontRenderContext frc = g2.getFontRenderContext();  
        TextLayout textTl = new TextLayout("Score: "+String.valueOf(SC.getScore()), SC.getF(), frc);
        
        TextLayout textT2 = new TextLayout(
                "$$ Multaplayer "+String.valueOf(multaplayer)+" $$",
                SC.getF(),
                frc);
        
        
        AffineTransform oldXForm = g2.getTransform();
        
        AffineTransform transform1 = new AffineTransform();
        AffineTransform transform2 = new AffineTransform();
        
        Shape outline1 = textTl.getOutline(null);
        Shape outline2 = textT2.getOutline(null);
             
        //Rectangle outlineBounds = outline.getBounds();
        transform1 = g2.getTransform();
        transform1.translate(SC.getX(), SC.getY());
        g2.transform(transform1);
        g2.setStroke(new BasicStroke(2));
        if(SC.getScore()<SC.getTempScore()){
            SC.setScore(SC.getScore()+1);
            g2.setColor(Color.BLUE);
        }else{
            g2.setColor(SC.getC());
        }
        
        g2.draw(outline1);
        //g2.setClip(outline1);
        //g2.dispose();
        
        g2.setTransform(oldXForm);
        
        
        int alp = 0;
        if(multaplayer > 1){
            if(SC.getScore()<SC.getTempScore()){
                g2.setColor(Color.BLUE);
                alp = 10;
            }else{
                multaplayer = 0;
                alp = 0;
            }  
        }
        float alpha = alp * 0.1f;
        AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(alcom);
        transform2 = g2.getTransform();
        transform2.translate(SC.getX()+170, SC.getY()+250);
        g2.transform(transform2);
        g2.draw(outline2);
        //g2.setClip(outline2);
        
        //reset
        g2.setTransform(oldXForm);
        float alpha2 = 10 * 0.1f;
        AlphaComposite alcom2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha2);
        g2.setComposite(alcom2);
    }// end score

    private void animationReturn(Graphics2D g2) {
        int tempsiaze;
        double difX = (snapMouse.x - PM.getDragZonesC()[tempPatternSelect].x) / 10, difY = (snapMouse.y - PM.getDragZonesC()[tempPatternSelect].y) / 10;
        /*
        Shape shape = U.makeRectangle(PM.getX(), yy + (PM.getCellW() * tempPatternSelect), dzgOffsetY + PM.getCellH(), yy + ((PM.getCellW() * tempPatternSelect) + PM.getCellH()));
         */
        // PM.getDragZones()[tempPatternSelect] = new Point(PM.getX(), yy + (PM.getCellH() * tempPatternSelect));
        g2.setPaint(Color.white);
        g2.fill(PM.getZoneShapes()[tempPatternSelect]);
        g2.setPaint(Color.black);
        g2.setStroke(new BasicStroke(3));
        g2.draw(PM.getZoneShapes()[tempPatternSelect]);

        for (Point p : peaces.get(tempPatternSelect)) {

            int nx = 0, ny = 0;
            if (p.x > 0) {
                nx = p.x + tempGap;
            } else if (p.x < 0) {
                nx = p.x - tempGap;
            }
            if (p.y > 0) {
                ny = p.y + tempGap;
            } else if (p.y < 0) {
                ny = p.y - tempGap;
            }
            Point pp = new Point(nx, ny);

            //System.out.println(snapMouse.x + " " + (difX * 10));
            //System.out.println((snapMouse.x-(int)animationReturnX));
            Shape pat = U.makeRectangle(
                    /*((dzgOffsetX + (snapMouse.x-(int)animationReturnX)) + (lGCW / 2)) + (pp.x - tempStartSize1 / 2),
                    ((yy + (snapMouse.y - (int)animationReturnY)) + ((lGCH * tempPatternSelect) + (lGCH / 2))) + (pp.y - tempStartSize1 / 2),
                    ((dzgOffsetX + (snapMouse.x-(int)animationReturnX)) + (lGCW / 2)) + (pp.x - tempStartSize1 / 2) + tempStartSize1,
                    ((yy + + (snapMouse.x-(int)animationReturnX) + ((lGCH * tempPatternSelect) + (lGCH / 2))) + (pp.y - tempStartSize1 / 2) + tempStartSize1*/
                    (snapMouse.x - (int) animationReturnX) + (pp.x - tempStartSize1 / 2),
                    (snapMouse.y - (int) animationReturnY) + (pp.y - tempStartSize1 / 2),
                    (snapMouse.x - (int) animationReturnX) + (pp.x - tempStartSize1 / 2) + tempStartSize1,
                    (snapMouse.y - (int) animationReturnY) + (pp.y - tempStartSize1 / 2) + tempStartSize1
            );
            // g2.setPaint(Color.red);
            g2.setPaint(new Color((int) an2R, (int) an2G, (int) an2B));
            g2.fill(pat);
            g2.setPaint(Color.black);
            g2.setStroke(new BasicStroke(1));
            g2.draw(pat);
        }

        if (animationReturnCount == 10) {
            tempStartSize1 = startSize1;
            animationReturnCount = 0;
            setAnimationReturn = false;
            tempGap = 0;
            an2R = 100.0;
            an2G = 100.0;
            an2B = 230.0;
            animationReturnX = 0;
            animationReturnY = 0;
        }
        /*
        if (tempStartSize1 == startSize2 - 5) {
            tempStartSize1 = startSize1;
            animationReturnCount = 0;
            setAnimationReturn = false;
            tempGap = 0;
            an2R = 100.0;
            an2G = 100.0;
            an2B = 230.0;
            animationReturnX = 0;
            animationReturnY = 0;
        }
         */

        if (an2R >= 10.0 && an2B <= 252.5) {
            an2R -= 10.0;
            an2G -= 10.0;
            an2B += 2.5;

        }
        animationReturnX += difX;
        animationReturnY += difY;
        tempGap -= 2;
        tempStartSize1--;
        animationReturnCount++;

    }//end animationReturn

    private void animPlace(Graphics2D g2) {
        boolean bull1 = false, bull2 = false, bull3 = false, bull4 = false, bull5 = false;
        //System.out.println(c.getX()+" "+c.getY()+" "+newx+" "+newy);

        for (Point p : tempPattern) {
            Shape pat = U.makeRectangle(
                    cubes.get(snapCubeUM).getX() + p.x,
                    cubes.get(snapCubeUM).getY() + p.y,
                    cubes.get(snapCubeUM).getX() + (p.x + 50),
                    cubes.get(snapCubeUM).getY() + (p.y + 50)
            );
            g2.setPaint(Color.white);
            // g2.setPaint(new Color(0, 0, 255));
            g2.fill(pat);
            g2.setPaint(Color.black);
            g2.setStroke(new BasicStroke(1));
            g2.draw(pat);
        }
 

         for (Point tempPattern1 : tempPattern) {
            Shape pat = U.makeRectangle(newx + (tempPattern1.x - (startSize1 / 2)), newy + (tempPattern1.y - (startSize1 / 2)), newx + (tempPattern1.x + (startSize1 - (startSize1 / 2))), newy + (tempPattern1.y + (startSize1 - (startSize1 / 2))));
            g2.setPaint(new Color(anR, anG, anB));
            // g2.setPaint(new Color(0, 0, 255));
            g2.fill(pat);
            g2.setPaint(Color.black);
            g2.setStroke(new BasicStroke(1));
            g2.draw(pat);
        }

        //System.out.println(cubes.get(snapCubeUM).getX() + " " + cubes.get(snapCubeUM).getY() + " " + newx + " " + newy);
        // System.out.println(c.getX()>newx);
        if (startSize1 < 50) {
            startSize1++;
        } else if (startSize1 == 50) {
            bull1 = true;
        }

        if (cubes.get(snapCubeUM).getX() > newx - (startSize1 / 2)) {
            newx++;
        } else if (cubes.get(snapCubeUM).getX() < newx - (startSize1 / 2)) {
            newx--;
        } else if (cubes.get(snapCubeUM).getX() == newx - (startSize1 / 2)) {
            bull2 = true;
        }

        if (cubes.get(snapCubeUM).getY() > newy - (startSize1 / 2)) {
            newy++;
        } else if (cubes.get(snapCubeUM).getY() < newy - (startSize1 / 2)) {
            newy--;
        } else if (cubes.get(snapCubeUM).getY() == newy - (startSize1 / 2)) {
            bull3 = true;
        }

        if (anR > 0) {
            anR -= 5;
            anG -= 5;
        } else if (anR == 0) {
            bull4 = true;
        }
        if (anB < 255) {
            anB++;
        } else if (anB == 255) {
            bull5 = true;
        }
        //System.out.println(bull1 + " " + bull2 + " " + bull3 + " " + bull4 + " " + bull5);
        if (bull1 && bull2 && bull3 && bull4 && bull5) {
            setAnimation = false;
            startSize1 = 40;
            anR = 100;
            anG = 100;
            anB = 230;
            checkForCompleetNull();
        }
    }//end animFin

    private void menu(Graphics2D g2) {

        Font font = new Font("Serif", Font.BOLD, 30);
        Shape s1 = U.makeRectangle(menux1, menuy1, menux1 + menuw, menuy1 + menuh);
        g2.setPaint(Color.white);
        g2.fill(s1);
        if (menu1hov) {
            g2.setPaint(Color.green);
        } else {
            g2.setPaint(Color.black);
        }

        g2.setStroke(new BasicStroke(2));
        g2.draw(s1);

        g2.setFont(font);
        g2.drawString(" Start ", menux1 + menuw / 2, menuy1 + menuh / 2);

        Shape s2 = U.makeRectangle(menux2, menuy2, menux2 + menuw, menuy2 + menuh);
        g2.setPaint(Color.white);
        g2.fill(s2);
        if (menu2hov) {
            g2.setPaint(Color.green);
        } else {
            g2.setPaint(Color.black);
        }
        g2.setStroke(new BasicStroke(2));
        g2.draw(s2);

        g2.setFont(font);
        g2.drawString(" Exit ", menux2 + menuw / 2, menuy2 + menuh / 2);

        /*
                    Font font = new Font("Serif", Font.PLAIN, 12);
                    g2.setFont(font);
                    g2.drawString("" + clock + "", c.getX() + c.getW() / 2, c.getY() + c.getH() / 2);
         */
    }//end menu

    private void puzzleMenu(Graphics2D g2) {

        ///// drag zones
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < 3; i++) {
            /*
            Shape shape = U.makeRectangle(dzgOffsetX, dzgOffsetY + (lGCW * i), dzgOffsetX + lGCH, dzgOffsetY + ((lGCW * i) + lGCH));
            dragZones[i] = new Point(dzgOffsetX, dzgOffsetY + (lGCH * i));
            
             */

            g2.setPaint(Color.white);
            g2.fill(PM.getZoneShapes()[i]);
            g2.setPaint(Color.black);
            g2.setStroke(new BasicStroke(3));
            g2.draw(PM.getZoneShapes()[i]);
        }
        ////

        //box border
        g2.setStroke(new BasicStroke(4));

        g2.setPaint(Color.black);
        Shape shape = U.makeRectangle(cubeObject.getX(), cubeObject.getY(), cubeObject.getX() + cubeObject.getW(), cubeObject.getY() + cubeObject.getH());
        g2.draw(shape);
        //pattern
        for (int i = 0; i < peaces.size(); i++) {

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
                                PM.getDragZonesC()[i].x + (p.x - displayCounter / 2),
                                PM.getDragZonesC()[i].y + (p.y - displayCounter / 2),
                                PM.getDragZonesC()[i].x + (p.x - displayCounter / 2) + displayCounter,
                                PM.getDragZonesC()[i].y + (p.y - displayCounter / 2) + displayCounter
                        );
                        if (patternsPosable[i]) {
                            g2.setPaint(new Color(0, 0, 200));
                        } else {
                            g2.setPaint(new Color(150, 0, 150));

                        }
                        g2.fill(pat);
                        g2.setPaint(Color.black);
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
                            g2.setPaint(Color.black);
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
                                    PM.getDragZonesC()[i].x + (p.x - startSize2 / 2),
                                    PM.getDragZonesC()[i].y + (p.y - startSize2 / 2),
                                    PM.getDragZonesC()[i].x + (p.x - startSize2 / 2) + startSize2,
                                    PM.getDragZonesC()[i].y + (p.y - startSize2 / 2) + startSize2
                            );
                            if (patternsPosable[i]) {
                                g2.setPaint(new Color(0, 0, 200));
                            } else {
                                g2.setPaint(new Color(150, 0, 150));

                            }
                            g2.fill(pat);
                            g2.setPaint(Color.black);
                            g2.setStroke(new BasicStroke(1));
                            g2.draw(pat);
                        }

                    }
                }
            }
        }

    }//end puzzleMenu

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

    }//end paintBackground

}//end Spiral


