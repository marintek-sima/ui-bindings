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
package com.rcpcompany.uibindings.internal.uiAttributeFactories.contentAdapters;

import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.IControlContentAdapter2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/**
 * An {@link IControlContentAdapter} for SWT Combo controls. This is a convenience class for easily
 * creating a {@link ContentProposalAdapter} for combo fields.
 */
public class CComboContentAdapter implements IControlContentAdapter, IControlContentAdapter2 {

	/*
	 * Set to <code>true</code> if we should compute the text vertical bounds rather than just use
	 * the field size. Workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=164748 The
	 * corresponding SWT bug is https://bugs.eclipse.org/bugs/show_bug.cgi?id=44072
	 */
	private static final boolean COMPUTE_TEXT_USING_CLIENTAREA = !"carbon".equals(SWT.getPlatform()); //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.taskassistance.IControlContentAdapter#getControlContents(org.eclipse
	 * .swt.widgets.Control )
	 */
	@Override
	public String getControlContents(Control control) {
		return ((CCombo) control).getText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.fieldassist.IControlContentAdapter#setControlContents(org.eclipse.swt.widgets
	 * .Control, java.lang.String, int)
	 */
	@Override
	public void setControlContents(Control control, String text, int cursorPosition) {
		((CCombo) control).setText(text);
		((CCombo) control).setSelection(new Point(cursorPosition, cursorPosition));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.fieldassist.IControlContentAdapter#insertControlContents(org.eclipse.swt
	 * .widgets.Control, java.lang.String, int)
	 */
	@Override
	public void insertControlContents(Control control, String text, int cursorPosition) {
		final CCombo combo = (CCombo) control;
		final String contents = combo.getText();
		final Point selection = combo.getSelection();
		final StringBuffer sb = new StringBuffer();
		sb.append(contents.substring(0, selection.x));
		sb.append(text);
		if (selection.y < contents.length()) {
			sb.append(contents.substring(selection.y, contents.length()));
		}
		combo.setText(sb.toString());
		selection.x += cursorPosition;
		selection.y = selection.x;
		combo.setSelection(selection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.fieldassist.IControlContentAdapter#getCursorPosition(org.eclipse.swt.widgets
	 * .Control)
	 */
	@Override
	public int getCursorPosition(Control control) {
		return ((CCombo) control).getSelection().x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.fieldassist.IControlContentAdapter#getInsertionBounds(org.eclipse.swt.widgets
	 * .Control)
	 */
	@Override
	public Rectangle getInsertionBounds(Control control) {
		// This doesn't take horizontal scrolling into affect.
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=204599
		final CCombo combo = (CCombo) control;
		final int position = combo.getSelection().y;
		final String contents = combo.getText();
		final GC gc = new GC(combo);
		gc.setFont(combo.getFont());
		final Point extent = gc.textExtent(contents.substring(0, Math.min(position, contents.length())));
		gc.dispose();
		if (COMPUTE_TEXT_USING_CLIENTAREA)
			return new Rectangle(combo.getClientArea().x + extent.x, combo.getClientArea().y, 1,
					combo.getClientArea().height);
		return new Rectangle(extent.x, 0, 1, combo.getSize().y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.fieldassist.IControlContentAdapter#setCursorPosition(org.eclipse.swt.widgets
	 * .Control, int)
	 */
	@Override
	public void setCursorPosition(Control control, int index) {
		((CCombo) control).setSelection(new Point(index, index));
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IControlContentAdapter2#getSelection(org.eclipse.swt.widgets.Control)
	 */
	@Override
	public Point getSelection(Control control) {
		return ((CCombo) control).getSelection();
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IControlContentAdapter2#setSelection(org.eclipse.swt.widgets.Control,
	 *      org.eclipse.swt.graphics.Point)
	 */
	@Override
	public void setSelection(Control control, Point range) {
		((CCombo) control).setSelection(range);
	}

}
