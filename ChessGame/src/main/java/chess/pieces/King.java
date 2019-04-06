package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.enums.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece == null || isThereAnOponentPiece(position);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(position.getRow(), position.getColumn());

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                pos.setValues(position.getRow() + i, position.getColumn() + j);

                if (getBoard().positionExists(pos) && canMove(pos))
                    mat[pos.getRow()][pos.getColumn()] = true;
            }
        }

        // Castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // Kingside
            Position rookPos = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(rookPos)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() + 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() + 2);
                if (!getBoard().thereIsAPiece(pos1) && !getBoard().thereIsAPiece(pos2)) {
                    mat[pos2.getRow()][pos2.getColumn()] = true;
                }
            }

            // Queen side
            rookPos = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(rookPos)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() - 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() - 2);
                Position pos3 = new Position(position.getRow(), position.getColumn() - 3);
                if (!getBoard().thereIsAPiece(pos1) && !getBoard().thereIsAPiece(pos2)
                        && !getBoard().thereIsAPiece(pos3)) {
                    mat[pos2.getRow()][pos2.getColumn()] = true;
                }
            }
        }

        return mat;
    }
}