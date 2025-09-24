package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.model.draftRepository.nodes.Room;
import draft.view.swing.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadTemplateAction extends AbstractRoomAction {
    public LoadTemplateAction() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        putValue(NAME, "Load Template");
        putValue(SHORT_DESCRIPTION, "Load Template");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser("src/main/resources/templates");

        if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try{
                File file = jfc.getSelectedFile();

                if(!(file.toString().endsWith(".DraftTemplate")))
                {
                    ApplicationFramework.getInstance().getMessageGenerator().loadProject();
                    return;
                }
                Room temp = ApplicationFramework.getInstance().getSerializer().loadTemplate(file);
               // MainFrame.getInstance().getProjectView().setCurrentRoomView();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
