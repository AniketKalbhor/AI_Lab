import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class TicTacToeMagicSq {
    // Magic Square representation of Tic Tac Toe board
    static final int[][] MAGIC_SQUARE = {
            {8, 1, 6},
            {3, 5, 7},
            {4, 9, 2}
    };

    static final int SIZE = 3;
    static char[][] board = { {' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '} };
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
                    return new int[] {i, j};
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
