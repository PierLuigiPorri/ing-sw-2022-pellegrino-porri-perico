package it.polimi.ingsw.MODEL;

public interface CharacterType {
    void applyEffect(Game game, Player player);
    void effectUsed();
    int getCost();
}
