package views.interfaces;

import exceptions.PostException;

//Interface for PieChart
public interface PieChartViewInterface {
	
	void updatePieChartData(boolean onlyCurrentUser) throws PostException;
}
