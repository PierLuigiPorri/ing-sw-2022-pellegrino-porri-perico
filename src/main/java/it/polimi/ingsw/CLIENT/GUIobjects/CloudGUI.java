package it.polimi.ingsw.CLIENT.GUIobjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CloudGUI extends Pane {

    private final int index;


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
            this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/cloud_card_5_2_and_4_players_game.png")));
        }
        else{
            switch(index){
                case 0:
                    this.setLayoutX(699);
                    this.setLayoutY(171);
                    this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/cloud_card_1.png")));
                    break;
                case 1:
                    this.setLayoutX(574);
                    this.setLayoutY(291);
                    this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/cloud_card_2.png")));
                    break;
                case 2:
                    this.setLayoutX(837);
                    this.setLayoutY(291);
                    this.getChildren().add(new ImageView(new Image("src/main/resources/Graphical_Assets/cloud_card_3.png")));
                    break;
            }
        }
    }

    public int getIndex() {
        return index;
    }
}
