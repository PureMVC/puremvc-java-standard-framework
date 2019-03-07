//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.patterns.facade;

import org.puremvc.java.core.Controller;
import org.puremvc.java.core.Model;
import org.puremvc.java.core.View;
import org.puremvc.java.interfaces.*;
import org.puremvc.java.patterns.observer.Notification;

import java.util.function.Supplier;

/**
 * A base Singleton <code>IFacade</code> implementation.
 *
 * <P>
 * In PureMVC, the <code>Facade</code> class assumes these
 * responsibilities:
 * <UL>
 * <LI>Initializing the <code>Model</code>, <code>View</code>
 * and <code>Controller</code> Singletons.</LI>
 * <LI>Providing all the methods defined by the <code>IModel,
 * IView, &amp; IController</code> interfaces.</LI>
 * <LI>Providing the ability to override the specific <code>Model</code>,
 * <code>View</code> and <code>Controller</code> Singletons created.</LI>
 * <LI>Providing a single point of contact to the application for
 * registering <code>Commands</code> and notifying <code>Observers</code></LI>
 * </UL>
 * <P>
 * Example usage:
 * <pre>
 *  {@code
 *	import org.puremvc.as3.patterns.facade.Facade;
 *
 *	import com.me.myapp.model.*;
 *	import com.me.myapp.view.*;
 *	import com.me.myapp.controller.*;
 *
 *	public class MyFacade extends Facade
 *	{
 *		// Notification constants. The Facade is the ideal
 *		// location for these constants, since any part
 *		// of the application participating in PureMVC
 *		// Observer Notification will know the Facade.
 *		public static final String GO_COMMAND = "go";
 *
 *		// Override Singleton Factory method
 *		public synchronized static IFacade getInstance(Supplier<IFacade> facadeSupplier) {
 *			if(instance == null) instance = facadeSupplier.get();
 *			return instance;
 *		}
 *
 *		// optional initialization hook for Facade
 *		public void initializeFacade() {
 *			super.initializeFacade();
 *			// do any special subclass initialization here
 *		}
 *
 *		// optional initialization hook for Controller
 *		public void initializeController()  {
 *			// call super to use the PureMVC Controller Singleton.
 *			super.initializeController();
 *
 *			// Otherwise, if you're implmenting your own
 *			// IController, then instead do:
 *			// if ( controller != null ) return;
 *			// controller = MyAppController.getInstance(() -> new MyAppController());
 *
 *			// do any special subclass initialization here
 *			// such as registering Commands
 *			registerCommand( GO_COMMAND, () -> new com.me.myapp.controller.GoCommand() )
 *		}
 *
 *		// optional initialization hook for Model
 *		public void initializeModel() {
 *			// call super to use the PureMVC Model Singleton.
 *			super.initializeModel();
 *
 *			// Otherwise, if you're implmenting your own
 *			// IModel, then instead do:
 *			// if ( model != null ) return;
 *			// model = MyAppModel.getInstance(() -> new MyAppModel());
 *
 *			// do any special subclass initialization here
 *			// such as creating and registering Model proxys
 *			// that don't require a facade reference at
 *			// construction time, such as fixed type lists
 *			// that never need to send Notifications.
 *			registerProxy( new USStateNamesProxy() );
 *
 *			// CAREFUL: Can't reference Facade instance in constructor
 *			// of new Proxys from here, since this step is part of
 *			// Facade construction!  Usually, Proxys needing to send
 *			// notifications are registered elsewhere in the app
 *			// for this reason.
 *		}
 *
 *		// optional initialization hook for View
 *		public void initializeView() {
 *			// call super to use the PureMVC View Singleton.
 *			super.initializeView();
 *
 *			// Otherwise, if you're implmenting your own
 *			// IView, then instead do:
 *			// if ( view != null ) return;
 *			// view = MyAppView.getInstance(() -> new MyAppView());
 *
 *			// do any special subclass initialization here
 *			// such as creating and registering Mediators
 *			// that do not need a Facade reference at construction
 *			// time.
 *			registerMediator( new LoginMediator() );
 *
 *			// CAREFUL: Can't reference Facade instance in constructor
 *			// of new Mediators from here, since this is a step
 *			// in Facade construction! Usually, all Mediators need
 *			// receive notifications, and are registered elsewhere in
 *			// the app for this reason.
 *		}
 *	}
 * }
 * </pre>
 *
 * @see org.puremvc.java.core.Model Model
 * @see org.puremvc.java.core.View View
 * @see org.puremvc.java.core.Controller Controller
 * @see org.puremvc.java.patterns.observer.Notification Notification
 * @see org.puremvc.java.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.patterns.proxy.Proxy Proxy
 * @see org.puremvc.java.patterns.command.SimpleCommand SimpleCommand
 * @see org.puremvc.java.patterns.command.MacroCommand MacroCommand
 */
public class Facade implements IFacade {

    // Private references to Model, View and Controlle
    protected IController controller;
    protected IModel model;
    protected IView view;

    // The Singleton Facade instance.
    protected static IFacade instance;

    // Message Constants
    protected final String SINGLETON_MSG = "Facade Singleton already constructed!";

    /**
     * Constructor.
     *
     * <P>
     * This <code>IFacade</code> implementation is a Singleton,
     * so you should not call the constructor
     * directly, but instead call the static Singleton
     * Factory method <code>Facade.getInstance()</code>
     *
     * @throws Error Error if Singleton instance has already been constructed
     *
     */
    public Facade() {
        if(instance != null) throw new Error(SINGLETON_MSG);
        instance = this;
        initializeFacade();
    }

    /**
     * Initialize the Singleton <code>Facade</code> instance.
     *
     * <P>
     * Called automatically by the constructor. Override in your
     * subclass to do any subclass specific initializations. Be
     * sure to call <code>super.initializeFacade()</code>, though.</P>
     */
    protected void initializeFacade() {
        initializeModel();
        initializeController();
        initializeView();
    }

    /**
     * Facade Singleton Factory method
     *
     * @param facadeSupplier facade Supplier Function
     * @return the Singleton instance of the Facade
     */
    public synchronized static IFacade getInstance(Supplier<IFacade> facadeSupplier) {
        if(instance == null) instance = facadeSupplier.get();
        return instance;
    }

    /**
     * Initialize the <code>Controller</code>.
     *
     * <P>
     * Called by the <code>initializeFacade</code> method.
     * Override this method in your subclass of <code>Facade</code>
     * if one or both of the following are true:</P>
     * <UL>
     * <LI> You wish to initialize a different <code>IController</code>.</LI>
     * <LI> You have <code>Commands</code> to register with the <code>Controller</code> at startup.</LI>
     * </UL>
     * <P>If you don't want to initialize a different <code>IController</code>,
     * call <code>super.initializeController()</code> at the beginning of your
     * method, then register <code>Command</code>s.
     * </P>
     */
    protected void initializeController() {
        if(controller != null) return;
        controller = Controller.getInstance(() -> new Controller());
    }

    /**
     * Initialize the <code>Model</code>.
     *
     * <P>
     * Called by the <code>initializeFacade</code> method.
     * Override this method in your subclass of <code>Facade</code>
     * if one or both of the following are true:
     * <UL>
     * <LI> You wish to initialize a different <code>IModel</code>.</LI>
     * <LI> You have <code>Proxy</code>s to register with the Model that do not
     * retrieve a reference to the Facade at construction time.</LI>
     * </UL>
     * If you don't want to initialize a different <code>IModel</code>,
     * call <code>super.initializeModel()</code> at the beginning of your
     * method, then register <code>Proxy</code>s.
     * <P>
     * Note: This method is <i>rarely</i> overridden; in practice you are more
     * likely to use a <code>Command</code> to create and register <code>Proxy</code>s
     * with the <code>Model</code>, since <code>Proxy</code>s with mutable data will likely
     * need to send <code>INotification</code>s and thus will likely want to fetch a reference to
     * the <code>Facade</code> during their construction.
     * </P>
     */
    protected void initializeModel() {
        if(model != null) return;
        model = Model.getInstance(() -> new Model());
    }

    /**
     * Initialize the <code>View</code>.
     *
     * <P>
     * Called by the <code>initializeFacade</code> method.
     * Override this method in your subclass of <code>Facade</code>
     * if one or both of the following are true:
     * <UL>
     * <LI> You wish to initialize a different <code>IView</code>.</LI>
     * <LI> You have <code>Observers</code> to register with the <code>View</code></LI>
     * </UL>
     * <p>If you don't want to initialize a different <code>IView</code>,
     * call <code>super.initializeView()</code> at the beginning of your
     * method, then register <code>IMediator</code> instances.</p>
     * <P>
     * Note: This method is <i>rarely</i> overridden; in practice you are more
     * likely to use a <code>Command</code> to create and register <code>Mediator</code>s
     * with the <code>View</code>, since <code>IMediator</code> instances will need to send
     * <code>INotification</code>s and thus will likely want to fetch a reference
     * to the <code>Facade</code> during their construction.
     * </P>
     */
    protected void initializeView() {
        if(view != null) return;
        view = View.getInstance(() -> new View());
    }

    /**
     * Register an <code>ICommand</code> with the <code>Controller</code> by Notification name.
     *
     * @param notificationName the name of the <code>INotification</code> to associate the <code>ICommand</code> with
     * @param commandSupplier a reference to the Supplier Function of the <code>ICommand</code>
     */
    public void registerCommand(String notificationName, Supplier<ICommand> commandSupplier) {
        controller.registerCommand(notificationName, commandSupplier);
    }

    /**
     * Remove a previously registered <code>ICommand</code> to <code>INotification</code> mapping from the Controller.
     *
     * @param notificationName the name of the <code>INotification</code> to remove the <code>ICommand</code> mapping for
     */
    public void removeCommand(String notificationName) {
        controller.removeCommand(notificationName);
    }

    /**
     * Check if a Command is registered for a given Notification
     *
     * @param notificationName notification name
     * @return whether a Command is currently registered for the given <code>notificationName</code>.
     */
    public boolean hasCommand(String notificationName) {
        return controller.hasCommand(notificationName);
    }

    /**
     * Register an <code>IProxy</code> with the <code>Model</code> by name.
     *
     * @param proxy the <code>IProxy</code> instance to be registered with the <code>Model</code>.
     */
    public void registerProxy(IProxy proxy) {
        model.registerProxy(proxy);
    }

    /**
     * Retrieve an <code>IProxy</code> from the <code>Model</code> by name.
     *
     * @param proxyName the name of the proxy to be retrieved.
     * @return the <code>IProxy</code> instance previously registered with the given <code>proxyName</code>.
     */
    public IProxy retrieveProxy(String proxyName) {
        return model.retrieveProxy(proxyName);
    }

    /**
     * Remove an <code>IProxy</code> from the <code>Model</code> by name.
     *
     * @param proxyName the <code>IProxy</code> to remove from the <code>Model</code>.
     * @return the <code>IProxy</code> that was removed from the <code>Model</code>
     */
    public IProxy removeProxy(String proxyName) {
        return model.removeProxy(proxyName);
    }

    /**
     * Check if a Proxy is registered
     *
     * @param proxyName proxy name
     * @return whether a Proxy is currently registered with the given <code>proxyName</code>.
     */
    public boolean hasProxy(String proxyName) {
        return model.hasProxy(proxyName);
    }

    /**
     * Register a <code>IMediator</code> with the <code>View</code>.
     *
     * @param mediator a reference to the <code>IMediator</code>
     */
    public void registerMediator(IMediator mediator) {
        view.registerMediator(mediator);
    }

    /**
     * Retrieve an <code>IMediator</code> from the <code>View</code>.
     *
     * @param mediatorName mediator name
     * @return the <code>IMediator</code> previously registered with the given <code>mediatorName</code>.
     */
    public IMediator retrieveMediator(String mediatorName) {
        return view.retrieveMediator(mediatorName);
    }

    /**
     * Remove an <code>IMediator</code> from the <code>View</code>.
     *
     * @param mediatorName name of the <code>IMediator</code> to be removed.
     * @return the <code>IMediator</code> that was removed from the <code>View</code>
     */
    public IMediator removeMediator(String mediatorName) {
        return view.removeMediator(mediatorName);
    }

    /**
     * Check if a Mediator is registered or not
     *
     * @param mediatorName mediator name
     * @return whether a Mediator is registered with the given <code>mediatorName</code>.
     */
    public boolean hasMediator(String mediatorName) {
        return view.hasMediator(mediatorName);
    }

    /**
     * Create and send an <code>INotification</code>.
     *
     * <P>
     * Keeps us from having to construct new notification
     * instances in our implementation code.
     * @param notificationName the name of the notiification to send
     * @param body the body of the notification
     * @param type the type of the notification
     */
    public void sendNotification(String notificationName, Object body, String type) {
        notifyObservers(new Notification(notificationName, body, type));
    }

    /**
     * Create and send an <code>INotification</code>.
     *
     * <P>
     * Keeps us from having to construct new notification
     * instances in our implementation code.
     * @param notificationName the name of the notiification to send
     * @param body the body of the notification
     */
    public void sendNotification(String notificationName, Object body) {
        sendNotification(notificationName, body, null);
    }

    /**
     * Create and send an <code>INotification</code>.
     *
     * <P>
     * Keeps us from having to construct new notification
     * instances in our implementation code.
     * @param notificationName the name of the notiification to send
     */
    public void sendNotification(String notificationName) {
        sendNotification(notificationName, null, null);
    }

    /**
     * Notify <code>Observer</code>s.
     * <P>
     * This method is left public mostly for backward
     * compatibility, and to allow you to send custom
     * notification classes using the facade.</P>
     *<P>
     * Usually you should just call sendNotification
     * and pass the parameters, never having to
     * construct the notification yourself.</P>
     *
     * @param notification the <code>INotification</code> to have the <code>View</code> notify <code>Observers</code> of.
     */
    public void notifyObservers(INotification notification) {
        view.notifyObservers(notification);
    }

}
