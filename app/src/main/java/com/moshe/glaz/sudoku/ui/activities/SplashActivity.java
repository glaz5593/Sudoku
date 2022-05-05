package com.moshe.glaz.sudoku.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moshe.glaz.sudoku.R;
import com.moshe.glaz.sudoku.databinding.ActivitySplashBinding;
import com.moshe.glaz.sudoku.enteties.User;
import com.moshe.glaz.sudoku.infrastructure.TextUtils;
import com.moshe.glaz.sudoku.infrastructure.UIUtils;
import com.moshe.glaz.sudoku.infrastructure.Utils;
import com.moshe.glaz.sudoku.managers.GameLogicManager;
import com.moshe.glaz.sudoku.managers.LogicManager;
import com.moshe.glaz.sudoku.managers.PermissionManager;
import com.moshe.glaz.sudoku.managers.PreferencesManager;
import com.moshe.glaz.sudoku.ui.board.BoardCellData;
import com.moshe.glaz.sudoku.ui.board.BoardView;
import com.moshe.glaz.sudoku.ui.board.E_TextColor;
import com.moshe.glaz.sudoku.ui.board.E_TextSize;
import com.moshe.glaz.sudoku.ui.board.E_cellBackground;
import com.moshe.glaz.sudoku.ui.dialog.ColorDialog;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    boolean hasLocationPermission,cancelPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UIUtils.setStatusBarColor(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

         if(LogicManager.getInstance().hasUser()){
             if(GameLogicManager.getInstance().hasActiveGame()){
                 startActivity(new Intent(getApplicationContext(),GameActivity.class));
                 finish();
                 return;
             }

             startActivity(new Intent(getApplicationContext(),MainActivity.class));
             finish();
             return;
         }

        initUi();
    }

    private void initUi() {
        hasLocationPermission = PermissionManager.getInstance().hasLocationPermission();

        binding.btnLogin.setVisibility(hasLocationPermission || cancelPermission? View.VISIBLE : View.GONE);
        binding.tvLocation.setVisibility(hasLocationPermission || cancelPermission? View.GONE : View.VISIBLE);
        binding.btnLocationNotOk.setVisibility(hasLocationPermission || cancelPermission? View.GONE : View.VISIBLE);
        binding.btnLocationOk.setVisibility(hasLocationPermission || cancelPermission? View.GONE : View.VISIBLE);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_location_ok:{
                PermissionManager.getInstance().askLocationPermission(this);
                break;
            }
            case R.id.btn_location_not_ok:{
                cancelPermission=true;
                initUi();
                break;
            }
            case R.id.btn_login:{
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initUi();
    }

}