package com.rcpcompany.uibindings.utils;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.IDisposable;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.internal.utils.FormCreator;

/**
 * This utility class is used to create complete forms based on the structure of data.
 * <p>
 * A new child {@link Composite} is created in the specified parent composite. The creation of the new child understands
 * the following layouts in the parent: {@link FillLayout}, {@link GridLayout} and {@link TableWrapLayout}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IFormCreator extends IDisposable {
	/**
	 * The factory methods for {@link IFormCreator}.
	 */
	public static final class Factory {
		/**
		 * Creates a new form with the specified parent.
		 * 
		 * @param context the binding context to use
		 * @param obj the current object
		 * @param toolkit the {@link FormToolkit} to use
		 * @param parent the parent composite of the new form
		 * @return the created form creator
		 */
		public static IFormCreator createForm(IBindingContext context, EObject obj, FormToolkit toolkit,
				Composite parent) {
			return new FormCreator(context, obj, toolkit, parent, null);
		}

		/**
		 * Creates a new form with the specified parent.
		 * 
		 * @param context the binding context to use
		 * @param value the observable value
		 * @param toolkit the {@link FormToolkit} to use
		 * @param parent the parent composite of the new form
		 * @return the created form creator
		 */
		public static IFormCreator createForm(IBindingContext context, IObservableValue value, FormToolkit toolkit,
				Composite parent) {
			return new FormCreator(context, value, toolkit, parent, null);
		}

		/**
		 * Creates a new form with the specified parent.
		 * 
		 * @param parent the parent composite of the new form
		 * @return the created form creator
		 */
		public static IFormCreator createForm(Composite parent) {
			return createForm(null, (EObject) null, IManager.Factory.getManager().getFormToolkit(), parent);
		}

		/**
		 * Creates a new form with the specified parent.
		 * 
		 * @param context the binding context to use
		 * @param value the observable value for the form
		 * @param toolkit the {@link FormToolkit} to use
		 * @param parent the parent composite of the new form
		 * @param formHeader the header text used for the form
		 * @return the created form creator
		 */
		public static IFormCreator createScrolledForm(IBindingContext context, IObservableValue value,
				FormToolkit toolkit, Composite parent, String formHeader) {
			return new FormCreator(context, value, toolkit, parent, formHeader);
		}

		/**
		 * Creates a new form with the specified parent.
		 * 
		 * @param context the binding context to use
		 * @param obj the current object
		 * @param toolkit the {@link FormToolkit} to use
		 * @param parent the parent composite of the new form
		 * @param formHeader the header text used for the form
		 * @return the created form creator
		 */
		public static IFormCreator createScrolledForm(IBindingContext context, EObject obj, FormToolkit toolkit,
				Composite parent, String formHeader) {
			return new FormCreator(context, obj, toolkit, parent, formHeader);
		}

		/**
		 * Creates a new form with the specified parent.
		 * 
		 * @param value the observable value for the form
		 * @param parent the parent composite of the new form
		 * @param formHeader the header text used for the form
		 * @return the created form creator
		 */
		public static IFormCreator createScrolledForm(IObservableValue value, Composite parent, String formHeader) {
			return createScrolledForm(null, value, IManager.Factory.getManager().getFormToolkit(), parent, formHeader);
		}

		/**
		 * Creates a new form with the specified parent.
		 * 
		 * @param obj the current object
		 * @param parent the parent composite of the new form
		 * @param formHeader the header text used for the form
		 * @return the created form creator
		 */
		public static IFormCreator createScrolledForm(EObject obj, Composite parent, String formHeader) {
			return createScrolledForm(null, obj, IManager.Factory.getManager().getFormToolkit(), parent, formHeader);
		}

		/**
		 * Creates a new form with the specified wizard page.
		 * 
		 * @param obj the data object of the page
		 * @param page the wizard page itself
		 * @param parent the parent composite
		 * @return the created form creator
		 */
		public static IFormCreator createWizardPage(EObject obj, WizardPage page, Composite parent) {
			return new FormCreator(obj, page, parent);
		}
	}

	/**
	 * Returns the used binding context.
	 * 
	 * @return the binding context
	 */
	public IBindingContext getContext();

	/**
	 * Returns the scrolled form the heads this form - if defined.
	 * 
	 * @return the scrolled form
	 */
	public ScrolledForm getScrolledForm();

	/**
	 * Returns the top {@link Composite} of this form creator.
	 * <p>
	 * The returned composite will have a {@link TableWrapLayout} with two columns.
	 * <p>
	 * Note that this composite need not be the same as the composite used in the factory.
	 * 
	 * @return the top composite
	 */
	public Composite getTop();

	/**
	 * Returns the form toolkit used by this creator
	 * 
	 * @return the toolkit
	 */
	public FormToolkit getToolkit();

	/**
	 * Returns the section of this form.
	 * <p>
	 * Will be <code>null</code> for "top level" section.
	 * 
	 * @return the section
	 */
	public Section getSection();

	/**
	 * Finishes the form and all created bindings.
	 * <p>
	 * Used to finish the context.
	 */
	public void finish();

	/**
	 * Sets the primary object of the creator for the next set of create methods.
	 * 
	 * @param object the new main object
	 * 
	 * @deprecated use {@link #createScrolledForm(EObject, Composite, String)} or
	 *             {@link #createScrolledForm(IObservableValue, Composite, String)}
	 */
	@Deprecated
	public void setObject(EObject object);

	/**
	 * Returns the primary object of the creator.
	 * 
	 * @return the primary object
	 * 
	 * @deprecated use {@link #getObservableValue()}
	 */
	@Deprecated
	public EObject getObject();

	/**
	 * Returns the observable value of the creator.
	 * 
	 * @return the observable value
	 */
	public IObservableValue getObservableValue();

	/**
	 * Sets whether all binding created by the form is read-only. The default is <code>false</code>.
	 * 
	 * @param readonly <code>true</code> if all bindings should be read-only
	 */
	public void setReadOnly(boolean readonly);

	/**
	 * Returns whether this form is read-only.
	 * 
	 * @return <code>true</code> if this form is read-only
	 */
	public boolean isReadOnly();

	/**
	 * Sets the heading of the scrolled form or section that forms the base of this form creator, if any.
	 * 
	 * @param heading the new heading
	 */
	public void setHeading(String heading);

	/**
	 * Creates and returns a new {@link Composite}.
	 * <p>
	 * Same as {@link #addComposite(boolean, boolean) <code>addComposite(true, false)</code>}.
	 * 
	 * @return the new composite
	 */
	public Composite addComposite();

	/**
	 * Creates and returns a new {@link Composite}.
	 * <p>
	 * The returned composite has a {@link FillLayout}, though this can be changed.
	 * 
	 * @param grabHorizontal whether to grab excess horizontal space in the form
	 * @param grabVertical whether to grab excess vertical space in the form
	 * 
	 * @return the new composite
	 */
	public Composite addComposite(boolean grabHorizontal, boolean grabVertical);

	/**
	 * Creates and returns a new table based on a table creator.
	 * 
	 * @param grabHorizontal TODO
	 * @param style styles passed to the table creator
	 * 
	 * @return the new table creator
	 * 
	 * @deprecated use {@link #addTableCreator(EReference, boolean, int)}
	 */
	@Deprecated
	public ITableCreator addTableCreator(boolean grabHorizontal, int style);

	/**
	 * Creates and returns a new table based on a table creator for the current object and the specified reference.
	 * 
	 * @param ref the reference for the table
	 * @param grabHorizontal TODO
	 * @param style styles passed to the table creator
	 * @return the new table creator
	 */
	public ITableCreator addTableCreator(EReference ref, boolean grabHorizontal, int style);

	/**
	 * Adds a new section to the form with the specified label.
	 * 
	 * @param label the label for the section
	 * @return a new independent {@link IFormCreator}
	 */
	public IFormCreator addSection(String label);

	/**
	 * Adds a new section to the form with the specified label.
	 * 
	 * @param label the label for the section
	 * @param grabVertical <code>true</code> if the new section should grab as space as possible vertically
	 * @return a new independent {@link IFormCreator}
	 */
	public IFormCreator addSection(String label, boolean grabVertical);

	/**
	 * Adds a new section to the form with the specified label.
	 * 
	 * @param label the label for the section
	 * @param obj the new observable
	 * @return a new independent {@link IFormCreator}
	 */
	public IFormCreator addSection(String label, IObservableValue obj, boolean grabVertical);

	/**
	 * Adds a new section to the form with the specified label for the specified base object.
	 * 
	 * @param label the label for the section
	 * @param obj the new base object
	 * @return a new independent {@link IFormCreator}
	 */
	public IFormCreator addSection(String label, EObject obj);

	/**
	 * Adds a new section to the form with the specified label for the specified base observable.
	 * 
	 * @param label the label for the section
	 * @param obj the new observable
	 * @return a new independent {@link IFormCreator}
	 * 
	 *         TODO test
	 */
	public IFormCreator addSection(String label, IObservableValue obj);

	/**
	 * Adds a simple label.
	 * <p>
	 * The label is set span the entire width of the form.
	 * 
	 * @param label the label
	 */
	public void addLabel(String label);

	/**
	 * Adds a label and a constant value.
	 * 
	 * @param label the label for the constant field
	 * @param value the value
	 * @param style additional styles to use for the value Control
	 */
	public void addConstantField(String label, Object value, int style);

	/**
	 * Adds a new label and field for the specified feature.
	 * <p>
	 * The text defaults to the default label of the feature
	 * 
	 * @param feature the feature - of {@link #setObject(EObject) the current main object}
	 * @return the value binding
	 */
	public IValueBinding addField(EStructuralFeature feature);

	/**
	 * Adds a new observable value for the specified specification.
	 * <p>
	 * See {@link IBindingSpec} for a description of the format and use of the specification.
	 * 
	 * @param spec the specification of the feature
	 * @return the observable value
	 */
	public IObservableValue getObservableValue(String spec);

	/**
	 * Adds a new observable value for the specified specification based on the specified base value.
	 * <p>
	 * See {@link IBindingSpec} for a description of the format and use of the specification.
	 * 
	 * @param base the base value
	 * @param spec the specification of the feature
	 * @return the observable value
	 */
	public IObservableValue getObservableValue(IObservableValue base, String spec);

	/**
	 * Adds a new label and field for the specified specification.
	 * <p>
	 * See {@link IBindingSpec} for a description of the format and use of the specification.
	 * <p>
	 * The text defaults to the default label of the feature
	 * 
	 * @param spec the specification of the feature to show
	 * @return the value binding
	 */
	public IValueBinding addField(String spec);

	/**
	 * Adds a new label and field for the specified specification.
	 * <p>
	 * See {@link IBindingSpec} for a description of the format and use of the specification.
	 * <p>
	 * The text defaults to the default label of the feature
	 * 
	 * @param value the value that forms the base of the specification
	 * @param spec the specification of the feature to show
	 * @return the value binding
	 */
	public IValueBinding addField(IObservableValue value, String spec);

	/**
	 * Adds a new label and field for the specified feature.
	 * <p>
	 * The text defaults to the default label of the feature
	 * 
	 * @param feature the feature - of {@link #setObject(EObject) the current main object}
	 * @param style additional styles to use for the value Control
	 * @return the value binding
	 */
	public IValueBinding addField(EStructuralFeature feature, int style);

	/**
	 * Adds a new label and field for the specified feature.
	 * <p>
	 * The text defaults to the default label of the feature
	 * 
	 * @param object the object
	 * @param feature the feature - of object
	 * @param style additional styles to use for the value Control
	 * @return the value binding
	 */
	public IValueBinding addField(EObject object, EStructuralFeature feature, int style);

	/**
	 * Adds a new label and field for the specified observable value.
	 * 
	 * @param value the observable value to show
	 * @param style TODO
	 * @return the value binding
	 */
	public IValueBinding addField(IObservableValue value, int style);

	/**
	 * Adds a new label and field for the specified feature of the observable value.
	 * 
	 * @param value the observable value to show
	 * @param feature the feature
	 * @param style TODO
	 * @return the value binding
	 * 
	 *         TODO: test
	 */
	public IValueBinding addField(IObservableValue value, EStructuralFeature feature, int style);

	/**
	 * Creates and returns a new sub-form of this form creator for the specified parent.
	 * 
	 * @param parent the parent composite of the sub-form
	 * @return the sub form
	 */
	public IFormCreator subForm(Composite parent);

	/**
	 * Creates and returns a new sub-form of this form creator for the specified parent.
	 * 
	 * @param parent the parent composite of the sub-form
	 * @param value the observable value of the sub-form
	 * @return the sub form
	 */
	public IFormCreator subForm(Composite parent, IObservableValue value);

	/**
	 * Sets the focus of the first field of the form that can accept focus.
	 */
	public void setFocus();

	/**
	 * Adds a separator between sections in this form.
	 * <p>
	 * Same as <code>addSeparator(Separator.LINE)</code>.
	 */
	public void addSeparator();

	/**
	 * The possible separator types.
	 */
	enum Separator {
		LINE, MICRO, TINY, SMALL, BIG
	}

	/**
	 * Adds a separator between parts this form.
	 * 
	 * @param type the wanted type of separator
	 */
	public void addSeparator(Separator type);

	/**
	 * Adds all objects messages for the current {@link #getObservableValue()}.
	 */
	public void addObjectMessages();

	/**
	 * Adds all objects messages for the specified {@link #getObservableValue(String) getObservableValue(spec)}.
	 * 
	 * @param spec the specification for the object
	 */
	public void addObjectMessages(String spec);

	/**
	 * Adds all objects messages for the specified value.
	 * 
	 * @param value the observable value
	 */
	public void addObjectMessages(IObservableValue value);

	/**
	 * Sets the layout data of the specified control to cover a part of this form.
	 * 
	 * @param ctl the control
	 * @param grabHorizontal <code>true</code> if horizontal space should be grabed
	 * @param grabVertical <code>true</code> if vertical space should be grabed
	 */
	public void setLayoutData(Control ctl, boolean grabHorizontal, boolean grabVertical);

	/**
	 * Splits the current form into a number of parallel forms.
	 * <p>
	 * A boolean argument is specified for each column to be added. The boolean specifies whether the column should grab
	 * space or not. Thus to add three columns, where only the middle column should grab space use the call
	 * 
	 * <pre>
	 *  IFormCreator[] columns = ...addColumns(false, true, false);
	 * </pre>
	 * 
	 * @param grab whether the column should grab space or not
	 * @return the constructed forms
	 */
	public IFormCreator[] addColumns(boolean... grab);

	/**
	 * Splits the current form into a number of parallel forms each formatted according to the {@link GridData} object.
	 * <p>
	 * A {@link GridData} object is specified for each column to be added.
	 * 
	 * @param layoutData layout data for the specific column
	 * @return the constructed forms
	 */
	public IFormCreator[] addColumns(GridData... layoutData);

	/**
	 * Adds a new form chooser based on discriminant in another field
	 * 
	 * @param discriminant the discriminator field
	 * @return the created chooser
	 */
	public IFormChooser addFormChooser(IValueBinding discriminant);

	/**
	 * Adds a new form chooser based on discriminant.
	 * 
	 * @param discriminant the discriminator
	 * @return the created chooser
	 */
	public IFormChooser addFormChooser(IObservableValue discriminant);

	/**
	 * Returns whether the created fields in this form creator and all child form are aligned on the left edge.
	 * 
	 * @return <code>true</code> if they are aligned
	 */
	public boolean areFieldsAligned();

	/**
	 * Set whether the created fields in this form creator and all child form are aligned on the left edge.
	 * 
	 * @param align <code>true</code> if they are aligned
	 */
	public void setFieldsAligned(boolean align);

	/**
	 * Returns whether this form is a top-level form or not
	 * 
	 * @return true if it is a top-level form
	 */
	public boolean isTopForm();
}