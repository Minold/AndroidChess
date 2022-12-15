package com.chess.cs213.chessandroid59.chess;
import com.chess.cs213.chessandroid59.pieces.ChessPiece;

/**
 *
 * @author Jaemin Shin
 * @author Minyoung Chung
 *
 */
public class Spot {
    int x;
    int y;
    ChessPiece piece;
    public Spot(int x, int y, ChessPiece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public ChessPiece getPiece()

    {
        return this.piece;
    }
    public void setPiece(ChessPiece p)

    {
        this.piece = p;
    }
    public boolean isEmpty() { return piece == null;}
    public int getXCoordinate()

    {
        return this.x;
    }
    public int getYCoordinate()

    {
        return this.y;
    }
}