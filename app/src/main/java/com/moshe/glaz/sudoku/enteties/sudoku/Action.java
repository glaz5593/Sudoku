package com.moshe.glaz.sudoku.enteties.sudoku;

import com.moshe.glaz.sudoku.enteties.Position;

import java.util.Date;

public class Action {
    public Action(){
        position=new Position();
        time=new Date().getTime();
    }
    public Position position;
    public int value;
    public long time;
    public int score;
}
