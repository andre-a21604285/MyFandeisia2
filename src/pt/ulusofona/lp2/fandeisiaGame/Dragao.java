package pt.ulusofona.lp2.fandeisiaGame;

import java.io.Serializable;

public class Dragao extends Creature implements java.io.Serializable {
    private static final long serialVersionUID = -628789568975888036L;

    public static final String DESCRICAO ="Voa por todas as casas a volta ";//variavel static por ser final, o valor tipo não altera
    public static final String TIPO = "Dragão";//variavel static por ser final, o valor tipo não altera
    public static final String IMAGE_PNG = "dragon.png";//variavel static por ser final, o valor tipo não altera
    public static final String COST = "9";//variavel static por ser final, o valor tipo não altera
    private int movement;
    private static final int INICIALMOVEMENT = 3;//variavel static por ser final, o valor tipo não altera
    int custo;

    Dragao( int id, int idEquipa,int x, int y, String orientacao){
        super( id,  idEquipa,  TIPO,  IMAGE_PNG,  DESCRICAO, x, y,  orientacao);
        movement=INICIALMOVEMENT;
        this.custo=9;
    }

    public int getMovement() {return movement;}
    public void movimento(){
        if(orientacao.equals("Norte")){
            y-=1;
        }else if(orientacao.equals("Sul")){
            y+=1;
        }else if(orientacao.equals("Este")){
            x+=1;
        }else if(orientacao.equals("Oeste")){
            x-=1;
        }else if(orientacao.equals("Nordeste")) {
            y -= 1;
            x += 1;
        }else if(orientacao.equals("Noroeste")){
            y-=1;
            x-=1;
        }else if(orientacao.equals("Sudeste")){
            y+=1;
            x+=1;
        }else if(orientacao.equals("Sudoeste")){
            y+=1;
            x-=1;
        }
    }

    public void setOrientation(){
        if(orientacao.equals(unnormalOrientation.get(unnormalOrientation.size()-1))){
            orientacao = "Norte";
        }else{
            orientacao = unnormalOrientation.get(unnormalOrientation.indexOf(orientacao)+1);
        }
    }
    public void setAlcance(int x){
        this.movement = x;

    }
    public void setAlcanceToNormal(){
        this.movement = INICIALMOVEMENT;
    }

    public int getCost(){
        return this.custo;
    }
}
