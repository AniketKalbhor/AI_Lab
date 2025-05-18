import java.util.*;

public class CryptarithmeticCSP {

    static String[] words;
    static String result;
    static Set<Character> uniqueChars = new HashSet<>();
    static List<Character> charList = new ArrayList<>();
    static Map<Character, Integer> assignment = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of words to sum: ");
        int n = Integer.parseInt(scanner.nextLine());
        words = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter word " + (i + 1) + ": ");
            words[i] = scanner.nextLine().toUpperCase();
        }

        System.out.print("Enter the result word: ");
        result = scanner.nextLine().toUpperCase();

        extractUniqueChars();

        if (uniqueChars.size() > 10) {
            System.out.println("❌ Too many unique letters. Cannot map to digits 0-9.");
            return;
        }

        boolean solved = solve(0, new boolean[10]);

        if (!solved) {
            System.out.println("❌ No solution found.");
        }

        scanner.close();
    }

    // Extract unique characters from words and result
    private static void extractUniqueChars() {
        uniqueChars.clear();
        charList.clear();
        assignment.clear();

        for (String word : words)
            for (char c : word.toCharArray())
                uniqueChars.add(c);

        for (char c : result.toCharArray())
            uniqueChars.add(c);

        charList.addAll(uniqueChars);
    }

    // Recursive backtracking
    private static boolean solve(int index, boolean[] usedDigits) {
        if (index == charList.size()) {
            if (isValidAssignment()) {
                printSolution();
                return true;
            }
            return false;
        }

        for (int digit = 0; digit <= 9; digit++) {
            if (usedDigits[digit])
                continue;

            char currentChar = charList.get(index);

            // Don't assign 0 to leading letters
            if (digit == 0 && isLeadingChar(currentChar))
                continue;

            assignment.put(currentChar, digit);
            usedDigits[digit] = true;

            if (solve(index + 1, usedDigits))
                return true;

            // Backtrack
            assignment.remove(currentChar);
            usedDigits[digit] = false;
        }

        return false;
    }

    private static boolean isLeadingChar(char c) {
        for (String word : words)
            if (word.charAt(0) == c)
                return true;
        return result.charAt(0) == c;
    }

    private static int wordToNumber(String word) {
        int num = 0;
        for (char c : word.toCharArray()) {
            num = num * 10 + assignment.get(c);
        }
        return num;
    }

    private static boolean isValidAssignment() {
        int sum = 0;
        for (String word : words)
            sum += wordToNumber(word);
        return sum == wordToNumber(result);
    }

    private static void printSolution() {
        System.out.println("✅ Solution Found:");
        for (Map.Entry<Character, Integer> entry : assignment.entrySet())
            System.out.println(entry.getKey() + " = " + entry.getValue());

        for (String word : words)
            System.out.println(word + " = " + wordToNumber(word));

        System.out.println(result + " = " + wordToNumber(result));
    }
}




/*
# Cryptarithmetic Solver Explanation

## Problem Statement
Cryptarithmetic puzzles are mathematical puzzles where letters represent digits in an arithmetic operation. 
Each letter must represent a unique digit, and the arithmetic operation must be valid. The classic example is SEND + MORE = MONEY, 
where each letter represents a unique digit from 0-9, and the addition must be mathematically correct.

## Explanation of Functions Used
- `main()`: Entry point that handles user input for the puzzle parameters
- `extractUniqueChars()`: Identifies all unique characters from input words and prepares for digit assignments
- `solve()`: Implements recursive backtracking to assign digits to characters
- `isLeadingChar()`: Checks if a character appears as the first letter in any word (leading letters can't be zero)
- `wordToNumber()`: Converts a word to a number using the current character-to-digit assignments
- `isValidAssignment()`: Verifies if the current digit assignments satisfy the arithmetic equation
- `printSolution()`: Displays the solution with character-to-digit mappings and numeric values

## Basic Approach (Constraints and Bounding)
The approach uses a constraint satisfaction problem (CSP) framework with backtracking:
1. Extract all unique characters from the words
2. Recursively assign digits (0-9) to each character
3. Apply constraints to prune the search space:
   - Each character must map to a unique digit
   - Leading characters (first letter of any word) cannot be zero
   - The arithmetic equation must be satisfied with the assignments
4. Use backtracking to try different assignments when constraints are violated

## Explanation of Variables
- `words`: Array storing the addend words
- `result`: The string representing the sum word
- `uniqueChars`: Set containing all unique characters from the words and result
- `charList`: List version of uniqueChars for indexed access
- `assignment`: Map that stores the current character-to-digit assignments
- `usedDigits`: Boolean array tracking which digits (0-9) have been assigned

## Flow of Execution
1. User inputs the number of words to add and enters each word
2. User inputs the result word
3. Program extracts all unique characters
4. If there are more than 10 unique characters, the problem is unsolvable (we only have digits 0-9)
5. The backtracking solver begins assigning digits to characters:
   - For each character, try assigning an unused digit
   - Skip assigning 0 to leading characters
   - After each assignment, recursively try to solve the rest
   - If the assignment leads to a contradiction, backtrack and try another digit
6. Once all characters are assigned, check if the arithmetic equation is satisfied
7. If satisfied, print the solution; otherwise, continue searching
8. If no solution exists after exploring all possibilities, report failure

## State Space Tree (Simplified)
```
                                [Start]
                                  |
                       [Assign first char (S)]
                      /        |         \
                   [S=1]     [S=2]  ...  [S=9]
                    |          |           |
              [Assign E]   [Assign E]   [Assign E]
             /    |    \      |            |
          [E=0] [E=2] [E=3]   |            |
           |     |     |      |            |
     [Assign N]  |     |      |            |
       /   \     |     |      |            |
   [N=2] [N=3]   |     |      |            |
    |      |     |     |      |            |
    |      |     |     |      |            |
  ... continues branching for all character assignments ...
                                |
                         [Check solution]
                          /         \
                     [Valid]     [Invalid]
                       |            |
                 [Print solution] [Backtrack]
```

The backtracking algorithm explores this tree, pruning branches that violate constraints and eventually finding a 
valid solution if one exists.

The key insight is that this approach effectively handles the exponential search space by immediately discarding invalid paths, 
making it much more efficient than trying all possible combinations of digit assignments.
*/
