package com.ippon.gol.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    //Any live cell with fewer than two live neighbours dies, as if by underpopulation
    public void shouldDieDueToUnderpopulation(){
        var aliveCell = new Cell(new Position(5,5),true);
        var neighbors = List.of(aliveCell);
        assertFalse(aliveCell.evaluate(neighbors).alive());
    }

    @Test
    //Any live cell with two or three live neighbours lives on to the next generation.
    public void shouldRemainAliveWith2(){
        var aliveCell = new Cell(new Position(5,5),true);
        var neighbors = List.of(aliveCell,aliveCell);
        assertTrue(aliveCell.evaluate(neighbors).alive());
    }
    @Test
    //Any live cell with two or three live neighbours lives on to the next generation.
    public void shouldRemainAliveWith3(){
        var aliveCell = new Cell(new Position(5,5),true);
        var neighbors = List.of(aliveCell,aliveCell,aliveCell);
        assertTrue(aliveCell.evaluate(neighbors).alive());
    }

    @Test
    //Any live cell with two or three live neighbours lives on to the next generation.
    public void shouldDieDueToOverPopulation(){
        var aliveCell = new Cell(new Position(5,5),true);
        var neighbors = List.of(aliveCell,aliveCell,aliveCell,aliveCell);
        assertFalse(aliveCell.evaluate(neighbors).alive());
    }

    @Test
    // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
    public void shouldResurrect(){
        var aliveCell = new Cell(new Position(5,5),true);
        var deadCell = new Cell(new Position(12,12),false);
        var neighbors = List.of(aliveCell,aliveCell,aliveCell);
        assertTrue(deadCell.evaluate(neighbors).alive());
    }

    @Test
    // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
    public void shouldRemainDead(){
        var aliveCell = new Cell(new Position(5,5),true);
        var deadCell = new Cell(new Position(12,12),false);
        var neighbors = List.of(aliveCell,aliveCell);
        assertFalse(deadCell.evaluate(neighbors).alive());
    }
}