//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.core;

import org.puremvc.java.interfaces.IMediator;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

/**
 * A Mediator class used by ViewTest.
 *
 * @see ViewTest ViewTest
 */
public class ViewTestMediator2 extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator2";

    /**
     * Constructor
     *
     * @param view view object
     */
    public ViewTestMediator2(Object view) {
        super(NAME, view);
    }

    public String[] listNotificationInterests() {
        // be sure that the mediator has some Observers created
        // in order to test removeMediator
        return new String[] {ViewTest.NOTE1, ViewTest.NOTE2};
    }

    public void handleNotification(INotification notification) {
        getViewTest().lastNotification = notification.getName();
    }

    public ViewTest getViewTest() {
        return (ViewTest) viewComponent;
    }

}
