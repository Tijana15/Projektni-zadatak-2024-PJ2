module org.unibl.etf.projektnizadatak2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;


    opens org.unibl.etf.projektnizadatak2024 to javafx.fxml;
    exports org.unibl.etf.projektnizadatak2024;
    exports org.unibl.etf.projektnizadatak2024.vehicle;
    opens org.unibl.etf.projektnizadatak2024.vehicle to javafx.fxml;
    exports org.unibl.etf.projektnizadatak2024.interfaces;
    opens org.unibl.etf.projektnizadatak2024.interfaces to javafx.fxml;
    exports org.unibl.etf.projektnizadatak2024.user;
    opens org.unibl.etf.projektnizadatak2024.user to javafx.fxml;
    exports org.unibl.etf.projektnizadatak2024.controllers;
    opens org.unibl.etf.projektnizadatak2024.controllers to javafx.fxml;
    exports org.unibl.etf.projektnizadatak2024.reports;
    opens org.unibl.etf.projektnizadatak2024.reports to javafx.fxml;
    exports org.unibl.etf.projektnizadatak2024.lossCalculations;
    opens org.unibl.etf.projektnizadatak2024.lossCalculations to javafx.fxml;
}