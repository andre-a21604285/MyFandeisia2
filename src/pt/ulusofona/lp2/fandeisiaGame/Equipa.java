package pt.ulusofona.lp2.fandeisiaGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class Equipa implements java.io.Serializable {
    private int id, moedas, pontos, size;
    private static final long serialVersionUID = -628789568975888036L;


    Equipa(int id){
        this.moedas = 50;
        this.id = id;
        pontos=0;
        size=0;
    }

    public void addCreature(int id, String tipo, int x, int y, String orientacao){
        switch(tipo){
            case "Anão":
                setMoedas(Integer.parseInt(Anao.COST));
                break;
            case "Dragão":
                setMoedas(Integer.parseInt(Dragao.COST));
                break;
            case "Humano":
                setMoedas(Integer.parseInt(Humano.COST));
                break;
            case "Gigante":
                setMoedas(Integer.parseInt(Gigante.COST));
                break;
            case "Elfo":
                setMoedas(Integer.parseInt(Elfo.COST));
                break;
            case "Druida":
                setMoedas(Integer.parseInt(Druida.COST));
                break;
        }
        size++;
    }

    public int getMoedas(){
        return moedas;
    }

    public void setMoedas(int moedas){
            this.moedas-=moedas;
    }

    public int getId(){return id;}


    public int getPontos(){return pontos;}

    public void setPontos(int pontos, Creature creature){
        this.pontos += pontos;
        creature.incTreasures();
    }

    public int getSize(){return size;}

    //   Private Methods
    public int[] position(String orientation){
        int[] position = new int[2];
        if(orientation.equals("Norte")){
            position[0] = 0;
            position[1] = -1;
        }else if(orientation.equals("Sul")){
            position[0] = 0;
            position[1] = 1;
        }else if(orientation.equals("Este")){
            position[0] = 1;
            position[1] = 0;
        }else if(orientation.equals("Oeste")){
            position[0] = -1;
            position[1] = 0;
        }else if(orientation.equals("Nordeste")) {
            position[0] = 1;
            position[1] = -1;
        }else if(orientation.equals("Noroeste")){
            position[0] = -1;
            position[1] = -1;
        }else if(orientation.equals("Sudeste")){
            position[0] = 1;
            position[1] = 1;
        }else if(orientation.equals("Sudoeste")){
            position[0] = -1;
            position[1] = 1;
        }
        return position;
    }

    public boolean checkMovement(int x, int y, Creature creature, Mapa map){
        boolean isValid=false;
        if(creature.getTipo().equals("Elfo")){
               isValid = !map.checkCreature(x,y);
        }else if(creature.getTipo().equals("Gigante")){
            isValid = !map.checkGigante(x,y);
        }else if(creature.getTipo().equals("Anao")||creature.getTipo().equals("Humano")){
            isValid=!(map.checkCreature(x,y) || map.checkBuraco(x,y));
        }else{
            isValid=true;
        }
        return isValid;
    }

    private boolean checkMoedas(Creature creature){
        boolean isValid=false;
        if(this.moedas - creature.getCost()>=0){
            isValid=true;
        }
        return isValid;
    }

    public boolean isDruidaInPar(Creature creature){
        if(creature.getTipo().equals(Druida.TIPO)&&(creature.getX()+creature.getY())%2==0){
            return true;
        }
        return false;
    }
}
