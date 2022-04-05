package it.polimi.ingsw.GAME;


import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;

import java.util.ArrayList;

public final class Effects{

    private static Game game;

    public static void initializeConcrete(int index, ConcreteCharacter c){
        c.students=new ArrayList<>();
        // RIGA (9) [c.students=new ArrayList<>();] è STATA SCRITTA DA PIER!!
        // è SOLO UNA ISTANZA PER FAR GIRARE IL TEST, NON SO SE SIA CORRETTO. I TEST PER ORA LI PASSA.
        switch(index){
            case 0:
            case 10:
                c.setMAX(4);
                    for(int i=0; i<c.getMAX(); i++) {
                        try {
                            c.students.add(game.getBg().extractStudent());
                        }catch (ImpossibleActionException e){
                            System.out.println(e.getMessage());
                        }
                    }
                break;
            case 6: c.setMAX(6);
                    for(int i=0; i<c.getMAX(); i++) {
                        try{
                            c.students.add(game.getBg().extractStudent());
                        }catch (ImpossibleActionException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
            default:break;
        }
    }


    public static void apply(int index, Player player) throws ImpossibleActionException {
        //TODO scrivere tutti gli effetti
        switch(index){
            case 1: //TODO: prima di attivare questo effetto, nel caso in cui il giocatore non abbia lo stesso numero di studenti
                    //di chi ha il professore, di conseguenza la carta non avrà effetto immediato, chiedere se davvero vuole attivarla
                player.getHall().activateCard();
                break;
            case 2:
                //TODO:chiedere l'isola al player
                int ind = 0;
                game.determineInfluence(ind);
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
                //TODO:chiedere al player quale colore vuole eliminare
                String c = "";
                game.colorTranslator(c).disableInfluence();
                break;
            case 9:
                //TODO:chiedere al player quali selezionare nel suo Gate, e quale/i colore/i selezionare nel suo Hall
                ArrayList<Integer> studens=new ArrayList<>();
                ArrayList<String> colors = new ArrayList<>();
                for(int g=0; g<studens.size(); g++){
                    game.removeFromHall(player, game.colorTranslator(colors.get(g)));
                    game.addStudentToHall(player.getGate().students.get(studens.get(g)).getColor(), player);
                    game.removeFromGate(player, studens.get(g));
                    game.addToGate(player, game.colorTranslator(colors.get(g)));
                }
                game.checkColorChanges(false);
                break;
            case 11:
                //TODO:chiedere al giocatore quale colore
                ColorTracker q = null;
                for(Player pl:game.getPlayers()){
                    for(int u=0; u<3&&game.getColor(pl, q)!=0; u++){
                        game.removeFromHall(pl, q);
                    }
                }
                game.checkColorChanges(false);
                break;
            default:break;
        }
    }

    public static void applyConcrete(int index, Player player, ConcreteCharacter c){
        switch(index){
            case 0:
                int i = 0; //TODO:l'indice e l'isola vanno chiesti al giocatore dopo aver attivato la carta
                int island = 0;
                game.addStudentToIsland(c.students.get(i).getColor(), island);
                c.students.remove(i);
                try {
                    c.students.add(game.getBg().extractStudent());
                }catch (ImpossibleActionException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                int isl = 0; //TODO:l'indice va chiesto al giocatore
                game.getB().islands.getIsland(isl).addTD();
                c.removeTD(); //TODO:aggiungere al calcolo dell'influenza il caso in cui esiste una TD, e rimetterla sulla carta
            case 10:
                int stud = 0; //TODO:chiedere al player quale prendere
                game.addStudentToHall(c.students.get(stud).getColor(), player);
                c.setMAX(4);
                try {
                    c.students.add(game.getBg().extractStudent());
                }catch (ImpossibleActionException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 6:
                ArrayList<Integer> card = new ArrayList<>(), gate= new ArrayList<>();
                //TODO: chiedere al player di selezionare MAX 3 stud dalla carta e dal suo gate, e mettere gli indici negli array
                Student tmp;
                for(int x=0; x<card.size(); x++){
                    tmp=c.students.get(card.get(x));
                    c.students.set(card.get(x), player.getGate().students.get(gate.get(x)));
                    player.getGate().students.set((gate.get(x)), tmp);
                }
                break;
            default:break;
        }
    }

    public static void restore(){
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

    public static void setTD(ConcreteCharacter c){
        c.addTD();
    }

    public static void setGame(Game g){
        game=g;
    }
}
