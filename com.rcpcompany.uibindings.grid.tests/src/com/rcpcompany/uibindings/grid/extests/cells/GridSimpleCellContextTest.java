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
package com.rcpcompany.uibindings.grid.extests.cells;

import static com.rcpcompany.test.utils.ui.UITestUtils.*;
import static com.rcpcompany.uibindings.grid.extests.GridBaseTestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibinding.tests.model.TestGrid;
import com.rcpcompany.uibinding.tests.model.TestModelPackage;
import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.grid.IGridBinding;
import com.rcpcompany.uibindings.grid.extests.models.TestGridGridModel;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;
import com.rcpcompany.uibindings.tests.utils.views.UIBTestView;
import com.rcpcompany.uibindings.utils.EditingDomainUtils;

/**
 * Simple test to test that the content of cells is correct.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class GridSimpleCellContextTest {

	private UIBTestView myView;
	private Grid myGrid;
	private IGridBinding myGridBinding;

	private TestGrid myTestGrid;
	private TestGridGridModel myModel;

	@Before
	public void setup() {
		BaseUIBTestUtils.resetAll();

		createModel();
		createView();
	}

	private void createModel() {
		myTestGrid = createTestGrid();
		myModel = new TestGridGridModel(EditingDomainUtils.getEditingDomain(), myTestGrid,
				TestModelPackage.Literals.TEST_GRID_CELL__DETAILS, null);
	}

	private void createView() {
		myView = BaseUIBTestUtils.createUIBTestView(this);

		myGrid = new Grid(myView.getBody(), SWT.NONE);
		final IBindingContext context = IBindingContext.Factory.createContext(myView.getBody());
		myGridBinding = IGridBinding.Factory.createGrid(context, myGrid, myModel);
		context.finish();
		yield();

		myView.getBody().layout();

		yield();
	}

	@After
	public void disposeView() {
		if (myView != null) {
			myView.getSite().getPage().hideView(myView);
		}
	}

	@Test
	public void testCellContent() {
		final GridItem item = myGrid.getItem(0);
		assertEquals("T1", item.getText(2));
	}
}
