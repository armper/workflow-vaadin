package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.Collection;
import java.util.function.Consumer;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;

public class AddressDialog extends Dialog {
    private final TextField address1 = new TextField("Address 1");
    private final TextField address2 = new TextField("Address 2");
    private final TextField city = new TextField("City");
    private final ComboBox<String> state = new ComboBox<>("State");
    private final TextField zipCode = new TextField("ZIP/Postal Code");
    private final Button save = new Button("Save");

    private Binder<Address> binder = new Binder<>(Address.class);
    private Consumer<Address> onSaveConsumer;

    public AddressDialog(Collection<String> states) {
        binder.setBean(new Address());

        setupBinder();

        VerticalLayout layout = new VerticalLayout(address1, address2, city, state, zipCode, save);

        add(layout);

        state.setItems(states);

        save.addClickListener(event -> {
            try {
                binder.writeBean(binder.getBean());
                if (onSaveConsumer != null) {
                    onSaveConsumer.accept(binder.getBean());
                }
                close();
            } catch (ValidationException e) {
                Notification.show("Please correct the errors before saving.");
            }
        });
    }

    private void setupBinder() {
        binder.forField(address1)
                .asRequired("Address 1 is required")
                .withValidator(new StringLengthValidator("Address 1 must be between 1 and 100 characters", 1, 100))
                .bind(Address::getAddress1, Address::setAddress1);

        binder.forField(city)
                .asRequired("City is required")
                .withValidator(new StringLengthValidator("City must be between 1 and 100 characters", 1, 100))
                .bind(Address::getCity, Address::setCity);

        binder.forField(state)
                .asRequired("State is required")
                .bind(Address::getState, Address::setState);

        binder.forField(zipCode)
                .asRequired("ZIP Code is required")
                .withValidator(new StringLengthValidator("ZIP Code must be exactly 5 digits", 5, 5))
                .bind(Address::getZipCode, Address::setZipCode);
    }

    public void setAddress(Address address) {
        binder.setBean(address != null ? address : new Address());
    }

    public void setOnSave(Consumer<Address> onSave) {
        this.onSaveConsumer = onSave;
    }

    public TextField getAddress1() {
        return address1;
    }

    public TextField getAddress2() {
        return address2;
    }

    public TextField getCity() {
        return city;
    }

    public ComboBox<String> getState() {
        return state;
    }

    public TextField getZipCode() {
        return zipCode;
    }

  

}
