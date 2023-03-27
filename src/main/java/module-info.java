module fr.afpa.afmap {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;

    opens fr.afpa.afmap.controllers to javafx.fxml;
    opens fr.afpa.afmap to javafx.fxml;
    opens fr.afpa.afmap.model to javafx.base;
    opens fr.afpa.afmap.controllers.popup to javafx.fxml;
    exports fr.afpa.afmap;
    exports fr.afpa.afmap.model;
    exports fr.afpa.afmap.controllers to javafx.fxml;
    exports fr.afpa.afmap.controllers.popup to javafx.fxml;
}