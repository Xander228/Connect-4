import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardCalculator {
    private static ArrayList<Integer> pathCounts = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> goodPaths = new ArrayList<>();//Container of lists of columns to play
    private static ArrayList<ArrayList<Integer>> neutralPaths = new ArrayList<>();//Container of lists of columns to play
    private static ArrayList<Integer> path = new ArrayList<>();

    public static void main(String[] args) {
        int[][] board = new int[Constants.BOARD_COLS][Constants.BOARD_ROWS];

        findPossibleMoves(board, 0, true);

        System.out.println("Good Paths: " + goodPaths.size());
        System.out.println("Neutral Paths: " + neutralPaths.size());
    }

    public static int findPossibleMoves(int[][] board, int iteration, boolean isMyTurn) {
        if (pathCounts.size() == iteration) {
            pathCounts.add(0);
            path.add(-1);
        }


        if (isWinner(board) && isMyTurn) {
            ArrayList<Integer> goodPath = new ArrayList<>(path);
            goodPaths.add(goodPath);
            return Constants.BOARD_COLS * Constants.BOARD_ROWS - iteration;
        }
        else if(isWinner(board) && !isMyTurn){
            return -(Constants.BOARD_COLS * Constants.BOARD_ROWS - iteration);
        } else if (iteration > 8){
            ArrayList<Integer> neutralPath = new ArrayList<>(path);
            neutralPaths.add(neutralPath);
            return 0;
        }


        int distanceToWin = 0;
        for (int i = 0; i < Constants.BOARD_COLS; i++) {
            if (board[i][0] == 0) {
                pathCounts.set(iteration, pathCounts.get(iteration) + 1);
                path.set(iteration, i);
                int[][] newBoard = copyBoard(board);
                newBoard[i][getCellsFilled(board, i)] = isMyTurn ? 1 : -1;
                //minimax the next move
                int score = findPossibleMoves(newBoard, iteration + 1, !isMyTurn);

                distanceToWin = Math.max(distanceToWin, score);
                if(iteration <= 0 && score != 0) System.out.println("Column: " + i + " Score: " + score);
            }
        }
        if(iteration == 0) printPathCounts();
        return distanceToWin;

    }

    public static void printPathCounts() {
        StringBuilder pathCount = new StringBuilder();
        for (int i = 0; i < pathCounts.size(); i++) {
            pathCount.append(pathCounts.get(i)).append(" ");
        }
        System.out.println(pathCount);
    }
    public static void printBoard(int[][] board) {
        for (int i = 0; i < Constants.BOARD_ROWS; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < Constants.BOARD_COLS; j++) {
                row.append(board[j][i]).append(" ");
            }
            System.out.println(row);
        }
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
