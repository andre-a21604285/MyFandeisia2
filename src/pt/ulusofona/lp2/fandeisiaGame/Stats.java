package pt.ulusofona.lp2.fandeisiaGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.Serializable;



public class Stats implements java.io.Serializable{
    private static final long serialVersionUID = -628789568975888036L;

    public List<String> as3MaisCarregadas(List<Creature> world){
        List<String> tmp = new ArrayList<String>();
        if(world.stream().anyMatch(c->c.nrTresures > 0)){
            world.stream().sorted(Comparator.comparingInt(Creature::getNrTreasure).reversed()).limit(3).
                    forEach(c->tmp.add(c.getId()+":"+c.getNrTreasure()));
        }else{
            world.stream().forEach(c->tmp.add(String.valueOf(c.getId())));
        }
        return tmp;
    }

    public List<String> as5MaisRicas(List<Creature> world){
        List<String> tmp = new ArrayList<String>();

        if(world.size() < 5){
            world.stream().forEach(c->tmp.add(c.getId()+":"+c.getNrPontos()+":"+c.getNrTreasure()));
        }else{
            world.stream().sorted(Comparator.comparingInt(Creature::getNrPontos)
                    .thenComparingInt(Creature::getNrTreasure).reversed()).limit(5)
                    .forEach(c->tmp.add(c.getId()+":"+c.getNrPontos()+":"+c.getNrTreasure()));
        }
        return tmp;
    }

    public List<String> osAlvosFavoritos(List<Creature> world){
        List<String> tmp = new ArrayList<String>();
        world.stream().sorted(Comparator.comparingInt(Creature::getNrSpells).reversed()).limit(3)
                .forEach(c->tmp.add(c.getId()+":"+c.getIdEquipa()+":"+c.getNrSpells()));
        return tmp;
    }

    public List<String> as3MaisViajadas(List<Creature>world){
        List<String> tmp = new ArrayList<String>();
        world.stream().sorted(Comparator.comparingInt(Creature::getKm).reversed()).limit(3)
                .forEach(c->tmp.add(c.getId()+":"+c.getKm()));
        return tmp;
    }

    public List<String> tiposDeCriaturaESeusTesouros(List<Creature>world){
        List<String> tmp = new ArrayList<String>();
        world.stream().collect(Collectors.groupingBy(Creature::getTipo,
                Collectors.summingInt(Creature::getNrPontos))).
                forEach(((s, integer) -> {tmp.add(s+":"+integer);}));
        return tmp;


    }

    public List<String> viradosPara(List<Creature> world){
        List<String> tmp = new ArrayList<String>();
        world.stream().collect(Collectors.groupingBy(Creature::getOrientation, Collectors.counting())).
                forEach(((s, aLong) -> tmp.add(s + ":" + aLong)));
        return tmp;
    }

    public List<String> asMaisEficientes(List<Creature> world){
        List<String> tmp = new ArrayList<String>();
        //world.stream().sorted(Comparator.comparingDouble(Creature::getRatio)).limit(3).
                //forEach(c-> tmp.add(c.getId()+":"+c.getNrTreasure()+":"+c.getKm()));
        return tmp;
    }



}
