package com.moshe.glaz.sudoku.enteties.sudoku;

import com.moshe.glaz.sudoku.enteties.Position;

public class SelectedCell {
    public SelectedCell(){
        position=new Position();
    }

    public Position position;
    public long time;
    public int score;

    public boolean hasValue() {
        return time > 0;
    }

}
