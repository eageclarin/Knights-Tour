import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class coords {
    int ci, cj; //current position

    coords (int ci, int cj) {
        this.ci = ci;
        this.cj = cj;
    }
}

public class blindSearch {
    private int ii, ij, boardSize;
    private coords initialPos; //initial position
    private FileWriter output;

    private final char[] charCoords = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
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

    /* print chessboard */
    private void printBoard(ArrayList<coords[]> path) throws IOException {
        String[][] board = new String[boardSize][boardSize];

        path.forEach((completePath) -> {
            int order = 1;
            for (coords c: completePath) {
                for (int row=0; row<boardSize; row++) {
                    for (int col=0; col<boardSize; col++) {
                        if (col == c.ci && row == c.cj) {
                            board[row][col] = String.format("%02d", order);
                            break;
                        }
                    }
                }
                order++;
            }
        });

        output.write("\n");
        for (int row=boardSize; row>0; row--) {
            for (int col=0; col<boardSize; col++) {
                if (board[row-1][col] == null) {
                    output.write("XX ");
                } else {
                    output.write(""+board[row-1][col]+" ");
                }
            }
            output.write("\n");
        }
    }

    /* search for possible positons */
    private ArrayList<coords> searchPositions (int i, int j) {
        ArrayList<coords> positions = new ArrayList<>();

        //traverse through 8 possible moves
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

    /* search for path to target */
    public boolean findCompletePath (ArrayList<coords[]> completePaths, ArrayList<coords> currentPaths, boolean[][] visited, coords currentPos) throws IOException {
        int i = currentPos.ci; int j = currentPos.cj;
    
        // if (visited[i][j]) { return; } //skip coordinate
        visited[i][j] = true; //mark visited
        currentPaths.add(currentPos);

        if (currentPos == initialPos) {
            completePaths.add(currentPaths.toArray( coords[]:: new));
            printBoard(completePaths);
            completePaths.remove(0);
        }

        coords[] candidatePositions = searchPositions(i, j).stream()
            .filter((pos) -> !visited[pos.ci][pos.cj]) //store unvisited coords
            .toArray( coords[]::new );
        
        /*
        boolean deadEnd = false;
        for (coords candidatePos : candidatePositions) {
            deadEnd = deadEnd || visited[candidatePos.ci][candidatePos.cj]; //check if deadEnd or one of them is visited
        }*/

        //check if deadEnd and complete
        if (candidatePositions.length == 0 && currentPaths.size() == boardSize * boardSize) { //awan unvisited coords
            completePaths.add(currentPaths.toArray( coords[]::new )); //path clone into array
            return true;
        } else {
            //search for candidatePos in currentPos
            for (coords candidatePos: candidatePositions) {
                //recurse function with candidatePos
                if (findCompletePath(completePaths, currentPaths, visited, candidatePos)) 
                    return true;
            }
        }

        visited[i][j] = false;
        currentPaths.remove(currentPaths.size() - 1); //remove recent path
        return false;
    }

    /* move knight from initialPos to targetPos */
    public void moveKnight (coords initialPos) throws IOException {
        ArrayList<coords[]> completePaths = new ArrayList<>();

        if (findCompletePath(completePaths, new ArrayList<>(), new boolean[boardSize][boardSize], initialPos)) {
            output.write("\nFINAL STATE:\n---------------");
            printBoard(completePaths);
        } else {
            System.out.println("No Solution.");
        }
        
    }    

    /* start knight's tour */
    public void knightTour(String ip, int size, FileWriter file) throws IOException {
        boardSize = size; output = file;
        ii = intEquiv(ip.charAt(0)); //char coord
            if (ii==9) { System.out.printf("\nInvalid position."); return; }
        ij = Character.getNumericValue(ip.charAt(1)) - 1; //int coord; assigned taret position j
        initialPos = new coords(ii,ij); //assign coords to targetPos

        //check if target position is within board
        if (withinBoard(initialPos)) {
            //display starting position
            output.write("\nINITIAL STATE:\n---------------");

            //move knight
            moveKnight(initialPos);
        } else {
            System.out.printf("\nInvalid Position.");
        } 
    }
}
