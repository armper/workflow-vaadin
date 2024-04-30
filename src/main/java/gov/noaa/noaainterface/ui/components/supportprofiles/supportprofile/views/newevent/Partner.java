package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.UUID;

public record Partner(UUID id, String email, String firstName, String lastName, String jobTitle, String city,
        String phone, String organization, Boolean sms) {

}
