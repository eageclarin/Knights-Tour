import java.util.Scanner;

class heuristicSearch {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); //size of board
        char[] charCoords = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        //ask user to enter size of baord
        System.out.printf("KNIGHT'S TOUR: WARNSDORF'S RULE\n");
        System.out.printf("Enter Size of Board (5-8): ");
        int boardSize = input.nextInt(); //size of board

        if (boardSize<=8 && boardSize>=5) {
            //display initial position
            System.out.printf("Initial Position: d4");
            
            //ask user to enter target position
            System.out.printf("\nEnter Target Position (a-"+ charCoords[boardSize-1] +"1-"+ boardSize +"): ");
            String tp = input.next(); //target position

            //perform heuristic search
            bsf knight = new bsf();
            knight.knightTour(tp, boardSize);
        } else if (boardSize<5) {
            System.out.printf("Board size too small for a knight.");
        } else {
            System.out.printf("Exceeded maximum size of board.");
        }
        
        input.close();
    }
}