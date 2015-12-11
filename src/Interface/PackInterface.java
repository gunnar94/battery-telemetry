/**
 * Sunseeker Telemetry
 *
 * Battery Interface
 *
 * @author Alec Carpenter <alec.g.carpenter@wmich.edu>
 */

package Sunseeker.Telemetry.Battery;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.SpringLayout;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;

class PackInterface extends JPanel implements Listener {
    // Pack information
    private static int count = 1;
    private final int packNumber;

    // Field labels
    private final String MAX_VOLTAGE  = "Maximum Cell Voltage:";
    private final String MIN_VOLTAGE  = "Minimum Cell Voltage:";
    private final String DIFF_VOLTAGE = "Voltage Difference:";
    private final String TOT_VOLTAGE  = "Total Voltage:";
    private final String AVG_VOLTAGE  = "Average Voltage:";

    // Other values
    private final String VOLT_UNIT_ABBR  = " V";
    private final String INITIAL_VOLTAGE = "0.0" + VOLT_UNIT_ABBR;

    // Layout
    private SpringLayout layout;

    // Data files
    private JLabel maxVoltage;
    private JLabel minVoltage;
    private JLabel diffVoltage;
    private JLabel totVoltage;
    private JLabel avgVoltage;

    PackInterface () {
        packNumber = count;
        count++;

        layout = new SpringLayout();

        setBorder(BorderFactory.createTitledBorder("  Pack " + packNumber + "  "));
        setLayout(layout);

        createDataFields();
        initializeListeners();
    }

    public void triggered (Event e) {
        switch (e.getEvent()) {
            case PageHandler.EVENT_NEW_PAGE:
                Pack[] packs = (Pack[]) e.getData();
                Pack pack    = packs[packNumber - 1];

                maxVoltage.setText(pack.getMaxVoltage() + VOLT_UNIT_ABBR);
                minVoltage.setText(pack.getMinVoltage() + VOLT_UNIT_ABBR);
                diffVoltage.setText(pack.getDiffVoltage() + VOLT_UNIT_ABBR);
                totVoltage.setText(pack.getTotalVoltage() + VOLT_UNIT_ABBR);
                avgVoltage.setText(pack.getAverageVoltage() + VOLT_UNIT_ABBR);
                break;
        }
    }

    private void initializeListeners () {
        Dispatcher.subscribe(PageHandler.EVENT_NEW_PAGE, this);
    }

    private void createDataFields () {
        JLabel maxV, minV, diffV, totV, avgV;

        maxV  = new JLabel(MAX_VOLTAGE);
        minV  = new JLabel(MIN_VOLTAGE);
        diffV = new JLabel(DIFF_VOLTAGE);
        totV  = new JLabel(TOT_VOLTAGE);
        avgV  = new JLabel(AVG_VOLTAGE);

        add(maxV);
        add(minV);
        add(diffV);
        add(totV);
        add(avgV);

        labelConstraint(maxV, this);
        labelConstraint(minV, maxV);
        labelConstraint(diffV, minV);
        labelConstraint(totV, diffV);
        labelConstraint(avgV, totV);

        maxVoltage  = new JLabel(INITIAL_VOLTAGE);
        minVoltage  = new JLabel(INITIAL_VOLTAGE);
        diffVoltage = new JLabel(INITIAL_VOLTAGE);
        totVoltage  = new JLabel(INITIAL_VOLTAGE);
        avgVoltage  = new JLabel(INITIAL_VOLTAGE);

        add(maxVoltage);
        add(minVoltage);
        add(diffVoltage);
        add(totVoltage);
        add(avgVoltage);

        valueConstraint(maxVoltage, this);
        valueConstraint(minVoltage, maxVoltage);
        valueConstraint(diffVoltage, minVoltage);
        valueConstraint(totVoltage, diffVoltage);
        valueConstraint(avgVoltage, totVoltage);
    }

    private void labelConstraint (Component comp, Component above) {
        int paddTop = paddingTop(above);

        layout.putConstraint(SpringLayout.NORTH, comp, paddTop, SpringLayout.NORTH, above);
        layout.putConstraint(SpringLayout.WEST, comp, MainInterface.PADDING, SpringLayout.WEST, this);
    }

    private void valueConstraint (Component comp, Component above) {
        int paddTop = paddingTop(above);

        layout.putConstraint(SpringLayout.NORTH, comp, paddTop, SpringLayout.NORTH, above);
        layout.putConstraint(SpringLayout.EAST, comp, MainInterface.PADDING * -1, SpringLayout.EAST, this);
    }

    private int paddingTop (Component comp) {
        int paddTop = MainInterface.PADDING * 2;

        if (comp != this)
            paddTop *= 2;

        return paddTop;
    }
}
