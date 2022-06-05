package it.polimi.ingsw.CLIENT.GUIobjects;


import java.util.ArrayList;

public abstract class CoordinatesData {
    private final static ArrayList<Coordinates> gateStudents=new ArrayList<>();

    private final static ArrayList<Coordinates> islandStudents= new ArrayList<>();

    private final static Coordinates towers=new Coordinates(67,57);

    private final static Coordinates motherNature=new Coordinates(108,3);
    private final static ArrayList<Coordinates> clouds3=new ArrayList<>();
    private final static ArrayList<Coordinates> clouds2=new ArrayList<>();

    public static void loadCoordinates(){
        loadGate();
        loadIslands();
        loadClouds();
    }

    private static void loadGate(){
        gateStudents.add(0, new Coordinates(37,629));
        gateStudents.add(1, new Coordinates(86,608));
        gateStudents.add(2, new Coordinates(86,651));
        gateStudents.add(3,new Coordinates(136,608));
        gateStudents.add(4,new Coordinates(136,651));
        gateStudents.add(5,new Coordinates(188,608));
        gateStudents.add(6,new Coordinates(188,651));
        gateStudents.add(7,new Coordinates(236,608));
        gateStudents.add(8,new Coordinates(236,651));
    }

    private static void loadIslands(){
        //RED,BLUE,GREEN,YELLOW,PINK
        islandStudents.add(new Coordinates(74,24));
        islandStudents.add(new Coordinates(105,52));
        islandStudents.add(new Coordinates(105,94));
        islandStudents.add(new Coordinates(37,94));
        islandStudents.add(new Coordinates(37,52));
    }

    private static void loadClouds(){
        clouds3.add(new Coordinates(90,24));
        clouds3.add(new Coordinates(14,49));
        clouds3.add(new Coordinates(73,103));
        clouds2.add(new Coordinates(56,59));
        clouds2.add(new Coordinates(90,24));
        clouds2.add(new Coordinates(14,49));
        clouds2.add(new Coordinates(73,103));
    }

    public static Coordinates getTowersCoordinates() {
        return towers;
    }

    public static Coordinates getMotherNatureCoordinates() {
        return motherNature;
    }

    public static ArrayList<Coordinates> getGate() {
        return gateStudents;
    }

    public static Coordinates getIsland(String color) {
        switch (color){
            case "RED":
                return islandStudents.get(0);
            case "BLUE":
                return islandStudents.get(1);
            case "GREEN":
                return islandStudents.get(2);
            case "YELLOW":
                return islandStudents.get(3);
            case "PINK":
                return islandStudents.get(4);
            default:return null;
        }
    }

    public static int getIndex(Coordinates c){
        for (Coordinates x : gateStudents) {
            if(c.getX() == x.getX() && c.getY() == x.getY())
                return gateStudents.indexOf(x);
        }
        return -1;
    }

    public static ArrayList<Coordinates> getClouds2() {
        return clouds2;
    }

    public static ArrayList<Coordinates> getClouds3() {
        return clouds3;
    }
}
