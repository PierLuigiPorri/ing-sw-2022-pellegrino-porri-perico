package it.polimi.ingsw.GAME;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Selects and generates the 3 CharacterCards that are in play in each Expert Game, and stores them in a CharacterType ArrayList. This is also the class
 * that is called by the Game class when an effect has to be activated.
 */
public class CharacterSelector {

    private final ArrayList<CharacterType> selectedCharacters;
    public final Effects effects;

    /**
     * Constructor class. Creates the CharacterType ArrayList, selects the indexes of the Characters to be created, and creates the correspondent
     * Abstract or Concrete classes.
     * @param game Instance of the Game class that called the constructor.
     */
    public CharacterSelector(Game game){
        this.effects= new Effects(game);
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
                case 0:
                case 6:
                        this.selectedCharacters.add(new ConcreteCharacter(i, 1, effects));
                        break;
                case 1:
                case 7:
                        this.selectedCharacters.add(new AbstractCharacter(i, 2, effects));
                        break;
                case 2:
                case 5:
                case 8:
                case 11:
                        this.selectedCharacters.add(new AbstractCharacter(i, 3, effects));
                        break;
                case 3:
                case 9:
                        this.selectedCharacters.add(new AbstractCharacter(i, 1, effects));
                        break;
                case 4:
                case 10:
                        this.selectedCharacters.add(new ConcreteCharacter(i, 2, effects));
                        break;

                default:break;
            }
        }
    }

    /**
     * Applies the effect of the card with the given index. Called by the Game class. Passes the right parameters calling the apply method of the Character class.
     * @param index Index of the card to be activated. Int value between 0-11.
     * @param player Player instance associated with the player that activated the effect.
     * @param intpar Integer ArrayList of parameters.
     * @param strpar String ArrayList of parameters.
     * @param intpar2 Second Integer Arraylist of parameters, needed in applying one specific effect. Null every other time.
     * @return Returns a String with a brief description of what happened to the model, to be shown to the players.
     * @throws ImpossibleActionException throws the exception when applying an effect by calling a method of the Game class isn't possible.
     */
    public String applyEffect(int index, Player player, ArrayList<Integer> intpar, ArrayList<String> strpar, ArrayList<Integer> intpar2) throws ImpossibleActionException {
        for(CharacterType ch:selectedCharacters){
            if(ch.getIndex()==index){
                return ch.applyEffect(player, intpar, strpar, intpar2);
            }
        }
        return "";
    }

    public ArrayList<CharacterType> getCharacters(){
        return this.selectedCharacters;
    }

    /**
     * Called by the Game class when a Prohibition Token needs to be returned to the card.
     */
    public void restoreTD(){
        for(CharacterType c:this.selectedCharacters){
            if(c.getIndex()==4){
                effects.setTD((ConcreteCharacter) c);
            }
        }
    }

    /**
     * Returns the cost of the given Card.
     * @param index The index of the chosen card. Int value between 0-11.
     * @return Returns the cost of the card if it's among the ones in play, 0 otherwise.
     */
    public int getCost(int index){
        int cha = 0;
        for(CharacterType c:selectedCharacters){
            if(c.getIndex()==index) {
                cha=c.getCost();
                break;
            }
        }
        return cha;
    }
}