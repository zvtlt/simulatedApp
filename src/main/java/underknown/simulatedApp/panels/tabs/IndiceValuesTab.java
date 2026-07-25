package underknown.simulatedApp.panels.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import underknown.simulatedApp.factories.IndicesFactory;
import underknown.simulatedApp.models.IndicesModel;
import underknown.simulatedApp.models.IndicesObjectsModel;
import underknown.simulatedApp.panels.TabManager;
import underknown.simulatedApp.utils.ComboBoxUtils;
import underknown.simulatedApp.utils.PriceUtils;

public class IndiceValuesTab {

	private final static String EURO_CURRENCY = "EURO";
	private final static String USD_CURRENCY = "USD";

	private final TabManager controller;
	private final JPanel indicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
	private final JPanel indiceValuesPanel = new JPanel();
	private final JPanel bottomPanel = new JPanel();
	private final JLabel warningLabel = new JLabel("");
	private final JTextField indiceNameTextField = new JTextField("");
	private final JTextField indiceCodeTextField = new JTextField("");
	private final JComboBox<String> currencyComboBox = new JComboBox<>(new String[] {EURO_CURRENCY, USD_CURRENCY});

	public IndiceValuesTab(final TabManager controller) {
		this.controller = controller;
	}

	public void initializeIndiceValuesTab() {
		indicePanel.setLayout(new BorderLayout(10, 10));
		indicePanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));

		indicePanel.add(initializeTopPanel(), BorderLayout.BEFORE_FIRST_LINE);
		indiceValuesPanel.setLayout(new BoxLayout(indiceValuesPanel, BoxLayout.Y_AXIS));
		indicePanel.add(indiceValuesPanel, BorderLayout.CENTER);

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
		final JPanel mainPanel = new JPanel(new GridLayout(2, 1));

		final JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

		final JLabel name = new JLabel("Nom");
		indiceNameTextField.setColumns(15);

		final JLabel code = new JLabel("Code");
		indiceCodeTextField.setColumns(15);

		final JLabel currency = new JLabel("Devise");

		final JButton addButton = new JButton("Ajouter à la liste");

		firstPanel.add(name);
		firstPanel.add(indiceNameTextField);
		firstPanel.add(code);
		firstPanel.add(indiceCodeTextField);
		firstPanel.add(currency);
		firstPanel.add(currencyComboBox);
		firstPanel.add(addButton);

		final JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

		final JButton removeButton = new JButton("Retirer l'élément séléctionné de la liste");

		secondPanel.add(ComboBoxUtils.getInstance().getComboBox());
		secondPanel.add(removeButton);

		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);

		addButton.addActionListener(new addActionListener());
		removeButton.addActionListener(new removeActionListener());

		return mainPanel;
	}

	private class addActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			final String name = indiceNameTextField.getText();
			final String code = indiceCodeTextField.getText();
			final String currency = currencyComboBox.getSelectedItem().toString();
			if (!name.isEmpty() && !code.isEmpty() && !currency.isEmpty()) {
				if (IndicesFactory.getInstance().getIndicesModelList().isEmpty()) {
					ComboBoxUtils.getInstance().getComboBox().removeAllItems();
				}
				if (IndicesFactory.getInstance().add(name, code, currency)) {
					ComboBoxUtils.getInstance().getComboBox().addItem(name);
				}

				refreshIndiceValuesPanel();
				
				controller.indiceValuesUpdatePanels();
			} else {
				warningLabel.setText("Veuillez entrer un nom et un code pour l'ajouter à la liste");
			}
		}

	}

	private class removeActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			final Object selectedItem = ComboBoxUtils.getInstance().getComboBox().getSelectedItem();
			if (selectedItem != null) {
				IndicesFactory.getInstance().remove((String) selectedItem);

				ComboBoxUtils.getInstance().getComboBox().removeItem(selectedItem);

				refreshIndiceValuesPanel();

				controller.indiceValuesUpdatePanels();
			}

			if (ComboBoxUtils.getInstance().getComboBox().getItemCount() == 0) {
				ComboBoxUtils.getInstance().setDefaultMessage();
			}
		}

	}

	private void refreshIndiceValuesPanel() {
		indicePanel.removeAll();
		indiceValuesPanel.removeAll();
		bottomPanel.removeAll();
		
		IndicesModel currentIndicesModel = null;
		try {
			for (final IndicesModel indicesModel : IndicesFactory.getInstance().getIndicesModelList()) {
				currentIndicesModel = indicesModel;
				final IndicesObjectsModel indicesObjectModel = new IndicesObjectsModel(indicesModel);
				final String price = PriceUtils.getInstance().getIndiceValue(indicesModel.getCode());
				final StringBuilder priceStringBuilder = new StringBuilder(price);
				priceStringBuilder.append(" ");
				if (indicesModel.getCurrency().equals(EURO_CURRENCY)) {
					priceStringBuilder.append(EURO_CURRENCY);
				} else {
					priceStringBuilder.append(USD_CURRENCY);
				}
				indicesObjectModel.getLabelValue().setText(priceStringBuilder.toString());
				indiceValuesPanel.add(indicesObjectModel.getJpanel());
				
				warningLabel.setText("");
			}
		} catch (final IOException ioE) {
			warningLabel.setText(ioE.getMessage());
		} catch (final InterruptedException iE) {
			warningLabel.setText(iE.getMessage());
		} catch (final NullPointerException iE) {
			final int comboboxsize = ComboBoxUtils.getInstance().getComboBox().getItemCount();
			ComboBoxUtils.getInstance().getComboBox().removeItemAt(comboboxsize - 1);
			IndicesFactory.getInstance().getIndicesModelList().remove(currentIndicesModel);
			warningLabel.setText(iE.getMessage());
		}

		initializeIndiceValuesTab();
	}

	private class refreshActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshIndiceValuesPanel();
			controller.indiceValuesUpdatePanels();
		}

	}

	public JPanel getIndicePanel() {
		return indicePanel;
	}

	public JPanel getBottomPanel() {
		return bottomPanel;
	}

}
