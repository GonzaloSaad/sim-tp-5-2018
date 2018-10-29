package utn.frc.sim.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import utn.frc.sim.simulation.SimulationType;
import utn.frc.sim.simulation.SimulationWrapper;
import utn.frc.sim.util.Fila;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller  {


    private static  final int MAX_SIMULATION = 30;

    @FXML private TableColumn event;
    @FXML private TableColumn clock;
    @FXML private TableColumn trucks;
    @FXML private TableColumn nextArrival;
    @FXML private TableColumn stateReception;
    @FXML private TableColumn truckRec;
    @FXML private TableColumn endRec;
    @FXML private TableColumn queueRec;
    @FXML private TableColumn stateBal;
    @FXML private TableColumn truckBal;
    @FXML private TableColumn endBal;
    @FXML private TableColumn queueBal;
    @FXML private TableColumn stateDar1;
    @FXML private TableColumn truckDar1;
    @FXML private TableColumn endDar1;
    @FXML private TableColumn stateDar2;
    @FXML private TableColumn truckDar2;
    @FXML private TableColumn endDar2;
    @FXML private TableColumn queueDar;
    @FXML private TableColumn truckServed;
    @FXML private TableColumn day;
    @FXML private TableColumn avg;
    @FXML private TableColumn truckXDayServed;
    @FXML private MenuItem miSim1;

    @FXML private TableView tvSim;
    @FXML private AnchorPane panelSim1;

//    private SimulationType type = new MainMenuController().getType();
    SimulationWrapper simulation;
    ObservableList<Fila> data ;



@FXML
    void btnRunClick(ActionEvent event){
    simulation = SimulationWrapper.ofType(MainMenuController.getType());
    data  = FXCollections.observableArrayList();

    while (Integer.parseInt(simulation.getDay()) <= MAX_SIMULATION) {

        loadTable();
        simulation.step();
    }
    }

    void loadTable(){

       String event1 = simulation.getLastEvent();
        String clock1 =simulation.getClock();
        String trucks1 = simulation.getNumberClient();
        String nextArrival1=simulation.getNextClientEvent();
        String stateReception1=simulation.getRecepcionState();
        String truckRec1= simulation.getRecepcionClient();
        String endRec1=simulation.getRecepcionNextEvent();
        String queueRec1=simulation.getRecepcionQueueLenght();
        String stateBal1=simulation.getBalanzaState();
        String truckBal1=simulation.getBalanzaClient();
        String endBal1=simulation.getBalanzaNextEvent();
        String queueBal1=simulation.getBalanzaQueueLenght();
        String stateDar11=simulation.getDarsena1State();
        String truckDar11=simulation.getDarsena1Client();
        String endDar11=simulation.getgetDarsena1NextEvent();
        String stateDar21=simulation.getDarsena2State();
        String truckDar21=simulation.getDarsena2Client();
        String endDar21=simulation.getgetDarsena2NextEvent();
        String queueDar1=simulation.getDarsenaQueueLenght();
        String truckServed1=simulation.getNumberOfTrucksServed();
        String day1=simulation.getDay();
        String avg1=simulation.getAverageDurationOfService();
        String truckXDayServed1=simulation.getTrucksServedPerDay();

        data.addAll(new Fila(event1,clock1,trucks1,nextArrival1,stateReception1,
                truckRec1,endRec1,queueRec1,stateBal1,truckBal1, endBal1,
                queueBal1,stateDar11,truckDar11,  endDar11, stateDar21,
                truckDar21, endDar21, queueDar1,truckServed1, day1, avg1, truckXDayServed1));

        event.setCellValueFactory(new PropertyValueFactory<Fila,String>("event"));
        clock.setCellValueFactory(new PropertyValueFactory<Fila,String>("clock"));
        trucks.setCellValueFactory(new PropertyValueFactory<Fila,String>("trucks"));
        nextArrival.setCellValueFactory(new PropertyValueFactory<Fila,String>("nextArrival"));
        stateReception.setCellValueFactory(new PropertyValueFactory<Fila,String>("stateReception"));
        truckRec.setCellValueFactory(new PropertyValueFactory<Fila,String>("truckRec"));
        endRec.setCellValueFactory(new PropertyValueFactory<Fila,String>("endRec"));
        queueRec.setCellValueFactory(new PropertyValueFactory<Fila,String>("queueRec"));
        stateBal.setCellValueFactory(new PropertyValueFactory<Fila,String>("stateBal"));
        truckBal.setCellValueFactory(new PropertyValueFactory<Fila,String>("truckBal"));
        endBal.setCellValueFactory(new PropertyValueFactory<Fila,String>("endBal"));
        queueBal.setCellValueFactory(new PropertyValueFactory<Fila,String>("queueBal"));
        stateDar1.setCellValueFactory(new PropertyValueFactory<Fila,String>("stateDar1"));
        truckDar1.setCellValueFactory(new PropertyValueFactory<Fila,String>("truckDar1"));
        endDar1.setCellValueFactory(new PropertyValueFactory<Fila,String>("endDar1"));
        stateDar2.setCellValueFactory(new PropertyValueFactory<Fila,String>("stateDar2"));
        truckDar2.setCellValueFactory(new PropertyValueFactory<Fila,String>("truckDar2"));
        endDar2.setCellValueFactory(new PropertyValueFactory<Fila,String>("endDar2"));
        queueDar.setCellValueFactory(new PropertyValueFactory<Fila,String>("queueDar"));
        truckServed.setCellValueFactory(new PropertyValueFactory<Fila,String>("truckServed"));
        day.setCellValueFactory(new PropertyValueFactory<Fila,String>("day"));
        avg.setCellValueFactory(new PropertyValueFactory<Fila,String>("asd"));
        truckXDayServed.setCellValueFactory(new PropertyValueFactory<Fila,String>("truckXDayServed"));

        tvSim.setItems(data);
    }
}
