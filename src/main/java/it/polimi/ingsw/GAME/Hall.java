package it.polimi.ingsw.GAME;


import java.util.Objects;

/**
 * Hall represents the "Dining Room" of the game. It is usually different for each player.
 * It contains the number of students of each color owned by a player.
 * @author GC56
 */
public class Hall {
    private int red;
    private int blue;
    private int green;
    private int pink;
    private int yellow;
    //cardActivated is needed to see if the character who decrease the students number of a certain color is active in this turn.
    // when cardActivated turns to false value from true value, then is needed to restore the previous state of the Hall.
    public boolean cardActivated=false; //True if and only if there is a character active in this turn which lets the player control a Professor with the same amount of students.


    /**
     * Hall constructor. It sets the default value for every color. It is called once a game, when the game is created.
     * At the beginning, every player has 0 students of each color in his hall.
     * @author Pier Luigi Porri
     */
    public Hall() {
        this.red = 0;
        this.blue = 0;
        this.green = 0;
        this.pink = 0;
        this.yellow = 0;
    }

    /**
     * This method is called every time is needed to add a student to a player's hall.
     * For example, when a player moves a student from his Gate to his Hall, then this method is called to add that student to his Hall.
     * requires (color != null).
     * ensures ( \old(hall.getColor(color)) == hall.getColor(color)-1 )
     * @param color the String of the color to be added to the Hall.
     * @author GC56
     */
    public void setColor(String color){
        if (Objects.equals(color, "RED")) {
            setRed();
        }
        else if (Objects.equals(color, "BLUE")) {
            setBlue();
           }
        else if (Objects.equals(color, "GREEN")) {
            setGreen();
        }
        else if (Objects.equals(color, "YELLOW")) {
            setYellow();
        }
        else {
            setPink();
            }
    }

    /**
     * The method is called only by a specific Character's effect.
     * It removes a specific color from the Hall.
     * ensures ( \old(hall.getColor(color)) == hall.getColor(color)+1 ).
     * requires (color != null)
     * @param color the String of the color to be removed from the Hall.
     * @author GC56
     */
    public void desetColor(String color){
        if (Objects.equals(color, "RED")) {
            desetRed();
        }
        else if (Objects.equals(color, "BLUE")) {
            desetBlue();
        }
        else if (Objects.equals(color, "GREEN")) {
            desetGreen();
        }
        else if (Objects.equals(color, "YELLOW")) {
            desetYellow();
        }
        else {
            desetPink();
        }
    }

    /**
     * The method returns how many students of the parameter color given are in the Hall.
     * ensures (color != null).
     * @param color the String of the color you want to get the number in the Hall.
     * @return the number of students of that color in the Hall.
     * @author GC56
     */
    public int getColor(String color){
        switch (color) {
            case "RED":
                return getRed();
            case "BLUE":
                return getBlue();
            case "GREEN":
                return getGreen();
            case "YELLOW":
                return getYellow();
            default:
                return getPink();
        }
    }

    /**
     * @return number of red students in Hall.
     */
    private int getRed() {
        return red;
    }

    /**
     * the number of red students is increased by 1.
     */
    private void setRed() {
        this.red++;
    }

    /**
     * the number of red students is decreased by 1.
     */
    private void desetRed(){
        this.red--;
    }

    /**
     * @return number of blue students in Hall.
     */
    private int getBlue() {
        return blue;
    }

    /**
     * the number of blue students is increased by 1.
     */
    private void setBlue() {
        this.blue ++;
    }

    /**
     * the number of blue students is decreased by 1.
     */
    private void desetBlue(){
        this.blue--;
    }

    /**
     * @return number of green students in Hall.
     */
    private int getGreen() {
        return green;
    }

    /**
     * the number of green students is increased by 1.
     */
    private void setGreen() {
        this.green++;
    }

    /**
     * the number of green students is decreased by 1.
     */
    private void desetGreen(){
        this.green--;
    }


    /**
     * @return number of pink students in Hall.
     */
    private int getPink() {
        return pink;
    }

    /**
     * the number of pink students is increased by 1.
     */
    private void setPink() {
        this.pink++;
    }

    /**
     * the number of pink students is decreased by 1.
     */
    private void desetPink(){
        this.pink--;
    }


    /**
     * @return number of yellow students in Hall.
     */
    private int getYellow() {
        return yellow;
    }

    /**
     * the number of yellow students is increased by 1.
     */
    private void setYellow() {
        this.yellow++;
    }

    /**
     * the number of yellow students is decreased by 1.
     */
    private void desetYellow(){
        this.yellow--;
    }


    /**
     * Set the attribute activateCard to true;
     */
    public void activateCard(){
        this.cardActivated=true;
    }

    /**
     * Set the attribute activateCard to false;
     */
    public void disableCard(){
        this.cardActivated=false;
    }

    /**
     * @return the value of the attribute cardActivated.
     */
    public boolean getCardState(){
        return cardActivated;
    }
}
