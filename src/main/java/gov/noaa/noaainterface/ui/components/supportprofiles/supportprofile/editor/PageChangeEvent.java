package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import com.vaadin.flow.component.ComponentEvent;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld.WorkflowSection;

public class PageChangeEvent extends ComponentEvent<WorkflowSection> {
    private final int currentPageIndex;
    private final int totalPages;

    public PageChangeEvent(WorkflowSection source, int currentPageIndex, int totalPages) {
        super(source, false);
        this.currentPageIndex = currentPageIndex;
        this.totalPages = totalPages;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }
}