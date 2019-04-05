package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.enums.Color;

public class Queen extends ChessPiece {

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);

        horizontalAndVertical(mat, pos);
        diagonals(mat, pos);

        return mat;
    }

    private void horizontalAndVertical(boolean[][] mat, Position pos) {
        // above
        pos.setValues(position.getRow() - 1, position.getColumn());

        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
            pos.setRow(pos.getRow() - 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
        }

        // left
        pos.setValues(position.getRow(), position.getColumn() - 1);

        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
            pos.setColumn(pos.getColumn() - 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
        }

        // right
        pos.setValues(position.getRow(), position.getColumn() + 1);

        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
            pos.setColumn(pos.getColumn() + 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
        }

        // below
        pos.setValues(position.getRow() + 1, position.getColumn());

        while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
            pos.setRow(pos.getRow() + 1);
        }
        if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos)) {
            mat[pos.getRow()][pos.getColumn()] = true;
        }
    }

    private void diagonals(boolean[][] mat, Position pos) {
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
    }

    private boolean canMoveTo(Position position) {
        return getBoard().positionExists(position) && (!getBoard().thereIsAPiece(position));
    }

    @Override
    public String toString() {
        return "Q";
    }
}