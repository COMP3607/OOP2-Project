package org.example.Commands;

/**
 * Represents an executable action in the Command design pattern.
 * 
 * <p>A Command encapsulates a request as an object, allowing actions to be
 * queued, logged, undone, or executed later. Implementing classes should
 * define the specific behavior for both executing and undoing the action.</p>
 */
public interface Command{

    /**
     * Executes the command's action.
     * 
     * <p>This method performs the primary behavior associated with the command.</p>
     */
    public void execute();
    
    /** 
     * Reverses the effects of the {@link #execute()} method.
     * 
     * <p>This method should restore the system to the state before the command
     * was executed. Not all commands may be reversible depending on design.</p>
     */
    public void undo();
}
