package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;

public class SupportScheduleLayout extends VerticalLayout implements ValidatableForm<SupportSchedule> {
    private Binder<SupportSchedule> binder = new Binder<>(SupportSchedule.class);

    private Consumer<SupportSchedule> valueChangeListener;

    private final DatePicker dailyBriefingTime = new DatePicker("Daily Briefing Time");
    private final DatePicker supportStartDate = new DatePicker("Support Start Date");

    private final TextArea briefingNotes = new TextArea("Briefing Notes");

    public SupportScheduleLayout() {
        binder.setBean(new SupportSchedule());

        FormLayout formLayout = createFormLayout();
        add(formLayout);

        setResponsiveSteps(formLayout);
        addlisteners();
        configureComponents();
        bindFields();
    }

    private void bindFields() {
        binder.forField(dailyBriefingTime)
                .asRequired("Daily Briefing Time is required")
                .bind(SupportSchedule::getDailyBriefingTime, SupportSchedule::setDailyBriefingTime);

        binder.bindInstanceFields(this);
    }

    private void configureComponents() {
        briefingNotes.setPlaceholder("Frequency and format of briefings");
        Tooltip.forComponent(supportStartDate);
        Tooltip.forComponent(supportStartDate)
                .withText("If IDSS begins before the event begins, then choose the day when IDSS will start.");

        binder.addValueChangeListener(event -> {
            if (valueChangeListener != null && binder.getBean() != null && binder.isValid()) {
                valueChangeListener.accept(binder.getBean());
            }
        });
    }

    private void addlisteners() {

    }

    private void setResponsiveSteps(FormLayout formLayout) {
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(dailyBriefingTime, 1);
        formLayout.add(supportStartDate, 1);
        formLayout.add(briefingNotes, 2);
        return formLayout;
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
    public void showErrors() {
        binder.validate().notifyBindingValidationStatusHandlers();
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public SupportSchedule getData() {
        return binder.getBean();
    }

    @Override
    public void addValueChangeListener(Consumer<SupportSchedule> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

  

}
