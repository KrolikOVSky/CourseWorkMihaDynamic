package com.backEnd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Month {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    private final String month;

    Month(){
        this.month = "";
    }

    Month(String month) {
        this.month = month;
    }

    public static ObservableList<String> getMonths(){
        return FXCollections.observableArrayList(
                JANUARY.getMonth(),
                FEBRUARY.getMonth(),
                MARCH.getMonth(),
                APRIL.getMonth(),
                MAY.getMonth(),
                JUNE.getMonth(),
                JULY.getMonth(),
                AUGUST.getMonth(),
                SEPTEMBER.getMonth(),
                OCTOBER.getMonth(),
                NOVEMBER.getMonth(),
                DECEMBER.getMonth()
        );
    }

    public String getMonth() {
        return month;
    }
}
