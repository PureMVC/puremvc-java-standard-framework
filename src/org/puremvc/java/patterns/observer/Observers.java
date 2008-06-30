/*
   PureMVC Java Port by Donald Stinchfield <donald.stinchfield@puremvc.org>, et al.
   PureMVC - Copyright(c) 2006-08 Futurescale, Inc., Some rights reserved.
   Your reuse is governed by the Creative Commons Attribution 3.0 License
*/

package org.puremvc.java.patterns.observer;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.interfaces.IObserver;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Helper class that contains all observers for a notification
 * 
 * @see org.puremvc.java.interfaces IObservers
 * @see org.puremvc.java.interfaces INotification
 */
public class Observers
{
	private Vector observers; // see View.registerObservers, "in the order in which they were registered"
	
	private String notificationName;

	/**
	 * Constructor
	 * 
	 * @param note
	 * @param observer
	 */
	public Observers( String note, IObserver observer )
	{
		if (note != null && observer != null) {
			this.observers = new Vector();
			this.observers.addElement( observer );
			this.notificationName = note;
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * Returns all observers
	 * 
	 * @return <code>Hashtable</code>
	 */
	public Vector getObservers( )
	{
		return this.observers;
	}

	/**
	 * Add new observer
	 * 
	 * @param observer
	 */
	public void addObserver( IObserver observer )
	{
		if (observer != null) {
			this.observers.addElement( observer );
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * Delete an observer
	 * 
	 * 
	 * @param observer
	 */
	public void deleteObserver( IObserver observer )
	{
		if (observer != null) {
			this.observers.removeElement( observer );
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * Notify all observers of the notification
	 * 
	 * @param note
	 */
	public void notifyObservers( INotification note )
	{
		if (note != null) {
			IObserver temp = null;
			for (Enumeration enu = this.observers.elements(); enu.hasMoreElements();) {
				temp = (IObserver) enu.nextElement();
				temp.notifyObserver( note );
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * Return the notification
	 * 
	 * @return String
	 */
	public String getNotification( )
	{
		return this.notificationName;
	}
}
