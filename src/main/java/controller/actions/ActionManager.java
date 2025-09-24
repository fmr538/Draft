package draft.controller.actions;


import draft.controller.actions.state.*;

import java.util.ArrayList;

public class ActionManager {

    private final ArrayList<AbstractRoomAction> actions;
    private final ArrayList<AbstractRoomAction> stateActions;
    private final draft.controller.actions.UndoAction undoAction;
    private final draft.controller.actions.RedoAction redoAction;

    public ActionManager() {
        actions = new ArrayList<>();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        actions.add(new AboutUsAction());
        actions.add(new RenameNodeAction());
        actions.add(new CreateNodeAction());
        actions.add(new RemoveNodeAction());
        actions.add(new ProjectInfoAction());
        actions.add(new SaveProjectAction());
        actions.add(new SaveProjectAsAction());
        actions.add(new LoadProjectAction());
        actions.add(new ExitAction());
        actions.add(new SaveTemplateAction());
        actions.add(new LoadTemplateAction());

        stateActions = new ArrayList<>();
        stateActions.add(new SelectStateAction());

        stateActions.add(new AddElementStateAction());
        stateActions.add(new EditElementStateAction());
        stateActions.add(new EditRoomStateAction());
        stateActions.add(new DeleteElementStateAction());

        stateActions.add(new CopyElementAction());
        stateActions.add(new PasteElementAction());

        stateActions.add(new RotateElementLeftAction());
        stateActions.add(new RotateElementRightAction());
        stateActions.add(new MoveElementAction());
        stateActions.add(new ResizeStateAction());
        stateActions.add(new OrganizeRoomAction());
        stateActions.add(undoAction);
        stateActions.add(redoAction);
    }


    public ArrayList<AbstractRoomAction> getActions() {
        return actions;
    }

    public ArrayList<AbstractRoomAction> getStateActions() {
        return stateActions;
    }

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }
}
