package com.example.kostenstellenrechner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainViewController {
    @FXML
    private DatePicker UntilDatePicker;

    @FXML
    private DatePicker FromDatePicker;

    @FXML
    private Button SearchButton;

    @FXML
    private TableView MachinesTable;

    @FXML
    private TableView DataTable;

    @FXML
    public void initialize()
    {
        UntilDatePicker.setValue(LocalDate.now());

        JSONFileHandler fileHandler = new JSONFileHandler();
        List<CurrentData> livedata = List.of();
        List<Machine> machinesdata = List.of();
        try {
            livedata = fileHandler.deseriseCurrentData("/Users/phillipeckstein/Code/Kostenstellenrechner/src/main/resources/com/example/kostenstellenrechner/currentdata.json");
            machinesdata = fileHandler.deseriseMaschine("/Users/phillipeckstein/Code/Kostenstellenrechner/src/main/resources/com/example/kostenstellenrechner/Maschines.json");
        } catch (IOException e) {

            showErrorPopup(e.getMessage());
        }

        ObservableList<MachineFX> machines = ConvertToObservableListMachines(machinesdata);
        ObservableList<DataFX> data = ConvertToObservableListData(livedata);
        PopulateMachinesView(machines);
        PopulateDataView(data);
    }

    private void PopulateDataView(ObservableList<DataFX> data) {
        TableColumn<DataFX, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("machineName"));

        TableColumn<DataFX, String> fromColumn = new TableColumn<>("From");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("fromDate"));

        TableColumn<DataFX, String> toColumn = new TableColumn<>("To");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("toDate"));

        DataTable.getColumns().addAll(nameColumn, fromColumn, toColumn);

        DataTable.setItems(data);
    }

    private ObservableList<DataFX> ConvertToObservableListData(List<CurrentData> livedata) {
        ObservableList<DataFX> returnvalue = FXCollections.observableArrayList();
        for (CurrentData data : livedata) {
            returnvalue.add(new DataFX(data.Maschine, data.beginn, data.end));
        }
        return returnvalue;
    }

    private ObservableList<MachineFX> ConvertToObservableListMachines(List<Machine> machinesdata) {
        ObservableList<MachineFX> returnvalue = FXCollections.observableArrayList();
        for (Machine machine : machinesdata) {
            returnvalue.add(new MachineFX(machine.MaschineName, machine.Cost));
        }
        return returnvalue;
    }

    private void PopulateMachinesView(ObservableList<MachineFX> machines) {
        TableColumn<MachineFX, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MachineFX, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

        MachinesTable.getColumns().addAll(nameCol, costCol);

        MachinesTable.setItems(machines);
    }

    private void showErrorPopup(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error Message");
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
}