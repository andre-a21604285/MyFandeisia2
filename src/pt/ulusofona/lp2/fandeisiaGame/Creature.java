package pt.ulusofona.lp2.fandeisiaGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Creature implements java.io.Serializable {

    private static final long serialVersionUID = -628789568975888036L;

    int id;
    int idEquipa;
    String imagePNG;
    String tipo;
    String descricao;
    int nrPontos;
    int nrSpells;
    int nrTresures;
    int km;
    String orientacao;
    int x;
    int y;
    int ouro;
    int prata;
    int bronze;
    protected List<String> normalOrientation = Arrays.asList ("Norte","Este","Sul","Oeste");
    protected List<String> unnormalOrientation = Arrays.asList("Norte","Nordeste","Este","Sudeste","Sul","Sudoeste","Oeste","Noroeste");

    Creature(){}

    Creature( int id, int idEquipa, String tipo, String imagePNG, String descricao,int x, int y, String orientacao){
        this.nrTresures=0;
        this.nrPontos = 0;
        this.ouro = 0;
        this.prata = 0;
        this.bronze = 0;
        this.tipo=tipo;
        this.imagePNG=imagePNG;
        this.descricao = descricao;
        this.id = id;
        this.idEquipa = idEquipa;
        this.orientacao = orientacao;
        this.x=x;
        this.y=y;
        this.nrSpells = 0;
        this.km=0;
    }

    public int getId(){return id;}

    public int getIdEquipa(){return idEquipa;}

    public int getNrPontos(){return nrPontos;}

    public int getX(){return x;}
    public void setX(int x){
        this.x = x;
    }

    public int getY(){return y; }
    public void setY(int y){
        this.y = y;
    }

    public int getOuro(){return ouro; }
    public int getPrata(){return prata; }
    public int getBronze(){return bronze; }

    public String getOrientation(){return orientacao;}
    public abstract void setOrientation();

    public String getdescricao(){return descricao;}

    public String getTipo(){return tipo;}

    public String getImagePNG(){return imagePNG;}

    public int getNrTreasure(){
        return nrTresures;
    }
    public void incTreasures(){
        nrTresures++;
    }

    public int getNrSpells(){ return nrSpells;}
    public void incNrSpells(){ nrSpells++;}

    public int getKm(){
        return km;
    }

    public void incKm(){
        km++;
    }

    public double getRatio(){
        return getNrTreasure() / getKm();
    }
    public String toString(){
        String toString = id + " | " + tipo + " | " + idEquipa + " | " + nrPontos + " @ (" + x + ", " + y+") "+ orientacao;
        return toString;
    }

    public abstract int getCost();
    public abstract int getMovement();
    public abstract void movimento();
    public abstract void setAlcance(int x);
    public abstract void setAlcanceToNormal();
}
