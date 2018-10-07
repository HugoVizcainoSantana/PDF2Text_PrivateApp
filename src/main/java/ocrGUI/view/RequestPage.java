package ocrGUI.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RequestPage  {

    private Integer firstPage;
    private final int lastPage;
    private JPanel requestPanel;
    private JSpinner spinnerPage;

    public RequestPage(Integer firstPage, int lastPage) {
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        createLayout();
    }

    public Integer expectValue(){
        JOptionPane.showConfirmDialog(
                null,
                requestPanel,
                "Solicitar pagina",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return ((Integer)spinnerPage.getValue())-1;
    }

    private void createLayout(){
        //Center Panel
        JLabel labelRequest = new JLabel("### Default Text ###");
        SpinnerNumberModel spinnerRestrictions = new SpinnerNumberModel();
        if (firstPage==null || firstPage==0) {
            labelRequest.setText("Introduzca la primera pagina a escanear");
            firstPage=0;
        } else {
            firstPage++;
            labelRequest.setText("Introduzca la ultima pagina a escanear");
        }
        spinnerRestrictions.setMinimum(firstPage+1);
        spinnerRestrictions.setValue(firstPage+1);
        spinnerRestrictions.setMaximum(lastPage+1);
        spinnerPage = new JSpinner(spinnerRestrictions);
        JPanel spinnerContainer = new JPanel(new BorderLayout());
        spinnerContainer.add(spinnerPage,BorderLayout.CENTER);
        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(labelRequest,BorderLayout.PAGE_START);
        panelCenter.add(spinnerContainer,BorderLayout.CENTER);
        //Global Panel
        requestPanel = new JPanel(new BorderLayout());
        requestPanel.add(panelCenter,BorderLayout.CENTER);
        requestPanel.setBorder(new EmptyBorder(10,10,10,10));

    }

}