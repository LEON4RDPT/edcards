module com.edcards.edcards {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.smartcardio;
    requires java.desktop;

    opens com.edcards.edcards to javafx.fxml;
    exports com.edcards.edcards;
    exports com.edcards.edcards.FormControllers;
    opens com.edcards.edcards.FormControllers to javafx.fxml;
    exports com.edcards.edcards.tests;
    opens com.edcards.edcards.tests to javafx.fxml;
    exports com.edcards.edcards.NotDoneApps;
    opens com.edcards.edcards.NotDoneApps to javafx.fxml;
}