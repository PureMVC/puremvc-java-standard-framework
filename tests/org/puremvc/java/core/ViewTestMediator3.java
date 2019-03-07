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
 * @see org.puremvc.java.core.ViewTest ViewTest
 */
public class ViewTestMediator3 extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator3";

    /**
     * Constructor
     *
     * @param view view object
     */
    public ViewTestMediator3(Object view) {
        super(NAME, view);
    }

    public String[] listNotificationInterests() {
        // be sure that the mediator has some Observers created
        // in order to test removeMediator
        return new String[] {ViewTest.NOTE3};
    }

    @Override
    public void handleNotification(INotification notification) {
        getViewTest().lastNotification = notification.getName();
    }

    public ViewTest getViewTest() {
        return (ViewTest) viewComponent;
    }

}
