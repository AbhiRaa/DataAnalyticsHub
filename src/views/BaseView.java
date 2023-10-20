package views;

/**
* An abstract base class that provides a foundation for all views in the application.
* Any view that inherits from this class must provide implementations for initializing 
* UI components and displaying the view.
* 
* This class ensures a consistent structure and lifecycle for views, allowing for 
* a uniform approach to GUI construction and display.
*/
public abstract class BaseView {
	
   /**
    * Initializes UI components, layouts, and other necessary elements for the view.
    * Derived classes must provide a concrete implementation of this method to set up their UI components.
    * 
    * Any errors or exceptions that occur during the initialization of components can be 
    * logged and/or handled in the derived classes' implementations.
    */
   protected abstract void initializeComponents();
   
   /**
    * Sets the scene and makes the stage visible, thus displaying the view to the user.
    * Derived classes must provide a concrete implementation of this method to display their view.
    * 
    * Any errors or exceptions that occur while setting the scene or displaying the view can be 
    * logged and/or handled in the derived classes' implementations.
    */
   protected abstract void show();
}
