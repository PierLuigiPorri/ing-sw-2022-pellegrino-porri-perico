package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class HandController {
    private static GUIAPP gui;
    private UpdateMessage update;
    ArrayList<Integer> intPar=new ArrayList<>();
    ArrayList<String> strPar=new ArrayList<>();
    private String userNickname;

    @FXML
    private Pane card0, card1, card2, card3, card4, card5, card6, card7, card8, card9;

    public static void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    public void refresh(){
        update=gui.getUpdate();
        this.userNickname=gui.getUserNickname();

        disableAllCards();
        setInvisibleCards();

        ArrayList<Pane> cards=new ArrayList<>();
        arrayBuild(cards, card0, card1, card2, card3, card4, card5, card6, card7, card8, card9);
        onMouseClicked(cards);
        setCards();
        if(!update.phase.equals("Planning") && !update.order.get(0).equals(userNickname))
            disableAllCards();
    }

    public void playCard(){
        strPar.add(userNickname);
        gui.perform(intPar, strPar, null,4);

        intPar.clear();
        strPar.clear();
    }

    public void onMouseClicked(ArrayList<Pane> cards){
        for (Pane card:cards) {
            card.setOnMouseClicked(e->{
                    card.setDisable(true);
                    intPar.add(cards.indexOf(card));
                    playCard();
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

    public void disableAllCards(){
        card0.setDisable(true);
        card1.setDisable(true);
        card2.setDisable(true);
        card3.setDisable(true);
        card4.setDisable(true);
        card5.setDisable(true);
        card6.setDisable(true);
        card7.setDisable(true);
        card8.setDisable(true);
        card9.setDisable(true);
    }

    public void setCards(){
        for(int i=1; i<update.handPlayer.get(userIndex()).size(); i=i+2){
            switch (update.handPlayer.get(userIndex()).get(i)) {
                case 0:
                    card0.setDisable(false);
                    card0.setVisible(true);
                    card0.setCursor(Cursor.HAND);
                    break;
                case 1:
                    card1.setDisable(false);
                    card1.setVisible(true);
                    card1.setCursor(Cursor.HAND);
                    break;
                case 2:
                    card2.setDisable(false);
                    card2.setVisible(true);
                    card2.setCursor(Cursor.HAND);
                    break;
                case 3:
                    card3.setDisable(false);
                    card3.setVisible(true);
                    card3.setCursor(Cursor.HAND);
                    break;
                case 4:
                    card4.setDisable(false);
                    card4.setVisible(true);
                    card4.setCursor(Cursor.HAND);
                    break;
                case 5:
                    card5.setDisable(false);
                    card5.setVisible(true);
                    card5.setCursor(Cursor.HAND);
                    break;
                case 6:
                    card6.setDisable(false);
                    card6.setVisible(true);
                    card6.setCursor(Cursor.HAND);
                    break;
                case 7:
                    card7.setDisable(false);
                    card7.setVisible(true);
                    card7.setCursor(Cursor.HAND);
                    break;
                case 8:
                    card8.setDisable(false);
                    card8.setVisible(true);
                    card8.setCursor(Cursor.HAND);
                    break;
                case 9:
                    card9.setDisable(false);
                    card9.setVisible(true);
                    card9.setCursor(Cursor.HAND);
                    break;
            }
        }
    }

    public void setInvisibleCards(){
        card0.setVisible(false);
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        card5.setVisible(false);
        card6.setVisible(false);
        card7.setVisible(false);
        card8.setVisible(false);
        card9.setVisible(false);
    }

}
