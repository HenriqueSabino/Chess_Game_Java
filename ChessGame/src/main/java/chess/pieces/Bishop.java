package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);

        // NW diagonal
        pos.setValues(position.getRow() - 1, position.getColumn() - 1);

        while (canMoveTo(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;

            pos.setRow(pos.getRow() - 1);
            pos.setColumn(pos.getColumn() - 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos))
            mat[pos.getRow()][pos.getColumn()] = true;

        // NE diagonal
        pos.setValues(position.getRow() - 1, position.getColumn() + 1);

        while (canMoveTo(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;

            pos.setRow(pos.getRow() - 1);
            pos.setColumn(pos.getColumn() + 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos))
            mat[pos.getRow()][pos.getColumn()] = true;

        // SE diagonal
        pos.setValues(position.getRow() + 1, position.getColumn() + 1);

        while (canMoveTo(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;

            pos.setRow(pos.getRow() + 1);
            pos.setColumn(pos.getColumn() + 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos))
            mat[pos.getRow()][pos.getColumn()] = true;

        // SW diagonal
        pos.setValues(position.getRow() + 1, position.getColumn() - 1);

        while (canMoveTo(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;

            pos.setRow(pos.getRow() + 1);
            pos.setColumn(pos.getColumn() - 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos))
            mat[pos.getRow()][pos.getColumn()] = true;
        return mat;
    }

    private boolean canMoveTo(Position position) {
        return getBoard().positionExists(position) && (!getBoard().thereIsAPiece(position));
    }

    @Override
    public String toString() {
        return "B";
    }
}