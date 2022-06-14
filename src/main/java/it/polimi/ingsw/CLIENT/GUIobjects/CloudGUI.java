package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;



public class CloudGUI extends Pane {

    private final int index;
    public Button button;


    public CloudGUI(int count, int index){
        this.index=index;
        this.setWidth(150);
        this.setHeight(150);
        if(count==2){
            switch (index){
                case 0:
                    this.setLayoutX(534);
                    this.setLayoutY(229);
                    break;
                case 1:
                    this.setLayoutX(864);
                    this.setLayoutY(229);
                    break;
            }
            ImageView image=new ImageView(new Image("Graphical_Assets/cloud_card_5_2_and_4_players_game.png"));
            image.setFitHeight(150);
            image.setFitWidth(150);
            this.getChildren().add(image);
        }
        else{
            switch(index){
                case 0:
                    this.setLayoutX(699);
                    this.setLayoutY(171);
                    ImageView image=new ImageView(new Image("Graphical_Assets/cloud_card_1.png"));
                    image.setFitHeight(150);
                    image.setFitWidth(150);
                    this.getChildren().add(image);
                    break;
                case 1:
                    this.setLayoutX(574);
                    this.setLayoutY(291);
                    image=new ImageView(new Image("Graphical_Assets/cloud_card_2.png"));
                    image.setFitHeight(150);
                    image.setFitWidth(150);
                    this.getChildren().add(image);
                    break;
                case 2:
                    this.setLayoutX(837);
                    this.setLayoutY(291);
                    image=new ImageView(new Image("Graphical_Assets/cloud_card_3.png"));
                    image.setFitHeight(150);
                    image.setFitWidth(150);
                    this.getChildren().add(image);
                    break;
            }
        }
    }

    public int getIndex() {
        return index;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
