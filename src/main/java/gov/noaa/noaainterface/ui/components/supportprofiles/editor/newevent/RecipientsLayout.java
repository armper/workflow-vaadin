package gov.noaa.noaainterface.ui.components.supportprofiles.editor.newevent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;

import gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos.Partner;
import gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos.Recipients;
import gov.noaa.noaainterface.ui.components.supportprofiles.editor.interfaces.ValidatableForm;

public class RecipientsLayout extends VerticalLayout implements ValidatableForm<Recipients> {
    private Span subTitle = new Span("Add contact details for people notified if weather thresholds are met.");

    private ComboBox<Partner> recipientsListComboBox;

    private final Button addRecipientButton = new Button("Add", VaadinIcon.PLUS.create());
    private final Button otherAddRecipientButton = new Button("Add Recipient", VaadinIcon.PLUS.create());

    private final TextField firstName = new TextField("First Name");

    private final TextField lastName = new TextField("Last Name");

    private final TextField title = new TextField("Title");

    private final TextField organization = new TextField("Organization");

    private final TextField emailAddress = new TextField("Email Address");

    private final TextField phoneNumber = new TextField("Phone Number");

    private final Checkbox smsCheckbox = new Checkbox("The recipient will get SMS text updates");

    private final FormLayout recipientLayout = new FormLayout();

    private final RadioButtonGroup<Partner> allRecipientsRadioButtonGroup = new RadioButtonGroup<>("Primary");

    private Recipients selectedRecipients = new Recipients();

    private Consumer<Recipients> recipientsConsumer;

    public void setRequestingPartner(Partner requestingPartner) {
        selectedRecipients.addRecipient(requestingPartner);
        selectedRecipients.setPrimaryRecipient(requestingPartner);

        allRecipientsRadioButtonGroup.setItems(selectedRecipients.getSelectedRecipients());
        allRecipientsRadioButtonGroup.setValue(requestingPartner);
    }

    public RecipientsLayout(Collection<Partner> recipientsList) {
        setWidthFull();
        new ArrayList<>(recipientsList);

        recipientsListComboBox = new ComboBox<>();
        recipientsListComboBox.setWidth("65%");
        recipientsListComboBox.setMaxWidth("65%");
        recipientsListComboBox.setItems(recipientsList);
        recipientsListComboBox.setItemLabelGenerator(partner -> partner.firstName() + " " + partner.lastName());
        recipientsListComboBox.setRenderer(new ComponentRenderer<>(PartnerComponentUtil::createPartnerComponent));
        recipientsListComboBox.setPlaceholder("Search by name or email address");

        recipientsListComboBox.setRequired(true);

        recipientsListComboBox.addValueChangeListener(listener -> {
            recipientsListComboBox.setInvalid(false);

            Partner selectedPartner = listener.getValue();
            if (selectedPartner != null) {
                firstName.setValue(selectedPartner.firstName());
                lastName.setValue(selectedPartner.lastName());
                title.setValue(selectedPartner.jobTitle());
                organization.setValue(selectedPartner.organization());
                emailAddress.setValue(selectedPartner.email());
                phoneNumber.setValue(selectedPartner.phone());
                smsCheckbox.setValue(selectedPartner.sms());
            }

        });

        HorizontalLayout recipientListAndButtonLayout = new HorizontalLayout(recipientsListComboBox,
                addRecipientButton);

        recipientsListComboBox.setWidth("38rem");

        recipientLayout.add(firstName, 2);
        recipientLayout.add(lastName, 2);
        recipientLayout.add(title, 2);
        recipientLayout.add(organization, 2);
        recipientLayout.add(emailAddress, 2);
        recipientLayout.add(phoneNumber, 2);
        recipientLayout.add(smsCheckbox, 2);

        recipientLayout.setMaxWidth("35rem");

        allRecipientsRadioButtonGroup.setItemLabelGenerator(partner -> partner.firstName() + " " + partner.lastName());
        allRecipientsRadioButtonGroup.setRenderer(new ComponentRenderer<>(this::createPartnerComponent));
        allRecipientsRadioButtonGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        allRecipientsRadioButtonGroup.setWidth("18rem");
        allRecipientsRadioButtonGroup.addValueChangeListener(listener -> {
            selectedRecipients.setPrimaryRecipient(listener.getValue());
            if (recipientsConsumer != null) {
                recipientsConsumer.accept(selectedRecipients);
            }
        });

        VerticalLayout allRecipientsLayout = new VerticalLayout();
        allRecipientsLayout.getStyle().set("background-color", "var(--lumo-contrast-5pct)");

        allRecipientsLayout.add(allRecipientsRadioButtonGroup);
        allRecipientsLayout.setMaxWidth("20rem");

        VerticalLayout listAndFields = new VerticalLayout(recipientListAndButtonLayout, recipientLayout);
        HorizontalLayout mainLayout = new HorizontalLayout(
                listAndFields, allRecipientsLayout);

        addRecipientButton.addClickListener(event -> {
            getSelectedPartner(recipientsList);
        });

        otherAddRecipientButton.addClickListener(listener -> {
            getSelectedPartner(recipientsList);
        });

        add(subTitle, mainLayout, otherAddRecipientButton);

    }

    private void getSelectedPartner(Collection<Partner> recipientsList) {
        Partner selectedPartner = recipientsListComboBox.getValue();
        if (selectedPartner != null && !selectedRecipients.getSelectedRecipients().contains(selectedPartner)) {
            selectedRecipients.addRecipient(selectedPartner);
            allRecipientsRadioButtonGroup.setItems(selectedRecipients.getSelectedRecipients());

            // Remove from the ComboBox list
            recipientsListComboBox.clear(); // Clear selection
            recipientsListComboBox.setItems(recipientsList); // Refresh the ComboBox

            if (recipientsConsumer != null) {
                recipientsConsumer.accept(selectedRecipients);
            }

        }
    }

    private FlexLayout createPartnerComponent(Partner person) {
        FlexLayout wrapper = new FlexLayout();
        wrapper.setAlignItems(Alignment.CENTER);
        wrapper.setFlexDirection(FlexDirection.COLUMN);
        wrapper.setWidthFull();

        // Chevron button to toggle details
        Button chevronButton = new Button(VaadinIcon.CHEVRON_DOWN.create());
        chevronButton.addClassNames("chevron-toggle");
        chevronButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);

        // Container for the name and chevron button
        HorizontalLayout header = new HorizontalLayout();

        Div nameDiv = new Div(new Span(person.firstName() + " " + person.lastName()));
        nameDiv.getStyle().set("flex", "1").set("margin-right", "auto");

        header.add(nameDiv, chevronButton);
        header.setAlignItems(Alignment.CENTER); // Align items vertically in the center
        header.setWidthFull(); // Ensure the header spans the full width
        header.setFlexGrow(1, nameDiv); // Allow nameDiv to take up remaining space

        Div detailsDiv = new Div(); // Container for details
        detailsDiv.setVisible(false); // Initially hidden

        Div jobTitleDiv = new Div(new Span(person.jobTitle()));
        jobTitleDiv.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.FontSize.SMALL);
        jobTitleDiv.getStyle().set("flex", "1").set("margin-right", "auto");

        Div orgDiv = new Div(new Span(person.organization()));
        orgDiv.addClassNames(LumoUtility.TextColor.TERTIARY, LumoUtility.FontSize.SMALL);
        orgDiv.getStyle().set("flex", "1").set("margin-right", "auto");

        Div emailDiv = new Div(new Span(person.email()));
        emailDiv.addClassNames(LumoUtility.TextColor.TERTIARY, LumoUtility.FontSize.SMALL);
        emailDiv.getStyle().set("flex", "1").set("margin-right", "auto");

        HorizontalLayout buttonControls = new HorizontalLayout();
        Button editButton = new Button("Edit", VaadinIcon.EDIT.create());
        editButton.addClickListener(event -> {
            editPerson(person.id());
        });

        Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
        deleteButton.addClickListener(event -> {
            showDeletePersonDialog(person.id());
        });

        buttonControls.add(editButton, deleteButton);

        detailsDiv.add(jobTitleDiv, orgDiv, emailDiv, buttonControls); // Add details to the container

        chevronButton.addClickListener(event -> {
            detailsDiv.setVisible(!detailsDiv.isVisible()); // Toggle visibility
            Icon upIcon = VaadinIcon.CHEVRON_UP.create();
            Icon downIcon = VaadinIcon.CHEVRON_DOWN.create();
            chevronButton.setIcon(detailsDiv.isVisible() ? upIcon : downIcon); // Toggle icon
        });

        wrapper.add(header, detailsDiv); // Add components to the main layout
        return wrapper;
    }

    private void showDeletePersonDialog(UUID id) {
        Dialog confirmDialog = new Dialog();
        confirmDialog.setCloseOnEsc(false);
        confirmDialog.setCloseOnOutsideClick(false);

        Span message = new Span("Are you sure you want to delete this person?");
        Button confirmButton = new Button("Yes", event -> {
            confirmDialog.close();
            deletePersonConfirmed(id);
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button cancelButton = new Button("No", event -> confirmDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        confirmDialog.add(message, new HorizontalLayout(confirmButton, cancelButton));
        confirmDialog.open();
    }

    private void deletePersonConfirmed(UUID id) {
        Partner toBeDeleted = selectedRecipients.getSelectedRecipients().stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElse(null);
        if (toBeDeleted != null) {
            selectedRecipients.removeRecipient(toBeDeleted);
            allRecipientsRadioButtonGroup.setItems(selectedRecipients.getSelectedRecipients());
        }
    }

    private void editPerson(UUID id) {
        Partner toBeEdited = allRecipientsRadioButtonGroup.getValue();
        if (toBeEdited != null) {
            firstName.setValue(toBeEdited.firstName());
            lastName.setValue(toBeEdited.lastName());
            title.setValue(toBeEdited.jobTitle());
            organization.setValue(toBeEdited.organization());
            emailAddress.setValue(toBeEdited.email());
            phoneNumber.setValue(toBeEdited.phone());
            smsCheckbox.setValue(toBeEdited.sms());

        }

    }

    @Override
    public boolean isValid() {
        return !selectedRecipients.getSelectedRecipients().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "Please select at least one recipient";
    }

    @Override
    public void showErrors() {
        recipientsListComboBox.setInvalid(true);
        Notification.show(getErrorMessage());
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Recipients getData() {
        return this.selectedRecipients;
    }

    @Override
    public void addValueChangeListener(Consumer<Recipients> recipientsConsumer) {
        this.recipientsConsumer = recipientsConsumer;
    }

}
