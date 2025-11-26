package org.example.FileImportTemplates;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;


/**
 * Concrete {@link FileImporter} implementation for importing questions from a
 * JSON file.<br>
 * Each JSON object in the root array defines one {@link JeopardyQuestion}
 * with exactly four options labeled A–D. The importer converts each JSON
 * object into a question instance.
 *
 * <p><strong>Error Handling:</strong> Any exception during parsing results in a
 * {@link RuntimeException} with a descriptive message.</p>
 */
public class JSONImporter extends FileImporter {

    /**
     * Parses a JSON file and converts each object in the root array into a
     * {@link JeopardyQuestion}.
     * <p>
     * This method loads the JSON data, iterates through each object, and
     * constructs the following fields:
     * </p>
     * <ul>
     *     <li><strong>Category</strong></li>
     *     <li><strong>Value</strong></li>
     *     <li><strong>Prompt</strong> (stored as <em>Question</em> in JSON)</li>
     *     <li><strong>Options A–D</strong> (from the <em>Options</em> object)</li>
     *     <li><strong>Correct Answer</strong></li>
     * </ul>
     *
     * @param file the JSON file to pars; must not be {@code null}.
     * @return a list of {@link JeopardyQuestion} objects representing each
     *         element in the JSON array.
     *
     * @throws RuntimeException if the file cannot be read or if the JSON format
     *         is invalid.
     */
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

