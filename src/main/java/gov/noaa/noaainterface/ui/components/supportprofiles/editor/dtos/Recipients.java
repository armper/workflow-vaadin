package gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos;

import java.util.ArrayList;
import java.util.Collection;

public class Recipients {

    private Partner primaryRecipient;

    private final Collection<Partner> selectedRecipients = new ArrayList<>();

    public Collection<Partner> getSelectedRecipients() {
        return selectedRecipients;
    }

    public void addRecipient(Partner recipient) {
        selectedRecipients.add(recipient);
    }

    public void removeRecipient(Partner recipient) {
        selectedRecipients.remove(recipient);
    }

    public void clearRecipients() {
        selectedRecipients.clear();
    }

    public Partner getPrimaryRecipient() {
        return primaryRecipient;
    }

    public void setPrimaryRecipient(Partner primaryRecipient) {
        this.primaryRecipient = primaryRecipient;
    }

    @Override
    public String toString() {
        return "Recipients{" +
                "primaryRecipient=" + primaryRecipient +
                ", selectedRecipients=" + selectedRecipients +
                '}';
    }
}
