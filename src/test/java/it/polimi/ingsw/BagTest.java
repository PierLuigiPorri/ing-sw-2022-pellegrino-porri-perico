package it.polimi.ingsw;

import it.polimi.ingsw.EXCEPTIONS.ImpossibleActionException;
import it.polimi.ingsw.GAME.*;

import org.junit.*;

public class BagTest {
    private Bag bag;


    @Test
    public void addStudent () {


        Assert.assertEquals(120, bag.getSize());

        Student student1=new Student("RED");
        Student student2=new Student("BLUE");
        Student student3=new Student("GREEN");
        Student student4=new Student("YELLOW");
        Student student5=new Student("PINK");

        try {
            bag.addStudent("RED");
            bag.addStudent("RED");
            Assert.assertEquals(bag.extractStudent().getColor(), student1.getColor());
            bag.addStudent("BLUE");
            Assert.assertEquals(bag.extractStudent().getColor(), student2.getColor());
            bag.addStudent("GREEN");
            Assert.assertEquals(bag.extractStudent().getColor(), student3.getColor());
            bag.addStudent("PINK");
            Assert.assertEquals(bag.extractStudent().getColor(), student5.getColor());
            bag.addStudent("YELLOW");
            Assert.assertEquals(bag.extractStudent().getColor(), student4.getColor());
            bag.addStudent("YELLOW");
            bag.addStudent("YELLOW");
            Assert.assertEquals(bag.extractStudent().getColor(), student4.getColor());
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
    public void setUp()  {
        bag=new Bag();
    }

    @After
    public void tearDown()  {

    }
}