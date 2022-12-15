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
public class Bishop extends ChessPiece{
    String pieceName="B";
    int pieceColor=0;
    int previousChange = 0;
    public Bishop(int color) {
        this.setColor(color);
        this.pieceColor=color;
    }

    public Bishop(int color, int prevChange) {
        this.setColor(color);
        this.pieceColor=color;
        this.previousChange = prevChange;
    }

    public int getPreviousChange() {
        return previousChange;
    }
    @Override
    public String getPieceName() {
        if (pieceColor==0) {
            this.pieceName="wB";
        } else {
            this.pieceName="bB";
        }
        return this.pieceName;
    }

    @Override
    public boolean validMove(Board board, Spot startPosition, Spot endPosition) {
        int yChange=Math.abs(endPosition.getYCoordinate()-startPosition.getYCoordinate());
        int xChange=Math.abs(endPosition.getXCoordinate()-startPosition.getXCoordinate());

        if (yChange==xChange){
            previousChange = yChange;
            return true;
        }
        return false;
    }
    @Override
    public boolean validMoveWithoutCheck(Board board, Spot startPosition, Spot endPosition) {
        if (!endPosition.isEmpty() && endPosition.getPiece().getColor()==this.getColor()) {
            return false;
        }
        int yChange=Math.abs(endPosition.getYCoordinate()-startPosition.getYCoordinate());
        int xChange=Math.abs(endPosition.getXCoordinate()-startPosition.getXCoordinate());

        if (yChange==xChange){
            previousChange = yChange;
            return true;
        }
        return false;
    }
}