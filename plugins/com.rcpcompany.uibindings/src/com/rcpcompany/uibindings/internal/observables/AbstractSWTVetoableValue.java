package com.rcpcompany.uibindings.internal.observables;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractVetoableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Widget;

/**
 * Copied strait out of {@link org.eclipse.jface.internal.databinding.provisional.swt.AbstractSWTVetoableValue}.
 */
public abstract class AbstractSWTVetoableValue extends AbstractVetoableValue implements ISWTObservableValue {

	private final Widget widget;

	/**
	 * Standard constructor for an SWT VetoableValue. Makes sure that the observable gets disposed when the SWT widget
	 * is disposed.
	 * 
	 * @param widget
	 */
	protected AbstractSWTVetoableValue(Widget widget) {
		this(SWTObservables.getRealm(widget.getDisplay()), widget);
	}

	/**
	 * Constructs a new instance for the provided <code>realm</code> and <code>widget</code>.
	 * 
	 * @param realm
	 * @param widget
	 */
	protected AbstractSWTVetoableValue(Realm realm, Widget widget) {
		super(realm);
		this.widget = widget;
		if (widget == null) {
			throw new IllegalArgumentException("The widget parameter is null."); //$NON-NLS-1$
		}
		widget.addDisposeListener(disposeListener);
	}

	private final DisposeListener disposeListener = new DisposeListener() {
		public void widgetDisposed(DisposeEvent e) {
			AbstractSWTVetoableValue.this.dispose();
		}
	};

	/**
	 * @return Returns the widget.
	 */
	public Widget getWidget() {
		return widget;
	}
}