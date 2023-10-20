package enums;

// Filter feature for posts
public enum FilterBy {
	My_posts("My Posts"),
	All_posts("All Posts");
	
	private String filterBy;

	private FilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	
	/**
	 * @return the filterBy
	 */
	public String getFilterBy() {
		return filterBy;
	}

	/**
	 * @param filterBy the filterBy to set
	 */
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
}
