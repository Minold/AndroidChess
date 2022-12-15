package com.chess.cs213.chessandroid59.chess;

import com.chess.cs213.chessandroid59.pieces.*;
import static com.chess.cs213.chessandroid59.pieces.King.castledK;
import static com.chess.cs213.chessandroid59.pieces.King.castledQ;

/**
 *
 * @author Jaemin Shin
 * @author Minyoung Chung
 *
 */

public class Chess {

    static boolean whiteTurn = true;

    static boolean askDraw=false;

    public static void setUpGame(Board chessBoard) {
        chessBoard = new Board();
        chessBoard.drawBoard();
    }

    public static void newBoardState(String input, Board chessBoard) {
        String[] tokens = input.trim().toLowerCase().split("\\s+");
        if (tokens.length == 0 || tokens.length > 3) {
            System.out.println("Illegal move, try again");
            if (whiteTurn) {
                System.out.println("White's move: ");
            } else {
                System.out.println("Black's move: ");
            }
        } else if (tokens.length == 1) {
            if (tokens[0].equals("resign")) {
                if (whiteTurn) {
                    System.out.println("Black wins");
                }
                else {
                    System.out.println("White wins");
                }
                System.exit(1);
            } else if (tokens[0].equals("draw")) {
                if (askDraw) {
                    System.out.println("Draw");
                    System.exit(1);
                } else {
                    System.out.println("Opponent has not asked for a draw yet");
                }
            }
        } else {
            int xfrom = Math.abs('a'-tokens[0].charAt(0));
            int yfrom = Math.abs(8-Character.getNumericValue(tokens[0].charAt(1)));
            int xto = Math.abs('a'-tokens[1].charAt(0));
            int yto = Math.abs(8-Character.getNumericValue(tokens[1].charAt(1)));
            int currColor = whiteTurn ? 0 : 1;
            Spot currSpot = chessBoard.grid[xfrom][yfrom];
            Spot destSpot = chessBoard.grid[xto][yto];

            if (currSpot.isEmpty()){
                System.out.println("Illegal move, try again");
            }else if(currSpot.getPiece().getColor() == currColor && currSpot.getPiece().validMoveWithoutCheck(chessBoard, currSpot, destSpot) && chessBoard.isPathEmpty(currSpot, destSpot)) {
                boolean pawnPromo = false;
                boolean enPassant = false;
                if (King.castledK) {
                    if (whiteTurn){
                        chessBoard.grid[5][7].setPiece(chessBoard.grid[7][7].getPiece());
                        chessBoard.grid[7][7].setPiece(null);
                    }else{
                        chessBoard.grid[5][0].setPiece(chessBoard.grid[7][0].getPiece());
                        chessBoard.grid[7][0].setPiece(null);
                    }
                    King.castledK = false;
                }
                if (King.castledQ) {
                    if (whiteTurn){
                        //same thing move rook only
                        chessBoard.grid[3][7].setPiece(chessBoard.grid[0][7].getPiece());
                        chessBoard.grid[0][7].setPiece(null);
                    }else{
                        chessBoard.grid[3][0].setPiece(chessBoard.grid[0][0].getPiece());
                        chessBoard.grid[0][0].setPiece(null);
                    }
                    King.castledQ = false;
                }
                ChessPiece mover = currSpot.getPiece();
                ChessPiece destPiece=destSpot.getPiece();

                if (mover.getPieceName().substring(1).equals("p")) {
                    Pawn currPawn = (Pawn) mover;
                    if (whiteTurn) {
                        if (currSpot.getYCoordinate() == 1){
                            char toPromo = tokens.length>2 ? tokens[2].charAt(0) : 'Q';
                            pawnPromotion(currSpot, destSpot, toPromo, 0);
                            pawnPromo = true;
                        }else if (currPawn.getEnPassant()){
                            enPassant = true;
                            chessBoard.grid[xto][yto+1].setPiece(null);
                            destSpot.setPiece(mover);
                        }
                    }else{
                        if (currSpot.getYCoordinate() == 6){
                            char toPromo = tokens.length>2 ? tokens[2].charAt(0) : 'Q';
                            pawnPromotion(currSpot, destSpot, toPromo, 1);
                            pawnPromo = true;
                        }else if (currPawn.getEnPassant()){
                            enPassant = true;
                            chessBoard.grid[xto][yto-1].setPiece(null);
                            destSpot.setPiece(mover);
                        }
                    }
                }

                if (!pawnPromo && !enPassant) {
                    if (destSpot.getPiece() != null){
                    }
                    destSpot.setPiece(mover);
                    currSpot.setPiece(null);
                    if (whiteTurn && isKingInCheck(0,findKingPosition(0, chessBoard), chessBoard)) {
                        System.out.println("Illegal move, try again");
                        currSpot.setPiece(mover);
                        destSpot.setPiece(destPiece);
                        return;
                    }
                    else if (!whiteTurn && isKingInCheck(1,findKingPosition(1, chessBoard), chessBoard)) {
                        System.out.println("Illegal move, try again");
                        currSpot.setPiece(mover);
                        destSpot.setPiece(destPiece);
                        return;
                    }
                }
                currSpot.setPiece(null);
                chessBoard.drawBoard();
                whiteTurn = whiteTurn ? false : true;
            } else {
                System.out.println("Illegal move, try again");

            }
            if (tokens.length>2 && tokens[2].equals("draw?")) {
                askDraw=true;
            }

        }
    }

    public static void pawnPromotion(Spot curr, Spot dest, char promo, int color){
        if (Character.toLowerCase(promo) == 'r'){
            dest.setPiece(new Rook(color));
        } else if (Character.toLowerCase(promo) == 'n') {
            dest.setPiece(new Knight(color));
        } else if (Character.toLowerCase(promo) == 'b') {
            dest.setPiece(new Bishop(color));
        } else {
            dest.setPiece(new Queen(color));
        }
    }

    public static boolean isKingInCheck(int color, Spot kingPosition, Board chessBoard) {
        if (color==0) {
            for (int i=0;i<8;i++) {
                for (int j=0;j<8;j++) {
                    if (chessBoard.grid[j][i].getPiece()!=null && chessBoard.grid[j][i].getPiece().getColor()==1 && chessBoard.grid[j][i].getPiece().validMove(chessBoard, chessBoard.grid[j][i], kingPosition ) && chessBoard.isPathEmpty(chessBoard.grid[j][i], kingPosition)) {
                        return true;
                    }
                }
            }
        } else {
            for (int i=0;i<8;i++) {
                for (int j=0;j<8;j++) {
                    if (chessBoard.grid[j][i].getPiece()!=null && chessBoard.grid[j][i].getPiece().getColor()==0 && chessBoard.grid[j][i].getPiece().validMove(chessBoard, chessBoard.grid[j][i], kingPosition ) && chessBoard.isPathEmpty(chessBoard.grid[j][i], kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCheckMate(int color, Board chessBoard) {
        Spot kingPosition=findKingPosition(color, chessBoard);

        int[] xDirection=new int[] {-1,0,1,-1,1,-1,0,1};
        int[] yDirection=new int[] {-1,-1,-1,0,0,1,1,1};

        for (int i=0;i<xDirection.length;i++) {
            int newX=kingPosition.getXCoordinate()+xDirection[i];
            int newY=kingPosition.getYCoordinate()+yDirection[i];
            if (newX<0 || newX>7 || newY<0 || newY>7) {
                continue;
            }
            Spot endPosition=chessBoard.grid[newX][newY];
            if (kingPosition.getPiece().validMoveWithoutCheck(chessBoard, kingPosition, endPosition)) {
                if (!isKingInCheck(color,endPosition, chessBoard)) {
                    return false;
                }
            }
        }
        if (!validMoves(color, chessBoard)) {
            return true;
        }
        return false;
    }

    public static Spot findKingPosition(int color, Board chessBoard) {
        Spot kingPosition=null;
        if (color==0) {
            for (int i=0;i<8;i++) {
                for (int j=0;j<8;j++) {
                    if (!chessBoard.grid[j][i].isEmpty() && chessBoard.grid[j][i].getPiece().getPieceName().equals("wK")) {
                        kingPosition=chessBoard.grid[j][i];
                        break;
                    }
                }
            }
        } else if (color==1) {
            for (int i=0;i<8;i++) {
                for (int j=0;j<8;j++) {
                    if (!chessBoard.grid[j][i].isEmpty() && chessBoard.grid[j][i].getPiece().getPieceName().equals("bK")) {
                        kingPosition=chessBoard.grid[j][i];
                        break;
                    }
                }
            }
        }
        return kingPosition;
    }

    public static boolean validMoves(int color, Board chessBoard) {
        String pieceToExclude="";
        if (color==0) {
            pieceToExclude="wK";
        }
        else {
            pieceToExclude="bK";
        }
        for (int i=0;i<8;i++) {
            for (int j=0;j<8;j++) {
                if (chessBoard.grid[j][i].getPiece()!=null && chessBoard.grid[j][i].getPiece().getColor()==color && !chessBoard.grid[j][i].getPiece().getPieceName().equals(pieceToExclude)) {
                    for (int a=0;a<8;a++) {
                        for (int b=0;b<8;b++) {
                            Spot newPosition=chessBoard.grid[b][a];
                            ChessPiece newPositionPiece=newPosition.getPiece();
                            ChessPiece current=chessBoard.grid[j][i].getPiece();
                            if (chessBoard.grid[j][i].getPiece().validMoveWithoutCheck(chessBoard, chessBoard.grid[j][i], newPosition) && chessBoard.isPathEmpty(chessBoard.grid[j][i], newPosition)) {

                                newPosition.setPiece(current);
                                chessBoard.grid[j][i].setPiece(null);
                                if (!isKingInCheck(color,findKingPosition(color, chessBoard), chessBoard)) {
                                    chessBoard.grid[j][i].setPiece(current);
                                    newPosition.setPiece(newPositionPiece);
                                    return true;
                                }
                            }
                            chessBoard.grid[j][i].setPiece(current);
                            newPosition.setPiece(newPositionPiece);
                        }
                    }
                }
            }
        }
        return false;
    }
}