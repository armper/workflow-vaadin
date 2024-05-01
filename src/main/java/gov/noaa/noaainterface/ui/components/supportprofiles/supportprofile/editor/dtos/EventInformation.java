package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventInformation {
    private String title;
    private boolean isSearEvent;
    private boolean isNsseEvent;
    private String searEventRanking;
    private int attendance;
    private String website;
    private String locationType;
    private boolean noAddress;
    private Address address = new Address();
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String recurrence;
    private LocalDate recurrUntil;
    private String notes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSearEvent() {
        return isSearEvent;
    }

    public void setSearEvent(boolean isSearEvent) {
        this.isSearEvent = isSearEvent;
    }

    public boolean isNsseEvent() {
        return isNsseEvent;
    }

    public void setNsseEvent(boolean isNsseEvent) {
        this.isNsseEvent = isNsseEvent;
    }

    public String getSearEventRanking() {
        return searEventRanking;
    }

    public void setSearEventRanking(String searEventRanking) {
        this.searEventRanking = searEventRanking;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public boolean isNoAddress() {
        return noAddress;
    }

    public void setNoAddress(boolean noAddress) {
        this.noAddress = noAddress;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public LocalDate getRecurrUntil() {
        return recurrUntil;
    }

    public void setRecurrUntil(LocalDate recurrUntil) {
        this.recurrUntil = recurrUntil;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "EventInformation [address=" + address + ", attendance=" + attendance + ", endDate=" + endDate
                + ", endTime=" + endTime + ", isNsseEvent=" + isNsseEvent + ", isSearEvent=" + isSearEvent
                + ", locationType=" + locationType + ", noAddress=" + noAddress + ", notes=" + notes + ", recurrUntil="
                + recurrUntil + ", recurrence=" + recurrence + ", searEventRanking=" + searEventRanking + ", startDate="
                + startDate + ", startTime=" + startTime + ", title=" + title + ", website=" + website + "]";
    }
}
