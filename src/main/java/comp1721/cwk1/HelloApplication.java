package comp1721.cwk1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.temporal.ChronoUnit.DAYS;

public class HelloApplication extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    // Returns an integer containing today's game number

    public int gameNum () {

        LocalDate firstGame = LocalDate.of(2021, 6, 19);
        LocalDate todayDate = LocalDate.now();

        long dayDiff = DAYS.between(firstGame, todayDate);
        return Math.toIntExact(dayDiff);
    }


    // Method to build the GUI.

    @Override
    public void start(Stage stage) throws Exception {

        FlowPane titlePane = new FlowPane(Orientation.HORIZONTAL, 20, 20);
        titlePane.setLayoutY(0);

        FlowPane buttonPane = new FlowPane(Orientation.HORIZONTAL, 20, 20);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        buttonPane.setLayoutY(500);

        FlowPane boxPane = new FlowPane(Orientation.HORIZONTAL, 20, 20);
        boxPane.setPadding(new Insets(10, 10, 10, 10));
        boxPane.setLayoutY(0);
        boxPane.setHgap(5);
        boxPane.setVgap(5);

        StackPane one = new StackPane();
        one.getChildren().add(boxPane);
        SubScene subSceneOne = new SubScene(one, 440, 550);

        StackPane two = new StackPane();
        two.getChildren().add(buttonPane);
        SubScene subSceneTwo = new SubScene(two, 800, 250);

        StackPane three = new StackPane();
        three.getChildren().add(titlePane);
        SubScene subSceneThree = new SubScene(three, 300, 75);

        Label titleLabel = new Label("Wordle: " + gameNum());
        titleLabel.setFont(Font.font(50));
        titleLabel.setAlignment(Pos.CENTER);
        titlePane.getChildren().add(titleLabel);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(subSceneThree, subSceneOne, subSceneTwo);

        Scene scene = new Scene(root, 750, 1000);

        stage.setTitle("Wordle!");
        stage.setWidth(825);
        stage.setHeight(1000);
        stage.setResizable(false);

        Button enter = new Button("ENTER");
        enter.setMinSize(140, 60);
        enter.setFont(Font.font(20));
        enter.setDisable(true);
        Button delete = new Button("DELETE");
        delete.setMinSize(140, 60);
        delete.setFont(Font.font(20));

        AtomicInteger labelCount = new AtomicInteger();
        AtomicInteger enterCount = new AtomicInteger();

        /*
           Loop to create buttons and labels.
           Also determines what the buttons do on click.
        */

        for (char a = 'A'; a <= 'Z'; a++) {
            Button letter = new Button("" + a);
            letter.setMinSize(60, 60);
            letter.setFont(Font.font(20));
            buttonPane.getChildren().add(letter);
            letter.setOnMouseClicked(event -> {
                letter.setStyle("-fx-background-color: #FFAADD;");
                if (labelCount.get() < 5) {
                    Label labels = new Label("");
                    labels.setMinSize(80, 80);
                    labels.setStyle("-fx-border-color: black;");
                    boxPane.getChildren().add(labels);
                    labelCount.getAndIncrement();
                    if (labelCount.get() == 5) {
                        enter.setDisable(false);
                    }
                }
                if (labelCount.get() < 10 && enterCount.get() == 1) {
                    Label labels = new Label("");
                    labels.setMinSize(80, 80);
                    labels.setStyle("-fx-border-color: black;");
                    boxPane.getChildren().add(labels);
                    labelCount.getAndIncrement();
                    if (labelCount.get() == 10) {
                        enter.setDisable(false);
                    }
                }
                if (labelCount.get() < 15 && enterCount.get() == 2) {
                    Label labels = new Label("");
                    labels.setMinSize(80, 80);
                    labels.setStyle("-fx-border-color: black;");
                    boxPane.getChildren().add(labels);
                    labelCount.getAndIncrement();
                    if (labelCount.get() == 15) {
                        enter.setDisable(false);
                    }
                }
                if (labelCount.get() < 20 && enterCount.get() == 3) {
                    Label labels = new Label("");
                    labels.setMinSize(80, 80);
                    labels.setStyle("-fx-border-color: black;");
                    boxPane.getChildren().add(labels);
                    labelCount.getAndIncrement();
                    if (labelCount.get() == 20) {
                        enter.setDisable(false);
                    }
                }
                if (labelCount.get() < 25 && enterCount.get() == 4) {
                    Label labels = new Label("");
                    labels.setMinSize(80, 80);
                    labels.setStyle("-fx-border-color: black;");
                    boxPane.getChildren().add(labels);
                    labelCount.getAndIncrement();
                    if (labelCount.get() == 25) {
                        enter.setDisable(false);
                    }
                }
            });
        }

        buttonPane.getChildren().add(enter);
        buttonPane.getChildren().add(delete);

        enter.setOnMouseClicked((event -> {
            enterCount.getAndIncrement();
            enter.setDisable(true);
        }));
        delete.setOnMouseClicked((event -> {

        }));

        stage.setScene(scene);
        stage.show();

    }

}