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

// TicTacToe with Alpha-Beta Pruning Explanation
// Problem Statement:
// The code implements a Tic-Tac-Toe game where a human player competes against an AI opponent. The AI uses the Minimax algorithm with Alpha-Beta Pruning to make optimal decisions. The human player uses 'X' while the AI uses 'O', and they take turns placing their symbols on a 3x3 board until one player wins or the game ends in a draw.
// Theory:
// 🔹 Minimax Algorithm: A decision-making algorithm used in two-player games to find the optimal move. It works by evaluating all possible future game states and choosing the move that maximizes the AI's chances of winning while assuming the opponent plays optimally.
// 🔹 Alpha-Beta Pruning: An optimization technique for the Minimax algorithm that reduces the number of nodes evaluated. It stops evaluating a move when it determines that the move is worse than a previously examined move, which helps save computational resources.
// 🔹 Game Tree: A tree structure representing all possible game states, where each node is a board configuration and edges represent moves.
// Constraints:
// 🔹 Board size: 3x3 grid
// 🔹 Two players: Human (X) and AI (O)
// 🔹 Legal moves: Only empty cells can be selected
// 🔹 Win condition: Three of the same symbol in a row, column, or diagonal
// Input:
// 🔹 Human player inputs row and column coordinates (0-2) to place their 'X'
// Output:
// 🔹 Visual representation of the board after each move
// 🔹 Announcement of the game result (win, loss, or draw)
// ⚙️ Functions Explained

// main(): Entry point that initializes the game, handles the game loop, and alternates between human and AI moves until the game ends.
// minimax(depth, isMax, alpha, beta): Implements the Minimax algorithm with Alpha-Beta Pruning to determine the best possible move for the AI.

// Recursively evaluates possible moves to determine their score
// Uses alpha-beta pruning to avoid evaluating clearly inferior moves
// Returns a score value indicating how favorable a board position is for the AI


// findBestMove(): Uses the minimax function to evaluate all possible moves and selects the one with the highest score for the AI.
// evaluate(): Checks if any player has won by examining all rows, columns, and diagonals. Returns +10 if AI wins, -10 if human wins, or 0 if no winner yet.
// movesLeft(): Determines if there are any empty cells left on the board.
// isGameOver(): Checks if the game has ended (win or draw) and prints the result.
// humanMove(scanner): Handles the human player's input and updates the board accordingly.
// printBoard(): Displays the current state of the board.

// 📊 Variables Used

// SIZE: Constant defining the board dimensions (3x3)
// board[][]: 2D character array representing the game board
// HUMAN: Character representing the human player ('X')
// AI: Character representing the AI player ('O')
// alpha, beta: Parameters for alpha-beta pruning to optimize the search
// best: Tracks the best score during minimax evaluation
// Move class: Helper class to store row and column coordinates

// 🔁 Flow of Execution

// Game starts with an empty 3x3 board
// Game enters the main loop:

// Human player inputs coordinates for their move
// Board is updated and displayed
// Game checks if human has won or if the board is full (draw)
// AI calculates its optimal move using Minimax with Alpha-Beta Pruning
// Board is updated and displayed
// Game checks if AI has won or if the board is full (draw)


// Loop continues until someone wins or the game ends in a draw

// Short State Space Tree:
// Initial Board (Empty)
//         |
//    Human places X
//         |
//    AI evaluates possible moves using Minimax+Alpha-Beta
//         |
//    AI places O optimally
//         |
//    Human places X
//         |
//    AI evaluates (with pruning to avoid exploring bad moves)
//         |
//    AI places O optimally
//         |
//        ...
//         |
//    Game ends (Win/Loss/Draw)
// In each AI turn, the algorithm builds a game tree where:

// Maximizing player (AI) chooses moves with highest value
// Minimizing player (Human) chooses moves with lowest value
// Alpha-Beta pruning cuts off branches that won't affect the final decision
// The depth of the tree depends on remaining empty cells
// Evaluation assigns scores to terminal states (+10 for AI win, -10 for Human win, 0 for draw)

// This implementation ensures the AI makes optimal decisions while being computationally efficient through pruning techniques.