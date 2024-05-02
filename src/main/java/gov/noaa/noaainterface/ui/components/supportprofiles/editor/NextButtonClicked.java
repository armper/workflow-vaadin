package gov.noaa.noaainterface.ui.components.supportprofiles.editor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;

public class NextButtonClicked extends ComponentEvent<Component> {

    private final Object data;

    public NextButtonClicked(Component source, Object data) {
        super(source, true);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
