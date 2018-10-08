package ocrGUI.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomProgressDialog extends JDialog {
    private final String progressText;
    private final int progressEnd;
    private JProgressBar progressBar;
    private JLabel progressLabel;

    public CustomProgressDialog(JFrame window, String title, boolean isModal, String progressText, int progressEnd) {
        super(window, title, isModal);
        this.progressText = progressText;
        this.progressEnd = progressEnd;
        progressBar = new JProgressBar(0, progressEnd);
        this.add(progressBar, BorderLayout.CENTER);
        progressLabel = new JLabel(progressText);
        this.add(progressLabel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Do Not Close
            }
        });
        this.setLocationRelativeTo(window);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.pack();
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void updateProgress(int progress) {
        progressLabel.setText(progressText + " " + progress + "/" + progressEnd);
        progressBar.setValue(progress);
    }
}
