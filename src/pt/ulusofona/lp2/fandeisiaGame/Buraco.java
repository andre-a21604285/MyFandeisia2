package pt.ulusofona.lp2.fandeisiaGame;
import java.io.Serializable;

public class Buraco implements java.io.Serializable  {
    private static final long serialVersionUID = -628789568975888036L;

    int id;
    int x;
    int y;
    static final String TIPO = "buraco";

    Buraco(){}

    Buraco(int id, int x, int y){
        this.id = id;
        this.x=x;
        this.y=y;
    }

    public int getId(){
        return id;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}
