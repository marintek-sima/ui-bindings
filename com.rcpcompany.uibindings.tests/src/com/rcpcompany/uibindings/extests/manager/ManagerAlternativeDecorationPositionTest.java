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
package com.rcpcompany.uibindings.extests.manager;

import org.eclipse.emf.ecore.EStructuralFeature;

import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.DecorationPosition;
import com.rcpcompany.uibindings.IUIBindingsPackage;

public class ManagerAlternativeDecorationPositionTest extends AbstractPreferenceStoreEnumTest<DecorationPosition> {

	@Override
	public DecorationPosition getDefault() {
		return DecorationPosition.TOP_LEFT;
	}

	@Override
	public DecorationPosition[] getValues() {
		return DecorationPosition.values();
	}

	@Override
	public EStructuralFeature getFeature() {
		return IUIBindingsPackage.Literals.MANAGER__ALTERNATIVE_DECORATION_POSITION;
	}

	@Override
	public String getPreferenceName() {
		return Constants.PREF_ALTERNATIVE_DECORATION_POSITION;
	}
}
