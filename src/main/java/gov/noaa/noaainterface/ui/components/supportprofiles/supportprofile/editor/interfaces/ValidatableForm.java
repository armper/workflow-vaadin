package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces;

import java.util.function.Consumer;

import com.vaadin.flow.component.Component;

public interface ValidatableForm<T> {
    boolean isValid();

    String getErrorMessage();

    void showErrors();

    Component getComponent();

    T getData();

    void addValueChangeListener(Consumer<T> object);

  
}
