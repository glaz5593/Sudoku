package com.moshe.glaz.sudoku.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.moshe.glaz.sudoku.R;
import com.moshe.glaz.sudoku.databinding.ActivityLoginBinding;
import com.moshe.glaz.sudoku.infrastructure.UIUtils;
import com.moshe.glaz.sudoku.managers.ActionListener;
import com.moshe.glaz.sudoku.managers.DataSourceManager;
import com.moshe.glaz.sudoku.managers.LogicManager;


public class LoginActivity extends AppCompatActivity   {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UIUtils.setStatusBarColor(this);
    }

    public void onLoginClick(View view) {
        if (binding.etUserName.length() == 0) {
            UIUtils.showSnackbar(binding.clMain, R.string.user_name_required);
            binding.etUserName.setFocusable(true);
            binding.etUserName.requestFocus();
            return;
        }

        if (binding.etPassword.length() == 0) {
            UIUtils.showSnackbar(binding.clMain, R.string.password_required);
            binding.etPassword.setFocusable(true);
            binding.etPassword.requestFocus();
            return;
        }

        LogicManager.getInstance().login(binding.etUserName.getText().toString(), binding.etPassword.getText().toString(), result->{
            if(result.success){
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else{
                UIUtils.showToast(result.error);
            }
        });
    }

    public void onRegistrationClick(View view) {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
    }


}