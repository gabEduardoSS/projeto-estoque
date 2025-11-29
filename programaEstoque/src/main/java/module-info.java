module projeto.estoque.programaestoque {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;
    requires mysql.connector.j;
    requires javafx.base;

    opens projeto.estoque.programaestoque to javafx.fxml;
    exports projeto.estoque.programaestoque;
}