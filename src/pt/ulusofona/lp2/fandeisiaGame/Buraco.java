package pt.ulusofona.lp2.fandeisiaGame;

public class Buraco {
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
