package views;

import java.util.List;

import controllers.PostController;
import controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Post;
import models.User;
import views.facade.GUIViewFacade;
import views.facade.GUIViewFacadeInterface;
import views.interfaces.PieChartViewInterface;

public class PieChartView extends BaseView implements PieChartViewInterface {

    private Stage stage;
    private User user;
    private PieChart sharesPieChart;
    private Button backButton, myPostsButton, allPostsButton;
    private VBox layout;
    
    private GUIViewFacadeInterface viewFacade;

    public PieChartView(Stage stage, User user, UserController userController, PostController postController) {
        this.user = user;
        this.stage = stage;
        this.viewFacade = new GUIViewFacade(stage, userController, postController);
        initializeComponents();
        show();
    }
    
    @Override
    protected void initializeComponents() {

        Label titleLabel = new Label("Posts Shares Distribution");
        titleLabel.setTextFill(Color.GREEN);
        titleLabel.setFont(Font.font("Arial", 20));
        
        myPostsButton = new Button("My Posts");
        myPostsButton.setOnAction(e -> updatePieChartData(true));

        allPostsButton = new Button("All Posts");
        allPostsButton.setOnAction(e -> updatePieChartData(false));
        
        HBox buttonBox = new HBox(10, myPostsButton, allPostsButton);
        buttonBox.setAlignment(Pos.CENTER);
        
        sharesPieChart = new PieChart();  // Initializing the PieChart
    	
        // Displaying "My Posts" by default
        updatePieChartData(true);

        backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> {
            // Return to the DashboardView
            viewFacade.navigateToDashboard(user);
        });

        layout = new VBox(10, titleLabel, buttonBox, sharesPieChart, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 30, 20));
        
    }
    
    @Override
    protected void show() {
    	Scene scene = new Scene(layout, 700, 700);
        stage.setScene(scene);
        stage.setTitle("Posts Shares Distribution");
        stage.show();
    }
    
    @Override
    public void updatePieChartData(boolean onlyCurrentUser) {
        List<Post> posts;
        if (onlyCurrentUser) {
            posts = viewFacade.getPostsByUser(user);
        } else {
            posts = viewFacade.getAllPosts();
        }

        int count0to99 = 0;
        int count100to999 = 0;
        int count1000Plus = 0;

        for (Post post : posts) {
            if (post.getShares() >= 0 && post.getShares() <= 99) {
                count0to99++;
            } else if (post.getShares() >= 100 && post.getShares() <= 999) {
                count100to999++;
            } else if (post.getShares() >= 1000) {
                count1000Plus++;
            }
        }
        
        ObservableList<PieChart.Data> newData = FXCollections.observableArrayList(
                new PieChart.Data("0-99 Shares:", count0to99),
                new PieChart.Data("100-999 Shares:", count100to999),
                new PieChart.Data("1000+ Shares:", count1000Plus)
            );
            
        // Clear the current data and add the new data
        sharesPieChart.getData().clear();
        sharesPieChart.getData().addAll(newData);
            
        // Add percentages to pie chart slices
        final ObservableList<PieChart.Data> chartData = sharesPieChart.getData();
        chartData.forEach(data ->
            data.nameProperty().bind(
                javafx.beans.binding.Bindings.concat(
            		data.getName(), " ", 
                    javafx.beans.binding.Bindings.format("%d", data.pieValueProperty().intValue()), " (", // Format as integer
                    javafx.beans.binding.Bindings.format("%.1f%%",
                        data.pieValueProperty().doubleValue() / chartData.stream().mapToDouble(PieChart.Data::getPieValue).sum() * 100),
                    ")"
                )
            )
        );
    }

}
