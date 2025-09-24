package draft.controller.actions;

import draft.core.ApplicationFramework;
import draft.view.swing.MainFrame;
import draft.view.tree.DraftTreeImplementation;
import draft.model.draftRepository.DraftRepositoryImplementation;
import draft.model.draftRepository.nodes.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ProjectInfoAction extends AbstractRoomAction{
    public ProjectInfoAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
//        putValue(SMALL_ICON, loadIcon("/images/about.png"));
        putValue(NAME, "Project");
        putValue(SHORT_DESCRIPTION, "Edit project information");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = ((DraftRepositoryImplementation)ApplicationFramework.getInstance().getDraftRepository()).getSelectedProject();

        if (project == null){
            ApplicationFramework.getInstance().getMessageGenerator().projectNotSelected();
            return;
        }

        String[] info = new String[3];
        info = showProjectInfoDialog(project);

        if (info == null) return;

        project.setName(info[0]);
        project.setFilepath(info[1]);
        project.setAuthor(info[2]);
        ((DraftRepositoryImplementation)ApplicationFramework.getInstance().getDraftRepository()).notifySubscribers(project);
        SwingUtilities.updateComponentTreeUI(((DraftTreeImplementation)MainFrame.getInstance().getDraftTree()).getTreeView());
    }

    public static String[] showProjectInfoDialog(Project project) {
        JTextField projectNameField = new JTextField(20);
        JLabel filepathLabel = new JLabel(project.getFilepath());
        JTextField authorField = new JTextField(20);

        projectNameField.setText(project.getName());
        if (project.getFilepath() == null || project.getFilepath().isEmpty())
            filepathLabel.setText("No path selected");
        else
            filepathLabel.setText(project.getFilepath());

        if (project.getAuthor() == null || project.getAuthor().isEmpty())
            authorField.setText("No author specified");
        else
            authorField.setText(project.getAuthor());

        JButton browseButton = getBrowseButton(filepathLabel);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setSize(new Dimension(100,200));
        panel.add(new JLabel("Project Name:"));
        panel.add(projectNameField);
        panel.add(new JLabel("Filepath:"));
        panel.add(filepathLabel);
        panel.add(new JLabel(""));
        panel.add(browseButton);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Edit Project Info",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String[] ret = new String[3];
            ret[0] = projectNameField.getText();
            ret[1] = filepathLabel.getText();
            ret[2] = authorField.getText();
            return ret;
        }
        
        return null;
    }

    private static JButton getBrowseButton(JLabel filepathLabel) {
        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    filepathLabel.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        return browseButton;
    }
}
