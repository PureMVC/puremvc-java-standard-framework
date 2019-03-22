//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.core;

import org.puremvc.java.interfaces.IMediator;
import org.puremvc.java.patterns.mediator.Mediator;

/**
 * A Mediator class used by ViewTest.
 *
 * @see ViewTest ViewTest
 */
public class ViewTestMediator extends Mediator implements IMediator {

    /**
     * The Mediator name
     */
    public static final String NAME = "ViewTestMediator";

    /**
     * Constructor
     *
     * @param view view object
     */
    public ViewTestMediator(Object view) {
        super (NAME, view);
    }

    public String[] listNotificationInterests() {
        // be sure that the mediator has some Observers created
        // in order to test removeMediator
        return new String[] { "ABC", "DEF", "GHI"};
    }
}
