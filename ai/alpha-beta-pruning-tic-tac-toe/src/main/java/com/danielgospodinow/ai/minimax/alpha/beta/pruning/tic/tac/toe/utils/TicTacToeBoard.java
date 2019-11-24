package com.danielgospodinow.ai.minimax.alpha.beta.pruning.tic.tac.toe.utils;

import java.util.ArrayList;
import java.util.List;

import static com.danielgospodinow.ai.minimax.alpha.beta.pruning.tic.tac.toe.utils.GameMarker.*;
import static com.danielgospodinow.ai.minimax.alpha.beta.pruning.tic.tac.toe.utils.GameState.*;

public class TicTacToeBoard {

    private static final int BOARD_SIZE = 3;

    private GameMarker[][] board;

    public TicTacToeBoard() {
        this.board = new GameMarker[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; ++i) {
            this.board[i] = new GameMarker[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; ++j) {
                this.board[i][j] = NONE;
            }
        }
    }

    public TicTacToeBoard(TicTacToeBoard board) {
        this.board = new GameMarker[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; ++i) {
            this.board[i] = new GameMarker[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; ++j) {
                this.board[i][j] = board.board[i][j];
            }
        }
    }

    public void setMarker(GameMarker marker, Position position) {
        board[position.getRow()][position.getCol()] = marker;
    }

    public GameState getState() {
        // Check for a win in the rows for each col
        for (int i = 0; i < BOARD_SIZE; ++i) {
            boolean isWinningState = true;
            GameMarker winningMarker = board[i][0];
            for (int j = 0; j < BOARD_SIZE - 1; ++j) {
                if (board[i][j] == NONE || board[i][j] != board[i][j + 1]) {
                    isWinningState = false;
                    break;
                }
            }

            if (isWinningState) {
                if (winningMarker == X) {
                    return X_WIN;
                } else if (winningMarker == O) {
                    return O_WIN;
                }
            }
        }

        // Check for a win in the cols for each row
        for (int i = 0; i < BOARD_SIZE; ++i) {
            boolean isWinningState = true;
            GameMarker winningMarker = board[0][i];
            for (int j = 0; j < BOARD_SIZE - 1; ++j) {
                if(board[j][i] != board[j + 1][i]) {
                    isWinningState = false;
                    break;
                }
            }

            if (isWinningState) {
                if (winningMarker == X) {
                    return X_WIN;
                } else if (winningMarker == O) {
                    return O_WIN;
                }
            }
        }

        // Check for main diagonal
        boolean isWinningStateMainDiag = true;
        GameMarker winningMarkerMainDiag = board[0][0];
        for (int i = 0; i < BOARD_SIZE - 1; ++i) {
            if (board[i][i] != board[i + 1][i + 1]) {
                isWinningStateMainDiag = false;
                break;
            }
        }
        if (isWinningStateMainDiag) {
            if (winningMarkerMainDiag == X) {
                return X_WIN;
            } else if (winningMarkerMainDiag == O) {
                return O_WIN;
            }
        }

        // Check for reverse diagonal
        boolean isWinningStateReverseDiag = true;
        GameMarker winningMarkerReverseDiag = board[0][BOARD_SIZE - 1];
        for (int i = 0; i < BOARD_SIZE - 1; ++i) {
            if (board[i][(BOARD_SIZE - 1) - i] != board[i + 1][(BOARD_SIZE - 1) - (i + 1)]) {
                isWinningStateReverseDiag = false;
                break;
            }
        }
        if (isWinningStateReverseDiag) {
            if (winningMarkerReverseDiag == X) {
                return X_WIN;
            } else if (winningMarkerReverseDiag == O) {
                return O_WIN;
            }
        }

        // Check for an even
        boolean isEven = true;
        for (int i = 0; i < BOARD_SIZE; ++i) {
            if (!isEven) {
                break;
            }

            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] == NONE) {
                    isEven = false;
                    break;
                }
            }
        }
        if (isEven) {
            return EVEN;
        }

        return UNFINISHED;
    }

    public void print() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public List<Position> getFreeSpaces() {
        List<Position> freeSpaces = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] == NONE) {
                    freeSpaces.add(new Position(i, j));
                }
            }
        }

        return freeSpaces;
    }
}
