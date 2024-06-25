package com.example.kostenstellenrechner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class MainViewController {
    @FXML
    private ListView ResultList;
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

    private JSONFileHandler fileHandler;
    private List<CurrentData> livedata;
    private List<CurrentData> filteredData = new ArrayList<>();

    private List<Machine> machinesdata;

    @FXML
    public void initialize()
    {
        UntilDatePicker.setValue(LocalDate.now());

        fileHandler = new JSONFileHandler();
        livedata = List.of();
        machinesdata = List.of();

    }

    private void LoadMachinesDataFromFile(String filePath) throws IOException {
        machinesdata = fileHandler.deseriseMaschine(filePath);
    }

    private void LoadLiveDataFromFile(String filePath) throws IOException {
        livedata = fileHandler.deseriseCurrentData(filePath);
    }

    private void PopulateDataView(ObservableList<DataFX> data) {
        TableColumn<DataFX, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("machineName"));

        TableColumn<DataFX, String> fromColumn = new TableColumn<>("From");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("fromDate"));

        TableColumn<DataFX, String> toColumn = new TableColumn<>("To");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("toDate"));

        DataTable.getColumns().clear();

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

    @FXML
    private void FilterBydate()
    {
        filteredData.clear();

        LocalDate fromdate = FromDatePicker.getValue();
        LocalDate todate = UntilDatePicker.getValue();

        if (fromdate == null) {
            fromdate = LocalDate.now().minusYears(1);
        }
        if (todate == null) {
            todate = LocalDate.now();
        }

        Instant instantFrom = Instant.from(fromdate.atStartOfDay(ZoneId.systemDefault()));
        Instant instandTo = Instant.from(todate.atStartOfDay(ZoneId.systemDefault()));
        for (CurrentData data : livedata) {
            if (data.beginn.after(Date.from(instantFrom)) && data.end.before(Date.from(instandTo)))
            {
                filteredData.add(data);
            }
        }

        ObservableList<DataFX> dataFX = ConvertToObservableListData(filteredData);
        PopulateDataView(dataFX);
    }

    @FXML
    private void CalculateCost()
    {
        Map<String,Long> DurationPerMachine = new HashMap<>();

        DurationPerMachine = DurationCalculation(DurationPerMachine);

        ObservableList<String> results = FXCollections.observableArrayList();

        for (Machine machine : machinesdata) {
            long duration = DurationPerMachine.get(machine.MaschineName);
            float cost = duration * machine.Cost;
            results.add(machine.MaschineName + ": " + cost+"€");
        }

        ResultList.setItems(results);
    }

    private Map<String, Long> DurationCalculation(Map<String, Long> DurationPerMachine) {
        if (filteredData.isEmpty())
        {
            FilterBydate();
        }

        for (Machine machine : machinesdata) {

            Long duration = 0l;

            for (CurrentData data : filteredData) {
                if (machine.MaschineName.equals( data.Maschine))
                {
                    long durationPerEntry = Duration.between(data.beginn.toInstant(),data.end.toInstant()).toHours();
                    duration += durationPerEntry;
                }
            }

            DurationPerMachine.put(machine.MaschineName,duration);
        }
        return DurationPerMachine;
    }

    @FXML
    private void LoadCurrentData(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);

        String fileName = fileChooser.showOpenDialog(DataTable.getScene().getWindow()).getPath();
        try {
            LoadLiveDataFromFile(fileName);
        } catch (IOException e) {
            showErrorPopup(e.getMessage());
        }

        ObservableList<DataFX> data = ConvertToObservableListData(livedata);
        PopulateDataView(data);

    }
    @FXML
    private void LoadMachinesData(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extensionFilter);

        String fileName = fileChooser.showOpenDialog(DataTable.getScene().getWindow()).getPath();
        try {
            LoadMachinesDataFromFile(fileName);
        } catch (IOException e) {
            showErrorPopup(e.getMessage());
        }

        ObservableList<MachineFX> machines = ConvertToObservableListMachines(machinesdata);
        PopulateMachinesView(machines);
    }
}