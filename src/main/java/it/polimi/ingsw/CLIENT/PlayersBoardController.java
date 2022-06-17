package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.CLIENT.GUIobjects.*;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PlayersBoardController {

    private GUIAPP gui;
    private UpdateMessage update;
    private String playerNickname;

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

    public void refresh() {
        update = gui.getUpdate();
        CoordinatesData.loadCoordinates();
        playerNickname=gui.getPlayerNickname();

        studentsOnGateUpdate();
        professorsUpdate();
        hallUpdate();
        setLastCardPlayed();
        nameField.setText(playerNickname);
        if(!update.coinsOnPlayer.isEmpty())
            coinsOwned.setText(""+update.coinsOnPlayer.get(playerIndex())+"");
    }

    private void studentsOnGateUpdate() {
        for (int i = 1; i < update.gatePlayer.get(playerIndex()).size(); i = i + 2) {
            StudentGUI student = new StudentGUI(update.gatePlayer.get(playerIndex()).get(i));
            gate.getChildren().add(student);
            student.setLayoutX(CoordinatesData.getGate().get(i / 2).getX());
            student.setLayoutY(CoordinatesData.getGate().get(i / 2).getY());
        }
    }

    private void setLastCardPlayed(){
        if(update.lastCardsPlayed.size() > (playerIndex()*2) + 1) {
            int value = update.lastCardsPlayed.get((playerIndex()*2) + 1);
            switch (value) {
                case 1:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (1).png"));
                    break;
                case 2:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (2).png"));
                    break;
                case 3:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (3).png"));
                    break;
                case 4:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (4).png"));
                    break;
                case 5:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (5).png"));
                    break;
                case 6:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (6).png"));
                    break;
                case 7:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (7).png"));
                    break;
                case 8:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (8).png"));
                    break;
                case 9:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (9).png"));
                    break;
                case 10:
                    lastCardPlayed.setImage(new Image("Graphical_Assets/Assistente (10).png"));
                    break;
            }
        }
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

    public void backToBoard(){
        gui.setScene("fxml/board.fxml");
        gui.boardController.refresh();
    }

}
