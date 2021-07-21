package com.ippon.gol.model;

import java.util.List;

record Cell(Position position, boolean alive) {

    /**
     * Any live cell with fewer than two live neighbours dies, as if by underpopulation
     * Any live cell with two or three live neighbours lives on to the next generation.
     * Any live cell with more than three live neighbours dies, as if by overpopulation.
     * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
     */
    Cell evaluate(List<Cell> neighbors) {
        int liveCellCount = neighbors.stream().filter(Cell::alive).mapToInt(i -> 1).sum();
        boolean willRemainAlive = (liveCellCount == 2 || liveCellCount == 3) && alive;
        boolean willResurrect = liveCellCount == 3 && !alive;
        boolean willBeAlive = willRemainAlive || willResurrect;
        return new Cell(position, willBeAlive);
    }

    String render() {
        return String.valueOf(alive)
                .replace("true", "*")
                .replace("false",".");
    }
}