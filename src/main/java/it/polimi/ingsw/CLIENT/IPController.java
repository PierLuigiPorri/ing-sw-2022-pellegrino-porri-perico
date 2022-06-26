package it.polimi.ingsw.CLIENT;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for the introductory scene. Lets the user select the IP address for the connection,
 * then moves on to the Main Menu scene via the GUIAPP class.
 * @author GC56
 */
public class IPController {

    private static GUIAPP gui;

    @FXML
    private TextField ipTextField;

    /**
     * Method associated with an FXML button. Performs the connection request via the GUIAPP class,
     * using the chosen IP address. If none is given, will use the standard local one.
     */
    @FXML
    private void connect(){
        if(!ipTextField.getText().equals("")) {
            gui.connect(ipTextField.getText());
        }
        else{
            gui.connect("127.0.0.1");
        }
    }

    public static void setGUI(GUIAPP guiApp){
        gui=guiApp;
    }
}
