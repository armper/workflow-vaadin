package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent.eventinformation.Address;

public class AreaOfConcern {

    private String type;

    private Address address;

    private double latitude;

    private double longitude;

    private String radius;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

}
