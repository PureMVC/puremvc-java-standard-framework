/*
   PureMVC Java Port by Donald Stinchfield <donald.stinchfield@puremvc.org>, et al.
   PureMVC - Copyright(c) 2006-08 Futurescale, Inc., Some rights reserved.
   Your reuse is governed by the Creative Commons Attribution 3.0 License
*/

package org.puremvc.java.core.view;

import java.util.Hashtable;
import java.util.Enumeration;

import org.puremvc.java.interfaces.IFunction;
import org.puremvc.java.interfaces.IMediator;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.interfaces.IObserver;
import org.puremvc.java.interfaces.IView;
import org.puremvc.java.patterns.observer.Observer;
import org.puremvc.java.patterns.observer.Observers;

/**
 * A Singleton <code>IView</code> implementation.
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
 * @see org.puremvc.java.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.patterns.observer.Observer Observer
 * @see org.puremvc.java.patterns.observer.Notification Notification
 */
public class View implements IView
{

	// Singleton instance
	private static View instance;

	// Mapping of Mediator names to Mediator instances
	// Mapping of Notification names to Observer lists
	private Hashtable mediatorMap, observerMap;

	/**
	 * Constructor.
	 * 
	 * <P>
	 * This <code>IView</code> implementation is a Singleton, so you should
	 * not call the constructor directly, but instead call the static Singleton
	 * Factory method <code>View.getInstance()</code>
	 * 
	 * @throws Error
	 *              Error if Singleton instance has already been constructed
	 * 
	 */
	protected View( )
	{
		instance = this;
		this.mediatorMap = new Hashtable();
		this.observerMap = new Hashtable();
		initializeView();
	}

	/**
	 * Initialize the Singleton View instance.
	 * 
	 * <P>
	 * Called automatically by the constructor, this is your opportunity to
	 * initialize the Singleton instance in your subclass without overriding
	 * the constructor.
	 * </P>
	 * 
	 */
	protected void initializeView( )
	{
	}

	/**
	 * View Singleton Factory method.
	 * 
	 * @return the Singleton instance of <code>View</code>
	 */
	public synchronized static View getInstance( )
	{
		if (instance == null) {
			instance = new View();
		}
		return instance;
	}

	/**
	 * Notify the <code>Observers</code> for a particular
	 * <code>Notification</code>.
	 * 
	 * <P>
	 * All previously attached <code>Observers</code> for this
	 * <code>Notification</code>'s list are notified and are passed a
	 * reference to the <code>Notification</code> in the order in which they
	 * were registered.
	 * </P>
	 * 
	 * @param note
	 *             the <code>Notification</code> to notify
	 *             <code>Observers</code> of.
	 */
	public void notifyObservers( INotification note )
	{
		Observers observers = (Observers) this.observerMap
				.get(note.getName());
		if (observers != null) {
			observers.notifyObservers(note);
		}
	}

	/**
	 * Register an <code>Mediator</code> instance with the <code>View</code>.
	 * 
	 * <P>
	 * Registers the <code>Mediator</code> so that it can be retrieved by
	 * name, and further interrogates the <code>Mediator</code> for its
	 * <code>Notification</code> interests.
	 * </P>
	 * <P>
	 * If the <code>Mediator</code> returns any <code>Notification</code>
	 * names to be notified about, an <code>Observer</code> is created
	 * encapsulating the <code>Mediator</code> instance's
	 * <code>handleNotification</code> method and registering it as an
	 * <code>Observer</code> for all <code>Notifications</code> the
	 * <code>Mediator</code> is interested in.
	 * </p>
	 * 
	 * @param mediator
	 *             the name to associate with this <code>IMediator</code>
	 *             instance
	 */
	public void registerMediator( final IMediator mediator )
	{
		// Register the Mediator for retrieval by name
		this.mediatorMap.put(mediator.getMediatorName(), mediator);

		// Get Notification interests, if any.
		String[] noteInterests = mediator.listNotificationInterests();
		if (noteInterests.length == 0) {
			return;
		}

		// Create java style function ref to mediator.handleNotification
		IFunction function = new IFunction()
		{

			public void onNotification( INotification notification )
			{
				mediator.handleNotification(notification);
			}
		};

		// Create Observer
		Observer observer = new Observer(function, mediator);

		// Register Mediator as Observer for its list of Notification
		// interests
		for (int i = 0; i < noteInterests.length; i++) {
			registerObserver(noteInterests[i], observer);
		}
	}

	/**
	 * Register an <code>Observer</code> to be notified of
	 * <code>INotifications</code> with a given name.
	 * 
	 * @param notificationName
	 *             the name of the <code>Notifications</code> to notify this
	 *             <code>Observer</code> of
	 * @param observer
	 *             the <code>Observer</code> to register
	 */
	public void registerObserver( String notificationName, IObserver observer )
	{
		if (this.observerMap.get(notificationName) != null) {
			Observers observers = (Observers) this.observerMap.get(notificationName);
			observers.addObserver(observer);
		} else {
			this.observerMap.put(notificationName, new Observers(notificationName, observer));
		}
	}

	/**
	 * Remove an <code>Mediator</code> from the <code>View</code>.
	 * 
	 * @param mediatorName
	 *             name of the <code>Mediator</code> instance to be removed.
	 */
	public IMediator removeMediator( String mediatorName )
	{
		// Remove all Observers with a reference to this Mediator
		// also, when an notification's observer list length falls to
		// zero, remove it.
		Observers temp = null;
		IObserver observer = null;

		for (Enumeration enu = this.observerMap.elements(); enu.hasMoreElements();) {
			temp = (Observers) enu.nextElement();
			for (Enumeration e = temp.getObservers().elements(); e
					.hasMoreElements();) {
				observer = (IObserver) e.nextElement();
				if (observer
						.compareNotifyContext(retrieveMediator(mediatorName))) {
					temp.deleteObserver(observer);
				}
				if (temp.getObservers().size() < 1) {
					this.observerMap.remove(temp.getNotification());
				}
			}
		}

		// Remove the reference to the Mediator itself
		return (IMediator) this.mediatorMap.remove(mediatorName);

	}

	/**
	 * Retrieve an <code>Mediator</code> from the <code>View</code>.
	 * 
	 * @param mediatorName
	 *             the name of the <code>Mediator</code> instance to
	 *             retrieve.
	 * @return the <code>Mediator</code> instance previously registered with
	 *         the given <code>mediatorName</code>.
	 */
	public IMediator retrieveMediator( String mediatorName )
	{
		return (IMediator) this.mediatorMap.get(mediatorName);
	}
}
