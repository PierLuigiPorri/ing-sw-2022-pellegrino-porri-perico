package it.polimi.ingsw.MODEL;

public class AbstractCharacter implements CharacterType {
    private int cost;
    private final int index;
    private boolean used=false;

    public AbstractCharacter(int index, int cost){
        this.cost=cost;
        this.index=index;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        Effects.apply(this.index, game, player);
        effectUsed();
    }

    @Override
    public void effectUsed() {
        if(!this.used){
            this.cost++;
            this.used=true;
        }
    }
}
