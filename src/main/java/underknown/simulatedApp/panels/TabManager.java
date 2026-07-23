package underknown.simulatedApp.panels;


import javax.swing.*;

import underknown.simulatedApp.panels.tabs.CalculatePercentTab;
import underknown.simulatedApp.panels.tabs.IndiceValuesTab;
import underknown.simulatedApp.panels.tabs.InterestTab;
import underknown.simulatedApp.panels.tabs.RateTab;

import java.awt.*;
import java.awt.event.*;

public class TabManager extends JPanel {

	private static final long serialVersionUID = -250857855515196097L;

	private final static String INTEREST_TAB = "Interets";
	private final static String RATE_TAB = "Rate";
	private final static String INDICE_VALUE_TAB = "Valeurs d'indices";
	private final static String CALCULATE_PERCENT_TAB = "Calcul de pourcentage";
	private final static Color SELECTED_BACKGROUND = new Color(255, 255, 255);
	private final static Color SELECTED_BORDER = new Color(200, 200, 200);
	private final static Color SELECTED_TEXT = new Color(40, 40, 40);
	private final static Color UNSELECTED_BACKGROUND = new Color(240, 240, 240);
	private final static Color UNSELECTED_BORDER = new Color(210, 210, 210);
	private final static Color UNSELECTED_TEXT = new Color(90, 90, 90);
	private final InterestTab interestTab;
	private final RateTab rateTab;
	private final IndiceValuesTab indiceValuesTab;
	private final CalculatePercentTab calculatePercentTab;
	private final JPanel interestTabPanel = new JPanel(new BorderLayout());
	private final JLabel interestTabText = new JLabel(INTEREST_TAB);
	private final JPanel rateTabPanel = new JPanel(new BorderLayout());
	private final JLabel rateTabText = new JLabel(RATE_TAB);
	private final JPanel indiceValuesTabPanel = new JPanel(new BorderLayout());
	private final JLabel indiceValuesTabText = new JLabel(INDICE_VALUE_TAB);
	private final JPanel calculatePercentTabPanel = new JPanel(new BorderLayout());
	private final JLabel calculatePercentTabText = new JLabel(CALCULATE_PERCENT_TAB);
	private boolean isInterestTabSelected = true;
	private boolean isRateTabSelected = false;
	private boolean isIndiceValuesTabSelected = false;
	private boolean isCalculatePercentTabSelected = false;

	public TabManager() {
		super();
		interestTab = new InterestTab(this);
		rateTab = new RateTab();
		indiceValuesTab = new IndiceValuesTab(this);
		calculatePercentTab = new CalculatePercentTab();

		// layout for InterestTab
		setLayout(new BorderLayout(20,15));
		initializeTabs();
		interestTab.initializeInterestTab();
		rateTab.initializeRateTab();
		indiceValuesTab.initializeIndiceValuesTab();
		calculatePercentTab.initializeCalculatePercentTab();
	}

	private void initializeSpecificTab(final JPanel jpanel, final JLabel jlabel) {
		jlabel.setHorizontalAlignment(SwingConstants.CENTER);
		jpanel.add(jlabel, BorderLayout.CENTER);
		jpanel.setBackground(SELECTED_BACKGROUND);
		jpanel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(SELECTED_BORDER),
						BorderFactory.createEmptyBorder(8, 25, 8, 25)
						)
				);
		jlabel.setForeground(SELECTED_TEXT);

		final Dimension tabSize = new Dimension(200, 45);
		jpanel.setPreferredSize(tabSize);

		jpanel.addMouseListener(new tabActionListener());
	}

	private void initializeTabs() {
		final JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
		tabsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		initializeSpecificTab(interestTabPanel, interestTabText);
		initializeSpecificTab(rateTabPanel, rateTabText);
		initializeSpecificTab(indiceValuesTabPanel, indiceValuesTabText);
		initializeSpecificTab(calculatePercentTabPanel, calculatePercentTabText);

		rateTabText.setForeground(UNSELECTED_TEXT);
		indiceValuesTabText.setForeground(UNSELECTED_TEXT);
		calculatePercentTabText.setForeground(UNSELECTED_TEXT);

		tabsPanel.add(interestTabPanel);
		tabsPanel.add(rateTabPanel);
		tabsPanel.add(indiceValuesTabPanel);
		tabsPanel.add(calculatePercentTabPanel);

		add(tabsPanel, BorderLayout.NORTH);
	}

	private class tabActionListener implements MouseListener {

		@Override
		public void mouseEntered(final MouseEvent e) {
			final JPanel panel = (JPanel) e.getSource();
			final JLabel label = (JLabel) panel.getComponent(0);
			label.setForeground(Color.BLUE);
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			final JPanel panel = (JPanel) e.getSource();
			final JLabel label = (JLabel) panel.getComponent(0);

			if (label.equals(interestTabText) && isInterestTabSelected) {
				updateForeground(interestTabText, rateTabText, indiceValuesTabText, calculatePercentTabText);
			} else if (label.equals(rateTabText) && isRateTabSelected) {
				updateForeground(rateTabText, interestTabText, indiceValuesTabText, calculatePercentTabText);
			} else if (label.equals(indiceValuesTabText) && isIndiceValuesTabSelected) {
				updateForeground(indiceValuesTabText, interestTabText, rateTabText, calculatePercentTabText);
			} else if (label.equals(calculatePercentTabText) && isCalculatePercentTabSelected) {
				updateForeground(calculatePercentTabText, interestTabText, rateTabText, indiceValuesTabText);
			} else {
				label.setForeground(UNSELECTED_TEXT);
			}

		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			final JPanel panel = (JPanel) e.getSource();
			final JLabel label = (JLabel) panel.getComponent(0);
			if (label.getText().equals(INTEREST_TAB) && !isInterestTabSelected) {
				interestUpdateFormat();
				interestUpdatePanels();
				updateForeground(interestTabText, rateTabText, indiceValuesTabText, calculatePercentTabText);
			} else if (label.getText().equals(RATE_TAB) && !isRateTabSelected) {
				rateUpdateFormat();
				rateUpdatePanels();
				updateForeground(rateTabText, interestTabText, indiceValuesTabText, calculatePercentTabText);
			} else if (label.getText().equals(INDICE_VALUE_TAB) && !isIndiceValuesTabSelected) {
				indiceValuesUpdateFormat();
				indiceValuesUpdatePanels();
				updateForeground(indiceValuesTabText, interestTabText, rateTabText, calculatePercentTabText);
			} else if (label.getText().equals(CALCULATE_PERCENT_TAB) && !isCalculatePercentTabSelected) {
				calculatePercentUpdateFormat();
				calculatePercentUpdatePanels();
				updateForeground(calculatePercentTabText, interestTabText, rateTabText, indiceValuesTabText);
			}
		}

		@Override
		public void mousePressed(final MouseEvent e) {
			// Do nothing
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			// Do nothing
		}

	}

	private void updateForeground (final JLabel selectedText, final JLabel unselectedText1, final JLabel unselectedText2, final JLabel unselectedText3) {
		selectedText.setForeground(SELECTED_TEXT);
		unselectedText1.setForeground(UNSELECTED_TEXT);
		unselectedText2.setForeground(UNSELECTED_TEXT);
		unselectedText3.setForeground(UNSELECTED_TEXT);
	}

	private void tabUpdateFormat(final JPanel selectedPanel, final JPanel unselectedPanel1, final JPanel unselectedPanel2, final JPanel unselectedPanel3) {
		// Set panel with selected format
		selectedPanel.setBackground(SELECTED_BACKGROUND);
		selectedPanel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(SELECTED_BORDER),
						BorderFactory.createEmptyBorder(8, 25, 8, 25)
						)
				);

		// Set panel with unselected format
		unselectedPanel1.setBackground(UNSELECTED_BACKGROUND);
		unselectedPanel1.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(UNSELECTED_BORDER),
						BorderFactory.createEmptyBorder(8, 25, 8, 25)
						)
				);

		// Set panel with unselected format
		unselectedPanel2.setBackground(UNSELECTED_BACKGROUND);
		unselectedPanel2.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(UNSELECTED_BORDER),
						BorderFactory.createEmptyBorder(8, 25, 8, 25)
						)
				);
		// Set panel with unselected format
		unselectedPanel3.setBackground(UNSELECTED_BACKGROUND);
		unselectedPanel3.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(UNSELECTED_BORDER),
						BorderFactory.createEmptyBorder(8, 25, 8, 25)
						)
				);
	}

	private void interestUpdateFormat() {
		// Set interest panel with selected format
		tabUpdateFormat(interestTabPanel, rateTabPanel, indiceValuesTabPanel, calculatePercentTabPanel);
		isInterestTabSelected = true;
		isRateTabSelected = false;
		isIndiceValuesTabSelected = false;
		isCalculatePercentTabSelected = false;
	}

	private void rateUpdateFormat() {
		// Set rate panel with unselected format
		tabUpdateFormat(rateTabPanel, interestTabPanel, indiceValuesTabPanel, calculatePercentTabPanel);
		isInterestTabSelected = false;
		isRateTabSelected = true;
		isIndiceValuesTabSelected = false;
		isCalculatePercentTabSelected = false;
	}

	private void indiceValuesUpdateFormat() {
		// Set indiceValues panel with unselected format
		tabUpdateFormat(indiceValuesTabPanel, interestTabPanel, rateTabPanel, calculatePercentTabPanel);
		isInterestTabSelected = false;
		isRateTabSelected = false;
		isIndiceValuesTabSelected = true;
		isCalculatePercentTabSelected = false;
	}

	private void calculatePercentUpdateFormat() {
		// Set indiceValues panel with unselected format
		tabUpdateFormat(calculatePercentTabPanel, interestTabPanel, rateTabPanel, indiceValuesTabPanel);
		isInterestTabSelected = false;
		isRateTabSelected = false;
		isIndiceValuesTabSelected = false;
		isCalculatePercentTabSelected = true;
	}

	private void interestUpdatePanels() {
		removeAll();
		initializeTabs();

		add(interestTab.getMainPanel(), BorderLayout.CENTER);
		add(interestTab.getBottomPanel(), BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

	private void rateUpdatePanels() {
		removeAll();
		initializeTabs();

		add(rateTab.getIndicePanel(), BorderLayout.CENTER);
		add(rateTab.getTablePanel(), BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

	public void indiceValuesUpdatePanels() {
		removeAll();
		initializeTabs();

		add(indiceValuesTab.getIndicePanel(), BorderLayout.CENTER);
		add(indiceValuesTab.getBottomPanel(), BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

	public void calculatePercentUpdatePanels() {
		removeAll();
		initializeTabs();

		add(calculatePercentTab.getMainPanel(), BorderLayout.CENTER);
		add(calculatePercentTab.getBottomPanel(), BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

}


