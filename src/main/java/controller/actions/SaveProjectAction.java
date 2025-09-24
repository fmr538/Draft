package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Building;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.Room;
import draft.view.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveProjectAction extends AbstractRoomAction{
    public SaveProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save Project");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getDraftTree().getSelectedNode() == null)
        {
            return;
        }
        DraftNode node = MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode();
        if(node == null)
            return;
        if(node instanceof Room)
        {
            node = node.getParent();
        }
        if(node instanceof Building)
            node = node.getParent();
        if(((Project) node).getFilepath() == null) {
            File file = new File("src/main/resources/projects/" + node.getName() + ".DraftProject");
            ((Project) node).setFilepath(file.getPath());

            try{
                ApplicationFramework.getInstance().getSerializer().saveProject((Project) node);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } else {
            File file = new File(((Project) node).getFilepath());
            try{
                ApplicationFramework.getInstance().getSerializer().saveProject((Project) node);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
