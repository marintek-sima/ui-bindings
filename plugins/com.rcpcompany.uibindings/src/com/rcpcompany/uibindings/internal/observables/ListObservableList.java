package com.rcpcompany.uibindings.internal.observables;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

/**
 * 
 */
public class ListObservableList extends MySWTObservableList {

	private final List list;

	/**
	 * @param list
	 */
	public ListObservableList(List list) {
		super(SWTObservables.getRealm(list.getDisplay()));
		this.list = list;
	}

	@Override
	protected int getItemCount() {
		return list.getItemCount();
	}

	@Override
	protected void setItems(String[] newItems) {
		list.getDisplay().addFilter(SWT.Modify, myModifyFilter);
		final String[] text = list.getSelection();
		list.setItems(newItems);
		list.setSelection(text);
		list.getDisplay().removeFilter(SWT.Modify, myModifyFilter);
	}

	@Override
	protected String[] getItems() {
		return list.getItems();
	}

	@Override
	protected String getItem(int index) {
		return list.getItem(index);
	}

	@Override
	protected void setItem(int index, String string) {
		list.getDisplay().addFilter(SWT.Modify, myModifyFilter);
		final String[] text = list.getSelection();
		list.setItem(index, string);
		list.setSelection(text);
		list.getDisplay().removeFilter(SWT.Modify, myModifyFilter);
	}

	private final Listener myModifyFilter = new Listener() {
		@Override
		public void handleEvent(Event event) {
			if (event.type == SWT.Modify) {
				event.type = SWT.None;
			}
		}
	};
}