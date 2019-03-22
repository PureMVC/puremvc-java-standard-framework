//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.patterns.command;

/**
 * A utility class used by SimpleCommandTest.
 *
 * @see SimpleCommandTest SimpleCommandTest
 * @see org.puremvc.java.patterns.command.SimpleCommandTestCommand SimpleCommandTestCommand
 */
public class SimpleCommandTestVO {

    public int input;
    public int result;

    /**
     * Constructor.
     *
     * @param input the number to be fed to the SimpleCommandTestCommand
     */
    public SimpleCommandTestVO(int input) {
        this.input = input;
    }
}
