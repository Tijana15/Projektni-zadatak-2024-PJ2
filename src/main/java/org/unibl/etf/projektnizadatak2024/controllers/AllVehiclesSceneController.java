package org.unibl.etf.projektnizadatak2024.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.unibl.etf.projektnizadatak2024.vehicle.Bike;
import org.unibl.etf.projektnizadatak2024.vehicle.Car;
import org.unibl.etf.projektnizadatak2024.vehicle.Scooter;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;
import org.unibl.etf.projektnizadatak2024.ePJ2Company;

import java.util.ArrayList;
import java.util.List;

public class AllVehiclesSceneController {

    @FXML
    private TableColumn<?, ?> cnB;

    @FXML
    private TableColumn<?, ?> cnC;

    @FXML
    private TableColumn<?, ?> cnS;

    @FXML
    private TableColumn<?, ?> dB;

    @FXML
    private TableColumn<?, ?> dnC;

    @FXML
    private TableColumn<?, ?> idB;

    @FXML
    private TableColumn<?, ?> idC;

    @FXML
    private TableColumn<?, ?> idS;

    @FXML
    private TableColumn<?, ?> mB;

    @FXML
    private TableColumn<?, ?> mC;

    @FXML
    private TableColumn<?, ?> mS;

    @FXML
    private TableColumn<?, ?> mbS;

    @FXML
    private TableColumn<?, ?> oC;

    @FXML
    private TableColumn<?, ?> pB;

    @FXML
    private TableColumn<?, ?> pC;

    @FXML
    private TableColumn<?, ?> pS;

    @FXML
    private TableColumn<?, ?> sbB;

    @FXML
    private TableColumn<?, ?> sbC;

    @FXML
    private TableColumn<?, ?> sbS;

    @FXML
    private TableView<Bike> tableViewBike;

    @FXML
    private TableView<Car> tableViewCar;

    @FXML
    private TableView<Scooter> tableViewScooter;

    @FXML
    private void initialize() {
        idC.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnC.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        pC.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        mC.setCellValueFactory(new PropertyValueFactory<>("model"));
        sbC.setCellValueFactory(new PropertyValueFactory<>("batteryPercentage"));
        dnC.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        oC.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Set up columns for Bike Table
        idB.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnB.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        pB.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        mB.setCellValueFactory(new PropertyValueFactory<>("model"));
        sbB.setCellValueFactory(new PropertyValueFactory<>("batteryPercentage"));
        dB.setCellValueFactory(new PropertyValueFactory<>("range"));

        // Set up columns for Scooter Table
        idS.setCellValueFactory(new PropertyValueFactory<>("id"));
        cnS.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        pS.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        mS.setCellValueFactory(new PropertyValueFactory<>("model"));
        sbS.setCellValueFactory(new PropertyValueFactory<>("batteryPercentage"));
        mbS.setCellValueFactory(new PropertyValueFactory<>("maxSpeed"));

        loadVehiclesIntoTables();
    }

    private void loadVehiclesIntoTables() {
        List<Car> cars = new ArrayList<>();
        List<Bike> bikes = new ArrayList<>();
        List<Scooter> scooters = new ArrayList<>();

        for (Vehicle vehicle : ePJ2Company.vehicles) {
            if (vehicle instanceof Car) {
                cars.add((Car) vehicle);
            } else if (vehicle instanceof Bike) {
                bikes.add((Bike) vehicle);
            } else if (vehicle instanceof Scooter) {
                scooters.add((Scooter) vehicle);
            }
        }

        tableViewCar.setItems(FXCollections.observableArrayList(cars));
        tableViewBike.setItems(FXCollections.observableArrayList(bikes));
        tableViewScooter.setItems(FXCollections.observableArrayList(scooters));
    }
}
