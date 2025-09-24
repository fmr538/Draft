package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Building;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.Room;
import draft.view.swing.MainFrame;

import java.awt.event.ActionEvent;
import java.io.File;

public class SaveTemplateAction extends AbstractRoomAction{
    public SaveTemplateAction() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        putValue(NAME, "Save Template");
        putValue(SHORT_DESCRIPTION, "Save Template");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoom() == null)
        {
            return;
        }
        Room template = MainFrame.getInstance().getProjectView().getCurrentRoomView().getRoom();
        try{
            ApplicationFramework.getInstance().getSerializer().saveTemplate(template);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
