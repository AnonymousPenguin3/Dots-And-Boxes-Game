package DecemberBreakWork.DotsAndBoxes;

public class ComputerPlayer implements Player {
    private Board board;
    private char currentPlayerMark;

    public ComputerPlayer(Board board, char currentPlayerMark) {
        this.board = board;
        this.currentPlayerMark = currentPlayerMark;
    }

    @Override
    public boolean playTurn() {
        return board.fillFirstEmptyEdge(this);
    }

    @Override
    public char getMark() {
        return currentPlayerMark;
    }
}
