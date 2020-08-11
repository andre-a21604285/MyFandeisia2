package pt.ulusofona.lp2.fandeisiaGame;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipa {
    List<Creature> creatures;
    int id, moedas, pontos, size;


    Equipa(int id){
        this.moedas = 50;
        this.id = id;
        creatures = new ArrayList<Creature>();
        pontos=0;
        size=0;
    }

    public void addCreature(int id, String tipo, int x, int y, String orientacao){
        switch(tipo){
            case "Anao":
                creatures.add( new Anao(id,this.id,x,y,orientacao));
                setMoedas(Integer.parseInt(Anao.COST));
                break;
            case "Dragao":
                creatures.add( new Dragao(id,this.id,x,y,orientacao));
                setMoedas(Integer.parseInt(Dragao.COST));
                break;
            case "Humano":
                creatures.add( new Humano(id,this.id,x,y,orientacao));
                setMoedas(Integer.parseInt(Humano.COST));
                break;
            case "Gigante":
                creatures.add( new Gigante(id,this.id,x,y,orientacao));
                setMoedas(Integer.parseInt(Gigante.COST));
                break;
            case "Elfo":
                creatures.add( new Elfo(id,this.id,x,y,orientacao));
                setMoedas(Integer.parseInt(Elfo.COST));
                break;
            case "Druida":
                creatures.add( new Druida(id,this.id,x,y,orientacao));
                setMoedas(Integer.parseInt(Druida.COST));
                break;
        }
        size++;


    }

    public List<Tresure> movimento(int linhas, int colunas, Mapa map,List<Tresure> tresures){
        for(int i = 0 ; i<creatures.size(); i++){
            for(int j=0;j<creatures.get(i).getMovement();j++){
                int x = creatures.get(i).getX() + position(creatures.get(i).getOrientation())[0];
                int y = creatures.get(i).getY() + position(creatures.get(i).getOrientation())[1];
                if(x<colunas && x>=0 && y<linhas && y>=0 ){
                    if(j==creatures.get(i).getMovement()-1){ //TALVEZ APAGAR
                        if(!checkMovement(x,y,creatures.get(i),map) || map.checkBuraco(x,y) || map.checkCreature(x,y)){
                            creatures.get(i).setOrientation();
                            break;
                        }
                        if(isDruidaInPar(creatures.get(i))) {
                            tresures.add(new Tresure("bronze",creatures.get(i).getX(),creatures.get(i).getY()));
                        }
                        creatures.get(i).incKm();
                        creatures.get(i).movimento();
                        for(int z=0;z<tresures.size();z++){
                            if(tresures.get(z).getX()==x && tresures.get(z).getY()==y){
                                setPontos(tresures.get(z).getPoints(),creatures.get(i));
                                tresures.remove(tresures.get(z));
                            }
                        }

                    }else if(!checkMovement(x,y,creatures.get(i),map)){
                        break;
                    }else{
                        if(isDruidaInPar(creatures.get(i))) {
                            tresures.add(new Tresure("bronze",creatures.get(i).getX(),creatures.get(i).getY()));
                            map.addPosition(creatures.get(i).getX(), creatures.get(i).getY(), 'b');
                        }
                        creatures.get(i).incKm();
                        creatures.get(i).movimento();
                        for(int z=0;z<tresures.size();z++){
                            if(tresures.get(z).getX()==x && tresures.get(z).getY()==y){
                                setPontos(tresures.get(z).getPoints(),creatures.get(i));
                                tresures.remove(tresures.get(z));
                            }
                        }
                    }
                }else{
                    creatures.get(i).setOrientation();
                    break;
                }

            }
        }
        return tresures;
    }

    public int getMoedas(){
        return moedas;
    }

    public void setMoedas(int moedas){
            this.moedas-=moedas;
    }

    public int getId(){return id;}

    public List<Creature> getCreatures(){
        Collections.sort(creatures, new Sort());
        return creatures;}

    public int getPontos(){return pontos;}

    public void setPontos(int pontos, Creature creature){
        this.pontos += pontos;
        creature.incTreasures();
    }

    public int getSize(){return size;}

    //   Private Methods
    private int[] position(String orientation){
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

    private boolean checkMovement(int x, int y, Creature creature, Mapa map){
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

    private boolean isDruidaInPar(Creature creature){
        if(creature.getTipo().equals(Druida.TIPO)&&(creature.getX()+creature.getY())%2==0){
            return true;
        }
        return false;
    }
}
