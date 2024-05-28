package com.example.kostenstellenrechner;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class MainViewController {
    @FXML
    private DatePicker UntilDatePicker;

    @FXML
    public void initialize()
    {
        UntilDatePicker.setValue(LocalDate.now());
    }
}