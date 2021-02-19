package DecemberBreakWork.DotsAndBoxes;

public class Board {
    private final boolean[][] edges;
    private final Player[][] board;
    private final int edgeRows;
    private final int edgeCols;
    private final int rows;
    private final int cols;

    public Board(int row, int columns) {
        this.edges = new boolean[row * 2 + 1][columns + 1];
        this.edgeRows = row * 2 + 1;
        this.edgeCols = columns + 1;
        this.board = new Player[row][columns];
        this.rows = row;
        this.cols = columns;
        initializeBoard();
    }

    public Board() {
        this(4, 4);
    }



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
                board[i][j] = null;
            }
        }
    }

    void printBoard() {
        for (int displayCol = 0; displayCol <= 2 * (this.edgeCols - 1); displayCol++) {
            System.out.print(displayCol + " ");
        }
        System.out.println("");

        for (int row = 0; row < this.edgeRows; row++) {
            for (int displayCol = 0; displayCol <= 2 * (this.edgeCols - 1); displayCol++) {
                if (row % 2 == 0) {
                    if (displayCol % 2 == 1) {
                        if (edges[row][displayCol/2]) {
                            System.out.print("---");
                        } else {
                            System.out.print("   ");
                        }
                    } else {
                        System.out.print("*");
                    }
                } else {
                    if (displayCol % 2 == 0) {
                        if (edges[row][displayCol/2]) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } else {
                        System.out.print(" " + getMark(board[row / 2][displayCol / 2]) + " ");
                    }
                }
            }
            System.out.print("  " + row);
            System.out.println();
        }
    }

    private char getMark(Player player) {
        return player == null ? ' ' : player.getMark();
    }

    boolean isBoardFull() {
        for (int i = 0; i < edgeRows; i++) {
            for (int j = 0; j < edgeCols; j++) {
                if (!edges[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeLine(int row, int displayCol) {
        // Make sure that row and column are in bounds of the board.
        if ((row >= 0) && (row < edgeRows)) {
            if ((displayCol >= 0) && (displayCol/2 < edgeCols)) {
                if (!edges[row][displayCol/2]) {
                    edges[row][displayCol/2] = true;
                    return;
                }
            }
        }
    }

    private boolean isSquareUp(int row, int displayCol, Player currentPlayer) {
        if (row % 2 == 0 && row != 0) {
            if (edges[row - 1][displayCol/2] && edges[row - 2][displayCol/2] &&
                    edges[row - 1][displayCol/2 + 1] && edges[row][displayCol/2]) {
                board[(row - 2)/2][displayCol/2] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    private boolean isSquareDown(int row, int displayCol, Player currentPlayer) {
        if (row % 2 == 0 && row != edgeRows - 1) {
            if (edges[row + 1][displayCol/2] && edges[row][displayCol/2] &&
                    edges[row + 1][displayCol/2 + 1] && edges[row + 2][displayCol/2]) {
                board[row /2][displayCol/2] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    private boolean isSquareLeft(int row, int displayCol, Player currentPlayer) {
        if (row % 2 == 1 && displayCol != 0) {
            if (edges[row][displayCol/2 - 1] && edges[row - 1][displayCol/2 - 1] &&
                    edges[row][displayCol/2] && edges[row + 1][displayCol/2 - 1]) {
                board[(row - 1)/2][displayCol/2 - 1] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    private boolean isSquareRight(int row, int displayCol, Player currentPlayer) {
        if (row % 2 == 1 && displayCol != cols * 2) {
            if (edges[row][displayCol/2] && edges[row - 1][displayCol/2] &&
                    edges[row][displayCol/2 + 1] && edges[row + 1][displayCol/2]) {
                board[(row - 1)/2][displayCol/2] = currentPlayer;
                return true;
            }
        }
        return false;
    }

    boolean doesCompleteSquare(int row, int displayCol, Player currentPlayer) {
        return (isSquareDown(row, displayCol, currentPlayer) | isSquareLeft(row, displayCol, currentPlayer) |
                isSquareRight(row, displayCol, currentPlayer) | isSquareUp(row, displayCol, currentPlayer));
    }

    boolean isValidMove(int point1Row, int point1Column) {
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

    public boolean fillFirstEmptyEdge(Player currentPlayer) {
        for (int row = 0; row < this.edgeRows; row++) {
            for (int displayCol = 0; displayCol <= 2 * (this.edgeCols - 1); displayCol++) {
                if (row % 2 == 0) {
                    if (displayCol % 2 == 1) {
                        if (!edges[row][displayCol/2]) {
                            System.out.println("My turn, I played " + currentPlayer.getMark() + " on row " + row + ", column " + displayCol);
                            return markMove(row, displayCol, currentPlayer);
                        }
                    }
                } else {
                    if (displayCol % 2 == 0) {
                        if (!edges[row][displayCol/2]) {
                            System.out.println("My turn, I played " + currentPlayer.getMark() + " on row " + row + ", column " + displayCol);
                            return markMove(row, displayCol, currentPlayer);
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean markMove(int edgeRow, int displayCol,Player currentPlayer) {
        assert !edges[edgeRow][displayCol/2];

        placeLine(edgeRow, displayCol);
        if (doesCompleteSquare(edgeRow, displayCol, currentPlayer)) {
            System.out.println("You earned a square!!");
            return true;
        }

        return false;
    }

    public Player findWinner(Player player1, Player player2) {
        int numOfPlayer1Squares = 0;
        int numOfPlayer2Squares = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == player1) {
                    numOfPlayer1Squares++;
                }
                if (board[row][col] == player2) {
                    numOfPlayer2Squares++;
                }
            }
        }
        
        return numOfPlayer1Squares > numOfPlayer2Squares ? player1 : player2;
    }

    public Player checkForWin(Player player1, Player player2) {
        if (isBoardFull()) {
            return findWinner(player1, player2);
        } else {
            return null;
        }
    }
}
