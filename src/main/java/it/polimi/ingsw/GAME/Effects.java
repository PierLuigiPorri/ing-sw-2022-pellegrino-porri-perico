package it.polimi.ingsw.GAME;


import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;
import java.util.Scanner;

public final class Effects{

    private final Game game;

    public Effects(Game game){
        this.game=game;
    }

    public void initializeConcrete(int index, ConcreteCharacter c){
        c.students=new ArrayList<>();
        // RIGA (9) [c.students=new ArrayList<>();] è STATA SCRITTA DA PIER!!
        // è SOLO UNA ISTANZA PER FAR GIRARE IL TEST, NON SO SE SIA CORRETTO. I TEST PER ORA LI PASSA.
        switch(index){
            case 0:
            case 10:
                c.setMAX(4);
                    for(int i=0; i<c.getMAX(); i++) {
                        c.students.add(this.game.getBg().extractStudent());
                    }
                break;
            case 6: c.setMAX(6);
                    for(int i=0; i<c.getMAX(); i++) {
                        c.students.add(game.getBg().extractStudent());
                    }
                    break;
            default:break;
        }
    }


    public String apply(int index, Player player, int par1, String par2, ArrayList<Integer> par3, ArrayList<String> par4) throws ImpossibleActionException {
        switch(index){
            case 1:
                player.getHall().activateCard();
                return "\nBe aware that until the end of the turn "+player+" will be able to control the Professors even if they have the same amount of students in the hall!";
            case 2:
                game.determineInfluence(par1);
                return "";
            case 3:
                game.setMNbonus();
                return "\n"+player+" has a bonus! This round, +2 to potential Mother Nature movement. Hooray!";
            case 5:
                Tower.disable();
                return "\nBy "+player+"'s decree, this turn Towers won't count when calculating the influence!";
            case 7:
                game.enableInfluenceBonus(player);
                return "\n"+player+" has a bonus! This turn, +2 to influence on Islands! Pray they won't use it against you.";
            case 8:
                game.colorTranslator(par2).disableInfluence();
                return "\nBy "+player+" decree, this turn "+par2+" students won't count when calculating the influence!";
            case 9:
                for(int g=0; g<par3.size(); g++){
                    game.removeFromHall(player, par4.get(g));
                    game.addStudentToHall(player.getGate().students.get(par3.get(g)).getColor(), player);
                    game.removeFromGate(player, par3.get(g));
                    game.addToGate(player, par4.get(g));
                }
                game.checkColorChanges(false);
                return "\nA shuffle occurred! "+player+" just swapped around "+par3.size()+" students between their Gate and their Hall.\n" +
                        "See for yourself, we can't tell you everything.";
            case 11:
                for(Player pl:game.getPlayers()){
                    for(int u=0; u<3&&pl.getHall().getColor(par2)!=0; u++){
                        pl.getHall().desetColor(par2);
                    }
                }
                game.checkColorChanges(false);
                return "\nTragedy struck! "+player+" just elected to get rid of up to 3 "+par2+" students from EVERY hall! CHAOS!";
            default:
                return "";
        }
    }

    public String applyConcrete(int index, Player player, ConcreteCharacter c, int par1, int par2, ArrayList<Integer> par3, ArrayList<Integer> par4){
        switch(index){
            case 0:
                game.addStudentToIsland(c.students.get(par1).getColor(), par2);
                c.students.remove(par1);
                c.students.add(game.getBg().extractStudent());
                return player+" just moved a "+c.students.get(par1).getColor()+" student on Island "+par2+"!";
            case 4:
                game.getB().islands.getIsland(par1).addTD();
                c.removeTD();
                return "\nUh-oh. "+player+" placed a Prohibition Token on Island "+par1+"!";
            case 10:
                game.addStudentToHall(c.students.get(par1).getColor(), player);
                String color=c.students.get(par1).getColor();
                c.getStudents().remove(par1);
                c.students.add(game.getBg().extractStudent());
                return "\nHeads up! "+player+" placed a "+color+" in their Hall! They're getting ahead!";
            case 6:
                Student tmp;
                for(int x=0; x<par3.size(); x++){
                    tmp=c.students.get(par3.get(x));
                    c.students.set(par3.get(x), player.getGate().students.get(par4.get(x)));
                    player.getGate().students.set((par4.get(x)), tmp);
                }
                return "\nA shuffle occurred! "+player+" just swapped around "+par3.size()+" students between their Gate and the Character card." +
                        "\nSee for yourself, we can't tell you everything.";
            default:
                return "";
        }
    }

    public void restore(){
        Tower.enable();
        game.disableMNbonus();
        game.disableInfluenceBonus();
        game.red.restoreInfluence();
        game.blue.restoreInfluence();
        game.yellow.restoreInfluence();
        game.green.restoreInfluence();
        game.pink.restoreInfluence();
        for(Player p:game.getPlayers()){
            p.getHall().disableCard();
        }
    }

    public void setTD(ConcreteCharacter c){
        c.addTD();
    }
}
