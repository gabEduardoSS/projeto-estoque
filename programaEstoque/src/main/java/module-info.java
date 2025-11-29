module projeto.estoque.programaestoque {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens projeto.estoque.programaestoque to javafx.fxml;
    exports projeto.estoque.programaestoque;
}