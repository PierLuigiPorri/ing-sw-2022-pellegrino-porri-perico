package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.CLIENT.GUIobjects.CloudGUI;
import it.polimi.ingsw.CLIENT.GUIobjects.CoordinatesData;
import it.polimi.ingsw.CLIENT.GUIobjects.StudentGUI;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class BoardController {

    private static GUIAPP gui;
    private UpdateMessage update;
    private int playersnum;

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
    private Pane island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12;
    @FXML
    private Pane gate;

    public static void setGUI(GUIAPP guiApp){
        gui=guiApp;
    }

    public void refresh(){
        update=gui.getUpdate();
        CoordinatesData.loadCoordinates();

        studentsOnIslandUpdate();
        studentsOnGateUpdate();

        if(playersnum==2)
            studentsOnCloud2Update();
        else
            studentsOnCloud3Update();

        professorsUpdate();
        hallUpdate();
    }

    private void studentsOnIslandUpdate(){
        ArrayList<Pane> islands=new ArrayList<>();
        arrayBuild(islands, island1, island2, island3, island4, island5, island6, island7, island8, island9, island10);
        islands.add(island11);
        islands.add(island12);

        for (int index:update.studentsOnIsland.keySet()) {
            for (int i=1; i<update.studentsOnIsland.get(index).size(); i=i+2){
                StudentGUI student=new StudentGUI(update.studentsOnIsland.get(index).get(i));
                islands.get(index).getChildren().add(student);
                student.setLayoutX(CoordinatesData.getIsland(update.studentsOnIsland.get(index).get(i)).getX());
                student.setLayoutY(CoordinatesData.getIsland(update.studentsOnIsland.get(index).get(i)).getY());
            }
        }
    }

    private void studentsOnGateUpdate(){
        for(int i=1; i<update.gatePlayer.get(0).size(); i=i+2){
            StudentGUI student=new StudentGUI(update.gatePlayer.get(0).get(i));
            gate.getChildren().add(student);
            student.setLayoutX(CoordinatesData.getGate().get(i/2).getX());
            student.setLayoutY(CoordinatesData.getGate().get(i/2).getY());
        }
    }

    private void studentsOnCloud2Update(){
        for (int index:update.studentsOnCloud.keySet()) {
            CloudGUI cloud=new CloudGUI(playersnum, index);
            for(int i=1; i<update.studentsOnCloud.get(index).size(); i=i+2){
                StudentGUI student=new StudentGUI(update.studentsOnCloud.get(index).get(i));
                cloud.getChildren().add(student);
                student.setLayoutX(CoordinatesData.getClouds2().get(i/2).getX());
                student.setLayoutY(CoordinatesData.getClouds2().get(i/2).getY());
            }
        }
    }

    private void studentsOnCloud3Update(){
        for (int index:update.studentsOnCloud.keySet()) {
            CloudGUI cloud=new CloudGUI(playersnum, index);
            for(int i=0; i<update.studentsOnCloud.get(index).size(); i=i+2){
                StudentGUI student=new StudentGUI(update.studentsOnCloud.get(index).get(i));
                cloud.getChildren().add(student);
                student.setLayoutX(CoordinatesData.getClouds3().get(i/2).getX());
                student.setLayoutY(CoordinatesData.getClouds3().get(i/2).getY());
            }
        }
    }

    private void professorsUpdate(){
        redProfessor.setVisible(update.professors.get(0).get(0));
        blueProfessor.setVisible(update.professors.get(0).get(1));
        greenProfessor.setVisible(update.professors.get(0).get(2));
        yellowProfessor.setVisible(update.professors.get(0).get(3));
        pinkProfessor.setVisible(update.professors.get(0).get(4));
    }

    private void hallUpdate(){
        ArrayList<Pane> redHall=new ArrayList<>();
        ArrayList<Pane> blueHall=new ArrayList<>();
        ArrayList<Pane> greenHall=new ArrayList<>();
        ArrayList<Pane> yellowHall=new ArrayList<>();
        ArrayList<Pane> pinkHall=new ArrayList<>();

        arrayBuild(redHall, redHall0, redHall1, redHall2, redHall3, redHall4, redHall5, redHall6, redHall7, redHall8, redHall9);
        arrayBuild(blueHall, blueHall0, blueHall1, blueHall2, blueHall3, blueHall4, blueHall5, blueHall6, blueHall7, blueHall8, blueHall9);
        arrayBuild(greenHall, greenHall0, greenHall1, greenHall2, greenHall3, greenHall4, greenHall5, greenHall6, greenHall7, greenHall8, greenHall9);
        arrayBuild(yellowHall, yellowHall0, yellowHall1, yellowHall2, yellowHall3, yellowHall4, yellowHall5, yellowHall6, yellowHall7, yellowHall8, yellowHall9);
        arrayBuild(pinkHall, pinkHall0, pinkHall1, pinkHall2, pinkHall3, pinkHall4, pinkHall5, pinkHall6, pinkHall7, pinkHall8, pinkHall9);

        int redNumber=update.hallPlayer.get(0).get(0);
        int blueNumber=update.hallPlayer.get(0).get(1);
        int greenNumber=update.hallPlayer.get(0).get(2);
        int yellowNumber=update.hallPlayer.get(0).get(3);
        int pinkNumber=update.hallPlayer.get(0).get(4);

        for (int i=0; i<redNumber; i++){
            redHall.get(i).setVisible(true);
        }
        for (int i=0; i<blueNumber; i++){
            blueHall.get(i).setVisible(true);
        }
        for (int i=0; i<greenNumber; i++){
            greenHall.get(i).setVisible(true);
        }
        for (int i=0; i<yellowNumber; i++){
            yellowHall.get(i).setVisible(true);
        }
        for (int i=0; i<pinkNumber; i++){
            pinkHall.get(i).setVisible(true);
        }
    }

    private void arrayBuild(ArrayList<Pane> array, Pane element1, Pane element2, Pane element3, Pane element4, Pane element5, Pane element6, Pane element7, Pane element8, Pane element9, Pane element10) {
        array.add(element1);
        array.add(element2);
        array.add(element3);
        array.add(element4);
        array.add(element5);
        array.add(element6);
        array.add(element7);
        array.add(element8);
        array.add(element9);
        array.add(element10);
    }
}
