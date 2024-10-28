package org.unibl.etf.projektnizadatak2024.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.unibl.etf.projektnizadatak2024.Receipt;
import org.unibl.etf.projektnizadatak2024.Rental;

import java.util.ArrayList;
import java.util.List;

public class DefectsSceneController {
    @FXML
    private TableColumn<?, ?> idPrevoznogSredstva;

    @FXML
    private TableColumn<?, ?> opisKvara;

    @FXML
    private TableView<Receipt> tableViewDefects;

    @FXML
    private TableColumn<?, ?> vrijeme;

    @FXML
    private TableColumn<?, ?> vrstPrevoznogSredstva;

    @FXML
    private void initialize() {
        vrstPrevoznogSredstva.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        idPrevoznogSredstva.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        vrijeme.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        opisKvara.setCellValueFactory(new PropertyValueFactory<>("breakages"));
        List<Receipt> defects = getDefectsFromReceipts();
        tableViewDefects.getItems().addAll(defects);

    }

    private List<Receipt> getDefectsFromReceipts() {
        List<Receipt> defects = new ArrayList<>();
        for (Receipt receipt : Rental.receiptList) {
            if (receipt.isHadBreakages()) {
                defects.add(receipt);
            }
        }
        return defects;
    }

}
