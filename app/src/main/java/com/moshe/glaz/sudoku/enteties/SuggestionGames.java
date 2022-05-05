package com.moshe.glaz.sudoku.enteties;

import java.util.Collection;
import java.util.HashMap;

public class SuggestionGames extends HashMap<String,SuggestionGame> {
    public SuggestionGames(SuggestionGame[] arr){
        if(arr==null){
            return;
        }

        for(SuggestionGame g:arr){
            this.put(g.uid,g);
        }
    }

    public SuggestionGame[] asArray(){
        Collection<SuggestionGame> values = values();

        SuggestionGame[] res=new SuggestionGame[values.size()];
        res=values.toArray(res);

        return res;
    }
}
