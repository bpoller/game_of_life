package com.ippon.gol.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public class World {

    private final Map<Position, Cell> aliveCellMap;
    private final int width;
    private final int height;

    World(Set<Cell> aliveCellList, int width, int height) {
        this.aliveCellMap = aliveCellList
                .stream()
                .collect(toMap(Cell::position, identity()));

        this.width = width;
        this.height = height;
    }

    public World nextGeneration() {

        Set<Cell> aliveCells = positions()
                .map(this::lookupCell)
                .map(cell -> cell.evaluate(neighboringCells(cell)))
                .filter(Cell::alive)
                .collect(Collectors.toSet());

        return new World(aliveCells, width, height);
    }

    Cell lookupCell(Position position) {
        return aliveCellMap.getOrDefault(position, new Cell(position, false));
    }

    List<Cell> neighboringCells(Cell cell) {
        return neighborPositions(cell.position()).map(this::lookupCell).toList();
    }

    Stream<Position> neighborPositions(Position position) {
        return List.of(
                new Position(position.x() - 1, position.y() - 1),
                new Position(position.x(), position.y() - 1),
                new Position(position.x() + 1, position.y() - 1),
                new Position(position.x() - 1, position.y()),
                new Position(position.x() + 1, position.y()),
                new Position(position.x() - 1, position.y() + 1),
                new Position(position.x(), position.y() + 1),
                new Position(position.x() + 1, position.y() + 1)
        ).stream();

    }

    Stream<Position> positions() {
        return lines()
                .flatMap(y -> columns()
                        .map(x -> new Position(x, y)));
    }

    public String render() {
        return lines()
                .map(this::renderLine)
                .collect(joining("\n"));
    }

    private String renderLine(int y) {
        return columns()
                .map(x -> new Position(x, y))
                .map(this::lookupCell)
                .map(Cell::render)
                .collect(Collectors.joining(" "));
    }

    private Stream<Integer> lines() {
        return Stream.iterate(1, i -> i <= height, i -> i + 1);
    }

    private Stream<Integer> columns() {
        return Stream.iterate(1, i -> i <= width, i -> i + 1);
    }
}