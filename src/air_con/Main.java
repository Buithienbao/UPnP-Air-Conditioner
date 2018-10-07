package air_con;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static View view;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/air_con/sample.fxml"));
        Parent root = fxmlLoader.load();

        view = fxmlLoader.getController();
        Controller controller = new Controller(view);
        view.setController(controller);
        primaryStage.setTitle("Air conditioner remote");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
