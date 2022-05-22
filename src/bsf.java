import java.io.FileNotFoundException;
import java.util.ArrayDeque;

class coords {
    int ci, cj; //current position

    coords (int ci, int cj) {
        this.ci = ci;
        this.cj = cj;
    }
}

public class bsf {
    private int ti, tj, boardSize; //declare var for targetPos coords and boardSize
    public coords initialPos = new coords(3,3); //initial position - d4
    private static final char[] charCoords = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private static final int[][] moves = { {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2} };

    /* check if within board */
    private boolean withinBoard (coords thisPos) {
        if (thisPos.ci>=0 && thisPos.cj>=0 && thisPos.ci<boardSize && thisPos.cj<boardSize) {
            return true; //position is within board
        } //else
        return false; //position not within board
    }

    /* get int equivalent of char coord */
    private int intEquiv (char i) {
        int k;
        for (k=0; k<boardSize; k++) {
            //if char coordinate is in array, return k
            if (i == charCoords[k]) { return k; }
        } //else
        return 9;
    }

    /* check if current position is in target position */
    private boolean isInTarget (coords currentPos) {
        coords targetPos = new coords(ti,tj); //target position and its coordinators

        //check if coordinations are same
        if (currentPos.ci == targetPos.ci && currentPos.cj == targetPos.cj) {
            return true; //in target position
        } //else
        return false; //not in target position
    }

    /* print chessboard */
    private void printBoard(coords currPos) {
        System.out.printf("\nX - Knight\tO - Target Position\t* - Knight in Target\n");
        
        //print char coordinates
        System.out.printf("    ");
        for (int k=0; k<boardSize; k++) {
            System.out.printf("("+ charCoords[k] +")");
        }
        System.out.printf("\n");
        
        for (int row=boardSize; row>0; row--) {
            System.out.printf(" ("+ row +")"); //print int coordinates
            
            for (int col=1; col<=boardSize; col++) {
                if((row+col)%2 == 0) { //if even
                    if ((row == ti+1 && col == tj+1) && (row == currPos.ci+1 && col == currPos.cj+1)) {
                        System.out.printf("(*)"); 
                    } else if (row == ti+1 && col == tj+1) { //if matched target position
                        System.out.printf("(O)"); 
                    } else if (row == currPos.ci+1 && col == currPos.cj+1) { //if matched initial/current position
                        System.out.printf("(X)");
                    } else { //if neither
                        System.out.printf("( )");
                    }
                } else {
                    if ((row == ti+1 && col == tj+1) && (row == currPos.ci+1 && col == currPos.cj+1)) {
                        System.out.printf("[*]"); 
                    } else if (row == ti+1 && col == tj+1) { //if matched target position
                        System.out.printf("[O]");
                    } else if (row == currPos.ci+1 && col == currPos.cj+1) { //if matched initial/current position
                        System.out.printf("[X]");
                    } else { //if neither
                        System.out.printf("[ ]");
                    }
                }
            }

            System.out.printf("\n");
        }
    }

    /* search for possible positions */
    private ArrayDeque<coords> searchPositions(int i, int j) {
        ArrayDeque<coords> positions = new ArrayDeque<>();
        
        //traverse through 8 moves
        for (int[] m: moves) {
            //assign value of new position
            int ni = i - m[0];
            int nj = j - m[1];
            coords newCoords = new coords(ni,nj);

            //if withinBoard add possible position
            if (withinBoard(newCoords)) {
                positions.add(newCoords);
             }
        }

        return positions;
    }

    /* move knight */
    public void moveKnight(coords currentPos) {
        //if current pos in target, exit function
        if (isInTarget(currentPos)) { return; }

        int i = currentPos.ci; int j = currentPos.cj; //initial position
        ArrayDeque<coords> pos = new ArrayDeque<>(); //queue for coord positions
        boolean[][] visited = new boolean[boardSize][boardSize];

        pos.add(currentPos); //add initial position to queue
        visited[i][j] = true; //mark visited

        while (!pos.isEmpty()) {
            coords c = pos.peek(); //remove and retrive first element
            i = c.ci; j = c.cj; //assign new values to i,j

            //print current board
            int y1 = c.cj+1;
            System.out.printf("\nCurrent:" + charCoords[c.ci]+y1);
            printBoard(c);

            //exit if in target position
            if (isInTarget(c)) { return; }

            //traverse through all possible next positions
            for (coords m: searchPositions(i,j)) {
                //move if not visited
                if(!visited[m.ci][m.cj]) {
                    pos.add(m); //add new position to queue
                    visited[m.ci][m.cj] = true; //mark visited
                    
                    //print board
                    int y2 = m.cj+1;
                    System.out.printf("\n" + charCoords[c.ci]+y1 + " -> " + charCoords[m.ci]+y2);
                    printBoard(m);

                    //exit if in target position
                    if (isInTarget(m)) {
                        for (coords p: pos) {
                            int n = p.cj+1;
                            System.out.printf("" + charCoords[p.ci] + n + "\n");
                        }
                        return;
                    }
                }
            }

            pos.remove();
        }
    }

    /* knight tour:
        check validity of given target positon,
        move knight (search possible positions, move, print board)
    */
    public void knightTour(String tp, int size) throws FileNotFoundException {
        //assign values to variables
        boardSize = size;
        ti = intEquiv(tp.charAt(0)); //char coord
            if (ti==9) { System.out.printf("\nInvalid position."); return; }
        tj = Character.getNumericValue(tp.charAt(1)) - 1; //int coord; assigned taret position j
        coords targetPos = new coords(ti,tj); //assign coords to targetPos

        //check if target position is within board
        if (withinBoard(targetPos)) {
            //display starting position
            System.out.printf("\nStarting Position: ");
            printBoard(initialPos); 

            //move knight
            moveKnight(initialPos);
            System.out.println("\n**TARGET POSITION REACHED**");
        } else {
            System.out.printf("\nInvalid Position.");
        } 
    }
}
