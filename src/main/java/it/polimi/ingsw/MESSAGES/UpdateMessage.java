package it.polimi.ingsw.MESSAGES;

import java.util.ArrayList;

public class UpdateMessage extends MessageType{
    public int game_Type;
    public int nPlayers;
    public ArrayList<String> order;
    public String phase;
    public int turnNumber;

    /*Players attributes:
     attributes which end by 0 refer to order.get(0);
     attributes which end by 1 refer to order.get(1);
     attributes which end by 2 refer to order.get(2);*/

    public ArrayList<Integer> coinsOnPlayer;     //[positions] 0:order.get(0); 1: order.get(1); 2: order.get(2);
    public ArrayList<Integer> towersOnPlayer;    //[positions] 0:order.get(0); 1: order.get(1); 2: order.get(2);

    public ArrayList<String> gatePlayer0;
    public ArrayList<String> gatePlayer1;
    public ArrayList<String> gatePlayer2;

    public ArrayList<Integer> hallPlayer0;      //[positions] 0: red; 1: blue; 2: green; 3: yellow; 4:pink;
    public ArrayList<Integer> hallPlayer1;
    public ArrayList<Integer> hallPlayer2;

    public ArrayList<Boolean> professors0;      //[positions] 0: red; 1: blue; 2: green; 3: yellow; 4:pink;
    public ArrayList<Boolean> professors1;
    public ArrayList<Boolean> professors2;
    public ArrayList<Integer> lastCardPlayed;
    //[positions] of lastCardPlayed:
    // 0:Movement of the last card played by game.getOrder(0); 1: Value of the last card played by game.getOrder(0);
    // 2: Movement of the last card played by game.getOrder(1); 3: Value of the last card played by game.getOrder(1);
    // 4:Movement of the last card played by game.getOrder(2); 5:Value of the last card played by game.getOrder(2);

    public int numIslands;
    public ArrayList<String> studentsOnIsland1;
    public ArrayList<String> studentsOnIsland2;
    public ArrayList<String> studentsOnIsland3;
    public ArrayList<String> studentsOnIsland4;
    public ArrayList<String> studentsOnIsland5;
    public ArrayList<String> studentsOnIsland6;
    public ArrayList<String> studentsOnIsland7;
    public ArrayList<String> studentsOnIsland8;
    public ArrayList<String> studentsOnIsland9;
    public ArrayList<String> studentsOnIsland10;
    public ArrayList<String> studentsOnIsland11;
    public ArrayList<String> studentsOnIsland12;

    public ArrayList<Boolean> motherNatureOnIsland; //[positions] 0: island1; 1: island2 ... True if and only if MotherNature is on THAT island;

    public ArrayList<Integer> towersOnIsland;      //[positions] 0: island1; 1: island2 ... number of tower on every island;

    public ArrayList<String> studentsOnCloud0;
    public ArrayList<String> studentsOnCloud1;
    public ArrayList<String> studentsOnCloud2;

    public int charactersNum; // can be 0 or 3, depends on GameType.
    public ArrayList<Integer> idCharacter; //if GameType=1, then here there will be the three id of the three characters in game.
    public ArrayList<Boolean> activated;   // 0: true if and only if character in position 0 has been already activated. 1: true if and only if character in position 1 has been already activated...
    public ArrayList<Integer> cardCost;
    public int numTD;
    public ArrayList<Boolean> numTDOnIsland;
    public ArrayList<String> studentsOnCard0;
    public ArrayList<String> studentsOnCard1;
    public ArrayList<String> studentsOnCard2;
    public int MNbonus;

    public UpdateMessage() {
        super(4);
        this.order = new ArrayList<>();
        this.coinsOnPlayer = new ArrayList<>();
        this.towersOnPlayer = new ArrayList<>();
        this.gatePlayer0 = new ArrayList<>();
        this.gatePlayer1 = new ArrayList<>();
        this.gatePlayer2 = new ArrayList<>();
        this.hallPlayer0 = new ArrayList<>();
        this.hallPlayer1 = new ArrayList<>();
        this.hallPlayer2 = new ArrayList<>();
        this.professors0 = new ArrayList<>();
        this.professors1 = new ArrayList<>();
        this.professors2 = new ArrayList<>();
        this.lastCardPlayed = new ArrayList<>();
        this.studentsOnIsland1 = new ArrayList<>();
        this.studentsOnIsland2 = new ArrayList<>();
        this.studentsOnIsland3 = new ArrayList<>();
        this.studentsOnIsland4 = new ArrayList<>();
        this.studentsOnIsland5 = new ArrayList<>();
        this.studentsOnIsland6 = new ArrayList<>();
        this.studentsOnIsland7 = new ArrayList<>();
        this.studentsOnIsland8 = new ArrayList<>();
        this.studentsOnIsland9 = new ArrayList<>();
        this.studentsOnIsland10 = new ArrayList<>();
        this.studentsOnIsland11 = new ArrayList<>();
        this.studentsOnIsland12 = new ArrayList<>();
        this.motherNatureOnIsland = new ArrayList<>();
        this.towersOnIsland = new ArrayList<>();
        this.studentsOnCloud0 = new ArrayList<>();
        this.studentsOnCloud1 = new ArrayList<>();
        this.studentsOnCloud2 = new ArrayList<>();
        this.idCharacter = new ArrayList<>();
        this.activated = new ArrayList<>();
        this.cardCost = new ArrayList<>();
        this.numTDOnIsland = new ArrayList<>();
        this.studentsOnCard0 = new ArrayList<>();
        this.studentsOnCard1 = new ArrayList<>();
        this.studentsOnCard2 = new ArrayList<>();
    }
}
