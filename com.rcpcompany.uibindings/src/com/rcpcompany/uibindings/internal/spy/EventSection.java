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
package com.rcpcompany.uibindings.internal.spy;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.swt.SWT;
import org.eclipse.ui.handlers.HandlerUtil;

import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.IBindingSpySection;
import com.rcpcompany.uibindings.internal.sourceProviders.BindingSourceProvider;
import com.rcpcompany.uibindings.utils.IFormCreator;

/**
 * Section for the {@link ExecutionEvent}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class EventSection implements IBindingSpySection {
	@Override
	public void build(IFormCreator creator, ExecutionEvent event) {
		if (event == null) return;

		final IFormCreator subform = creator.addSection("Event");

		final String[] sourceNames = BindingSourceProvider.PROVIDED_SOURCE_NAMES;
		for (final String n : sourceNames) {
			String label = n;
			if (label.startsWith(Constants.SOURCES)) {
				label = label.substring(Constants.SOURCES.length());
			}
			final Object v = HandlerUtil.getVariable(event, n);

			String s;
			if (v == null) {
				s = "<null>";
			} else if (v == IEvaluationContext.UNDEFINED_VARIABLE) {
				s = "<undef>";
			} else {
				s = v.toString();
			}
			if (s.length() > 30) {
				s = s.substring(0, 28) + "...";
			}

			subform.addConstantField(label, s, SWT.NULL);
		}
	}
}
