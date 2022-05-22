/* akala ko po hahanapin yung path papuntang target position.
    i'll leave the functions i've used for ^^ as comments na lang po kasi nasasayangan ako D': */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

class knightsTour {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in); //size of board
        int boardSize = 8; String ip = "d4"; //boardSize & initialPosition

        //ask user to choose between blind search and heuristic search
        System.out.println("KNIGHT'S TOUR");
        System.out.println("Initial Position: " + ip);
        System.out.println("Board Size: " + boardSize + "x" + boardSize);
        System.out.printf("\n> Press 1 for Blind Search\n> Press 2 for Heuristic Search\n(1 or 2): ");
        int searchChoice = input.nextInt();

        switch(searchChoice) {
            case 1: //blind search
                File file = new File("blindSearch.txt");
                try {
                    blindSearch knight = new blindSearch();
                    FileWriter output = new FileWriter(file, true);

                    long startTime = System.currentTimeMillis();
                    knight.knightTour(ip, boardSize, output);
                    long endTime = System.currentTimeMillis();
                    
                    float sec = (endTime - startTime) / 1000F;
                    output.write("\nRuntime: "+sec+"s\n");
                    output.close();
                    System.out.println("Check blindSearch.txt");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                break;
            case 2:
            default:
                System.out.println("Invalid choice.");
        }
        
        input.close();
    }
}
