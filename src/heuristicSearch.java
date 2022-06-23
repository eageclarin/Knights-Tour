import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class coords {
    int ci, cj; //current position

    coords (int ci, int cj) {
        this.ci = ci;
        this.cj = cj;
    }
}

public class heuristicSearch {
    private int boardSize, algo;
    private coords initialPos; //initial position
    private FileWriter output;

    private final char[] charCoords = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    //private static final int[][] moves = { {1, -2}, {2, -1}, {2, 1},{1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2} };
    //private static final int[][] moves = { {1, -2}, {2, -1}, {2, 1},{1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2} };
    private static final int[][] moves = { {1, -2}, {2, -1}, {2, 1}, {-2, -1}, {1, 2},  {-1, 2}, {-2, 1},  {-1, -2}  };

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

    /* find complete path using heuristicSearch */
    public boolean findCompletePath (ArrayList<coords[]> completePaths, ArrayList<coords> currentPaths, boolean[][] visited, coords currentPos) throws IOException {
        int i = currentPos.ci; int j = currentPos.cj;
    
        visited[i][j] = true; //mark visited
        currentPaths.add(currentPos);

        //print initial state
        if (currentPos == initialPos) {
            completePaths.add(currentPaths.toArray( coords[]:: new));
            printBoard(completePaths);
            completePaths.remove(0);
        }
        
        //function variable to getCandidatePositions and store
        Function<coords, List<coords>> getCandidatePositions = (coords refPos) -> 
            searchPositions(refPos.ci, refPos.cj).stream()
                .filter((pos) -> !visited[pos.ci][pos.cj]) //store unvisited coords only
                .collect( Collectors.toList() );
        
        //all candidate positions from currentPos
        List<coords> candidatePositions = getCandidatePositions.apply(currentPos); //read-only - list

        //check if deadEnd and complete
        if (candidatePositions.size() == 0 && currentPaths.size() == boardSize * boardSize) { //awan unvisited coords
            completePaths.add(currentPaths.toArray( coords[]::new )); //path clone into array
            return true;
        } else {
            //move to nextPosition with least next unvisited position
            Optional<coords> nextPosition = candidatePositions.stream()
                .min(Comparator.comparingInt((pos) -> getCandidatePositions.apply(pos).size()));

            //recurse function until solution is found
            if (findCompletePath(completePaths, currentPaths, visited, nextPosition.get())) 
                return true;
        }

        //for backtracking
        visited[i][j] = false;
        currentPaths.remove(currentPaths.size() - 1);
        return false;
    }

    /* move knight based on chosen algorithm */
    public void moveKnight () throws IOException {
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
        int ii = intEquiv(ip.charAt(0)); //char coord
            if (ii==9) { System.out.printf("\nInvalid position."); return; }
        int ij = Character.getNumericValue(ip.charAt(1)) - 1; //int coord; assigned taret position j
        initialPos = new coords(ii,ij); //assign coords to targetPos

        //check if target position is within board
        if (withinBoard(initialPos)) {
            //display starting position
            output.write("\n*****HEURISTIC SEARCH*****\n");
            output.write("\nINITIAL STATE:\n---------------");

            //move knight
            moveKnight();
        } else {
            System.out.printf("\nInvalid Position.");
        } 
    }
}