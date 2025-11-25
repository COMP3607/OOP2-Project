package org.example.FileImportTemplates;

import org.example.Question.JeopardyQuestion;

import java.io.File;
import java.util.List;

public abstract class FileImporter 
{
    
    public final List<JeopardyQuestion> Questions(String filename)
    {
        File file = openFile(filename);
        validate(file);
        List<JeopardyQuestion> questions = parse(file);
        closeFile(file);
        return questions;
    }

    public File openFile(String filename)
    {
        System.out.println("Opening file: " + filename);
        return new File(filename);
    }

    public void closeFile(File file)
    {
         System.out.println("Finished reading: " + file.getName());
    }

    protected void validate(File file) 
    {
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + file.getName());
        }
    }

    protected abstract List<JeopardyQuestion> parse(File file);
}
