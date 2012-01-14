/*
 PureMVC Java port by Frederic Saunier <frederic.saunier@puremvc.org>
 
 Adapted from sources of thoses different authors :
 	Donald Stinchfield <donald.stinchfield@puremvc.org>, et all
 	Ima OpenSource <opensource@ima.eu>
 	Anthony Quinault <anthony.quinault@puremvc.org>
 
 PureMVC - Copyright(c) 2006-10 Futurescale, Inc., Some rights reserved. 
 Your reuse is governed by the Creative Commons Attribution 3.0 License
*/
package org.puremvc.java.patterns.observer;

import org.puremvc.java.patterns.facade.Facade;

/**
 * A Base <code>INotifier</code> implementation.
 * 
 * <P>
 * <code>MacroCommand, Command, Mediator</code> and <code>Proxy</code> all
 * have a need to send <code>Notifications</code>.
 * <P>
 * <P>
 * The <code>INotifier</code> interface provides a common method called
 * <code>sendNotification</code> that relieves implementation code of the
 * necessity to actually construct <code>Notifications</code>.
 * </P>
 * 
 * <P>
 * The <code>Notifier</code> class, which all of the above mentioned classes
 * extend, provides an initialized reference to the <code>Facade</code>
 * Singleton, which is required for the convienience method for sending
 * <code>Notifications</code>, but also eases implementation as these classes
 * have frequent <code>Facade</code> interactions and usually require access
 * to the facade anyway.
 * </P>
 * 
 * @see org.puremvc.java.patterns.facade.Facade Facade
 * @see org.puremvc.java.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.patterns.proxy.Proxy Proxy
 * @see org.puremvc.java.patterns.command.SimpleCommand SimpleCommand
 * @see org.puremvc.java.patterns.command.MacroCommand MacroCommand
 */
public class Notifier
{
	// The Multiton Key for this app
	/**
	 * Local reference to the Facade Singleton
	 */
	protected Facade facade = Facade.getInstance();

	/**
	 * Send an <code>INotification</code>s.
	 * 
	 * <P>
	 * Keeps us from having to construct new notification instances in our
	 * implementation code.
	 * 
	 * @param notificationName
	 *            the name of the notiification to send
	 * @param body
	 *            the body of the notification (optional)
	 * @param type
	 *            the type of the notification (optional)
	 */

	public void sendNotification( String notificationName, Object body,	String type )
	{
		facade.sendNotification( notificationName, body, type );
	}
	
	/**
	 * Send an <code>INotification</code>s.
	 * 
	 * <P>
	 * Keeps us from having to construct new notification instances in our
	 * implementation code.
	 * 
	 * @param notificationName
	 *            the name of the notiification to send
	 * @param body
	 *            the body of the notification (optional)
	 */

	public void sendNotification( String notificationName, Object body)
	{
		facade.sendNotification( notificationName, body);
	}
	
	/**
	 * Send an <code>INotification</code>s.
	 * 
	 * <P>
	 * Keeps us from having to construct new notification instances in our
	 * implementation code.
	 * 
	 * @param notificationName
	 *            the name of the notiification to send
	 */

	public void sendNotification( String notificationName)
	{
		facade.sendNotification( notificationName);
	}

}
