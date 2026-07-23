package underknown.simulatedApp.panels.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import underknown.simulatedApp.panels.TabManager;

public class InterestTab {

	private final static String EURO = "€";
	private final static String PERCENT = "%";
	private final static String MONTHS = "Mois";
	private final static String YEARS = "années";
	private final static String DEFAULT_EURO_VALUE = "X €";
	private final static String STARTING_VALUE = "Valeur de départ";
	private final static String FOR = "Pendant";
	private final static String RATE = "Taux";
	private final static String CONTRIBUTION = "Contribution";
	private final static String EVERY = "Tous les";

	private final TabManager controller;
	private final JPanel mainPanel = new JPanel();
	private final JPanel bottomPanel = new JPanel();
	private final JTextField startingValueLeftTextField = new JTextField("0");
	private final JTextField durationTextField = new JTextField("1");
	private final JTextField interestRateTextField = new JTextField("0");
	private final JTextField contributionTextField = new JTextField("0");
	private final JComboBox<String> overTimeComboBox = new JComboBox<>(new String[] {MONTHS, YEARS});
	private final JLabel startingValueRightValue = new JLabel(DEFAULT_EURO_VALUE);
	private final JLabel totalContributionValue = new JLabel(DEFAULT_EURO_VALUE);
	private final JLabel totalInterestValue = new JLabel(DEFAULT_EURO_VALUE);
	private final JLabel totalValue = new JLabel(DEFAULT_EURO_VALUE);
	private final JLabel warningLabel = new JLabel();

	public InterestTab(final TabManager controller) {
		this.controller = controller;
	}

	public void initializeInterestTab() {
		mainPanel.add(initializeLeftPanel());
		mainPanel.add(initializeRightPanel());
		mainPanel.setLayout(new GridLayout(1, 2, 60, 0));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(200, 10, 10, 10));

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		final JButton calculate = new JButton("Calculate");
		warningLabel.setForeground(new Color(255, 44,44));

		calculate.setAlignmentX(Component.CENTER_ALIGNMENT);
		warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		bottomPanel.add(Box.createVerticalStrut(50));
		bottomPanel.add(calculate);
		bottomPanel.add(Box.createVerticalStrut(20));
		bottomPanel.add(warningLabel);


		controller.add(mainPanel, BorderLayout.CENTER);
		controller.add(bottomPanel, BorderLayout.SOUTH);

		calculate.addActionListener(new calculateActionListener());
	}

	private JPanel initializeLeftPanel() {
		final JLabel euro1 = new JLabel(EURO);
		euro1.setForeground(Color.GRAY);
		final JLabel euro2 = new JLabel(EURO);
		euro2.setForeground(Color.GRAY);
		final JLabel percent = new JLabel(PERCENT);
		percent.setForeground(Color.GRAY);
		final JLabel years = new JLabel(YEARS);
		years.setForeground(Color.GRAY);

		final JPanel mainLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
		mainLeftPanel.setLayout(new GridLayout(5,2));

		final JPanel startingValueLeftPanel = new JPanel(new BorderLayout());
		final JLabel startingValueLeft = new JLabel(STARTING_VALUE);
		startingValueLeftTextField.setHorizontalAlignment(JTextField.CENTER);
		startingValueLeftPanel.add(startingValueLeftTextField, BorderLayout.CENTER);
		startingValueLeftPanel.add(euro1, BorderLayout.EAST);

		final JPanel durationPanel = new JPanel(new BorderLayout());
		final JLabel duration = new JLabel(FOR);
		durationTextField.setHorizontalAlignment(JTextField.CENTER);
		durationTextField.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.GRAY),
						BorderFactory.createEmptyBorder(0, 34, 0, 0)
						)
				);
		durationPanel.add(durationTextField, BorderLayout.CENTER);
		durationPanel.add(years, BorderLayout.EAST);

		final JPanel interestRatePanel = new JPanel(new BorderLayout());
		final JLabel interestRate = new JLabel(RATE);
		interestRateTextField.setHorizontalAlignment(JTextField.CENTER);
		interestRatePanel.add(interestRateTextField, BorderLayout.CENTER);
		interestRatePanel.add(percent, BorderLayout.EAST);

		final JPanel contributionPanel = new JPanel(new BorderLayout());
		final JLabel contribution = new JLabel(CONTRIBUTION);
		contributionTextField.setHorizontalAlignment(JTextField.CENTER);
		contributionPanel.add(contributionTextField, BorderLayout.CENTER);
		contributionPanel.add(euro2, BorderLayout.EAST);

		final JLabel overTime = new JLabel(EVERY);

		mainLeftPanel.add(startingValueLeft);
		mainLeftPanel.add(startingValueLeftPanel);
		mainLeftPanel.add(duration);
		mainLeftPanel.add(durationPanel);
		mainLeftPanel.add(interestRate);
		mainLeftPanel.add(interestRatePanel);
		mainLeftPanel.add(contribution);
		mainLeftPanel.add(contributionPanel);
		mainLeftPanel.add(overTime);
		mainLeftPanel.add(overTimeComboBox);

		return mainLeftPanel;
	}

	private JPanel initializeRightPanel() {
		final JPanel mainRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0,0));
		mainRightPanel.setLayout(new GridLayout(6,1));

		final JPanel resultPanel = new JPanel(new BorderLayout());
		final JLabel result = new JLabel("Resultat");
		result.setBackground(controller.getBackground());
		resultPanel.add(result, BorderLayout.WEST);

		final JPanel EmptyPanel = new JPanel(new BorderLayout());

		final JPanel startingValueRightPanel = new JPanel(new BorderLayout());
		final JLabel startingValueRight = new JLabel("Valeur de départ");
		startingValueRight.setBackground(controller.getBackground());
		startingValueRightValue.setBackground(controller.getBackground());
		startingValueRightPanel.add(startingValueRight, BorderLayout.WEST);
		startingValueRightPanel.add(startingValueRightValue, BorderLayout.EAST);

		final JPanel totalContributionPanel = new JPanel(new BorderLayout());
		final JLabel totalContribution = new JLabel("Contribution total");
		totalContribution.setBackground(controller.getBackground());
		totalContributionValue.setBackground(controller.getBackground());
		totalContributionPanel.add(totalContribution, BorderLayout.WEST);
		totalContributionPanel.add(totalContributionValue, BorderLayout.EAST);

		final JPanel totalInterestPanel = new JPanel(new BorderLayout());
		final JLabel totalInterest = new JLabel("Total d'interet");
		totalInterest.setBackground(controller.getBackground());
		totalInterestValue.setBackground(controller.getBackground());
		totalInterestPanel.add(totalInterest, BorderLayout.WEST);
		totalInterestPanel.add(totalInterestValue, BorderLayout.EAST);

		final JPanel totalPanel = new JPanel(new BorderLayout());
		final JLabel total = new JLabel("Total");
		total.setBackground(controller.getBackground());
		totalValue.setBackground(controller.getBackground());
		totalPanel.add(total, BorderLayout.WEST);
		totalPanel.add(totalValue, BorderLayout.EAST);

		mainRightPanel.add(resultPanel);
		mainRightPanel.add(EmptyPanel);
		mainRightPanel.add(startingValueRightPanel);
		mainRightPanel.add(totalContributionPanel);
		mainRightPanel.add(totalInterestPanel);
		mainRightPanel.add(totalPanel);

		return mainRightPanel;
	}

	private class calculateActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkTextFieldPassedAndError()) {
				startingValueRightValue.setText(getStartingValueRightValue());
				totalContributionValue.setText(getTotalContributionValue());
				totalInterestValue.setText(getTotalInterestString());
				totalValue.setText(getTotalValue());
				warningLabel.setText("");
			}
		}

	}

	private String getStartingValueRightValue() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getStartingValueLeftTextField())
		.append(" ")
		.append(EURO);
		return sb.toString();
	}

	private String getTotalContributionValue() {
		final StringBuilder sb = new StringBuilder();

		if (getContributionTextField() == -1 || getDurationTextField() == -1) {
			return totalContributionValue.getText();
		}

		sb.append(getContributionPerYear() * getDurationTextField())
		.append(" ")
		.append(EURO);

		return sb.toString();
	}

	private String getTotalInterestString() {
		final StringBuilder sb = new StringBuilder();

		if (getContributionTextField() == -1 || getDurationTextField() == -1 || getInterestRateTextField() == -1) {
			return totalInterestValue.getText();
		}

		sb.append(getInterestAfterXYears(getDurationTextField()))
		.append(" ")
		.append(EURO);

		return sb.toString();
	}

	private String getTotalValue() {
		final StringBuilder sb = new StringBuilder();

		if (getContributionTextField() == -1 || getDurationTextField() == -1 || getInterestRateTextField() == -1) {
			return totalValue.getText();
		}

		float initialContribution = getStartingValueLeftTextField();
		float totalContribution = getContributionPerYear() * getDurationTextField();
		float totalInterest = getInterestAfterXYears(getDurationTextField());
		float total = initialContribution + totalContribution + totalInterest;

		sb.append(total)
		.append(" ")
		.append(EURO);

		return sb.toString();
	}

	private float getContributionPerYear() {
		if(getOverTimeComboBox().equals(MONTHS)) {
			return getContributionTextField() * 12;
		} else {
			return getContributionTextField();
		}
	}

	private float getInterestAfterXYears(final int yearsNumber) {
		float startingValue = getContributionPerYear() + getStartingValueLeftTextField();
		float cumulatedValue = -1;
		float interestMadeThisYear = -1;
		float totalInterestMade = -1;
		for (int i = yearsNumber ; i > 0 ; i--) {
			if (i == yearsNumber) {
				interestMadeThisYear = startingValue * getInterestRateTextField();
				cumulatedValue = startingValue + interestMadeThisYear;
				totalInterestMade = interestMadeThisYear;
			} else {
				cumulatedValue = cumulatedValue + getContributionPerYear();
				interestMadeThisYear = cumulatedValue * getInterestRateTextField();
				cumulatedValue = cumulatedValue + interestMadeThisYear;
				totalInterestMade = totalInterestMade + interestMadeThisYear;
			}
		}

		return Math.round(totalInterestMade * 100f) / 100f;
	}

	private boolean checkTextFieldPassedAndError() {
		final StringBuilder sb = new StringBuilder();
		final String firstString = "Le champ '";
		final String lastString = "' doit contenir uniquement des nombres";

		if (getStartingValueLeftTextField() == -1) {
			sb.append(firstString)
			.append(STARTING_VALUE)
			.append(lastString);
			warningLabel.setText(sb.toString());
			return false;
		} else if (getDurationTextField() == -1) {
			sb.append(firstString)
			.append(FOR)
			.append(lastString);
			warningLabel.setText(sb.toString());
			return false;
		} else if (getInterestRateTextField() == -1) {
			sb.append(firstString)
			.append(RATE)
			.append(lastString);
			warningLabel.setText(sb.toString());
			return false;
		} else if (getContributionTextField() == -1) {
			sb.append(firstString)
			.append(CONTRIBUTION)
			.append(lastString);
			warningLabel.setText(sb.toString());
			return false;
		}

		return true;
	}

	private int getContributionTextField() {
		try {
			return Integer.parseInt(contributionTextField.getText());
		} catch (final NumberFormatException e) {
			return -1;
		}
	}

	private int getDurationTextField() {
		try {
			return Integer.parseInt(durationTextField.getText());
		} catch (final NumberFormatException e) {
			return -1;
		}
	}

	private float getInterestRateTextField() {
		try {
			return Integer.parseInt(interestRateTextField.getText()) / 100f;
		} catch (final NumberFormatException e) {
			return -1;
		}
	}

	private String getOverTimeComboBox() {
		return (String) overTimeComboBox.getSelectedItem();
	}

	private int getStartingValueLeftTextField() {
		try {
			return Integer.parseInt(startingValueLeftTextField.getText());
		} catch (final NumberFormatException e) {
			return -1;
		}
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JPanel getBottomPanel() {
		return bottomPanel;
	}

}
