package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.CLIENT.GUIobjects.*;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * FXML Controller class for the Players Board information scene. Displays information about the selected opponent's
 * board and general game status.
 *
 * @author GC56
 */
public class PlayersBoardController {

    private GUIAPP gui;
    private UpdateMessage update;
    private String playerNickname, userNickname;

    @FXML
    private ImageView tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8;
    @FXML
    private Text nameField, coinsOwned;
    @FXML
    private Pane redHall0, redHall1, redHall2, redHall3, redHall4, redHall5, redHall6, redHall7, redHall8, redHall9;
    @FXML
    private Pane blueHall0, blueHall1, blueHall2, blueHall3, blueHall4, blueHall5, blueHall6, blueHall7, blueHall8, blueHall9;
    @FXML
    private Pane greenHall0, greenHall1, greenHall2, greenHall3, greenHall4, greenHall5, greenHall6, greenHall7, greenHall8, greenHall9;
    @FXML
    private Pane yellowHall0, yellowHall1, yellowHall2, yellowHall3, yellowHall4, yellowHall5, yellowHall6, yellowHall7, yellowHall8, yellowHall9;
    @FXML
    private Pane pinkHall0, pinkHall1, pinkHall2, pinkHall3, pinkHall4, pinkHall5, pinkHall6, pinkHall7, pinkHall8, pinkHall9;
    @FXML
    private Pane redProfessor, blueProfessor, greenProfessor, yellowProfessor, pinkProfessor;
    @FXML
    private Pane gate;
    @FXML
    private ImageView lastCardPlayed;

    public void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    /**
     * Main method of the View. Builds and shows to the user all the appropriate GUI objects and the FXML scene.
     * Called by the GUIAPP when the scene is set.
     */
    public void refresh() {
        update = gui.getUpdate();
        CoordinatesData.loadCoordinates();
        playerNickname = gui.getPlayerNickname();
        userNickname = gui.getUserNickname();

        towersUpdate();
        playersNicknames();
        studentsOnGateUpdate();
        professorsUpdate();
        hallUpdate();
        setLastCardPlayed();
        nameField.setText(playerNickname);
        if (!update.coinsOnPlayer.isEmpty())
            coinsOwned.setText("" + update.coinsOnPlayer.get(playerIndex()) + "");
    }

    private void studentsOnGateUpdate() {
        for (int i = 1; i < update.gatePlayer.get(playerIndex()).size(); i = i + 2) {
            StudentGUI student = new StudentGUI(update.gatePlayer.get(playerIndex()).get(i));
            gate.getChildren().add(student);
            student.setLayoutX(CoordinatesData.getGate().get(i / 2).getX());
            student.setLayoutY(CoordinatesData.getGate().get(i / 2).getY());
        }
    }

    /**
     * Displays the last card played by the selected opponent.
     */
    private void setLastCardPlayed() {
        if (update.lastCardsPlayed.size() > (playerIndex() * 2) + 1) {
            if (update.valueCardsPlayed.get(playerIndex()) != 100) {
                int value = update.valueCardsPlayed.get((playerIndex()));
                System.out.println(value);
                lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (" + value + ").png"));
            }
        }
    }

    public void playersNicknames() {
        ArrayList<String> nicknames = new ArrayList<>(update.players);
        nicknames.remove(update.players.indexOf(userNickname));

        if (playerNickname.equals(update.players.get(0))) {
            nicknames.remove(playerIndex());
        } else if (!nicknames.isEmpty()) {
            setTowerImage();
        }
    }


    private void towersUpdate() {
        int k = 8 - update.towersOnPlayer.get(playerIndex());
        ArrayList<ImageView> towers = new ArrayList<>();
        towers.add(tower1);
        towers.add(tower2);
        towers.add(tower3);
        towers.add(tower4);
        towers.add(tower5);
        towers.add(tower6);
        towers.add(tower7);
        towers.add(tower8);
        for (int i = 0; i < k; i++) {
            towers.get(i).setVisible(false);
        }
    }

    private void setTowerImage() {
        tower1.setImage(new Image("Graphical_Assets/grey_tower.png"));
        tower2.setImage(new Image("Graphical_Assets/grey_tower.png"));
        tower3.setImage(new Image("Graphical_Assets/grey_tower.png"));
        tower4.setImage(new Image("Graphical_Assets/grey_tower.png"));
        tower5.setImage(new Image("Graphical_Assets/grey_tower.png"));
        tower6.setImage(new Image("Graphical_Assets/grey_tower.png"));
        tower7.setImage(new Image("Graphical_Assets/grey_tower.png"));
        tower8.setImage(new Image("Graphical_Assets/grey_tower.png"));
    }


    private void professorsUpdate() {
        redProfessor.setVisible(update.professors.get(playerIndex()).get(0));
        blueProfessor.setVisible(update.professors.get(playerIndex()).get(1));
        greenProfessor.setVisible(update.professors.get(playerIndex()).get(2));
        yellowProfessor.setVisible(update.professors.get(playerIndex()).get(3));
        pinkProfessor.setVisible(update.professors.get(playerIndex()).get(4));
    }

    private void hallUpdate() {
        ArrayList<Pane> redHall = new ArrayList<>();
        ArrayList<Pane> blueHall = new ArrayList<>();
        ArrayList<Pane> greenHall = new ArrayList<>();
        ArrayList<Pane> yellowHall = new ArrayList<>();
        ArrayList<Pane> pinkHall = new ArrayList<>();

        arrayBuild(redHall, redHall0, redHall1, redHall2, redHall3, redHall4, redHall5, redHall6, redHall7, redHall8, redHall9);
        arrayBuild(blueHall, blueHall0, blueHall1, blueHall2, blueHall3, blueHall4, blueHall5, blueHall6, blueHall7, blueHall8, blueHall9);
        arrayBuild(greenHall, greenHall0, greenHall1, greenHall2, greenHall3, greenHall4, greenHall5, greenHall6, greenHall7, greenHall8, greenHall9);
        arrayBuild(yellowHall, yellowHall0, yellowHall1, yellowHall2, yellowHall3, yellowHall4, yellowHall5, yellowHall6, yellowHall7, yellowHall8, yellowHall9);
        arrayBuild(pinkHall, pinkHall0, pinkHall1, pinkHall2, pinkHall3, pinkHall4, pinkHall5, pinkHall6, pinkHall7, pinkHall8, pinkHall9);

        int redNumber = update.hallPlayer.get(playerIndex()).get(0);
        int blueNumber = update.hallPlayer.get(playerIndex()).get(1);
        int greenNumber = update.hallPlayer.get(playerIndex()).get(2);
        int yellowNumber = update.hallPlayer.get(playerIndex()).get(3);
        int pinkNumber = update.hallPlayer.get(playerIndex()).get(4);

        for (int i = 0; i < redNumber; i++) {
            redHall.get(i).setVisible(true);
        }
        for (int i = 0; i < blueNumber; i++) {
            blueHall.get(i).setVisible(true);
        }
        for (int i = 0; i < greenNumber; i++) {
            greenHall.get(i).setVisible(true);
        }
        for (int i = 0; i < yellowNumber; i++) {
            yellowHall.get(i).setVisible(true);
        }
        for (int i = 0; i < pinkNumber; i++) {
            pinkHall.get(i).setVisible(true);
        }
    }

    public int playerIndex() {
        return update.players.indexOf(playerNickname);
    }


    private void arrayBuild(ArrayList<Pane> array, Pane element1, Pane element2, Pane element3, Pane element4, Pane element5, Pane element6, Pane element7, Pane element8, Pane element9, Pane element10) {
        HandController.arrayBuild(array, element1, element2, element3, element4, element5, element6, element7, element8, element9, element10);
    }

    /**
     * Method associated with an FXML button. Sets the scene back to the Board scene.
     */
    @FXML
    public void backToBoard() {
        gui.setScene("fxml/board.fxml");
        gui.boardController.refresh();
    }

}
