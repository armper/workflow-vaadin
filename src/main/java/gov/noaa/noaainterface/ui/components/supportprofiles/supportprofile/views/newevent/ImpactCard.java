package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.newevent;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class ImpactCard extends VerticalLayout {

    private final H4 levelSpan = new H4();
    private final H4 riskSpan = new H4();
    private final Span impactStatementSpan = new Span();
    private final Span actionsSpan = new Span();
    private final Button editButton = new Button("Edit");
    private final Button deleteButton = new Button("Delete");

    private Impact impact = new Impact();

    public ImpactCard(Impact impact) {
        this.impact = impact;

        addClassName(LumoUtility.Border.ALL);
        levelSpan.getStyle().set("font-weight", "bold");
        levelSpan.setText(impact.getLevel() + ":");
        riskSpan.getStyle().set("font-weight", "bold");
        riskSpan.setText(impact.getRisk());
        impactStatementSpan.setText(impact.getImpactStatement());
        actionsSpan.setText(impact.getActions());

        Span spacer = new Span();
        spacer.getStyle().set("flex-grow", "1");

        HorizontalLayout headerLayout = new HorizontalLayout(levelSpan, riskSpan, spacer, editButton, deleteButton);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(Alignment.BASELINE);
        headerLayout.expand(spacer);

        HorizontalLayout mainLayout = new HorizontalLayout(
                new VerticalLayout(new Span("Impact Statement"), impactStatementSpan),
                new VerticalLayout(new Span("Actions"), actionsSpan));
        mainLayout.setWidthFull();

        add(headerLayout, mainLayout);
        setWidth("100%");

    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

}
