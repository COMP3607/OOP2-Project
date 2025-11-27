package org.example.Question;

import java.util.List;

/**
 * Builder class for constructing {@link JeopardyQuestion} objects
 */
public class QuestionBuilder {

    String answer;
    String prompt;
    String category;
    List<Option> options;
    boolean isAnswered = false;
    int value;

    /**
     * Sets the correct answer for the question.
     *
     * @param answer the correct answer text
     * @return this builder for method chaining
     */
    public QuestionBuilder answer(String answer) {
        this.answer = answer;
        return this;
    }

    /**
     * Sets the prompt or clue of the question.
     *
     * @param prompt the question text
     * @return this builder for method chaining
     */
    public QuestionBuilder prompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    /**
     * Sets the category for the question.
     *
     * @param category the category name
     * @return this builder for method chaining
     */
    public QuestionBuilder category(String category) {
        this.category = category;
        return this;
    }

    /**
     * Sets the dollar value of the question.
     *
     * @param value the monetary value
     * @return this builder for method chaining
     */
    public QuestionBuilder value(int value) {
        this.value = value;
        return this;
    }

    /**
     * Sets the list of multiple-choice options.
     *
     * @param options list of {@link Option} objects
     * @return this builder for method chaining
     */
    public QuestionBuilder options(List<Option> options) {
        this.options = options;
        return this;
    }

    /**
     * Builds and returns a fully constructed {@link JeopardyQuestion}.
     *
     * @return a new JeopardyQuestion instance
     */
    public JeopardyQuestion build() {
        return new JeopardyQuestion(answer, prompt, category, value, options);
    }

    /**
     * Validates whether this builder contains enough information
     * to successfully construct a JeopardyQuestion.
     *
     * @return true if all required fields are valid
     */
    public boolean validate() {
        return answer != null && !answer.isBlank() &&
               prompt != null && !prompt.isBlank() &&
               category != null && !category.isBlank() &&
               value > 0;
    }
}