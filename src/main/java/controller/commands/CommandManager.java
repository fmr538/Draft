package draft.controller.commands;

import draft.model.draftRepository.nodes.Room;
import draft.view.swing.MainFrame;
import draft.view.tree.DraftTreeImplementation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {

    private HashMap<Room, ArrayList<AbstractCommand>> roomCommands = new HashMap<>();
    private HashMap<Room, Integer> roomCounters = new HashMap<>();
    private List<AbstractCommand> commands = new ArrayList<AbstractCommand>();
    private int currentCommand = 0;

    public void addCommand(AbstractCommand command){
        Room room = MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoom();

        if (!roomCommands.containsKey(room)) {
            roomCommands.put(room, new ArrayList<>());
        }

        if (!roomCounters.containsKey(room)) {
            roomCounters.put(room, 0);
            System.out.println(room);
            System.out.println("Room added hashCode: " + room.hashCode());
            System.out.println("Room used hashCode: " + MainFrame.getInstance().getProjectView().getCurrentRoomView().hashCode());
        }
//        while(currentCommand < commands.size())
//            commands.remove(currentCommand);

        List<AbstractCommand> commands = roomCommands.get(room);
        int counter = roomCounters.get(room);

// Remove only commands after the current position
        if (counter < commands.size()) {
            commands.subList(counter, commands.size()).clear();
        }

        roomCommands.get(room).add(command);
//        commands.add(command);
        doCommand();
    }

    public void doCommand(){
        Room room = MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoom();
        System.out.println("do "+ room);
//        if(currentCommand < commands.size()){
        if(roomCounters.get(room) < roomCommands.get(room).size()){
//            commands.get(currentCommand++).doCommand();
            roomCommands.get(room).get(roomCounters.get(room)).doCommand();
            roomCounters.put(room, roomCounters.get(room)+1);
            SwingUtilities.updateComponentTreeUI(((DraftTreeImplementation) MainFrame.getInstance().getDraftTree()).getTreeView());
//            MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
        }
//        if(currentCommand==commands.size()){
        if(roomCounters.get(room) == roomCommands.get(room).size()){
//            MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
        }
    }

    /*
     * Metoda koja poziva redo konkretne komande
     */
    public void undoCommand(){
        Room room = MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoom();
//        if(currentCommand > 0){
        System.out.println("undo " + room);
        System.out.println("Room added hashCode: " + room.hashCode());
        System.out.println("Room used hashCode: " + MainFrame.getInstance().getProjectView().getCurrentRoomView().hashCode());
        if(roomCounters.get(room) > 0){
//            MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
//            commands.get(--currentCommand).undoCommand();
            roomCounters.put(room, roomCounters.get(room)-1);
            roomCommands.get(room).get(roomCounters.get(room)).undoCommand();
            SwingUtilities.updateComponentTreeUI(((DraftTreeImplementation)MainFrame.getInstance().getDraftTree()).getTreeView());
        }
//        if(currentCommand==0){
        if(roomCounters.get(room) == 0){
//            MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
        }
    }
}
