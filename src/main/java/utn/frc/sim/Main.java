package utn.frc.sim;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/menu/main-menu.fxml"));
        primaryStage.setTitle("Hello CIDS");
        primaryStage.setScene(new Scene(root, 1024, 600));
        primaryStage.setOnCloseRequest(e -> forceClose());
        primaryStage.show();
    }

    private static void forceClose() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
       // SimulationWrapper simulation = SimulationWrapper.ofType(SimulationType.Type1);
        //for (int i = 0; i < 50; i++) {
          //  simulation.step();
       // }

    }
}
