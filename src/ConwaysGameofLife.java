package src;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConwaysGameofLife {

    private static final int MAX_GENERATIONS = 100;
    private static final int GRID_LENGTH = 200;
    private static final int GRID_WIDTH = 200;

    private static final int[][] NEIGHBOUR_OFFSETS = {
            {-1, 1},   {0, 1},  {1, 1},
            {-1, 0},            {1, 0},
            {-1, -1,}, {0, -1}, {1, -1}
    };

    public static void main(String[] args) {
        ArrayList<Cell> initPopulatedCells = new ArrayList<>();
        // Map command line input to cells
        if (args.length == 1) {
            // Extract cell coordinates from the input string using regex
            // Expected format [[x1, y1], [x2, y2]]
            String input = args[0];
            // Handle any unexpected whitespace in input
            input = input.replaceAll("\\s", "");
            Pattern pattern = Pattern.compile("\\[(\\d+),(\\d+)\\]");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                initPopulatedCells.add(new Cell(x, y));
            }
        } else {
            System.err.println("Invalid input. Example usage: java ConwaysGameofLife \"[[x1, y1], [x2, y2], ...]\"");
            System.exit(1);
        }

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

        // TODO Change to nested for loops
        for (int[] neighbourOffset : NEIGHBOUR_OFFSETS) {
            int neighbourX = cell.getX() + neighbourOffset[0]; // x change
            int neighbourY = cell.getY() + neighbourOffset[1]; // y change
            if (populatedCells.contains(new Cell(neighbourX, neighbourY))) {
                populatedNeighbours++;
            }
        }
        return populatedNeighbours;
    }
}
