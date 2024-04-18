package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.events;

import com.vaadin.flow.component.ComponentEvent;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkflowSection;

public class PageChangeEvent<T> extends ComponentEvent<WorkflowSection> {
    private final int currentPageIndex;
    private final int totalPages;
    private final T data;

    public PageChangeEvent(WorkflowSection source, int currentPageIndex, int totalPages, T data) {
        super(source, false);
        this.currentPageIndex = currentPageIndex;
        this.totalPages = totalPages;
        this.data = data;

    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public T getData() {
        return data;
    }
}