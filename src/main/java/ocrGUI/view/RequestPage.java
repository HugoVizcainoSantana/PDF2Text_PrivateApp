package ocrGUI.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RequestPage {

    private final int lastPage;
    private Integer firstPage;
    private JPanel requestPanel;
    private JSpinner spinnerPage;

    public RequestPage(Integer firstPage, int lastPage) {
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        createLayout();
    }

    public Integer expectValue() {
        JOptionPane.showConfirmDialog(
                null,
                requestPanel,
                "Solicitar pagina",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return ((Integer) spinnerPage.getValue()) - 1;
    }

    private void createLayout() {
        //Center Panel
        JLabel labelRequest = new JLabel("### Default Text ###");
        SpinnerNumberModel spinnerRestrictions = new SpinnerNumberModel();
        int initialValue;
        if (firstPage == null || firstPage == 0) {
            labelRequest.setText("Introduzca la primera pagina a escanear");
            firstPage = 0;
            initialValue = firstPage + 1;
        } else {
            initialValue = lastPage;
            labelRequest.setText("Introduzca la ultima pagina a escanear, inclusive");
        }
        //System.out.println("Setting up page request.");
        //System.out.println("firstPage = " + firstPage);
        //System.out.println("lastPage = " + lastPage);
        spinnerRestrictions.setMinimum(firstPage + 1);
        spinnerRestrictions.setValue(initialValue);
        spinnerRestrictions.setMaximum(lastPage);
        spinnerPage = new JSpinner(spinnerRestrictions);
        JPanel spinnerContainer = new JPanel(new BorderLayout());
        spinnerContainer.add(spinnerPage, BorderLayout.CENTER);
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(labelRequest, BorderLayout.PAGE_START);
        panelCenter.add(spinnerContainer, BorderLayout.CENTER);
        //Global Panel
        requestPanel = new JPanel(new BorderLayout());
        requestPanel.add(panelCenter, BorderLayout.CENTER);
        requestPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    }

}