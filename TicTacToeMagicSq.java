
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeMagicSq {

    // Magic Square representation of Tic Tac Toe board
    static final int[][] MAGIC_SQUARE = {
        {8, 1, 6},
        {3, 5, 7},
        {4, 9, 2}
    };

    static final int SIZE = 3;
    static char[][] board = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    static boolean[] usedNumbers = new boolean[10]; // Track used numbers (1-9)
    static List<Integer> player1Moves = new ArrayList<>();
    static List<Integer> computerMoves = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean gameWon = false;
        int moves = 0;

        System.out.println("Welcome to Tic Tac Toe with AI!");
        printBoard();

        while (moves < 9 && !gameWon) {
            if (moves % 2 == 0) { // Player 1's turn
                System.out.println("Player 1 (X), enter a number (1-9):");
                int number = scanner.nextInt();

                // Validate input
                if (number < 1 || number > 9 || usedNumbers[number]) {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }

                // Mark number as used and update board
                usedNumbers[number] = true;
                player1Moves.add(number);
                int[] position = findPosition(number);
                board[position[0]][position[1]] = 'X';
                printBoard();

                // Check if Player 1 wins
                if (checkWin(player1Moves)) {
                    System.out.println("Player 1 (X) wins!");
                    gameWon = true;
                    break;
                }
            } else { // Computer's turn
                System.out.println("Computer (O) is making its move...");
                int computerMove = calculateComputerMove();
                usedNumbers[computerMove] = true;
                computerMoves.add(computerMove);
                int[] position = findPosition(computerMove);
                board[position[0]][position[1]] = 'O';
                printBoard();

                // Check if Computer wins
                if (checkWin(computerMoves)) {
                    System.out.println("Computer (O) wins!");
                    gameWon = true;
                    break;
                }
            }
            moves++;
        }

        if (!gameWon) {
            System.out.println("It's a draw!");
        }
    }

    // Find the position of the given number in the Magic Square board
    private static int[] findPosition(int number) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAGIC_SQUARE[i][j] == number) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Invalid number: " + number);
    }

    // Check if a player has won using the magic square logic
    private static boolean checkWin(List<Integer> moves) {
        int targetSum = 15;
        for (int i = 0; i < moves.size(); i++) {
            for (int j = i + 1; j < moves.size(); j++) {
                for (int k = j + 1; k < moves.size(); k++) {
                    if (moves.get(i) + moves.get(j) + moves.get(k) == targetSum) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Calculate the best move for the computer
    private static int calculateComputerMove() {
        int targetSum = 15;

        // Check if computer can win in this move
        for (int number = 1; number <= 9; number++) {
            if (!usedNumbers[number] && canFormWinningCombination(computerMoves, number)) {
                return number;
            }
        }

        // Block player's winning move
        for (int number = 1; number <= 9; number++) {
            if (!usedNumbers[number] && canFormWinningCombination(player1Moves, number)) {
                return number;
            }
        }

        // Pick a random available move
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(9) + 1;
        } while (usedNumbers[number]);
        return number;
    }

    // Check if adding a number to a list can form a winning combination
    private static boolean canFormWinningCombination(List<Integer> moves, int number) {
        int targetSum = 15;
        for (int i = 0; i < moves.size(); i++) {
            for (int j = i + 1; j < moves.size(); j++) {
                if (moves.get(i) + moves.get(j) + number == targetSum) {
                    return true;
                }
            }
        }
        return false;
    }

    // Print the Tic Tac Toe board
    private static void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print("|" + board[i][j]);
            }
            System.out.println("|");
            System.out.println("---------");
        }
    }
}

// TicTacToeMagicSq.java:

// Using Magic Squares for Tic-Tac-Toe AI
// Problem Statement:
// This code implements
// a variant
// of Tic-Tac-Toe using
// the concept
// of magic
// squares to
// determine winning
// conditions.Instead of
// the traditional
// approach of
// checking rows, columns, and diagonals,this
// implementation uses a 3×3
// magic square
// where any
// three numbers
// that sum to 15
// represent a
// winning combination.Theory:🔹
// Magic Square Concept:A 3×3
// magic square
// has numbers 1-9
// arranged so
// that each row,column,
// and diagonal
// sums to 15.
// The specific
// magic square
// used is:8 1 6 3 5 7 4 9 2🔹
// Winning Condition:
// A player
// wins when they'v
// e selected
// three positions
// whose corresponding
// numbers in
// the magic
// square sum to 15.🔹Input:
// Player moves
// in the
// form of numbers 1-9(
// corresponding to
// positions in
// the magic square).🔹Output:
// The current
// state of
// the board
// and game

// results (win, loss, or draw).
// ⚙️ Functions Explained

// main(): Entry point that manages game flow, alternating between player and computer moves until someone wins or the board is full.
// findPosition(): Converts a number (1-9) to its

// corresponding position (row, column) on the board by searching the magic square.
// checkWin(): Determines if a player has won by checking if any three numbers in their move list sum to 15.
// calculateComputerMove(): Determines the best move for the computer using three strategies:

// First checks if it can win in the current move
// If not, checks if it must block the player from winning
// Otherwise, selects a random available move


// canFormWinningCombination(): Helper function that checks if adding a number to a player's existing moves would form a sum of 15.
// printBoard(): Displays the current state of the Tic-Tac-Toe board.

// 📊 Variables Used

// MAGIC_SQUARE: A 3×3 array containing the magic square values.
// SIZE: Constant value 3, representing the size of the board.
// board: Character array representing the Tic-Tac-Toe board with 'X', 'O', and empty spaces.
// usedNumbers: Boolean array to track

// which numbers (1-9) have been chosen.
// player1Moves: List of numbers chosen by Player 1.
// computerMoves: List of numbers chosen by the computer.

// 🔁 Flow of Execution

// Initialize the game board and player move trackers.
// Display the empty board to the player.
// Enter the main game loop:

// If it's Player 1's turn:

// Prompt for

// a number (1-9)
// Validate the move
// Update the board and check for a win


// If it's the computer's turn:

// Calculate the best move
// Update the board and check for a win


// Increment move counter


// If the board is full without a winner, declare a draw.

// Short State Space Tree:
// Initial State: Empty board

// Level 1: Player's

// first move (possible positions: 1-9)
//    ↓
// Level 2: Computer's response
//    ↓
// Level 3: Player's second move
//    ...

// Terminal States:
// - Player forms a magic sum of 15 (Player wins)
// - Computer forms a magic sum of 15 (Computer wins)
// - All positions filled with no magic sum (Draw)
// General Approach:
// The code uses a magic square approach to simplify win condition checking. By mapping each position to a number in the magic square, winning combinations become any three numbers that sum to 15. The computer AI uses a three-tier strategy: trying to win, preventing the player from winning, or making a random move. This approach provides a cleaner alternative to the traditional method of checking rows, columns, and diagonals.
