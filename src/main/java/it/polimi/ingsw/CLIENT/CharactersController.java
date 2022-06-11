package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.CLIENT.GUIobjects.IslandGUI;
import it.polimi.ingsw.CLIENT.GUIobjects.StudentGUI;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class CharactersController {

    private static GUIAPP gui;
    private UpdateMessage update;
    private String userNickname;
    private int selectedChar;

    @FXML
    private Pane character1, character2, character3;
    @FXML
    private Text coinsNumber;
    @FXML
    private Text activated1, activated2, activated3;
    @FXML
    private ImageView image1, image2,image3;
    @FXML
    private Text effect1, effect2, effect3;
    @FXML
    private Button confirmButton;
    private IslandGUI selectedIsland;
    private StudentGUI selectedStudent;

    public static void setGui(GUIAPP gui) {
        CharactersController.gui = gui;
    }

    public void refresh(){
        update = gui.getUpdate();
        userNickname=gui.getUserNickname();

        coinsNumber.setText(""+update.coinsOnPlayer.get(userIndex())+"");
        setImage(image1, update.idCharacter.get(0));
        setImage(image2, update.idCharacter.get(1));
        setImage(image3, update.idCharacter.get(2));
        effect1.setText(characterEffect(update.idCharacter.get(0)));
        effect2.setText(characterEffect(update.idCharacter.get(1)));
        effect3.setText(characterEffect(update.idCharacter.get(2)));

        if(update.activated.get(0)) {
            character1.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            activated1.setVisible(true);
        }
        if(update.activated.get(1)){
            character2.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            activated2.setVisible(true);
        }
        if(update.activated.get(2)){
            character3.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            activated3.setVisible(true);
        }

        character1.setOnMousePressed(e->{
            selectedChar=update.idCharacter.get(0);
            confirmButton.setVisible(true);
            confirmButton.setDisable(false);
        });

        character2.setOnMousePressed(e->{
            selectedChar=update.idCharacter.get(1);
            confirmButton.setVisible(true);
            confirmButton.setDisable(false);
        });

        character3.setOnMousePressed(e->{
            selectedChar=update.idCharacter.get(2);
            confirmButton.setVisible(true);
            confirmButton.setDisable(false);
        });


        if(update.phase.equals("Planning")) {
            character1.setDisable(true);
            character2.setDisable(true);
            character3.setDisable(true);
        }
    }

    public int userIndex(){
        return update.players.indexOf(userNickname);
    }

    public void setImage(ImageView imageView, int idCharacter){
        switch(idCharacter){
            case 0:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front.jpg"));
                break;
            case 1:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front2.jpg"));
                break;
            case 2:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front3.jpg"));
                break;
            case 3:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front4.jpg"));
                break;
            case 4:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front5.jpg"));
                break;
            case 5:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front6.jpg"));
                break;
            case 6:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front7.jpg"));
                break;
            case 7:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front8.jpg"));
                break;
            case 8:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front9.jpg"));
                break;
            case 9:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front10.jpg"));
                break;
            case 10:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front11.jpg"));
                break;
            case 11:
                imageView.setImage(new Image("Graphical_Assets/CarteTOT_front12.jpg"));
                break;
        }
        imageView.setCursor(Cursor.HAND);
    }

    @FXML
    private void activateCharacter(){
        if(update.coinsOnPlayer.get(userIndex()) >= characterCost(selectedChar)){
            ArrayList<Integer> a = new ArrayList<>(), c;
            ArrayList<String> b = new ArrayList<>();
            a.add(selectedChar);
            switch (selectedChar) {
                /*case 0: //Serve prima la selezione dello studente, poi la selezione dell'isola
                    int i, x;
                        System.out.println("What's the position of the student on the card you want to move?");
                        i = getIntInput();
                        if (i == -1) {
                            cancel = true;
                            break;
                        }
                        checkIntInput(0, 3, i, "What's the position of the student on the card you want to move?\n");
                        a.add(inputInt.get(inputInt.size() - 1));
                        System.out.println("What's the index of the island on which you want to move the student?");
                        x = getIntInput();
                        if (x == -1) {
                            cancel = true;
                            break;
                        }
                        checkIntInput(1, 12, x, "What's the index of the island on which you want to move the student?");
                        a.add(inputInt.get(inputInt.size() - 1));
                    break;*/
                case 1:
                case 7:
                case 3:
                case 5:
                    gui.perform(a, b, null,5);
                    break;
                /*case 2:  //Serve la selezione dell'isola
                        System.out.println("What's the index of the island you want to determine the influence of?");
                        i = getIntInput();
                        if (i == -1) {
                            cancel = true;
                            break;
                        }
                        checkIntInput(1, 12, i, "What's the index of the island you want to determine the influence of?\n");
                        a.add(inputInt.get(inputInt.size() - 1));
                    break;*/
                /*case 4:   //Serve la selezione dell'isola
                        System.out.println("What's the index of the island you want to put the counter on?");
                        i = getIntInput();
                        if (i == -1) {
                            cancel = true;
                            break;
                        }
                        checkIntInput(1, 12, i, "What's the index of the island you want to put the counter on?\n");
                        a.add(inputInt.get(inputInt.size() - 1));
                    break;*/
                /*case 6:   //Questo è impossibile non so come farlo
                        System.out.println("How many students do you want to swap?");
                        i = getIntInput();
                        if (i == -1) {
                            cancel = true;
                            break;
                        }
                        checkIntInput(1, 3, i, "How many students do you want to swap?");
                        int max = inputInt.get(inputInt.size() - 1);
                        inputInt.remove(inputInt.size() - 1);
                        System.out.println("What are the indexes on the Character card of the students you want to swap?");
                        for (int z = 0; z < max; z++) {
                            i = getIntInput();
                            checkIntInput(0, 3, i, "What's the index?");
                            a.add(inputInt.get(inputInt.size() - 1));
                        }
                        c = new ArrayList<>();
                        System.out.println("What are the indexes on the gate of the students you want to swap?");
                        for (int z = 0; z < max; z++) {
                            i = getIntInput();
                            checkIntInput(0, 3, i, "What's the index?");
                            c.add(inputInt.get(inputInt.size() - 1));
                    break;*/
                /*case 8:   //Serve la selezione del colore
                        System.out.println("What color would you like to disable?");
                        String in = getStrInput();
                        if (in.equals("-1")) {
                            cancel = true;
                            break;
                        }
                        checkStrInput(in, "What color would you like to disable?");
                        b.add(inputStr.get(inputStr.size() - 1));
                    break;*/
                /*case 9:   //Questo non è impossibile ma è un lavoro enorme
                        System.out.println("How many students would you like to swap?");
                        i = getIntInput();
                        if (i == -1) {
                            cancel = true;
                            break;
                        }
                        checkIntInput(1, 2, i, "How many students would you like to swap?");
                        int max = inputInt.get(inputInt.size() - 1);
                        inputInt.remove(inputInt.size() - 1);
                        System.out.println("What are the indexes of the students on the gate you want to swap?");
                        for (int z = 0; z < max; z++) {
                            i = getIntInput();
                            checkIntInput(0, 9, i, "What's the index?");
                            a.add(inputInt.get(inputInt.size() - 1));
                        }
                        System.out.println("Which colors would you like to swap in you hall?");
                        for (int z = 0; z < max; z++) {
                            String in = getStrInput();
                            checkStrInput(in, "Which color?");
                            b.add(inputStr.get(inputStr.size() - 1));
                        }
                    break;*/
                /*case 10:  //Serve la selezione dello studente
                        System.out.println("What is the index on the card of the student you want to add?");
                        i = getIntInput();
                        if (i == -1) {
                            cancel = true;
                            break;
                        }
                        checkIntInput(0, 3, i, "What is the index on the card of the student you want to add?");
                        a.add(inputInt.get(inputInt.size() - 1));
                    break;*/
                /*case 11:  //Serve la selezione del colore
                        System.out.println("What color would you like to affect?");
                        String in = getStrInput();
                        if (in.equals("-1")) {
                            cancel = true;
                            break;
                        }
                        checkStrInput(in, "Which color?");
                        b.add(inputStr.get(inputStr.size() - 1));
                    break;*/
                default:
                    break;
            }
        }else{
            //TODO:popup no soldi :)
        }
    }

    private String characterEffect(int index) {
        switch (index) {
            case 0:
                return "You can take a student from this card and place it on an Island of your choosing! Don't worry, the student on the card will replace itself!";
            case 1:
                return "This guy lets you take control of a Professor even if you have the same number of corresponding students in the Hall, until the end of this turn!";
            case 2:
                return "Perform an extraordinary calculation of the influence on an Island of your choosing!";
            case 3:
                return "This guy gives you a bonus of +2 on your Mother Nature potential movement!";
            case 4:
                return "You can take a Prohibition counter from this card and place it on an Island of your choosing! It'll prevent Mother Nature from calculating the influence there, but just once!";
            case 5:
                return "This guy disables the towers' influence on the Islands, until the end of the turn! They will count as 0!";
            case 6:
                return "You can take up to 3 students from this card, and swap them with the same number of students in your Gate!";
            case 7:
                return "This guy gives you a bonus of +2 when calculating the influence this turn!";
            case 8:
                return "This guy disables the influence of 1 color of your choosing! Every student of that color will count as 0!";
            case 9:
                return "You can take up to 2 students from your Gate and swap them with the same number of students in your Hall!";
            case 10:
                return "You can take a student from this card and place it directly in your Hall! Don't worry, the student on the card will replace itself!";
            case 11:
                return "This guy takes away 3 students (or up to 3 if they have less) of a color of your choosing from EVERYONE's Hall!";
            default:
                return "";
        }
    }

    private int characterCost(int index) {
        switch (index) {
            case 0:
            case 3:
            case 6:
            case 9:
                return update.activated.get(update.idCharacter.indexOf(index)) ? 2 : 1;
            case 1:
            case 4:
            case 7:
            case 10:
                return update.activated.get(update.idCharacter.indexOf(index)) ? 3 : 2;
            case 2:
            case 5:
            case 8:
            case 11:
                return update.activated.get(update.idCharacter.indexOf(index)) ? 4 : 3;
            default:
                return 0;
        }
    }

}
