package org.example;

import java.util.ArrayList;
import java.util.Scanner;

import org.example.Commands.LoadQuestionsFileCommand;
import org.example.Game.GameController;
import org.example.Game.JeopardyGame;

public class Main {
    public static void main(String[] args) {

        System.out.println("Plese Select the File type to read from:");
        System.out.println("1. JSON");
        System.out.println("2. CSV");
        System.out.println("3. XML");
        System.out.print("Enter your choice (1-3): ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        String filename1 = "";

        if (choice == 1) 
        {
            filename1 = "sample_game_JSON.json";
        }
        else if (choice == 2) 
        {
            filename1 = "sample_game_CSV.csv";
        }
        else if (choice == 3) 
        {
            filename1 = "sample_game_XML.xml";
        }

        
        JeopardyGame newGame = new JeopardyGame(new ArrayList<>());
        
        GameController tempController = new GameController(newGame);
        LoadQuestionsFileCommand loadCmd = new LoadQuestionsFileCommand(newGame, filename1, tempController);
        tempController.executeCommand(loadCmd);
        Scanner sc = new Scanner(System.in);
        GameController controller = new GameController(newGame, sc);
        controller.runCLIGame();
    }
}