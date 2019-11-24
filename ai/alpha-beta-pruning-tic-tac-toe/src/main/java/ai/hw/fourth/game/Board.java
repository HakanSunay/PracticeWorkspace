package ai.hw.fourth.game;

import java.util.ArrayList;
import java.util.List;

import static ai.hw.fourth.game.BoxMarker.*;
import static ai.hw.fourth.game.State.*;

public class Board {

    private static final int BOARD_SIZE = 3;

    // tribute to Rey Mysterio
    private static final int NOT_FINISHER = 619;

    private BoxMarker[][] board;

    public Board() {
        this.board = new BoxMarker[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; ++i) {
            this.board[i] = new BoxMarker[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; ++j) {
                this.board[i][j] = EMPTY;
            }
        }
    }

    public void setMarker(BoxMarker marker, Position position) {
        board[position.getRow()][position.getCol()] = marker;
    }

    public State getState() {
        BoxMarker possibleWinner = null;
        boolean isWon = false;

        // Check for win in rows
        for (int i = 0; i < BOARD_SIZE; ++i) {
            isWon = true;
            possibleWinner = board[i][0];
            for (int j = 0; j < BOARD_SIZE - 1; ++j) {
                if (board[i][j] == EMPTY || board[i][j] != board[i][j + 1]) {
                    isWon = false;
                    break;
                }
            }

            if (isWon) {
                if (possibleWinner == X) {
                    return X_WON;
                } else if (possibleWinner == O) {
                    return O_WON;
                }
            }
        }

        // Check for a win in cols
        for (int j = 0; j < BOARD_SIZE; ++j) {
            isWon = true;
            possibleWinner = board[0][j];
            for (int i = 0; i < BOARD_SIZE - 1; ++i) {
                if(board[i][j] == EMPTY || board[i][j] != board[i + 1][j]) {
                    isWon = false;
                    break;
                }
            }

            if (isWon) {
                if (possibleWinner == X) {
                    return X_WON;
                } else if (possibleWinner == O) {
                    return O_WON;
                }
            }
        }

        // Check for win in main diagonal
        isWon = true;
        possibleWinner = board[0][0];
        for (int i = 0; i < BOARD_SIZE - 1; ++i) {
            if (board[i][i] == EMPTY || board[i][i] != board[i + 1][i + 1]) {
                isWon = false;
                break;
            }
        }
        if (isWon) {
            if (possibleWinner == X) {
                return X_WON;
            } else if (possibleWinner == O) {
                return O_WON;
            }
        }

        // Check for reverse diagonal
        isWon = true;
        possibleWinner = board[0][BOARD_SIZE - 1];
        for (int i = 0; i < BOARD_SIZE - 1; ++i) {
            if (board[i][(BOARD_SIZE - 1) - i] == EMPTY
                    || board[i][(BOARD_SIZE - 1) - i] != board[i + 1][(BOARD_SIZE - 1) - (i + 1)]) {
                isWon = false;
                break;
            }
        }
        if (isWon) {
            if (possibleWinner == X) {
                return X_WON;
            } else if (possibleWinner == O) {
                return O_WON;
            }
        }

        // Check for draw
        boolean isDraw = true;
        for (int i = 0; i < BOARD_SIZE; ++i) {
            if (!isDraw) {
                break;
            }

            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] == EMPTY) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw) {
            return DRAW;
        }

        return IN_PROGRESS;
    }

    public void print() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public List<Position> getEmptyBoxes() {
        List<Position> freeSpaces = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] == EMPTY) {
                    freeSpaces.add(new Position(i, j));
                }
            }
        }

        return freeSpaces;
    }

    public Position oneMoveFinisher(BoxMarker marker) {
        Position finisherPosition = new Position(NOT_FINISHER, NOT_FINISHER);

        for (Position freeSpace : this.getEmptyBoxes()) {
            setMarker(marker, freeSpace);

            if ((marker == X && getState() == X_WON) || (marker == O && getState() == O_WON)) {
                finisherPosition = freeSpace;
                setMarker(EMPTY, freeSpace);

                break;
            }

            setMarker(EMPTY, freeSpace);
        }

        return finisherPosition;
    }

    public static boolean isOneMoveFromWin(Position finisherPosition) {
        return finisherPosition.getRow() != NOT_FINISHER && finisherPosition.getCol() != NOT_FINISHER;
    }
}
