import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuMenu {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Sudoku Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);


        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\user1\\Desktop\\DSA LAB\\Suduko\\src\\Images\\images (1).jpeg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        backgroundLabel.setLayout(new BorderLayout());


        Image scaledImage = backgroundImage.getImage().getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        backgroundImage.setImage(scaledImage);


        JLabel heading = new JLabel("Sudoku");
        heading.setFont(new Font("Serif", Font.BOLD, 50));
        heading.setForeground(Color.WHITE);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBounds(0, 20, frame.getWidth(), 60);


        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton difficultButton = new JButton("Difficult");

        Font buttonFont = new Font("SansSerif", Font.BOLD, 20);
        easyButton.setFont(buttonFont);
        mediumButton.setFont(buttonFont);
        difficultButton.setFont(buttonFont);

        easyButton.setBounds(300, 200, 200, 50);
        mediumButton.setBounds(300, 300, 200, 50);
        difficultButton.setBounds(300, 400, 200, 50);


        easyButton.addActionListener(e -> launchSudokuGame(0));
        mediumButton.addActionListener(e -> launchSudokuGame(1));
        difficultButton.addActionListener(e -> launchSudokuGame(2));


        frame.add(heading);
        frame.add(easyButton);
        frame.add(mediumButton);
        frame.add(difficultButton);
        frame.add(backgroundLabel);


        frame.setVisible(true);
    }

    private static void launchSudokuGame(int difficulty) {
        SwingUtilities.invokeLater(() -> {
            try {
                Sudoku game = new Sudoku(difficulty);
                game.showGame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
