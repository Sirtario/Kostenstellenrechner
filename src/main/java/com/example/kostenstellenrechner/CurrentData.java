package com.example.kostenstellenrechner;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CurrentData {
    public String Maschine;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date beginn;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date end;
}
