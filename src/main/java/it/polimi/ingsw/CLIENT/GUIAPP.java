package it.polimi.ingsw.CLIENT;

import it.polimi.ingsw.MESSAGES.ActionMessage;
import it.polimi.ingsw.MESSAGES.MessageType;
import it.polimi.ingsw.MESSAGES.ResponseMessage;
import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;


public class GUIAPP extends Application implements View {

    private String userNickname, playerNickname;
    private final Object lock;
    private ClientMsgHandler msgHandler;
    private AckSender ackSender;
    private final ArrayList<Integer> inputInt, third;
    private final ArrayList<String> inputStr;
    private final ArrayList<Integer> int3=null;
    private UpdateMessage update;
    public boolean gameStarted;
    public WaitController waitController;
    public BoardController boardController;
    private PlayersBoardController playersBoardController;
    private HandController handController;
    private CharactersController charactersController;
    public CharacterParametersController characterParametersController;
    @FXML
    private Stage currentStage;
    public String updateLog="";
    public String gameid;

    public GUIAPP(){
        lock = new Object();
        inputInt=new ArrayList<>();
        inputStr=new ArrayList<>();
        third=new ArrayList<>();
        gameStarted=false;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    @FXML
    @Override
    public void start(Stage primaryStage) throws Exception {
        currentStage=new Stage();
        IPController.setGUI(this);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/IP_choice.fxml")));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        Image icon=new Image("Graphical_Assets/sfondo.jpg");
        currentStage.getIcons().add(icon);
        currentStage.setTitle("Eriantys");
        currentStage.setResizable(false);
        currentStage.show();
    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("CLOSE APPLICATION");
            alert.setContentText("Are you sure you want to close the application?");
            alert.initOwner(currentStage.getOwner());
            Optional<ButtonType> res = alert.showAndWait();
            if(res.isPresent()) {
                if(res.get().equals(ButtonType.CANCEL))
                    event.consume();
            }
    }

    public void connect(String ip){
        msgHandler = new ClientMsgHandler(ip, 4000, lock); //Connection setup with this IP and Port numbers
        ackSender = new AckSender(msgHandler, 5000);
        msgHandler.setAckSender(ackSender);
        new Thread(ackSender).start();
        new Thread(msgHandler).start();
        msgHandler.setView(this);
        MainMenuController.setGUI(this);
        setScene("fxml/mainMenu.fxml");
    }

    @FXML
    public void setScene(String address) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(address));
            Parent root = fxmlLoader.load();
            switch(address){
                case "fxml/waitGameToStart.fxml":
                    waitController=fxmlLoader.getController();
                    waitController.setGame(gameid);
                    break;
                case "fxml/board.fxml":
                    boardController=fxmlLoader.getController();
                    boardController.setGUI(this);
                    break;
                case "fxml/PlayersBoard.fxml":
                    playersBoardController=fxmlLoader.getController();
                    playersBoardController.setGUI(this);
                    playersBoardController.refresh();
                break;
                case "fxml/Hand.fxml":
                    handController=fxmlLoader.getController();
                    handController.setGUI(this);
                    handController.refresh();
                    break;
                case "fxml/Characters.fxml":
                    charactersController=fxmlLoader.getController();
                    charactersController.setGui(this);
                    charactersController.refresh();
                    break;
                case "characterParametersSelection.fxml":
                    characterParametersController=fxmlLoader.getController();
                    characterParametersController.setGUI(this);
            }
            //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            //double width = gd.getDisplayMode().getWidth()*0.9;
            //double height = gd.getDisplayMode().getHeight()*0.9;
            //Scene scene = new Scene(root, width, height);
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            //currentStage.setMaximized(true);
            currentStage.centerOnScreen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //delay(1000, () -> startGame());
    }

    public void waitForMessage(){
        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop(){
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    public void send(MessageType message){
        msgHandler.send(message);
    }



    public ArrayList<ResponseMessage> getResponses(){
        return msgHandler.getResponses();
    }

    public void setUserNickname(String nickname){
        this.userNickname=nickname;
    }

    public String getUserNickname(){
        return this.userNickname;
    }

    public void perform(ArrayList<Integer> intpar, ArrayList<String> strpar, ArrayList<Integer> third, int action){
        inputStr.add(userNickname);
        if(strpar != null)
            inputStr.addAll(strpar);
        if(intpar!=null)
            inputInt.addAll(intpar);
        if(third!=null)
            this.third.addAll(third);
        switch (action){
            case 3://moveMotherNature
                moveMotherNature();
                break;
            case 0://gateToIsland
                gateToIsland();
                break;
            case 1://gateToHall
                gateToHall();
                break;
            case 2://cloudToGate
                cloudToGate();
                break;
            case 4://playCard
                playCard();
                break;
            case 5://activateCharacter
                activateCharacter();
                break;
        }
        inputInt.clear();
        inputStr.clear();
    }


    @Override
    public void moveMotherNature() {
        ArrayList<Integer> integers=new ArrayList<>(inputInt);
        ArrayList<String> strings=new ArrayList<>(inputStr);
        msgHandler.send(new ActionMessage(integers, strings, int3, 3));
    }

    @Override
    public void gateToIsland() {
        ArrayList<Integer> integers=new ArrayList<>(inputInt);
        ArrayList<String> strings=new ArrayList<>(inputStr);
        msgHandler.send(new ActionMessage(integers, strings, int3, 0));
    }

    @Override
    public void gateToHall() {
        ArrayList<Integer> integers=new ArrayList<>(inputInt);
        ArrayList<String> strings=new ArrayList<>(inputStr);
        msgHandler.send(new ActionMessage(integers, strings, int3, 1));
    }

    @Override
    public void cloudToGate() {
        ArrayList<Integer> integers=new ArrayList<>(inputInt);
        ArrayList<String> strings=new ArrayList<>(inputStr);
        msgHandler.send(new ActionMessage(integers, strings, int3, 2));
    }

    @Override
    public void activateCharacter() {
        ArrayList<Integer> integers=new ArrayList<>(inputInt);
        ArrayList<String> strings=new ArrayList<>(inputStr);
        msgHandler.send(new ActionMessage(integers, strings, int3, 5));
    }

    @Override
    public void playCard() {
        ArrayList<Integer> integers=new ArrayList<>(inputInt);
        ArrayList<String> strings=new ArrayList<>(inputStr);
        msgHandler.send(new ActionMessage(integers, strings, int3, 4));
    }

    @Override
    public void signalUpdate(){
        //Runs on Message Handler Thread
        System.out.println("è arrivato un update");
        Platform.runLater(() -> update(msgHandler.getUpdates().remove(0)));
    }

    @Override
    public void update(UpdateMessage up) {
        this.update=up;
        if(!gameStarted){
            gameStarted=true;
        }
        if(!update.gameEnded) {
            for (String s:update.update) {
                s = s.replace("\n", "");
                updateLog = s + "\n" +updateLog;
            }
            setScene("fxml/board.fxml");
            boardController.refresh();
            System.out.println("Ho applicato l'update");
            System.out.println(update.update);
            currentStage.show();
        }
        else{
            gameStarted=false;
            setScene("fxml/mainMenu.fxml");
            currentStage.show();
            showPopup("GAME ENDED", update.update.get(0));
            popupButton1.setOnAction(e -> popupWindow.close());
            popupWindow.showAndWait();
        }
    }

    @Override
    public void signalResponse(){
        //Runs on Message Handler Thread
        if(!msgHandler.getResponses().isEmpty()) {
            if(!msgHandler.getResponses().get(msgHandler.getResponses().size() - 1).allGood) {
                String s = msgHandler.getResponses().get(msgHandler.getResponses().size() - 1).response;
                Platform.runLater(() -> response(s));
            }
        }
    }

    public void response(String r){
        showPopup("ERROR", r);
        popupButton1.setOnAction(e -> popupWindow.close());
        popupWindow.showAndWait();
    }

    public UpdateMessage getUpdate(){
        return update;
    }

    public void setPlayerNickname(String name){
        this.playerNickname=name;
    }

    public String getPlayerNickname(){
        return this.playerNickname;
    }

    //Popup stuff
    private Stage popupWindow;
    private Button popupButton1;
    private void showPopup(String title, String message) {
        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(title);
        popupWindow.setMinHeight(200);
        popupWindow.setMinWidth(200);
        javafx.scene.control.Label label = new javafx.scene.control.Label();
        label.setText(message);

        popupButton1 =new Button("OK");

        VBox layout = new VBox(15);
        layout.getChildren().addAll(label, popupButton1);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        popupWindow.setScene(scene);
    }
}
