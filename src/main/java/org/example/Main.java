package org.example;

import java.util.List;

import org.example.FileImportTemplates.*;
import org.example.Question.JeopardyQuestion;

public class Main {
    public static void main(String[] args) 
    {

        String filename1 = "sample_game_JSON.json";
        FileImporter test = new JSONImporter();

        List<JeopardyQuestion>qtest = test.Questions(filename1);

        for(JeopardyQuestion j: qtest)
        {
            System.out.println(j.toString());
        }


    }
}