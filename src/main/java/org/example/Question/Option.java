package org.example.Question;


public class Option 
{
    private String label;    // "A", "B", "C", "D"
    private String text;     // The text of the option

    public Option(String label, String text) 
    {
        this.label = label;
        this.text = text;
    }

    public String getLabel() { return label; }
    public String getText() { return text; }
}