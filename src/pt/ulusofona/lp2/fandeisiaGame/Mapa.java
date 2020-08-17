package pt.ulusofona.lp2.fandeisiaGame;
import java.io.Serializable;

//0=vazio  | b=buracos | h=humanos | a=anao | d=dragao | g=gigante | e=elfo | t=tesouros |

public class Mapa implements java.io.Serializable{
    private static final long serialVersionUID = -628789568975888036L;
    char [][] map;

    Mapa(){}

    Mapa(int linhas, int colunas){
        map = new char[linhas][colunas];
        fillMap(linhas,colunas);
    }
    public void addPosition(int x, int y, char symbol){
        map[x][y] = symbol;

    }

    public boolean isPositionFilled(int x, int y){
        return map[x][y]!='0';
    }

    public boolean checkCreature(int x, int y){ //verifica se há uma criatura
        return map[x][y] != '0' && map[x][y] !='b' && map[x][y] !='t';
    }

    public boolean checkBuraco(int x, int y){ //verirfica se há um buraco
        return map[x][y] == 'b';
    }

    public boolean checkGigante(int x, int y){
        return map[x][y] == 'g';
    } //verifica se há um gigante

    public boolean checkTresure(int x, int y){
        return map[x][y] == 't';
    } // verifica se há um tesouro

    private void fillMap(int linhas, int colunas){
        for(int i=0;i<linhas;i++){
            for(int j=0;j<colunas;j++){
                map[i][j] = '0';
            }
        }
    }
}
