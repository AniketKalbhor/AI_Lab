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
