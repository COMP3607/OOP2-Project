package org.example.FileImportTemplates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

/**
 * Concrete {@link FileImporter} implementation for the questions from a CSV file.<br>
 * Each row after the header defines one {@link JeopardyQuestion} with exactly
 * four options labeled A–D. The importer will convert each row into a question
 *
 * <p><strong>Error Handling:</strong> Any exception during parsing results in a
 * {@link RuntimeException} with a descriptive message.</p>
 */
public class CSVImporter extends FileImporter
{
    /**
     * Parses a CSV file and converts each row (excluding the header) into a
     * {@link JeopardyQuestion}.
     * <p>
     * This method reads the file line by line, splits each line by commas, and
     * constructs the following fields:
     * </p>
     * <ul>
     *     <li><strong>Category</strong></li>
     *     <li><strong>Value</strong></li>
     *     <li><strong>Prompt</strong></li>
     *     <li><strong>Options A–D</strong></li>
     *     <li><strong>Correct Answer</strong></li>
     * </ul>
     *
     * @param file the CSV file to pars; must not be {@code null}.
     * @return a list of {@link JeopardyQuestion} objects representing each row
     *         in the CSV file.
     *
     * @throws RuntimeException if the file cannot be read or if the data format
     *         is invalid.
     */
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

                if(data[7].isEmpty()){
                    throw new RuntimeException("Error parsing CSV: ");
                }

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
