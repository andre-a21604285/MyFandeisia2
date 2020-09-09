package pt.ulusofona.lp2.fandeisiaGame;


public class InsufficientCoinsException extends Exception {
    private Equipa user;
    private Equipa computer;

    public InsufficientCoinsException(Equipa user, Equipa computer, String message){
        super(message);
        this.user=user;
        this.computer=computer;
    }

    public boolean teamRequiresMoreCoins(int teamID){
        if(getEquipa(teamID).getMoedas()<0){
            user.resetCoins();
            computer.resetCoins();
            return true;
        }
        return false;
    }

    public int getRequiredCoinsForTeam(int teamID){
        return Math.abs(getEquipa(teamID).getMoedas())+50;
    }

    private Equipa getEquipa(int teamId){
        if(teamId == 10){
            return user;
        }
        return computer;
    }

}
