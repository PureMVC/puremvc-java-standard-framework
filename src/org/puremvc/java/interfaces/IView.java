/*
 PureMVC Java port by Frederic Saunier <frederic.saunier@puremvc.org>
 
 Adapted from sources of thoses different authors :
 	Donald Stinchfield <donald.stinchfield@puremvc.org>, et all
 	Ima OpenSource <opensource@ima.eu>
 	Anthony Quinault <anthony.quinault@puremvc.org>
 
 PureMVC - Copyright(c) 2006-10 Futurescale, Inc., Some rights reserved. 
 Your reuse is governed by the Creative Commons Attribution 3.0 License
*/
package org.puremvc.java.interfaces;

/**
 * The interface definition for a PureMVC View.
 * 
 * <P>
 * In PureMVC, <code>IView</code> implementors assume these responsibilities:
 * </P>
 * 
 * <P>
 * In PureMVC, the <code>View</code> class assumes these responsibilities:
 * <UL>
 * <LI>Maintain a cache of <code>IMediator</code> instances.</LI>
 * <LI>Provide methods for registering, retrieving, and removing
 * <code>IMediators</code>.</LI>
 * <LI>Managing the observer lists for each <code>INotification</code> in the
 * application.</LI>
 * <LI>Providing a method for attaching <code>IObservers</code> to an
 * <code>INotification</code>'s observer list.</LI>
 * <LI>Providing a method for broadcasting an <code>INotification</code>.</LI>
 * <LI>Notifying the <code>IObservers</code> of a given
 * <code>INotification</code> when it broadcast.</LI>
 * </UL>
 * 
 * @see org.puremvc.java.interfaces.IMediator IMediator
 * @see org.puremvc.java.interfaces.IObserver IObserver
 * @see org.puremvc.java.interfaces.INotification INotification
 */
public interface IView
{

	/**
	 * Register an <code>IObserver</code> to be notified of
	 * <code>INotifications</code> with a given name.
	 * 
	 * @param noteName
	 *            the name of the <code>INotifications</code> to notify this
	 *            <code>IObserver</code> of
	 * @param observer
	 *            the <code>IObserver</code> to register
	 */
	public void registerObserver( String noteName, IObserver observer );

	/**
	 * Notify the <code>IObservers</code> for a particular
	 * <code>INotification</code>.
	 * 
	 * <P>
	 * All previously attached <code>IObservers</code> for this
	 * <code>INotification</code>'s list are notified and are passed a
	 * reference to the <code>INotification</code> in the order in which they
	 * were registered.
	 * </P>
	 * 
	 * @param note
	 *            the <code>INotification</code> to notify
	 *            <code>IObservers</code> of.
	 */
	public void notifyObservers( INotification note );

	/**
	 * Register an <code>IMediator</code> instance with the <code>View</code>.
	 * 
	 * <P>
	 * Registers the <code>IMediator</code> so that it can be retrieved by
	 * name, and further interrogates the <code>IMediator</code> for its
	 * <code>INotification</code> interests.
	 * </P>
	 * <P>
	 * If the <code>IMediator</code> returns any <code>INotification</code>
	 * names to be notified about, an <code>Observer</code> is created
	 * encapsulating the <code>IMediator</code> instance's
	 * <code>handleNotification</code> method and registering it as an
	 * <code>Observer</code> for all <code>INotifications</code> the
	 * <code>IMediator</code> is interested in.
	 * </p>
	 * 
	 * @param mediator
	 *            a reference to the <code>IMediator</code> instance
	 */
	public void registerMediator( IMediator mediator );

	/**
	 * Retrieve an <code>IMediator</code> from the <code>View</code>.
	 * 
	 * @param mediatorName
	 *            the name of the <code>IMediator</code> instance to retrieve.
	 * @return the <code>IMediator</code> instance previously registered with
	 *         the given <code>mediatorName</code>.
	 */
	public IMediator retrieveMediator( String mediatorName );

	/**
	 * Remove an <code>IMediator</code> from the <code>View</code>.
	 * 
	 * @param mediatorName
	 *            name of the <code>IMediator</code> instance to be removed.
	 */
	public IMediator removeMediator( String mediatorName );
	
	/**
	 * Check if a Mediator is registered or not
	 * 
	 * @param mediatorName
	 * @return whether a Mediator is registered with the given <code>mediatorName</code>.
	 */
	public boolean hasMediator( String mediatorName );
}
