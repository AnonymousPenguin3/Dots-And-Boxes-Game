package DecemberBreakWork.DotsAndBoxes;

public class OneClassDotsGame {
    private final boolean[][] edges;
    private final char[][] board;
    private final int edgeRows;
    private final int edgeCols;
    private final int rows;
    private final int cols;

    private char winner;
    private char currentPlayerMark = 'A';
    private int numOfPlayerASquares;
    private int numOfPlayerBSquares;
    private char PlayerA = 'A';
    private char PlayerB = 'B';


    public static void main(String[] argv) {
        // ask to run the game in a loop
        String finishGame;
        OneClassDotsGame game;
        do {
            game = new OneClassDotsGame(4, 3);
            game.initializeBoard();
            game.playDots();
            game.printBoard();
            game.findWinner();
            System.out.println("Player " + game.winner + " won.");
            System.out.print("Do you want to play again? :");
            finishGame = DecemberBreakWork.TicTacToe.TextIO.getlnString();
        } while(!finishGame.startsWith("n"));

        System.out.println("You have finished playing DemeberBreakWork.TicTacToe. Thanks for playing.");
    }

    public OneClassDotsGame(int row, int columns) {
        edges = new boolean[row * 2 + 1][columns + 1];
        edgeRows = row * 2 + 1;
        edgeCols = columns + 1;
        board = new char[row][columns];
        rows = row;
        cols = columns;
    }

    public OneClassDotsGame() {
        this(4,4);
    }

    private void playDots() {
        System.out.println("********Welcome to Dots.********");
        System.out.println("Here are the rules: ");
        System.out.println("1. The first person will start as player A and the second player B");
        System.out.println("2. The player who has the most boxes after the end of the game will win.");
        System.out.println("3. To get a box you must place a line last and get the boxes.");
        System.out.println("4. You cannot place a line that has both odd rows and columns, this means nothing");
        System.out.println("5. You cannot place a line that has both even rows and columns, this means a dot.");
        int typeGame;
        do {
            System.out.print("Would you like to play (1)2 player or (2)against a computer? : ");
            typeGame = DecemberBreakWork.TicTacToe.TextIO.getlnInt();
            if (!(typeGame == 1 || typeGame == 2)) {
                System.out.print("Please re-enter a valid input. (1) 2 player or (2) against a computer? : ");
            } else if(typeGame == 1) {
                humanTwoPlayerGame();
            } else {
                computerOpponentGame();
            }
        } while(!checkForWin() || isBoardFull());
    }

    private void printBoard() {
        for (int col = 0; col <= 2 * (this.edgeCols - 1); col++) {
            System.out.print(col + " ");
        }
        System.out.println("");

        for (int row = 0; row < this.edgeRows; row++) {
            for (int col = 0; col <= 2 * (this.edgeCols - 1); col++) {
                if (row % 2 == 0) {
                    if (col % 2 == 1) {
                        if (edges[row][col/2]) {
                            System.out.print("---");
                        } else {
                            System.out.print("   ");
                        }
                    } else {
                        System.out.print("*");
                    }
                } else {
                    if (col % 2 == 0) {
                        if (edges[row][col/2]) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } else {
                        System.out.print(" " + board[row / 2][col / 2] + " ");
                    }
                }
            }
            System.out.print("  " + row);
            System.out.println();
        }
    }

    // Set/Reset the board back to all empty values.
    private void initializeBoard() {
        // Loop through rows
        for (int i = 0; i < edgeRows; i++) {
            // Loop through columns
            for (int j = 0; j < edgeCols; j++) {
                edges[i][j] = false;
            }
        }

        for (int i =0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Loop through all cells of the board and if one is found to be empty (contains char '-') then return false.
    // Otherwise the board is full.
    private boolean isBoardFull() {
        for (int i = 0; i < edgeRows; i++) {
            for (int j = 0; j < edgeCols; j++) {
                if (edges[i][j] == false) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkForWin() {
        if (isBoardFull()) {
            if (numOfPlayerASquares > numOfPlayerBSquares) {
                winner = PlayerA;
                return true;
            } else if (numOfPlayerASquares < numOfPlayerBSquares) {
                winner = PlayerB;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void placeLine(int row, int col) {
        // Make sure that row and column are in bounds of the board.
            if ((row >= 0) && (row < edgeRows)) {
                if ((col >= 0) && (col/2 < edgeCols)) {
                    if (!edges[row][col/2]) {
                        edges[row][col/2] = true;
                        return;
                    }
                }
            }
    }

    private void humanTurn() {
        int point1Row;
        int point1Column;
        System.out.println("Here is the current board:");
        printBoard();
        do {
            System.out.println("Player " + currentPlayerMark + "'s turn.");
            System.out.print("What is the value of the row of the line? : ");
            point1Row = TextIO.getlnInt();
            System.out.print("What is the value of the column of the line? : ");
            point1Column = TextIO.getlnInt();
        } while (!isValidMove(point1Row, point1Column));
        placeLine(point1Row, point1Column);
        if (doesCompleteSquare(point1Row, point1Column)) {
            System.out.println("You earned a square!!");
            currentPlayerMark = currentPlayerMark;
        } else if (currentPlayerMark == 'A') {
            currentPlayerMark = 'B';
        } else {
            currentPlayerMark = 'A';
        }
    }

    private void computerTurn() {
        for (int row = 0; row < edgeRows; row++) {
            for (int col = 0; col < edgeCols; col++) {
                if (edges[row][col] == false) {
                    edges[row][col] = true;
                    System.out.println("My turn! I played  " + currentPlayerMark + " on row " + row + ", column " + col);
                    if (doesCompleteSquare(row, col)) {
                        System.out.println("I earned a square!!");
                        currentPlayerMark = currentPlayerMark;
                    } else if (currentPlayerMark == 'A') {
                        currentPlayerMark = 'B';
                    } else {
                        currentPlayerMark = 'A';
                    }
                    return;
                }
            }
        }
    }

    private void humanTwoPlayerGame() {
        do {
            humanTurn();

        } while(!checkForWin() || isBoardFull());
    }

    private void computerOpponentGame() {
        boolean isHumanTurn = true;
        do {
            if (isHumanTurn) {
                humanTurn();
            } else {
                computerTurn();
                isHumanTurn = true;
            }
            
        } while(!checkForWin() || isBoardFull());
    }

    private boolean isSquareUp(int row, int col) {
        if (row % 2 == 0 && row != 0) {
            if (edges[row - 1][col/2] && edges[row - 2][col/2] && edges[row - 1][col/2 + 1] && edges[row][col/2]) {
                board[(row - 2)/2][col/2] = currentPlayerMark;
                return true;
            }
        }
        return false;
    }

    private boolean isSquareDown(int row, int col) {
        if (row % 2 == 0 && row != edgeRows - 1) {
            if (edges[row + 1][col/2] && edges[row][col/2] && edges[row + 1][col/2 + 1] && edges[row + 2][col/2]) {
                board[row /2][col/2] = currentPlayerMark;
                return true;
            }
        }
        return false;
    }

    private boolean isSquareLeft(int row, int col) {
        if (row % 2 == 1 && col != 0) {
            if (edges[row][col/2 - 1] && edges[row - 1][col/2 - 1] && edges[row][col/2] && edges[row + 1][col/2 - 1]) {
                board[(row - 1)/2][col/2 - 1] = currentPlayerMark;
                return true;
            }
        }
        return false;
    }

    private boolean isSquareRight(int row, int col) {
        if (row % 2 == 1 && col != cols * 2) {
            if (edges[row][col/2] && edges[row - 1][col/2] && edges[row][col/2 + 1] && edges[row + 1][col/2]) {
                board[(row - 1)/2][col/2] = currentPlayerMark;
                return true;
            }
        }
        return false;
    }

    private boolean doesCompleteSquare(int row, int col) {
        return (isSquareDown(row, col) | isSquareLeft(row, col) | isSquareRight(row, col) | isSquareUp(row, col));
    }

    private void findWinner() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == 'A') {
                    numOfPlayerASquares++;
                }
                if (board[row][col] == 'B') {
                    numOfPlayerBSquares++;
                }
            }
        }
    }

    private boolean isValidMove(int point1Row, int point1Column) {
        if ((point1Column % 2 == 0) && (point1Row % 2 == 0)) {
            System.out.println("You cannot place a line that has both even rows and columns, this means a dot. " +
                    "Please re-enter a position where you can draw a line");
            return false;
        }
        if ((point1Column % 2 == 1) && (point1Row % 2 == 1)) {
            System.out.println("You cannot place a line that has both odd rows and columns, this means nothing. " +
                    "Please re-enter a position where you can draw a line");
            return false;
        }
        if (point1Column > cols * 2 || point1Row > edgeRows || point1Column < 0 || point1Row < 0) {
            System.out.println("This position is not a position on the board. Please re-enter a position on the board.");
            return false;
        }
        if (edges[point1Row][point1Column / 2]) {
            System.out.println("This position has already been taken. Please re-enter an open position");
            return false;
        }
        return true;
    }
}
