package com.moshe.glaz.sudoku.server;

public class ServerManager {
    public  static ServerManager instance;
    public static ServerManager getInstance() {
        if(instance==null){
            instance=new ServerManager();
        }
        return instance;
    }

    ServerManager(){

    }


}
