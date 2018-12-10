package com.kodilla;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;


public class TicTacToeApp extends Application {

    private char currentPlayer = 'X';
    private char comp = 'O';
    private Cell[][] cell = new Cell[3][3];
    private Label statusMsg = new Label("  X has to move first");

    private RadioButton radioButton = new RadioButton("play against PC");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cell[i][j] = new Cell();
                gridPane.add(cell[i][j], i, j);
            }
        }
        statusMsg.setFont(Font.font("Verdana", 20));

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setCenter(gridPane);
        borderPane.setStyle("-fx-padding: 10");
        borderPane.setTop(statusMsg);
        borderPane.setBottom(null);

        // Przycisk NewGame jest do zaprogramowania
        Button newGameButton = new Button("New GAME");
        newGameButton.setPrefSize(100, 20);
//      newGameButton.setDefaultButton(true);
//      newGameButton.setOnAction(event ->


        Button exitButton = new Button("EXIT");
        exitButton.setPrefSize(100, 20);
        exitButton.setOnAction(e -> Platform.exit());


        radioButton.setSelected(true);

        VBox buttonBar = new VBox();
        buttonBar.getChildren().addAll(newGameButton, exitButton, radioButton);
        buttonBar.setPadding(new Insets(15, 12, 15, 12));
        buttonBar.setSpacing(30);   // odstęp między przełącznikami


        borderPane.setRight(buttonBar);
        Scene scene = new Scene(borderPane, 500, 400);
        primaryStage.setTitle("Tic Tac Toe (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                if (cell[i][j].getPlayer() == ' ') {
                    return false;
                }
        }
        return true;
    }

    private boolean hasWon(char player) {
        // pionowo
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getPlayer() == player && cell[i][1].getPlayer() == player
                    && cell[i][2].getPlayer() == player) {
                cell[i][0].setStyle("-fx-background-color: darkgray;");
                cell[i][1].setStyle("-fx-background-color: darkgray;");
                cell[i][2].setStyle("-fx-background-color: darkgray;");
                return true;
            }
        // poziomo
        for (int i = 0; i < 3; i++) {
            if (cell[0][i].getPlayer() == player && cell[1][i].getPlayer() == player
                    && cell[2][i].getPlayer() == player) {
                cell[0][i].setStyle("-fx-background-color: darkgray;");
                cell[1][i].setStyle("-fx-background-color: darkgray;");
                cell[2][i].setStyle("-fx-background-color: darkgray;");
                return true;
            }
        }
        // góra dół po skosie
        if (cell[0][0].getPlayer() == player && cell[1][1].getPlayer() == player
                && cell[2][2].getPlayer() == player) {
            cell[0][0].setStyle("-fx-background-color: darkgray;");
            cell[1][1].setStyle("-fx-background-color: darkgray;");
            cell[2][2].setStyle("-fx-background-color: darkgray;");
            return true;
        }
        // dół góra po skosie
        if (cell[0][2].getPlayer() == player && cell[1][1].getPlayer() == player
                && cell[2][0].getPlayer() == player) {
            cell[0][2].setStyle("-fx-background-color: darkgray;");
            cell[1][1].setStyle("-fx-background-color: darkgray;");
            cell[2][0].setStyle("-fx-background-color: darkgray;");
            return true;
        }
        return false;
    }

    public class Cell extends Pane {
        private char player = ' ';

        public Cell() {
            this.setStyle("-fx-border-color: black");
            this.setPrefSize(300, 400);
            this.setOnMouseClicked(e -> handleClick());
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

        private boolean compTurn() {
            int see = 0;
            while (see == 0) {
                Random r = new Random();
                int i = r.nextInt(3);
                int j = r.nextInt(3);

                if (cell[i][j].getPlayer() == ' ') {
                    cell[i][j].setPlayer(comp);
                    see = 1;
                }
                if (hasWon(comp)) {
                    statusMsg.setText(comp + " won! The game is over");
                    comp = ' ';
                } else if (isBoardFull()) {
                    statusMsg.setText("Draw! The game is over");
                    comp = ' ';
                }
            }
            return false;
        }

        private void handleClick() {
            if (radioButton.isSelected()) {
                if (player == ' ' && currentPlayer != ' ') {
                    setPlayer(currentPlayer);
                    if (hasWon(currentPlayer)) {
                        statusMsg.setText(currentPlayer + " won !");
                        currentPlayer = ' ';
                    } else if (isBoardFull()) {
                        statusMsg.setText("Draw !");
                        currentPlayer = ' ';
                    } else {
                        compTurn();
                    }
                }
            } else {
                if (player == ' ' && currentPlayer != ' ') {
                    setPlayer(currentPlayer);
                    if (hasWon(currentPlayer)) {
                        statusMsg.setText(currentPlayer + " won !");
                        currentPlayer = ' ';
                    } else if (isBoardFull()) {
                        statusMsg.setText("Draw !");
                        currentPlayer = ' ';
                    } else {
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        statusMsg.setText(currentPlayer + " must play");
                    }
                }
            }
        }
    }
}