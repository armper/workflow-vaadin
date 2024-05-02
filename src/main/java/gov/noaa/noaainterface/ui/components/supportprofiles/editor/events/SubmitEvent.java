package gov.noaa.noaainterface.ui.components.supportprofiles.editor.events;

import com.vaadin.flow.component.ComponentEvent;

import gov.noaa.noaainterface.ui.components.supportprofiles.editor.WorkflowSection;


public class SubmitEvent extends ComponentEvent<WorkflowSection> {

    public SubmitEvent(WorkflowSection source) {
        super(source, false);
    }

}
