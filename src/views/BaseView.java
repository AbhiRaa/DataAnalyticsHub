package views;

public abstract class BaseView {
	
	// The initializeComponents() method remains responsible for creating and initializing components, layouts, and other UI elements.
	protected abstract void initializeComponents();
	
	// The show() method is now responsible for setting the scene and making the stage visible.
	protected abstract void show();
}
