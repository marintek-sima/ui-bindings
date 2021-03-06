package com.rcpcompany.uibindings.bindings.xtext;

import org.eclipse.core.databinding.observable.value.IObservableValue;

import com.google.inject.Module;
import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.bindings.xtext.internal.Activator;

public interface UIBXTextContants extends Constants {
	/**
	 * Prefix for all IDs.
	 */
	String PREFIX = Activator.ID + "."; //$NON-NLS-1$

	/**
	 * Argument name for UIBEmbeddedXTextEditor to use for an XText binding.
	 * <p>
	 * The argument value is {@link UIBEmbeddedXTextEditor}.
	 */
	String ARG_XTEXT_EDITOR = "xtextEditor"; //$NON-NLS-1$

	/**
	 * Argument name for Injector Module to use for an XText binding.
	 * <p>
	 * The argument value is {@link Module}.
	 */
	String ARG_XTEXT_INJECTOR_MODULE = "xtextInjectorModule"; //$NON-NLS-1$

	/**
	 * Argument name for an {@link IObservableValue} that will be updated with the current parsed AST from the editor
	 * parse result.
	 * <p>
	 * The argument value is {@link IObservableValue}.
	 */
	String ARG_XTEXT_AST_OV = "xtextASTOV"; //$NON-NLS-1$

	/**
	 * The context ID for the XText Editors.
	 */
	String XTEXT_EDITOR_CONTEXT_ID = PREFIX + "contexts.xtextEditorContext"; //$NON-NLS-1$
}
