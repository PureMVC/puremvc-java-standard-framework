/*
   PureMVC Java Port by Donald Stinchfield <donald.stinchfield@puremvc.org>, et al.
   PureMVC - Copyright(c) 2006-08 Futurescale, Inc., Some rights reserved.
   Your reuse is governed by the Creative Commons Attribution 3.0 License
*/

package org.puremvc.java.interfaces;

/**
 * This interface must be implemented by all classes that want to be notified of
 * a notification.
 */
public interface IFunction
{

	/**
	 * @param notification
	 */
	public void onNotification( INotification notification );
}
