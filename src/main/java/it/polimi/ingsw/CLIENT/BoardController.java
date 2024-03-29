package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.CLIENT.GUIobjects.*;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.util.ArrayList;

/**
 * FXML Controller class for the main game board. Sets and manages the scene during the game. Manages and shows the updates
 * when one arrives. Creates the appropriate GUI objects every time an update is notified, based on the received board state,
 * to avoid inconsistencies between Server and Client. Sets the method that handle user input to the appropriate GUI objects,
 * and executes them. Finally, this is the class that notifies the main GUI class when a message should be sent to the server.
 * @author GC56
 */
public class BoardController {

    private GUIAPP gui;
    private UpdateMessage update;
    private int playersNumber;
    private String userNickname, player1Nickname, player2Nickname;
    private MotherNatureGUI motherNature;
    private StudentGUI selectedStudent;
    private CloudGUI selectedCloud;
    private int gameType;
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
    @FXML
    private Pane root;
    @FXML
    private Text logMessage1, logMessage2, logMessage3;
    @FXML
    private TextArea updateMessageLog;
    @FXML
    private ImageView tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8;

    public void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    /**
     * Main method of the View. Builds and shows to the user all the appropriate GUI objects and the FXML scene.
     * Called by the GUIAPP when an update is received.
     */
    public void refresh() {
        update = gui.getUpdate();
        this.userNickname = gui.getUserNickname();
        this.playersNumber = update.players.size();
        gameType = update.game_Type;
        CoordinatesData.loadCoordinates();

        playersNicknames();
        player1.setText(player1Nickname);
        createIslands();
        studentsOnGateUpdate();


        setLogMessage();
        setUpdateText();
        towersUpdate();
        motherNatureUpdate();
        studentsOnIslandUpdate();


        if (gameType == 1) {
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
        System.gc();
    }


    public void playersNicknames() {
        ArrayList<String> nicknames = new ArrayList<>(update.players);
        nicknames.remove(userIndex());
        player1Nickname = nicknames.remove(0);
        if (!nicknames.isEmpty()) {
            player2Nickname = nicknames.remove(0);
        }
    }


    /**
     * Sets the towers on the board based off the last update received. Called by
     * the refresh() method.
     */
    private void towersUpdate() {
        for (int i = 0; i < update.numIslands; i++) {
            String nick = update.whoOwnTowers.get(i);

            if (nick.equals(userNickname)) {
                setTowerOnIsland(i, "WHITE");
            } else if (nick.equals(player1Nickname)) {
                setTowerOnIsland(i, "BLACK");
            } else if (nick.equals(player2Nickname)) {
                setTowerOnIsland(i, "GREY");
            }
        }
        if (userNickname.equals(player1Nickname)) {
            setTowerImage("Graphical_Assets/black_tower.png");
        }
        if (userNickname.equals(player2Nickname)) {
            setTowerImage("Graphical_Assets/grey_tower.png");
        }

        int k = 8 - update.towersOnPlayer.get(userIndex());
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

    private void setTowerImage(String address) {
        tower1.setImage(new Image(address));
        tower2.setImage(new Image(address));
        tower3.setImage(new Image(address));
        tower4.setImage(new Image(address));
        tower5.setImage(new Image(address));
        tower6.setImage(new Image(address));
        tower7.setImage(new Image(address));
        tower8.setImage(new Image(address));
    }

    /**
     * Sets a tower on a specific Island. If the island has no tower on it, a TowerGUI object is created
     * and put on it with the right coordinates. Otherwise, a TowerCountPane object is also created and set
     * next to the existing tower, with the right tower count, and the island is set as SuperIsland.
     */
    private void setTowerOnIsland(int index, String color) {
        if (update.towersOnIsland.get(index) != 0) {
            TowerGUI tower = new TowerGUI(color);
            tower.setLayoutY(CoordinatesData.getTowersCoordinates().getY());
            tower.setLayoutX(CoordinatesData.getTowersCoordinates().getX());
            islands.get(index).getChildren().add(tower);
            if (update.towersOnIsland.get(index) > 1) {
                islands.get(index).getChildren().add(new TowerCountPane(update.towersOnIsland.get(index)));
                Pane sPane=new Pane();
                sPane.setPrefWidth(109);
                sPane.setPrefHeight(51);
                sPane.setLayoutX(36);
                sPane.setLayoutY(134);
                ImageView img=new ImageView(new Image("Graphical_Assets/SuperIslandScroll.png"));
                img.setFitWidth(109);
                img.setFitHeight(51);
                sPane.getChildren().add(img);
                islands.get(index).getChildren().add(sPane);
            }
        }
    }

    /**
     * Sets the MotherNatureGUI object on the right Island. Also checks if any island has a
     * Prohibition counter on it, and handles the affermative case.
     */
    private void motherNatureUpdate() {
        motherNature = new MotherNatureGUI();
        for (int i = 0; i < update.numIslands; i++) {
            if (update.motherNatureOnIsland.get(i)) {
                islands.get(i).getChildren().add(motherNature);
                motherNature.setLayoutX(CoordinatesData.getMotherNatureCoordinates().getX());
                motherNature.setLayoutY(CoordinatesData.getMotherNatureCoordinates().getY());
                motherNature.setOnMousePressed((e) -> onMotherNaturePressed(e, motherNature));
                motherNature.setOnMouseDragged((e) -> onMotherNatureDragged(e, motherNature));
                motherNature.setOnDragDetected(this::MNDragHandling);
            }
            if (gameType == 1) {
                if (update.numTDOnIsland.get(i)) {
                    Pane pn = new Pane();
                    pn.setLayoutX(CoordinatesData.getMotherNatureCoordinates().getX());
                    pn.setLayoutY(CoordinatesData.getMotherNatureCoordinates().getY());
                    pn.setPrefWidth(80);
                    pn.setPrefHeight(80);
                    ImageView td = new ImageView(new Image("Graphical_Assets/deny_island_icon.png"));
                    td.setFitWidth(80);
                    td.setFitHeight(80);
                    pn.getChildren().add(td);
                    islands.get(i).getChildren().add(pn);
                }
            }
        }
    }

    /**
     * Creates and sets the students on the islands based off the last update received. If there's more than
     * one student of a given color on an island, a CountPane of that color is also created and set next to the student,
     * showing the right student count.
     */
    private void studentsOnIslandUpdate() {

        for (int index : update.studentsOnIsland.keySet()) {
            for (int i = 1; i < update.studentsOnIsland.get(index).size(); i = i + 2) {
                switch (update.studentsOnIsland.get(index).get(i)) {
                    case "RED":
                        islands.get(index - 1).setRed();
                        break;
                    case "BLUE":
                        islands.get(index - 1).setBlue();
                        break;
                    case "GREEN":
                        islands.get(index - 1).setGreen();
                        break;
                    case "YELLOW":
                        islands.get(index - 1).setYellow();
                        break;
                    case "PINK":
                        islands.get(index - 1).setPink();
                        break;
                }
            }
            if (islands.get(index - 1).getRed() > 0) {
                StudentGUI student = new StudentGUI("RED");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("RED").getX());
                student.setLayoutY(CoordinatesData.getIsland("RED").getY());
                if (islands.get(index - 1).getRed() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("RED", islands.get(index - 1).getRed()));
                }
            }
            if (islands.get(index - 1).getBlue() > 0) {
                StudentGUI student = new StudentGUI("BLUE");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("BLUE").getX());
                student.setLayoutY(CoordinatesData.getIsland("BLUE").getY());
                if (islands.get(index - 1).getBlue() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("BLUE", islands.get(index - 1).getBlue()));
                }
            }
            if (islands.get(index - 1).getGreen() > 0) {
                StudentGUI student = new StudentGUI("GREEN");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("GREEN").getX());
                student.setLayoutY(CoordinatesData.getIsland("GREEN").getY());
                if (islands.get(index - 1).getGreen() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("GREEN", islands.get(index - 1).getGreen()));
                }
            }
            if (islands.get(index - 1).getYellow() > 0) {
                StudentGUI student = new StudentGUI("YELLOW");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("YELLOW").getX());
                student.setLayoutY(CoordinatesData.getIsland("YELLOW").getY());
                if (islands.get(index - 1).getYellow() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("YELLOW", islands.get(index - 1).getYellow()));
                }
            }
            if (islands.get(index - 1).getPink() > 0) {
                StudentGUI student = new StudentGUI("PINK");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("PINK").getX());
                student.setLayoutY(CoordinatesData.getIsland("PINK").getY());
                if (islands.get(index - 1).getPink() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("PINK", islands.get(index - 1).getPink()));
                }
            }
        }
    }

    private void createIslands() {
        islands = new ArrayList<>();
        for (int index : update.studentsOnIsland.keySet()) {
            IslandGUI in= new IslandGUI(index);
            in.setLayoutX(CoordinatesData.getIslandsCoord(update.studentsOnIsland.keySet().size()).get(index - 1).getX());
            in.setLayoutY(CoordinatesData.getIslandsCoord(update.studentsOnIsland.keySet().size()).get(index - 1).getY());
            in.setOnDragDropped((dragEvent) -> onDragOnIsland(dragEvent, islands.get(index - 1)));
            in.setOnDragOver(this::onDragIslandOver);
            islands.add(in);
            root.getChildren().add(in);
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
            student.setOnDragDetected(this::studentDragHandling);
        }
    }

    private void studentsOnCloud2Update() {
        for (int index : update.studentsOnCloud.keySet()) {
            CloudGUI cloud = new CloudGUI(playersNumber, index);
            root.getChildren().add(cloud);
            for (int i = 1; i < update.studentsOnCloud.get(index).size(); i = i + 2) {
                StudentGUI student = new StudentGUI(update.studentsOnCloud.get(index).get(i));
                cloud.getChildren().add(student);
                student.setLayoutX(CoordinatesData.getClouds2().get(i / 2).getX());
                student.setLayoutY(CoordinatesData.getClouds2().get(i / 2).getY());
            }
            cloudStyle(cloud);
        }
    }

    /**
     * Sets the style of a given cloud. Creates a button associated with the cloud that shows up when
     * the cloud is pressed, that when pressed tries to perform the cloudToGate action request.
     */
    private void cloudStyle(CloudGUI cloud) {
        cloud.setOnMousePressed(this::onCloudPressed);
        cloud.setOnMouseExited(this::onCloudExited);
        Button button = new Button("Get Students!");
        button.setFont(Font.font("papyrus", 16));
        button.setStyle("-fx-background-color: rgba(160, 40, 236, 0.7); -fx-background-radius: 100");
        button.setLayoutX(49);
        button.setLayoutY(138);
        button.setDisable(true);
        button.setVisible(false);
        button.setOnMousePressed((e) -> onCloudButtonPressed());
        cloud.getChildren().add(button);
        cloud.setButton(button);
    }

    private void studentsOnCloud3Update() {
        for (int index : update.studentsOnCloud.keySet()) {
            CloudGUI cloud = new CloudGUI(playersNumber, index);
            root.getChildren().add(cloud);
            for (int i = 1; i < update.studentsOnCloud.get(index).size(); i = i + 2) {
                StudentGUI student = new StudentGUI(update.studentsOnCloud.get(index).get(i));
                cloud.getChildren().add(student);
                student.setLayoutX(CoordinatesData.getClouds3().get(i / 2).getX());
                student.setLayoutY(CoordinatesData.getClouds3().get(i / 2).getY());
            }
            cloudStyle(cloud);
        }
    }

    private void professorsUpdate() {
        redProfessor.setVisible(update.professors.get(userIndex()).get(0));
        blueProfessor.setVisible(update.professors.get(userIndex()).get(1));
        greenProfessor.setVisible(update.professors.get(userIndex()).get(2));
        yellowProfessor.setVisible(update.professors.get(userIndex()).get(3));
        pinkProfessor.setVisible(update.professors.get(userIndex()).get(4));
    }

    /**
     * Creates and sets the Panes and the Students of the Hall.
     */
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

    /**
     * Utility method used to build an array given the parameters.
     */
    private void arrayBuild(ArrayList<Pane> array, Pane element1, Pane element2, Pane element3, Pane element4, Pane element5, Pane element6, Pane element7, Pane element8, Pane element9, Pane element10) {
        HandController.arrayBuild(array, element1, element2, element3, element4, element5, element6, element7, element8, element9, element10);
    }

    /**
     * Drag event method that handles the drop of a GUI object on an IslandGUI. If the object is
     * a MotherNatureGUI instance, calculates the distance that the token traveled and performs the
     * moveMotherNature action request. Otherwise, it has to be a StudentGUI, so in that case
     * retrieves the index of the student in the gate and performs the gateToIsland action request.
     * @param event DragEvent object.
     * @param i The Island on which the event happened.
     */
    private void onDragOnIsland(DragEvent event, IslandGUI i) {
        if (event.getDragboard().getString().equals("MotherNature")) {
            ArrayList<Integer> par = new ArrayList<>();
            par.add(i.getIndex() + (i.getIndex() < (update.motherNatureOnIsland.indexOf(true) + 1) ? update.numIslands : 0) - (update.motherNatureOnIsland.indexOf(true) + 1));
            gui.perform(par, null, null, 3);
        } else {
            ArrayList<Integer> par = new ArrayList<>();
            par.add(CoordinatesData.getIndex(selectedStudent.getCoord()));
            par.add(i.getIndex());
            gui.perform(par, null, null, 0);
        }
        event.setDropCompleted(true);
        event.consume();
    }

    /**
     * Method called when a drag over an IslandGUI object is detected.
     * Prepares the object to a drop.
     */
    private void onDragIslandOver(DragEvent e) {
        if (e.getDragboard().hasString()) {
            e.acceptTransferModes(TransferMode.ANY);
            e.consume();
        }
    }

    /**
     * Method called when a drag over the Hall (a FXML Pane) is detected.
     * Prepares the Hall to a drop.
     */
    @FXML
    private void onDragHallOver(DragEvent e) {
        if (e.getDragboard().hasString()) {
            e.acceptTransferModes(TransferMode.ANY);
            e.consume();
        }
    }

    /**
     * Method called when a StudentGUI object is pressed. Sets it up for a drag if the game logic permits it.
     * @param s The selected student.
     */
    private void onStudentPressed(MouseEvent mouseEvent, StudentGUI s) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0))) {
            s.pressed(mouseEvent.getX(), mouseEvent.getY());
            selectedStudent = s;
            s.getParent().toFront();
        }
    }

    /**
     * Handles the drag event of a StudentGUI object. Saves the necessary parameters and starts the event.
     */
    private void studentDragHandling(MouseEvent e) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0))) {
            Dragboard db = selectedStudent.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cp = new ClipboardContent();
            cp.putString(selectedStudent.getColor());
            db.setContent(cp);
            e.consume();
        }
    }

    /**
     * Method called when a StudentGUI object is dragged. Sets it up for a drag if the game logic permits it.
     * @param s The selected student.
     */
    private void onStudentDragged(MouseEvent mouseEvent, StudentGUI s) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0))) {
            s.dragged(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    /**
     * Method called when a MotherNatureGUI object is pressed. Sets it up for a drag if the game logic permits it.
     * @param m The selected token.
     */
    private void onMotherNaturePressed(MouseEvent mouseEvent, MotherNatureGUI m) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0)) && update.cloudtaken) {
            m.pressed(mouseEvent.getSceneX(), mouseEvent.getSceneY());
            m.getParent().toFront();
        }
    }

    /**
     * Handles the drag event of a MotherNatureGUI object. Saves the necessary parameters and starts the event.
     */
    private void MNDragHandling(MouseEvent e) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0)) && update.cloudtaken) {
            Dragboard db = motherNature.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cp = new ClipboardContent();
            cp.putString("MotherNature");
            db.setContent(cp);
            e.consume();
        }
    }

    /**
     * Method called when a MotherNatureGUI object is pressed. Sets it up for a drag if the game logic permits it.
     * @param s The selected token.
     */
    private void onMotherNatureDragged(MouseEvent mouseEvent, MotherNatureGUI s) {
        if (update.phase.equals("Action") && userNickname.equals(update.order.get(0)) && update.cloudtaken) {
            s.dragged(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    /**
     * Method called when a drop is detected over the Hall FXML pane. If it's a StudentGUI object,
     * retrieves the color of the student and performs a gateToHall action request.
     */
    @FXML
    private void onDragOnHall(DragEvent event) {
        if (event.getDragboard().hasString()) {
            ArrayList<String> par = new ArrayList<>();
            par.add(selectedStudent.getColor());
            gui.perform(null, par, null, 1);
            event.setDropCompleted(true);
            event.consume();
        }
    }

    /**
     * Method called when a cloud is pressed. Sets its button as visible and enables it.
     */
    private void onCloudPressed(MouseEvent e) {
        if (update.playersMoves.get(0) == 0) {
            selectedCloud = (CloudGUI) e.getSource();
            selectedCloud.button.setVisible(true);
            selectedCloud.button.setDisable(false);
            selectedCloud.button.setCursor(Cursor.HAND);
            selectedCloud.toFront();
        }
    }

    /**
     * Method called when the mouse exits the CloudGUI pane. Disables its button.
     */
    public void onCloudExited(MouseEvent e) {
        if (selectedCloud != null) {
            selectedCloud.button.setVisible(false);
            selectedCloud.button.setDisable(true);
        }
    }

    /**
     * Method called when a CloudGUI button is pressed. Performs a cloudToGate action request.
     */
    private void onCloudButtonPressed() {
        ArrayList<Integer> par = new ArrayList<>();
        par.add(selectedCloud.getIndex());
        gui.perform(par, null, null, 2);
    }

    public int userIndex() {
        return update.players.indexOf(userNickname);
    }

    /**
     * Method associated with an FXML button. Sets the scene to the hall information of one of the opponents.
     */
    @FXML
    public void seePlayer1Hall() {
        gui.setPlayerNickname(player1Nickname);
        gui.setScene("fxml/PlayersBoard.fxml");
    }

    /**
     * Method associated with an FXML button. Sets the scene to the hall information of one of the opponents.
     */
    @FXML
    public void seePlayer2Hall() {
        gui.setPlayerNickname(player2Nickname);
        gui.setScene("fxml/PlayersBoard.fxml");
    }

    /**
     * Method called when an update is received. Updates the log.
     */
    private void setUpdateText() {
        updateMessageLog.setText(gui.updateLog);
    }

    /**
     * Sets and updates the log of messages received from the server in the UpdateMessage objects.
     * Shows also system messages associated with the game logic.
     */
    private void setLogMessage() {

        String currentPhase = update.phase;
        if (currentPhase.equals("Planning"))
            introducePhase(0);
        else
            introducePhase(1);

        logMessage1.setText("Players that have to play, in order: " + update.order);
        if (update.phase.equals("Planning")) {    //Planning Phase
            if (update.order.get(0).equals(userNickname)) {             //Player's turn
                introduceTurn(0);
            } else {                                            //not Player's turn
                introduceTurn(1);
            }
        } else {                                                     //Action Phase
            if (update.order.get(0).equals(userNickname) && update.playersMoves.get(0) != 0) {  //Player's turn, student managing
                introduceTurn(3);
            } else if (update.order.get(0).equals(userNickname) && !update.cloudtaken) {                   //Player's turn, cloud sub-phase
                introduceTurn(4);
            } else if (update.order.get(0).equals(userNickname)) {                              //Player's turn, MN sub-phase
                introduceTurn(5);
            } else {                                                                     //not Player's turn
                if (!userNickname.equals(update.order.get(0))) {
                    introduceTurn(2);
                }
            }
        }
    }

    private void introducePhase(int phase) {
        switch (phase) {
            case 0:
                logMessage2.setText(" Turn " + update.turnNumber + "!" + " Planning Time!" +
                        " Now's your chance to, you know, plan." +
                        " You should all play a card. The best stuff happens later.");
                break;
            case 1:
                logMessage2.setText(" Turn " + update.turnNumber + "!" + " Action time!" +
                        " This is the big league. Now is when the game is decided. Every round. Let's go!" +
                        " Move students. Activate special effects. Move digital imaginary tokens. Your call.");
        }
    }

    private void introduceTurn(int turn) {
        switch (turn) {
            case 0://Player's turn, Planning phase
                logMessage3.setText(" " + "Now! Fire your card!" +
                        " Remember, you can't play a card that has already been played this round. Just don't.");
                break;
            case 1://Planning phase, not player's turn
                logMessage3.setText(" It's not your time to play a card. Hold...");
                break;
            case 2://Action phase, not player's turn
                logMessage3.setText(" " + "Not your time to shine yet, somebody else is playing." +
                        " Be ready for when your turn comes.");
                break;
            case 3://Action phase, player's turn
                logMessage3.setText(" Your turn!");
                break;
            case 4://End of action phase, player's turn, cloud sub-phase
                logMessage3.setText(" You moved your students, you get new ones. That's how it works. Go get some from a cloud!");
                break;
            case 5://End of action phase, player's turn, MN movement
                logMessage3.setText(" " + "OK! Good student managing. Now let's end this round. " +
                        " Time to politely ask Lady Mother Nature to relocate on an Island of your choosing.");
                break;
        }
    }

    /**
     * Method associated with an FXML button. Sets the scene to the player's hand information scene.
     */
    @FXML
    public void SeeYourHand() {
        gui.setScene("fxml/Hand.fxml");
    }

    /**
     * Method associated with an FXML button. Sets the scene to the Characters' information scene.
     */
    @FXML
    public void SeeCharacter() {
        gui.setScene("fxml/Characters.fxml");
    }

}
