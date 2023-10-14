package views;

import java.util.List;

import controllers.PostController;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import models.Post;
import models.User;

public class PieChartView {

    private Stage stage;
    private User user;
    private PostController postController;
    private PieChart sharesPieChart;

    public PieChartView(PostController postController, User user) {
        this.postController = postController;
        this.user = user;
        this.stage = new Stage();
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
        sharesPieChart.getData().add(slice1);
        sharesPieChart.getData().add(slice2);
        sharesPieChart.getData().add(slice3);

        Scene scene = new Scene(sharesPieChart, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Posts Shares Distribution");
        stage.show();
    }
}
