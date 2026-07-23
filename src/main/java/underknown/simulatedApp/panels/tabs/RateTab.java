package underknown.simulatedApp.panels.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class RateTab {

	private final static String[] COLUMS_TITLES = {"Valeur", "Interet Calculé"};

	private final JPanel indicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
	private final JTextField valueTextField = new JTextField("0");
	private final JPanel tablePanel = new JPanel();
	private DefaultTableModel model;
	private JTable table;
	private final List<Float> list = new ArrayList<>();
	private final JLabel resultLabel = new JLabel();
	private final JLabel warningLabel = new JLabel();

	public RateTab() {
	}

	public void initializeRateTab() {
		indicePanel.setLayout(new BorderLayout(10, 10));
		indicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		indicePanel.add(initializeIndiceSelectionPanel(), BorderLayout.NORTH);
		indicePanel.add(initializeTabletPanel(), BorderLayout.CENTER);

		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

		resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		warningLabel.setForeground(new Color(255, 44, 44));
		warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		final JButton calculate = new JButton("Calculer la moyenne d'interet");
		calculate.setAlignmentX(Component.CENTER_ALIGNMENT);

		tablePanel.add(calculate);
		tablePanel.add(Box.createVerticalStrut(20));
		tablePanel.add(resultLabel);
		tablePanel.add(Box.createVerticalStrut(20));
		tablePanel.add(warningLabel);

		calculate.addActionListener(new calculateActionListener());
	}

	private JPanel initializeIndiceSelectionPanel() {		
		final JPanel indiceSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		final JLabel indiceSelectText = new JLabel("Séléctionnez une valeur incrémentale à ajouter pour calculer l'interet par année");
		final Dimension dimension = new Dimension(120, 28);
		valueTextField.setPreferredSize(dimension);
		valueTextField.setMaximumSize(dimension);
		valueTextField.setHorizontalAlignment(JTextField.CENTER);

		final JButton addButton = new JButton("Ajouter");
		final JButton deleteButton = new JButton("Retirer");
		final JButton resetButton = new JButton("Réinitialiser");

		indiceSelectionPanel.add(valueTextField);
		indiceSelectionPanel.add(addButton);
		indiceSelectionPanel.add(deleteButton);
		indiceSelectionPanel.add(resetButton);
		indiceSelectionPanel.add(Box.createHorizontalStrut(10));
		indiceSelectionPanel.add(indiceSelectText);

		addButton.addActionListener(new addActionListener());
		deleteButton.addActionListener(new removeActionListener());
		resetButton.addActionListener(new resetActionListener());

		return indiceSelectionPanel;
	}

	private JScrollPane initializeTabletPanel() {
		model = new DefaultTableModel(COLUMS_TITLES, 0);
		table = new JTable(model);
		table.getTableHeader().setBorder(
				BorderFactory.createLineBorder(Color.GRAY)
				);
		table.getTableHeader().setBackground(new Color(230, 230, 230));
		table.getTableHeader().setForeground(Color.BLACK);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		return new JScrollPane(table);
	}

	private class addActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			final float value = getValueTextField();
			if (value != -1) {
				list.add(value);
				updateTable();

				warningLabel.setText("");
			} else {
				warningLabel.setText("Veuillez spécifier uniquement des valeurs numéraire.");
			}
		}

	}

	private class removeActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			final int selectedRow = table.getSelectedRow();

			if (selectedRow != -1) {
				final Float value = (Float) model.getValueAt(selectedRow, 0);
				list.remove(value);
				model.removeRow(selectedRow);

				updateTable();

				warningLabel.setText("");
			} else {
				warningLabel.setText("Veuillez séléctionner une ligne à supprimer dans le tableau.");
			}
		}

	}

	private class resetActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			list.clear();

			final int modelTotalRowCount = model.getRowCount();
			for (int counter = 0; counter < modelTotalRowCount; counter++) {
				model.removeRow(0);
			}

			resultLabel.setText("");
			warningLabel.setText("");
		}

	}

	private class calculateActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			final List<Float> percentList = new ArrayList<>();
			for (final Vector<?> currentVector : model.getDataVector()) {
				if (!currentVector.get(1).equals("")) {
					final String currentPercentString = (String) currentVector.get(1);
					final int currentPercentStringSize = currentPercentString.length();
					final String formatedCurrentPercent = currentPercentString.substring(0, currentPercentStringSize - 2)
							.replace(",", ".");
					final float value = Float.parseFloat(formatedCurrentPercent.trim());

					percentList.add(value);
				}
			}

			float totalPercent = 0f;
			for (final float currentPercentFloat : percentList) {
				totalPercent += currentPercentFloat;
			}

			final float averagePercent = totalPercent / percentList.size();

			if (percentList.isEmpty()) {
				resultLabel.setText("");
			} else {
				resultLabel.setText(String.format("La valeur moyenne des interets sur toutes les années est de %.2f %%", averagePercent));
			}

			warningLabel.setText("");
		}

	}

	private void updateTable() {
		model.setRowCount(0);

		for (int i = 0; i < list.size(); i++) {

			float value = list.get(i);

			if (i == 0) {
				model.addRow(new Object[]{value, ""});
			} else {
				float oldValue = list.get(i - 1);
				float percent = ((value - oldValue) / oldValue) * 100f;

				model.addRow(new Object[]{
						value,
						String.format("%.2f %%", percent)
				});
			}
		}
	}

	private float getValueTextField() {
		try {
			final float value = Float.parseFloat(valueTextField.getText().trim());

			return Math.round(value * 1000f) / 1000f;
		} catch (final NumberFormatException e) {
			return -1;
		}
	}

	public JPanel getIndicePanel() {
		return indicePanel;
	}

	public JPanel getTablePanel() {
		return tablePanel;
	}

}
