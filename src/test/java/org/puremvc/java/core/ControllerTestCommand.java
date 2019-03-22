//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.core;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

/**
 * A SimpleCommand subclass used by ControllerTest.
 *
 * @see ControllerTest ControllerTest
 * @see ControllerTestVO ControllerTestVO
 */
public class ControllerTestCommand extends SimpleCommand {

    /**
     * Fabricate a result by multiplying the input by 2
     *
     * @param notification the note carrying the ControllerTestVO
     */
    public void execute(INotification notification) {
        ControllerTestVO vo = (ControllerTestVO)notification.getBody();

        vo.result = 2 * vo.input;
    }
}
