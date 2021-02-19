package DecemberBreakWork.DotsAndBoxes;

public class DotsGame {
    private final Board board;
    private final Player firstPlayer;
    private final Player secondPlayer;

    private static final char firstPlayerMark = 'A';
    private static final char secondPlayerMark = 'B';

    public static void main(String[] argv) {
        System.out.println("********Welcome to Dots.********");
        System.out.println("Here are the rules: ");
        System.out.println("1. The first person will start as player A and the second player B");
        System.out.println("2. The player who has the most boxes after the end of the game will win.");
        System.out.println("3. To get a box you must place a line last and get the boxes.");
        System.out.println("4. You cannot place a line that has both odd rows and columns, this means nothing");
        System.out.println("5. You cannot place a line that has both even rows and columns, this means a dot.");

        // ask to run the game in a loop
        String finishGame;
        DotsGame game;
        do {
            System.out.print("Would you like to play (1)2 player or (2)against a computer? : ");
            int typeGame = DecemberBreakWork.TicTacToe.TextIO.getlnInt();
            if (!(typeGame == 1 || typeGame == 2)) {
                System.out.print("Please re-enter a valid input. (1) 2 player or (2) against a computer? : ");
            } else {
                game = new DotsGame(4, 3, typeGame);
                Player winner = game.playDots();
                System.out.println("Player " + winner.getMark() + " won.");
            }
            System.out.print("Do you want to play still? :");
            finishGame = DecemberBreakWork.TicTacToe.TextIO.getlnString();
        } while(!finishGame.startsWith("n"));

        System.out.println("You have finished playing DemeberBreakWork.TicTacToe. Thanks for playing.");
    }

    public DotsGame(int row, int columns, int gameType) {
        board = new Board(row, columns);
        firstPlayer = new HumanPlayer(board, firstPlayerMark);
        if (gameType == 1) {
            secondPlayer = new HumanPlayer(board, secondPlayerMark);
        } else {
            secondPlayer = new ComputerPlayer(board, secondPlayerMark);
        }
    }

    private Player playDots() {
        Player currentPlayer = firstPlayer;
        do {
            if (!currentPlayer.playTurn()) {
                currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
            }
        } while(!board.isBoardFull());

        board.printBoard();
        return board.findWinner(firstPlayer, secondPlayer);
    }
}
