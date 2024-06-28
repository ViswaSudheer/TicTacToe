package p1;
import java.util.*;
import java.io.*;
public class Main {
    public static int COMPUTER=1;
    public static int HUMAN=2;
    public static int SIDE=3;
    public static char CMove='O';
    public static char HMove='X';
    public static Scanner sc=new Scanner(System.in);
    public static void initialise(char board[][]) {
        for(int i=0;i<SIDE;i++) {
            for(int j=0;j<SIDE;j++) {
                board[i][j]='*';
            }
        }
    }
    public static void showInstructions() {
        // TODO Auto-generated method stub
        System.out.println("Choose a cell numbered from 1 to 9 and play");
        System.out.println();
        System.out.println("\t\t\t 1 | 2 | 3 ");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 4 | 5 | 6 ");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 7 | 8 | 9 ");


    }
    public static void showboard(char[][] board) {
        System.out.printf("\t\t\t  %c | %c | %c \n",board[0][0],board[0][1],board[0][2]);
        System.out.println("\t\t\t-------------");
        System.out.printf("\t\t\t  %c | %c | %c \n",board[1][0],board[1][1],board[1][2]);
        System.out.println("\t\t\t-------------");
        System.out.printf("\t\t\t  %c | %c | %c \n",board[2][0],board[2][1],board[2][2]);

    }

    public static boolean rowCrossed(char[][] board) {
        for(int i=0;i<SIDE;i++) {
            if(board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0]!='*') return true;
        }
        return false;
    }
    public static boolean colCrossed(char[][] board) {
        for(int i=0;i<SIDE;i++) {
            if(board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i]!='*') return true;
        }
        return false;
    }
    public static boolean diagCrossed(char[][] board) {
        if(board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[1][1]!='*') return true;
        if(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[1][1]!='*') return true;
        return false;
    }

    private static boolean gameOver(char[][] board) {
        return (rowCrossed(board) || colCrossed(board) || diagCrossed(board));
    }
    public static int bestMove(char[][] board, int moveIndex) {
        int x=-1;
        int y=-1;
        int score=0, bestScore=-999;
        for(int i=0;i<SIDE;i++) {
            for(int j=0;j<SIDE;j++) {
                if(board[i][j]=='*') {
                    board[i][j]=CMove;
                    score =minimax(board,moveIndex+1,false);
                    board[i][j]='*';
                    if(score>bestScore) {
                        bestScore=score;
                        x=i;
                        y=j;
                    }
                }
            }
        }
        return x*3+y;
    }
    public static int minimax(char[][] board, int depth, boolean isAI) {
        int score =0;
        int bestScore=0;
        if(gameOver(board)) {
            if(isAI) return -10;
            else return 10;
        }
        else {
            if(depth<9) {
                if(isAI) {
                    bestScore=-999;
                    for(int i=0;i<SIDE;i++) {
                        for(int j=0;j<SIDE;j++) {
                            if(board[i][j]=='*') {
                                board[i][j]=CMove;
                                score=minimax(board,depth+1,false);
                                board[i][j]='*';
                                if(score>bestScore) bestScore=score;
                            }
                        }
                    }
                    return bestScore;
                }
                else {
                    bestScore=999;
                    for(int i=0;i<SIDE;i++) {
                        for(int j=0;j<SIDE;j++) {
                            if(board[i][j]=='*') {
                                board[i][j]=HMove;
                                score=minimax(board,depth+1,true);
                                board[i][j]='*';
                                if(score<bestScore) bestScore=score;
                            }
                        }
                    }
                    return bestScore;
                }
            }
        }
        return 0;
    }
    public static void playTicTacToe(int whoseTurn) throws IOException {
        // TODO Auto-generated method stub
        char board[][]=new char[SIDE][SIDE];
        int moveIndex=0 ,x=0 ,y=0;
        initialise(board);
        showInstructions();
        while(gameOver(board)==false && moveIndex!=SIDE*SIDE) {
            int n;
            if(whoseTurn==COMPUTER) {
                n=bestMove(board,moveIndex);
                x= n/SIDE;
                y= n%SIDE;
                board[x][y]=CMove;
                System.out.printf("COMPUTER has put %c in cell %d\n",CMove,n+1);
                showboard(board);
                moveIndex++;
                whoseTurn= HUMAN;
            }
            else if(whoseTurn==HUMAN) {
                System.out.println("You can insert in the following positions :  ");
                for(int i=0;i<SIDE;i++) {
                    for(int j=0;j<SIDE;j++) {
                        if(board[i][j]=='*') {
                            //System.out.println();

                            System.out.print((i*3+j+1)+" " );

                        }
                    }
                }
                System.out.println();
                System.out.println("Enter the position: ");
                n=sc.nextInt();
                if(n<0|| n>9) {

                    continue;

                }
                n--;
                x=n/SIDE;
                y=n%SIDE;
                if(board[x][y]=='*' && n<9 && n>=0) {
                    board[x][y]=HMove;
                    System.out.printf("HUMAN has put %c int cell %d\n",HMove,n+1);
                    showboard(board);
                    moveIndex++;
                    whoseTurn= COMPUTER;
                }
                else if(board[x][y]!='*' && n<9 && n>=0) {
                    System.out.println("already ocupied , please select another position");

                }
                else System.out.println("Invalid position");
            }

        }
        if(gameOver(board)==false && moveIndex==SIDE*SIDE) System.out.println("IT'S A DRAW");
        else {
            if(whoseTurn==COMPUTER) whoseTurn=HUMAN;
            else whoseTurn=COMPUTER;
            declare(whoseTurn);
        }

    }



    public static void declare(int whoseTurn) {
        if(whoseTurn==COMPUTER) System.out.println("COMPUTER HAS WON");
        else System.out.println("HUMAN HAS WON");

    }
    public static void main(String[] args) throws IOException {
        char cont = 'y';
        while (cont == 'y') {
            System.out.println("---------------------------------------------------");
            System.out.println("               Welcome to Tic Tac Toe              ");
            System.out.println("---------------------------------------------------");

            char c = ' ';
            while (c != 'y' && c != 'n') {
                System.out.println("Do you want to play first? (y/n)");
                String input = sc.nextLine();
                if (input.length() > 0) {
                    c = input.charAt(0);
                }
                if (c != 'y' && c != 'n') {
                    System.out.println("You have entered wrong input. Please enter 'y' or 'n'.");
                }
            }

            if (c == 'n') playTicTacToe(COMPUTER);
            else playTicTacToe(HUMAN);

            cont = ' ';
            while (cont != 'y' && cont != 'n') {
                System.out.println("Do you want to play again (y/n)");

                String input = sc.nextLine();
                if (input.length() > 0) {
                    cont = input.charAt(0);
                }
                if (cont != 'y' && cont != 'n') {
                    System.out.println("You have entered wrong input. Please enter 'y' or 'n'.");
                }
            }
        }
    }



}
