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
package com.rcpcompany.uibindings.internal.decorators;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.rcpcompany.uibindings.IUIBindingDecorator;
import com.rcpcompany.uibindings.decorators.SimpleUIBindingDecorator;

/**
 * {@link IUIBindingDecorator} implementation for {@link EStructuralFeature}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class EStructuralFeatureDecorator extends SimpleUIBindingDecorator implements IUIBindingDecorator {

	@Override
	protected Object convertModelToUI(Object fromObject) {
		final EStructuralFeature sf = (EStructuralFeature) fromObject;
		if (sf == null) return "<null>";
		String s = sf.getEContainingClass().getName() + "." + sf.getName() + ": " + sf.getEType().getName();

		switch (sf.getUpperBound()) {
		case 1:
			break;
		case -1:
			s += "[*]";
			break;
		default:
			s += "[" + sf.getUpperBound() + "]";
			break;
		}

		return s;
	}

	@Override
	protected Object convertUIToModel(Object fromObject) {
		return null;
	}

	@Override
	public boolean isChangeable() {
		return false;
	}
}
