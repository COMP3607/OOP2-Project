
package org.example.UI;

import org.example.Game.JeopardyGame;
import org.example.Question.JeopardyQuestion;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

public class GameWindow extends JFrame {
    private JeopardyGame game;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JLabel titleLabel;
    private JLabel scoreLabel;
    private QuestionBoard questionBoard;

    public GameWindow() {
        initializeWindow();
        createComponents();
        setVisible(true);
    }

    public GameWindow(JeopardyGame game) {
        this.game = game;
        initializeWindow();
        createComponents();
        setVisible(true);
    }

    private void initializeWindow() {
        setTitle("Jeopardy Game");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(6, 12, 233));
    }

    private void createComponents() {
        footerPanel = new JPanel();
        footerPanel.setBackground(Color.BLACK);
        footerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        scoreLabel = new JLabel("Score: $0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 12));
        scoreLabel.setForeground(Color.yellow);
        footerPanel.add(scoreLabel);


        if (game != null && game.getQuestions() != null) {
            loadGameBoard(game.getQuestions());
        }
    }

    public void loadGameBoard(List<JeopardyQuestion> questionList) {
        if (questionList == null || questionList.isEmpty()) {
            return;
        }
        if (questionBoard != null) {
            remove(questionBoard);
        }
        questionBoard = new QuestionBoard(questionList);
        add(questionBoard, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: $" + score);
    }

    public JeopardyGame getGame() {
        return game;
    }
}