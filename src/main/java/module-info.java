module edu.montecarlo {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.montecarlo.gui to javafx.fxml;

    exports edu.montecarlo;
    exports edu.montecarlo.model;
    exports edu.montecarlo.experiment;
    exports edu.montecarlo.gui;
}
