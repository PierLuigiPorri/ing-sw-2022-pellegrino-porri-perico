package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class HandController {
    private GUIAPP gui;
    private UpdateMessage update;
    ArrayList<Integer> intPar=new ArrayList<>();
    ArrayList<String> strPar=new ArrayList<>();
    private String userNickname;

    @FXML
    private Pane card0, card1, card2, card3, card4, card5, card6, card7, card8, card9;

    public void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    public void refresh(){
        update=gui.getUpdate();
        this.userNickname=gui.getUserNickname();

        disableAllCards(true);
        setVisibleCards(false);

        ArrayList<Pane> cards=new ArrayList<>();
        arrayBuild(cards, card0, card1, card2, card3, card4, card5, card6, card7, card8, card9);
        setCards();

        if(!update.phase.equals("Planning") || !update.order.get(0).equals(userNickname))
            disableAllCards(true);

        onMouseClicked(cards);
    }

    public void playCard(){
        strPar.add(userNickname);
        gui.perform(intPar, strPar, null,4);

        intPar.clear();
        strPar.clear();
    }

    public void onMouseClicked(ArrayList<Pane> cards){
        for (Pane card:cards) {
            if(!card.isDisable()) {
                card.setOnMouseClicked(e -> {
                    card.setDisable(true);
                    intPar.add(cards.indexOf(card));
                    playCard();
                });
            }
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

    public void disableAllCards(boolean b) {
        card0.setDisable(b);
        card1.setDisable(b);
        card2.setDisable(b);
        card3.setDisable(b);
        card4.setDisable(b);
        card5.setDisable(b);
        card6.setDisable(b);
        card7.setDisable(b);
        card8.setDisable(b);
        card9.setDisable(b);
        if (b)
            setCardsCursor(Cursor.DEFAULT);
        else
            setCardsCursor(Cursor.HAND);
    }


    private void setCardsCursor(Cursor c){
        card0.setCursor(c);
        card1.setCursor(c);
        card2.setCursor(c);
        card3.setCursor(c);
        card4.setCursor(c);
        card5.setCursor(c);
        card6.setCursor(c);
        card7.setCursor(c);
        card8.setCursor(c);
        card9.setCursor(c);
    }

    public void setCards(){
        for(int i=1; i<update.handPlayer.get(userIndex()).size(); i=i+2){
            switch (update.handPlayer.get(userIndex()).get(i)) {
                case 1:
                    card0.setDisable(false);
                    card0.setVisible(true);
                    card0.setCursor(Cursor.HAND);
                    break;
                case 2:
                    card1.setDisable(false);
                    card1.setVisible(true);
                    card1.setCursor(Cursor.HAND);
                    break;
                case 3:
                    card2.setDisable(false);
                    card2.setVisible(true);
                    card2.setCursor(Cursor.HAND);
                    break;
                case 4:
                    card3.setDisable(false);
                    card3.setVisible(true);
                    card3.setCursor(Cursor.HAND);
                    break;
                case 5:
                    card4.setDisable(false);
                    card4.setVisible(true);
                    card4.setCursor(Cursor.HAND);
                    break;
                case 6:
                    card5.setDisable(false);
                    card5.setVisible(true);
                    card5.setCursor(Cursor.HAND);
                    break;
                case 7:
                    card6.setDisable(false);
                    card6.setVisible(true);
                    card6.setCursor(Cursor.HAND);
                    break;
                case 8:
                    card7.setDisable(false);
                    card7.setVisible(true);
                    card7.setCursor(Cursor.HAND);
                    break;
                case 9:
                    card8.setDisable(false);
                    card8.setVisible(true);
                    card8.setCursor(Cursor.HAND);
                    break;
                case 10:
                    card9.setDisable(false);
                    card9.setVisible(true);
                    card9.setCursor(Cursor.HAND);
                    break;
            }
        }
    }

    public void setVisibleCards(boolean b){
        card0.setVisible(b);
        card1.setVisible(b);
        card2.setVisible(b);
        card3.setVisible(b);
        card4.setVisible(b);
        card5.setVisible(b);
        card6.setVisible(b);
        card7.setVisible(b);
        card8.setVisible(b);
        card9.setVisible(b);
    }

    public void backToBoard(){
        gui.setScene("fxml/board.fxml");
        gui.boardController.refresh();
    }

}
