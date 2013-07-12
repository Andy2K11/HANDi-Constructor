/**
 * This package leads the hierarchy for objects to be placed on the main window
 * of the ui environment.
 * 
 * <p>It is based upon the Model - View - Controller compound design pattern. </p>
 * 
 * <p>Critical elements of this model are that the Model is able to exist independently 
 * and need not have any knowledge of the existence of the View or the Controller. </p>
 * 
 * <p>While both the model and controller are abstract, and so implement some standard
 * methods, the view component has been made an interface as views must inherit from
 * a type of JavaFX parent or node. This frees the subclass to choose exactly which
 * type of JavaFX object it will extend e.g. Region or Group depending upon purpose. </p>
 * 
 * <img src="doc-files/mvc-1.png" />
 */
package uk.co.corductive.msc.mvc;
