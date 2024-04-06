package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WorkflowPage extends VerticalLayout {

    private final String title;

    public WorkflowPage(String title, Component content) {
        this.title = title;

        setHeight("75vh");

        add(content);
    }

    public String getTitle() {
        return title;
    }

}
