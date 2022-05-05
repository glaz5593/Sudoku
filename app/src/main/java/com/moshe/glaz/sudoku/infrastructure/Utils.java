package com.moshe.glaz.sudoku.infrastructure;

import com.moshe.glaz.sudoku.enteties.SuggestionGame;

public class Utils {

    public static void sleep(long time){
        try{
            Thread.sleep(time);
        }catch (Exception e){

        }
    }

    public static int getInt(String value) {
        return tryParseInt(value, 0);
    }

    public static double getDouble(String value) {
        return tryParseDouble(value, 0d);
    }

    public static double tryParseDouble(String value, Double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (Exception Ex) {
        }
        return defaultValue;
    }


    public static int tryParseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception Ex) {
        }
        return defaultValue;
    }
}
