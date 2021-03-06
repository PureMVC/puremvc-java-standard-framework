//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.core;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.observer.Notification;

/**
 * A Notification class used by ViewTest.
 *
 * @see ViewTest ViewTest
 */
public class ViewTestNote extends Notification implements INotification {

    /**
     * The name of this Notification.
     */
    public static final String NAME = "ViewTestNote";

    /**
     * Constructor.
     *
     * @param name Ignored and forced to NAME.
     * @param body the body of the Notification to be constructed.
     */
    public ViewTestNote(String name, Object body) {
        super(name, body);
    }

    /**
     * Factory method.
     *
     * <P>This method creates new instances of the ViewTestNote class,
     * automatically setting the note name so you don't have to. Use
     * this as an alternative to the constructor.</P>
     *
     * @param body the body of the Notification to be constructed.
     * @return INotification
     */
    public static INotification create(Object body) {
        return  new ViewTestNote(NAME, body);
    }
}
