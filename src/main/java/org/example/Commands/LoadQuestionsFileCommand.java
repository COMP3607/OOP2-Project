package org.example.Commands;

import org.example.FileImportTemplates.FileImporter;
import org.example.FileImportTemplates.CSVImporter;
import org.example.FileImportTemplates.JSONImporter;
import org.example.FileImportTemplates.XMLImporter;

import org.example.Game.JeopardyGame;
import org.example.Question.JeopardyQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * A command that loads Jeopardy questions from a file and inserts them into the game.
 * <p>
 * Supported file formats include:
 * <ul>
 *     <li>CSV</li>
 *     <li>JSON</li>
 *     <li>XML</li>
 * </ul>
 *</p>

 * <p>
 * This command uses the Template Method pattern through {@link FileImporter},
 * allowing different file importers to share a common workflow.
 * </p>
 *
 * <p>
 * Full undo support is implemented via storing a deep copy of the previously loaded
 * questions. If loading fails, the previous questions are restored immediately.
 * </p>
 */
public class LoadQuestionsFileCommand implements Command {

    /** The game instance whose question list will be modified. */
    private final JeopardyGame game;

    /** The filename to load questions from. */
    private final String filename;

    /** Backup of the game's question list for supporting undo. */
    private List<JeopardyQuestion> previousQuestions;

    /** The questions most recently loaded from file. */
    private List<JeopardyQuestion> loadedQuestions;

    /**
     * Creates a new command for loading questions from a file.
     *
     * @param game     the Jeopardy game that will receive the imported questions
     * @param filename the file path to load, must end in .csv, .json, or .xml
     */
    public LoadQuestionsFileCommand(JeopardyGame game, String filename) {
        this.game = game;
        this.filename = filename.trim();
    }

    /**
     * Executes the command by importing questions from the file.
     * <p>
     * Steps performed:
     * <ol>
     *     <li>Backup current questions for undo.</li>
     *     <li>Determine appropriate file importer based on file extension.</li>
     *     <li>Use the importer's template method to load and parse questions.</li>
     *     <li>Replace the game's question list with the newly loaded questions.</li>
     * </ol>
     *
     * @throws RuntimeException if file loading fails at any point
     */
    @Override
    public void execute() {
        // 1. Backup current questions for undo
        previousQuestions = new ArrayList<>(game.getQuestions());

        // 2. Detect file type and create appropriate importer
        FileImporter importer = createImporterFor(filename);

        System.out.println("Loading questions from: " + filename);

        try {
            // 3. Use the Template Method: importer handles open → validate → parse → close
            loadedQuestions = importer.Questions(filename);

            // 4. Replace questions in game
            game.getQuestions().clear();
            game.getQuestions().addAll(loadedQuestions);

            System.out.println("SUCCESS: Loaded " + loadedQuestions.size() + " questions from " + filename);
            System.out.println("Ready to play!\n");

        } catch (Exception e) {
            // On failure: restore previous state immediately
            game.getQuestions().clear();
            game.getQuestions().addAll(previousQuestions);
            throw new RuntimeException("Failed to load questions: " + e.getMessage(), e);
        }
    }

    /**
     * Undoes the question loading operation by restoring the original
     * list of questions.
     * <p>
     * If no previous questions exist (e.g., command was never executed),
     * a message is printed and no action is taken.
     */
    @Override
    public void undo() {
        if (previousQuestions == null) {
            System.out.println("Nothing to undo — no previous questions.");
            return;
        }

        game.getQuestions().clear();
        game.getQuestions().addAll(previousQuestions);

        System.out.println("Reverted to previous question set (" + previousQuestions.size() + " questions).");
    }

    /**
     * Determines the correct {@link FileImporter} implementation based on
     * the filename extension.
     *
     * @param filename the file path being imported
     * @return a {@link CSVImporter}, {@link JSONImporter}, or {@link XMLImporter}
     * @throws IllegalArgumentException if the file extension is unsupported
     */
    private FileImporter createImporterFor(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        String lower = filename.toLowerCase();

        if (lower.endsWith(".csv")) {
            return new CSVImporter();
        } else if (lower.endsWith(".json")) {
            return new JSONImporter();
        } else if (lower.endsWith(".xml")) {
            return new XMLImporter();
        } else {
            throw new IllegalArgumentException(
                "Unsupported file type: " + filename +
                "\nSupported formats: .csv, .json, .xml"
            );
        }
    }

    /**
     * Returns the number of questions loaded during the most recent execution
     * of the command.
     *
     * @return the number of loaded questions, or 0 if none were loaded
     */
    public int getLoadedCount() {
        return loadedQuestions != null ? loadedQuestions.size() : 0;
    }
}