package com.moshe.glaz.sudoku.ui.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.moshe.glaz.sudoku.enteties.Position;
import com.moshe.glaz.sudoku.enteties.User;
import com.moshe.glaz.sudoku.enteties.sudoku.BoardUIValues;
import com.moshe.glaz.sudoku.enteties.sudoku.DataSource;
import com.moshe.glaz.sudoku.enteties.sudoku.Game;
import com.moshe.glaz.sudoku.enteties.sudoku.Player;
import com.moshe.glaz.sudoku.enteties.sudoku.SelectedCell;
import com.moshe.glaz.sudoku.enteties.sudoku.values.BoardUIValue;
import com.moshe.glaz.sudoku.infrastructure.TextUtils;
import com.moshe.glaz.sudoku.managers.DataSourceManager;
import com.moshe.glaz.sudoku.managers.LogicManager;
import com.moshe.glaz.sudoku.managers.SudokuManager;
import com.moshe.glaz.sudoku.ui.views.CellTextView;

import java.util.ArrayList;
import java.util.Date;

public class SudokuActivityVM {
    public SudokuActivityVM(Context context,SudokuActivityVMListener listener){
        this.context = context;
        this.listener = listener;

    }

    public boolean numberButtonClick(int value) {
        if (myPlayer.suggestionBoard.has(myPlayer.selectedCell.position, value)) {
            myPlayer.suggestionBoard.remove(myPlayer.selectedCell.position, value);
            listener.onBoardUpdate();
            return false;
        } else {
            myPlayer.suggestionBoard.add(myPlayer.selectedCell.position, value);
            listener.onBoardUpdate();
            return true;
        }
     }


    interface SudokuActivityVMListener{
        void onBoardUpdate();
        void onSelectedCellUpdate();
    }

    Context context;

    DataSource dataSource;
    SudokuActivityVMListener listener;
    User myUser;
    User otherUser;
    Game game;
    Player myPlayer;
    Player otherPlayer;
    int myPlayerColor;
    int otherPlayerColor;
    int baseColor;

    public void init() {

        game = SudokuManager.getInstance().getActiveGame();
        if (game == null) {
            return;
        }

        boolean isMyPlayerUser1 = LogicManager.getInstance().getUser().uid.equals(game.user1.uid);

        myPlayer = isMyPlayerUser1 ? game.user1 : game.user2;
        myUser = DataSourceManager.getInstance().getUser(myPlayer.uid);
        myPlayerColor = getPlayerColor(myUser.avatar);

        otherPlayer = isMyPlayerUser1 ? game.user2 : game.user1;
        otherUser = DataSourceManager.getInstance().getUser(otherPlayer.uid);
        otherPlayerColor = getPlayerColor(otherUser.avatar);

        dataSource = DataSourceManager.getInstance().getSudokuDataSource(game.dataSourceId);
    }

    public boolean hasActiveGame() {
        return game != null;
    }

    public String getNickName1() {
        return myUser.nickName;
    }

    public String getUserStatus1() {
        return myUser.status;
    }

    public int getAvatarResId1() {
        return DataSourceManager.getInstance().getAvatarResId(myUser.avatar);
    }

    public String getScoreHtmlText2() {
        return getScoreHtmlText(otherPlayer);
    }

    public String getNickName2() {
        return otherUser.nickName;
    }

    public String getUserStatus2() {
        return otherUser.status;
    }

    public int getAvatarResId2() {
        return DataSourceManager.getInstance().getAvatarResId(otherUser.avatar);
    }

    public String getScoreHtmlText1() {
        return getScoreHtmlText(myPlayer);
    }


    private String getSuggestionHtmlText(int x, int y) {
        ArrayList<Integer> values = myPlayer.suggestionBoard.asValues(x, y);
        if (values.size() == 0) {
            return ("");
        }

        //if (values.size()==1){
        //    return TextUtils.getHTMLText_green(values.get(0)+"");
        //}

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < 10; i++) {
            if (values.contains(i)) {
                builder.append(TextUtils.getHTMLText_green(i + ""));
            } else {
                builder.append(TextUtils.getHTMLText_white(i + ""));
            }

            if (i == 3 || i == 6) {
                builder.append(TextUtils.getHTMLEnter());
            } else {
                if (i != 9) {
                    builder.append("  ");
                }
            }
        }

        return builder.toString();
    }

    private int getPlayerColor(int avatar) {
        Drawable drawable = context.getDrawable(DataSourceManager.getInstance().getAvatarResId(avatar));

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        int x = bitmap.getWidth() / 20;
        int y = bitmap.getHeight() / 2;
        int pixel = bitmap.getPixel(x, y);
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);

        while (redValue + blueValue + greenValue > 250) {
            redValue = (int) (redValue * 0.9f);
            blueValue = (int) (blueValue * 0.9f);
            greenValue = (int) (greenValue * 0.9f);
        }

        return Color.rgb(redValue, blueValue, greenValue);
    }

    private String getScoreHtmlText(Player user) {
        StringBuilder builder = new StringBuilder();

        builder.append("ניקוד:");
        builder.append(user.getScore());
        builder.append(" ");
        int score = user.getActiveActionScore();
        if (score > 0) {
            StringBuilder builder1 = new StringBuilder();
            builder1.append("(+");
            builder1.append(score);
            builder1.append(")");
            builder.append(TextUtils.getHTMLText_blue(builder1.toString()));
        }

        return builder.toString();
    }

    private int getRectNumber(int x, int y) {
        int xx = x / 3;
        xx++;
        xx *= 100;
        int yy = y / 3;
        yy++;
        return xx + yy;
    }

    public BoardUIValues getBoardUIValues() {
        BoardUIValues res = new BoardUIValues();

        int rectNumSelected = 0;
        ArrayList<Integer> selectedValues = new ArrayList<>();
        // בודק אם השחקן שלי תפס משבצת

        if (myPlayer.selectedCell != null) {
            int boardValue = game.getBoardValue(myPlayer.selectedCell.position.x, myPlayer.selectedCell.position.y);
            if (boardValue > 0) {
                selectedValues.add(boardValue);
            } else {
                selectedValues = myPlayer.suggestionBoard.asValues(myPlayer.selectedCell.position);
            }
            rectNumSelected = getRectNumber(myPlayer.selectedCell.position.x, myPlayer.selectedCell.position.y);
        }

        //עובר בלולאה על כל הפקדים כדי להגדיר את הנתונים שלהם
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                BoardUIValue boardUI = new BoardUIValue();
                boolean isSelected = myPlayer.selectedCell != null && myPlayer.selectedCell.position.equals(x, y);

                //
                // set state
                //
                ArrayList<Integer> badValues = new ArrayList<>();
                if (myPlayer.selectedCell != null) {
                    int rectNum = getRectNumber(x, y);
                    int value = game.getBoardValue(x, y);

                    boolean isSameRect = rectNum == rectNumSelected;
                    boolean isSameRow = myPlayer.selectedCell.position.y == y;
                    boolean isSameLine = myPlayer.selectedCell.position.x == x;
                    boolean isContains = selectedValues.contains(value);

                    boardUI.selected = isSameRow || isSameLine || isSameRect;
                    boardUI.activated = isSelected || isContains;
                    boardUI.checked = isSelected && game.getBoardValue(x, y) == 0;
                    if (!isSelected && (isSameRow || isSameLine || isSameRect) && isContains) {
                        badValues.add(value);
                    }
                } else {
                    boardUI.selected = false;
                    boardUI.activated = false;
                    boardUI.checked = false;
                }

                //
                // setText
                //
                if (myPlayer.board.get(x, y) > 0) {
                    boardUI.text = myPlayer.board.get(x, y) + "";
                } else if (otherPlayer.board.get(x, y) > 0) {
                    boardUI.text = otherPlayer.board.get(x, y) + "";
                } else if (dataSource.baseValues.get(x, y) > 0) {
                    boardUI.text = dataSource.baseValues.get(x, y) + "";
                } else {
                    boardUI.text = getSuggestionHtmlText(x, y);
                }

                res.set(x, y, boardUI);
            }
        }

        return res;
    }

    public void onSelectCell(Position position) {
        if(myPlayer.selectedCell != null) {
            if (myPlayer.selectedCell.position.equals(position)) {
                setRealValue(position);
            }
        }

        myPlayer.selectedCell = new SelectedCell();
        myPlayer.selectedCell.position=position;
        myPlayer.selectedCell.time=new Date().getTime();
        listener.onBoardUpdate();
        listener.onSelectedCellUpdate();
    }

    private void setRealValue(Position position) {
        ArrayList<Integer> values = myPlayer.suggestionBoard. asValues(position);
        if (values.size() == 1) {
            int value=values.get(0);
            if(dataSource.values.get(position)==value) {
                myPlayer.addAction(position, value);
            }else{
                myPlayer.addBadAction(position, value);
            }
            listener.onBoardUpdate();
            listener.onSelectedCellUpdate();
        }
    }


    public boolean hasSelectedCell() {
        return myPlayer.selectedCell!=null;
    }

    public ArrayList<Integer> getButtonsSelectedValues() {
        if (myPlayer.selectedCell == null) {
            return null;
        }

        return myPlayer.suggestionBoard.asValues(myPlayer.selectedCell.position);
    }
}