package com.kodilla;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class TicTacToeApp extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Tic Tac Toe (JavaFX)";
    private char currentPlayer = 'X';
    private char comp = 'O';
    private Cell[][] cell = new Cell[3][3];
    private Label statusMsg = new Label("  X has to move first");
    private RadioButton radioButton = new RadioButton("play against PC");
    private GridPane gridPane;
    private BorderPane borderPane;
    private boolean isBoardFull = false;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        initBoard();

        statusMsg.setFont(Font.font("Verdana", 20));
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.setCenter(gridPane);
        borderPane.setStyle("-fx-padding: 10");
        borderPane.setTop(statusMsg);
        borderPane.setBottom(null);

        Button newGameButton = new Button("New GAME");
        newGameButton.setPrefSize(100, 20);
        newGameButton.setDefaultButton(true);
        newGameButton.setOnAction(event -> {
            try {
                initBoard();
                currentPlayer = 'X';
                comp = 'O';
                borderPane.setCenter(gridPane);
                statusMsg.setText("  X has to move first");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button exitButton = new Button("EXIT");
        exitButton.setPrefSize(100, 20);
        exitButton.setOnAction(e -> Platform.exit());

        radioButton.setSelected(true);

        VBox buttonBar = new VBox();
        buttonBar.getChildren().addAll(newGameButton, exitButton, radioButton);
        buttonBar.setPadding(new Insets(15, 12, 15, 12));
        buttonBar.setSpacing(30);

        borderPane.setRight(buttonBar);
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initBoard() {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cell[i][j] = new Cell();
                cell[i][j].setOnMouseClicked(e -> handleClick(e));
                gridPane.add(cell[i][j], i, j);
            }
        }
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
        for (int i = 0; i < 3; i++)
            if (cell[i][0].getPlayer() == player && cell[i][1].getPlayer() == player
                    && cell[i][2].getPlayer() == player) {
                cell[i][0].setStyle("-fx-background-color: darkgray;");
                cell[i][1].setStyle("-fx-background-color: darkgray;");
                cell[i][2].setStyle("-fx-background-color: darkgray;");
                return true;
            }
        for (int i = 0; i < 3; i++) {
            if (cell[0][i].getPlayer() == player && cell[1][i].getPlayer() == player
                    && cell[2][i].getPlayer() == player) {
                cell[0][i].setStyle("-fx-background-color: darkgray;");
                cell[1][i].setStyle("-fx-background-color: darkgray;");
                cell[2][i].setStyle("-fx-background-color: darkgray;");
                return true;
            }
        }
        if (cell[0][0].getPlayer() == player && cell[1][1].getPlayer() == player
                && cell[2][2].getPlayer() == player) {
            cell[0][0].setStyle("-fx-background-color: darkgray;");
            cell[1][1].setStyle("-fx-background-color: darkgray;");
            cell[2][2].setStyle("-fx-background-color: darkgray;");
            return true;
        }
        if (cell[0][2].getPlayer() == player && cell[1][1].getPlayer() == player
                && cell[2][0].getPlayer() == player) {
            cell[0][2].setStyle("-fx-background-color: darkgray;");
            cell[1][1].setStyle("-fx-background-color: darkgray;");
            cell[2][0].setStyle("-fx-background-color: darkgray;");
            return true;
        }
        return false;
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
            } else if (isBoardFull) {
                statusMsg.setText("Draw! The game is over");
                comp = ' ';
            }
        }
        return false;
    }

    private void handleClick(MouseEvent e) {
        Cell cell = (Cell) e.getSource();
        if (radioButton.isSelected()) {
            if (cell.getPlayer() == ' ' && currentPlayer != ' ') {
                cell.setPlayer(currentPlayer);
                if (hasWon(currentPlayer)) {
                    statusMsg.setText(currentPlayer + " won!");
                    currentPlayer = ' ';
                } else if (isBoardFull) {
                    statusMsg.setText("Draw!");
                    currentPlayer = ' ';
                } else {
                    compTurn();
                }
            }
        } else {
            if (cell.getPlayer() == ' ' && currentPlayer != ' ') {
                cell.setPlayer(currentPlayer);
                if (hasWon(currentPlayer)) {
                    statusMsg.setText(currentPlayer + " won !");
                    currentPlayer = ' ';
                } else if (isBoardFull) {
                    statusMsg.setText("Draw!");
                    currentPlayer = ' ';
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    statusMsg.setText(" Now it's " + currentPlayer + "'s move");
                }
            }
        }
    }
}