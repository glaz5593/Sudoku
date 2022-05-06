package com.moshe.glaz.sudoku.enteties.sudoku.values;

public class BoardUIValue {
    public String text;
    public boolean selected;
    public boolean activated;
    public boolean checked;

    public void copy(BoardUIValue value) {
        this.text = value.text;
        this.selected = value.selected;
        this.activated = value.activated;
        this.checked = value.checked;
    }

    public String getHashCode(){
        return text + this.selected+this.activated+ this.checked;
    }
}
