package it.polimi.ingsw.CLIENT;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WaitController {

    @FXML
    private Text idLabel;

    public void setGame(String gameid){
        idLabel.setVisible(true);
        idLabel.setText("Game ID: "+gameid);
    }
}
