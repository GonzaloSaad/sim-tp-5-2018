package utn.frc.sim;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utn.frc.sim.simulation.Simulation;
import utn.frc.sim.simulation.SimulationWrapper;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
        SimulationWrapper simulation = new SimulationWrapper(new Simulation());
        for (int i = 0; i < 30000; i++) {
            simulation.step();
        }
        System.out.println(simulation.getAverageDurationOfService());
    }
}
