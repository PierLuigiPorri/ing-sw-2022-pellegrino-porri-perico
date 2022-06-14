package it.polimi.ingsw.CLIENT;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class IPController {

    private static GUIAPP gui;

    @FXML
    private Button connectButton;
    @FXML
    private TextField ipTextField;

    @FXML
    private void connect(){
        if(!ipTextField.getText().equals("")) {
            gui.connect(ipTextField.getText());
        }
        else{
            //TODO: Rimuovere questo else prima della consegna e dell'esame
            gui.connect("127.0.0.1");
        }
    }

    public static void setGUI(GUIAPP guiApp){
        gui=guiApp;
    }
}
