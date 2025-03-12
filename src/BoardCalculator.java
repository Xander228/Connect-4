import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardCalculator {

    private int[][] board;
    private int searchDepth;
    private boolean isRed;

    public BoardCalculator(int[][] board, int searchDepth, boolean isRed) {
        this.board = board;
        this.searchDepth = searchDepth;
        this.isRed = isRed;

    }

    public int calculateBestMove() {
        int bestMove = 0;
        int bestScore = Integer.MIN_VALUE;

        for(int i = 0; i < Constants.BOARD_COLS; i++) {
            int[][] newBoard = copyBoard(board);
            int row = getCellsFilled(board, i);
            if(row == -1) continue;
            newBoard[i][row] = isRed ? 1 : -1;
            int score = findPossibleMoves(newBoard, 1, searchDepth, false);
            if(score > bestScore) {
                bestScore = score;
                bestMove = i;
            }
            System.out.println("Column: " + i + " Score: " + score);
        }

        return bestMove;
    }

    public void printBoard(int[][] board){
        System.out.println();
        for(int i = 0; i < Constants.BOARD_ROWS; i++){
            for(int j = 0; j < Constants.BOARD_COLS; j++){
                System.out.print((board[j][i] != -1 ? " " : "") + board[j][i] + " ");
            }
            System.out.println();
        }
    }

    public int findPossibleMoves(int[][] board, int iteration, int lastIteration, boolean isMyTurn) {
        if (isWinner(board) && isMyTurn) {
            printBoard(board);
            return Constants.BOARD_COLS * Constants.BOARD_ROWS - iteration;
        }
        else if(isWinner(board) && !isMyTurn) {
            return -(Constants.BOARD_COLS * Constants.BOARD_ROWS - iteration);
        } else if (iteration >= lastIteration) {
            return 0;
        }


        int distanceToWin = isMyTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < Constants.BOARD_COLS; i++) {
            if (board[i][0] == 0) {
                int[][] newBoard = copyBoard(board);
                newBoard[i][getCellsFilled(board, i)] = (!isRed ^ isMyTurn) ? 1 : -1;

                int score = findPossibleMoves(newBoard, iteration + 1, lastIteration ,!isMyTurn);

                if(isMyTurn) distanceToWin = Math.max(distanceToWin, score);
                else distanceToWin = Math.min(distanceToWin, score);
                //if(iteration <= 0 && score != 0) System.out.println("Column: " + i + " Score: " + score);
            }
        }
        return distanceToWin;

    }



    public static int getCellsFilled(int[][] board, int col) {
        for(int indexY = 0; indexY < Constants.BOARD_ROWS; indexY++) {
            if(board[col][indexY] != 0) {
                return indexY - 1;
            }
        }
        return Constants.BOARD_ROWS - 1;
    }

    public static boolean isWinner(int[][] board) {
        for (int col = 0; col < Constants.BOARD_COLS; col++){
            for (int row = 0; row < Constants.BOARD_ROWS; row++){
                if (checkVertical(board, row, col)) return true;
                if (checkHorizontal(board, row, col)) return true;
                if (checkDiagonalUp(board, row, col)) return true;
                if (checkDiagonalDown(board, row, col)) return true;
            }
        }
        return false;
    }


    public static boolean isTie(int[][] board) {
        for (int row = 0; row < Constants.BOARD_ROWS; row++) {
            for (int col = 0; col < Constants.BOARD_COLS; col++) {
                if (board[col][row] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkVertical(int[][] board, int row, int col) {
        int total = 0;
        if(row + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_ROWS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col][row + i];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) return true;
        return false;
    }

    private static boolean checkHorizontal(int[][] board, int row, int col) {
        int total = 0;
        if(col + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_COLS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col + i][row];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) return true;
        return false;
    }

    private static boolean checkDiagonalUp(int[][] board, int row, int col) {
        int total = 0;
        if(row + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_ROWS ||
                col + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_COLS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col + i][row + i];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) return true;
        return false;
    }

    private static boolean checkDiagonalDown(int[][] board, int row, int col) {
        int total = 0;
        if(row + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_ROWS ||
                col + Constants.WINNING_LENGTH - 1 >= Constants.BOARD_COLS) return false;
        for(int i = 0; i < Constants.WINNING_LENGTH; i++) {
            total += board[col + i][row + ((Constants.WINNING_LENGTH - 1) - i)];
        }
        if (total == -Constants.WINNING_LENGTH || total == Constants.WINNING_LENGTH) return true;
        return false;
    }

    public static int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[Constants.BOARD_COLS][Constants.BOARD_ROWS];
        for (int i = 0; i < Constants.BOARD_COLS; i++) {
            for (int j = 0; j < Constants.BOARD_ROWS; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
}
