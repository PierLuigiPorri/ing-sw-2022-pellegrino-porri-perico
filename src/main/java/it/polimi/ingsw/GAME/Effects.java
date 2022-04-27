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
                        try {
                            c.students.add(this.game.getBg().extractStudent());
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


    public void apply(int index, Player player) throws ImpossibleActionException {
        //TODO scrivere tutti gli effetti
        Scanner s=new Scanner(System.in);
        switch(index){
            case 1: //TODO: prima di attivare questo effetto, nel caso in cui il giocatore non abbia lo stesso numero di studenti
                    //di chi ha il professore, di conseguenza la carta non avrà effetto immediato, chiedere se davvero vuole attivarla
                player.getHall().activateCard();
                break;
            case 2:
                System.out.println("indice isola? 1-12");
                int ind = s.nextInt();
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
                System.out.println("colore da eliminare? In caps grazie :)");
                String c = s.nextLine();
                game.colorTranslator(c).disableInfluence();
                break;
            case 9:
                ArrayList<Integer> students=new ArrayList<>();
                ArrayList<String> colors = new ArrayList<>();
                int line=0;
                while(line!=-1){
                    System.out.println("indice gate? -1 per uscire");
                    line=s.nextInt();
                    if(line!=-1) {
                        students.add(line);
                        System.out.println("colore gate?");
                        colors.add(s.nextLine());
                    }
                }
                for(int g=0; g<students.size(); g++){
                    game.removeFromHall(player, colors.get(g));
                    game.addStudentToHall(player.getGate().students.get(students.get(g)).getColor(), player);
                    game.removeFromGate(player, students.get(g));
                    game.addToGate(player, colors.get(g));
                }
                game.checkColorChanges(false);
                break;
            case 11:
                System.out.println("colore?");
                String q = s.nextLine();
                for(Player pl:game.getPlayers()){
                    for(int u=0; u<3&&pl.getHall().getColor(q)!=0; u++){
                        pl.getHall().desetColor(q);
                    }
                }
                game.checkColorChanges(false);
                break;
            default:break;
        }
    }

    public void applyConcrete(int index, Player player, ConcreteCharacter c){
        Scanner s=new Scanner(System.in);
        switch(index){
            case 0:
                System.out.println("indice studente? 0-3");
                int i = s.nextInt();
                System.out.println("indice isola? 1-12");
                int island = s.nextInt();
                game.addStudentToIsland(c.students.get(i).getColor(), island);
                c.students.remove(i);
                try {
                    c.students.add(game.getBg().extractStudent());
                }catch (ImpossibleActionException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                System.out.println("indice isola? 1-12");
                int isl = s.nextInt();
                game.getB().islands.getIsland(isl).addTD();
                c.removeTD();
            case 10:
                System.out.println("indice studente? 0-3");
                int stud = s.nextInt();
                game.addStudentToHall(c.students.get(stud).getColor(), player);
                c.getStudents().remove(stud);
                try {
                    c.students.add(game.getBg().extractStudent());
                }catch (ImpossibleActionException e){
                    System.out.println(e.getMessage());
                }
                break;
            case 6:
                ArrayList<Integer> card = new ArrayList<>(), gate= new ArrayList<>();
                int line=0;
                while(line!=-1){
                    System.out.println("indice studente? 0-5, -1 per uscire");
                    line=s.nextInt();
                    if(line!=-1) {
                        card.add(line);
                        System.out.println("indice gate?");
                        gate.add(s.nextInt());
                    }
                }
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
