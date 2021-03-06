/*******************************************************************************
 * Copyright (c) 2006-2013 The RCP Company and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The RCP Company - initial API and implementation
 *******************************************************************************/
package com.rcpcompany.uibindings.utils;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;

import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.IDisposable;
import com.rcpcompany.uibindings.internal.utils.BindingContextSelectionProvider;

/**
 * Selection provider for a binding context.
 * <p>
 * Added to the context with {@link Factory#adapt(IBindingContext, IWorkbenchPartSite, boolean)
 * IBindingContextSelectionProvider.Factory.adapt(IBindingContext, IWorkbenchPartSite)}. Additional
 * controls and viewers can be added with the different <code>add...(...)</code> methods in this
 * interface.
 * 
 * @author Tonny Madsen, The RCP Company
 * 
 *         TODO TEST
 */
public interface IBindingContextSelectionProvider extends IDisposable {
	/**
	 * Factory...
	 */
	final class Factory {
		private Factory() {
		}

		/**
		 * Adds menu along with the corresponding selection provider for the specified context.
		 * <p>
		 * The selection provider is set on the site of the context.
		 * 
		 * @param context the context
		 * @param site the site
		 * @return the new selection provider
		 */
		public static IBindingContextSelectionProvider adapt(IBindingContext context, IWorkbenchPartSite site) {
			return adapt(context, site, true);
		}

		/**
		 * Adds menu along with the corresponding selection provider for the specified context.
		 * <p>
		 * The selection provider is set on the site is wanted.
		 * 
		 * @param context the context
		 * @param site the site
		 * @param setupSelectionProvider whether to set the selection provider on the site
		 * @return the new selection provider
		 */
		public static IBindingContextSelectionProvider adapt(IBindingContext context, IWorkbenchPartSite site,
				boolean setupSelectionProvider) {
			return BindingContextSelectionProvider.adapt(context, site, setupSelectionProvider);
		}

		/**
		 * Adds menu along with the corresponding selection provider for the specified context.
		 * <p>
		 * The selection provider is set on the site of the context.
		 * 
		 * @param context the context
		 * @param site the site
		 * @return the new selection provider
		 */
		public static IBindingContextSelectionProvider adapt(IBindingContext context) {
			return adapt(context, true);
		}

		/**
		 * Adds menu along with the corresponding selection provider for the specified context.
		 * <p>
		 * The selection provider is set on the site is wanted.
		 * 
		 * @param context the context
		 * @param setupSelectionProvider whether to set the selection provider on the site
		 * @param site the site
		 * @return the new selection provider
		 */
		public static IBindingContextSelectionProvider adapt(IBindingContext context, boolean setupSelectionProvider) {
			@SuppressWarnings("restriction")
			final IWorkbenchLocationService ls = (IWorkbenchLocationService) context.getServiceLocator().getService(
					IWorkbenchLocationService.class);
			if (ls == null) return null;

			@SuppressWarnings("restriction")
			final IWorkbenchPartSite site = ls.getPartSite();

			return adapt(context, site, setupSelectionProvider);
		}
	}

	/**
	 * Adds the specified control with the specified selection provider.
	 * 
	 * @param control the control to add
	 * @param provider the selection provider used when the control has focus
	 */
	void addControl(Control control, ISelectionProvider provider);

	/**
	 * Adds the specified control with the specified "constant" selection value.
	 * <p>
	 * The selection does react to changes in the observable value,.
	 * 
	 * @param control the control to add
	 * @param selection the "constant" selection value to use
	 */
	void addControl(Control control, IObservableValue selection);

	/**
	 * Adds the specified control with the specified "constant" selection list.
	 * <p>
	 * The selection does react to changes in the observable list
	 * 
	 * @param control the control to add
	 * @param selection the "constant" selection list to use
	 */
	void addControl(Control control, IObservableList selection);

	/**
	 * Removes the control from the list of controls managed by this manager.
	 * 
	 * @param control the control to remove
	 */
	void removeControl(Control control);

	/**
	 * Adds the specified viewer with its "natural" selection provider.
	 * 
	 * @param viewer the viewer to add
	 */
	void addViewer(Viewer viewer);
}
