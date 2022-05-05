package com.moshe.glaz.sudoku.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.moshe.glaz.sudoku.R;
import com.moshe.glaz.sudoku.app.AppBase;
import com.moshe.glaz.sudoku.enteties.User;
import com.moshe.glaz.sudoku.managers.DataSourceManager;
import com.moshe.glaz.sudoku.ui.modelView.FullUserModelView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    onSelectListener listener;
    private List<User> mData;
    private LayoutInflater mInflater;

    public interface onSelectListener {
        void onSelectUser(User user);
    }

    public UserAdapter(onSelectListener listener) {
        this.mInflater = LayoutInflater.from(AppBase.getContext());
        this.mData = DataSourceManager.getInstance().getUsersAsList();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_full, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mData.get(position);
        holder.modelView.setUser(user);
        holder.modelView.initUi();
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FullUserModelView modelView;

        ViewHolder(View itemView) {
            super(itemView);
            modelView = new FullUserModelView(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onSelectUser(modelView.getUser());
        }
    }

}