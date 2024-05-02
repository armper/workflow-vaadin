package gov.noaa.noaainterface.ui.components.supportprofiles.editor.newevent;

import java.util.UUID;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import gov.noaa.noaainterface.ui.components.supportprofiles.editor.dtos.Impact;

public class ImpactCard extends VerticalLayout {

    private static final int MAX_LENGTH = 125;

    private final H4 levelSpan = new H4();
    private final H4 riskSpan = new H4();
    private final Span impactStatementSpan = new Span();
    private final Span actionsSpan = new Span();
    private final Span impactStatementFull = new Span();
    private final Span actionsFull = new Span();
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");
    private final Button showMoreImpact = new Button("Show more");
    private final Button showMoreActions = new Button("Show more");

    private Impact impact;

    public ImpactCard(Impact impact) {
        this.impact = impact;

        addClassName(LumoUtility.Border.ALL);
        levelSpan.getStyle().set("font-weight", "bold");
        levelSpan.setText(impact.getLevel() + ":");
        riskSpan.getStyle().set("font-weight", "bold");
        riskSpan.setText(impact.getRisk());
        updateTextDisplays(impact);

        Span spacer = new Span();
        spacer.getStyle().set("flex-grow", "1");

        HorizontalLayout headerLayout = new HorizontalLayout(levelSpan, riskSpan, spacer, editButton, deleteButton);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.BASELINE);
        headerLayout.expand(spacer);

        showMoreImpact.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        showMoreActions.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        showMoreImpact.setIcon(new Icon(VaadinIcon.CARET_DOWN));
        showMoreActions.setIcon(new Icon(VaadinIcon.CARET_DOWN));

        HorizontalLayout mainLayout = new HorizontalLayout(
                new VerticalLayout(new Span("Impact Statement"), impactStatementSpan, showMoreImpact),
                new VerticalLayout(new Span("Actions"), actionsSpan, showMoreActions));
        mainLayout.setWidthFull();

        add(headerLayout, mainLayout);
        setWidth("100%");

        setupShowMoreLess();
    }

    private void updateTextDisplays(Impact impact) {
        impactStatementFull.setText(impact.getImpactStatement());
        actionsFull.setText(impact.getActions());

        impactStatementSpan.setText(truncateText(impact.getImpactStatement()));
        actionsSpan.setText(truncateText(impact.getActions()));
    }

    private String truncateText(String text) {
        return text.length() > MAX_LENGTH ? text.substring(0, MAX_LENGTH) + " ..." : text;
    }

    private void setupShowMoreLess() {
        showMoreImpact.addClickListener(e -> toggleText(impactStatementSpan, impactStatementFull, showMoreImpact));
        showMoreActions.addClickListener(e -> toggleText(actionsSpan, actionsFull, showMoreActions));
    }

    private void toggleText(Span displaySpan, Span fullTextSpan, Button toggleButton) {
        if (toggleButton.getText().equals("Show more")) {
            displaySpan.setText(fullTextSpan.getText());
            toggleButton.setText("Show less");
            toggleButton.setIcon(new Icon(VaadinIcon.CARET_UP)); 
        } else {
            displaySpan.setText(truncateText(fullTextSpan.getText()));
            toggleButton.setText("Show more");
            toggleButton.setIcon(new Icon(VaadinIcon.CARET_DOWN));
        }
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
        updateDisplay();
    }

    private void updateDisplay() {
        levelSpan.setText(impact.getLevel() + ":");
        riskSpan.setText(impact.getRisk());
        updateTextDisplays(impact);
    }

    public Impact getImpact() {
        return impact;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public UUID getImpactId() {
        return impact.getId();
    }

}
