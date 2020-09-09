package pt.ulusofona.lp2.fandeisiaGame;

import java.io.Serializable;

public class Tresure implements java.io.Serializable {
    private static final long serialVersionUID = -628789568975888036L;

    private int id;
    private int x;
    private int y;
    private String type;
    private static final String TIPO = "tresure";//variavel static por ser final, o valor tipo não altera
    private int pontos;

    Tresure(){}

    Tresure(String type, int x, int y){
        this.x=x;
        this.y=y;
        setPoints(type);
    }

    Tresure(int id,String type, int x, int y){

        this.id = id;
        this.x=x;
        this.y=y;
        setPoints(type);
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
    public int getPoints(){return pontos;}

    public void setPoints(String type){
        this.type = type;
        if(type.equals("gold")){
            pontos=3;
        }else if(type.equals("silver")){
            pontos=2;
        }else if(type.equals("bronze")){
            pontos=1;
        }

    }
    public String getType(){
        return type;
    }

}
