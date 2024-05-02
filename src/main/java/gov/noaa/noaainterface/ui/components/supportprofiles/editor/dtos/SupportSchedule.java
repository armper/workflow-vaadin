package gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos;

import java.time.LocalDate;

public class SupportSchedule {

    private LocalDate dailyBriefingTime;
    private LocalDate supportStartDate;
    private String briefingNotes;

    public SupportSchedule(LocalDate dailyBriefingTime) {
        this.dailyBriefingTime = dailyBriefingTime;
    }

    public SupportSchedule() {
        
    }

    public LocalDate getDailyBriefingTime() {
        return dailyBriefingTime;
    }

    public void setDailyBriefingTime(LocalDate dailyBriefingTime) {
        this.dailyBriefingTime = dailyBriefingTime;
    }

    public LocalDate getSupportStartDate() {
        return supportStartDate;
    }

    public void setSupportStartDate(LocalDate supportStartDate) {
        this.supportStartDate = supportStartDate;
    }

    public String getBriefingNotes() {
        return briefingNotes;
    }

    public void setBriefingNotes(String briefingNotes) {
        this.briefingNotes = briefingNotes;
    }

}
