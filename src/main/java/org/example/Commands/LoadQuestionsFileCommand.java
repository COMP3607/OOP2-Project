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
 * Command to load questions from a file (CSV, JSON, or XML).
 * Automatically detects file type by extension and uses the correct importer.
 * Fully supports undo/redo via storing previous question list.
 */
public class LoadQuestionsFileCommand implements Command {

    private final JeopardyGame game;
    private final String filename;


    private List<JeopardyQuestion> previousQuestions;


    private List<JeopardyQuestion> loadedQuestions;

    public LoadQuestionsFileCommand(JeopardyGame game, String filename) {
        this.game = game;
        this.filename = filename.trim();
    }

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

    
    public int getLoadedCount() {
        return loadedQuestions != null ? loadedQuestions.size() : 0;
    }
}