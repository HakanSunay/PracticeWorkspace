package ai.hw.fourth.game;

import java.util.List;
import java.util.TreeMap;

public class TicTacToe {

    public static Position getNextMoveFromAI(Board board, BoxMarker aiMarker) {
        TreeMap<Integer, Position> possibleMoves = new TreeMap<>();

        for (Position emptyBox: board.getEmptyBoxes()) {
            // Make move
            board.setMarker(aiMarker, emptyBox);
            // Calculate scores for opponent's next move
            int score = minimax(board, aiMarker.getOppositeMove(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

            // Undo move
            board.setMarker(BoxMarker.EMPTY, emptyBox);

            possibleMoves.put(score, emptyBox);
        }

        if (aiMarker == BoxMarker.X) {
            // Needs highest value
            return possibleMoves.lastEntry().getValue();
        }

        // Marker is O => needs lowest value
        return possibleMoves.firstEntry().getValue();
    }

    private static int minimax(Board board, BoxMarker marker, int depth, int alpha, int beta) {
        // recursion base
        if (board.getState() != State.IN_PROGRESS) {
            State gameState = board.getState();
            switch (gameState) {
                case X_WON:
                    return 10 - depth;
                case O_WON:
                    return -10 + depth;
                default:
                    return 0;
            }
        } else {
            if(marker == BoxMarker.X) {
                // Maximizing
                int bestScore = Integer.MIN_VALUE;

                List<Position> freeSpaces = board.getEmptyBoxes();
                for (Position freeSpace : freeSpaces) {
                    // Make move
                    board.setMarker(BoxMarker.X, freeSpace);
                    // Calculate for opposite move
                    int score = minimax(board, BoxMarker.O, depth + 1, alpha, beta);

                    // Undo move
                    board.setMarker(BoxMarker.EMPTY, freeSpace);

                    bestScore = Math.max(bestScore, score);
                    alpha = Math.max(alpha, bestScore);

                    // prune
                    if(beta <= alpha) {
                        break;
                    }
                }

                return bestScore;

            } else if(marker == BoxMarker.O) {
                // Minimizing
                int bestScore = Integer.MAX_VALUE;

                List<Position> freeSpaces = board.getEmptyBoxes();
                for (Position freeSpace : freeSpaces) {
                    // Make move
                    board.setMarker(BoxMarker.O, freeSpace);
                    // Calculate for opposite move
                    int score = minimax(board, BoxMarker.X, depth + 1, alpha, beta);

                    // Undo move
                    board.setMarker(BoxMarker.EMPTY, freeSpace);

                    bestScore = Math.min(bestScore, score);
                    beta = Math.min(beta, bestScore);

                    // prune
                    if(beta <= alpha) {
                        break;
                    }
                }

                return bestScore;

            } else {
                throw new RuntimeException("Unexpected exception!");
            }
        }
    }
}
