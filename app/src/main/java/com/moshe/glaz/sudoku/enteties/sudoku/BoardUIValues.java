package com.moshe.glaz.sudoku.enteties.sudoku;

import com.moshe.glaz.sudoku.enteties.Position;
import com.moshe.glaz.sudoku.enteties.sudoku.values.BoardUIValue;
import com.moshe.glaz.sudoku.enteties.sudoku.values.IntValue;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardUIValues implements Serializable {
    int size = 9;
    public BoardUIValues() {
        values = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                values.add(new BoardUIValue());
            }
        }
    }

    public ArrayList<BoardUIValue> values;

    public BoardUIValue get(int x, int y) {
        return values.get((y * size) + x);
    }

    public BoardUIValue get(Position position) {
        return get(position.x,position.y);
    }

    public void set(Position position, BoardUIValue value) {
        set(position.x,position.y,value);
    }

    public void set(int x, int y, BoardUIValue value) {
        values.get((y * size) + x).copy(value);
    }

}
