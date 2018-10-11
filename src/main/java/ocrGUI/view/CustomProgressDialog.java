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

    public CustomProgressDialog(String title, boolean isModal, String progressText, int progressEnd) {
        super((Frame) null, title, isModal);
        this.progressText = progressText;
        this.progressEnd = progressEnd;
        JPanel panel = new JPanel(new BorderLayout());
        progressBar = new JProgressBar(0, progressEnd);
        progressBar.setSize(350, 50);
        panel.add(progressBar, BorderLayout.CENTER);
        progressLabel = new JLabel(progressText);
        panel.add(progressLabel, BorderLayout.NORTH);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(panel);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Do Not Close
            }
        });
        this.pack();
        this.setSize(Math.max(200, progressEnd), 100); // Make Dialog larger on larger progress
        this.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void updateProgress(int progress) {
        progressLabel.setText(progressText + " " + progress + "/" + progressEnd);
        progressBar.setValue(progress);
    }
}
