package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.CLIENT.GUIobjects.*;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.ArrayList;

public class CharacterParametersController {

    @FXML
    private Pane selectionPane;
    @FXML
    private Text desc;
    @FXML
    private Button activate;
    private GUIAPP gui;
    private UpdateMessage update;
    private int playersNumber;
    private int gameType;
    private String userNickname;
    private MotherNatureGUI motherNature;
    private StudentGUI selectedStudent=null;
    private CloudGUI selectedCloud;
    private String selectedColor;
    private IslandGUI selectedIsland=null;
    private ArrayList<StudentGUI> studentsOnCard=new ArrayList<>();

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
    private Text selectedText;

    public void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    public void refresh(int selection) {
        this.index = selection;
        update = gui.getUpdate();
        System.out.println(update.players);
        this.userNickname = gui.getUserNickname();
        this.playersNumber = update.players.size();
        this.gameType = update.game_Type;
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
                for(IslandGUI i:islands){
                    i.setOnMousePressed(this::onIslandSelected);
                }
                selectedText=new Text();
                selectedText.setFont(Font.font("papyrus",16));
                LinearGradient paint = new LinearGradient(
                        0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.0, 0.0, 0.0, 1.0)),
                        new Stop(1.0, new Color(1.0, 0.0, 0.0, 1.0)));
                selectedText.setFill(paint);
                selectedText.setLayoutX(373);
                selectedText.setLayoutY(130);
                selectionPane.getChildren().add(selectedText);
                Pane p=new Pane();
                p.setPrefWidth(159);
                p.setPrefHeight(240);
                p.setLayoutX(103);
                p.setLayoutY(40);
                ImageView imageView=new ImageView();
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front0.jpg"));
                imageView.setCursor(Cursor.HAND);
                p.getChildren().add(imageView);
                CharacterGUI studs=new CharacterGUI(4);
                for(int i=1; i<update.studentsOnCard.get(update.idCharacter.indexOf(10)).size(); i+=2){
                    StudentGUI student=new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(10)).get(i));
                    student.setOnMousePressed(this::studentPressed);
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
                selectedText=new Text();
                selectedText.setFont(Font.font("papyrus",16));
                paint = new LinearGradient(
                        0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.0, 0.0, 0.0, 1.0)),
                        new Stop(1.0, new Color(1.0, 0.0, 0.0, 1.0)));
                selectedText.setFill(paint);
                selectedText.setLayoutY(130);
                selectedText.setLayoutX(245);
                selectionPane.getChildren().add(selectedText);
                for(IslandGUI i:islands){
                    i.setOnMousePressed(this::onIslandSelected);
                }
                activate.setOnMousePressed(this::islandConfirmed);
                break;
            case 6: //Student swapping, case 1
                break;
            case 9: //Student swapping, case 2
                break;
            case 10: //Student selection
                desc.setText("CHOOSE A STUDENT ON THE CARD!");
                selectedText=new Text();
                selectedText.setFont(Font.font("papyrus",16));
                paint = new LinearGradient(
                        0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        new Stop(0.0, new Color(0.0, 0.0, 0.0, 1.0)),
                        new Stop(1.0, new Color(1.0, 0.0, 0.0, 1.0)));
                selectedText.setFill(paint);
                selectedText.setLayoutX(373);
                selectedText.setLayoutY(130);
                selectionPane.getChildren().add(selectedText);
                p=new Pane();
                p.setPrefWidth(159);
                p.setPrefHeight(240);
                p.setLayoutX(103);
                p.setLayoutY(40);
                 imageView=new ImageView();
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front10.jpg"));
                imageView.setCursor(Cursor.HAND);
                p.getChildren().add(imageView);
                studs=new CharacterGUI(4);
                for(int i=1; i<update.studentsOnCard.get(update.idCharacter.indexOf(10)).size(); i+=2){
                    StudentGUI student=new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(10)).get(i));
                    student.setOnMousePressed(this::studentPressed);
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

    private void motherNatureUpdate() {
        motherNature = new MotherNatureGUI();
        for (int i = 0; i < update.numIslands; i++) {
            if (update.motherNatureOnIsland.get(i)) {
                islands.get(i).getChildren().add(motherNature);
                motherNature.setLayoutX(CoordinatesData.getMotherNatureCoordinates().getX());
                motherNature.setLayoutY(CoordinatesData.getMotherNatureCoordinates().getY());
            }
        }
    }

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
                    islands.get(index - 1).getChildren().add(new CountPane("RED", islands.get(index).getRed()));
                }
            }
            if (islands.get(index - 1).getBlue() > 0) {
                StudentGUI student = new StudentGUI("BLUE");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("BLUE").getX());
                student.setLayoutY(CoordinatesData.getIsland("BLUE").getY());
                if (islands.get(index - 1).getBlue() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("BLUE", islands.get(index).getBlue()));
                }
            }
            if (islands.get(index - 1).getGreen() > 0) {
                StudentGUI student = new StudentGUI("GREEN");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("GREEN").getX());
                student.setLayoutY(CoordinatesData.getIsland("GREEN").getY());
                if (islands.get(index - 1).getGreen() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("GREEN", islands.get(index).getGreen()));
                }
            }
            if (islands.get(index - 1).getYellow() > 0) {
                StudentGUI student = new StudentGUI("YELLOW");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("YELLOW").getX());
                student.setLayoutY(CoordinatesData.getIsland("YELLOW").getY());
                if (islands.get(index - 1).getYellow() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("YELLOW", islands.get(index).getYellow()));
                }
            }
            if (islands.get(index - 1).getPink() > 0) {
                StudentGUI student = new StudentGUI("PINK");
                islands.get(index - 1).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland("PINK").getX());
                student.setLayoutY(CoordinatesData.getIsland("PINK").getY());
                if (islands.get(index - 1).getPink() > 1) {
                    islands.get(index - 1).getChildren().add(new CountPane("PINK", islands.get(index).getPink()));
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


    public int userIndex() {
        System.out.println(userNickname);
        return update.players.indexOf(userNickname);
    }

    private void colorHighlight(MouseEvent e) {
        if(e.getSource().equals(group.getSelectedToggle())){
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
        }else {
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
            this.activate.setDisable(false);
        }
    }

    private void colorConfirmed(MouseEvent e) {
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        b.add(((ColorButton) group.getSelectedToggle()).color);
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

    private void onIslandSelected(MouseEvent e){
        if(selectedIsland!=null){
            selectedIsland.deselect();
        }
        ((IslandGUI)e.getSource()).setSelected();
        selectedIsland=((IslandGUI) e.getSource());
        selectedText.setText("SELECTED ISLAND "+selectedIsland.getIndex()+"!");
        if(index==0){
            if(selectedStudent!=null&&selectedIsland!=null)
                activate.setDisable(false);
        }else
            activate.setDisable(false);
    }

    private void islandConfirmed(MouseEvent e){
        selectedText.setText("CONFIRMED!");
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        a.add(selectedIsland.getIndex());
        selectedIsland.deselect();
        gui.perform(a,b,null,5);
    }

    private void studentPressed(MouseEvent e){
        if(selectedStudent!=null){
            selectedStudent.deselect();
        }
        ((StudentGUI)e.getSource()).setSelected();
        selectedStudent= (StudentGUI) e.getSource();
        selectedText.setText("SELECTED "+selectedStudent.getColor()+" STUDENT!");
        if(index==0){
            if(selectedStudent!=null&&selectedIsland!=null)
                activate.setDisable(false);
        }else
            activate.setDisable(false);
    }

    private void studentConfirmed(MouseEvent e){
        selectedText.setText("CONFIRMED!");
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        a.add(studentsOnCard.indexOf(selectedStudent));
        selectedStudent.deselect();
        gui.perform(a,b,null,5);
    }

    private void studentIslandConfirmed(MouseEvent e){
        selectedText.setText("CONFIRMED!");
        activate.setDisable(true);
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add(index);
        a.add(studentsOnCard.indexOf(selectedStudent));
        a.add(selectedIsland.getIndex());
        selectedIsland.deselect();
        selectedStudent.deselect();
        gui.perform(a,b,null,5);
    }

}

