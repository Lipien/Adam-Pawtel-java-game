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
import javafx.stage.Stage;

import java.util.Random;


public class TicTacToeApp extends Application {


    private char currentPlayer = 'X';
    private char comp = 'O';

    // komórki do GridPane
    private Cell[][] cell = new Cell[3][3];
    private Label statusMsg = new Label("  X has to move first");


    @Override
    public void start(Stage primaryStage) throws Exception {
// GridPane wstawione do Center w BorderPane
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cell[i][j] = new Cell();
                pane.add(cell[i][j], j, i);
            }
        }
// BorderPane jest umieszczone w Scene
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5, 5, 5, 5));
        borderPane.setCenter(pane);
        borderPane.setTop(statusMsg);
        borderPane.setBottom(null);

// Przycisk NewGame jest do zaprogramowania
        Button newGameButton = new Button("New GAME");
        newGameButton.setPrefSize(100, 20);
//   newGameButton.setDefaultButton(true);
//   newGameButton.setOnAction(event ->


        Button exitButton = new Button("EXIT");
        exitButton.setPrefSize(100, 20);
        exitButton.setOnAction(e -> Platform.exit());

        RadioButton radioButton = new RadioButton("play against PC");
        radioButton.setSelected(true);
//      radioButton.setAlignment();


        VBox buttonBar = new VBox();
        buttonBar.getChildren().addAll(newGameButton, exitButton, radioButton);
// styl
        buttonBar.setPadding(new Insets(15, 12, 15, 12));
        buttonBar.setSpacing(30);   // Gap between nodes
        buttonBar.setStyle("-fx-background-color: #E6E6FA;");


        borderPane.setRight(buttonBar);
// Scene jest umieszczone w PrimaryStage
        Scene scene = new Scene(borderPane, 500, 400);
        primaryStage.setTitle("Tic Tac Toe (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cell[i][j].getPlayer() == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean hasWon(char player) {
        for (int i = 0; i < 3; i++) {
            if (cell[i][0].getPlayer() == player && cell[i][1].getPlayer() == player && cell[i][2].getPlayer() == player) {

                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (cell[0][i].getPlayer() == player && cell[1][i].getPlayer() == player && cell[2][i].getPlayer() == player) {
                return true;
            }
        }
        if (cell[0][0].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][2].getPlayer() == player) {
            return true;
        }
        return cell[0][2].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][0].getPlayer() == player;
    }


    public class Cell extends Pane {
        private char player = ' ';

        public char getPlayer() {
            return player;
        }

        public Cell() {
            setStyle("-fx-border-color : black");
            this.setPrefSize(300, 400);
            this.setOnMouseClicked(e -> handleClick());
        }


        //  Ustawić przełączanie radio buttonem (gra z PC)

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
                    comp = ' '; // Game is over
                } else if (isBoardFull()) {
                    statusMsg.setText("Draw! The game is over");
                    comp = ' '; // Game is over
                }
            }
            return false;
        }


        private void handleClick() {
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

// -> do połączenia z RadioButton (gra z PC)

                    compTurn();
                }
            }
        }

        public void setPlayer(char player) {
            this.player = player;


            if (player == 'X') {
                Line line1 = new Line(30, 30, this.getWidth() - 30, this.getHeight() - 30);
                line1.endXProperty().bind(this.widthProperty().subtract(30));
                line1.endYProperty().bind(this.heightProperty().subtract(30));
                line1.setStrokeWidth(10);
                line1.setStroke(Color.GREEN);

                Line line2 = new Line(30, this.getHeight() - 30, this.getWidth() - 30, 30);
                line2.endXProperty().bind(this.widthProperty().subtract(30));
                line2.startYProperty().bind(this.heightProperty().subtract(30));
                line2.setStrokeWidth(10);
                line2.setStroke(Color.GREEN);


                getChildren().addAll(line1, line2);

            } else if (player == 'O') {

                Circle circle = new Circle(40);


                circle.centerXProperty().bind(this.widthProperty().divide(2));
                circle.centerYProperty().bind(this.heightProperty().divide(2));
                circle.setStrokeWidth(10);
                circle.setStroke(Color.GREEN);
                circle.setFill(null);

                getChildren().add(circle);

            }

        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
