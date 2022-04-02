package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class CharacterSelector {

    private final ArrayList<CharacterType> selectedCharacters;
    private final Game game;

    public CharacterSelector(Game game){
        this.game=game;
        ArrayList<Integer> selectedIDs = new ArrayList<>();
        this.selectedCharacters=new ArrayList<>();
        Random rand= new Random();
        selectedIDs.add(rand.nextInt(12));
        selectedIDs.add(rand.nextInt(12));
        while(Objects.equals(selectedIDs.get(1), selectedIDs.get(0))){
            selectedIDs.remove(1);
            selectedIDs.add(rand.nextInt(12));
        }
        selectedIDs.add(rand.nextInt(12));
        while(Objects.equals(selectedIDs.get(2), selectedIDs.get(1)) || Objects.equals(selectedIDs.get(2), selectedIDs.get(0))){
            selectedIDs.remove(2);
            selectedIDs.add(rand.nextInt(12));
        }
        for(Integer i: selectedIDs){
            switch(i){
                case 0:                                                                     //piazza stud da carta su isola
                case 6:                                                                     //scambia fino a 3 stud da carta a gate
                        this.selectedCharacters.add(new ConcreteCharacter(i, 1, game));
                        break;
                case 1:                                                                     //controlla prof con stud uguali
                case 7:                                                                     //influenza +2
                        this.selectedCharacters.add(new AbstractCharacter(i, 2));
                        break;
                case 2:                                                                     //calcola influenza su isola specifica
                case 5:                                                                     //torri 0 influenza
                case 8:                                                                     //colore 0 influenza
                case 11:                                                                    //-3 stud da hall di colore specifico
                        this.selectedCharacters.add(new AbstractCharacter(i, 3));
                        break;
                case 3:                                                                     //movimento MN+2
                case 9:                                                                     //scambia fino a 2 tra gate e hall
                        this.selectedCharacters.add(new AbstractCharacter(i, 1));
                        break;
                case 4:                                                                     //tessere divieto
                case 10:                                                                    //piazza stud da carta a hall
                        this.selectedCharacters.add(new ConcreteCharacter(i, 2, game));
                        break;

                default:break;
            }
        }
    }

    public void applyEffect(int index, Player player){
        //TODO:assert che il giocatore abbia il soldo -Doot
        this.selectedCharacters.get(index).applyEffect(this.game, player);
    }

    public ArrayList<CharacterType> getCharacters(){
        return this.selectedCharacters;
    }

    public void restoreTD(){
        for(CharacterType c:this.selectedCharacters){
            if(c.getIndex()==4){
                Effects.setTD((ConcreteCharacter) c);
            }
        }
    }
}