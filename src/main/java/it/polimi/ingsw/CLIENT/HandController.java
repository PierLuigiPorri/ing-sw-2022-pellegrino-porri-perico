package it.polimi.ingsw.CLIENT;


import it.polimi.ingsw.MESSAGES.UpdateMessage;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * FXML Controller class for the Hand information scene. Displays the user's hand information and lets the user
 * select a card to play if the game logic permits it.
 * @author GC56
 */
public class HandController {
    private GUIAPP gui;
    private UpdateMessage update;
    final ArrayList<Integer> intPar=new ArrayList<>();
    final ArrayList<String> strPar=new ArrayList<>();
    private String userNickname;

    @FXML
    private Pane card0, card1, card2, card3, card4, card5, card6, card7, card8, card9;
    private final ArrayList<Integer> originalIndexes=new ArrayList<>();

    public void setGUI(GUIAPP guiApp) {
        gui = guiApp;
    }

    /**
     * Main method of the View. Builds and shows to the user all the appropriate GUI objects and the FXML scene.
     * Called by the GUIAPP when the scene is set. Builds the GUI objects and assigns them the methods for
     * the Mouse events.
     */
    public void refresh(){
        update=gui.getUpdate();
        this.userNickname=gui.getUserNickname();
        //originalIndexes init
        for(int i=0; i<10; i++){
            originalIndexes.add(-1);
        }

        disableAllCards(true);
        setVisibleCards(false);

        ArrayList<Pane> cards=new ArrayList<>();
        arrayBuild(cards, card0, card1, card2, card3, card4, card5, card6, card7, card8, card9);
        setCards();

        if(!update.phase.equals("Planning") || !update.order.get(0).equals(userNickname))
            disableAllCards(true);

        onMouseClicked(cards);
    }

    /**
     * Performs the playCard action request.
     */
    public void playCard(){
        strPar.add(userNickname);
        gui.perform(intPar, strPar, null,4);

        intPar.clear();
        strPar.clear();
    }

    /**
     * Called when a card is selected by the user. If the game logic permits it, starts the playCard action
     * request sequence.
     * @param cards The cards in the current hand of the user.
     */
    public void onMouseClicked(ArrayList<Pane> cards){
        for (Pane card:cards) {
            if(!card.isDisable()) {
                card.setOnMouseClicked(e -> {
                    card.setDisable(true);
                    intPar.add(originalIndexes.get(cards.indexOf(card)));
                    playCard();
                });
            }
        }
    }

    /**
     * Utility method used to build an array given the parameters.
     */
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

    /**
     * Builds the scene and shows the cards in the current hand of the user.
     */
    public void setCards(){
        for(int i=1; i<update.handPlayer.get(userIndex()).size(); i=i+2){
            switch (update.handPlayer.get(userIndex()).get(i)) {
                case 1:
                    card0.setDisable(false);
                    card0.setVisible(true);
                    card0.setCursor(Cursor.HAND);
                    originalIndexes.set(0, i/2);
                    break;
                case 2:
                    card1.setDisable(false);
                    card1.setVisible(true);
                    card1.setCursor(Cursor.HAND);
                    originalIndexes.set(1, i/2);
                    break;
                case 3:
                    card2.setDisable(false);
                    card2.setVisible(true);
                    card2.setCursor(Cursor.HAND);
                    originalIndexes.set(2, i/2);
                    break;
                case 4:
                    card3.setDisable(false);
                    card3.setVisible(true);
                    card3.setCursor(Cursor.HAND);
                    originalIndexes.set(3, i/2);
                    break;
                case 5:
                    card4.setDisable(false);
                    card4.setVisible(true);
                    card4.setCursor(Cursor.HAND);
                    originalIndexes.set(4, i/2);
                    break;
                case 6:
                    card5.setDisable(false);
                    card5.setVisible(true);
                    card5.setCursor(Cursor.HAND);
                    originalIndexes.set(5, i/2);
                    break;
                case 7:
                    card6.setDisable(false);
                    card6.setVisible(true);
                    card6.setCursor(Cursor.HAND);
                    originalIndexes.set(6, i/2);
                    break;
                case 8:
                    card7.setDisable(false);
                    card7.setVisible(true);
                    card7.setCursor(Cursor.HAND);
                    originalIndexes.set(7, i/2);
                    break;
                case 9:
                    card8.setDisable(false);
                    card8.setVisible(true);
                    card8.setCursor(Cursor.HAND);
                    originalIndexes.set(8, i/2);
                    break;
                case 10:
                    card9.setDisable(false);
                    card9.setVisible(true);
                    card9.setCursor(Cursor.HAND);
                    originalIndexes.set(9, i/2);
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

    /**
     * Method associated with an FXML button. Sets the scene back to the Board scene.
     */
    public void backToBoard(){
        gui.setScene("fxml/board.fxml");
        gui.boardController.refresh();
    }

}
