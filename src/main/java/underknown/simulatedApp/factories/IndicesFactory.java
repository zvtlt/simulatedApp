package underknown.simulatedApp.factories;

import java.util.ArrayList;
import java.util.List;

import underknown.simulatedApp.models.IndicesModel;

public class IndicesFactory {

	private static IndicesFactory indicesFactory = null;
	private final List<IndicesModel> indicesModelList = new ArrayList<>();

	private IndicesFactory() {
	}

	public static IndicesFactory getInstance() {
		if (indicesFactory == null) {
			indicesFactory = new IndicesFactory();
		}

		return indicesFactory;
	}

	public List<IndicesModel> getIndicesModelList() {
		return indicesModelList;
	}

	public boolean add(final String name, final String code, final String currency) {
		final IndicesModel indicesModel = new IndicesModel(name, code, currency);
		boolean alreadyAdded = false;
		for (final IndicesModel currentIndicesModel : indicesModelList) {
			if (currentIndicesModel.getName().equals(name)) {
				alreadyAdded = true;
			}
		}

		if (alreadyAdded) {
			return false;
		} else {
			return indicesModelList.add(indicesModel);
		}
	}

	public void remove(final String name) {
		for (int i = 0; i < indicesModelList.size(); i++) {
			if (indicesModelList.get(i).getName().equals(name)) {
				indicesModelList.remove(i);
			}
		}
	}
	
}
