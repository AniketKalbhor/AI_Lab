import java.util.Scanner;

public class TicTacToeAlphaBeta {
    static final int SIZE = 3;
    static char[][] board = {
            { '_', '_', '_' },
            { '_', '_', '_' },
            { '_', '_', '_' }
    };

    static final char HUMAN = 'X';
    static final char AI = 'O';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe - You are 'X', AI is 'O'");

        printBoard();

        while (true) {
            // Human Move
            humanMove(scanner);
            printBoard();
            if (isGameOver()) break;

            // AI Move
            System.out.println("AI is making a move...");
            Move bestMove = findBestMove();
            board[bestMove.row][bestMove.col] = AI;
            printBoard();
            if (isGameOver()) break;
        }
        scanner.close();
    }

    // Minimax with Alpha-Beta Pruning
    static int minimax(int depth, boolean isMax, int alpha, int beta) {
        int score = evaluate();

        if (score == 10 || score == -10) return score;
        if (!movesLeft()) return 0;

        if (isMax) {  // Maximizing player (AI)
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = AI;
                        best = Math.max(best, minimax(depth + 1, false, alpha, beta));
                        board[i][j] = '_';
                        alpha = Math.max(alpha, best);
                        if (beta <= alpha) return best;
                    }
                }
            }
            return best;
        } else {  // Minimizing player (Human)
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == '_') {
                        board[i][j] = HUMAN;
                        best = Math.min(best, minimax(depth + 1, true, alpha, beta));
                        board[i][j] = '_';
                        beta = Math.min(beta, best);
                        if (beta <= alpha) return best;
                    }
                }
            }
            return best;
        }
    }

    // AI chooses the best move
    static Move findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = AI;
                    int moveVal = minimax(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = '_';

                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    // Evaluate board score
    static int evaluate() {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == AI) return 10;
                if (board[row][0] == HUMAN) return -10;
            }
        }
        for (int col = 0; col < SIZE; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == AI) return 10;
                if (board[0][col] == HUMAN) return -10;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == AI) return 10;
            if (board[0][0] == HUMAN) return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == AI) return 10;
            if (board[0][2] == HUMAN) return -10;
        }
        return 0;
    }

    // Check if there are moves left
    static boolean movesLeft() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == '_') return true;
        return false;
    }

    // Check if the game is over
    static boolean isGameOver() {
        int score = evaluate();
        if (score == 10) {
            System.out.println("AI Wins! 😎");
            return true;
        } else if (score == -10) {
            System.out.println("You Win! 🎉");
            return true;
        } else if (!movesLeft()) {
            System.out.println("It's a Draw! 🤝");
            return true;
        }
        return false;
    }

    // Human player's move
    static void humanMove(Scanner scanner) {
        int row, col;
        while (true) {
            System.out.print("Enter row and column (0-2): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] == '_') {
                board[row][col] = HUMAN;
                break;
            }
            System.out.println("Invalid move. Try again.");
        }
    }

    // Print the board
    static void printBoard() {
        System.out.println("Current Board:");
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Helper class for moves
    static class Move {
        int row, col;
        Move(int r, int c) { row = r; col = c; }
    }
}
