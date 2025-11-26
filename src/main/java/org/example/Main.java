package org.example;

import java.util.List;
import java.util.Scanner;

import org.example.FileImportTemplates.*;
import org.example.Game.JeopardyGame;
import org.example.Game.Player;
import org.example.Question.JeopardyQuestion;
import org.example.UI.GameWindow;

public class Main {
    public static void main(String[] args) {

        String filename1 = "sample_game_JSON.json";
        FileImporter test = new JSONImporter();
        List<JeopardyQuestion> qtest = test.Questions(filename1);
        JeopardyGame newGame = new JeopardyGame(qtest);


        Scanner sc = new Scanner(System.in);
        System.out.println("Type GUI to launch the GUI Or 'Text' to continue in terminal");
        String input = sc.nextLine();

        if (input.equalsIgnoreCase("GUI")) {
            GameWindow g = new GameWindow(newGame);
            // force window to top temporarily
            g.setAlwaysOnTop(true);
            g.setAlwaysOnTop(false);
        }
        else{
            if(input.equalsIgnoreCase("text")){

            }
        }


    }
}