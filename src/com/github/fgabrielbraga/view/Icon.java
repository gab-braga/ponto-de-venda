package com.github.fgabrielbraga.view;

import javafx.scene.image.Image;

import java.net.URI;
import java.net.URISyntaxException;

public class Icon {

    private URI uri;
    private Image image;

    public Icon() {
        startURI();
        startImage();
    }

    private void startURI() {
        URI uri = null;
        try {
            uri = Login.class.getResource("/com/github/fgabrielbraga/view/images/logo-pdv.png").toURI();
        } catch (URISyntaxException e) {
            uri = null;
        } finally {
            this.uri = uri;
        }
    }

    private void startImage() {
        if(uri != null) {
            image = new Image(uri.toString());
        }
    }

    public Image getImage() {
        return this.image;
    }
}
