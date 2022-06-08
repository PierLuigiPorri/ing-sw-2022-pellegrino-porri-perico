package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class HandController {
    private static GUIAPP gui;
    private UpdateMessage update;
    ArrayList<Integer> intPar=new ArrayList<>();
    ArrayList<String> strPar=new ArrayList<>();
    private String userNickname;

    @FXML
    private Pane card1, card2, card3, card4, card5, card6, card7, card8, card9, card10;


    public static void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    public void refresh(){

        update=gui.getUpdate();
        this.userNickname=gui.getUserNickname();

        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);
        card4.setDisable(true);
        card5.setDisable(true);
        card6.setDisable(true);
        card7.setDisable(true);
        card8.setDisable(true);
        card9.setDisable(true);
        card10.setDisable(true);

        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        card5.setVisible(false);
        card6.setVisible(false);
        card7.setVisible(false);
        card8.setVisible(false);
        card9.setVisible(false);
        card10.setVisible(false);

        for(int i=1; i<update.handPlayer.get(userIndex()).size(); i=i+2){
            switch (update.handPlayer.get(userIndex()).get(i)) {
                case 0:
                    card1.setDisable(false);
                    card1.setVisible(true);
                    break;
                case 1:
                    card2.setDisable(false);
                    card2.setVisible(true);
                    break;
                case 2:
                    card3.setDisable(false);
                    card3.setVisible(true);
                    break;
                case 3:
                    card4.setDisable(false);
                    card4.setVisible(true);
                    break;
                case 4:
                    card5.setDisable(false);
                    card5.setVisible(true);
                    break;
                case 5:
                    card6.setDisable(false);
                    card6.setVisible(true);
                    break;
                case 6:
                    card7.setDisable(false);
                    card7.setVisible(true);
                    break;
                case 7:
                    card8.setDisable(false);
                    card8.setVisible(true);
                    break;
                case 8:
                    card9.setDisable(false);
                    card9.setVisible(true);
                    break;
                case 9:
                    card10.setDisable(false);
                    card10.setVisible(true);
                    break;
            }
        }
    }

    public void playCard(){
        String userNickname = gui.getUserNickname();
        ArrayList<Pane> cards=new ArrayList<>();

        arrayBuild(cards, card1, card2, card3, card4, card5, card6, card7, card8, card9, card10);

        strPar.add(userNickname);

        onMouseClicked(cards);

        gui.perform(intPar, strPar, 4);
    }

    public void onMouseClicked(ArrayList<Pane> cards){
        for (Pane card:cards) {
            card.setOnMouseClicked(e->{
                card.setDisable(true);
                intPar.add(cards.indexOf(card));
            });
        }

    }

    static void arrayBuild(ArrayList<Pane> cards, Pane card1, Pane card2, Pane card3, Pane card4, Pane card5, Pane card6, Pane card7, Pane card8, Pane card9, Pane card10) {
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        cards.add(card8);
        cards.add(card9);
        cards.add(card10);
    }

    public int userIndex() {
        return update.players.indexOf(userNickname);
    }

}
