package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);

        for (int i = -2; i <= 2; i += 4) {
            for (int j = -1; j <= 1; j += 2) {
                pos.setValues(position.getRow() - i, position.getColumn() + j);
                if (getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereAnOponentPiece(pos)))
                    mat[pos.getRow()][pos.getColumn()] = true;
            }
        }

        for (int i = -1; i <= 1; i += 2) {
            for (int j = -2; j <= 2; j += 4) {
                pos.setValues(position.getRow() - i, position.getColumn() + j);
                if (getBoard().positionExists(pos) && (!getBoard().thereIsAPiece(pos) || isThereAnOponentPiece(pos)))
                    mat[pos.getRow()][pos.getColumn()] = true;
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "k";
    }
}