package pt.ulusofona.lp2.fandeisiaGame;

public class Feitico {

    private static final String EN="EmpurraParaNorte";
    private static final String ES="EmpurraParaSul";
    private static final String EO="EmpurraParaOeste";
    private static final String EE="EmpurraParaEeste";
    private static final String RA="ReduzAlcance";
    private static final String DA="DuplicaAlcance";
    private static final String C="Congela";
    private static final String C4="Congela4Ever";
    private static final String D="Descongela";


    public static String nome;
    public static String cost;
    public static String efeito;


    Feitico(String nome){
        this.nome=nome;
        this.cost=cost;
        this.efeito=efeito;
    }

    public int getFeitico(Creature creature, String name){
        int cost = 0;
        if(name.toUpperCase().equals(EN.toUpperCase())){
            paraNorte(creature);
            cost = 1;
        }else if(name.toUpperCase().equals(ES.toUpperCase())){
             paraSul(creature);
             cost = 1;
        }else if(name.toUpperCase().equals(EE.toUpperCase())){
             paraEste(creature);
             cost = 1;
        }else if(name.toUpperCase().equals(EO.toUpperCase())){
            paraOeste(creature);
            cost = 1;
        }else if(name.toUpperCase().equals(RA.toUpperCase())){
             menosAlcance(creature);
             cost = 2;
        }else if(name.toUpperCase().equals(DA.toUpperCase())){
             maisAlcance(creature);
             cost = 3;
        }else if(name.toUpperCase().equals(C.toUpperCase())){
             gelo(creature);
             cost = 3;
        }else if(name.toUpperCase().equals(C4.toUpperCase())){
             sempreGelo(creature);
            cost = 10;
        }else if(name.toUpperCase().equals(D.toUpperCase())){
             semGelo(creature);
            cost = 8;
        }
    return cost;
    }

    public static String[] empurraParaNorte(){
         nome ="EmpurraParaNorte";
         cost = "1";
         efeito = "Move a criatura uma unidade para Norte.";
         String descricao[] = {nome,cost,efeito};
         return descricao;
    }

    public static String[] empurraParaSul(){
        nome ="EmpurraParaSul";
        cost = "1";
        efeito = "Move a criatura uma unidade para Sul.";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }

    public static String[] empurraParaEste(){
        nome ="EmpurraParaEste";
        cost = "1";
        efeito = "Move a criatura uma unidade para Este.";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }

    public static String[] empurraParaOeste(){
        nome ="EmpurraParaOeste";
        cost = "1";
        efeito = "Move a criatura uma unidade para Oeste.";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }

    public static String[] reduzAlcance(){
        nome ="ReduzAlcance";
        cost = "2";
        efeito = "Reduz o alcance da criatura para MIN (alcance original, 1)";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }

    public static String[] duplicaAlcance(){
        nome ="DuplicaAlcance";
        cost = "3";
        efeito = "Aumenta o alcance da criatura para o dobro.";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }

    public static String[] congela(){
        nome ="Congela";
        cost = "3";
        efeito = "A criatura alvo não se move neste turno.";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }

    public static String[] congela4Ever(){
        nome ="Congela4Ever";
        cost = "10";
        efeito = "A criatura alvo não se move mais até ao final do jogo.";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }

    public static String[] descongela(){
        nome ="Descongela";
        cost = "8";
        efeito = "Inverte a aplicação de um Feitiço Congela4Ever.\n" +
                "Ou seja, uma criatura que tenha sido congelada com o Congela4Ever pode voltar a mover-se após ser alvo de um Feitiço de Descongela.";
        String descricao[] = {nome,cost,efeito};
        return descricao;
    }



    private void paraNorte( Creature creature){
        creature.setY(creature.getY()-1);
    }
    private void paraSul(Creature creature){
        creature.setY(creature.getY()+1);
    }
    private void paraEste( Creature creature){
        creature.setY(creature.getX()+1);;
    }
    private void paraOeste( Creature creature){
        creature.setX(creature.getX()-1);
    }
    private void menosAlcance( Creature creature){//reduzir o movimento
       creature.setAlcance(Math.min(creature.getMovement(),1));
    }

    private void maisAlcance( Creature creature){//aumenta o movimento
        creature.setAlcance(2*creature.getMovement());
    }

    private void gelo( Creature creature){ // congelar
        creature.setAlcance(0);
    }

    private void sempreGelo( Creature creature){ // congelar o resto do jogo
        creature.setAlcance(0);
    }

    private void semGelo( Creature creature){ // descongelar
        creature.setAlcanceToNormal();
    }

}
