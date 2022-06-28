package it.polimi.ingsw.GAME;

import it.polimi.ingsw.MESSAGES.UpdateMessage;
import it.polimi.ingsw.SERVER.GameManager;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ModelView extends Observable implements Observer {
    private UpdateMessage update;
    private final Game game;
    private boolean gameOver;


    public ModelView(Game game, GameManager gameManager) {
        this.update = new UpdateMessage();
        this.game = game;
        this.addObserver(gameManager);
        gameOver=false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!gameOver) {
            this.update = new UpdateMessage();
            if (game.getGameOver()) {
                update.gameEnded = true;
                gameOver = true;
            }
            update.update = (ArrayList<String>) arg;
            update.update = new ArrayList<>(update.update);
            setGameAttributes();
            setBoardAttributes();
            setPlayersAttributes();
            setExpertGameAttributes();

            setChanged();
            notifyObservers(update);
        }
    }


    private void setGameAttributes() {
        for (Player p : game.order) {
            update.order.add(p.nickname);
        }
        update.phase = game.roundMaster.round.getCurrentPhase();
        update.turnNumber = game.roundMaster.getRoundCount();
        update.game_Type = game.getGameType();
        update.nPlayers = game.getPlayerCount();
        update.numIslands = game.getBoard().islands.size();
    }


    private void setBoardAttributes() {
        for (Card c : game.getCardsPlayed()) {
            update.lastCardsPlayed.add(c.getMovement());
            update.lastCardsPlayed.add(c.getValue());
        }

        update.valueCardsPlayed.addAll(game.getValueCardPlayed());

        for (int k = 1; k <= game.getBoard().islands.size(); k++) {
            if (game.getBoard().islands.getIsland(k).motherNature)
                update.motherNatureOnIsland.add(true);
            else update.motherNatureOnIsland.add(false);
        }

        for (int k = 1; k <= game.getBoard().islands.size(); k++) {
            update.towersOnIsland.add(game.getBoard().islands.getIsland(k).towers.size());
        }
        update.cloudtaken=game.cloudEmptied;
        ArrayList<String> tmp;
        for (int i = 0; i < game.getBoard().clouds.size(); i++) {
            tmp = new ArrayList<>();
            for (Student s : game.getBoard().clouds.get(i).students) {
                tmp.add(s.getColorInCLI());
                tmp.add(s.getColor());

            }
            update.studentsOnCloud.put(i, tmp);
        }

        for (int i = 1; i <= game.getBoard().islands.size(); i++) {
            if (game.getBoard().islands.getIsland(i).getPlayer() != null)
                update.whoOwnTowers.add(game.getBoard().islands.getIsland(i).getPlayer().nickname);
            else update.whoOwnTowers.add("NONE");
        }

        for (int i = 1; i <= game.getBoard().islands.size(); i++) {
            tmp = new ArrayList<>();
            for (Student s : game.getBoard().islands.getIsland(i).getStudents()) {
                tmp.add(s.getColorInCLI());
                tmp.add(s.getColor());
            }
            update.studentsOnIsland.put(i, tmp);
        }

        for (int i = 0; i < game.getPlayerCount(); i++) {
            tmp = new ArrayList<>();
            for (Student s : game.getPlayers().get(i).getGate().getStudents()) {
                tmp.add(s.getColorInCLI());
                tmp.add(s.getColor());
            }
            update.gatePlayer.put(i, tmp);
        }
    }


    private void setPlayersAttributes() {
        for (Player p : game.getPlayers()) {
            update.towersOnPlayer.add(p.getTower_count());
        }

        for (Player p : game.getPlayers()) {
            update.players.add(p.nickname);
        }

        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            tmp.add(game.getPlayers().get(i).getHall().getColor("RED"));
            tmp.add(game.getPlayers().get(i).getHall().getColor("BLUE"));
            tmp.add(game.getPlayers().get(i).getHall().getColor("GREEN"));
            tmp.add(game.getPlayers().get(i).getHall().getColor("YELLOW"));
            tmp.add(game.getPlayers().get(i).getHall().getColor("PINK"));
            update.hallPlayer.put(i, tmp);
            tmp = new ArrayList<>();

        }

        ArrayList<Boolean> bool = new ArrayList<>();
        for (int i = 0; i < game.getPlayerCount(); i++) {
            if (game.red.getPlayer()!=null && game.getPlayers().get(i).nickname.equals(game.red.getPlayer().nickname))
                bool.add(true);
            else bool.add(false);
            if (game.blue.getPlayer()!=null && game.getPlayers().get(i).nickname.equals(game.blue.getPlayer().nickname))
                bool.add(true);
            else bool.add(false);
            if (game.green.getPlayer()!=null && game.getPlayers().get(i).nickname.equals(game.green.getPlayer().nickname))
                bool.add(true);
            else bool.add(false);
            if (game.yellow.getPlayer()!=null && game.getPlayers().get(i).nickname.equals(game.yellow.getPlayer().nickname))
                bool.add(true);
            else bool.add(false);
            if (game.pink.getPlayer()!=null && game.getPlayers().get(i).nickname.equals(game.pink.getPlayer().nickname))
                bool.add(true);
            else bool.add(false);
            update.professors.put(i, bool);
            bool = new ArrayList<>();
        }

        update.playersMoves.add(game.order.get(0).maxMoves);
        if(!game.order.isEmpty()){
            update.playersMoves.add(game.order.get(0).maxMoves);
        }

        for (int i = 0; i < game.getPlayers().size(); i++) {
            tmp = new ArrayList<>();
            for (Card c : game.getPlayers().get(i).getHand().getCards()) {
                tmp.add(c.getMovement());
                tmp.add(c.getValue());
            }
            update.handPlayer.put(i, tmp);
        }

        if (!game.order.isEmpty() && update.nPlayers == 3) {
            update.playersMoves.add(game.order.get(0).maxMoves);
        }
    }


    private void setExpertGameAttributes() {
        if (update.game_Type == 1) {
            for (Player p : game.getPlayers()) {
                update.coinsOnPlayer.add(p.getCoins());
            }

            for (CharacterType c : game.characterSelector.getCharacters()) {
                update.idCharacter.add(c.getIndex());
                update.cardCost.add(c.getCost());
            }

            update.activated.add(game.characterSelector.getCharacters().get(0).getUsed());
            update.activated.add(game.characterSelector.getCharacters().get(1).getUsed());
            update.activated.add(game.characterSelector.getCharacters().get(2).getUsed());
            update.numTD = 0;

            for (CharacterType c :
                    game.characterSelector.getCharacters()) {
                if (c.getIndex() == 4) {
                    ConcreteCharacter k = (ConcreteCharacter) c;
                    update.numTD = k.getTD();
                }
            }

            for (int i = 1; i <= game.getBoard().islands.size(); i++) {
                update.numTDOnIsland.add(game.getBoard().islands.getIsland(i).TD);
            }

            CharacterType c;
            ArrayList<String> tmp;
            for (int i = 0; i < 3; i++) {
                tmp = new ArrayList<>();
                c = game.characterSelector.getCharacters().get(i);
                if (c.getIndex() == 0 || c.getIndex() == 6 || c.getIndex() == 10) {
                    ConcreteCharacter k = (ConcreteCharacter) c;
                    for (Student s:k.getStudents()) {
                        tmp.add(s.getColorInCLI());
                        tmp.add(s.getColor());
                    }
                    update.studentsOnCard.put(i, tmp);
                }
            }

        }
    }
}
