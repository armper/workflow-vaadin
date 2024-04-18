package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.events.PageChangeEvent;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;

public class WorkflowPage<T> extends VerticalLayout {

    private final String title;
    private ValidatableForm<T> content;

    public WorkflowPage(String title, ValidatableForm<T> content) {
        this.title = title;
        this.content = content;

        setHeight("85vh");

        // add scrollbars for overlfow
        addClassNames(Overflow.AUTO);

        add(content.getComponent());
        
    }

    public String getTitle() {
        return title;
    }

    public boolean isValid() {
        return content.isValid();
    }

    public void showErrors() {
        content.showErrors();
    }

    public Registration addPageChangeListener(ComponentEventListener<PageChangeEvent<?>> listener) {
        @SuppressWarnings("unchecked")
        Registration registration = addListener((Class<PageChangeEvent<?>>) (Class<?>) PageChangeEvent.class, listener);
        return registration;
    }

    public T getData() {
        return content.getData();
    }

    public void addValueChangeListener(Consumer<T> object) {
        content.addValueChangeListener(object);
    }

   

}
