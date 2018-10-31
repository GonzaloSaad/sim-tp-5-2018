package utn.frc.sim.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.simulation.SimulationFinishedException;
import utn.frc.sim.simulation.SimulationWrapper;
import utn.frc.sim.util.Fila;

public class SimulationController {

    private static final Logger logger = LogManager.getLogger(SimulationController.class);
    private static final int MAX_SIMULATION = 30;

    @FXML
    private TableColumn<Fila, String> event;
    @FXML
    private TableColumn<Fila, String> clock;
    @FXML
    private TableColumn<Fila, String> trucks;
    @FXML
    private TableColumn<Fila, String> nextArrival;
    @FXML
    private TableColumn<Fila, String> stateReception;
    @FXML
    private TableColumn<Fila, String> truckRec;
    @FXML
    private TableColumn<Fila, String> endRec;
    @FXML
    private TableColumn<Fila, String> queueRec;
    @FXML
    private TableColumn<Fila, String> stateBal;
    @FXML
    private TableColumn<Fila, String> truckBal;
    @FXML
    private TableColumn<Fila, String> endBal;
    @FXML
    private TableColumn<Fila, String> queueBal;
    @FXML
    private TableColumn<Fila, String> stateDar1;
    @FXML
    private TableColumn<Fila, String> truckDar1;
    @FXML
    private TableColumn<Fila, String> endDar1;
    @FXML
    private TableColumn<Fila, String> stateDar2;
    @FXML
    private TableColumn<Fila, String> truckDar2;
    @FXML
    private TableColumn<Fila, String> endDar2;
    @FXML
    private TableColumn<Fila, String> queueDar;
    @FXML
    private TableColumn<Fila, String> truckServed;
    @FXML
    private TableColumn<Fila, String> day;
    @FXML
    private TableColumn<Fila, String> avg;
    @FXML
    private TableColumn<Fila, String> truckXDayServed;

    @FXML
    private TableView<Fila> tvSim;

    @FXML
    private AnchorPane panelSim1;

    @FXML
    private Button semiautomatic;

    private SimulationWrapper simulation;
    private ObservableList<Fila> data;

    @FXML
    public void initialize() {
        resetSimulation();
    }

    @FXML
    void btnRunClick(ActionEvent event) {
        runSimulationToEnd();
    }

    @FXML
    void btnRunSemiClick(ActionEvent event) {
        runOneStepOfSimulation();
    }

    private void runSimulationToEnd() {
        disableSemiautomaticButton();
        runSimulation(Boolean.TRUE);
    }

    private void runOneStepOfSimulation() {
        runSimulation(Boolean.FALSE);
    }

    @FXML
    void btnReset(ActionEvent event) {
        resetSimulation();
    }

    private void resetSimulation() {
        logger.info("Force ending the current simulation. Restart was requested.");
        enableSemiautomaticButton();
        clearItemsInTableView();
        initializeNewSimulation();
    }

    private void clearItemsInTableView() {
        tvSim.getItems().clear();
    }

    private void disableSemiautomaticButton() {
        semiautomatic.setDisable(true);
    }

    private void enableSemiautomaticButton() {
        semiautomatic.setDisable(false);
    }

    private void initializeNewSimulation() {
        logger.info("Initializing simulation of {} days.", MAX_SIMULATION);
        simulation = SimulationWrapper.ofType(MainMenuController.getType(), MAX_SIMULATION);
        data = FXCollections.observableArrayList();
    }

    private void runSimulation(boolean auto) {

        if (auto) {
            while (true) {
                try {
                    runOneStepAndAddToTable();
                } catch (SimulationFinishedException e) {
                    logger.info("Simulation finished.");
                    break;
                }
            }
        } else {
            try {
                runOneStepAndAddToTable();
            } catch (SimulationFinishedException e) {
                logger.info("Simulation finished.");
            }
        }
    }

    private void runOneStepAndAddToTable() throws SimulationFinishedException {
        simulation.step();
        loadTable();
    }

    private void loadTable() {

        String eventContent = simulation.getLastEvent();
        String clockContent = simulation.getClock();
        String trucksContent = simulation.getNumberClient();
        String nextArrivalContent = simulation.getNextClientEvent();
        String stateReceptionContent = simulation.getRecepcionState();
        String truckRecContent = simulation.getRecepcionClient();
        String endRecContent = simulation.getRecepcionNextEvent();
        String queueRecContent = simulation.getRecepcionQueueLenght();
        String stateBalContent = simulation.getBalanzaState();
        String truckBalContent = simulation.getBalanzaClient();
        String endBalContent = simulation.getBalanzaNextEvent();
        String queueBalContent = simulation.getBalanzaQueueLenght();
        String stateDar1Content = simulation.getDarsena1State();
        String truckDar1Content = simulation.getDarsena1Client();
        String endDar1Content = simulation.getgetDarsena1NextEvent();
        String stateDar2Content = simulation.getDarsena2State();
        String truckDar2Content = simulation.getDarsena2Client();
        String endDar2Content = simulation.getgetDarsena2NextEvent();
        String queueDarContent = simulation.getDarsenaQueueLenght();
        String truckServedContent = simulation.getNumberOfTrucksServed();
        String dayContent = simulation.getDay();
        String avgContent = simulation.getAverageDurationOfService();
        String truckXDayServedContent = simulation.getTrucksServedPerDay();

        data.addAll(new Fila(eventContent, clockContent, trucksContent, nextArrivalContent, stateReceptionContent,
                truckRecContent, endRecContent, queueRecContent, stateBalContent, truckBalContent, endBalContent,
                queueBalContent, stateDar1Content, truckDar1Content, endDar1Content, stateDar2Content,
                truckDar2Content, endDar2Content, queueDarContent, truckServedContent, dayContent, avgContent,
                truckXDayServedContent));

        event.setCellValueFactory(new PropertyValueFactory<>("event"));
        clock.setCellValueFactory(new PropertyValueFactory<>("clock"));
        trucks.setCellValueFactory(new PropertyValueFactory<>("trucks"));
        nextArrival.setCellValueFactory(new PropertyValueFactory<>("nextArrival"));
        stateReception.setCellValueFactory(new PropertyValueFactory<>("stateReception"));
        truckRec.setCellValueFactory(new PropertyValueFactory<>("truckRec"));
        endRec.setCellValueFactory(new PropertyValueFactory<>("endRec"));
        queueRec.setCellValueFactory(new PropertyValueFactory<>("queueRec"));
        stateBal.setCellValueFactory(new PropertyValueFactory<>("stateBal"));
        truckBal.setCellValueFactory(new PropertyValueFactory<>("truckBal"));
        endBal.setCellValueFactory(new PropertyValueFactory<>("endBal"));
        queueBal.setCellValueFactory(new PropertyValueFactory<>("queueBal"));
        stateDar1.setCellValueFactory(new PropertyValueFactory<>("stateDar1"));
        truckDar1.setCellValueFactory(new PropertyValueFactory<>("truckDar1"));
        endDar1.setCellValueFactory(new PropertyValueFactory<>("endDar1"));
        stateDar2.setCellValueFactory(new PropertyValueFactory<>("stateDar2"));
        truckDar2.setCellValueFactory(new PropertyValueFactory<>("truckDar2"));
        endDar2.setCellValueFactory(new PropertyValueFactory<>("endDar2"));
        queueDar.setCellValueFactory(new PropertyValueFactory<>("queueDar"));
        truckServed.setCellValueFactory(new PropertyValueFactory<>("truckServed"));
        day.setCellValueFactory(new PropertyValueFactory<>("day"));
        avg.setCellValueFactory(new PropertyValueFactory<>("asd"));
        truckXDayServed.setCellValueFactory(new PropertyValueFactory<>("truckXDayServed"));

        tvSim.setItems(data);
    }
}
