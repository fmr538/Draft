package draft.view.tree.controller;

import draft.core.ApplicationFramework;
import draft.view.swing.MainFrame;
import draft.view.tree.DraftTreeImplementation;
import draft.view.tree.view.DraftTreeView;
import draft.model.draftRepository.DraftRepositoryImplementation;
import draft.model.draftRepository.nodes.Project;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DraftTreeItemMouseListener implements MouseListener {
    private final DraftRepositoryImplementation draftRepository;
    public DraftTreeItemMouseListener() {
        draftRepository = (DraftRepositoryImplementation) ApplicationFramework.getInstance().getDraftRepository();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2)
        {
            DraftTreeView treeView = ((DraftTreeImplementation) MainFrame.getInstance().getDraftTree()).getTreeView();
            treeView.expandPath(treeView.getSelectionPath());

            if(MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode() instanceof Project)
            {
                draftRepository.setSelectedProject((Project) MainFrame.getInstance().getDraftTree().getSelectedNode().getDraftNode());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
