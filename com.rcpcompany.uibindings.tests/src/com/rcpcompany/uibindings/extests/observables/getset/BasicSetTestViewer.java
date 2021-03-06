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
package com.rcpcompany.uibindings.extests.observables.getset;

import static com.rcpcompany.test.utils.ui.UITestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.IBinding;
import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.IViewerBinding;
import com.rcpcompany.uibindings.TextCommitStrategy;
import com.rcpcompany.uibindings.tests.shop.Shop;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopItem;
import com.rcpcompany.uibindings.tests.shop.ShopPackage;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;
import com.rcpcompany.uibindings.tests.utils.views.UIBTestView;

/**
 * Test of get and set value in a viewer. E.g. when the value in the model or a cell is changed, the
 * other part is changed too.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class BasicSetTestViewer {
	private UIBTestView myView;
	private Shop myShop;
	private Composite myBody;
	private TableViewer myTableViewer;
	private TableViewerColumn myPriceColumn;
	private IBindingContext myContext;
	private IViewerBinding myViewerBinding;
	private ShopItem myShopItem;
	private Table myTable;

	@Before
	public void before() {
		BaseUIBTestUtils.resetAll();
		IManager.Factory.getManager().setTextCommitStrategy(TextCommitStrategy.ON_MODIFY);
		IManager.Factory.getManager().setEditCellSingleClick(false);

		createShop();
		createView();
		bindUI();

		myView.getSite().getPage().activate(myView);
		myBody.layout();
	}

	/**
	 * Creates the shop itself
	 */
	public void createShop() {
		myShop = ShopFactory.eINSTANCE.createShop();

		myShopItem = ShopFactory.eINSTANCE.createShopItem();
		myShopItem.setName("item");
		myShopItem.setPrice(10f);

		myShop.getShopItems().add(myShopItem);
	}

	/**
	 * Creates the view
	 */
	public void createView() {
		myView = BaseUIBTestUtils.createUIBTestView(this);
		myBody = myView.getBody();

		myTableViewer = new TableViewer(myBody, SWT.FULL_SELECTION);
		myTable = myTableViewer.getTable();
		myTable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		myTable.setHeaderVisible(true);

		myPriceColumn = new TableViewerColumn(myTableViewer, SWT.NONE);
		myPriceColumn.getColumn().setWidth(100);
	}

	@After
	public void disposeView() {
		if (myView != null) {
			myView.getSite().getPage().hideView(myView);
		}
	}

	/**
	 * Binds the UI
	 */
	public void bindUI() {
		myContext = IBindingContext.Factory.createContext(myView.getScrolledForm());

		myViewerBinding = myContext.addViewer(myTableViewer, myShop, ShopPackage.Literals.SHOP__SHOP_ITEMS);
		myViewerBinding.addColumn(myPriceColumn, ShopPackage.Literals.SHOP_ITEM__PRICE).type("##.#");

		myContext.finish();
	}

	@Test
	public void testValues() {
		final ColumnViewerEditor columnViewerEditor = myTableViewer.getColumnViewerEditor();
		final EList<IBinding> bindings = myContext.getBindings();

		yield();
		// TODO: does not work any more!
		assertEquals(String.format("%,.2f", myShopItem.getPrice()),
				myTable.getItem(0).getText(0 + myViewerBinding.getFirstTableColumnOffset()));

		/*
		 * Change the price and check that the cell is also changed
		 */
		myShopItem.setPrice(20f);
		yield();
		// TODO: does not work any more!
		assertEquals(String.format("%,.2f", myShopItem.getPrice()),
				myTable.getItem(0).getText(0 + myViewerBinding.getFirstTableColumnOffset()));

		/*
		 * - focus on the table
		 */
		assertTrue(myTable.setFocus());
		postMouse(myTable, 0 + myViewerBinding.getFirstTableColumnOffset(), 0);
		yield();
		assertEquals(0, myTable.getSelectionIndex());
		assertNotNull(columnViewerEditor.getFocusCell());

		/*
		 * - press return to edit
		 */
		postKeyStroke(myTable, "ENTER");
		yield();
		assertNotNull(columnViewerEditor.getFocusCell());
		assertEquals(true, myTableViewer.isCellEditorActive());

		IValueBinding editBinding = (IValueBinding) bindings.get(bindings.size() - 1);

		Text t = (Text) editBinding.getControl();
		assertNotNull("edit controll null", t);

		assertEquals(String.format("%,.2f", myShopItem.getPrice()), t.getText());

		/*
		 * - Enter new number
		 */
		t.setText("30");
		yield();
		assertEquals(30f, myShopItem.getPrice(), 0.001);
		// TODO: does not work any more!
		assertEquals(String.format("%,.2f", myShopItem.getPrice()),
				myTable.getItem(0).getText(0 + myViewerBinding.getFirstTableColumnOffset()));

		/*
		 * - cancel edit
		 */
		postKeyStroke(myTable, "ESCAPE");
		yield();
		assertEquals(false, myTableViewer.isCellEditorActive());
		assertEquals(20f, myShopItem.getPrice(), 0.001);
		// TODO: does not work any more!
		assertEquals(String.format("%,.2f", myShopItem.getPrice()),
				myTable.getItem(0).getText(0 + myViewerBinding.getFirstTableColumnOffset()));

		/*
		 * - press return AGAIN to edit
		 */
		assertTrue(myTable.setFocus());
		assertEquals(0, myTable.getSelectionIndex());
		assertNotNull(columnViewerEditor.getFocusCell());
		postKeyStroke(myTable, "ENTER");
		yield();
		assertNotNull(columnViewerEditor.getFocusCell());
		assertEquals(true, myTableViewer.isCellEditorActive());

		editBinding = (IValueBinding) bindings.get(bindings.size() - 1);

		t = (Text) editBinding.getControl();
		assertNotNull("edit controll null", t);

		assertEquals(String.format("%,.2f", myShopItem.getPrice()), t.getText());// !!!!

		/*
		 * - Enter new number
		 */
		t.setText("40");
		yield();
		assertEquals(40f, myShopItem.getPrice(), 0.001);
		// TODO: does not work any more!
		assertEquals(String.format("%,.2f", myShopItem.getPrice()),
				myTable.getItem(0).getText(0 + myViewerBinding.getFirstTableColumnOffset()));

		/*
		 * - commit edit
		 */
		postKeyStroke(myTable, "ENTER");
		yield();
		assertEquals(false, myTableViewer.isCellEditorActive());
		assertEquals(40f, myShopItem.getPrice(), 0.001);
		// TODO: does not work any more!
		assertEquals(String.format("%,.2f", myShopItem.getPrice()),
				myTable.getItem(0).getText(0 + myViewerBinding.getFirstTableColumnOffset()));
	}
}
