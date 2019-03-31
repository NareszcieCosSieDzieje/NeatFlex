package NeatFlexClasses;

import java.util.Scanner;

public class Utilities {

    static boolean bitAnswer(){
        Scanner input = new Scanner(System.in);
        while(true) {
            char answer = input.next().trim().charAt(0);
            if (!((answer == 'Y') || (answer == 'N') || (answer == 'y') || (answer == 'n'))) {
                System.out.println("Wrong letter, please try again (Y/N)");
            } else if (answer == 'Y' || answer == 'y') {
                return true;
            } else if (answer == 'N' || answer == 'n') {
                return false;
            }
        }
    }
}
