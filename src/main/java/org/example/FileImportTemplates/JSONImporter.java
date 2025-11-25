package org.example.FileImportTemplates;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

public class JSONImporter extends FileImporter {

    @Override
    protected List<JeopardyQuestion> parse(File file) {
        List<JeopardyQuestion> questions = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(file); // root is an array

            for (JsonNode node : root) 
            {
                String category = node.get("Category").asText();
                int value = node.get("Value").asInt();
                String prompt = node.get("Question").asText();
                String answer = node.get("CorrectAnswer").asText();
                
                JsonNode optNode = node.get("Options");
                List<Option> optionList = new ArrayList<>();

                optNode.fieldNames().forEachRemaining(label -> {
                    String text = optNode.get(label).asText();
                    optionList.add(new Option(label, text));
                });

                JeopardyQuestion q = new JeopardyQuestion(answer, prompt, category, value, optionList);

                questions.add(q);
            }

        } 
        catch (Exception e) 
        {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage());
        }

        return questions;
    }
}


