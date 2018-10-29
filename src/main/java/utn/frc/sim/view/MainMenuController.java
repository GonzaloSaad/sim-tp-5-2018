package utn.frc.sim.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.simulation.SimulationType;
import utn.frc.sim.simulation.SimulationWrapper;

import java.io.IOException;

public class MainMenuController {


    public static SimulationType type;
    @FXML
        private Pane paneMain;
        @FXML
        private MenuItem miSim1;
        @FXML
        private MenuItem miSim2;



        private static Logger logger = LogManager.getLogger(MainMenuController.class);


        @FXML
        void openSimulation1(ActionEvent event) {
            setSimulationDialog();
           // simulation
            type = SimulationType.Type2;
        }

        /**
         * Metodo que maneja el evento de click sobre
         * la opcion de la prueba de chi-cuadrado.
         */
        @FXML
        void openSimulation12(ActionEvent event) {
            setSimulation2Dialog();
            type = SimulationType.Type1;
        }

        /**
         * Metodo que maneja el evento de click sobre
         * la opcion de volver a pantalla de info.
         *
         */
     @FXML
         void openEssayInfoDialog(ActionEvent event) {
            setEssayInfoDialog();
        }

        /**
         * Accion de setear la vista de la lista de
         * generacion de numeros aleatorios al
         * panel principal
         **/
        private void setSimulationDialog() {
            try {
                paneMain.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("/views/sim1.fxml")));
            } catch (IOException e) {
                logger.error("Problem opening SimulacionDialog.", e);
            }
        }

        /**
         * Accion de setear la vista de la el test
         * de chi cuadrado al panel principal.
         */
        private void setSimulation2Dialog() {
            try {
                paneMain.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("/views/sim1.fxml")));
            } catch (IOException e) {
                logger.error("Problem opening AutomaticDialog.", e);
            }
        }

    private void setEssayInfoDialog() {
        try {
            paneMain.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource("/views/menu/essay_info.fxml")));
        } catch (IOException e) {
            logger.error("Problem opening EssayInfoDialog.", e);
        }
    }


    public static SimulationType getType(){
            return type;
    }


}
