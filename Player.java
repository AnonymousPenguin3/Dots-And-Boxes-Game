package DecemberBreakWork.DotsAndBoxes;

public interface Player {
    // return true means the player continues to play, false means switch to other player
    boolean playTurn();

    char getMark();
}
