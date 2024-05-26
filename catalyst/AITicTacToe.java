import java.util.Scanner;

public class TicTacToe {
    static char[][] board = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}  
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            playerMove(scanner);
            if (isGameOver()) {
                break;
            }
            aiMove();
            if (isGameOver()) {
                break;
            } 
        }
        scanner.close();
    }

    static void playerMove(Scanner scanner) {
        while (true) {
            System.out.println("Enter your move (row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if (row >= 0 && col >= 0 && row < 3 && col < 3 && board[row][col] == ' ') {
                board[row][col] = 'X';
                break;
            } else {
                System.out.println("This move is not valid");
            }
        }
    }

    static void aiMove() {
        int[] bestMove = findBestMove();
        board[bestMove[0]][bestMove[1]] = 'O';
    }

    static int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(board, 0, false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }

        return move;
    }

    static int minimax(char[][] board, int depth, boolean isMaximizing) {
        char result = checkWinner();
        if (result != ' ') {
            return score(result);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    static int score(char result) {
        if (result == 'O') {
            return 1;
        } else if (result == 'X') {
            return -1;
        } else {
            return 0;
        }
    }

    static char checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                return board[i][0];
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
                return board[0][i];
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            return board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
            return board[0][2];
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return ' ';
                }
            }
        }
        return 'T'; // Tie
    }

    static boolean isGameOver() {
        char result = checkWinner();
        if (result == 'X') {
            printBoard();
            System.out.println("You win!");
            return true;
        } else if (result == 'O') {
            printBoard();
            System.out.println("AI wins!");
            return true;
        } else if (result == 'T') {
            printBoard();
            System.out.println("It's a tie!");
            return true;
        }
        return false;
    }

    static void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("----------");
            }
        }
    }
}
