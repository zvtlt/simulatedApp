package underknown.simulatedApp.models;

public class IndicesModel {

	private String name;
	private String code;
	private String currency;
	private float percent;
	private float price;
	private int actionCount;

	public IndicesModel(final String name, final String code, final String currency) {
		this.name = name;
		this.code = code;
		this.currency = currency;
	}

	public int getActionCount() {
		return actionCount;
	}

	public String getCode() {
		return code;
	}

	public String getCurrency() {
		return currency;
	}

	public String getName() {
		return name;
	}

	public float getPercent() {
		return percent;
	}

	public float getPrice() {
		return price;
	}

	public void setActionCount(final int actionCount) {
		this.actionCount = actionCount;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPercent(final float percent) {
		this.percent = percent;
	}

	public void setPrice(final String price) {
		this.price = Float.parseFloat(price);
	}

}
