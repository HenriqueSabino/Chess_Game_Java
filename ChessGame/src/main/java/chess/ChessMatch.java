package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Position;
import chess.enums.Color;
import chess.exceptions.ChessException;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;
    private int turn;
    private Color currentPlayer;
    private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private List<ChessPiece> capturedPieces = new ArrayList<>();
    private boolean check;
    private boolean checkMate;

    public ChessMatch() {
        turn = 1;
        currentPlayer = Color.WHITE;

        board = new Board(8, 8);
        initialSetup();
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }

        return mat;
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece peformChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();

        validateSourcePosition(source);
        validateTargetPosition(source, target);
        ChessPiece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You cannot put yourself in check");
        }

        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private ChessPiece makeMove(Position source, Position target) {
        ChessPiece sourcePiece = (ChessPiece) board.removePiece(source);
        sourcePiece.increaseMoveCount();
        ChessPiece capturedPiece = (ChessPiece) board.removePiece(target);
        board.placePiece(sourcePiece, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, ChessPiece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateSourcePosition(Position source) {
        if (!board.thereIsAPiece(source)) {
            throw new ChessException("There's no piece on source position");
        }
        if (((ChessPiece) board.piece(source)).getColor() != currentPlayer)
            throw new ChessException("The chosen piece is not yours");
        if (!board.piece(source).isThereAnyPossibleMove()) {
            throw new ChessException("There's no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece cannot move to the target position");
        }
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<ChessPiece> list = piecesOnTheBoard.stream().filter(p -> p.getColor() == color)
                .collect(Collectors.toList());

        for (ChessPiece p : list) {
            if (p instanceof King) {
                return p;
            }
        }

        throw new IllegalStateException("There's no " + color + "king on the board");
    }

    private boolean testCheck(Color color) {
        ChessPiece king = king(color);
        List<ChessPiece> opponents = piecesOnTheBoard.stream().filter(p -> p.getColor() == opponent(color))
                .collect(Collectors.toList());

        for (ChessPiece p : opponents) {
            if (p.possibleMove(king.getChessPosition().toPosition())) {
                check = true;
                return true;
            }
        }

        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color))
            return false;

        List<ChessPiece> list = piecesOnTheBoard.stream().filter(p -> p.getColor() == color)
                .collect(Collectors.toList());

        for (ChessPiece p : list) {
            boolean[][] mat = p.possibleMoves();

            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length; j++) {
                    if (mat[i][j]) {
                        Position source = p.getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        ChessPiece capturedPiece = makeMove(source, target);
                        boolean test = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!test)
                            return false;
                    }
                }
            }
        }

        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        for (char i = 'a'; i <= 'h'; i++) {
            placeNewPiece(i, 7, new Pawn(board, Color.BLACK));
        }
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));

        for (char i = 'a'; i <= 'h'; i++) {
            placeNewPiece(i, 2, new Pawn(board, Color.WHITE));
        }
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
    }
}