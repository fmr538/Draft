package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.nodes.Project;
import draft.view.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class LoadProjectAction extends AbstractRoomAction{
    public LoadProjectAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        putValue(NAME, "Open");
        putValue(SHORT_DESCRIPTION, "Open Project");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser("src/main/resources/projects");

        if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try{
                File file = jfc.getSelectedFile();

                if(!(file.toString().endsWith(".DraftProject")))
                {
                    ApplicationFramework.getInstance().getMessageGenerator().loadProject();
                    return;
                }
                Project project = ApplicationFramework.getInstance().getSerializer().loadProject(file);
                project.setFilepath(file.getPath());
                MainFrame.getInstance().getDraftTree().loadProject(project);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
