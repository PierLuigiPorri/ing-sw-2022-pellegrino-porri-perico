package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.CLIENT.GUIobjects.*;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * FXML Controller class for the Characters Parameters selection scene. Shows a stripped down version
 * of the board to the player, in order to collect specific user inputs based on what Character effect has been
 * activated. Once the input is collected, performs a activateCharacter request to the GUIAPP class.
 * @author GC56
 */
public class CharacterParametersController {

    @FXML
    private Pane selectionPane;
    @FXML
    private Text desc;
    @FXML
    private Button activate;
    private Button confirmMax;
    private TextField max;
    private GUIAPP gui;
    private UpdateMessage update;
    private int playersNumber;
    private String userNickname;
    private StudentGUI selectedStudent = null;
    private final ArrayList<StudentGUI> selectedStudents1 = new ArrayList<>();
    private final ArrayList<StudentGUI> selectedStudents2 = new ArrayList<>();
    private String selectedColor = null;
    private String selectedColor2 = null;
    private IslandGUI selectedIsland = null;
    private final ArrayList<StudentGUI> studentsOnCard = new ArrayList<>();
    private final ArrayList<StudentGUI> studentsOnGate = new ArrayList<>();

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
    private Pane root;
    private ToggleGroup group;
    private int index;
    private int maxSwappable;
    private Text selectedText;

    public void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    /**
     * Main method of the View. Builds and shows to the user all the appropriate GUI objects and the FXML scene.
     * Called by the GUIAPP when the scene is set. The switch-case statement builds a different scene
     * based on the Character which has been activated.
     *
     * @param selection The index of the activated Character Card. The scene shown to the user
     *                  is different for each Character.
     */
    public void refresh(int selection) {
        this.index = selection;
        update = gui.getUpdate();
        this.userNickname = gui.getUserNickname();
        this.playersNumber = update.players.size();
        CoordinatesData.loadCoordinates();
        createIslands();
        studentsOnGateUpdate();

        towersUpdate();
        motherNatureUpdate();
        studentsOnIslandUpdate();


        if (playersNumber == 2) {
            studentsOnCloud2Update();
        } else {
            studentsOnCloud3Update();
        }
        professorsUpdate();
        hallUpdate();
        switch (selection) {
            case 0: //Student AND Island selection
                desc.setText("CHOOSE A STUDENT AND AN ISLAND!");
                for (IslandGUI i : islands) {
                    i.setOnMousePressed(this::onIslandSelected);
                }
                selectedText = new Text();
                selectedText.setFont(Font.font("papyrus", 16));
                LinearGradient paint = new LinearGradient(
                        0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.0, 0.0, 0.0, 1.0)),
                        new Stop(1.0, new Color(1.0, 0.0, 0.0, 1.0)));
                selectedText.setFill(paint);
                selectedText.setLayoutX(373);
                selectedText.setLayoutY(130);
                selectionPane.getChildren().add(selectedText);
                Pane p = new Pane();
                p.setPrefWidth(159);
                p.setPrefHeight(240);
                p.setLayoutX(103);
                p.setLayoutY(40);
                ImageView imageView = new ImageView();
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front0.jpg"));
                imageView.setFitWidth(159);
                imageView.setFitHeight(240);
                p.getChildren().add(imageView);
                CharacterGUI studs = new CharacterGUI(4);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(index)).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(index)).get(i));
                    student.setOnMousePressed(this::studentPressed);
                    student.setLayoutX(CoordinatesData.getCardStudents(4).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(4).get(i / 2).getY());
                    studentsOnCard.add(student);
                    studs.getChildren().add(student);
                }
                p.getChildren().add(studs);
                selectionPane.getChildren().add(p);
                activate.setOnMousePressed(this::studentIslandConfirmed);
                break;
            case 2: //Island selection
            case 4:
                desc.setText("CHOOSE AN ISLAND!");
                selectedText = new Text();
                selectedText.setFont(Font.font("papyrus", 16));
                paint = new LinearGradient(
                        0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.0, 0.0, 0.0, 1.0)),
                        new Stop(1.0, new Color(1.0, 0.0, 0.0, 1.0)));
                selectedText.setFill(paint);
                selectedText.setLayoutY(130);
                selectedText.setLayoutX(245);
                selectionPane.getChildren().add(selectedText);
                for (IslandGUI i : islands) {
                    i.setOnMousePressed(this::onIslandSelected);
                }
                activate.setOnMousePressed(this::islandConfirmed);
                break;
            case 6: //Student swapping, case 1
                desc.setText("CHOOSE HOW MANY STUDENTS TO SWAP!");
                max = new TextField();
                max.setPromptText("How many?");
                max.setLayoutX(404);
                max.setLayoutY(126);
                confirmMax = new Button("NEXT!");
                confirmMax.setLayoutY(126);
                confirmMax.setLayoutX(555);
                confirmMax.setPrefWidth(140);
                confirmMax.setPrefHeight(40);
                confirmMax.setStyle("-fx-background-radius:100; -fx-background-color: cyan");
                confirmMax.setFont(Font.font("papyrus", 14));
                confirmMax.setOnMousePressed(this::confirmMaxPressed);
                selectionPane.getChildren().add(max);
                selectionPane.getChildren().add(confirmMax);
                p = new Pane();
                p.setPrefWidth(159);
                p.setPrefHeight(240);
                p.setLayoutX(103);
                p.setLayoutY(40);
                imageView = new ImageView();
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front6.jpg"));
                imageView.setFitWidth(159);
                imageView.setFitHeight(240);
                p.getChildren().add(imageView);
                studs = new CharacterGUI(6);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(index)).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(index)).get(i));
                    student.setOnMousePressed(this::studentPressed);
                    student.setLayoutX(CoordinatesData.getCardStudents(6).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(6).get(i / 2).getY());
                    student.setDisable(true);
                    studentsOnCard.add(student);
                    studs.getChildren().add(student);
                }
                p.getChildren().add(studs);
                selectionPane.getChildren().add(p);
                activate.setOnMousePressed(this::gateCardSwapConfirmed);
                break;
            case 9: //Student swapping, case 2
                desc.setText("CHOOSE HOW MANY STUDENTS TO SWAP!");
                Text t = new Text("Choose here which colors to swap in the hall:");
                t.setFont(Font.font("papyrus", 14));
                max = new TextField();
                max.setLayoutX(404);
                max.setLayoutY(126);
                confirmMax = new Button("NEXT!");
                confirmMax.setLayoutY(126);
                confirmMax.setLayoutX(555);
                confirmMax.setPrefWidth(140);
                confirmMax.setPrefHeight(40);
                confirmMax.setStyle("-fx-background-radius:100; -fx-background-color: cyan");
                confirmMax.setFont(Font.font("papyrus", 14));
                confirmMax.setOnMousePressed(this::confirmMaxPressed);
                selectionPane.getChildren().add(max);
                selectionPane.getChildren().add(confirmMax);
                selectionPane.getChildren().add(t);
                activate.setOnMousePressed(this::gateHallSwapConfirmed);
                break;
            case 10: //Student selection
                desc.setText("CHOOSE A STUDENT ON THE CARD!");
                selectedText = new Text();
                selectedText.setFont(Font.font("papyrus", 16));
                paint = new LinearGradient(
                        0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.0, 0.0, 0.0, 1.0)),
                        new Stop(1.0, new Color(1.0, 0.0, 0.0, 1.0)));
                selectedText.setFill(paint);
                selectedText.setLayoutX(373);
                selectedText.setLayoutY(130);
                selectionPane.getChildren().add(selectedText);
                p = new Pane();
                p.setPrefWidth(159);
                p.setPrefHeight(240);
                p.setLayoutX(103);
                p.setLayoutY(40);
                imageView = new ImageView();
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front10.jpg"));
                imageView.setFitWidth(159);
                imageView.setFitHeight(240);
                p.getChildren().add(imageView);
                studs = new CharacterGUI(4);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(index)).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(index)).get(i));
                    student.setOnMousePressed(this::studentPressed);
                    student.setLayoutX(CoordinatesData.getCardStudents(4).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(4).get(i / 2).getY());
                    studentsOnCard.add(student);
                    studs.getChildren().add(student);
                }
                p.getChildren().add(studs);
                selectionPane.getChildren().add(p);
                activate.setOnMousePressed(this::studentConfirmed);
                break;
            case 8: //Color selection
            case 11:
                desc.setText("CHOOSE A COLOR!");
                ColorButton red = new ColorButton("RED");
                ColorButton blue = new ColorButton("BLUE");
                ColorButton yellow = new ColorButton("YELLOW");
                ColorButton green = new ColorButton("GREEN");
                ColorButton pink = new ColorButton("PINK");
                red.setLayoutX(CoordinatesData.getButtons("RED").getX());
                red.setLayoutY(CoordinatesData.getButtons("RED").getY());
                blue.setLayoutX(CoordinatesData.getButtons("BLUE").getX());
                blue.setLayoutY(CoordinatesData.getButtons("BLUE").getY());
                green.setLayoutX(CoordinatesData.getButtons("GREEN").getX());
                green.setLayoutY(CoordinatesData.getButtons("GREEN").getY());
                yellow.setLayoutX(CoordinatesData.getButtons("YELLOW").getX());
                yellow.setLayoutY(CoordinatesData.getButtons("YELLOW").getY());
                pink.setLayoutX(CoordinatesData.getButtons("PINK").getX());
                pink.setLayoutY(CoordinatesData.getButtons("PINK").getY());
                group = new ToggleGroup();
                red.setToggleGroup(group);
                blue.setToggleGroup(group);
                green.setToggleGroup(group);
                yellow.setToggleGroup(group);
                pink.setToggleGroup(group);
                red.setOnMousePressed(this::colorHighlight);
                blue.setOnMousePressed(this::colorHighlight);
                green.setOnMousePressed(this::colorHighlight);
                yellow.setOnMousePressed(this::colorHighlight);
                pink.setOnMousePressed(this::colorHighlight);
                selectionPane.getChildren().add(red);
                selectionPane.getChildren().add(blue);
                selectionPane.getChildren().add(green);
                selectionPane.getChildren().add(yellow);
                selectionPane.getChildren().add(pink);
                activate.setOnMousePressed(this::colorConfirmed);
                break;
            default:
                break;
        }
    }

    /**
     * Sets the towers on the board based off the last update received. Called by
     * the refresh() method.
     */
    private void towersUpdate() {
        for (int i = 0; i < update.numIslands; i++) {
            String nick = update.whoOwnTowers.get(i);

            if (nick.equals(update.players.get(0))) {
                setTowerOnIsland(i, "WHITE");
            } else if (nick.equals(update.players.get(1))) {
                setTowerOnIsland(i, "BLACK");
            } else if (update.players.size() == 3 && nick.equals(update.players.get(2))) {
                setTowerOnIsland(i, "GREY");
            }
        }
    }

    /**
     * Sets a tower on a specific Island. If the island has no tower on it, a TowerGUI object is created
     * and put on it with the right coordinates. Otherwise, a TowerCountPane object is also created and set
     * next to the existing tower, with the right tower count, and the island is set as SuperIsland.
     */
    private void setTowerOnIsland(int index, String color) {
        if (update.towersOnIsland.get(index) != 0) {
            TowerGUI tower = new TowerGUI(color);
            islands.get(index).getChildren().add(tower);
            tower.setLayoutY(CoordinatesData.getTowersCoordinates().getY());
            tower.setLayoutX(CoordinatesData.getTowersCoordinates().getX());
            if (update.towersOnIsland.get(index) > 1) {
                islands.get(index).getChildren().add(new TowerCountPane(update.towersOnIsland.get(index)));
            }
        }
    }

    /**
     * Sets the MotherNatureGUI object on the right Island. Also checks if any island has a
     * Prohibition counter on it, and handles the affermative case.
     */
    private void motherNatureUpdate() {
        MotherNatureGUI motherNature = new MotherNatureGUI();
        for (int i = 0; i < update.numIslands; i++) {
            if (update.motherNatureOnIsland.get(i)) {
                islands.get(i).getChildren().add(motherNature);
                motherNature.setLayoutX(CoordinatesData.getMotherNatureCoordinates().getX());
                motherNature.setLayoutY(CoordinatesData.getMotherNatureCoordinates().getY());
            }
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
            islands.add(new IslandGUI(index));
            root.getChildren().add(islands.get(index - 1));
            islands.get(index - 1).setLayoutX(CoordinatesData.getIslandsCoord(update.numIslands).get(index - 1).getX());
            islands.get(index - 1).setLayoutY(CoordinatesData.getIslandsCoord(update.numIslands).get(index - 1).getY());
        }

    }

    private void studentsOnGateUpdate() {
        for (int i = 1; i < update.gatePlayer.get(userIndex()).size(); i = i + 2) {
            StudentGUI student = new StudentGUI(update.gatePlayer.get(userIndex()).get(i));
            studentsOnGate.add(student);
            gate.getChildren().add(student);
            student.setLayoutX(CoordinatesData.getGate().get(i / 2).getX());
            student.setLayoutY(CoordinatesData.getGate().get(i / 2).getY());
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


    public int userIndex() {
        return update.players.indexOf(userNickname);
    }

    /**
     * Method called when a ColorButton object is pressed. Sets as selected and highlights every StudentGUI objects
     * of the color of the pressed ColorButton on the gate and on every Island.
     */
    private void colorHighlight(MouseEvent e) {
        if (e.getSource().equals(group.getSelectedToggle())) {
            activate.setDisable(true);
            for (IslandGUI i : islands) {
                for (Node s : i.getChildren()) {
                    if (s instanceof StudentGUI) {
                        ((StudentGUI) s).deselect();
                    }
                }
            }
            for (Node s : gate.getChildren()) {
                if (s instanceof StudentGUI) {
                    ((StudentGUI) s).deselect();
                }
            }
        } else {
            for (IslandGUI i : islands) {
                for (Node s : i.getChildren()) {
                    if (s instanceof StudentGUI) {
                        if (((StudentGUI) s).getColor().equals(((ColorButton) e.getSource()).color)) {
                            ((StudentGUI) s).setSelected();
                        } else
                            ((StudentGUI) s).deselect();
                    }
                }
            }
            for (Node s : gate.getChildren()) {
                if (s instanceof StudentGUI) {
                    if (((StudentGUI) s).getColor().equals(((ColorButton) e.getSource()).color)) {
                        ((StudentGUI) s).setSelected();
                    } else
                        ((StudentGUI) s).deselect();
                }
            }
            selectedColor = ((ColorButton) e.getSource()).color;
            this.activate.setDisable(false);
        }
    }

    /**
     * Method called when a selection of a Color parameter is confirmed by pressing the confirm button.
     * Collects the parameters, builds the parameter ArrayLists and performs the activateCharacter action request.
     */
    private void colorConfirmed(MouseEvent e) {
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        b.add(selectedColor);
        for (IslandGUI i : islands) {
            for (Node s : i.getChildren()) {
                if (s instanceof StudentGUI) {
                    ((StudentGUI) s).deselect();
                }
            }
        }
        for (Node s : gate.getChildren()) {
            if (s instanceof StudentGUI) {
                ((StudentGUI) s).deselect();
            }
        }
        gui.perform(a, b, null, 5);
    }

    /**
     * Method called when an Island is selected as a parameter by pressing on it. Highlights the island and saves
     * it as a parameter.
     */
    private void onIslandSelected(MouseEvent e) {
        if (selectedIsland != null) {
            selectedIsland.deselect();
        }
        ((IslandGUI) e.getSource()).setSelected();
        selectedIsland = ((IslandGUI) e.getSource());
        selectedText.setText("SELECTED ISLAND " + selectedIsland.getIndex() + "!");
        if (index == 0) {
            if (selectedStudent != null && selectedIsland != null)
                activate.setDisable(false);
        } else
            activate.setDisable(false);
    }

    /**
     * Method called when a selection of an Island as a parameter is confirmed by pressing the confirm button.
     * Collects the parameters, builds the parameter ArrayLists and performs the activateCharacter action request.
     */
    private void islandConfirmed(MouseEvent e) {
        selectedText.setText("CONFIRMED!");
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        a.add(selectedIsland.getIndex());
        selectedIsland.deselect();
        gui.perform(a, b, null, 5);
    }

    /**
     * Method called when a student is selected by pressing on it. Highlights the student and sets it as selected,
     * then saves it as a parameter in different data structures depending on the index of the activated Card.
     */
    private void studentPressed(MouseEvent e) {
        if (index == 6) {
            if (selectedStudents1.contains((StudentGUI) e.getSource())) {
                selectedStudents1.remove(((StudentGUI) e.getSource()));
                ((StudentGUI) e.getSource()).deselect();
                activate.setDisable(true);
            } else {
                if (selectedStudents1.size() < maxSwappable) {
                    ((StudentGUI) e.getSource()).setSelected();
                    selectedStudents1.add(((StudentGUI) e.getSource()));
                }
                if (selectedStudents1.size() == maxSwappable && selectedStudents2.size() == maxSwappable) {
                    activate.setDisable(false);
                }
            }
        } else {
            if (selectedStudent != null) {
                selectedStudent.deselect();
                if (index == 0)
                    activate.setDisable(true);
            }
            ((StudentGUI) e.getSource()).setSelected();
            selectedStudent = (StudentGUI) e.getSource();
            selectedText.setText("SELECTED " + selectedStudent.getColor() + " STUDENT!");
            if (index == 0) {
                if (selectedStudent != null && selectedIsland != null)
                    activate.setDisable(false);
            } else
                activate.setDisable(false);
        }
    }

    /**
     * Method called when a student on the Gate is selected by pressing on it. Highlights the student and sets it as selected,
     * then saves it as a parameter in different data structures depending on the index of the activated Card.
     */
    private void studentPressedOnGate(MouseEvent e) {
        StudentGUI sel = (StudentGUI) e.getSource();
        if (index == 6) {
            if (selectedStudents2.contains(sel)) {
                selectedStudents2.remove(sel);
                sel.deselect();
                activate.setDisable(true);
            } else {
                if (selectedStudents2.size() < maxSwappable) {
                    sel.setSelected();
                    selectedStudents2.add(sel);
                }
                if (selectedStudents1.size() == maxSwappable && selectedStudents2.size() == maxSwappable) {
                    activate.setDisable(false);
                }
            }
        } else {
            if (selectedStudents1.contains(sel)) {
                selectedStudents1.remove(sel);
                sel.deselect();
                activate.setDisable(true);
            } else {
                if (selectedStudents1.size() < maxSwappable) {
                    sel.setSelected();
                    selectedStudents1.add(sel);
                }
                if ((maxSwappable == 1 && selectedColor != null && selectedStudents1.size() == maxSwappable) || (maxSwappable > 1 && selectedColor != null && selectedColor2 != null && selectedStudents1.size() == maxSwappable)) {
                    activate.setDisable(false);
                }
            }
        }
    }

    /**
     * Method called when a selection of a Student as a parameter is confirmed by pressing the confirm button.
     * Collects the parameters, builds the parameter ArrayLists and performs the activateCharacter action request.
     */
    private void studentConfirmed(MouseEvent e) {
        selectedText.setText("CONFIRMED!");
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        a.add(studentsOnCard.indexOf(selectedStudent));
        selectedStudent.deselect();
        gui.perform(a, b, null, 5);
    }

    /**
     * Method called when a selection of a Student and an Island as parameters is confirmed by pressing the confirm button.
     * Collects the parameters, builds the parameter ArrayLists and performs the activateCharacter action request.
     */
    private void studentIslandConfirmed(MouseEvent e) {
        selectedText.setText("CONFIRMED!");
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        a.add(studentsOnCard.indexOf(selectedStudent));
        a.add(selectedIsland.getIndex());
        selectedIsland.deselect();
        selectedStudent.deselect();
        gui.perform(a, b, null, 5);
    }

    /**
     * Method called when the user confirms their choice of maximum number of students to swap by pressing on
     * the max confirm button. If the input is correct, sets the scene to accomodate the right user inputs for
     * the selection of the parameters, depending on the index of the activated card.
     */
    private void confirmMaxPressed(MouseEvent e) {
        if (max.getText().equals("1") || max.getText().equals("2") || (max.getText().equals("3") && index == 6)) {
            maxSwappable = Integer.parseInt(max.getText());
            max.setDisable(true);
            confirmMax.setText("CONFIRMED!");
            confirmMax.setDisable(true);
            desc.setText("CHOOSE THE STUDENTS!");
            if (index == 6) {
                for (StudentGUI s : studentsOnCard) {
                    s.setDisable(false);
                }
            } else {
                selectionPane.getChildren().remove(confirmMax);
                selectionPane.getChildren().remove(max);
                ColorButton red = new ColorButton("RED");
                ColorButton blue = new ColorButton("BLUE");
                ColorButton yellow = new ColorButton("YELLOW");
                ColorButton green = new ColorButton("GREEN");
                ColorButton pink = new ColorButton("PINK");
                red.setLayoutX(120);
                red.setLayoutY(126);
                blue.setLayoutX(268);
                blue.setLayoutY(126);
                green.setLayoutX(430);
                green.setLayoutY(126);
                yellow.setLayoutX(592);
                yellow.setLayoutY((126));
                pink.setLayoutX(764);
                pink.setLayoutY(126);
                group = new ToggleGroup();
                red.setToggleGroup(group);
                blue.setToggleGroup(group);
                green.setToggleGroup(group);
                yellow.setToggleGroup(group);
                pink.setToggleGroup(group);
                red.setOnMousePressed(this::colorSelection1);
                blue.setOnMousePressed(this::colorSelection1);
                green.setOnMousePressed(this::colorSelection1);
                yellow.setOnMousePressed(this::colorSelection1);
                pink.setOnMousePressed(this::colorSelection1);
                selectionPane.getChildren().add(red);
                selectionPane.getChildren().add(blue);
                selectionPane.getChildren().add(green);
                selectionPane.getChildren().add(yellow);
                selectionPane.getChildren().add(pink);
                if (maxSwappable == 2) {
                    ColorButton red2 = new ColorButton("RED");
                    ColorButton blue2 = new ColorButton("BLUE");
                    ColorButton yellow2 = new ColorButton("YELLOW");
                    ColorButton green2 = new ColorButton("GREEN");
                    ColorButton pink2 = new ColorButton("PINK");
                    red2.setLayoutX(120);
                    red2.setLayoutY(183);
                    blue2.setLayoutX(268);
                    blue2.setLayoutY(183);
                    green2.setLayoutX(430);
                    green2.setLayoutY(183);
                    yellow2.setLayoutX(592);
                    yellow2.setLayoutY((183));
                    pink2.setLayoutX(764);
                    pink2.setLayoutY(183);
                    ToggleGroup group2 = new ToggleGroup();
                    red2.setToggleGroup(group2);
                    blue2.setToggleGroup(group2);
                    green2.setToggleGroup(group2);
                    yellow2.setToggleGroup(group2);
                    pink2.setToggleGroup(group2);
                    red2.setOnMousePressed(this::colorSelection2);
                    blue2.setOnMousePressed(this::colorSelection2);
                    green2.setOnMousePressed(this::colorSelection2);
                    yellow2.setOnMousePressed(this::colorSelection2);
                    pink2.setOnMousePressed(this::colorSelection2);
                    selectionPane.getChildren().add(red2);
                    selectionPane.getChildren().add(blue2);
                    selectionPane.getChildren().add(green2);
                    selectionPane.getChildren().add(yellow2);
                    selectionPane.getChildren().add(pink2);
                }
            }
            for (StudentGUI j : studentsOnGate) {
                j.setOnMousePressed(this::studentPressedOnGate);
            }
        } else {
            if (index == 6)
                desc.setText("COME ON. A NUMBER UP TO 3.");
            else
                desc.setText("COME ON. A NUMBER UP TO 2.");
            confirmMax.setText("RETRY!");
        }
    }

    /**
     * Method called when the selection of the parameters for a Student swap with the Card is confirmed by pressing the confirm button.
     * Collects the parameters, builds the parameter ArrayLists and performs the activateCharacter action request.
     */
    private void gateCardSwapConfirmed(MouseEvent e) {
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>(), c;
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        for (StudentGUI s : selectedStudents1) {
            a.add(studentsOnCard.indexOf(s));
            s.deselect();
        }
        c = new ArrayList<>();
        for (StudentGUI k : selectedStudents2) {
            c.add(studentsOnGate.indexOf(k));
            k.deselect();
        }
        gui.perform(a, b, c, 5);
    }

    private void colorSelection1(MouseEvent e) {
        selectedColor = ((ColorButton) e.getSource()).color;
        if ((maxSwappable == 1 && selectedColor != null && selectedStudents1.size() == maxSwappable) || (maxSwappable > 1 && selectedColor != null && selectedColor2 != null && selectedStudents1.size() == maxSwappable)) {
            activate.setDisable(false);
        }
    }

    private void colorSelection2(MouseEvent e) {
        selectedColor2 = ((ColorButton) e.getSource()).color;
        if (selectedColor != null && selectedColor2 != null && selectedStudents1.size() == maxSwappable) {
            activate.setDisable(false);
        }
    }

    /**
     * Method called when the selection of the parameters for a Student swap with the Hall is confirmed by pressing the confirm button.
     * Collects the parameters, builds the parameter ArrayLists and performs the activateCharacter action request.
     */
    private void gateHallSwapConfirmed(MouseEvent e) {
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        for (StudentGUI s : selectedStudents1) {
            a.add(studentsOnGate.indexOf(s));
            s.deselect();
        }
        b.add(selectedColor);
        if (selectedColor2 != null)
            b.add(selectedColor2);
        gui.perform(a, b, null, 5);
    }


}

