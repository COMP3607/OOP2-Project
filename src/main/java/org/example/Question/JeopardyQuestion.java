package org.example.Question;

import java.util.List;

public class JeopardyQuestion 
{

    private String answer;
    private String prompt;
    private String category;
    private boolean isAnswered;
    private List<Option> options;
    private int value;

    public JeopardyQuestion(String answer, String prompt, String category, int value, List<Option> options) 
    {
        this.answer = answer;
        this.prompt = prompt;
        this.category = category;
        this.options = options;
        this.value = value;
        this.isAnswered = false;
    }

    public String getAnswer() { return answer; }
    public String getPrompt() { return prompt; }
    public String getCategory() { return category; }
    public List<Option> getOptions() { return options; }
    public boolean isAnswered() { return isAnswered; }
    public int getValue() { return value; }
    public void markAnswered() { this.isAnswered = true; }
    public void reset() { this.isAnswered = false; }
    
    @Override
    public String toString()
    {
        String q = "";

        q += "\nCategory: " + getCategory();
        q += "\nQuestion: " + getPrompt();
        q += "\nValue: " + getValue();
        q += "\nOptions: ";

        for(Option o : getOptions())
        {
            q += "\n" + (o.getLabel() + " " +o.getText());
        }

        q += "\n";
        
        return q;
    }
}
