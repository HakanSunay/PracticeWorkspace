package ai.hw.fourth;

import ai.hw.fourth.game.*;

import java.util.Scanner;

public class ConsoleGame {

    private Board board = new Board();
    private Scanner scanner = new Scanner(System.in);

    public void start(boolean humanTurn) {
        BoxMarker startingMarker = BoxMarker.X;

        while (board.getState() == State.IN_PROGRESS) {
            getNextMove(humanTurn, startingMarker);

            board.print();
            System.out.println();

            humanTurn = !humanTurn;
            // take turns
            startingMarker = startingMarker.getOppositeMove();
        }

        printResults();
    }

    private void getNextMove(boolean humanTurn, BoxMarker currentMarker) {
        if (humanTurn) {
            System.out.println("Enter desired move");
            String[] position = scanner.nextLine().split(",");
            Position inputPosition = new Position(Integer.parseInt(position[0]) - 1,
                    Integer.parseInt(position[1]) - 1);

            board.setMarker(currentMarker, inputPosition);
        } else {
            Position aiPosition = TicTacToe.getNextMoveFromAI(board, currentMarker);

            board.setMarker(currentMarker, aiPosition);
            System.out.println("AI's move");
        }
    }

    private void printResults() {
        System.out.println("Result");
        board.print();

        switch (board.getState()) {
            case DRAW:
                System.out.println("Draw!");
                break;
            case X_WON:
                System.out.println("X won!");
                break;
            case O_WON:
                System.out.println("O won!");
                break;
            case IN_PROGRESS:
                System.out.println("Ongoing!");
        }
    }
}
