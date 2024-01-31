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
    requires mysql.connector.j;

    exports org.example.mealplannerfx.coloredscreen;
    opens org.example.mealplannerfx.coloredscreen to javafx.fxml;
    exports org.example.mealplannerfx.control;
    opens org.example.mealplannerfx.control to javafx.fxml;
    exports org.example.mealplannerfx.bwscreen;
    opens org.example.mealplannerfx.bwscreen to javafx.fxml;
    exports org.example.mealplannerfx.dao;
    opens org.example.mealplannerfx.dao to javafx.fxml;
    opens org.example.mealplannerfx.entity to javafx.fxml;
    exports org.example.mealplannerfx.entity;
    exports org.example.mealplannerfx.dao.fs;
    opens org.example.mealplannerfx.dao.fs to javafx.fxml;
    exports org.example.mealplannerfx.dao.db;
    opens org.example.mealplannerfx.dao.db to javafx.fxml;
}