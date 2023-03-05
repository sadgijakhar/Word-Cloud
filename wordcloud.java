package com.example.wordcloud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class WordCloud extends Application {

    private final Random random = new Random();
    private final List<Color> colors = Arrays.asList(
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE);

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(WordCloud.class.getResource("WordCloud.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 500, 500);
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color:  #B0E0E6  ");

        Label label = new Label("Enter some text:");
        label.setStyle("-fx-text-fill:  #DDA0DD ;");
        label.setFont(new Font(24));
        TextArea textArea = new TextArea();
        Button button = new Button("Generate Word Cloud");
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        FlowPane wordCloudPane = new FlowPane();
        wordCloudPane.setPadding(new Insets(10));
        wordCloudPane.setHgap(10);
        wordCloudPane.setVgap(10);

        button.setOnAction(e -> {
            String text = textArea.getText();
            Map<String, Integer> frequencyMap = createFrequencyMap(text);
            createWordCloud(frequencyMap, wordCloudPane);
        });

        root.getChildren().addAll(label, textArea, button, wordCloudPane);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private Map<String, Integer> createFrequencyMap(String text) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        String[] words = text.split("\\s+");
        for (String word : words) {
            String cleanedWord = word.toLowerCase().replaceAll("[^a-z]", "");
            if (!cleanedWord.isEmpty()) {
                frequencyMap.put(cleanedWord, frequencyMap.getOrDefault(cleanedWord, 0) + 1);
            }
        }
        return frequencyMap;
    }

    private void createWordCloud(Map<String, Integer> frequencyMap, FlowPane wordCloudPane) {
        List<String> sortedWords = new ArrayList<>(frequencyMap.keySet());
        sortedWords.sort((w1, w2) -> frequencyMap.get(w2) - frequencyMap.get(w1));

        int maxFrequency = frequencyMap.get(sortedWords.get(0));
        double scaleFactor = 1.0 / maxFrequency;

        wordCloudPane.getChildren().clear();

        for (String word : sortedWords) {
            int frequency = frequencyMap.get(word);
            double fontSize = 24 + Math.log10(frequency) * 8;
            Text text = new Text(word);
            text.setFont(Font.font("Arial", fontSize));
            text.setFill(colors.get(random.nextInt(colors.size())));
            text.setStyle("-fx-font-weight: bold;");
            text.setOnMouseClicked(e -> {
                System.out.println(word + " was clicked!");
            });
            wordCloudPane.getChildren().add(text);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
