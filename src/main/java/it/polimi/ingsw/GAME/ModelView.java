package it.polimi.ingsw.GAME;

import it.polimi.ingsw.MESSAGES.UpdateMessage;

import java.util.Observable;
import java.util.Observer;

public class ModelView extends Observable implements Observer {
    private final UpdateMessage update;
    private final Game game;

    public ModelView(Game game) {
        this.update = new UpdateMessage();
        this.game = game;
    }

    @Override
    public void update(Observable o, Object arg) {
        update.charactersNum = 0;
        update.update=(String)arg;
        setGameAttributes();
        setBoardAttributes();
        setPlayersAttributes();
        setExpertGameAttributes();
        set3PlayersAttributes();
        setChanged();
        notifyObservers(update);
    }


    private void setGameAttributes(){
        for (Player p : game.order) {
            update.order.add(p.nickname);
        }
        update.phase=game.roundMaster.round.getCurrentPhase();
        update.turnNumber=game.roundMaster.getRoundCount();
        update.game_Type = game.getGameType();
        update.nPlayers = game.getPlayerCount();
        update.numIslands = game.getB().islands.size();
    }




    private void setBoardAttributes() {
        for (Card c : game.getCardsPlayed()) {
            update.lastCardPlayed.add(c.getMovement());
            update.lastCardPlayed.add(c.getValue());
        }
        for (Student s : game.getB().islands.getIsland(1).getStudents()) {
            update.studentsOnIsland1.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(2).getStudents()) {
            update.studentsOnIsland2.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(3).getStudents()) {
            update.studentsOnIsland3.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(4).getStudents()) {
            update.studentsOnIsland4.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(5).getStudents()) {
            update.studentsOnIsland5.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(6).getStudents()) {
            update.studentsOnIsland6.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(7).getStudents()) {
            update.studentsOnIsland7.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(8).getStudents()) {
            update.studentsOnIsland8.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(9).getStudents()) {
            update.studentsOnIsland9.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(10).getStudents()) {
            update.studentsOnIsland10.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(11).getStudents()) {
            update.studentsOnIsland11.add(s.getColor());
        }
        for (Student s : game.getB().islands.getIsland(12).getStudents()) {
            update.studentsOnIsland12.add(s.getColor());
        }
        for (int k = 1; k <= game.getB().islands.size(); k++) {
            if (game.getB().islands.getIsland(k).motherNature)
                update.motherNatureOnIsland.add(true);
            else update.motherNatureOnIsland.add(false);
        }
        for (int k = 1; k <= game.getB().islands.size(); k++) {
            update.towersOnIsland.add(game.getB().islands.getIsland(k).towers.size());
        }
        for (Student s : game.getB().clouds.get(0).students) {
            update.studentsOnCloud0.add(s.getColor());
        }
        for (Student s : game.getB().clouds.get(1).students) {
            update.studentsOnCloud1.add(s.getColor());
        }
        for (Student s : game.getPlayers().get(0).getGate().getStudents()) {
            update.gatePlayer0.add(s.getColor());
        }
        for (Student s : game.getPlayers().get(1).getGate().getStudents()) {
            update.gatePlayer1.add(s.getColor());
        }
    }






    private void setPlayersAttributes() {
        for (Player p : game.order) {
            update.towersOnPlayer.add(p.getTower_count());
        }
        update.hallPlayer0.add(game.getPlayers().get(0).getHall().getColor("RED"));
        update.hallPlayer0.add(game.getPlayers().get(0).getHall().getColor("BLUE"));
        update.hallPlayer0.add(game.getPlayers().get(0).getHall().getColor("GREEN"));
        update.hallPlayer0.add(game.getPlayers().get(0).getHall().getColor("YELLOW"));
        update.hallPlayer0.add(game.getPlayers().get(0).getHall().getColor("PINK"));

        update.hallPlayer1.add(game.getPlayers().get(1).getHall().getColor("RED"));
        update.hallPlayer1.add(game.getPlayers().get(1).getHall().getColor("BLUE"));
        update.hallPlayer1.add(game.getPlayers().get(1).getHall().getColor("GREEN"));
        update.hallPlayer1.add(game.getPlayers().get(1).getHall().getColor("YELLOW"));
        update.hallPlayer1.add(game.getPlayers().get(1).getHall().getColor("PINK"));

        if (game.getPlayers().get(0).nickname.equals(game.red.getPlayer().nickname))
            update.professors0.add(true);
        else update.professors0.add(false);

        if (game.getPlayers().get(0).nickname.equals(game.blue.getPlayer().nickname))
            update.professors0.add(true);
        else update.professors0.add(false);

        if (game.getPlayers().get(0).nickname.equals(game.green.getPlayer().nickname))
            update.professors0.add(true);
        else update.professors0.add(false);

        if (game.getPlayers().get(0).nickname.equals(game.yellow.getPlayer().nickname))
            update.professors0.add(true);
        else update.professors0.add(false);

        if (game.getPlayers().get(0).nickname.equals(game.pink.getPlayer().nickname))
            update.professors0.add(true);
        else update.professors0.add(false);

        if (game.getPlayers().get(1).nickname.equals(game.red.getPlayer().nickname))
            update.professors1.add(true);
        else update.professors1.add(false);

        if (game.getPlayers().get(1).nickname.equals(game.blue.getPlayer().nickname))
            update.professors1.add(true);
        else update.professors1.add(false);

        if (game.getPlayers().get(1).nickname.equals(game.green.getPlayer().nickname))
            update.professors1.add(true);
        else update.professors1.add(false);

        if (game.getPlayers().get(1).nickname.equals(game.yellow.getPlayer().nickname))
            update.professors1.add(true);
        else update.professors1.add(false);

        if (game.getPlayers().get(1).nickname.equals(game.pink.getPlayer().nickname))
            update.professors1.add(true);
        else update.professors1.add(false);

        update.playersMoves.add(game.order.get(0).maxMoves);
        update.playersMoves.add(game.order.get(1).maxMoves);

    }






    private void set3PlayersAttributes() {
        if (update.nPlayers == 3) {
            update.hallPlayer2.add(game.getPlayers().get(2).getHall().getColor("RED"));
            update.hallPlayer2.add(game.getPlayers().get(2).getHall().getColor("BLUE"));
            update.hallPlayer2.add(game.getPlayers().get(2).getHall().getColor("GREEN"));
            update.hallPlayer2.add(game.getPlayers().get(2).getHall().getColor("YELLOW"));
            update.hallPlayer2.add(game.getPlayers().get(2).getHall().getColor("PINK"));

            if (game.getPlayers().get(2).nickname.equals(game.red.getPlayer().nickname))
                update.professors2.add(true);
            else update.professors2.add(false);

            if (game.getPlayers().get(2).nickname.equals(game.blue.getPlayer().nickname))
                update.professors2.add(true);
            else update.professors2.add(false);

            if (game.getPlayers().get(2).nickname.equals(game.green.getPlayer().nickname))
                update.professors2.add(true);
            else update.professors2.add(false);

            if (game.getPlayers().get(2).nickname.equals(game.yellow.getPlayer().nickname))
                update.professors2.add(true);
            else update.professors2.add(false);

            if (game.getPlayers().get(2).nickname.equals(game.pink.getPlayer().nickname))
                update.professors2.add(true);
            else update.professors2.add(false);

            for (Student s : game.getB().clouds.get(2).students) {
                update.studentsOnCloud2.add(s.getColor());
            }
            for (Student s : game.getPlayers().get(2).getGate().getStudents()) {
                update.gatePlayer2.add(s.getColor());
            }
            update.playersMoves.add(game.order.get(2).maxMoves);
        }
    }






    private void setExpertGameAttributes() {
        if (update.game_Type == 1) {
            update.charactersNum = 3;
            for (Player p : game.order) {
                update.coinsOnPlayer.add(p.getCoins());
            }
            for (CharacterType c : game.characterSelector.getSelectedCharacters()) {
                update.idCharacter.add(c.getIndex());
                update.cardCost.add(c.getCost());
            }
            update.activated.add(game.characterSelector.getSelectedCharacters().get(0).getUsed());
            update.activated.add(game.characterSelector.getSelectedCharacters().get(1).getUsed());
            update.activated.add(game.characterSelector.getSelectedCharacters().get(2).getUsed());
            update.MNbonus = game.getMNbonus();
            update.numTD = 0;
            for (CharacterType c :
                    game.characterSelector.getSelectedCharacters()) {
                if (c.getIndex() == 4) {
                    ConcreteCharacter k = (ConcreteCharacter) c;
                    update.numTD = k.getTD();
                }
            }
            for (int i = 1; i <= game.getB().islands.size(); i++) {
                update.numTDOnIsland.add(game.getB().islands.getIsland(i).TD);
            }
            CharacterType c = game.characterSelector.getSelectedCharacters().get(0);
            if (c.getIndex() == 0 || c.getIndex() == 6 || c.getIndex() == 10) {
                ConcreteCharacter k = (ConcreteCharacter) c;
                for (int i = 0; i < k.students.size(); i++) {
                    update.studentsOnCard0.add(k.students.get(i).getColor());
                }
            }
            c = game.characterSelector.getSelectedCharacters().get(0);
            if (c.getIndex() == 0 || c.getIndex() == 6 || c.getIndex() == 10) {
                ConcreteCharacter k = (ConcreteCharacter) c;
                for (int i = 0; i < k.students.size(); i++) {
                    update.studentsOnCard1.add(k.students.get(i).getColor());
                }
            }
            c = game.characterSelector.getSelectedCharacters().get(0);
            if (c.getIndex() == 0 || c.getIndex() == 6 || c.getIndex() == 10) {
                ConcreteCharacter k = (ConcreteCharacter) c;
                for (int i = 0; i < k.students.size(); i++) {
                    update.studentsOnCard2.add(k.students.get(i).getColor());
                }
            }
        }
    }
}
