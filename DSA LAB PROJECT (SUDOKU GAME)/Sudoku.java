

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Sudoku {

    private JFrame frame;
    private boolean isStarted = false;
    private int[][] prevBoard = new int[9][9];
    private int[][] currentBoard = new int[9][9];
    private final JTextField[][] grid = new JTextField[9][9];
    private final Timer timer;
    private int timeCount;
    private final int difficulty;
    private final CustomStack boardHistory;

    public Sudoku(int difficulty) {
        this.difficulty = difficulty;
        this.boardHistory = new CustomStack(100);
        timer = new Timer(1000, e -> {
            timeCount--;
            timerLabel.setText("Time Left: " + countToTime(timeCount));
            if (timeCount == 0) gameOver();
        });
        initialize();
    }

    public void showGame() {
        frame.setVisible(true);
    }

    private final JLabel timerLabel = new JLabel("Time Left:");
    private final JButton startButton = new JButton("Start");
    private final JButton submitButton = new JButton("Submit");
    private final JButton hintButton = new JButton("Hint");
    private final JButton checkMoveButton = new JButton("Check Move");
    private final JButton solutionButton = new JButton("Solution");
    private final JButton undoButton = new JButton("Undo");

    private String countToTime(int count) {
        String min = Integer.toString(count / 60);
        String sec = Integer.toString(count % 60);
        if (Integer.parseInt(min) < 10) min = "0" + min;
        if (Integer.parseInt(sec) < 10) sec = "0" + sec;
        return min + ":" + sec;
    }

    private void gameOver() {
        timer.stop();
        prevBoard = SudokuSolver.solve(prevBoard);
        boolean isFine = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].getText().equals("") ||
                        Integer.parseInt(grid[i][j].getText()) != prevBoard[i][j]) {
                    isFine = false;
                    break;
                }
            }
        }
        JOptionPane.showMessageDialog(null, isFine ? "You Won!" : "You Lose!");
        resetGame();
    }

    private void resetGame() {
        isStarted = false;
        startButton.setText("Start");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].setText("");
                grid[i][j].setEditable(false);
            }
        }
        boardHistory.clear();
    }

    private void initialize() {
        frame = new JFrame("Sudoku Game");
        frame.setBounds(100, 100, 668, 438);
        frame.getContentPane().setBackground(new Color(0, 98, 132));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        timerLabel.setFont(new Font("Calibri Light", Font.BOLD, 16));
        timerLabel.setBounds(435, 13, 176, 16);
        frame.getContentPane().add(timerLabel);

        startButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        startButton.setBounds(435, 200, 155, 37);
        frame.getContentPane().add(startButton);

//        submitButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
//        submitButton.setBounds(435, 270, 155, 37);
//        frame.getContentPane().add(submitButton);
//        submitButton.setVisible(false);

        hintButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        hintButton.setBounds(435, 50, 155, 37);
        frame.getContentPane().add(hintButton);

        checkMoveButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        checkMoveButton.setBounds(435, 100, 155, 37);
        frame.getContentPane().add(checkMoveButton);

        solutionButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        solutionButton.setBounds(435, 150, 155, 37);
        frame.getContentPane().add(solutionButton);

        undoButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
        undoButton.setBounds(435, 250, 155, 37);
        frame.getContentPane().add(undoButton);

        initializeGrid();
        setupStartButton();
        setupAdditionalButtons();
        setupUndoButton();
    }

    private void initializeGrid() {
        int h = 12, w = 13, hi = 39, wi = 37;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) w += 13;
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) h += 11;
                grid[i][j] = new JTextField();
                grid[i][j].setColumns(10);
                grid[i][j].setBounds(h, w, 38, 37);
                grid[i][j].setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 22));
                grid[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                grid[i][j].setEditable(false);
                frame.getContentPane().add(grid[i][j]);
                h += hi;
            }
            h = 12;
            w += wi;
        }
    }

    private void setupStartButton() {
        startButton.addActionListener(e -> {
            if (isStarted) {
                gameOver();
            } else {
                startNewGame();
            }
        });
    }

    private void setupAdditionalButtons() {
        hintButton.addActionListener(e -> provideHint());
        checkMoveButton.addActionListener(e -> checkMove());
        solutionButton.addActionListener(e -> showSolution());
    }

    private void setupUndoButton() {
        undoButton.addActionListener(e -> undoLastMove());
    }

    private void startNewGame() {
        isStarted = true;
        int[][] board = SudokuGenerator.generate(difficulty);

        timeCount = difficulty == 0 ? 600 : difficulty == 1 ? 360 : 180;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                prevBoard[i][j] = board[i][j];
                currentBoard[i][j] = board[i][j];
                if (board[i][j] != 0) {
                    grid[i][j].setText(Integer.toString(board[i][j]));
                } else {
                    grid[i][j].setText("");
                    grid[i][j].setEditable(true);
                }
            }
        }
        timerLabel.setVisible(true);
        submitButton.setVisible(true);
        startButton.setText("Give up!");
        timer.start();

        saveBoardState();
    }

    private void saveBoardState() {
        int[][] boardCopy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(currentBoard[i], 0, boardCopy[i], 0, 9);
        }
        boardHistory.push(boardCopy);
    }

    private void undoLastMove() {
        int[][] previousBoard = boardHistory.pop();

        if (previousBoard == null) {
            JOptionPane.showMessageDialog(frame, "No moves to undo!");
            return;
        }


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                currentBoard[i][j] = previousBoard[i][j];
                grid[i][j].setText(currentBoard[i][j] == 0 ? "" : Integer.toString(currentBoard[i][j]));
                grid[i][j].setEditable(currentBoard[i][j] == 0);
            }
        }
    }

    private void provideHint() {

        int[][] solvedBoard = SudokuSolver.solve(prevBoard);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (grid[i][j].getText().isEmpty()) {

                    grid[i][j].setText(String.valueOf(solvedBoard[i][j]));
                    grid[i][j].setEditable(false);
                    return;
                }
            }
        }


        JOptionPane.showMessageDialog(frame, "No hints available!");
    }


    private void checkMove() {
        boolean isCorrect = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid[i][j].getText().isEmpty() &&
                        Integer.parseInt(grid[i][j].getText()) != currentBoard[i][j]) {
                    isCorrect = false;
                    break;
                }
            }
        }
        JOptionPane.showMessageDialog(frame, isCorrect ? "All moves are correct so far!" : "There are incorrect moves.");
    }

    private void showSolution() {
        int[][] solvedBoard = SudokuSolver.solve(currentBoard);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j].setText(String.valueOf(solvedBoard[i][j]));
                grid[i][j].setEditable(false);
            }
        }
        timer.stop();
        JOptionPane.showMessageDialog(frame, "Here is the solution!");
    }
}