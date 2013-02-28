package com.rcpcompany.uibindings.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.rcpcompany.uibindings.IBindingDataType;
import com.rcpcompany.uibindings.IColumnBinding;
import com.rcpcompany.uibindings.IViewerBinding;

public class SuperPasteValidator {

	public static IStatus isValidPaste(IViewerBinding viewerBinding, String[][] table) {
		final int irows = table.length;
		if (irows == 0) return new Status(IStatus.ERROR, "com.rcpcompany.uibinding", "No data found");
		final int nColumns = table[0].length;
		if (nColumns == 0) return new Status(IStatus.ERROR, "com.rcpcompany.uibinding", "No data found");

		final List<EStructuralFeature> features = new ArrayList<EStructuralFeature>();

		final EList<IColumnBinding> columns = viewerBinding.getColumns();
		for (final IColumnBinding column : columns) {
			if (column.getColumnAdapter().getWidth() == 0) {
				continue;
			}
			// Is this the __ROW_NO__ Binding?
			if ("No".equals(column.getLabel())) {
				if (!column.getArgument(IColumnChooser.ARG_CHOOSABLE, Boolean.class, Boolean.TRUE)) {
					continue;
				}
			}
			final IBindingDataType type = column.getDataType();
			final Object vt = type.getValueType();
			if (vt instanceof EStructuralFeature) {
				features.add((EStructuralFeature) vt);
			} else
				// Not able to decide
				return new Status(IStatus.ERROR, "com.rcpcompany.uibinding", "Unexpected type in table encountered");
		}

		// First check number of columns
		if (nColumns > features.size())
			return new Status(IStatus.ERROR, "com.rcpcompany.uibinding", "Data contains too many columns: " + nColumns
					+ " > " + features.size());

		return Status.OK_STATUS;
	}
}
