package com.ippon.gol.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class WorldTest {

    @Test
    public void shouldRender1x1() {
        Set<Cell> aliveCellList = Set.of(new Cell(new Position(1, 1), true));
        var world = new World(aliveCellList, 3, 3);
        var expected = "* . .\n. . .\n. . .";
        Assertions.assertEquals(expected, world.render());
    }

    @Test
    public void shouldRender2x1() {
        Set<Cell> aliveCellList = Set.of(new Cell(new Position(2, 1), true));
        var world = new World(aliveCellList, 3, 3);
        var expected = ". * .\n. . .\n. . .";
        Assertions.assertEquals(expected, world.render());
    }

    @Test
    public void shouldGeneratePositions() {
        Set<Cell> aliveCellList = Set.of(new Cell(new Position(2, 1), true));
        var world = new World(aliveCellList, 3, 3).nextGeneration();

        var positions = world.positions().map(Position::render).collect(Collectors.joining(","));
        var expected = "[1,1],[2,1],[3,1],[1,2],[2,2],[3,2],[1,3],[2,3],[3,3]";
        Assertions.assertEquals(expected, positions);
    }

    @Test
    public void shouldGenerateNeighbourPositions() {

        var world = new World(Collections.emptySet(), 3, 3).nextGeneration();
        var positions = world.neighborPositions(new Position(3, 3)).map(Position::render).collect(Collectors.joining(","));
        var expected = "[2,2],[3,2],[4,2],[2,3],[4,3],[2,4],[3,4],[4,4]";
        Assertions.assertEquals(expected, positions);
    }

    @Test
    public void shouldCreateNewGen() {
        Set<Cell> aliveCellList = Set.of(
                new Cell(new Position(2, 2), true),
                new Cell(new Position(3, 2), true),
                new Cell(new Position(2, 3), true)
        );

        var world = new World(aliveCellList, 5, 5);
        System.out.println(world.render());
        System.out.println();

        var nextGen = world.nextGeneration();
        System.out.println(nextGen.render());

        var expected = ". . . . .\n. * * . .\n. * * . .\n. . . . .\n. . . . .";
        Assertions.assertEquals(expected, nextGen.render());
    }

    @Test
    public void shouldOscillate() {
        Set<Cell> aliveCellList = Set.of(
                new Cell(new Position(3, 2), true),
                new Cell(new Position(3, 3), true),
                new Cell(new Position(3, 4), true)
        );

        var world = new World(aliveCellList, 5, 5);

        for (int generation = 1; generation <= 10; generation++) {
            System.out.println(world.render());
            System.out.println();
            world = world.nextGeneration();
        }

        var expected = ". . . . .\n. . * . .\n. . * . .\n. . * . .\n. . . . .";
        Assertions.assertEquals(expected, world.render());
    }
}