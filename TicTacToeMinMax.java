import java.util.*;

public class TicTacToeMinMax {
    static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    public static void printBoard() {
        System.out.println("-------------");
        for (char[] row : board) {
            System.out.print("| ");
            for (char cell : row) {
                System.out.print(cell + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public static boolean isMovesLeft() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') return true;
            }
        }
        return false;
    }

    public static int evaluate() {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == 'O') return +10;
                else if (board[row][0] == 'X') return -10;
            }
        }
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == 'O') return +10;
                else if (board[0][col] == 'X') return -10;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'O') return +10;
            else if (board[0][0] == 'X') return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'O') return +10;
            else if (board[0][2] == 'X') return -10;
        }
        return 0;
    }

    public static int minimax(boolean isMax) {
        int score = evaluate();
        if (score == 10 || score == -10) return score;
        if (!isMovesLeft()) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        best = Math.max(best, minimax(false));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        best = Math.min(best, minimax(true));
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }
    }

    public static int[] findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int moveVal = minimax(false);
                    board[i][j] = ' ';

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe!");
        printBoard();

        while (true) {
            System.out.print("Enter your move (row [0-2] and column [0-2]): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ') {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            board[row][col] = 'X';
            printBoard();

            if (evaluate() == -10) {
                System.out.println("You win!");
                break;
            }
            if (!isMovesLeft()) {
                System.out.println("It's a draw!");
                break;
            }

            System.out.println("AI is making a move...");
            int[] bestMove = findBestMove();
            board[bestMove[0]][bestMove[1]] = 'O';
            printBoard();

            if (evaluate() == 10) {
                System.out.println("AI wins!");
                break;
            }
            if (!isMovesLeft()) {
                System.out.println("It's a draw!");
                break;
            }
        }
        scanner.close();
    }
}
