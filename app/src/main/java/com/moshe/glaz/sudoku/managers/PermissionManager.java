package com.moshe.glaz.sudoku.managers;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.moshe.glaz.sudoku.app.AppBase;
import com.moshe.glaz.sudoku.enteties.User;
import com.moshe.glaz.sudoku.ui.activities.SplashActivity;

public class PermissionManager {
    private   static PermissionManager instance;
    public static PermissionManager getInstance() {
        if(instance==null){
            instance=new PermissionManager();
        }
        return instance;
    }

    PermissionManager(){

    }


    public boolean hasLocationPermission() {
      int locationPermission= ActivityCompat.checkSelfPermission(AppBase.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
      return locationPermission == PackageManager.PERMISSION_GRANTED;
    }

    public void askLocationPermission(Activity activity) {
        if (hasLocationPermission()) {
            return;
        }

        String[] array = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(activity,array, 123409);
    }
}
