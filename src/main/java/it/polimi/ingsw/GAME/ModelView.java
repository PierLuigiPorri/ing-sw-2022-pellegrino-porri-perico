package it.polimi.ingsw.GAME;

import it.polimi.ingsw.MESSAGES.UpdateMessage;
import it.polimi.ingsw.SERVER.GameManager;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * It fills the update message with all the information needed. This class is notified by the class Game when something in the model has changed, and notifies the GameManager.
 * @author GC56
 */
public class ModelView extends Observable implements Observer {
    private UpdateMessage update;
    private final Game game;
    private boolean gameOver;

    /**
     * ModelView constructor. It constructs the update message and sets the Game from which this is going to take information, and GameManager which is the object to which the information are sent to be sent then to te clients.
     * @param game the game
     * @param gameManager the GameManager which is going to send the update to all the clients.
     */
    public ModelView(Game game, GameManager gameManager) {
        this.update = new UpdateMessage();
        this.game = game;
        this.addObserver(gameManager);
        gameOver=false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!gameOver) {
            //creates a new UpdateMessage
            this.update = new UpdateMessage();
            if (game.getGameOver()) {
                update.gameEnded = true;
                gameOver = true;
            }
            //sets all the attributes needed in update message.
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

    /**
     * It sets the game attributes, so every information from the model which is not specifically of a player or of the board.
     */
    private void setGameAttributes() {
        for (Player p : game.order) {
            update.order.add(p.nickname);
        }
        //sets current phase, the current turn's number, the game type, the number of players in the game and, the number of islands.
        update.phase = game.roundMaster.round.getCurrentPhase();
        update.turnNumber = game.roundMaster.getRoundCount();
        update.game_Type = game.getGameType();
        update.nPlayers = game.getPlayerCount();
        update.numIslands = game.getBoard().islands.size();
    }

    /**
     * It sets every information from the model which is common between players in the game.
     */
    private void setBoardAttributes() {
        //setting the last card played by each player.
        for (Card c : game.getCardsPlayed()) {
            update.lastCardsPlayed.add(c.getMovement());
            update.lastCardsPlayed.add(c.getValue());
        }

        // sets the values of the card played by each player.
        update.valueCardsPlayed.addAll(game.getValueCardPlayed());

        // sets, for every island, true if and only if MotherNature is placed on the specified island.
        for (int k = 1; k <= game.getBoard().islands.size(); k++) {
            if (game.getBoard().islands.getIsland(k).motherNature)
                update.motherNatureOnIsland.add(true);
            else update.motherNatureOnIsland.add(false);
        }

        // sets, for every island, the number of towers on the specified island.
        for (int k = 1; k <= game.getBoard().islands.size(); k++) {
            update.towersOnIsland.add(game.getBoard().islands.getIsland(k).towers.size());
        }

        // true if and only if the current player has already taken students from a cloud.
        update.cloudtaken=game.cloudEmptied;

        //sets in update.studentsOnCloud, all the students placed on each cloud.
        ArrayList<String> tmp;
        for (int i = 0; i < game.getBoard().clouds.size(); i++) {
            tmp = new ArrayList<>();
            for (Student s : game.getBoard().clouds.get(i).students) {
                tmp.add(s.getColorInCLI());
                tmp.add(s.getColor());

            }
            update.studentsOnCloud.put(i, tmp);
        }

        // sets for each island the owner of the towers on the specified island.
        for (int i = 1; i <= game.getBoard().islands.size(); i++) {
            if (game.getBoard().islands.getIsland(i).getPlayer() != null)
                update.whoOwnTowers.add(game.getBoard().islands.getIsland(i).getPlayer().nickname);
            else update.whoOwnTowers.add("NONE");
        }

        // sets in update.studentsOnIsland, all the students placed on each island.
        for (int i = 1; i <= game.getBoard().islands.size(); i++) {
            tmp = new ArrayList<>();
            for (Student s : game.getBoard().islands.getIsland(i).getStudents()) {
                tmp.add(s.getColorInCLI());
                tmp.add(s.getColor());
            }
            update.studentsOnIsland.put(i, tmp);
        }

        // sets in update.gatePlayer, all the students that are in the gate of the specified player, for each player.
        for (int i = 0; i < game.getPlayerCount(); i++) {
            tmp = new ArrayList<>();
            for (Student s : game.getPlayers().get(i).getGate().getStudents()) {
                tmp.add(s.getColorInCLI());
                tmp.add(s.getColor());
            }
            update.gatePlayer.put(i, tmp);
        }
    }

    /**
     * It sets every information from the model which is of a specific player, so everything which is not in common between players.
     */
    private void setPlayersAttributes() {
        //sets the number of towers for each player.
        for (Player p : game.getPlayers()) {
            update.towersOnPlayer.add(p.getTower_count());
        }

        // sets the nickname of every player.
        for (Player p : game.getPlayers()) {
            update.players.add(p.nickname);
        }

        // sets the number of RED, BLUE, GREEN, YELLOW and PINK students on the hall of a player, for each player.
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

        // sets the professors of a player, for each player. Every professor is true for a player if and only if is the one who owns the professor.
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

        // sets the number of moves remained to a player.
        update.playersMoves.add(game.order.get(0).maxMoves);
        if(!game.order.isEmpty()){
            update.playersMoves.add(game.order.get(0).maxMoves);
        }
        if (!game.order.isEmpty() && update.nPlayers == 3) {
            update.playersMoves.add(game.order.get(0).maxMoves);
        }

        // sets in update.handPlayer the cards contained in a player's hand, for each player.
        for (int i = 0; i < game.getPlayers().size(); i++) {
            tmp = new ArrayList<>();
            for (Card c : game.getPlayers().get(i).getHand().getCards()) {
                tmp.add(c.getMovement());
                tmp.add(c.getValue());
            }
            update.handPlayer.put(i, tmp);
        }
    }


    /**
     * Sets all the information from the model that are used only with expert game rules.
     */
    private void setExpertGameAttributes() {
        if (update.game_Type == 1) {
            //sets the number of coins of a player, for each player.
            for (Player p : game.getPlayers()) {
                update.coinsOnPlayer.add(p.getCoins());
            }

            // sets the id and the cost of a character, for each character.
            for (CharacterType c : game.characterSelector.getCharacters()) {
                update.idCharacter.add(c.getIndex());
                update.cardCost.add(c.getCost());
            }

            //sets for each character true if and only if the specific character has been activated.
            update.activated.add(game.characterSelector.getCharacters().get(0).getUsed());
            update.activated.add(game.characterSelector.getCharacters().get(1).getUsed());
            update.activated.add(game.characterSelector.getCharacters().get(2).getUsed());

            // default value of prohibition cards.
            update.numTD = 0;

            // sets the number of prohibition cards.
            for (CharacterType c :
                    game.characterSelector.getCharacters()) {
                if (c.getIndex() == 4) {
                    ConcreteCharacter k = (ConcreteCharacter) c;
                    update.numTD = k.getTD();
                }
            }

            // sets the number of prohibition cards on an island, for each island.
            for (int i = 1; i <= game.getBoard().islands.size(); i++) {
                update.numTDOnIsland.add(game.getBoard().islands.getIsland(i).TD);
            }

            // sets the students on a character card, for each character card in the game that can have students on itself.
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
