package org.example.Question;

public class JeopardyQuestion {

    private String answer;
    private String prompt;
    private String category;
    private boolean isAnswered;
    private int value;


    public JeopardyQuestion(String answer, String prompt, String category, int value) {
        this.answer = answer;
        this.prompt = prompt;
        this.category = category;
        this.isAnswered = false;
    }

    public String getAnswer() { return answer; }
    public String getPrompt() { return prompt; }
    public String getCategory() { return category; }
    public boolean isAnswered() { return isAnswered; }
    public int getValue() { return value; }
}
