package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CharactersController {

    private static GUIAPP gui;
    private UpdateMessage update;
    private String userNickname;

    @FXML
    private ImageView character1, character2, character3;
    @FXML
    private Text coinsNumber;
    @FXML
    private Pane activated1, activated2, activated3;

    public void refresh(){
        update = gui.getUpdate();
        userNickname=gui.getUserNickname();

        coinsNumber.setText(""+update.coinsOnPlayer.get(userIndex())+"");
        setImage(character1, update.idCharacter.get(0));
        setImage(character2, update.idCharacter.get(1));
        setImage(character3, update.idCharacter.get(2));

        character1.setOnMouseClicked(e->{

        });

        character2.setOnMouseClicked(e->{

        });

        character3.setOnMouseClicked(e->{

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
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front.jpg"));
                break;
            case 1:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front2.jpg"));
                break;
            case 2:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front3.jpg"));
                break;
            case 3:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front4.jpg"));
                break;
            case 4:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front5.jpg"));
                break;
            case 5:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front6.jpg"));
                break;
            case 6:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front7.jpg"));
                break;
            case 7:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front8.jpg"));
                break;
            case 8:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front9.jpg"));
                break;
            case 9:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front10.jpg"));
                break;
            case 10:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front11.jpg"));
                break;
            case 11:
                imageView.setImage(new Image("src/main/resources/Graphical_Assets/CarteTOT_front12.jpg"));
                break;
        }
        imageView.setCursor(Cursor.HAND);
    }

}
