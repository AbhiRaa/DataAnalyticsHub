package views;

import java.util.List;

import controllers.PostController;
import controllers.UserController;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Post;
import models.User;

public class PieChartView {

    private Stage stage;
    private User user;
    private PostController postController;
    private UserController userController;
    private PieChart sharesPieChart;
    private Button backButton;

    public PieChartView(Stage stage, User user, UserController userController, PostController postController) {
        this.postController = postController;
        this.userController = userController;
        this.user = user;
        this.stage = stage;
        initializeComponents();
    }

    private void initializeComponents() {
        List<Post> allPosts = postController.getPostsByUser(user);

        int count0to99 = 0;
        int count100to999 = 0;
        int count1000Plus = 0;

        for (Post post : allPosts) {
            if (post.getShares() >= 0 && post.getShares() <= 99) {
                count0to99++;
            } else if (post.getShares() >= 100 && post.getShares() <= 999) {
                count100to999++;
            } else if (post.getShares() >= 1000) {
                count1000Plus++;
            }
        }

        PieChart.Data slice1 = new PieChart.Data("0-99 Shares", count0to99);
        PieChart.Data slice2 = new PieChart.Data("100-999 Shares", count100to999);
        PieChart.Data slice3 = new PieChart.Data("1000+ Shares", count1000Plus);

        sharesPieChart = new PieChart();
        sharesPieChart.getData().addAll(slice1, slice2, slice3);

        // Add percentages to pie chart slices
        final ObservableList<PieChart.Data> chartData = sharesPieChart.getData();
        chartData.forEach(data ->
            data.nameProperty().bind(
                javafx.beans.binding.Bindings.concat(
                    data.getName(), " ", data.pieValueProperty(), " (",
                    javafx.beans.binding.Bindings.format("%.1f%%",
                        data.pieValueProperty().doubleValue() / chartData.stream().mapToDouble(PieChart.Data::getPieValue).sum() * 100),
                    ")"
                )
            )
        );

        Label titleLabel = new Label("Posts Shares Distribution");
        backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> {
            // Return to the DashboardView
            new DashboardView(stage, user, postController, userController);
        });

        VBox layout = new VBox(10, titleLabel, sharesPieChart, backButton);
        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Posts Shares Distribution");
        stage.show();
    }
}
