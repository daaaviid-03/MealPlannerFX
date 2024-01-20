module org.example.mealplannerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    exports org.example.mealplannerfx.coloredScreen;
    opens org.example.mealplannerfx.coloredScreen to javafx.fxml;
    exports org.example.mealplannerfx.control;
    opens org.example.mealplannerfx.control to javafx.fxml;
    exports org.example.mealplannerfx.bwScreen;
    opens org.example.mealplannerfx.bwScreen to javafx.fxml;
    exports org.example.mealplannerfx.dao;
    opens org.example.mealplannerfx.dao to javafx.fxml;
    opens org.example.mealplannerfx.entity to javafx.fxml;
    exports org.example.mealplannerfx.entity;
    exports org.example.mealplannerfx.dao.fs;
    opens org.example.mealplannerfx.dao.fs to javafx.fxml;
    exports org.example.mealplannerfx.dao.db;
    opens org.example.mealplannerfx.dao.db to javafx.fxml;
}