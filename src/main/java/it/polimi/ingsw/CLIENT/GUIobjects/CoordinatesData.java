package it.polimi.ingsw.CLIENT.GUIobjects;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores and returns the Coordinates of all the GUI objects. Static class so it can be accessed from anywhere. Abstract because it does not need to be instantiated.
 * @author GC56
 */
public abstract class CoordinatesData {
    private final static ArrayList<Coordinates> gateStudents=new ArrayList<>();

    private final static ArrayList<Coordinates> islandStudents= new ArrayList<>();

    private final static Coordinates towers=new Coordinates(42,57);

    private final static Coordinates motherNature=new Coordinates(87,-15);
    private final static ArrayList<Coordinates> clouds3=new ArrayList<>();
    private final static ArrayList<Coordinates> clouds2=new ArrayList<>();
    private final static ArrayList<Coordinates> colorButtons= new ArrayList<>();
    private final static ArrayList<Coordinates> cardStudents4=new ArrayList<>();
    private final static ArrayList<Coordinates> cardStudents6=new ArrayList<>();

    private final static HashMap<Integer, ArrayList<Coordinates>> islands=new HashMap<>();

    /**
     * Loads and stores the coordinates of all objects.
     */
    public static void loadCoordinates(){
        loadGate();
        loadIslands();
        loadClouds();
        loadSelections();
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

    private static void loadSelections(){
        //RED,BLUE,GREEN,YELLOW,PINK
        colorButtons.add(new Coordinates(46,68));
        colorButtons.add(new Coordinates(230,68));
        colorButtons.add(new Coordinates(414,68));
        colorButtons.add(new Coordinates(580,68));
        colorButtons.add(new Coordinates(769,68));
        cardStudents4.add(new Coordinates(21,39));
        cardStudents4.add(new Coordinates(21,87));
        cardStudents4.add(new Coordinates(104,21));
        cardStudents4.add(new Coordinates(104,87));
        cardStudents6.add(new Coordinates(21,8));
        cardStudents6.add(new Coordinates(21,56));
        cardStudents6.add(new Coordinates(21,97));
        cardStudents6.add(new Coordinates(104,97));
        cardStudents6.add(new Coordinates(104,56));
        cardStudents6.add(new Coordinates(104,8));
    }

    private static void loadIslands(){
        //RED,BLUE,GREEN,YELLOW,PINK
        islandStudents.add(new Coordinates(74,24));
        islandStudents.add(new Coordinates(105,52));
        islandStudents.add(new Coordinates(105,94));
        islandStudents.add(new Coordinates(37,94));
        islandStudents.add(new Coordinates(37,52));

        ArrayList<Coordinates> coord=new ArrayList<>();
        coord.add(new Coordinates(684,14));
        coord.add(new Coordinates(864,404));
        coord.add(new Coordinates(504,404));
        islands.put(3,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(684,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(684,404));
        coord.add(new Coordinates(324,214));
        islands.put(4,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(566,14));
        coord.add(new Coordinates(808,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(684,404));
        coord.add(new Coordinates(324,214));
        islands.put(5,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(566,14));
        coord.add(new Coordinates(808,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(808,404));
        coord.add(new Coordinates(566,404));
        coord.add(new Coordinates(324,214));
        islands.put(6,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(466,14));
        coord.add(new Coordinates(684,14));
        coord.add(new Coordinates(907,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(808,404));
        coord.add(new Coordinates(566,404));
        coord.add(new Coordinates(324,214));
        islands.put(7,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(466,14));
        coord.add(new Coordinates(684,14));
        coord.add(new Coordinates(907,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(907,404));
        coord.add(new Coordinates(684,404));
        coord.add(new Coordinates(466,404));
        coord.add(new Coordinates(324,214));
        islands.put(8,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(403,14));
        coord.add(new Coordinates(579,14));
        coord.add(new Coordinates(759,14));
        coord.add(new Coordinates(939,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(907,404));
        coord.add(new Coordinates(684,404));
        coord.add(new Coordinates(466,404));
        coord.add(new Coordinates(324,214));
        islands.put(9,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(403,14));
        coord.add(new Coordinates(579,14));
        coord.add(new Coordinates(759,14));
        coord.add(new Coordinates(939,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(939,404));
        coord.add(new Coordinates(759,404));
        coord.add(new Coordinates(579,404));
        coord.add(new Coordinates(403,404));
        coord.add(new Coordinates(324,214));
        islands.put(10,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(324,14));
        coord.add(new Coordinates(504,14));
        coord.add(new Coordinates(684,14));
        coord.add(new Coordinates(864,14));
        coord.add(new Coordinates(1044,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(939,404));
        coord.add(new Coordinates(759,404));
        coord.add(new Coordinates(579,404));
        coord.add(new Coordinates(403,404));
        coord.add(new Coordinates(324,214));
        islands.put(11,coord);
        coord=new ArrayList<>();
        coord.add(new Coordinates(324,14));
        coord.add(new Coordinates(504,14));
        coord.add(new Coordinates(684,14));
        coord.add(new Coordinates(864,14));
        coord.add(new Coordinates(1044,14));
        coord.add(new Coordinates(1044,214));
        coord.add(new Coordinates(1044,404));
        coord.add(new Coordinates(864,404));
        coord.add(new Coordinates(684,404));
        coord.add(new Coordinates(504,404));
        coord.add(new Coordinates(324,404));
        coord.add(new Coordinates(324,214));
        islands.put(12,coord);
    }

    private static void loadClouds(){
        clouds2.add(new Coordinates(90,24));
        clouds2.add(new Coordinates(14,49));
        clouds2.add(new Coordinates(73,103));
        clouds3.add(new Coordinates(56,59));
        clouds3.add(new Coordinates(90,24));
        clouds3.add(new Coordinates(14,49));
        clouds3.add(new Coordinates(73,103));
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

    /**
     * Returns the coordinates on an IslandGUI of a Student of the given color.
     * @param color The chosen color. String parameter.
     * @return Returns the coordinates of the given color on the class IslandGUI, if the color is correct. Returns Coordinates(0,0) otherwise.
     */
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
        }
        return new Coordinates(0,0);
    }

    /**
     * Returns the index of a given student in the gate based off its coordinates.
     * @param c The coordinates of the given student.
     * @return  The index of the student in the gate. Int value. -1 if the coordinates do not match.
     */
    public static int getIndex(Coordinates c){
        for (Coordinates x : gateStudents) {
            if(c.getX() == x.getX() && c.getY() == x.getY())
                return gateStudents.indexOf(x);
        }
        return -1;
    }

    public static Coordinates getButtons(String color){
        switch (color){
            case "RED":
                return colorButtons.get(0);
            case "BLUE":
                return colorButtons.get(1);
            case "GREEN":
                return colorButtons.get(2);
            case "YELLOW":
                return colorButtons.get(3);
            case "PINK":
                return colorButtons.get(4);
        }
        return new Coordinates(0,0);
    }

    public static ArrayList<Coordinates> getCardStudents(int num){
        if(num==4)
            return cardStudents4;
        else
            return cardStudents6;
    }

    public static ArrayList<Coordinates> getClouds2() {
        return clouds2;
    }

    public static ArrayList<Coordinates> getClouds3() {
        return clouds3;
    }

    public static ArrayList<Coordinates> getIslandsCoord(int dim){
        return islands.get(dim);
    }
}
