package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import com.vaadin.flow.component.ComponentEvent;

public class SubmitEvent extends ComponentEvent<WorkflowSection> {

    public SubmitEvent(WorkflowSection source) {
        super(source, false);
    }

}
