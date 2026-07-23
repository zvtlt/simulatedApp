package underknown.simulatedApp.models;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IndicesObjectsModel {

	private final IndicesModel indicesModel;
	private final JPanel jpanel;
	private final JLabel label;
	private final JLabel labelValue;


	public IndicesObjectsModel(final IndicesModel indicesModel) {
		this.indicesModel = indicesModel;
		jpanel = new JPanel(new BorderLayout());
		jpanel.setBorder(BorderFactory.createEmptyBorder(0, 500, 0, 500));
		label =  new JLabel(indicesModel.getName());
		labelValue =  new JLabel();
		
		initializePanel();
	}

	private void initializePanel() {
		jpanel.add(label, BorderLayout.WEST);
		jpanel.add(labelValue, BorderLayout.EAST);
	}


	public IndicesModel getIndicesModel() {
		return indicesModel;
	}


	public JPanel getJpanel() {
		return jpanel;
	}


	public JLabel getLabel() {
		return label;
	}


	public JLabel getLabelValue() {
		return labelValue;
	}

}
