package com.moshe.glaz.sudoku.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.moshe.glaz.sudoku.R;
import com.moshe.glaz.sudoku.databinding.ActivityUsersBinding;
import com.moshe.glaz.sudoku.enteties.sudoku.Game;
import com.moshe.glaz.sudoku.enteties.SuggestionGame;
import com.moshe.glaz.sudoku.enteties.User;
import com.moshe.glaz.sudoku.infrastructure.UIUtils;
import com.moshe.glaz.sudoku.managers.DataSourceManager;
import com.moshe.glaz.sudoku.ui.adapters.UserAdapter;

public class UsersActivity extends AppCompatActivity implements DataSourceManager.UpdateListener, UserAdapter.onSelectListener {
    UserAdapter adapter;
    ActivityUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UIUtils.setStatusBarColor(this);

        setTitle(R.string.users);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initAdapter();
        DataSourceManager.getInstance().setUpdateListener(this);
        DataSourceManager.getInstance().refreshData();
    }

    private void initAdapter() {
        adapter=new UserAdapter(this);
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUsers.setAdapter(adapter);
    }

    @Override
    public void onGameUpdate(Game game) {

    }

    @Override
    public void onOnUserUpdate(User user) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onOnSuggestionGameUpdate(SuggestionGame suggestionGame) {

    }

    @Override
    public void onOnSuggestionGamesChange() {

    }

    @Override
    public void onOnUsersChange() {
        initAdapter();
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSelectUser(User user) {
        UIUtils.showToast(user.nickName);
    }
}