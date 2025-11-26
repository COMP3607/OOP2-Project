package org.example.FileImportTemplates;

import org.example.Question.JeopardyQuestion;

import java.io.File;
import java.util.List;

/**
 * Defines the common structure for importing {@link JeopardyQuestion} objects
 * from different file formats. Concrete importer classes implement the
 * {@link #parse(File)} method to handle their specific formats.
 *
 * <p>The import process includes opening the file, validating it, parsing the
 * questions, and closing the file.</p>
 */
public abstract class FileImporter
{
    /**
     * Loads all questions from the given file.
     * This method performs the full import routine.
     *
     * @param filename the name of the file to read.
     * @return a list of {@link JeopardyQuestion} objects.
     */
    public final List<JeopardyQuestion> Questions(String filename)
    {
        File file = openFile(filename);
        validate(file);
        List<JeopardyQuestion> questions = parse(file);
        closeFile(file);
        return questions;
    }

    /**
     * Opens the file and returns a {@link File} reference.
     *
     * @param filename the name of the file.
     * @return a {@link File} object pointing to the given filename.
     */
    public File openFile(String filename)
    {
        System.out.println("Opening file: " + filename);
        return new File(filename);
    }

    /**
     * Called after the file has been processed.
     *
     * @param file the file that has been opened.
     */
    public void closeFile(File file)
    {
        System.out.println("Finished reading: " + file.getName());
    }

    /**
     * Checks that the file exists before parsing.
     *
     * @param file the file to validate.
     * @throws RuntimeException if the file does not exist.
     */
    protected void validate(File file)
    {
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + file.getName());
        }
    }

    /**
     * Parses the file into question objects.
     * Must be implemented by each importer.
     *
     * @param file the file containing the questions.
     * @return a list of parsed questions.
     */
    protected abstract List<JeopardyQuestion> parse(File file);
}


