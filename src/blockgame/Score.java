
package blockgame;

import java.awt.Color;
import java.awt.Font;


class Score {
    private int score = 0;
    private int tempScore = 0;
    private final int sooreUnit = 5;
    private int x, y, Fsize;
    private String FS;
    private Font F;
    private Color C;

    public Score(int x, int y, int Fsize, String FS, Color C) {
        this.x = x;
        this.y = y;
        this.Fsize = Fsize;
        this.F = new Font(FS, Font.BOLD, Fsize); 
        this.C = C;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFsize() {
        return Fsize;
    }

    public void setFsize(int Fsize) {
        this.Fsize = Fsize;
    }


    public Color getC() {
        return C;
    }

    public void setC(Color C) {
        this.C = C;
    }

    public String getFS() {
        return FS;
    }

    public void setFS(String FS) {
        this.FS = FS;
    }

    public Font getF() {
        return F;
    }

    public void setF(Font F) {
        this.F = F;
    }

    public int getSooreUnit() {
        return sooreUnit;
    }

    public int getTempScore() {
        return tempScore;
    }

    public void setTempScore(int tempScore) {
        this.tempScore = tempScore;
    }
    
    
    
    

      

    @Override
    public String toString() {
        return "Score{"+
                "score=" + score +
                ", x=" + x + 
                ", y=" + y + 
                ", Fsize=" + Fsize + 
                ", FS=" + FS + 
                ", F=" + F + 
                ", C=" + C + 
                '}';
    }
     
}
