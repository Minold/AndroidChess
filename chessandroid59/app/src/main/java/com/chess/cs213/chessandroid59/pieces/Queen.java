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
public class Queen extends ChessPiece{
    String pieceName="Q";
    int pieceColor=0;
    int previousChange = 0;

    public Queen(int color) {
        this.setColor(color);
        this.pieceColor=color;
    }

    public Queen(int color, int prevChange) {
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
            this.pieceName="wQ";
        } else {
            this.pieceName="bQ";
        }
        return this.pieceName;
    }

    @Override
    public boolean validMove(Board board, Spot startPosition, Spot endPosition) {
        int yChange=Math.abs(endPosition.getYCoordinate()-startPosition.getYCoordinate());
        int xChange=Math.abs(endPosition.getXCoordinate()-startPosition.getXCoordinate());

        if ((xChange==yChange) || (xChange == 0 && yChange != 0) || (xChange != 0 && yChange == 0)){
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

        if ((xChange==yChange) || (xChange == 0 && yChange != 0) || (xChange != 0 && yChange == 0)){
            previousChange = yChange;
            return true;
        }
        return false;
    }
}