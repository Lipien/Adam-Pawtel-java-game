package com.kodilla;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Cell extends Pane {
    private char player = ' ';

    public Cell() {
        this.setStyle("-fx-border-color: black");
        this.setPrefSize(300, 400);
    }

    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) {
        this.player = player;
        if (player == 'X') {
            Line line1 = new Line(30, 30, this.getWidth() - 30, this.getHeight() - 30);
            line1.setStrokeWidth(10);
            line1.setStroke(Color.GREEN);
            Line line2 = new Line(30, this.getHeight() - 30, this.getWidth() - 30, 30);
            line2.setStrokeWidth(10);
            line2.setStroke(Color.GREEN);

            getChildren().addAll(line1, line2);

        } else if (player == 'O') {
            Circle circle = new Circle(35);
            circle.centerXProperty().bind(this.widthProperty().divide(2));
            circle.centerYProperty().bind(this.heightProperty().divide(2));
            circle.setStrokeWidth(10);
            circle.setStroke(Color.GREEN);
            circle.setFill(null);

            getChildren().add(circle);
        }
    }
}