package org.example.Commands;

/**
 * The Invoker in the Command design pattern.
 * 
 * <p>This class is responsible for storing and executing commands.
 * It decouples the object that issues the request from the object
 * that performs the action.</p>
 */
public class Invoker {

    /** The currently assigned command to execute. */
    private Command command;

    /**
     * Assigns a command for the Invoker to execute.
     *
     * @param command the command instance to store
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the currently stored command.
     *
     * @throws NullPointerException if no command has been set
     */
    public void executeCommand() {
        command.execute();
    }

    /**
     * Reverses the effects of the currently stored command (if supported).
     * Prints a confirmation message after the undo operation.
     *
     * @throws NullPointerException if no command has been set
     */
    public void undoCommand() {
        command.undo();
        System.out.println("Undoing command");
    }
}