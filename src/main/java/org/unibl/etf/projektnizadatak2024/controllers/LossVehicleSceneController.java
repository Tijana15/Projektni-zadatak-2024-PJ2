package org.unibl.etf.projektnizadatak2024.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.unibl.etf.projektnizadatak2024.ConfigLoader;
import org.unibl.etf.projektnizadatak2024.lossCalculations.VehicleLossCalculate;
import org.unibl.etf.projektnizadatak2024.lossCalculations.VehicleWithLoss;
import org.unibl.etf.projektnizadatak2024.vehicle.Bike;
import org.unibl.etf.projektnizadatak2024.vehicle.Car;
import org.unibl.etf.projektnizadatak2024.vehicle.Scooter;


import java.io.FileNotFoundException;

public class LossVehicleSceneController {

    @FXML
    private TableColumn<Bike, ?> cbB;

    @FXML
    private TableColumn<Scooter, ?> cbS;

    @FXML
    private TableColumn<Car, ?> cnC;

    @FXML
    private TableColumn<Bike, ?> dB;

    @FXML
    private TableColumn<Car, ?> dnC;

    @FXML
    private TableColumn<Bike, ?> idB;

    @FXML
    private TableColumn<Car, ?> idC;

    @FXML
    private TableColumn<Scooter, ?> idS;

    @FXML
    private TableColumn<VehicleWithLoss, ?> ig1;

    @FXML
    private TableColumn<VehicleWithLoss, ?> ig2;

    @FXML
    private TableColumn<VehicleWithLoss, ?> ig3;

    @FXML
    private TableColumn<Bike, ?> mB;

    @FXML
    private TableColumn<Car, ?> mC;

    @FXML
    private TableColumn<Scooter, ?> mS;

    @FXML
    private TableColumn<Scooter, ?> mbS;

    @FXML
    private TableColumn<Car, ?> oC;

    @FXML
    private TableColumn<Bike, ?> pB;

    @FXML
    private TableColumn<Car, ?> pC;

    @FXML
    private TableColumn<Scooter, ?> pS;

    @FXML
    private TableColumn<Bike, ?> sbB;

    @FXML
    private TableColumn<Car, ?> sbC;

    @FXML
    private TableColumn<Scooter, ?> sbS;

    @FXML
    private TableView<Bike> tableViewLossB;

    @FXML
    private TableView<Car> tableViewLossC;

    @FXML
    private TableView<Scooter> tableViewLossS;
    @FXML
    private TableView<VehicleWithLoss> tw1;

    @FXML
    private TableView<VehicleWithLoss> tw2;

    @FXML
    private TableView<VehicleWithLoss> tw3;

    @FXML
    private void initialize() {
        try {
            VehicleWithLoss car = VehicleLossCalculate.deserializeVehicleWithLoss(ConfigLoader.getProperty("MAX_LOSS_CAR_PATH"));
            if (car != null) {
                idC.setCellValueFactory(new PropertyValueFactory<>("id"));
                cnC.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
                pC.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
                mC.setCellValueFactory(new PropertyValueFactory<>("model"));
                sbC.setCellValueFactory(new PropertyValueFactory<>("batteryPercentage"));
                dnC.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
                oC.setCellValueFactory(new PropertyValueFactory<>("description"));

                Car carC = (Car) car.getVehicle();
                tableViewLossC.getItems().add(carC);
                tw1.getItems().add(car);
                ig1.setCellValueFactory(new PropertyValueFactory<>("lossAmount"));
            }else{
                throw new FileNotFoundException("File 'maxLossCar' not found");
            }
        }catch (FileNotFoundException e) {
            System.out.println("File with max loss car not found.");
        }
        try {
            VehicleWithLoss bike = VehicleLossCalculate.deserializeVehicleWithLoss(ConfigLoader.getProperty("MAX_LOSS_BIKE_PATH"));
            if (bike != null) {
                idB.setCellValueFactory(new PropertyValueFactory<>("id"));
                cbB.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
                pB.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
                mB.setCellValueFactory(new PropertyValueFactory<>("model"));
                sbB.setCellValueFactory(new PropertyValueFactory<>("batteryPercentage"));
                dB.setCellValueFactory(new PropertyValueFactory<>("range"));
                Bike bikeB = (Bike) bike.getVehicle();
                tableViewLossB.getItems().add(bikeB);
                tw2.getItems().add(bike);
                ig2.setCellValueFactory(new PropertyValueFactory<>("lossAmount"));
            }
            else{
                throw new FileNotFoundException("File 'maxLossBike' not found");
            }
        }catch (FileNotFoundException e){
            System.out.println("File with max loss bike not found.");
        }
        try {
            VehicleWithLoss scooter = VehicleLossCalculate.deserializeVehicleWithLoss(ConfigLoader.getProperty("MAX_LOSS_SCOOTER_PATH"));
            if (scooter != null) {

                idS.setCellValueFactory(new PropertyValueFactory<>("id"));
                cbS.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
                pS.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
                mS.setCellValueFactory(new PropertyValueFactory<>("model"));
                sbS.setCellValueFactory(new PropertyValueFactory<>("batteryPercentage"));
                mbS.setCellValueFactory(new PropertyValueFactory<>("maxSpeed"));
                Scooter scooterS = (Scooter) scooter.getVehicle();
                tableViewLossS.getItems().add(scooterS);
                tw3.getItems().add(scooter);
                ig3.setCellValueFactory(new PropertyValueFactory<>("lossAmount"));
            }else {
                throw new FileNotFoundException("File 'maxLossScooter' not found");
            }
        }catch (FileNotFoundException e){
            System.out.println("File with max loss scooter not found.");
        }
    }
}
