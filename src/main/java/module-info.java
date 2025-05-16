module com.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.sql;
    requires com.jfoenix;
    requires java.desktop;
    
    // Apache POI dependencies
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    
    // ControlsFX
    requires org.controlsfx.controls;
    
    // ZXing dependencies for QR Code generation
    requires com.google.zxing;
    requires com.google.zxing.javase;
    
    // Jakarta Mail dependencies
    requires jakarta.mail;
    requires org.eclipse.angus.mail;
    requires jbcrypt;

    opens com.esprit.controllers to javafx.fxml;
    opens com.esprit.models to javafx.base;
    
    exports com.esprit.controllers;
    exports com.esprit.models;
} 