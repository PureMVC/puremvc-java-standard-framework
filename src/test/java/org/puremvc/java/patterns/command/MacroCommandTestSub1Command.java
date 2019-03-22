//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.patterns.command;

import org.puremvc.java.interfaces.INotification;

/**
 * A SimpleCommand subclass used by MacroCommandTestCommand.
 *
 * @see MacroCommandTest MacroCommandTest
 * @see MacroCommandTestCommand MacroCommandTestCommand
 * @see MacroCommandTestVO MacroCommandTestVO
 */
public class MacroCommandTestSub1Command extends SimpleCommand {

    /**
     * Fabricate a result by multiplying the input by 2
     *
     * @param notification the <code>INotification</code> carrying the <code>MacroCommandTestVO</code>
     */
    public void execute(INotification notification) {
        MacroCommandTestVO vo = (MacroCommandTestVO)notification.getBody();

        // Fabricate a result
        vo.result1 = 2 * vo.input;
    }
}
