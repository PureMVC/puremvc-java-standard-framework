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
public class ViewTestMediator4 extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator4";

    /**
     * Constructor
     *
     * @param view view object
     */
    public ViewTestMediator4(Object view) {
        super(NAME, view);
    }

    public ViewTest getViewTest() {
        return (ViewTest) viewComponent;
    }

    @Override
    public void onRegister() {
        getViewTest().onRegisterCalled = true;
    }

    @Override
    public void onRemove() {
        getViewTest().onRemoveCalled = true;
    }
}
