package org.example.FileImportTemplates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

public class CSVImporter extends FileImporter
{
    @Override
    protected List<JeopardyQuestion> parse(File file) 
    {
        List<JeopardyQuestion> questions = new ArrayList<>();

        try 
        {
            FileReader reader = new FileReader(file);
            BufferedReader bfreader = new BufferedReader(reader);

            String line;

            bfreader.readLine();

            while((line = bfreader.readLine()) != null)
            {
                String[] data = line.split(",");
                
                String category = data[0];
                int value = Integer.parseInt(data[1]);
                String prompt = data[2];
                
                List<Option> optionList = new ArrayList<>();

                optionList.add(new Option("A", data[3]));
                optionList.add(new Option("B", data[4]));
                optionList.add(new Option("C", data[5]));
                optionList.add(new Option("D", data[6]));

                String answer = data[7];
                
                JeopardyQuestion q = new JeopardyQuestion(answer, prompt, category, value, optionList);

                questions.add(q);
            }
        } 
        catch (Exception e) 
        {
            throw new RuntimeException("Error parsing CSV: " + e.getMessage());
        }

        return questions;
    }
}
