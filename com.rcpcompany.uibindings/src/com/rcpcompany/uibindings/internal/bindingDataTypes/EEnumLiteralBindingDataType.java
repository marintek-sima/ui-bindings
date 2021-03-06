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
package com.rcpcompany.uibindings.internal.bindingDataTypes;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;

import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.IArgumentProvider;
import com.rcpcompany.uibindings.IBindingDataType;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.internal.BindingDataTypeImpl;

/**
 * {@link IBindingDataType} for {@link EEnumLiteral}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class EEnumLiteralBindingDataType extends BindingDataTypeImpl {

	private final EEnumLiteral myLiteral;
	private IBindingDataType myParent = null;

	/**
	 * Constructs and returns a new data type for the specified enum literal.
	 * 
	 * @param literal the listeral
	 */
	public EEnumLiteralBindingDataType(EEnumLiteral literal) {
		myLiteral = literal;
	}

	@Override
	public Class<?> getDataType() {
		return getEType().getInstanceClass();
	}

	@Override
	public EAnnotation getEAnnotation() {
		return myLiteral.getEAnnotation(Constants.EMF_ANNOTATION_SOURCE);
	}

	@Override
	public IArgumentProvider getArgumentProvider(String type) {
		final IManager manager = IManager.Factory.getManager();
		manager.runModelArgumentMediators(myLiteral.getEEnum());
		return manager.getModelFeatureInfo(myLiteral.getEEnum().getInstanceClassName(), myLiteral.getLiteral(), type,
				false);
	}

	@Override
	public Object getValueType() {
		return myLiteral;
	}

	@Override
	public EClassifier getEType() {
		return myLiteral.getEEnum();
	}

	@Override
	public String getName() {
		return myLiteral.getLiteral();
	}

	@Override
	public IBindingDataType getParentDataType() {
		if (myParent == null) {
			myParent = IBindingDataType.Factory.create(null, getEType());
		}
		return myParent;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public boolean isChangeable() {
		return false;
	}

	@Override
	public String getBaseType() {
		return myLiteral.getEEnum().getName() + "." + myLiteral.getName();
	}
}
