package it.polimi.ingsw.CLIENT;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * FXML Controller class for the Wait scene. Makes the user wait until an ongoing Joining or Creation process is complete.
 * @author GC56
 */
public class WaitController {

    @FXML
    private Text idLabel;

    public void setGame(String gameid){
        idLabel.setVisible(true);
        idLabel.setText("Game ID: "+gameid);
    }
}
