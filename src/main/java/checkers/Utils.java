package checkers;

import java.util.Scanner;

public class Utils {

    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";
    public static final Scanner in = new Scanner(System.in);
    
    // returns a string of the specified color using ANSI
    public static String color(String color, String text) {
        return color + text + RESET;
    }

    // gets int value in range, inclusive
    public static int getIntInRange(String prompt, int lower, int upper) {
      int res;
      while (true) {
        System.out.print(prompt);
        try {
          res = in.nextInt();
          if (res <= upper && lower <= res) {
            return res;
          }
        } catch (Exception e) {
          in.nextLine();
        }
        System.out.println("Invalid input. Please enter an integer in the range " + lower + ", " + upper + ".");
      }
    }
}
