package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.DraftNode;
import draft.model.draftRepository.nodes.Project;
import draft.view.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class SaveProjectAsAction extends AbstractRoomAction{
    public SaveProjectAsAction() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        putValue(NAME, "Save As");
        putValue(SHORT_DESCRIPTION, "Save Project As");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getDraftTree().getSelectedNode() == null)
        {
            return;
        }
        DraftNode node = MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode();
        while(node.getClass() != Project.class)
        {
            if(node.getParent() == null)
                return;
            node = node.getParent();
        }

        JFileChooser jfc = new JFileChooser("src/main/resources/projects");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            node.setName(jfc.getSelectedFile().getName());
            File file = new File(jfc.getSelectedFile() + "/" + node.getName() + ".DraftProject");
            ((Project) node).setFilepath(file.getPath());

            try{
                ApplicationFramework.getInstance().getSerializer().saveProject((Project) node);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}

