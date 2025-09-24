package draft.core;

import draft.controller.commands.AbstractCommand;
import draft.controller.commands.CommandManager;

public interface Gui {
    public void start();

    CommandManager getCommandManager();
}
