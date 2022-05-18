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


    public void apply(int index, Player player, int par1, String par2, ArrayList<Integer> par3, ArrayList<String> par4) throws ImpossibleActionException {
        //TODO scrivere tutti gli effetti
        switch(index){
            case 1: //TODO: prima di attivare questo effetto, nel caso in cui il giocatore non abbia lo stesso numero di studenti
                    //di chi ha il professore, di conseguenza la carta non avrà effetto immediato, chiedere se davvero vuole attivarla
                player.getHall().activateCard();
                break;
            case 2:
                game.determineInfluence(par1);
                break;
            case 3:
                game.setMNbonus();
                break;
            case 5:
                Tower.disable();
                break;
            case 7:
                game.enableInfluenceBonus(player);
                break;
            case 8:
                game.colorTranslator(par2).disableInfluence();
                break;
            case 9:
                for(int g=0; g<par3.size(); g++){
                    game.removeFromHall(player, par4.get(g));
                    game.addStudentToHall(player.getGate().students.get(par3.get(g)).getColor(), player);
                    game.removeFromGate(player, par3.get(g));
                    game.addToGate(player, par4.get(g));
                }
                game.checkColorChanges(false);
                break;
            case 11:
                for(Player pl:game.getPlayers()){
                    for(int u=0; u<3&&pl.getHall().getColor(par2)!=0; u++){
                        pl.getHall().desetColor(par2);
                    }
                }
                game.checkColorChanges(false);
                break;
            default:break;
        }
    }

    public void applyConcrete(int index, Player player, ConcreteCharacter c, int par1, int par2, ArrayList<Integer> par3, ArrayList<Integer> par4){
        switch(index){
            case 0:
                game.addStudentToIsland(c.students.get(par1).getColor(), par2);
                c.students.remove(par1);
                c.students.add(game.getBg().extractStudent());
                break;
            case 4:
                game.getB().islands.getIsland(par1).addTD();
                c.removeTD();
                break;
            case 10:
                game.addStudentToHall(c.students.get(par1).getColor(), player);
                c.getStudents().remove(par1);
                c.students.add(game.getBg().extractStudent());
                break;
            case 6:
                Student tmp;
                for(int x=0; x<par3.size(); x++){
                    tmp=c.students.get(par3.get(x));
                    c.students.set(par3.get(x), player.getGate().students.get(par4.get(x)));
                    player.getGate().students.set((par4.get(x)), tmp);
                }
                break;
            default:break;
        }
    }

    public void restore(){     //TODO:capire dove cazzo mettere questo metodo
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
