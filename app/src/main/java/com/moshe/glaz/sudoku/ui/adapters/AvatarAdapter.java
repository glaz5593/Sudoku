package com.moshe.glaz.sudoku.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moshe.glaz.sudoku.R;
import com.moshe.glaz.sudoku.app.AppBase;
import com.moshe.glaz.sudoku.managers.DataSourceManager;
import com.moshe.glaz.sudoku.managers.LogicManager;

public class AvatarAdapter extends ArrayAdapter<Integer> {
    onSelectListener listener;

    public interface onSelectListener {
        void onSelectAvatar(Integer avatarId);
    }

    public AvatarAdapter(onSelectListener listener) {
        super(AppBase.getContext(), R.layout.item_avatar, DataSourceManager.getInstance().getAvatarResList());
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(AppBase.getContext()).inflate(R.layout.item_avatar, parent, false);

        ImageView iv_icon = convertView.findViewById(R.id.iv_icon);
        iv_icon.setImageResource(getItem(position));

        convertView.setTag(position+1);
        convertView.setOnClickListener(
                v -> {
                    listener.onSelectAvatar((Integer) v.getTag());
                });
        return convertView;
    }
}
