module com.example.kostenstellenrechner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens com.example.kostenstellenrechner to javafx.fxml;
    exports com.example.kostenstellenrechner;
}