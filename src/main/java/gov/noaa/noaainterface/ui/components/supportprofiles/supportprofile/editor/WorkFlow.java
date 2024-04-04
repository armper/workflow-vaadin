package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;

public class WorkFlow extends VerticalLayout {

    private int currentPageIndex = 0;
    
    private final Component[] pages;
    private final Div pagesContainer;
    private final Button nextButton;
    private final Button previousButton;
    private final ProgressBar progressBar;
    private final Span progressLabel;
    private final String itemName;

    public WorkFlow(String title, String subTitle, String itemName, Component... pages) {
        this.pages = pages;
        this.itemName = itemName;

        this.pagesContainer = new Div();
        pagesContainer.setWidthFull();
        pagesContainer.setHeight("75vh");

        this.nextButton = new Button("Next");
        this.previousButton = new Button("Back");

        this.progressBar = new ProgressBar();
        this.progressLabel = new Span();
        updateProgress();

        setupTitleAndButtons(title, subTitle);

        setupPages();

        setupNavigationButtons();

        setupProgressIndicator();

        updateView();
    }

    private void setupTitleAndButtons(String title, String subTitle) {
        H2 titleComponent = new H2(title);
        Span subTitleComponent = new Span(subTitle);
        subTitleComponent.setMaxWidth("60%");

        Button discardDraftButton = new Button("Discard draft", e -> {
            /* Handle discard logic */
        });
        discardDraftButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        discardDraftButton.setIcon(new Icon(VaadinIcon.CLOSE));

        Button saveAndCloseButton = new Button("Save and Close", e -> {
            /* Handle save and close logic */
        });
        saveAndCloseButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        saveAndCloseButton.setIcon(new Icon(VaadinIcon.FOLDER_OPEN_O));

        HorizontalLayout titleAndButtons = new HorizontalLayout(titleComponent, discardDraftButton, saveAndCloseButton);
        titleAndButtons.setWidthFull();
        titleAndButtons.setFlexGrow(1, titleComponent);

        add(titleAndButtons, subTitleComponent);
    }

    private void setupPages() {
        pagesContainer.removeAll();
        if (pages.length > 0) {
            pagesContainer.add(pages[currentPageIndex]);
        }
        add(pagesContainer);
    }

    private void setupNavigationButtons() {
        nextButton.addClickListener(e -> {
            if (currentPageIndex < pages.length - 1) {
                currentPageIndex++;
                updateView();
            }
        });

        previousButton.addClickListener(e -> {
            if (currentPageIndex > 0) {
                currentPageIndex--;
                updateView();
            }
        });

        Span spacer = new Span();
        HorizontalLayout buttons = new HorizontalLayout(previousButton, spacer, nextButton);
        buttons.setWidthFull();
        buttons.setFlexGrow(1, spacer);

        add(buttons);
    }

    private void setupProgressIndicator() {
        progressBar.setWidthFull();
        progressBar.setValue(0);

        add(progressBar, progressLabel);
    }

    private void updateView() {
        pagesContainer.removeAll();
        if (currentPageIndex >= 0 && currentPageIndex < pages.length) {
            pagesContainer.add(pages[currentPageIndex]);
        }

        previousButton.setEnabled(currentPageIndex > 0);
        nextButton.setEnabled(currentPageIndex < pages.length - 1);

        updateProgress();

    }

    private void updateProgress() {
        double progress = (currentPageIndex + 1) / (double) pages.length;
        progressBar.setValue(progress);

        progressLabel.setText(String.format(itemName + " Information %d of %d", currentPageIndex + 1, pages.length));
    }
}
