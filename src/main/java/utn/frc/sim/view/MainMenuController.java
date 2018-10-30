package utn.frc.sim.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.simulation.SimulationType;

import java.io.IOException;

public class MainMenuController {


    private static SimulationType type;

    @FXML
    private Pane paneMain;

    private static Logger logger = LogManager.getLogger(MainMenuController.class);

    @FXML
    void openSimulation1(ActionEvent event) {
        setSimulationDialog();

        type = SimulationType.Type1;
    }

    @FXML
    void openSimulation12(ActionEvent event) {
        setSimulation2Dialog();
        type = SimulationType.Type2;
    }

    @FXML
    void openEssayInfoDialog(ActionEvent event) {
        setEssayInfoDialog();
    }

    private void setSimulationDialog() {
        try {
            paneMain.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("/views/simulations/sim1.fxml")));
        } catch (IOException e) {
            logger.error("Problem opening Simulacion Dialog.", e);
        }
    }


    private void setEssayInfoDialog() {
        try {
            paneMain.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("/views/menu/essay_info.fxml")));
        } catch (IOException e) {
            logger.error("Problem opening EssayInfoDialog.", e);
        }
    }


    private void setSimulation2Dialog() {
        try {
            paneMain.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("/views/simulations/sim1.fxml")));
        } catch (IOException e) {
            logger.error("Problem opening AutomaticDialog.", e);
        }
    }

    public static SimulationType getType() {
        return type;
    }


}
