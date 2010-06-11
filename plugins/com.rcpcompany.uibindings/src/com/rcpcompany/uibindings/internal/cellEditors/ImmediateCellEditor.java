package com.rcpcompany.uibindings.internal.cellEditors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A cell editor that will not show anything, but immediately return.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class ImmediateCellEditor extends CellEditor {

	private final Runnable myRunnable;

	public ImmediateCellEditor() {
		this(null);
	}

	public ImmediateCellEditor(Runnable runnable) {
		myRunnable = runnable;
	}

	@Override
	protected Control createControl(Composite parent) {
		return null;
	}

	@Override
	public void activate() {
		super.activate();
		if (myRunnable != null) {
			myRunnable.run();
		}
		fireApplyEditorValue();
	}

	@Override
	public void activate(ColumnViewerEditorActivationEvent activationEvent) {
		if (activationEvent.eventType != ColumnViewerEditorActivationEvent.TRAVERSAL) {
			super.activate(activationEvent);
		}
	}

	@Override
	protected Object doGetValue() {
		return null;
	}

	@Override
	protected void doSetFocus() {

	}

	@Override
	protected void doSetValue(Object value) {
	}
}