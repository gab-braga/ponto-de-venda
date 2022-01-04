module ponto.de.venda {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;

    opens view;
    opens controller;
    opens model;
}