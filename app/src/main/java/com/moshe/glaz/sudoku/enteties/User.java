package com.moshe.glaz.sudoku.enteties;

import java.util.Date;

public class User {
    public String uid;
    public String nickName;
    public int gender;
    public int avatar;
    public String status;
    public Date registrationDate;
    public int gamesWin;
    public int gamesLose;
    public int gamesPlayed;
    public UserLocation lastKnowLocation;

    public void copy(User user) {
        this.uid = user.uid;
        this.nickName = user.nickName;
        this.gender = user.gender;
        this.status = user.status;
        this.avatar = user.avatar;
        this.registrationDate = user.registrationDate;
        this.gamesWin = user.gamesWin;
        this.gamesLose = user.gamesLose;
        this.gamesPlayed = user.gamesPlayed;
    }
}
