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

// Understanding the TicTacToe Minimax Algorithm
// Problem Statement:
// The TicTacToeMinMax.java implements a Tic Tac Toe game where a human player competes against an AI opponent that uses the Minimax algorithm to make optimal moves. The AI analyses possible future game states to choose the move that maximizes its chances of winning, while assuming the human player will also play optimally.
// Theory:
// 🔹 Minimax Algorithm: A decision-making algorithm used in two-player, zero-sum games (like Tic Tac Toe) where one player aims to maximize their score, and the other aims to minimize it. The algorithm recursively evaluates all possible future states of the game.
// 🔹 Zero-Sum Game: A mathematical representation of a situation where one player's gain is equivalent to another player's loss, so the net change in total benefit is zero.
// 🔹 Game Tree: A tree where each node represents a game state, and edges represent possible moves leading to new states.
// Explanation of General Approach:
// The program creates a 3x3 Tic Tac Toe board and allows the human player (X) to play against the AI (O). The AI uses the Minimax algorithm to determine the optimal move by:

// Generating all possible future game states
// Evaluating each state with scores (+10 for AI win, -10 for human win, 0 for draw)
// Choosing the move that maximizes the AI's score, assuming the human player will play optimally

// Functions Explained:
// ⚙️ printBoard(): Displays the current state of the Tic Tac Toe board with dividing lines.
// ⚙️ isMovesLeft(): Checks if there are empty spaces left on the board where moves can be made.
// ⚙️ evaluate(): Analyzes the current board state to determine if someone has won, returning +10 for AI win, -10 for human win, or 0 if no winner.
// ⚙️ minimax(): The core recursive algorithm that evaluates possible game states. It takes a boolean parameter "isMax" to alternate between maximizing player (AI) and minimizing player (human).
// ⚙️ findBestMove(): Uses the minimax algorithm to find the optimal move for the AI by evaluating all possible moves and selecting the one with the highest score.
// ⚙️ main(): Controls the game flow, alternating between human input and AI moves until the game ends.
// Variables Used:
// 📊 board[][]: 3x3 character array representing the game board, initialized with spaces.
// 📊 isMax: Boolean parameter in minimax() that determines whose turn it is (true for AI, false for human).
// 📊 bestVal: Tracks the best evaluation score found during minimax search.
// 📊 bestMove: Stores the coordinates of the best move found.
// Flow of Execution:

// Game starts with an empty board displayed to the user
// Human player enters their move coordinates (row and column)
// Move is validated and placed on the board
// The program checks if the human has won or if the board is full (draw)
// The AI calls findBestMove() to determine its optimal move
// The minimax algorithm recursively evaluates possible game states:

// For AI's turn (maximizing player), it selects the move with highest value
// For human's turn (minimizing player), it assumes human will select the move with lowest value


// AI makes its move and the board is updated
// The program checks if the AI has won or if the board is full
// Steps 2-8 repeat until someone wins or the game ends in a draw

// State Space Tree:
//               Initial Board
//                    |
//        +-----------+-----------+
//        |           |           |
//   AI places O    AI places O  AI places O
//   at (0,0)      at (0,1)     at (0,2)
//        |           |           |
//   /   |   \    /   |   \   /   |   \
//  ... ... ... ... ... ... ... ... ...
// The complete tree would be very large (9! = 362,880 possible game sequences), but the minimax algorithm efficiently evaluates the most promising branches.
// In this implementation, the AI always plays optimally, making it either win or force a draw against a human player who doesn't play perfectly.