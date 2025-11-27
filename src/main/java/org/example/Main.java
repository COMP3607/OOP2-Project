package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import org.example.Commands.LoadQuestionsFileCommand;
import org.example.Game.GameController;
import org.example.Game.JeopardyGame;

public class Main {
    public static void main(String[] args) {
        String filename1 = "sample_game_JSON.json";
        JeopardyGame newGame = new JeopardyGame(new ArrayList<>());
        
        GameController tempController = new GameController(newGame);
        LoadQuestionsFileCommand loadCmd = new LoadQuestionsFileCommand(newGame, filename1, tempController);
        tempController.executeCommand(loadCmd);
        Scanner sc = new Scanner(System.in);
        GameController controller = new GameController(newGame, sc);
        controller.runCLIGame();
    }
}