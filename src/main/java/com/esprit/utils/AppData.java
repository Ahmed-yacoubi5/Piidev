package com.esprit.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppData {
    private static AppData instance;
    private int selectedCandidatId;
    private int pendingId;
    private String redirectionFrom;
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
    public int getSelectedCandidatId() {
        return selectedCandidatId;
    }
    public void setSelectedCandidatId(int selectedCandidatId) {
        this.selectedCandidatId = selectedCandidatId;
    }
    public int getPendingId() {
        return pendingId;
    }
    public void setPendingId(int pendingId) {
        this.pendingId = pendingId;
    }
    public static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return currentDateTime.format(formatter);
    }
   public String getRedirectionFrom() {
        return redirectionFrom;
   }
   public void setRedirectionFrom(String redirectionFrom) {
        this.redirectionFrom = redirectionFrom;
   }
}
