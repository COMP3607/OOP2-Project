package org.example.UI;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuestionBoard extends JPanel {
    private final int BOARD_COLUMNS = 5;
    private  final int BOARD_ROWS = 5;
    private final int CATEGORY_SIZE = 5;

    private List<JeopardyQuestion> questions;
    private List<List<JeopardyQuestion>> categorizedQuestions;
    private JPanel boardPanel;

    public QuestionBoard(List<JeopardyQuestion> questions) {
        this.questions = new ArrayList<JeopardyQuestion>(questions);
        this.categorizedQuestions = new ArrayList<List<JeopardyQuestion>>();

        setLayout(new BorderLayout());
        setBackground(new Color(6, 12, 233));

        initializeBoard();
    }

    private void initializeBoard() {
        removeAll();
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_ROWS + 1, BOARD_COLUMNS, 8, 8));
        boardPanel.setBackground(Color.BLUE);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        categorizedQuestions = buildCategoryColumns();
        addCategoryHeaders();
        addQuestionCells();

        add(boardPanel, BorderLayout.CENTER);
    }

    private List<List<JeopardyQuestion>> buildCategoryColumns() {
        List<List<JeopardyQuestion>> groupedQuestions = new ArrayList<List<JeopardyQuestion>>();
        if (questions == null || questions.isEmpty()) {
            return groupedQuestions;
        }

        int totalQuestions = questions.size();
        for (int index = 0; index < totalQuestions; index += CATEGORY_SIZE) {
            List<JeopardyQuestion> categoryQuestions = new ArrayList<JeopardyQuestion>();
            for (int offset = index; offset < index + CATEGORY_SIZE && offset < totalQuestions; offset++) {
                categoryQuestions.add(questions.get(offset));
            }
            groupedQuestions.add(categoryQuestions);
        }
        return groupedQuestions;
    }

    private void addCategoryHeaders() {
        for (int column = 0; column < BOARD_COLUMNS; column++) {
            String categoryName = "Category";
            if (column < categorizedQuestions.size() && !categorizedQuestions.get(column).isEmpty()) {
                categoryName = categorizedQuestions.get(column).get(0).getCategory();
            }
            boardPanel.add(createCategoryLabel(categoryName));
        }
    }

    private void addQuestionCells() {
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int column = 0; column < BOARD_COLUMNS; column++) {
                JButton button = null;
                if (column < categorizedQuestions.size()) {
                    List<JeopardyQuestion> categoryQuestions = categorizedQuestions.get(column);
                    if (row < categoryQuestions.size()) {
                        button = createQuestionButton(categoryQuestions.get(row));
                    }
                }
                    boardPanel.add(button);
            }
        }
    }

    private JButton createPlaceholderCell() {
        JButton placeholder = new JButton("");
        placeholder.setEnabled(false);
        placeholder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        placeholder.setBackground(new Color(3, 6, 116));
        return placeholder;
    }

    private JLabel createCategoryLabel(String category) {
        JLabel label = new JLabel(category.toUpperCase(), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(6, 12, 233));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return label;
    }

    private JButton createQuestionButton(JeopardyQuestion question) {
        JButton button = new JButton("$" + question.getValue());
        button.setFont(new Font("Arial", Font.BOLD, 36));
        button.setForeground(new Color(255, 204, 0));
        button.setBackground(new Color(6, 12, 233));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!question.isAnswered()) {
                    showQuestionDialog(question);
                    question.markAnswered();
                    button.setEnabled(false);
                    button.setText("");
                    button.setBackground(new Color(3, 6, 116));
                }
            }
        });
        return button;
    }

    private void showQuestionDialog(JeopardyQuestion question) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Question - " + question.getCategory() + " $" + question.getValue(), true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel questionPanel = new JPanel();
        questionPanel.setBackground(new Color(6, 12, 233));
        questionPanel.setLayout(new BorderLayout());

        JLabel questionLabel = new JLabel(question.getPrompt());
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(questionLabel, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(question.getOptions().size(), 1, 5, 5));
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        ButtonGroup buttonGroup = new ButtonGroup();
        for (Option option : question.getOptions()) {
            JRadioButton radioButton = new JRadioButton(option.getLabel() + ". " + option.getText());
            radioButton.setFont(new Font("Arial", Font.PLAIN, 16));
            radioButton.setBackground(Color.WHITE);
            buttonGroup.add(radioButton);
            optionsPanel.add(radioButton);
        }

        JButton submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(Color.YELLOW);
        submitButton.setForeground(Color.BLACK);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(dialog,
                        "Correct answer: " + question.getAnswer(),
                        "Answer",
                        JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        dialog.add(questionPanel, BorderLayout.NORTH);
        dialog.add(new JScrollPane(optionsPanel), BorderLayout.CENTER);
        dialog.add(submitButton, BorderLayout.SOUTH);
        dialog.setVisible(true);

    }

    public void refreshBoard() {
        initializeBoard();
        revalidate();
        repaint();
    }
}