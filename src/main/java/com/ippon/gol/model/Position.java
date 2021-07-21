package com.ippon.gol.model;

public record Position(int x, int y) {

    String render() {
        return ("[" + x + "," + y + "]");
    }
}