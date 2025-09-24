package draft.view.tree.view;

import draft.view.tree.model.DraftTreeItem;
import draft.model.draftRepository.nodes.Project;
import draft.model.draftRepository.nodes.ProjectExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class DraftTreeCellRenderer extends DefaultTreeCellRenderer {
    public DraftTreeCellRenderer() {}

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row, hasFocus);
            URL imageURL = null;

            if (((DraftTreeItem)value).getDraftNode() instanceof ProjectExplorer) {
                imageURL = getClass().getResource("/images/tdiagram.gif");
            }
            else if (((DraftTreeItem)value).getDraftNode() instanceof Project) {
                imageURL = getClass().getResource("/images/tproject.gif");
            }

            Icon icon = null;
            if (imageURL != null)
                icon = new ImageIcon(imageURL);
            setIcon(icon);

            return this;
        }

}


