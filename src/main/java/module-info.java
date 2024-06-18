module com.example.spotiffy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

//    requires org.controlsfx.controls;
//    requires org.kordamp.bootstrapfx.core;
//    requires com.almasb.fxgl.all;

    opens com.example.spotiffy to javafx.fxml;
    exports com.example.spotiffy;
}