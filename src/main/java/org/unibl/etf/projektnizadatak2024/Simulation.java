package org.unibl.etf.projektnizadatak2024;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.unibl.etf.projektnizadatak2024.lossCalculations.VehicleWithLoss;
import org.unibl.etf.projektnizadatak2024.reports.DailyReport;
import org.unibl.etf.projektnizadatak2024.reports.SummaryReport;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.unibl.etf.projektnizadatak2024.lossCalculations.VehicleLossCalculate.*;
import static org.unibl.etf.projektnizadatak2024.ePJ2Company.vehicles;
import static org.unibl.etf.projektnizadatak2024.reports.DailyReport.generateDailyReports;
import static org.unibl.etf.projektnizadatak2024.reports.DailyReport.printDailyReports;

public class Simulation extends Application {
    public volatile static Map<String, Vehicle> vehicleMap;
    public volatile static Map<LocalDate, DailyReport.DailySummary> dailyReports;
    public static final GridPane gridPane1 = new GridPane();

    /**
     * The start method is the main entry point for JavaFX applications.
     * This method initializes the primary stage, sets up the scene, and configures the user interface components,
     * including navigation buttons and layout elements. It also loads FXML files for different scenes and sets
     * up the handlers for the buttons to switch between scenes.
     *
     * @param stage The primary stage for this application, onto which the application scene will be set.
     */
    @Override
    public void start(Stage stage) {
        try {
            //definisanje dugmadi
            Button btnAllTransport = new Button("All transport vehicles");
            Button btnBreakdowns = new Button("Defects");
            Button btnBusinessResults = new Button("Business results");
            Button btnLoss = new Button("Company loss vehicles");

            //prva scena
            stage.setTitle("ePJ2");
            stage.getIcons().add(new Image("iconApp.jpg"));
            AnchorPane anchorPane1 = new AnchorPane();

            HBox buttonBar = new HBox(10);
            buttonBar.getChildren().addAll(btnAllTransport, btnBreakdowns, btnBusinessResults, btnLoss);

            anchorPane1.getChildren().addAll(buttonBar, gridPane1);

            for (int i = 0; i < 20; i++) {
                RowConstraints row = new RowConstraints(40);
                gridPane1.getRowConstraints().add(row);
            }

            for (int i = 0; i < 20; i++) {
                ColumnConstraints col = new ColumnConstraints(40);
                gridPane1.getColumnConstraints().add(col);
            }

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    AnchorPane pane = new AnchorPane();
                    pane.setStyle("-fx-border-color: grey;");
                    if (i >= 5 && i <= 14 && j >= 5 && j <= 14) {
                        pane.setStyle("-fx-background-color: lightgreen; -fx-border-color: grey;");
                    }
                    GridPane.setRowIndex(pane, i);
                    GridPane.setColumnIndex(pane, j);
                    gridPane1.getChildren().add(pane);
                }
            }

            AnchorPane.setTopAnchor(buttonBar, 0.0);
            AnchorPane.setLeftAnchor(buttonBar, 0.0);
            AnchorPane.setRightAnchor(buttonBar, 0.0);

            AnchorPane.setTopAnchor(gridPane1, 40.0);
            AnchorPane.setLeftAnchor(gridPane1, 0.0);
            AnchorPane.setRightAnchor(gridPane1, 0.0);
            AnchorPane.setBottomAnchor(gridPane1, 0.0);

            Scene firstScene = new Scene(anchorPane1, 800, 800);
            stage.setScene(firstScene);
            stage.show();

            FXMLLoader fxmlLoader2 = new FXMLLoader(Simulation.class.getResource("AllVehiclesScene.fxml"));
            btnAllTransport.setOnAction(e -> {
                try {
                    Scene allVehiclesScene = new Scene(fxmlLoader2.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("All vehicles of the ePJ2 Company");
                    newStage.setScene(allVehiclesScene);
                    newStage.getIcons().add(new Image("iconApp.jpg"));
                    newStage.show();
                } catch (IOException ex) {
                    System.err.println("Error caused by switching to AllVehiclesScene");
                }
            });

            FXMLLoader fxmlLoader3 = new FXMLLoader(Simulation.class.getResource("DefectsScene.fxml"));
            btnBreakdowns.setOnAction(e -> {
                try {
                    Scene breakdownsScene = new Scene(fxmlLoader3.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("All vehicle defects of ePJ2 Company");
                    newStage.setScene(breakdownsScene);
                    newStage.getIcons().add(new Image("iconApp.jpg"));
                    newStage.show();
                } catch (IOException ex) {
                    System.err.println("Error caused by switching to DefectsScene");
                }
            });


            FXMLLoader fxmlLoader4 = new FXMLLoader(Simulation.class.getResource("BusinessResultsScene.fxml"));
            btnBusinessResults.setOnAction(e -> {
                try {
                    Scene businessResultsScene = new Scene(fxmlLoader4.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("Business results of ePJ2 Company");
                    newStage.setScene(businessResultsScene);
                    newStage.getIcons().add(new Image("iconApp.jpg"));
                    newStage.show();
                } catch (IOException ex) {
                    System.err.println("Error caused by switching to BusinessResultsScene");
                }
            });

            FXMLLoader fxmlLoader5 = new FXMLLoader(Simulation.class.getResource("LossVehiclesScene.fxml"));
            btnLoss.setOnAction(e -> {
                try {
                    Scene lossReportsScene = new Scene(fxmlLoader5.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("Vehicles that incurred the greatest loss for ePJ2 Company");
                    newStage.setScene(lossReportsScene);
                    newStage.getIcons().add(new Image("iconApp.jpg"));
                    newStage.show();
                } catch (IOException ex) {
                    System.err.println("Error caused by switching to LossVehiclesScene");
                }
            });
            new Thread(() -> {
                TreeMap<LocalDateTime, List<Rental>> groupedRentals = ePJ2Company.rentals.stream()
                        .collect(Collectors.groupingBy(Rental::getRentalDateTime, TreeMap::new, Collectors.toList()));
                for (Map.Entry<LocalDateTime, List<Rental>> entry : groupedRentals.entrySet()) {
                    List<Rental> rentalGroup = entry.getValue();

                    System.out.println("\n=========================\nStarting rentals for date: " + entry.getKey());
                    List<Thread> threads = new ArrayList<>();
                    for (Rental rental : rentalGroup) {
                        Thread thread = new Thread(rental);
                        thread.start();
                        threads.add(thread);
                    }

                    for (Thread thread : threads) {
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            System.err.println("Interrupted in Main when joining thread");
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.err.println("Interrupted during sleep between rental groups");
                    }
                }

                Platform.runLater(() -> {
                    dailyReports = generateDailyReports(Rental.receiptList, vehicles);
                    printDailyReports(dailyReports);
                    SummaryReport.generateSummaryReport(Rental.receiptList, vehicles);
                    System.out.println(new SummaryReport());
                });

                VehicleWithLoss maxLossCar = findVehicleWithMaxLoss(Rental.receiptList, "automobil");
                VehicleWithLoss maxLossBike = findVehicleWithMaxLoss(Rental.receiptList, "bicikl");
                VehicleWithLoss maxLossScooter = findVehicleWithMaxLoss(Rental.receiptList, "trotinet");

                if (maxLossCar != null) {
                    serializeVehicleWithLoss(maxLossCar, ConfigLoader.getProperty("MAX_LOSS_CAR_PATH"));
                } else System.out.println("There is no car that had breakages, so no car with max loss for company.");
                if (maxLossBike != null) {
                    serializeVehicleWithLoss(maxLossBike, ConfigLoader.getProperty("MAX_LOSS_BIKE_PATH"));
                } else System.out.println("There is no bike that had breakages, so no bike with max loss for company.");
                if (maxLossScooter != null) {
                    serializeVehicleWithLoss(maxLossScooter, ConfigLoader.getProperty("MAX_LOSS_SCOOTER_PATH"));
                } else
                    System.out.println("There is no scooter that had breakages, so no scooter with max loss for company.");

            }).start();

        } catch (Exception e) {
            System.err.println("Error caused by start method of Application.");
        }
    }

    /**
     * Moves a vehicle from its old position on the grid pane to a new position.
     *
     * @param rental The rental object containing details about the vehicle being moved.
     * @param oldX   The old x-coordinate of the vehicle on the grid pane.
     * @param oldY   The old y-coordinate of the vehicle on the grid pane.
     * @param newX   The new x-coordinate of the vehicle on the grid pane.
     * @param newY   The new y-coordinate of the vehicle on the grid pane.
     */
    public static synchronized void moveVehicleOnGridPane(Rental rental, int oldX, int oldY, int newX, int newY) {
        Platform.runLater(() -> {
            synchronized (gridPane1) {
                // Uklanjanje vozila sa stare pozicije
                removeVehicleFromGridPane(oldX, oldY);

                // Prikaz vozila na novoj poziciji
                String vehicleType = rental.getVehicleType();
                ImageView vehicleImageView = switch (vehicleType) {
                    case "automobil" -> createImageView("Car.png", rental);
                    case "trotinet" -> createImageView("Scooter.png", rental);
                    default -> createImageView("Bike.png", rental);
                };

                GridPane.setRowIndex(vehicleImageView, newY);
                GridPane.setColumnIndex(vehicleImageView, newX);
                Simulation.gridPane1.getChildren().add(vehicleImageView);
            }
        });
    }

    /**
     * Creates an ImageView for a vehicle based on the given image path and rental details.
     *
     * @param imagePath The path to the image representing the vehicle.
     * @param rental    The rental object containing details about the vehicle.
     * @return An ImageView object representing the vehicle.
     */

    public static ImageView createImageView(String imagePath, Rental rental) {
        Image vehicleImage = new Image(imagePath);
        ImageView vehicleImageView = new ImageView(vehicleImage);

        vehicleImageView.setFitWidth(40);
        vehicleImageView.setFitHeight(40);

        Tooltip tooltip = new Tooltip(printVehicleInfo(rental));
        Tooltip.install(vehicleImageView, tooltip);
        vehicleImageView.setOnMouseEntered(event -> tooltip.show(vehicleImageView, event.getScreenX(), event.getScreenY() + 15));
        vehicleImageView.setOnMouseExited(event -> tooltip.hide());

        return vehicleImageView;
    }

    /**
     * Removes a vehicle's ImageView from the grid pane based on the specified coordinates.
     *
     * @param x The x-coordinate of the vehicle to be removed.
     * @param y The y-coordinate of the vehicle to be removed.
     */
    public static synchronized void removeVehicleFromGridPane(int x, int y) {
        Platform.runLater(() -> {
            synchronized (gridPane1) {
                Simulation.gridPane1.getChildren().removeIf(node ->
                        GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y && node instanceof ImageView
                );
            }
        });
    }

    /**
     * Displays a vehicle on the grid pane at the specified starting coordinates.
     *
     * @param rental           The rental object containing details about the vehicle.
     * @param xStartCoordinate The x-coordinate where the vehicle should be displayed.
     * @param yStartCoordinate The y-coordinate where the vehicle should be displayed.
     */
    public static synchronized void showVehicleOnGridPane(Rental rental, int xStartCoordinate, int yStartCoordinate) {
        Platform.runLater(() -> {
            synchronized (Simulation.gridPane1) {
                String vehicleType = rental.getVehicleType();
                if (vehicleType.equals("automobil")) {
                    Image carImage = new Image("Car.png");
                    ImageView carImageView = new ImageView(carImage);

                    carImageView.setFitWidth(40);
                    carImageView.setFitHeight(40);

                    Tooltip tooltip = new Tooltip(printVehicleInfo(rental));
                    Tooltip.install(carImageView, tooltip);
                    carImageView.setOnMouseEntered(event -> tooltip.show(carImageView, event.getScreenX(), event.getScreenY() + 15));
                    carImageView.setOnMouseExited(event -> tooltip.hide());

                    GridPane.setRowIndex(carImageView, yStartCoordinate);
                    GridPane.setColumnIndex(carImageView, xStartCoordinate);
                    gridPane1.getChildren().add(carImageView);
                } else if (vehicleType.equals("trotinet")) {
                    Image scooterImage = new Image("Scooter.png");
                    ImageView scooterImageView = new ImageView(scooterImage);

                    scooterImageView.setFitWidth(40);
                    scooterImageView.setFitHeight(40);

                    Tooltip tooltip = new Tooltip(printVehicleInfo(rental));
                    Tooltip.install(scooterImageView, tooltip);
                    scooterImageView.setOnMouseEntered(event -> tooltip.show(scooterImageView, event.getScreenX(), event.getScreenY() + 15));
                    scooterImageView.setOnMouseExited(event -> tooltip.hide());

                    GridPane.setRowIndex(scooterImageView, yStartCoordinate);
                    GridPane.setColumnIndex(scooterImageView, xStartCoordinate);
                    gridPane1.getChildren().add(scooterImageView);
                } else {
                    Image bikeImage = new Image("Bike.png");
                    ImageView bikeImageView = new ImageView(bikeImage);

                    bikeImageView.setFitWidth(40);
                    bikeImageView.setFitHeight(40);

                    Tooltip tooltip = new Tooltip(printVehicleInfo(rental));
                    Tooltip.install(bikeImageView, tooltip);
                    bikeImageView.setOnMouseEntered(event -> tooltip.show(bikeImageView, event.getScreenX(), event.getScreenY() + 15));
                    bikeImageView.setOnMouseExited(event -> tooltip.hide());

                    GridPane.setRowIndex(bikeImageView, yStartCoordinate);
                    GridPane.setColumnIndex(bikeImageView, xStartCoordinate);
                    gridPane1.getChildren().add(bikeImageView);
                }
            }

        });
    }

    /**
     * Returns a string with the vehicle's ID and battery percentage for display on the screen.
     *
     * @param rental The rental object containing the vehicle ID.
     * @return A formatted string with the vehicle's ID and battery percentage.
     */
    public static String printVehicleInfo(Rental rental) {
        String vehicleId = rental.getVehicleId();
        Vehicle vehicle = null;
        for (Vehicle v : vehicles) {
            if (vehicleId.equals(v.getId())) {
                vehicle = v;
            }
        }
        assert vehicle != null;
        return "ID: " + rental.getVehicleId() + "\nBattery percentage: " + vehicle.getBatteryPercentage();
    }

    public static void main(String[] args) throws IOException, ParseException {

        File file = new File(ConfigLoader.getProperty("VEHICLES_PATH"));
        File file1 = new File(ConfigLoader.getProperty("RENTALS_PATH"));
        ePJ2Company.parseVehicles(file.toPath());
        vehicleMap = ePJ2Company.parseVehiclesToMap(file.toPath());
        ePJ2Company.parseRentals(file1.toPath());
        System.out.println(ePJ2Company.rentals.size());
        ePJ2Company.sortRentalsByDateTime();
        ePJ2Company.makeEveryTenthRental();
        launch(args);
    }

}