package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.CLIENT.GUIobjects.*;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class BoardController {

    private static GUIAPP gui;
    private UpdateMessage update;
    private int playersNumber;
    private int gameType;
    private String userNickname, player1Nickname, player2Nickname;
    private MotherNatureGUI motherNature;
    private StudentGUI selectedStudent;
    private CloudGUI selectedCloud;

    private ArrayList<IslandGUI> islands;

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
    private Button seePlayer2Hall, seeCharacter;
    @FXML
    private Text player1, player2;

    public static void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    public void refresh() {
        update = gui.getUpdate();
        this.userNickname = gui.getUserNickname();
        this.playersNumber = update.players.size();
        this.gameType = update.game_Type;
        CoordinatesData.loadCoordinates();

        playersNicknames();
        player1.setText(player1Nickname);
        createIslands();

        towersUpdate();
        motherNatureUpdate();
        studentsOnIslandUpdate();
        studentsOnGateUpdate();

        if(gameType==1){
            seeCharacter.setVisible(true);
            seeCharacter.setDisable(false);
        }

        if (playersNumber == 2) {
            studentsOnCloud2Update();
        } else {
            studentsOnCloud3Update();
            seePlayer2Hall.setVisible(true);
            seePlayer2Hall.setDisable(false);
            player2.setText(player2Nickname);
        }
        professorsUpdate();
        hallUpdate();
    }


    public void playersNicknames(){
        ArrayList<String> nicknames = new ArrayList<>(update.players);
        nicknames.remove(userIndex());
        player1Nickname=nicknames.remove(0);
        if(!nicknames.isEmpty()){
            player2Nickname=nicknames.remove(0);
        }
    }


    private void towersUpdate() {
        for (int i = 0; i < update.numIslands; i++) {
            String nick = update.whoOwnTowers.get(i);

            if (nick.equals(update.players.get(0))) {
                setTowerOnIsland(i, "WHITE");
            } else if (nick.equals(update.players.get(1))) {
                setTowerOnIsland(i, "BLACK");
            } else if (nick.equals(update.players.get(2))) {
                setTowerOnIsland(i, "GREY");
            }
        }
    }


    private void setTowerOnIsland(int index, String color) {
        if(update.towersOnIsland.get(index)!=0) {
            TowerGUI tower = new TowerGUI(color);
            islands.get(index).getChildren().add(tower);
            tower.setLayoutY(CoordinatesData.getTowersCoordinates().getY());
            tower.setLayoutX(CoordinatesData.getTowersCoordinates().getX());
            if(update.towersOnIsland.get(index)>1){
                islands.get(index).getChildren().add(new TowerCountPane(update.towersOnIsland.get(index)));
            }
        }
    }

    private void motherNatureUpdate() {
        motherNature = new MotherNatureGUI();
        for (int i = 0; i < update.numIslands; i++) {
            if (update.motherNatureOnIsland.get(i)) {
                islands.get(i).getChildren().add(motherNature);
                motherNature.setLayoutX(CoordinatesData.getMotherNatureCoordinates().getX());
                motherNature.setLayoutY(CoordinatesData.getMotherNatureCoordinates().getY());
                motherNature.setOnMousePressed((e) -> onMotherNaturePressed(e, motherNature));
                motherNature.setOnMouseDragged((e) -> onMotherNatureDragged(e, motherNature));
            }
        }
    }

    private void studentsOnIslandUpdate() {

        for (int index : update.studentsOnIsland.keySet()) {
            for (int i = 1; i < update.studentsOnIsland.get(index).size(); i = i + 2){
                switch(update.studentsOnIsland.get(index).get(i)){
                    case "RED":
                        islands.get(index).setRed();
                        break;
                    case "BLUE":
                        islands.get(index).setBlue();
                        break;
                    case "GREEN":
                        islands.get(index).setGreen();
                        break;
                    case "YELLOW":
                        islands.get(index).setYellow();
                        break;
                    case "PINK":
                        islands.get(index).setPink();
                        break;
                }
            }
            if(islands.get(index).getRed()>0) {
                StudentGUI student = new StudentGUI("RED");
                islands.get(index).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("RED").getX());
                student.setLayoutY(CoordinatesData.getIsland("RED").getY());
                if(islands.get(index).getRed()>1){
                    islands.get(index).getChildren().add(new CountPane("RED", islands.get(index).getRed()));
                }
            }
            if(islands.get(index).getBlue()>0) {
                StudentGUI student = new StudentGUI("BLUE");
                islands.get(index).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("BLUE").getX());
                student.setLayoutY(CoordinatesData.getIsland("BLUE").getY());
                if(islands.get(index).getBlue()>1){
                    islands.get(index).getChildren().add(new CountPane("BLUE", islands.get(index).getBlue()));
                }
            }
            if(islands.get(index).getGreen()>0) {
                StudentGUI student = new StudentGUI("GREEN");
                islands.get(index).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("GREEN").getX());
                student.setLayoutY(CoordinatesData.getIsland("GREEN").getY());
                if(islands.get(index).getGreen()>1){
                    islands.get(index).getChildren().add(new CountPane("GREEN", islands.get(index).getGreen()));
                }
            }
            if(islands.get(index).getYellow()>0) {
                StudentGUI student = new StudentGUI("YELLOW");
                islands.get(index).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("YELLOW").getX());
                student.setLayoutY(CoordinatesData.getIsland("YELLOW").getY());
                if(islands.get(index).getYellow()>1){
                    islands.get(index).getChildren().add(new CountPane("YELLOW", islands.get(index).getYellow()));
                }
            }
            if(islands.get(index).getPink()>0) {
                StudentGUI student = new StudentGUI("PINK");
                islands.get(index).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("PINK").getX());
                student.setLayoutY(CoordinatesData.getIsland("PINK").getY());
                if(islands.get(index).getPink()>1){
                    islands.get(index).getChildren().add(new CountPane("PINK", islands.get(index).getPink()));
                }
            }
        }
    }

    private void createIslands() {
        islands = new ArrayList<>();

        for (int index : update.studentsOnIsland.keySet()) {
            islands.add(new IslandGUI(index));
            islands.get(index).setLayoutX(CoordinatesData.getIslandsCoord(update.numIslands).get(index).getX());
            islands.get(index).setLayoutY(CoordinatesData.getIslandsCoord(update.numIslands).get(index).getY());
            islands.get(index).setOnDragDropped((dragEvent) -> onDragOnIsland(dragEvent, islands.get(index)));
        }

    }

    private void studentsOnGateUpdate() {
        for (int i = 1; i < update.gatePlayer.get(userIndex()).size(); i = i + 2) {
            StudentGUI student = new StudentGUI(update.gatePlayer.get(userIndex()).get(i));
            gate.getChildren().add(student);
            student.setLayoutX(CoordinatesData.getGate().get(i / 2).getX());
            student.setLayoutY(CoordinatesData.getGate().get(i / 2).getY());
            student.setOnMousePressed((e) -> onStudentPressed(e, student));
            student.setOnMouseDragged((e) -> onStudentDragged(e, student));
        }
    }

    private void studentsOnCloud2Update() {
        for (int index : update.studentsOnCloud.keySet()) {
            CloudGUI cloud = new CloudGUI(playersNumber, index);
            for (int i = 1; i < update.studentsOnCloud.get(index).size(); i = i + 2) {
                StudentGUI student = new StudentGUI(update.studentsOnCloud.get(index).get(i));
                cloud.getChildren().add(student);
                student.setLayoutX(CoordinatesData.getClouds2().get(i / 2).getX());
                student.setLayoutY(CoordinatesData.getClouds2().get(i / 2).getY());
            }
            cloud.setOnMousePressed(this::onCloudPressed);
        }
    }

    private void studentsOnCloud3Update() {
        for (int index : update.studentsOnCloud.keySet()) {
            CloudGUI cloud = new CloudGUI(playersNumber, index);
            for (int i = 0; i < update.studentsOnCloud.get(index).size(); i = i + 2) {
                StudentGUI student = new StudentGUI(update.studentsOnCloud.get(index).get(i));
                cloud.getChildren().add(student);
                student.setLayoutX(CoordinatesData.getClouds3().get(i / 2).getX());
                student.setLayoutY(CoordinatesData.getClouds3().get(i / 2).getY());
            }
            cloud.setOnMousePressed(this::onCloudPressed);
        }
    }

    private void professorsUpdate() {
        redProfessor.setVisible(update.professors.get(userIndex()).get(0));
        blueProfessor.setVisible(update.professors.get(userIndex()).get(1));
        greenProfessor.setVisible(update.professors.get(userIndex()).get(2));
        yellowProfessor.setVisible(update.professors.get(userIndex()).get(3));
        pinkProfessor.setVisible(update.professors.get(userIndex()).get(4));
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

        int redNumber = update.hallPlayer.get(userIndex()).get(0);
        int blueNumber = update.hallPlayer.get(userIndex()).get(1);
        int greenNumber = update.hallPlayer.get(userIndex()).get(2);
        int yellowNumber = update.hallPlayer.get(userIndex()).get(3);
        int pinkNumber = update.hallPlayer.get(userIndex()).get(4);

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

    private void arrayBuild(ArrayList<Pane> array, Pane element1, Pane element2, Pane element3, Pane element4, Pane element5, Pane element6, Pane element7, Pane element8, Pane element9, Pane element10) {
        HandController.arrayBuild(array, element1, element2, element3, element4, element5, element6, element7, element8, element9, element10);
    }

    private void onDragOnIsland(DragEvent event, IslandGUI i) {
        if (event.getSource() instanceof StudentGUI) {
            ArrayList<Integer> par = new ArrayList<>();
            par.add(CoordinatesData.getIndex(selectedStudent.getCoord()));
            par.add(i.getIndex());
            gui.perform(par, null, 0);
        } else if (event.getSource() instanceof MotherNatureGUI) {
            ArrayList<Integer> par = new ArrayList<>();
            par.add(i.getIndex() + (i.getIndex() < update.motherNatureOnIsland.indexOf(true) ? update.numIslands : 0) - update.motherNatureOnIsland.indexOf(true));
            gui.perform(par, null, 3);
        }
    }


    private void onStudentPressed(MouseEvent mouseEvent, StudentGUI s) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0))) {
            s.pressed(mouseEvent.getX(), mouseEvent.getY());
            selectedStudent = s;
        }
    }

    private void onStudentDragged(MouseEvent mouseEvent, StudentGUI s) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0))) {
            s.dragged(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    private void onMotherNaturePressed(MouseEvent mouseEvent, MotherNatureGUI m) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0))) {
            m.pressed(mouseEvent.getX(), mouseEvent.getY());
        }
    }

    private void onMotherNatureDragged(MouseEvent mouseEvent, MotherNatureGUI s) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0))) {
            s.dragged(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    @FXML
    private void onDragOnHall() {
        ArrayList<String> par = new ArrayList<>();
        par.add(selectedStudent.getColor());
        gui.perform(null, par, 1);
    }

    private void onCloudPressed(MouseEvent e) {
        selectedCloud = (CloudGUI) e.getSource();
    }

    @FXML
    private void onCloudButtonPressed() {
        ArrayList<Integer> par = new ArrayList<>();
        par.add(selectedCloud.getIndex());
        gui.perform(par, null, 2);
    }

    public int userIndex() {
        return update.players.indexOf(userNickname);
    }

    @FXML
    public void seePlayer1Hall() {
        gui.setPlayerNickname(player1Nickname);
        gui.setScene("fxml/PlayersBoard.fxml");
    }

    @FXML
    public void seePlayer2Hall() {
        gui.setPlayerNickname(player2Nickname);
        gui.setScene("fxml/PlayersBoard.fxml");
    }

    @FXML
    public void SeeYourHand() {
        gui.setScene("fxml/Hand.fxml");
    }

    @FXML
    public void SeeCharacter() {
        gui.setScene("fxml/Characters.fxml");
    }

}
