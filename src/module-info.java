/**
 * 
 */
/**
 * 
 */
module VendorConnectMain {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires javafx.web;
	requires java.desktop;
	requires org.jxmapviewer.jxmapviewer2;

    // Expose packages for JavaFX
    opens controller to javafx.fxml;

    // Export other packages to make them accessible
    exports controller;
    exports model;
    exports view;
    exports main;
}
