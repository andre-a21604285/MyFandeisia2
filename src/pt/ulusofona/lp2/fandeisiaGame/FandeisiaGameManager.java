package pt.ulusofona.lp2.fandeisiaGame;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class FandeisiaGameManager implements java.io.Serializable {

    private static final long serialVersionUID = -628789568975888036L;
    static int turn;
    int linhas;
    int colunas;
    List<String> results = new ArrayList<String>();
    Stats stats;
    Equipa user;
    Equipa computer;
    Equipa corrente;
    Equipa vencedor;
    List<Creature> world= new ArrayList();
    List<Tresure> tresures = new ArrayList();
    List<Buraco> holes = new ArrayList();
    Map<String,Creature> feiticosTurno;
    List<Creature> congelados;
    Mapa map;

    public FandeisiaGameManager() {
        user = new Equipa(10);
        computer = new Equipa(20);
        corrente = user; // so para nao dar null
        feiticosTurno = new HashMap<>();
        congelados = new ArrayList();
        map = new Mapa(linhas,colunas);
        turn = 0;
        stats = new Stats();
    }

    public void addCreature(Equipa equipa, int id, String tipo, String orientacao, int x , int y){

            if(tipo.equals("Elfo")){
                map.addPosition(x,y,'e');
                world.add(new Elfo(id,equipa.getId(),x,y,orientacao));
            }else if(tipo.equals("Anão")){
                map.addPosition(x,y,'a');
                world.add(new Anao(id,equipa.getId(),x,y,orientacao));
            }else if(tipo.equals("Dragão")){
                map.addPosition(x,y,'d');
                world.add(new Dragao(id,equipa.getId(),x,y,orientacao));
            }else if(tipo.equals("Gigante")){
                map.addPosition(x,y,'g');
                world.add(new Gigante(id,equipa.getId(),x,y,orientacao));
            }else if(tipo.equals("Humano")){
                map.addPosition(x,y,'h');
                world.add(new Humano(id,equipa.getId(),x,y,orientacao));
            }else if(tipo.equals("Druida")){
                map.addPosition(x,y,'d');
                world.add(new Druida(id,equipa.getId(),x,y,orientacao));
            }
            equipa.addCreature(id,tipo,x,y,orientacao);//funcao para retirar moedas a equipa
        }

    public void addTresure(int id,String type, int x , int y){
        map.addPosition(x,y,'t');
        tresures.add(new Tresure(id,type,x,y));
    }

    public void addHole(int id, int x , int y){
        map.addPosition(x,y,'b');
        holes.add(new Buraco(id,x,y));
    }

    public void startGame(String[] content, int rows, int columns) throws InsufficientCoinsException{
        int count;
        linhas = rows;
        colunas = columns;
        map = new Mapa(columns,rows);
        boolean userValid=true;
        boolean computerValid=true;
        String message = "";
        for (count = 0; count < content.length; count++) {
            String dados[] = content[count].split(", ");
            dados[0] = dados[0].replace("id: ", "");
            dados[1] = dados[1].replace("type: ", "");
            int id = Integer.parseInt(dados[0]);
            String type = dados[1];
            if (dados[1].equals("gold") || dados[1].equals("silver") || dados[1].equals("bronze")) {
                dados[2] = dados[2].replace("x: ", "");
                dados[3] = dados[3].replace("y: ", "");
                int x = Integer.parseInt(dados[2]);
                int y = Integer.parseInt(dados[3]);
                if (checkAdd(x, y, rows, columns, map)) {
                    addTresure(id, type, x, y);
                }
            } else if (dados[1].equals("hole")) {
                dados[2] = dados[2].replace("x: ", "");
                dados[3] = dados[3].replace("y: ", "");
                int x = Integer.parseInt(dados[2]);
                int y = Integer.parseInt(dados[3]);
                if (checkAdd(x, y, rows, columns, map)) {
                    addHole(id, x, y);
                }
            } else {
                dados[2] = dados[2].replace("teamId: ", "");
                int teamId = Integer.parseInt(dados[2]);
                dados[3] = dados[3].replace("x: ", "");
                int x = Integer.parseInt(dados[3]);
                dados[4] = dados[4].replace("y: ", "");
                int y = Integer.parseInt(dados[4]);
                dados[5] = dados[5].replace("orientation: ", "");
                String orientation = dados[5];
                if (checkAdd(x, y, rows, columns, map)) {
                    addCreature(getEquipa(teamId), id, type, orientation, x, y);
                }
            }
        }
        if (user.getMoedas() < 0) {
            userValid = false;
        }
        if (computer.getMoedas() < 0) {
            computerValid = false;
        }
        if (!userValid && !computerValid) {
            message = "Ambas as equipas não respeitam o plafond";
            throw new InsufficientCoinsException(user, computer, message);
        } else if (!userValid && computerValid) {
            message = "O user não respeita o plafond ";
            throw new InsufficientCoinsException(user, computer, message);
        } else if (userValid && !computerValid) {
            message = "O computer não respeita o plafond";
            throw new InsufficientCoinsException(user, computer, message);
        }
    }


    public Equipa getEquipa(int teamId){
        if(teamId == 10){
            return user;
        }else{
            return computer;
        }
    }

    public Map<String, Integer> createComputerArmy(){
        Map<String, Integer> equipa = new HashMap<>();
        equipa.put("Humano",2);
        return equipa;
    }

    public void setInitialTeam(int teamId) {
        if(teamId == 10){
            corrente = user;
        }else{
            corrente = computer;
        }
    }

    public void processTurn() {
        movimento();
        turn++;
        tiraGelo();
        if(corrente.getId() == 10){
            corrente=computer;
        }else{
            corrente=user;
        }
    }

    public void toggleAI(boolean active){

    }

    public boolean saveGame(File fich){
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(fich));
            file.writeObject(this);
            file.flush();
            file.close();
        } catch (IOException e){
            return false;
        }
        return true;

    }

    public boolean loadGame(File fich){
        FandeisiaGameManager fandeisia = null;
        try {
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(fich));
            fandeisia = (FandeisiaGameManager) file.readObject();
            this.user = fandeisia.user;
            this.computer = fandeisia.computer;
            this.corrente = fandeisia.corrente;
            this.world = fandeisia.world;
            this.tresures = fandeisia.tresures;
            this.map = fandeisia.map;
            this.stats = fandeisia.stats;
            this.holes = fandeisia.holes;
            this.congelados = fandeisia.congelados;
            this.feiticosTurno = fandeisia.feiticosTurno;
            this.colunas = fandeisia.colunas;
            this.linhas = fandeisia.linhas;
            this.results = fandeisia.results;
            this.vencedor = fandeisia.vencedor;
            file.close();
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public List<Creature> getCreatures() {
        return world;
    }

    public String[][] getCreatureTypes() {
        String[][] creatureTypes = new String[6][4];
        creatureTypes[0][0] = Anao.TIPO;
        creatureTypes[0][1] = Anao.IMAGE_PNG;
        creatureTypes[0][2] = Anao.DESCRICAO;
        creatureTypes[0][3] = Anao.COST;

        creatureTypes[1][0] = Dragao.TIPO;
        creatureTypes[1][1] = Dragao.IMAGE_PNG;
        creatureTypes[1][2] = Dragao.DESCRICAO;
        creatureTypes[1][3] = Dragao.COST;

        creatureTypes[2][0] = Humano.TIPO;
        creatureTypes[2][1] = Humano.IMAGE_PNG;
        creatureTypes[2][2] = Humano.DESCRICAO;
        creatureTypes[2][3] = Humano.COST;

        creatureTypes[3][0] = Elfo.TIPO;
        creatureTypes[3][1] = Elfo.IMAGE_PNG;
        creatureTypes[3][2] = Elfo.DESCRICAO;
        creatureTypes[3][3] = Elfo.COST;

        creatureTypes[4][0] = Gigante.TIPO;
        creatureTypes[4][1] = Gigante.IMAGE_PNG;
        creatureTypes[4][2] = Gigante.DESCRICAO;
        creatureTypes[4][3] = Gigante.COST;

        creatureTypes[5][0] = Druida.TIPO;
        creatureTypes[5][1] = Druida.IMAGE_PNG;
        creatureTypes[5][2] = Druida.DESCRICAO;
        creatureTypes[5][3] = Druida.COST;

        return creatureTypes;
    }

    public String[][] getSpellTypes() {
        String[][] spellTypes = new String[9][3];
        spellTypes[0][0] = Feitico.empurraParaNorte()[0];
        spellTypes[0][1] = Feitico.empurraParaNorte()[1];
        spellTypes[0][2] = Feitico.empurraParaNorte()[2];

        spellTypes[1][0] = Feitico.empurraParaSul()[0];
        spellTypes[1][1] = Feitico.empurraParaSul()[1];
        spellTypes[1][2] = Feitico.empurraParaSul()[2];

        spellTypes[2][0] = Feitico.empurraParaEste()[0];
        spellTypes[2][1] = Feitico.empurraParaEste()[1];
        spellTypes[2][2] = Feitico.empurraParaEste()[2];

        spellTypes[3][0] = Feitico.empurraParaOeste()[0];
        spellTypes[3][1] = Feitico.empurraParaOeste()[1];
        spellTypes[3][2] = Feitico.empurraParaOeste()[2];

        spellTypes[4][0] = Feitico.reduzAlcance()[0];
        spellTypes[4][1] = Feitico.reduzAlcance()[1];
        spellTypes[4][2] = Feitico.reduzAlcance()[2];

        spellTypes[5][0] = Feitico.duplicaAlcance()[0];
        spellTypes[5][1] = Feitico.duplicaAlcance()[1];
        spellTypes[5][2] = Feitico.duplicaAlcance()[2];

        spellTypes[6][0] = Feitico.congela()[0];
        spellTypes[6][1] = Feitico.congela()[1];
        spellTypes[6][2] = Feitico.congela()[2];

        spellTypes[7][0] = Feitico.congela4Ever()[0];
        spellTypes[7][1] = Feitico.congela4Ever()[1];
        spellTypes[7][2] = Feitico.congela4Ever()[2];

        spellTypes[8][0] = Feitico.descongela()[0];
        spellTypes[8][1] = Feitico.descongela()[1];
        spellTypes[8][2] = Feitico.descongela()[2];
        return spellTypes;
    }

    public boolean gameIsOver() {
        if (turn == 15 || tresures.isEmpty() || pointsWorld() == true ) {
            return true;
        }
            return false;
    }

    public boolean enchant(int x, int y, String spellName){ //vamos fazer um arraybidimensional

        Feitico feitico = new Feitico(spellName);
        Creature creature = getCreatureByPosition(x, y);
        if(creature==null){
            return false;
        }else if(canMove(spellName,x,y)){
            return false;
        }
        int equipaId = creature.getIdEquipa();
        int cost = feitico.getFeitico(creature,spellName);
        feiticosTurno.put(spellName,creature);
        getEquipa(equipaId).setMoedas(cost);
        if(spellName.equals("Congela")){
            congelados.add(creature);
        }
        return true;
    }

    public String getSpell(int x, int y) { //verificar no mapa de feiticos qual a criatura afetada
        String spellName="";
        for (Map.Entry<String, Creature> entry : feiticosTurno.entrySet()) {
            Creature v = entry.getValue();
            if(v.getX() == x && v.getY()==y ){
                spellName = entry.getKey();
                break;
            }
        }
        return spellName;
    }

    public int getElementId(int x, int y) {
        int elementId = 0;
        if(getCreatureByPosition(x,y)!=null){
            elementId=getCreatureByPosition(x,y).getId();
        }else if(getTresure(x,y)!=null){
            elementId=getTresure(x,y).getId();
        }else if(getBuraco(x,y)!=null){
            elementId=getBuraco(x,y).getId();
        }
        return elementId;
    }

    public Tresure getTresure(int x , int y){
        Tresure tresure=null;
        for(int i=0; i<tresures.size();i++){
            if(tresures.get(i).getX()==x && tresures.get(i).getY()==y){
                tresure=tresures.get(i);
                break;
            }
        }
        return tresure;
    }

    public Buraco getBuraco(int x , int y){
        Buraco hole=null;
        for(int i=0; i<holes.size();i++){
            if(holes.get(i).getX()==x && holes.get(i).getY()==y){
                hole=holes.get(i);
                break;
            }
        }
        return hole;
    }

    public int getCurrentTeamId() {
        return corrente.getId();
    }

    public int getCurrentScore(int teamID) {

        if (teamID == 10) {
            return user.getPontos();
        } else {
            return computer.getPontos();
        }
    }

    public int getCoinTotal(int teamID){
        if (teamID == 10) {
            return user.getMoedas();
        } else {
            return computer.getMoedas();
        }
    }

    public List<String> getAuthors() {
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("    André Rego - 21604285  ");
        return authors;
    }

    public String whoIsLordEder(){
        String lord="Éderzito António Macedo Lopes";
        return lord;
    }

    public List<String> getResults() {return results;}

    public void setResults() {
        String resultado;
        String creatures;
        if(vencedor==null){
            resultado="\n Welcome to FANDEISIA \n Resultado: EMPATE \n LRD: " + user.getPontos() + "\n RESISTENCIA: "
                    + computer.getPontos() + "\n Nr. de Turnos jogados: " + turn +"\n----------\n";
        }else{
            String vencedor = "RESISTENCIA";
            String derrotado = "LRD";
            Equipa derr = user;
            if(this.vencedor.getId()==10){
                vencedor = "LRD";
                derrotado = "RESISTENCIA";
                derr = computer;
            }
            resultado="\n Welcome to FANDEISIA \n Resultado: Vitoria da Equipa \n " + vencedor + "\n"
                    + vencedor + " : " + this.vencedor.getPontos() + "\n" + derrotado + " : " + derr.getPontos() +
                    "\n Nr. de Turnos jogados: " + turn +"\n----------\n";
        }
        results.add(resultado);
        for(Creature creature:world){
            creatures= creature.getId() + " : " + creature.getTipo() +" : "+creature.getOuro()+" : "+creature.getPrata()+" : "+creature.getBronze()+" : "
                    +creature.getNrPontos();
            results.add(creatures);
        }
    }

    public Map<String, List<String>> getStatistics(){
        Map<String, List<String>> tmp = new HashMap<String,List<String>>();
        tmp.put("as3MaisCarregadas",stats.as3MaisCarregadas(world));
        tmp.put("as5MaisRicas",stats.as5MaisRicas(world));
        tmp.put("osAlvosFavoritos",stats.osAlvosFavoritos(world));
        tmp.put("as3MaisViajadas",stats.as3MaisViajadas(world));
        tmp.put("tiposDeCriaturaESeusTesouros",stats.tiposDeCriaturaESeusTesouros(world));
        tmp.put("viradosPara",stats.viradosPara(world));
        tmp.put("asMaisEficientes",stats.asMaisEficientes(world));

        return tmp;
    }

    // - Metodos privados - //
    public void movimento(){
        List<Creature> creatures = getCreaturesFromCurrentTeam();
        for(int i = 0 ; i<creatures.size(); i++){
            for(int j=0;j<creatures.get(i).getMovement();j++){
                int x = creatures.get(i).getX() + corrente.position(creatures.get(i).getOrientation())[0];
                int y = creatures.get(i).getY() + corrente.position(creatures.get(i).getOrientation())[1];
                if(x<colunas && x>=0 && y<linhas && y>=0 ){
                    if(j==creatures.get(i).getMovement()-1){ //TALVEZ APAGAR
                        if(!corrente.checkMovement(x,y,creatures.get(i),map) || map.checkBuraco(x,y) || map.checkCreature(x,y)){
                            creatures.get(i).setOrientation();
                            break;
                        }
                        if(corrente.isDruidaInPar(creatures.get(i))) {
                            tresures.add(new Tresure("bronze",creatures.get(i).getX(),creatures.get(i).getY()));
                        }
                        creatures.get(i).incKm();
                        creatures.get(i).movimento();
                        for(int z=0;z<tresures.size();z++){
                            if(tresures.get(z).getX()==x && tresures.get(z).getY()==y){
                                corrente.setPontos(tresures.get(z).getPoints(),creatures.get(i));
                                tresures.remove(tresures.get(z));
                            }
                        }

                    }else if(!corrente.checkMovement(x,y,creatures.get(i),map)){
                        break;
                    }else{
                        if(corrente.isDruidaInPar(creatures.get(i))) {
                            tresures.add(new Tresure("bronze",creatures.get(i).getX(),creatures.get(i).getY()));
                            map.addPosition(creatures.get(i).getX(), creatures.get(i).getY(), 'b');
                        }
                        creatures.get(i).incKm();
                        getCreatureByPosition(creatures.get(i).getX(),creatures.get(i).getY()).movimento();//update world
                        for(int z=0;z<tresures.size();z++){
                            if(tresures.get(z).getX()==x && tresures.get(z).getY()==y){
                                corrente.setPontos(tresures.get(z).getPoints(),creatures.get(i));
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
    }

    private boolean pointsWorld(){
        int sum=0;
        for(Tresure tresure : tresures){
            sum+=tresure.getPoints();//soma os valores de pontos totais no board na variavel sum
        }
        if(user.getPontos() >sum/2 || computer.getPontos() > sum/2){
            return true;
        }
        return false;
    }

    private Creature getCreatureByPosition(int x, int y){
        Creature creature=null;
        for(int i=0; i<world.size();i++){
            if(world.get(i).getX()==x && world.get(i).getY()==y){
                creature=world.get(i);
                break;
            }
        }
        return creature;
    }

    private boolean hasBuraco(int x, int y){
        boolean found=false;
        for(int i=0; i<holes.size();i++){
            if(holes.get(i).getX()==x && holes.get(i).getY()==y){
                found=true;
                break;
            }
        }
        return found;
    }

    private boolean hasCreature(int x, int y){
        boolean found=false;
        for(int i=0; i<world.size();i++){
            if(world.get(i).getX()==x && world.get(i).getY()==y){
                found=true;
                break;
            }
        }
        return found;
    }

    private boolean canMove(String name,int x, int y){
        boolean found = false;
        switch (name){
            case Feitico.EN:
                found = hasBuraco(x,y+1) || hasCreature(x,y+1);
                break;
            case Feitico.ES:
                found =hasBuraco(x,y-1) || hasCreature(x,y-1);
                break;
            case Feitico.EE:
                found = hasBuraco(x+1,y) || hasCreature(x+1,y);
                break;
            case Feitico.EO:
                found = hasBuraco(x-1,y) || hasCreature(x-1,y);
                break;
        }
        return found;
    }

    private void tiraGelo(){
        for(int i=0; i<congelados.size();i++){
            congelados.get(i).setAlcanceToNormal();
        }
        congelados.clear();
    }

    private boolean checkAdd(int x, int y, int rows, int columns, Mapa map){
        if(x>=0 && x<columns && y>=0 && y<rows && !map.isPositionFilled(x,y)){
            return true;
        }
        return false;
    }

    private Creature getCreatureByID(int id){
        Creature creature = null;
        for(int i=0; i<world.size();i++){
            if(world.get(i).id == id){
                creature = world.get(i);
                break;
            }
        }
        return creature;
    }

    private List<Creature> getCreaturesFromCurrentTeam(){
        int equipaId = corrente.getId();
        List<Creature> creatures = new LinkedList<>();
        for(Creature creature: world){
            if(creature.getIdEquipa() == equipaId){
                creatures.add(creature);
            }
        }
        return  creatures;
    }


}

