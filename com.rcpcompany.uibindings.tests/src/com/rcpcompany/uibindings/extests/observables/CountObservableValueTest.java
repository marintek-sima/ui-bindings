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
package com.rcpcompany.uibindings.extests.observables;

import static org.junit.Assert.*;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.UIBindingsEMFObservables;
import com.rcpcompany.uibindings.observables.CountObservableValue;
import com.rcpcompany.uibindings.tests.shop.Shop;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopPackage;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;

/**
 * Test of {@link CountObservableValue}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class CountObservableValueTest {
	private Shop myShop;

	@Before
	public void before() {
		BaseUIBTestUtils.resetAll();
		myShop = ShopFactory.eINSTANCE.createShop();
	}

	protected int changeCount = 0;

	@Test
	public void testValue() {
		final IObservableList list = UIBindingsEMFObservables.observeList(null, myShop,
				ShopPackage.Literals.SHOP__COUNTRIES);
		final CountObservableValue v = new CountObservableValue(list);

		assertEquals(EcorePackage.Literals.EINT, v.getValueType());
		assertEquals(0, v.getValue());
		final IValueChangeListener listener = new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				changeCount++;
			}
		};
		v.addValueChangeListener(listener);

		assertEquals(0, changeCount);
		myShop.getCountries().add(ShopFactory.eINSTANCE.createCountry());
		assertEquals(1, changeCount);
		assertEquals(1, v.getValue());

		myShop.getCountries().add(ShopFactory.eINSTANCE.createCountry());
		assertEquals(2, changeCount);
		assertEquals(2, v.getValue());

		myShop.getCountries().clear();
		assertEquals(3, changeCount);
		assertEquals(0, v.getValue());

		v.removeValueChangeListener(listener);
	}
}
