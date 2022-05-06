package com.moshe.glaz.sudoku.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.text.HtmlCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.moshe.glaz.sudoku.R;
import com.moshe.glaz.sudoku.databinding.ActivitySudokuBinding;
import com.moshe.glaz.sudoku.enteties.Position;
import com.moshe.glaz.sudoku.enteties.User;
import com.moshe.glaz.sudoku.enteties.sudoku.BoardUIValues;
import com.moshe.glaz.sudoku.enteties.sudoku.DataSource;
import com.moshe.glaz.sudoku.enteties.sudoku.Game;
import com.moshe.glaz.sudoku.enteties.sudoku.Player;
import com.moshe.glaz.sudoku.enteties.sudoku.SelectedCell;
import com.moshe.glaz.sudoku.enteties.sudoku.values.BoardUIValue;
import com.moshe.glaz.sudoku.managers.DataSourceManager;
import com.moshe.glaz.sudoku.managers.LogicManager;
import com.moshe.glaz.sudoku.managers.SudokuManager;
import com.moshe.glaz.sudoku.infrastructure.*;
import com.moshe.glaz.sudoku.ui.views.CellTextView;

import java.util.ArrayList;
import java.util.Date;

public class SudokuActivity extends AppCompatActivity implements SudokuActivityVM.SudokuActivityVMListener {
ActivitySudokuBinding binding;
    SudokuActivityVM vm;
    ArrayList<CellTextView> views;
    TextView[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySudokuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UIUtils.setStatusBarColor(this);
        buttons = new TextView[]{
                binding.btn1,
                binding.btn2,
                binding.btn3,
                binding.btn4,
                binding.btn5,
                binding.btn6,
                binding.btn7,
                binding.btn8,
                binding.btn9
        };
        for(TextView tv:buttons){
            tv.setTag(Utils.getInt(tv.getText().toString()));
            tv.setOnClickListener(v->onNumberButtonClick(v));
        }

        vm=new SudokuActivityVM(SudokuActivity.this,SudokuActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        vm.init();

        if(!vm.hasActiveGame())   {
            finish();
            return;
        }

        if (views == null) {
            initBoardViews();
        }

        initUsersUi();
        initViews();
    }

    private void initUsersUi() {
        binding.tvData1.setText(vm.getNickName1());
        binding.ivIcon1.setImageResource(vm.getAvatarResId1());
        binding.tvDescription1.setText(vm.getUserStatus1());
        binding.tvScore1.setText(Html.fromHtml(vm.getScoreHtmlText1(), HtmlCompat.FROM_HTML_MODE_LEGACY));

        binding.tvData2.setText(vm.getNickName2());
        binding.ivIcon2.setImageResource(vm.getAvatarResId2());
        binding.tvDescription2.setText(vm.getUserStatus2());
        binding.tvScore2.setText(Html.fromHtml(vm.getScoreHtmlText2(), HtmlCompat.FROM_HTML_MODE_LEGACY));
    }



    private void initBoardViews() {
        int size = 9;
        views = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            CellTextView view = new CellTextView(this);
            view.setLayoutParams(new ConstraintLayout.LayoutParams(0, 0));
            view.setId(View.generateViewId());
            view.setPosition(new Position(i % size, i / size));
            view.setGravity(Gravity.CENTER);
            view.setBackgroundResource(R.drawable.sudoku_background);
            view.setFocusable(true);
            view.setSelected(false);
            view.setActivated(false);
            view.setText(i + "");
            view.setOnClickListener(v -> {
                onCellClick(((CellTextView)v).getPosition());
                v.setActivated(true);
            });
            binding.clBoard.addView(view, 0);
            views.add(view);
        }

        for (int i = 0; i < size * size; i++) {
            CellTextView view = views.get(i);
            ConstraintSet set = new ConstraintSet();
            set.clone(binding.clBoard);

            int marginTop= (i / size ==3 || i / size==6) ? 2 : 1;
            int marginBottom=(i / size ==2 || i / size==5) ? 2 : 1;
            int marginStart=(i % size ==3 || i % size==6) ? 2 : 1;
            int marginEnd=(i % size ==2 || i % size==5) ? 2 : 1;

            if (i < size) {
                set.connect(view.getId(), ConstraintSet.TOP, binding.clBoard.getId(), ConstraintSet.TOP, marginTop);
            } else {
                set.connect(view.getId(), ConstraintSet.TOP, views.get(i - size).getId(), ConstraintSet.BOTTOM, marginTop);
            }
            if (i >= (size * size) - size) {
                set.connect(view.getId(), ConstraintSet.BOTTOM, binding.clBoard.getId(), ConstraintSet.BOTTOM, marginBottom);
            } else {
                set.connect(view.getId(), ConstraintSet.BOTTOM, views.get(i + size).getId(), ConstraintSet.TOP, marginBottom);
            }
            if (i % size == 0) {
                set.connect(view.getId(), ConstraintSet.START, binding.clBoard.getId(), ConstraintSet.START, marginStart);
            } else {
                set.connect(view.getId(), ConstraintSet.START, views.get(i - 1).getId(), ConstraintSet.END, marginStart);
            }
            if ((i + 1) % size == 0) {
                set.connect(view.getId(), ConstraintSet.END, binding.clBoard.getId(), ConstraintSet.END, marginEnd);
            } else {
                set.connect(view.getId(), ConstraintSet.END, views.get(i + 1).getId(), ConstraintSet.START, marginEnd);
            }
            set.applyTo(binding.clBoard);
        }
    }

    void initViews(){
        BoardUIValues values = vm.getBoardUIValues();
        for(CellTextView tv : views){
            BoardUIValue value= values.get(tv.getPosition());
            if(value.getHashCode().equals(tv.getTag())){
                continue;
            }

            tv.setChecked  (value.checked);
            tv.setSelected (value.selected);
            tv.setActivated(value.activated);
            tv.setText(Html.fromHtml(value.text,HtmlCompat.FROM_HTML_MODE_LEGACY));
         }
     }

    private void onCellClick(Position position) {
         vm.onSelectCell(position);
    }

    private void initButtons() {
        if (!vm.hasSelectedCell()) {
            for (TextView tv : buttons) {
                tv.setSelected(false);
            }
            return;
        }

        ArrayList<Integer> values = vm.getButtonsSelectedValues();
        for (TextView tv : buttons) {
            tv.setSelected(values.contains(tv.getTag()));
        }
    }

    private void onNumberButtonClick(View view) {
        if (!vm.hasSelectedCell()) {
            return;
        }

        int value = (Integer) view.getTag();
        view.setSelected(vm.numberButtonClick(value));
    }

    @Override
    public void onBoardUpdate() {
        initViews();
    }

    @Override
    public void onSelectedCellUpdate() {
        initButtons();
    }
}