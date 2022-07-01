package it.polimi.ingsw.GAME;


import it.polimi.ingsw.EXCEPTIONS.BagEmptyException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

/**
 * Effects final class that contains and manages the effects of the Character Cards. It initializes the cards that need to be initialized, applies the effects and
 * restores the status quo at the end of the round.
 * It's not abstract because it depends on the instance of game and needs to be passed to the CharacterSelector class.
 * @author GC56
 */
public final class Effects {

    private final Game game;

    /**
     * Constructor class. Sets the Game attribute.
     * @param game Game class instance of the current game. Needed to apply the effects.
     */
    public Effects(Game game) {
        this.game = game;
    }

    /**
     * Initializes the Concrete character cards. Basically, puts the students on the cards that need to be filled with students.
     * @param index index of the card to be initialized. Comes from the ConcreteCard class, which passes its own index.
     * @param c the card itself. Comes from the ConcreteCard class, which passes itself.
     */
    public void initializeConcrete(int index, ConcreteCharacter c) {
        c.students = new ArrayList<>();
        switch (index) {
            case 0:
            case 10:
                c.setMAX(4);
                for (int i = 0; i < c.getMAX(); i++) {
                    try {
                        c.students.add(this.game.extractStudent());
                    } catch (BagEmptyException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            case 6:
                c.setMAX(6);
                for (int i = 0; i < c.getMAX(); i++) {
                    try {
                        c.students.add(this.game.extractStudent());
                    } catch (BagEmptyException e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * Applies the effect of an AbstractCharacter. Called by the AbstractCharacter itself, when instructed to activate its effect by the CharacterSelector. The switch-case
     * statement applies a different effect based on the index of the card.
     * @param index the index of the card to be activated. Integer value between 1-11.
     * @param player the Player class instance associated with the Player that activated the card. Needed for some effects that target the Player.
     * @param intpar Arraylist of Integer values needed for the effects. Contains each and every Integer value needed, in order. If an effect needs only one int, it will
     *               access the first element of the Arraylist. If they need two, they will then access the second, and so on.
     * @param strpar Same as above, but for String parameters. Accessed in order of need like intpar.
     * @return returns a String with the description of the action that has been performed. Basically, an info message on what happened for the players.
     * @throws ImpossibleActionException thrown by the Game class if an action called by this method cannot be performed.
     */
    public String applyAbstract(int index, Player player, ArrayList<Integer> intpar, ArrayList<String> strpar) throws ImpossibleActionException {
        switch (index) {
            case 1:
                player.getHall().activateCard();
                game.checkColorChanges(true);
                return "\nBe aware that until the end of the turn " + player.nickname + " will be able to control the Professors even if they have the same amount of students in the hall!";
            case 2:
                game.determineInfluence(intpar.get(0));
                return "";
            case 3:
                game.setMNbonus();
                return "\n" + player.nickname + " has a bonus! This round, +2 to potential Mother Nature movement. Hooray!";
            case 5:
                Tower.disable();
                return "\nBy " + player.nickname + "'s decree, this turn Towers won't count when calculating the influence!";
            case 7:
                game.enableInfluenceBonus(player);
                return "\n" + player.nickname + " has a bonus! This turn, +2 to influence on Islands! Pray they won't use it against you.";
            case 8:
                game.colorTranslator(strpar.get(0)).disableInfluence();
                return "\nBy " + player.nickname + "'s decree, this turn " + strpar.get(0) + " students won't count when calculating the influence!";
            case 9:
                for (int g = 0; g < intpar.size(); g++) {
                    game.removeFromHall(player, strpar.get(g));
                    game.addStudentToHall(player.getGate().students.get(intpar.get(g)).getColor(), player);
                    game.removeFromGate(player, intpar.get(g));
                    game.addToGate(player, strpar.get(g));
                }
                game.checkColorChanges(false);
                return "\nA shuffle occurred! " + player.nickname + " just swapped around " + intpar.size() + " students between their Gate and their Hall.\n" +
                        "See for yourself, we can't tell you everything.";
            case 11:
                for (Player pl : game.getPlayers()) {
                    for (int u = 0; u < 3 && pl.getHall().getColor(strpar.get(0)) != 0; u++) {
                        pl.getHall().desetColor(strpar.get(0));
                    }
                }
                game.checkColorChanges(false);
                return "\nTragedy struck! " + player.nickname + " just elected to get rid of up to 3 " + strpar.get(0) + " students from EVERY hall! CHAOS!";
            default:
                return "";
        }
    }

    /**
     * Applies the effect of a ConcreteCharacter. Called by the ConcreteCharacter itself, when instructed to activate its effect by the CharacterSelector. The switch-case
     * statement applies a different effect based on the index of the card.
     * @param index the index of the card to be activated. Integer value between 0-10.
     * @param player the Player class instance associated with the Player that activated the card. Needed for some effects that target the Player.
     * @param c the ConcreteCharacter class instance that called the method. Needed for the effects that manipulate the students on the card itself.
     * @param intpar Arraylist of Integer values needed for the effects. Contains each and every Integer value needed, in order. If an effect needs only one int, it will
     *               access the first element of the Arraylist. If they need two, they will then access the second, and so on.
     * @param intpar2 a second ArrayList of Integer values needed for one specific effect that manipulates students between two indexed locations. Null when activating any
     *                other effect.
     * @return returns a String with the description of the action that has been performed. Basically, an info message on what happened for the players.
     */
    public String applyConcrete(int index, Player player, ConcreteCharacter c, ArrayList<Integer> intpar, ArrayList<Integer> intpar2) {
        switch (index) {
            case 0:
                game.addStudentToIsland(c.students.get(intpar.get(0)).getColor(), intpar.get(1));
                c.students.remove((int) intpar.get(0));
                try {
                    c.students.add(this.game.extractStudent());
                } catch (BagEmptyException e) {
                    System.out.println(e.getMessage());
                }
                return player.nickname + " just moved a " + c.students.get(intpar.get(0)).getColor() + " student on Island " + intpar.get(1) + "!";
            case 4:
                game.getBoard().islands.getIsland(intpar.get(0)).addTD();
                c.removeTD();
                return "\nUh-oh. " + player.nickname + " placed a Prohibition Token on Island " + intpar.get(0) + "!";
            case 10:
                game.addStudentToHall(c.students.get(intpar.get(0)).getColor(), player);
                String color = c.students.get(intpar.get(0)).getColor();
                c.getStudents().remove((int) intpar.get(0));
                try {
                    c.students.add(this.game.extractStudent());
                } catch (BagEmptyException e) {
                    System.out.println(e.getMessage());
                }
                return "\nHeads up! " + player.nickname + " placed a " + color + " in their Hall! They're getting ahead!";
            case 6:
                Student tmp;
                for (int x = 0; x < intpar.size(); x++) {
                    tmp = c.students.get(intpar.get(x));
                    c.students.set(intpar.get(x), player.getGate().students.get(intpar2.get(x)));
                    player.getGate().students.set((intpar2.get(x)), tmp);
                }
                return "\nA shuffle occurred! " + player.nickname + " just swapped around " + intpar.size() + " students between their Gate and the Character card." +
                        "\nSee for yourself, we can't tell you everything.";
            default:
                return "";
        }
    }

    /**
     * Restores the values that could have been modified by an effect activation at the end of the phase. Called by the Game class when changing phases.
     */
    public void restore() {
        Tower.enable();
        game.disableMNbonus();
        game.disableInfluenceBonus();
        game.red.restoreInfluence();
        game.blue.restoreInfluence();
        game.yellow.restoreInfluence();
        game.green.restoreInfluence();
        game.pink.restoreInfluence();
        for (Player p : game.getPlayers()) {
            p.getHall().disableCard();
        }
    }

    /**
     * Restores a Prohibition Token on the card that stores them when it's been used on the board. Used only if a card with index 4 is in play.
     * @param c the card with the Tokens.
     */
    public void setTD(ConcreteCharacter c) {
        c.addTD();
    }
}
