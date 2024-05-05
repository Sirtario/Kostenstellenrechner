module com.example.kostenstellenrechner {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.kostenstellenrechner to javafx.fxml;
    exports com.example.kostenstellenrechner;
}