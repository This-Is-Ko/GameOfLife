import java.util.ArrayList;

public class ConwaysGameofLife {

    private static final int MAX_GENERATIONS = 100;
    private static final int GRID_LENGTH = 200;
    private static final int GRID_WIDTH = 200;

    public static void main(String[] args) {
        ArrayList<Cell> initPopulatedCells = new ArrayList<>();
//        initPopulatedCells.add(new Cell(5, 5));
//        initPopulatedCells.add(new Cell(6, 5));
//        initPopulatedCells.add(new Cell(7, 5));
//        initPopulatedCells.add(new Cell(5, 6));
//        initPopulatedCells.add(new Cell(6, 6));
//        initPopulatedCells.add(new Cell(7, 6));
        gameOfLife(initPopulatedCells, MAX_GENERATIONS, GRID_LENGTH, GRID_WIDTH);
    }

    private static void gameOfLife(ArrayList<Cell> populatedCells, int maxGenerations, int gridLength, int gridWidth){
        ArrayList<Cell> currentGenCells = populatedCells;

        for (int generation = 1; generation <= maxGenerations; generation++) {
            // TODO Change to foreach or stream
            // Generate behaviour of populated cells
            ArrayList<Cell> nextGenCells = new ArrayList<>();
            for (Cell cell : currentGenCells) {

                // Find neighbours
                int populatedNeighbours = findPopulatedNeighbours(cell, populatedCells);

                // Cell populated scenario:
                // Any live cell with two or three live neighbors lives on
                if (populatedNeighbours == 2 || populatedNeighbours == 3) {
                    nextGenCells.add(cell);
                }
                // Cell nonpopulated scenarios:
                // Any live cell with fewer than two live neighbours dies in the next generation as if caused by underpopulation.
                // Any live cell with more than three live neighbors dies (overpopulation)
            }

            // Check existing unpopulated cells which satisfy the following:
            // Any dead cell with exactly three live neighbours becomes a live cell in the next generation, as if by reproduction.
            for (int x = 0; x < gridWidth; x++) {
                for (int y = 0; y < gridLength; y++) {
                    Cell cell = new Cell(x, y);
                    if (!currentGenCells.contains(cell)) {
                        int populatedNeighbours = findPopulatedNeighbours(cell, currentGenCells);
                        if (populatedNeighbours == 3) {
                            nextGenCells.add(cell);
                        }
                    }
                }
            }
            System.out.println(generation + ": " + nextGenCells);
            currentGenCells = nextGenCells;
        }
    }

    private static int findPopulatedNeighbours(Cell cell, ArrayList<Cell> populatedCells) {
        // c | c   | c
        // c | x,y | c
        // c | c   | c
        // (x,y) offset
        int populatedNeighbours = 0;
        int[][] neighbourOffsets = {
                {-1, 1},   {0, 1},  {1, 1},
                {-1, 0},            {1, 0},
                {-1, -1,}, {0, -1}, {1, -1}
        };
        // TODO Check neighbour offset is inside grid
        for (int[] neighbourOffset : neighbourOffsets) {
            int neighbourX = cell.getX() + neighbourOffset[0]; // x change
            int neighbourY = cell.getY() + neighbourOffset[1]; // y change
            if (populatedCells.contains(new Cell(neighbourX, neighbourY))) {
                populatedNeighbours++;
            }
        }
        return populatedNeighbours;
    }
}
