package underknown.simulatedApp.panels.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;

import underknown.simulatedApp.factories.IndicesFactory;
import underknown.simulatedApp.models.IndicesModel;
import underknown.simulatedApp.utils.ComboBoxUtils;
import underknown.simulatedApp.utils.PriceUtils;

public class CalculatePercentTab {

	private final static String[] COLUMS_TITLES = {"Nom", "Valeur", "Pourcentage", "Nombre d'action"};

	private final JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
	private JPanel topPanel = new JPanel();
	private final JPanel bottomPanel = new JPanel();
	private final JLabel warningLabel = new JLabel("");
	private final JTextField capitalTextField = new JTextField("");
	private JSpinner spinner;
	private DefaultTableModel model;
	private JComboBox<String> duplicatedComboBox;
	private JTable table;
	private JScrollPane tablePanel = initializeTablePanel();
	private final List<String> list = new ArrayList<>();

	public CalculatePercentTab() {
	}

	public void initializeCalculatePercentTab() {
		mainPanel.setLayout(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		mainPanel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);
		mainPanel.add(tablePanel, BorderLayout.CENTER);

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

		warningLabel.setForeground(new Color(255, 44, 44));
		warningLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		final JButton refresh = new JButton("Rafraichir");
		refresh.setAlignmentX(Component.CENTER_ALIGNMENT);

		bottomPanel.add(Box.createVerticalStrut(50));
		bottomPanel.add(refresh);
		bottomPanel.add(Box.createVerticalStrut(20));
		bottomPanel.add(warningLabel);

		refresh.addActionListener(new refreshActionListener());
	}

	private JPanel initializeTopPanel() {
		final JLabel euro = new JLabel("€");
		euro.setForeground(Color.GRAY);

		final JPanel mainPanel = new JPanel(new GridLayout(3, 1));

		final JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		final JPanel capitalTextFieldPanel = new JPanel(new BorderLayout());
		final JLabel capital = new JLabel("Capital");
		capitalTextField.setHorizontalAlignment(JTextField.CENTER);
		capitalTextField.setPreferredSize(new Dimension(120, 28));
		capitalTextFieldPanel.add(capitalTextField, BorderLayout.CENTER);
		capitalTextFieldPanel.add(euro, BorderLayout.EAST);

		firstPanel.add(capital);
		firstPanel.add(capitalTextFieldPanel);

		final JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		initializeSpinner();
		final JButton assignPercentButton = new JButton("Assigner un pourcentage");
		
		duplicatedComboBox = ComboBoxUtils.getInstance().getDuplicatedComboBox();
		secondPanel.add(duplicatedComboBox);
		secondPanel.add(spinner);
		secondPanel.add(assignPercentButton);

		final JPanel thirdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		final JButton addButton = new JButton("Ajouter");
		final JButton removeButton = new JButton("Retirer");

		thirdPanel.add(addButton);
		thirdPanel.add(removeButton);

		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);
		mainPanel.add(thirdPanel);

		assignPercentButton.addActionListener(new assignPercentActionListener());
		addButton.addActionListener(new addActionListener());
		removeButton.addActionListener(new removeActionListener());

		return mainPanel;
	}

	private JScrollPane initializeTablePanel() {
		model = new DefaultTableModel(COLUMS_TITLES, 0);
		table = new JTable(model);
		table.getTableHeader().setBorder(
				BorderFactory.createLineBorder(Color.GRAY)
				);
		table.getTableHeader().setBackground(new Color(230, 230, 230));
		table.getTableHeader().setForeground(Color.BLACK);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(800, 300));
		return scrollPane;
	}

	private void initializeSpinner() {
		final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1.0, 1.0, 100.0, 0.1);
		spinner = new JSpinner(spinnerModel);
		final JFormattedTextField textField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		final DefaultFormatter formatter = (DefaultFormatter) textField.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(false);
	}

	private void refreshTopPanel() {
		mainPanel.removeAll();
		topPanel.removeAll();
		tablePanel.removeAll();
		bottomPanel.removeAll();

		topPanel = initializeTopPanel();
		tablePanel = initializeTablePanel();

		initializeCalculatePercentTab();
	}

	private void refreshTable(final boolean isRefresh) {
		if (!list.isEmpty() && updateList(isRefresh)) {
			updateTable();
		}
	}

	private boolean updateList(final boolean isRefresh) {
		final float capitalValue = getCapitalValue();
		final Object comboBoxSelectedItem = getComboBoxSelectedItem();
		if (ComboBoxUtils.DEFAULT_MESSAGE.equals(comboBoxSelectedItem)) {
			list.clear();
			warningLabel.setText("Veuillez ajouter des éléments à la liste");
			return false;
		} else if (capitalValue == -1) {
			warningLabel.setText("Veuillez entrer un capital en valeur numéraire");
			return false;
		} else {
			try {
				for (final IndicesModel indicesModel : getIndicesModelList()) {
					final String indiceName = indicesModel.getName();
					if (isRefresh || indiceName.equals(comboBoxSelectedItem)) {
						final float indicePercent = indicesModel.getPercent();
						if (indicePercent == 0.0) {
							final StringBuilder errorMessage = new StringBuilder();
							errorMessage.append("Veuillez assigner un pourcentage pour l'indice '")
								.append(indiceName)
								.append("'");
							warningLabel.setText(errorMessage.toString());
							return false;
						} else {
							indicesModel.setPrice(PriceUtils.getInstance().getIndiceValue(indicesModel.getCode()));
							final float indicePrice = indicesModel.getPrice();
							final int capitalAllocatedToThisAction = (int) (capitalValue * indicePercent / 100f);
							final int actionCount = (int) (capitalAllocatedToThisAction / indicePrice);
							indicesModel.setActionCount(actionCount);

							if (!list.contains(indiceName)) {
								list.add(indiceName);
							}
							
						}
					}
				}
			} catch (final NullPointerException npE) {
				warningLabel.setText(npE.getMessage());
				return false;
			} catch (final IOException ioE) {
				warningLabel.setText(ioE.getMessage());
				return false;
			} catch (final InterruptedException iE) {
				warningLabel.setText(iE.getMessage());
				return false;
			}
		}

		return true;
	}

	private void updateTable() {
		model.setRowCount(0);

		for (int i = 0; i < list.size(); i++) {
			final String name = list.get(i);
			final List<IndicesModel> indicesModelList = getIndicesModelList();
			for (final IndicesModel indicesModel : indicesModelList) {
				final String indiceName = indicesModel.getName();
				if (indicesModel.getName().equals(name)) {
					final float indicePercent = indicesModel.getPercent();
					final float indicePrice = indicesModel.getPrice();
					final int actionCount = indicesModel.getActionCount();

					final StringBuilder indicePriceStringBuilder = new StringBuilder();
					indicePriceStringBuilder.append(indicePrice);
					if (indicesModel.getCurrency().equals("EURO")) {
						indicePriceStringBuilder.append(" €");
					} else {
						indicePriceStringBuilder.append(" $");
					}

					final String indiceFormattedPercent = String.format("%.2f %%", indicePercent);

					model.addRow(new Object[]{indiceName, indicePriceStringBuilder.toString(), indiceFormattedPercent, actionCount});
				}
			}
		}

	}

	private float getCapitalValue() {
		try {
			final float value = Float.parseFloat(capitalTextField.getText().trim());

			return Math.round(value * 1000f) / 1000f;
		} catch (final NumberFormatException e) {
			return -1;
		}
	}

	private float getSpinnerValue() {
		return ((Double) spinner.getValue()).floatValue();
	}

	private List<IndicesModel> getIndicesModelList() {
		return IndicesFactory.getInstance().getIndicesModelList();
	}

	private Object getComboBoxSelectedItem() {
		return duplicatedComboBox.getSelectedItem();
	}

	private class addActionListener implements ActionListener{

		@Override
		public void actionPerformed(final ActionEvent e) {
			if (updateList(false)) {
				updateTable();
			}
		}

	}

	private class removeActionListener implements ActionListener{

		@Override
		public void actionPerformed(final ActionEvent e) {
			final int selectedRow = table.getSelectedRow();

			if (selectedRow != -1) {
				final String name = (String) model.getValueAt(selectedRow, 0);
				list.remove(name);

				updateTable();

				warningLabel.setText("");
			} else {
				warningLabel.setText("Veuillez séléctionner une ligne à supprimer dans le tableau.");
			}
		}

	}

	private class assignPercentActionListener implements ActionListener{

		@Override
		public void actionPerformed(final ActionEvent e) {
			final  List<IndicesModel> indicesModelList = getIndicesModelList();
			final float spinnerValue = getSpinnerValue();
			String indiceName = "";
			if (indicesModelList.isEmpty()) {
				warningLabel.setText("Veuillez ajouter des éléments à la liste");
			} else {
				for (final IndicesModel indicesModel : indicesModelList) {
					if (indicesModel.getName().equals(getComboBoxSelectedItem())) {
						indiceName = indicesModel.getName();
						indicesModel.setPercent(spinnerValue);
					}
				}
				warningLabel.setText("");
			}

			if (list.contains(indiceName) && updateList(false)) {
				updateTable();
			}
		}

	}

	private class refreshActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshTable(true);
		}

	}

	public JPanel getMainPanel() {
		refreshTopPanel();
		refreshTable(false);
		return mainPanel;
	}

	public JPanel getBottomPanel() {
		return bottomPanel;
	}

}
