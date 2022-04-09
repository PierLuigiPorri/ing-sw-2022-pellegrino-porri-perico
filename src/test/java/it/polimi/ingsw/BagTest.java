package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

public class BagTest {
    private Bag bag;
    private Game game;


    @Test
    public void addStudent () {
        ColorTracker color1= game.red;
        ColorTracker color2= game.blue;
        ColorTracker color3= game.green;
        ColorTracker color4= game.yellow;
        ColorTracker color5= game.pink;

        Assert.assertEquals(120, bag.getSize());

        Student student1=new Student(color1);
        Student student2=new Student(color2);
        Student student3=new Student(color3);
        Student student4=new Student(color4);
        Student student5=new Student(color5);

        try {
            bag.addStudent(color1);
            bag.addStudent(color1);
            Assert.assertEquals(bag.extractStudent().getColor().getColor(), student1.getColor().getColor());
            bag.addStudent(color2);
            Assert.assertEquals(bag.extractStudent().getColor().getColor(), student2.getColor().getColor());
            bag.addStudent(color3);
            Assert.assertEquals(bag.extractStudent().getColor().getColor(), student3.getColor().getColor());
            bag.addStudent(color5);
            Assert.assertEquals(bag.extractStudent().getColor().getColor(), student5.getColor().getColor());
            bag.addStudent(color4);
            Assert.assertEquals(bag.extractStudent().getColor().getColor(), student4.getColor().getColor());
            bag.addStudent(color4);
            bag.addStudent(color4);
            Assert.assertEquals(bag.extractStudent().getColor().getColor(), student4.getColor().getColor());
            Assert.assertEquals(122, bag.getSize());
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void extractStudent() {
        Assert.assertEquals(120, bag.getSize());

        try {
            bag.extractStudent();
            bag.extractStudent();
            bag.extractStudent();
            bag.extractStudent();
            bag.extractStudent();
            bag.extractStudent();
            bag.extractStudent();
            bag.extractStudent();
        }catch (ImpossibleActionException e){
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(112, bag.getSize());
    }

    @Before
    public void setUp() throws Exception {
        game=new Game(2, 0, "Pier", null, "Paolo", null, null, null);
        bag=new Bag();
    }

    @After
    public void tearDown() throws Exception {

    }
}