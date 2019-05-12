package MyGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoAction implements ActionListener {
    private DrawArea panel;

    public UndoAction(DrawArea panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.undoHistory();
    }
}