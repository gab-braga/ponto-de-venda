module ponto.de.venda {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens view;
    opens controller;
    opens model;
    opens controller.util;
}