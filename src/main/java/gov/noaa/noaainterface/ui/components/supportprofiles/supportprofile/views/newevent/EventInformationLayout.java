package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.time.LocalTime;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;

public class EventInformationLayout extends VerticalLayout implements ValidatableForm<EventInformation> {

    private final TextField eventTitle = new TextField("Event Title");
    private final Checkbox searEvent = new Checkbox("SEAR Event");
    private final Checkbox nsseEvent = new Checkbox("NSSE Event");
    private final ComboBox<String> searEventRanking = new ComboBox<>("SEAR Event Ranking");
    private final TextField attendance = new TextField("Attendance");
    private final TextField website = new TextField("Website");
    private final RadioButtonGroup<String> locationType = new RadioButtonGroup<>();
    private final Checkbox noAddressCheckbox = new Checkbox("This event does not have a street address");
    private final TextField address1 = new TextField("Address 1");
    private final TextField address2 = new TextField("Address 2");
    private final TextField city = new TextField("City");
    private final ComboBox<String> state = new ComboBox<>("State");
    private final TextField zipCode = new TextField("ZIP/Postal Code");
    private final DatePicker eventStartDate = new DatePicker("Event Start Date");
    private final DatePicker eventEndDate = new DatePicker("Event End Date");
    private final Checkbox multiDayEvent = new Checkbox("This is a multi-day event");
    private final TimePicker eventStartTime = new TimePicker("Event Start Time");
    private final TimePicker eventEndTime = new TimePicker("Event End Time");
    private final ComboBox<String> recurrence = new ComboBox<>("Recurrence");
    private final DatePicker recurrUntil = new DatePicker("Recurs Until");
    private final TextArea notes = new TextArea("Notes");

    private Binder<EventInformation> binder = new Binder<>(EventInformation.class);

    private Consumer<EventInformation> valueChangeListener;
    private final Collection<String> states;

    public EventInformationLayout(Collection<String> states) {
        this.states = states;
        
        binder.setBean(new EventInformation());

        FormLayout formLayout = createFormLayout();
        add(formLayout);
        setResponsiveSteps(formLayout);
        addlisteners();
        configureComponents();
        setInitialVisibility();
        bindFields();

    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(eventTitle, searEvent, nsseEvent, searEventRanking, attendance, website,
                locationType, noAddressCheckbox, address1, address2, city, state, zipCode,
                eventStartDate, eventEndDate, multiDayEvent, eventStartTime, eventEndTime,
                recurrence, recurrUntil, notes);
        return formLayout;
    }

    private void setResponsiveSteps(FormLayout formLayout) {
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
    }

    private void addlisteners() {

        Tooltip.forComponent(searEvent);
        Tooltip.forComponent(searEvent).withText(
                "Special Event Assessment Ratig events are voluntarily submitted special events, which are sent to the Department of Homeland Security for a risk assessment.");

        Tooltip.forComponent(nsseEvent);
        Tooltip.forComponent(nsseEvent).withText(
                "A National Security Special Event is an event of national or international significance that is lead by Secret Service.");

        searEvent.addValueChangeListener(event -> searEventRanking.setVisible(event.getValue()));

        noAddressCheckbox.addValueChangeListener(event -> {
            boolean isVisible = !event.getValue();
            address1.setVisible(isVisible);
            address2.setVisible(isVisible);
            city.setVisible(isVisible);
            state.setVisible(isVisible);
            zipCode.setVisible(isVisible);
        });

        multiDayEvent.addValueChangeListener(event -> eventEndDate.setVisible(event.getValue()));
        recurrence.addValueChangeListener(event -> {
            boolean isRecurring = !"Never".equals(event.getValue());
            recurrUntil.setVisible(isRecurring);
            recurrUntil.setRequiredIndicatorVisible(isRecurring);
        });
    }

    private void setInitialVisibility() {
        // Initial visibility and selections
        searEventRanking.setVisible(false);
        eventEndDate.setVisible(false);
        recurrUntil.setVisible(false);
        locationType.setValue("Indoor");
        noAddressCheckbox.setValue(false);
        recurrence.setValue("Never");
        eventStartTime.setValue(LocalTime.MIDNIGHT);
        eventEndTime.setValue(LocalTime.MIDNIGHT);
    }

    private void configureComponents() {
        // Set properties for components
        eventTitle.setPlaceholder("Type here");
        searEventRanking.setPlaceholder("Select");
        searEventRanking.setItems("SEAR-I", "SEAR-II", "SEAR-III", "SEAR-IV", "SEAR-V");
        attendance.setPlaceholder("Type here");
        website.setPlaceholder("Type here");
        locationType.setLabel("Location");
        locationType.setItems("Indoor", "Outdoor");
        address1.setPlaceholder("Type here");
        address2.setPlaceholder("Type here");
        city.setPlaceholder("Type here");
        state.setPlaceholder("Select");
        zipCode.setPlaceholder("Type here");
        recurrence.setItems("Never", "Daily", "Weekly", "Bi-Weekly", "Monthly", "Every three months",
                "Every six months", "Annually");
        notes.setHeight("8rem");

        state.setItems(states);

        // Set colspan for certain fields
        FormLayout formLayout = (FormLayout) this.getChildren().findFirst().get();
        formLayout.setColspan(eventTitle, 2);
        formLayout.setColspan(searEventRanking, 2);
        formLayout.setColspan(attendance, 2);
        formLayout.setColspan(noAddressCheckbox, 2);
        formLayout.setColspan(website, 2);
        formLayout.setColspan(address1, 2);
        formLayout.setColspan(address2, 2);
        formLayout.setColspan(city, 2);
        formLayout.setColspan(locationType, 2);
        formLayout.setColspan(multiDayEvent, 2);
        formLayout.setColspan(notes, 2);

        eventStartDate.setPlaceholder("mm/dd/yyyy");
        eventEndDate.setPlaceholder("mm/dd/yyyy");
        recurrUntil.setPlaceholder("mm/dd/yyyy");

        binder.addValueChangeListener(event -> {
            if (valueChangeListener != null && binder.getBean() != null && binder.isValid()) {
                valueChangeListener.accept(binder.getBean());
            }
        });

    }

    private void bindFields() {
        binder.forField(eventTitle)
                .asRequired("Event title is required.")
                .bind(EventInformation::getTitle, EventInformation::setTitle);

        binder.forField(searEvent)
                .bind(EventInformation::isSearEvent, EventInformation::setSearEvent);

        binder.forField(attendance)
                .asRequired("Attendance is required.")
                .withValidator(value -> value.matches("\\d+") && Integer.parseInt(value) > 0,
                        "Attendance must be a number.")
                .bind(eventInformation -> String.valueOf(eventInformation.getAttendance()),
                        (eventInformation, stringValue) -> {
                            try {
                                eventInformation.setAttendance(Integer.parseInt(stringValue));
                            } catch (NumberFormatException e) {
                                eventInformation.setAttendance(0);

                            }
                        });

        binder.forField(eventStartDate)
                .asRequired("Start date is required.")
                .bind(EventInformation::getStartDate, EventInformation::setStartDate);

        binder.forField(eventStartTime)
                .asRequired("Start time is required.")
                .bind(EventInformation::getStartTime, EventInformation::setStartTime);

        binder.bind(address1, "address.address1");
        binder.bind(address2, "address.address2");
        binder.bind(city, "address.city");
        binder.bind(state, "address.state");
        binder.bind(zipCode, "address.zipCode");

        binder.bindInstanceFields(this);

    }

    @Override
    public boolean isValid() {
        return binder.isValid();
    }

    @Override
    public String getErrorMessage() {
        if (isValid()) {
            return "";
        } else {
            return binder.validate().getBeanValidationErrors().stream()
                    .map(error -> error.getErrorMessage())
                    .collect(Collectors.joining(", "));
        }
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void showErrors() {
        binder.validate().notifyBindingValidationStatusHandlers();
    }

    @Override
    public EventInformation getData() {
        return binder.getBean();
    }

    @Override
    public void addValueChangeListener(Consumer<EventInformation> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }


}
