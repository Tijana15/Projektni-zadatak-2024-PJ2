package org.unibl.etf.projektnizadatak2024.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.unibl.etf.projektnizadatak2024.Simulation;
import org.unibl.etf.projektnizadatak2024.reports.DailyReport;
import org.unibl.etf.projektnizadatak2024.reports.SummaryReport;

public class BusinessResultsSceneController {

    @FXML
    private TableColumn<?, ?> datumDR;

    @FXML
    private TableColumn<?, ?> porezSR;

    @FXML
    private TableView<DailyReport.DailySummary> tableViewDailyReport;

    @FXML
    private TableView<SummaryReport> tableViewSummaryReport;

    @FXML
    private TableColumn<?, ?> troskoviKompanijeSR;

    @FXML
    private TableColumn<?, ?> ukupanPopustDR;

    @FXML
    private TableColumn<?, ?> ukupanPopustSR;

    @FXML
    private TableColumn<?, ?> ukupanPrihodDR;

    @FXML
    private TableColumn<?, ?> ukupanPrihodSR;

    @FXML
    private TableColumn<?, ?> ukupnoKvaroviSR;

    @FXML
    private TableColumn<?, ?> ukupnoOdrzavanjeDR;

    @FXML
    private TableColumn<?, ?> ukupnoOdrzavanjeSR;

    @FXML
    private TableColumn<?, ?> ukupnoPopravkeDR;

    @FXML
    private TableColumn<?, ?> ukupnoPromocijeDR;

    @FXML
    private TableColumn<?, ?> ukupnoPromocijeSR;

    @FXML
    private TableColumn<?, ?> voznjeSiriDioDR;

    @FXML
    private TableColumn<?, ?> voznjeSiriDioSR;

    @FXML
    private TableColumn<?, ?> voznjeUziDioDR;

    @FXML
    private TableColumn<?, ?> voznjeUziDioSR;

    @FXML
    private void initialize() {
        ukupanPrihodSR.setCellValueFactory(new PropertyValueFactory<>("totalIncome"));
        ukupanPopustSR.setCellValueFactory(new PropertyValueFactory<>("totalDiscount"));
        ukupnoPromocijeSR.setCellValueFactory(new PropertyValueFactory<>("totalPromotions"));
        voznjeUziDioSR.setCellValueFactory(new PropertyValueFactory<>("totalInnerCityRides"));
        voznjeSiriDioSR.setCellValueFactory(new PropertyValueFactory<>("totalOuterCityRides"));
        ukupnoOdrzavanjeSR.setCellValueFactory(new PropertyValueFactory<>("totalMaintenanceCost"));
        ukupnoKvaroviSR.setCellValueFactory(new PropertyValueFactory<>("totalRepairCost"));
        troskoviKompanijeSR.setCellValueFactory(new PropertyValueFactory<>("totalCompanyCosts"));
        porezSR.setCellValueFactory(new PropertyValueFactory<>("totalTax"));

        ObservableList<SummaryReport> summaryReportDataList = FXCollections.observableArrayList();
        SummaryReport summaryReport=SummaryReport.getSummaryReport();
        summaryReportDataList.add(summaryReport);
        tableViewSummaryReport.setItems(summaryReportDataList);

        datumDR.setCellValueFactory(new PropertyValueFactory<>("date"));
        ukupanPrihodDR.setCellValueFactory(new PropertyValueFactory<>("totalIncome"));
        ukupanPopustDR.setCellValueFactory(new PropertyValueFactory<>("totalDiscount"));
        ukupnoPromocijeDR.setCellValueFactory(new PropertyValueFactory<>("totalPromotions"));
        voznjeUziDioDR.setCellValueFactory(new PropertyValueFactory<>("totalInnerCityRides"));
        voznjeSiriDioDR.setCellValueFactory(new PropertyValueFactory<>("totalOuterCityRides"));
        ukupnoOdrzavanjeDR.setCellValueFactory(new PropertyValueFactory<>("totalMaintenenceCost"));
        ukupnoPopravkeDR.setCellValueFactory(new PropertyValueFactory<>("totalRepairCost"));

        loadDailyReports();
    }

    private void loadDailyReports() {
        ObservableList<DailyReport.DailySummary> reports = FXCollections.observableArrayList(Simulation.dailyReports.values());
        tableViewDailyReport.setItems(reports);
    }

}