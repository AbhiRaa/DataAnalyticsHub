package enums;

public enum SortBy {
	By_Likes("By Likes"),
	By_Shares("By Shares");

	private String sortBy;

	private SortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}
