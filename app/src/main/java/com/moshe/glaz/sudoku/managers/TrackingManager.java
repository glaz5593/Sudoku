package com.moshe.glaz.sudoku.managers;

import android.location.Location;
import android.location.LocationManager;

import com.google.firebase.database.FirebaseDatabase;
import com.moshe.glaz.sudoku.enteties.User;
import com.moshe.glaz.sudoku.enteties.UserLocation;
import com.moshe.glaz.sudoku.infrastructure.TimeSpan;
import com.moshe.glaz.sudoku.services.TrackingService;

public class TrackingManager {
    UserLocation lastKnowLocation;
    private static TrackingManager instance;
    public static TrackingManager getInstance() {
        if(instance==null){
            instance=new TrackingManager();
        }
        return instance;
    }

    TrackingManager(){

    }

    public void startTracking() {
        if(!PermissionManager.getInstance().hasLocationPermission()){
            return;
        }

        TrackingService.start();
    }

    public void addLocation(Location location) {
        if(lastKnowLocation != null){
            if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
                if(lastKnowLocation.getProvider().equals(LocationManager.GPS_PROVIDER)){
                    if (TimeSpan.getDiff(lastKnowLocation.getDate()).totalMinutes < 30){
                        return;
                    }
                }
            }
        }

        lastKnowLocation=new UserLocation();
        lastKnowLocation.setLat(location.getLatitude());
        lastKnowLocation.setLon(location.getLongitude());
        lastKnowLocation.setProvider(location.getProvider());

        User user= LogicManager.getInstance().getUser();
        user.lastKnowLocation=lastKnowLocation;
        FirebaseManager.getInstance().updateUser(user, result -> {

        });
    }
}
