package boardgame;

public class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        // The position of a piece is null at first
    }

    // Board is readonly
    protected Board getBoard() {
        return board;
    }
}