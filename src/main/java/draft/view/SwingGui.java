package draft.view;

import draft.controller.commands.CommandManager;
import draft.core.Gui;
import draft.view.swing.MainFrame;

import java.awt.*;

public class SwingGui implements Gui {
    private MainFrame instance;
    private CommandManager commandManager;

    public SwingGui() {
    }

    @Override
    public void start() {
        instance = MainFrame.getInstance();
        instance.setSize(new Dimension(1024, 600));
        instance.setMinimumSize(new Dimension(640, 350));
        instance.setVisible(true);
        commandManager = new CommandManager();
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }
}
