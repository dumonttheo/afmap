module fr.afpa.afmap {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens fr.afpa.afmap.controllers to javafx.fxml;
    opens fr.afpa.afmap to javafx.fxml;
    exports fr.afpa.afmap;
    exports fr.afpa.afmap.controllers to javafx.fxml;
}