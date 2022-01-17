module ponto.de.venda {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens com.github.fgabrielbraga.view;
    opens com.github.fgabrielbraga.controller;
    opens com.github.fgabrielbraga.model;
    opens com.github.fgabrielbraga.controller.util;
}