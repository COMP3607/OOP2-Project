package org.example.Commands;

public class Invoker{
    private Command command;
    public void setCommand(Command command){
        this.command = command;
    }
    public void executeCommand(){
        command.execute();
    }
    public void undoCommand(){
        command.undo();
        System.out.println("Undoing command");
    }
}