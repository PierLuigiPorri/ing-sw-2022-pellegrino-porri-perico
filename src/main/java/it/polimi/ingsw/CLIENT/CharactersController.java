package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.CLIENT.GUIobjects.CharacterGUI;
import it.polimi.ingsw.CLIENT.GUIobjects.CoordinatesData;
import it.polimi.ingsw.CLIENT.GUIobjects.StudentGUI;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import java.util.ArrayList;

/**
 * FXML Controller class for the Characters' information scene. Shows the 3 Character cards in play, their effect
 * and their cost, and lets the user activate their effects if the game logic permits it.
 * @author GC56
 */
public class CharactersController {

    private GUIAPP gui;
    private UpdateMessage update;
    private String userNickname;
    private int selectedChar = -1;

    @FXML
    private Pane character1, character2, character3;
    @FXML
    private Text coinsNumber;
    @FXML
    private Text activated1, activated2, activated3;
    @FXML
    private ImageView image1, image2, image3;
    @FXML
    private Text effect1, effect2, effect3;
    @FXML
    private Button confirmButton;
    @FXML
    private Text poorAlert;
    @FXML
    private CubicCurve arrowLine;
    @FXML
    private Polygon arrowHead;

    public void setGui(GUIAPP gui) {
        this.gui = gui;
    }


    /**
     * Main method of the View. Builds and shows to the user all the appropriate GUI objects and the FXML scene.
     * Called by the GUIAPP when the scene is set. Builds the GUI objects and assigns them the methods for
     * the Mouse events.
     */
    public void refresh() {
        update = gui.getUpdate();
        userNickname = gui.getUserNickname();

        coinsNumber.setText("" + update.coinsOnPlayer.get(userIndex()) + "");
        setImage(image1, update.idCharacter.get(0));
        setImage(image2, update.idCharacter.get(1));
        setImage(image3, update.idCharacter.get(2));
        effect1.setText(characterEffect(update.idCharacter.get(0)));
        effect2.setText(characterEffect(update.idCharacter.get(1)));
        effect3.setText(characterEffect(update.idCharacter.get(2)));
        setStudents();


        if (update.activated.get(0)) {
            character1.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            activated1.setVisible(true);
        }
        if (update.activated.get(1)) {
            character2.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            activated2.setVisible(true);
        }
        if (update.activated.get(2)) {
            character3.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            activated3.setVisible(true);
        }

        character1.setOnMousePressed(e -> {
            if (update.order.get(0).equals(userNickname) && update.phase.equals("Action")) {
                if (selectedChar != -1) {
                    if (selectedChar == update.idCharacter.get(1))
                        character2.setBorder(null);
                    else
                        character3.setBorder(null);
                }
                selectedChar = update.idCharacter.get(0);
                character1.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                confirmButton.setVisible(true);
                confirmButton.setDisable(false);
            }
        });

        character2.setOnMousePressed(e -> {
            if (update.order.get(0).equals(userNickname) && update.phase.equals("Action")) {
                if (selectedChar != -1) {
                    if (selectedChar == update.idCharacter.get(0))
                        character1.setBorder(null);
                    else
                        character3.setBorder(null);
                }
                selectedChar = update.idCharacter.get(1);
                character2.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                confirmButton.setVisible(true);
                confirmButton.setDisable(false);
            }
        });

        character3.setOnMousePressed(e -> {
            if (update.order.get(0).equals(userNickname) && update.phase.equals("Action")) {
                if (selectedChar != -1) {
                    if (selectedChar == update.idCharacter.get(0))
                        character1.setBorder(null);
                    else
                        character2.setBorder(null);
                }
                selectedChar = update.idCharacter.get(2);
                character3.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                confirmButton.setVisible(true);
                confirmButton.setDisable(false);
            }
        });


        if (update.phase.equals("Planning")) {
            character1.setDisable(true);
            character2.setDisable(true);
            character3.setDisable(true);
        }
    }

    public int userIndex() {
        return update.players.indexOf(userNickname);
    }

    public void setImage(ImageView imageView, int idCharacter) {
        imageView.setImage(new Image("Graphical_Assets/CarteTOT_front" + idCharacter + ".jpg"));
        imageView.setCursor(Cursor.HAND);
    }

    /**
     * Builds and sets the students on the cards that contains them, by building a CharacterGUI object.
     */
    private void setStudents() {
        if (update.idCharacter.get(0) == 0 || update.idCharacter.get(0) == 6 || update.idCharacter.get(0) == 10) {
            CharacterGUI studs;
            if (update.idCharacter.get(0) == 6) {
                studs = new CharacterGUI(6);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(0))).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(0))).get(i));
                    student.setLayoutX(CoordinatesData.getCardStudents(6).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(6).get(i / 2).getY());
                    studs.getChildren().add(student);
                }
            } else {
                studs = new CharacterGUI(4);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(0))).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(0))).get(i));
                    student.setLayoutX(CoordinatesData.getCardStudents(4).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(4).get(i / 2).getY());
                    studs.getChildren().add(student);
                }
            }
            character1.getChildren().add(studs);
        }
        if (update.idCharacter.get(1) == 0 || update.idCharacter.get(1) == 6 || update.idCharacter.get(1) == 10) {
            CharacterGUI studs;
            if (update.idCharacter.get(1) == 6) {
                studs = new CharacterGUI(6);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(1))).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(1))).get(i));
                    student.setLayoutX(CoordinatesData.getCardStudents(6).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(6).get(i / 2).getY());
                    studs.getChildren().add(student);
                }
            } else {
                studs = new CharacterGUI(4);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(1))).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(1))).get(i));
                    student.setLayoutX(CoordinatesData.getCardStudents(4).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(4).get(i / 2).getY());
                    studs.getChildren().add(student);
                }
            }
            character2.getChildren().add(studs);
        }
        if (update.idCharacter.get(2) == 0 || update.idCharacter.get(2) == 6 || update.idCharacter.get(2) == 10) {
            CharacterGUI studs;
            if (update.idCharacter.get(2) == 6) {
                studs = new CharacterGUI(6);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(2))).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(2))).get(i));
                    student.setLayoutX(CoordinatesData.getCardStudents(6).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(6).get(i / 2).getY());
                    studs.getChildren().add(student);
                }
            } else {
                studs = new CharacterGUI(4);
                for (int i = 1; i < update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(2))).size(); i += 2) {
                    StudentGUI student = new StudentGUI(update.studentsOnCard.get(update.idCharacter.indexOf(update.idCharacter.get(2))).get(i));
                    student.setLayoutX(CoordinatesData.getCardStudents(4).get(i / 2).getX());
                    student.setLayoutY(CoordinatesData.getCardStudents(4).get(i / 2).getY());
                    studs.getChildren().add(student);
                }
            }
            character3.getChildren().add(studs);
        }
    }

    /**
     * Method associated with an FXML button. Checks if the user has enough coins to activate the selected card,
     * then either performs an activateCharacter action request, or sets the scene to the Characters' parameters
     * selection scene, depending on the index of the activated card.
     */
    @FXML
    private void activateCharacter() {
        if (update.coinsOnPlayer.get(userIndex()) >= characterCost(selectedChar)) {
            poorAlert.setVisible(false);
            arrowHead.setVisible(false);
            arrowLine.setVisible(false);
            ArrayList<Integer> a = new ArrayList<>();
            ArrayList<String> b = new ArrayList<>();
            a.add(selectedChar);
            switch (selectedChar) {
                case 1:
                case 7:
                case 3:
                case 5:
                    gui.perform(a, b, null, 5);
                    break;
                case 6:
                case 9:
                case 0:
                case 2:
                case 4:
                case 8:
                case 10:
                case 11:
                    gui.setScene("fxml/characterParametersSelection.fxml");
                    gui.characterParametersController.refresh(selectedChar);
                    break;
                default:
                    break;
            }
        } else {
            poorAlert.setVisible(true);
            arrowHead.setVisible(true);
            arrowLine.setVisible(true);
        }
    }

    /**
     * Stores and returns the effects of the Characters.
     * @param index The index of the given Card. Int parameter between 0-11.
     * @return Returns a string containing the effect of the given Character.
     */
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

    /**
     * Stores and returns the Characters' costs.
     * @param index The index of the chosen Character. Between 0-11.
     * @return Returns the cost of the Character, handling the case in which it has been already activated.
     */
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

    /**
     * Method associated with an FXML button. Sets the scene back to the Board scene.
     */
    public void backToBoard() {
        gui.setScene("fxml/board.fxml");
        gui.boardController.refresh();
    }

}
