package utn.frc.sim;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
<<<<<<< HEAD

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;
=======
>>>>>>> 2a142fae72e8f3039806a55b0021b51cc6c7acac

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
<<<<<<< HEAD
        Parent root = FXMLLoader.load(getClass().getResource("/views/menu/main-menu.fxml"));
        primaryStage.setTitle("Hello CIDS");
        primaryStage.setScene(new Scene(root, 1024, 600));
        primaryStage.setOnCloseRequest(e -> forceClose());
=======
        Parent root = FXMLLoader.load(getClass().getResource("/views/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
>>>>>>> 2a142fae72e8f3039806a55b0021b51cc6c7acac
        primaryStage.show();
    }

    private static void forceClose() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
<<<<<<< HEAD
       // SimulationWrapper simulation = SimulationWrapper.ofType(SimulationType.Type1);
        //for (int i = 0; i < 50; i++) {
          //  simulation.step();
       // }

=======
        /*SimulationWrapper simulation = SimulationWrapper.ofType(SimulationType.Type1);
        for (int i = 0; i < 30000; i++) {
            simulation.step();
        }*/
>>>>>>> 2a142fae72e8f3039806a55b0021b51cc6c7acac
    }
}
