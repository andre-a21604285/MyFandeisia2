package pt.ulusofona.lp2.fandeisiaGame;

import java.io.Serializable;

public class Druida extends Creature implements java.io.Serializable {

    private static final long serialVersionUID = -628789568975888036L;

    public static final String DESCRICAO ="Anda duas casas nos turnos pares e uma casa nos turnos impares";//variavel static por ser final, o valor tipo não altera
    public static final String TIPO = "Druída";//variavel static por ser final, o valor tipo não altera
    public static final String IMAGE_PNG = "druida.png";//variavel static por ser final, o valor tipo não altera
    public static final String COST = "4";//variavel static por ser final, o valor tipo não altera
    private int movement;
    private static final int INICIALMOVEMENT = 1;//variavel static por ser final, o valor tipo não altera
    private int custo;


    Druida( int id, int idEquipa, int x, int y, String orientacao){
        super( id,  idEquipa,  TIPO,  IMAGE_PNG,  DESCRICAO, x, y,  orientacao);
        movement=INICIALMOVEMENT;
        this.custo=2;
    }

    public int getMovement() {
        return movement;
    }

    public void movimento(){

        if(orientacao.equals("Norte")){
            y-=1;
        }else if(orientacao.equals("Sul")){
            y+=1;
        }else if(orientacao.equals("Este")){
            x+=1;
        }else if(orientacao.equals("Oeste")){
            x-=1;
        }
    }

    public void setOrientation(){
        if(orientacao.equals(normalOrientation.get(normalOrientation.size()-1))){
            orientacao = "Norte";
        }else{
            orientacao = normalOrientation.get(normalOrientation.indexOf(orientacao)+1);
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
