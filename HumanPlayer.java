package DecemberBreakWork.DotsAndBoxes;

public class HumanPlayer implements Player{
    private Board board;
    private char currentPlayerMark;

    public HumanPlayer(Board board, char currentPlayerMark) {
        this.board = board;
        this.currentPlayerMark = currentPlayerMark;
    }

    @Override
    public boolean playTurn() {
        int point1Row;
        int point1Column;
        System.out.println("Here is the current board:");
        board.printBoard();

        do {
            System.out.println("Player " + currentPlayerMark + "'s turn.");
            System.out.print("What is the value of the row of the line? : ");
            point1Row = TextIO.getlnInt();
            System.out.print("What is the value of the column of the line? : ");
            point1Column = TextIO.getlnInt();
        } while (!board.isValidMove(point1Row, point1Column));
        return board.markMove(point1Row, point1Column, this);
    }

    @Override
    public char getMark() {
        return currentPlayerMark;
    }
}
