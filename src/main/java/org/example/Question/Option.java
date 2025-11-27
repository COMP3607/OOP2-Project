package org.example.Question;

/**
 * Represents a single multiple-choice option for a Jeopardy question.
 * Each option has a label and descriptive text.
 */
public class Option {

    /** The label identifying the option ("A", "B", "C", "D"). */
    private String label;

    /** The text describing the option. */
    private String text;

    /**
     * Creates a new multiple-choice option.
     *
     * @param label the option label
     * @param text  the option text
     */
    public Option(String label, String text) {
        this.label = label;
        this.text = text;
    }

    /** @return the label for this option */
    public String getLabel() { return label; }

    /** @return the text description of this option */
    public String getText() { return text; }
}