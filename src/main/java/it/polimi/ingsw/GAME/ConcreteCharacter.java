package it.polimi.ingsw.GAME;

public class ConcreteCharacter extends StudentSpace implements CharacterType{
    private int cost;
    private final int index;
    private boolean used=false;
    private int TD=4;
    private int MAX;
    private final Effects effects;

    public ConcreteCharacter(int index, int cost, Effects effects){
        this.effects=effects;
        this.cost=cost;
        this.index=index;
        effects.initializeConcrete(index, this);
    }


    @Override
    public void applyEffect(Player player) {
        effects.applyConcrete(this.index, player, this);
        effectUsed();
    }

    @Override
    public void effectUsed() {
        if(!this.used){
            this.cost++;
            this.used=true;
        }
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public void addStudent(String color) {
        //TODO:assert che ci sia spazio
        this.students.add(new Student(color));
    }

    @Override
    public void removeStudent(int index) {
        //TODO:assert che non sia vuoto
        this.students.remove(index);
    }

    public void setMAX(int val){
        this.MAX=val;
    }

    public int getMAX(){
        return this.MAX;
    }
    public void addTD(){
        //TODO: assert che siano max 4
        if(this.index==4) this.TD++;
    }
    public void removeTD(){
        //TODO: assert che non siano 0
        if(this.index==4) this.TD--;
    }
}
