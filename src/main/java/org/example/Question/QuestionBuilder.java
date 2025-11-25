package org.example.Question;

public class QuestionBuilder {

    String answer;
    String prompt;
    String category;
    boolean isAnswered = false;
    int value;
    public QuestionBuilder answer(String answer) {
        this.answer = answer;
        return this;
    }

    public QuestionBuilder prompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public QuestionBuilder category(String category) {
        this.category = category;
        return this;
    }

    public QuestionBuilder value(int value) {
        this.value = value;
        return this;
    }

   // public JeopardyQuestion build() {
    //    return new JeopardyQuestion(answer,prompt,category,value);
    //}

    public boolean validate(){
        return answer != null && !answer.isBlank() &&
                prompt != null && !prompt.isBlank() &&
                category != null && !category.isBlank() &&
                value > 0;
    }

}
