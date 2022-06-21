package it.polimi.ingsw.GAME;


import it.polimi.ingsw.EXCEPTIONS.BagEmptyException;
import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public final class Effects {

    private final Game game;

    public Effects(Game game) {
        this.game = game;
    }

    public void initializeConcrete(int index, ConcreteCharacter c) {
        c.students = new ArrayList<>();
        switch (index) {
            case 0:
            case 10:
                c.setMAX(4);
                for (int i = 0; i < c.getMAX(); i++) {
                    try {
                        c.students.add(this.game.getBag().extractStudent());
                    } catch (BagEmptyException e) {
                        game.bagEmptyHandler();
                    }
                }
                break;
            case 6:
                c.setMAX(6);
                for (int i = 0; i < c.getMAX(); i++) {
                    try {
                        c.students.add(this.game.getBag().extractStudent());
                    } catch (BagEmptyException e) {
                        game.bagEmptyHandler();
                    }
                }
                break;
            default:
                break;
        }
    }


    public String apply(int index, Player player, ArrayList<Integer> intpar, ArrayList<String> strpar) throws ImpossibleActionException {
        switch (index) {
            case 1:
                player.getHall().activateCard();
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

    public String applyConcrete(int index, Player player, ConcreteCharacter c, ArrayList<Integer> intpar, ArrayList<Integer> intpar2) {
        switch (index) {
            case 0:
                game.addStudentToIsland(c.students.get(intpar.get(0)).getColor(), intpar.get(1));
                c.students.remove((int) intpar.get(0));
                try {
                    c.students.add(this.game.getBag().extractStudent());
                } catch (BagEmptyException e) {
                    game.bagEmptyHandler();
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
                    c.students.add(this.game.getBag().extractStudent());
                } catch (BagEmptyException e) {
                    game.bagEmptyHandler();
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

    public void setTD(ConcreteCharacter c) {
        c.addTD();
    }
}
