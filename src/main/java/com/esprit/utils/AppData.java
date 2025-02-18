package com.esprit.utils;

public class AppData {
    private static AppData instance;
    private int selectedEmployeeId;

    private AppData() {
        // Private constructor to prevent instantiation
    }

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    private int currentSelectedId;

    public int getCurrentSelectedId() {
        return currentSelectedId;
    }
    public void setCurrentSelectedId(int currentSelectedId) {
        this.currentSelectedId = currentSelectedId;
    }
}
