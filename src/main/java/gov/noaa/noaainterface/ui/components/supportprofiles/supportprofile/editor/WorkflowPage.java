package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import java.util.function.Consumer;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.events.PageChangeEvent;
import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.interfaces.ValidatableForm;

public class WorkflowPage<T> extends VerticalLayout {

    private final String title;
    private ValidatableForm<T> content;

    private Button discardDraft = new Button("Discard Draft");
    private Button saveAndClose = new Button("Save and Close");

    public WorkflowPage(String title, ValidatableForm<T> content) {
        this.title = title;
        this.content = content;

        setHeight("85vh");

        // add scrollbars for overlfow
        addClassNames(Overflow.AUTO);

        H2 titleComponent = new H2(title);
        Span spacer = new Span();
        HorizontalLayout buttonLayout = new HorizontalLayout(titleComponent, spacer, discardDraft, saveAndClose);
        // buttons all the way to the right of the page
        buttonLayout.setAlignItems(Alignment.END);
        buttonLayout.setWidthFull();
        buttonLayout.setFlexGrow(1, spacer);


        add(buttonLayout, content.getComponent());

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
