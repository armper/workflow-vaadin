package gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import gov.noaa.noaainterface.ui.components.supportprofiles.supportprofile.editor.WorkFlow;

@PageTitle("Hello World")
@Route(value = "hello-world")
@RouteAlias(value = "")
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        VerticalLayout testPageOne = new VerticalLayout();
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        //<theme-editor-local-classname>
        sayHello.addClassName("hello-world-view-button-1");
        sayHello.addClickShortcut(Key.ENTER);
        sayHello.addClickListener(e -> {
            Notification.show("Hello, " + name.getValue());
        });

        VerticalLayout testPageTwo = new VerticalLayout();
        testPageTwo.add(new Button("Test button"));

        VerticalLayout testPageThree = new VerticalLayout();
        testPageThree.add(new Span(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nec purus nec nunc tincidunt tincidunt."),
                new Button("Test button"));

        VerticalLayout testPageFour = new VerticalLayout();
        testPageFour.add(new Span(
                "Donec auctor, nunc nec fermentum ultricies, nunc sapien ultricies nunc, nec fermentum nunc sapien nec nunc."),
                new Button("Test button"));

        testPageOne.add(name, sayHello);

        WorkFlow workFlow = new WorkFlow("Requesting Partner Information",
                "Contact information for the partner who put in the request for IDSS for an event. If you do not see the partner listed, go to the Contacts page and enter them as a new contact.",
                "Event", testPageOne, testPageTwo, testPageThree, testPageFour);

        add(workFlow);
    }

}
