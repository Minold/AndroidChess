package com.chess.cs213.chessandroid59.pieces;
import com.chess.cs213.chessandroid59.chess.Board;
import com.chess.cs213.chessandroid59.chess.Spot;

import com.chess.cs213.chessandroid59.chess.*;


/**
 *
 * @author Jaemin Shin
 * @author Minyoung Chung
 *
 */
public abstract class ChessPiece {
    int color=0;
    boolean first=true;
    int previousChange;

    public void setColor(int col) {
        this.color = col;
    }
    public void setFirst(boolean bool) {first = bool;}
    public int getColor() {
        return this.color;
    }
    public abstract String getPieceName();
    public abstract boolean validMove(Board board, Spot startPosition, Spot endPosition);
    public abstract boolean validMoveWithoutCheck(Board board, Spot startPosition, Spot endPosition);
    public abstract int getPreviousChange();
}