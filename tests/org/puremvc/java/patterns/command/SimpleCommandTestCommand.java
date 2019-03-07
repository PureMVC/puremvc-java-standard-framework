//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.patterns.command;

import org.puremvc.java.interfaces.INotification;

/**
 * A SimpleCommand subclass used by SimpleCommandTest.
 *
 * @see org.puremvc.java.patterns.command.SimpleCommandTest SimpleCommandTest
 * @see org.puremvc.java.patterns.command.SimpleCommandTestVO SimpleCommandTestVO
 */
public class SimpleCommandTestCommand extends SimpleCommand {

    /**
     * Fabricate a result by multiplying the input by 2
     *
     * @param notification the <code>INotification</code> carrying the <code>SimpleCommandTestVO</code>
     */
    @Override
    public void execute(INotification notification) {
        SimpleCommandTestVO vo = (SimpleCommandTestVO) notification.getBody();

        // Fabricate a result
        vo.result = 2 * vo.input;
    }
}
