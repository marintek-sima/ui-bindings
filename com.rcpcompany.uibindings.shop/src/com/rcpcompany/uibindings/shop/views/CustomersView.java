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
package com.rcpcompany.uibindings.shop.views;

import org.eclipse.nebula.widgets.radiogroup.RadioGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.SpecialBinding;
import com.rcpcompany.uibindings.tests.shop.Shop;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopPackage;
import com.rcpcompany.uibindings.utils.EditingDomainUtils;
import com.rcpcompany.uibindings.utils.IBindingContextSelectionProvider;
import com.rcpcompany.uibindings.utils.IDnDSupport;
import com.rcpcompany.uibindings.utils.IFormCreator;
import com.rcpcompany.uibindings.utils.ITableCreator;

/**
 * The customer view of the shop application.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class CustomersView extends ViewPart {

	private IFormCreator myForm;

	@Override
	public void createPartControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		final Shop shop = ShopFactory.eINSTANCE.getShop(EditingDomainUtils.getEditingDomain());
		myForm = IFormCreator.Factory.createScrolledForm(shop, composite, "Customers");

		final ITableCreator table = myForm.addTableCreator(ShopPackage.Literals.SHOP__CUSTOMERS, true,
				ITableCreator.FILTER);
		table.addColumn(SpecialBinding.ROW_NO, 40);
		table.addColumn("contact(w=100)");
		table.addColumn("contact.country(w=100)");
		table.addColumn("contact.newsletter(w=50)");
		table.addColumn("loyalty(w=100)");
		table.addColumn("logoFileName(w=200)");

		final IFormCreator detailsSection = myForm.addSection("Details", table.getBinding().getSingleSelection());
		detailsSection.addObjectMessages();
		detailsSection.addField("contact.name(w=200)");
		detailsSection.addField("contact.address(w=200)");
		detailsSection.addField("contact.country(w=200)");
		detailsSection.addField("contact.newsletter(w=50)");
		detailsSection.addField("logoFileName(w=200)");
		detailsSection.addField("loyalty").arg(Constants.ARG_PREFERRED_CONTROL, RadioGroup.class.getName());

		final IFormCreator ordersSection = myForm.addSection("Orders", table.getBinding().getSingleSelection());
		final ITableCreator ordersTable = ordersSection.addTableCreator(ShopPackage.Literals.CUSTOMER__ORDERS, true,
				SWT.NONE);
		ordersSection.addObjectMessages();
		ordersTable.addColumn("no(w=40)");
		ordersTable.addColumn("price(w=100)");

		myForm.finish();
		IBindingContextSelectionProvider.Factory.adapt(myForm.getContext(), getSite());
		IDnDSupport.Factory.installOn(myForm.getContext());
		// form.getScrolledForm().reflow(true);

		// final Listener l = new Listener() {
		// @Override
		// public void handleEvent(Event event) {
		// LogUtils.debug(this, ToStringUtils.toString(event));
		// }
		// };
		// for (int i = SWT.None; i < SWT.ImeComposition; i++) {
		// Display.getCurrent().addFilter(i, l);
		// }
	}

	@Override
	public void setFocus() {
		myForm.setFocus();
	}
}
