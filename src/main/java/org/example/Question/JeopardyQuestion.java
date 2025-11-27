package org.example.Question;

import java.util.List;

/**
 * Represents a single Jeopardy-style question, including the prompt,
 * answer, category, dollar value, and multiple-choice options.
 * 
 * <p>A question can be marked as answered and later reset if needed.</p>
 */
public class JeopardyQuestion {

    /** The correct answer text. */
    private String answer;

    /** The clue or question prompt displayed to the player. */
    private String prompt;

    /** The category this question belongs to. */
    private String category;

    /** Whether the question was already answered. */
    private boolean isAnswered;

    /** List of multiple-choice options associated with the question. */
    private List<Option> options;

    /** The dollar value of the question. */
    private int value;

    /**
     * Creates a new Jeopardy question.
     *
     * @param answer   the correct answer
     * @param prompt   the clue or prompt
     * @param category the category of the question
     * @param value    the dollar value of the question
     * @param options  the available multiple-choice options
     */
    public JeopardyQuestion(String answer, String prompt, String category,
                            int value, List<Option> options) {
        this.answer = answer;
        this.prompt = prompt;
        this.category = category;
        this.options = options;
        this.value = value;
        this.isAnswered = false;
    }

    /** @return the correct answer */
    public String getAnswer() { return answer; }

    /** @return the question prompt */
    public String getPrompt() { return prompt; }

    /** @return the category of the question */
    public String getCategory() { return category; }

    /** @return the list of answer options */
    public List<Option> getOptions() { return options; }

    /** @return true if the question has been answered */
    public boolean isAnswered() { return isAnswered; }

    /** @return the dollar value of the question */
    public int getValue() { return value; }

    /** Marks this question as answered. */
    public void markAnswered() { this.isAnswered = true; }

    /** Resets the question so it can be asked again. */
    public void reset() { this.isAnswered = false; }

    /**
     * Returns a formatted string representation of the question, including
     * category, prompt, value, and all options.
     *
     * @return formatted question details
     */
    @Override
    public String toString() {
        String q = "";

        q += "\nCategory: " + getCategory();
        q += "\nQuestion: " + getPrompt();
        q += "\nValue: " + getValue();
        q += "\nOptions: ";

        for (Option o : getOptions()) {
            q += "\n" + (o.getLabel() + " " + o.getText());
        }

        q += "\n";

        return q;
    }
}