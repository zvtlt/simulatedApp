package underknown.simulatedApp.utils;

import javax.swing.JComboBox;

public class ComboBoxUtils {

	public final static String DEFAULT_MESSAGE = "Veuillez ajouter un/des indice(s)";
	private static ComboBoxUtils comboBoxUtils = null;
	private final JComboBox<String> indicesNamesComboBox = new JComboBox<>(new String[] {DEFAULT_MESSAGE});
	private final JComboBox<String> duplicatedIndicesNamesComboBox = new JComboBox<>(new String[] {});

	private ComboBoxUtils() {
	}

	public static ComboBoxUtils getInstance() {
		if (comboBoxUtils == null) {
			comboBoxUtils = new ComboBoxUtils();
		}

		return comboBoxUtils;
	}

	public JComboBox<String> getComboBox() {
		return indicesNamesComboBox;
	}

	public JComboBox<String> getDuplicatedComboBox() {
		duplicatedIndicesNamesComboBox.removeAllItems();
	    for (int i = 0; i < indicesNamesComboBox.getItemCount(); i++) {
	        duplicatedIndicesNamesComboBox.addItem(indicesNamesComboBox.getItemAt(i));
	    }

	    return duplicatedIndicesNamesComboBox;
	}

	public int getDuplicatedComboBoxSelectedIndex() {
		return duplicatedIndicesNamesComboBox.getSelectedIndex();
	}

	public void setDefaultMessage() {
		indicesNamesComboBox.addItem(DEFAULT_MESSAGE);
	}
}
