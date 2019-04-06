package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);
        pos.setValues(position.getRow(), position.getColumn());

        int inc = (getColor() == Color.WHITE) ? -1 : 1;

        pos.setRow(pos.getRow() + inc);

        if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos))
            mat[pos.getRow()][pos.getColumn()] = true;

        if (getMoveCount() == 0) {
            // incrementing the already incremented position
            pos.setRow(pos.getRow() + inc);

            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos))
                mat[pos.getRow()][pos.getColumn()] = true;
        }

        for (int i = -1; i < 2; i += 2) {
            pos.setValues(position.getRow(), position.getColumn());
            pos.setRow(pos.getRow() + inc);

            pos.setColumn(pos.getColumn() + i);
            if (getBoard().positionExists(pos) && isThereAnOponentPiece(pos))
                mat[pos.getRow()][pos.getColumn()] = true;
        }

        // Special move en passant
        if (position.getRow() == 3 || position.getRow() == 4) {
            Position left = new Position(position.getRow(), position.getColumn() - 1);
            if (getBoard().positionExists(left) && isThereAnOponentPiece(left)
                    && getBoard().piece(left) == chessMatch.getEnPassantVulnarable()) {
                mat[left.getRow() + inc][left.getColumn()] = true;
            }

            Position right = new Position(position.getRow(), position.getColumn() + 1);
            if (getBoard().positionExists(right) && isThereAnOponentPiece(right)
                    && getBoard().piece(right) == chessMatch.getEnPassantVulnarable()) {
                mat[right.getRow() + inc][right.getColumn()] = true;
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}