# Knights Tour

The knight’s tour is a problem in which only one chess piece, the knight, is used. The knight is placed on a chess board and moves according to the rules of chess– to follow an L-shaped path. The goal is to find a sequence of moves that allows the knight to visit each square exactly once.

The objective of this Machine Problem is to experiment with blind and heuristic search algorithms on the Knight’s tour problem.

## Data
- `Board Size`: 8x8
- `Starting Position`: d4
- `Moves`: (1, -2), (2, -1), (2,1), (-2, -1), (1, 2), (-1, 2), (-2, 1), (-1, -2)

The values can be changed from the main class `knightsTour.java`.

## Functions
- `blindSearch`
> The blindSearch function implemented the *blind search algorithm*– a search with no information or does not use any of the information in a state. The type of blind search used was the *depth-first search*, which goes move by move through the tree, until it finds a dead end where the path is not completed yet before it backs up to the tree and move on to the next possible move.
- `heuristicSearch`
> The heuristicSearch, on the other hand, implemented the *heuristic search algorithm*. It evaluates the available information at each step and decides which position to move to. It does so by using the *Warnsdorf’s rule* where it chooses the next position with the least next unvisited positions.

## Average Runtime (10 Trials)
- `Blind Search Algorithm (Depth-first Search)`: Average Runtime: 87.695s
- `Heuristic Search Algorithm (Wandorf's Rule)`: Average Runtime: 0.024s
