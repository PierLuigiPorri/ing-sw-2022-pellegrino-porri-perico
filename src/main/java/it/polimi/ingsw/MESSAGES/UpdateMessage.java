package it.polimi.ingsw.MESSAGES;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UpdateMessage extends MessageType{

    public String update;
    public boolean gameEnded;
    public int game_Type;
    public int nPlayers;
    public ArrayList<String> order;
    public String phase;
    public int turnNumber;
    public int cloudsNumber;

    public LinkedHashMap<Integer, ArrayList<Integer>> handPlayer;

    /*Player attributes:
     attributes which end by 0 refer to order.get(0);
     attributes which end by 1 refer to order.get(1);
     attributes which end by 2 refer to order.get(2);*/

    public ArrayList<Integer> coinsOnPlayer;     //[positions] 0:order.get(0); 1: order.get(1); 2: order.get(2);
    public ArrayList<Integer> towersOnPlayer;    //[positions] 0:order.get(0); 1: order.get(1); 2: order.get(2);


    public LinkedHashMap<Integer, ArrayList<String>> gatePlayer;

    //[positions] 0: red; 1: blue; 2: green; 3: yellow; 4:pink;
    public LinkedHashMap<Integer, ArrayList<Integer>> hallPlayer;


    //[positions] 0: red; 1: blue; 2: green; 3: yellow; 4:pink;
    public LinkedHashMap<Integer, ArrayList<Boolean>> professors;


    public ArrayList<Integer> lastCardPlayed;
    //[positions] of lastCardPlayed:
    // 0:Movement of the last card played by game.getOrder(0); 1: Value of the last card played by game.getOrder(0);
    // 2: Movement of the last card played by game.getOrder(1); 3: Value of the last card played by game.getOrder(1);
    // 4:Movement of the last card played by game.getOrder(2); 5:Value of the last card played by game.getOrder(2);

    public int numIslands;

    public LinkedHashMap<Integer, ArrayList<String>> studentsOnIsland;

    public ArrayList<Boolean> motherNatureOnIsland; //[positions] 0: island1; 1: island2 ... True if and only if MotherNature is on THAT island;

    public ArrayList<Integer> towersOnIsland; //[positions] 0: island1; 1: island2 ... number of tower on every island;
    public ArrayList<String> whoOwnTowers; //[positions] 0: who owns island1, 1: who owns island2...

    public LinkedHashMap<Integer, ArrayList<String>> studentsOnCloud;

    public int charactersNum; // can be 0 or 3, depends on GameType.
    public ArrayList<Integer> idCharacter; //if GameType=1, then here there will be the three id of the three characters in game.
    public ArrayList<Boolean> activated;   // 0: true if and only if character in position 0 has been already activated. 1: true if and only if character in position 1 has been already activated...
    public ArrayList<Integer> cardCost;
    public int numTD;

    public ArrayList<String> players;
    public ArrayList<Boolean> numTDOnIsland;

    public LinkedHashMap<Integer, ArrayList<String>> studentsOnCard;
    public int MNbonus;
    public ArrayList<Integer> playersMoves;

    public UpdateMessage() {
        super(4);
        this.order = new ArrayList<>();
        this.coinsOnPlayer = new ArrayList<>();
        this.towersOnPlayer = new ArrayList<>();
        this.players=new ArrayList<>();
        this.lastCardPlayed = new ArrayList<>();
        this.motherNatureOnIsland = new ArrayList<>();
        this.towersOnIsland = new ArrayList<>();
        this.idCharacter = new ArrayList<>();
        this.activated = new ArrayList<>();
        this.cardCost = new ArrayList<>();
        this.numTDOnIsland = new ArrayList<>();

        this.playersMoves = new ArrayList<>();
        this.whoOwnTowers = new ArrayList<>();

        this.studentsOnIsland= new LinkedHashMap<>();
        this.gatePlayer= new LinkedHashMap<>();
        this.handPlayer= new LinkedHashMap<>();
        this.hallPlayer= new LinkedHashMap<>();
        this.professors= new LinkedHashMap<>();
        this.studentsOnCloud= new LinkedHashMap<>();
        this.studentsOnCard= new LinkedHashMap<>();
    }
}
