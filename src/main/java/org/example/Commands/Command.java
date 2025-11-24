package org.example.Commands;

public abstract class Command {

    public abstract void execute();
    public abstract void undo();

    public Command(){

    }

}
