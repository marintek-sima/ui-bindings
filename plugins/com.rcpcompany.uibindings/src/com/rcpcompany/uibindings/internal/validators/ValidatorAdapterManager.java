package com.rcpcompany.uibindings.internal.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.widgets.Display;

import com.rcpcompany.uibindings.BindingMessageSeverity;
import com.rcpcompany.uibindings.IBindingMessage;
import com.rcpcompany.uibindings.IBindingMessageTarget;
import com.rcpcompany.uibindings.IDisposable;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IUIBindingsPackage;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.IBindingMessage.FeatureMatchingAlgorithm;
import com.rcpcompany.uibindings.bindingMessages.AbstractBindingMessage;
import com.rcpcompany.uibindings.internal.Activator;
import com.rcpcompany.uibindings.validators.IValidationAdapterManagerChangeEvent;
import com.rcpcompany.uibindings.validators.IValidationAdapterManagerChangeListener;
import com.rcpcompany.uibindings.validators.IValidatorAdapter;
import com.rcpcompany.uibindings.validators.IValidatorAdapterManager;
import com.rcpcompany.uibindings.validators.IValidatorAdapterMessageDecorator;
import com.rcpcompany.utils.logging.LogUtils;

/**
 * Implementation of {@link IValidatorAdapterManager}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class ValidatorAdapterManager extends EventManager implements IValidatorAdapterManager, IDisposable {

	/**
	 * The base manager...
	 */
	protected final static IManager theManager = IManager.Factory.getManager();

	/**
	 * Returns the validation adapter manager
	 * 
	 * @return the manager
	 */
	public static IValidatorAdapterManager getManager() {
		IValidatorAdapterManager mng = theManager.getService(IValidatorAdapterManager.class);
		if (mng == null) {
			mng = new ValidatorAdapterManager();
		}
		return mng;
	}

	/**
	 * Contructs and returns a new manager.
	 */
	protected ValidatorAdapterManager() {
		theManager.registerService(this);
	}

	@Override
	public void dispose() {
		theManager.deregisterService(this);

		for (final ValidationRoot root : myValidationRoots.toArray(new ValidationRoot[0])) {
			removeRoot(root.myRoot, root.myValidationAdapter);
		}

		for (final IValidatorAdapterMessageDecorator d : myDecorators.toArray(new IValidatorAdapterMessageDecorator[0])) {
			removeDecorator(d);
		}

		myUnboundMessagesOLUnmodifiable.dispose();
		myUnboundMessagesOL.dispose();
	}

	@Override
	public void addValidationAdapterManagerChangeListener(IValidationAdapterManagerChangeListener listener) {
		addListenerObject(listener);
	}

	@Override
	public void removeValidationAdapterManagerChangeListener(IValidationAdapterManagerChangeListener listener) {
		removeListenerObject(listener);
	}

	/**
	 * List of all the current validation roots.
	 */
	protected List<ValidationRoot> myValidationRoots = new ArrayList<ValidationRoot>();

	@Override
	public void reset() {
		for (final ValidationRoot root : myValidationRoots.toArray(new ValidationRoot[0])) {
			removeRoot(root.myRoot, root.myValidationAdapter);
		}
	}

	@Override
	public void addRoot(EObject root, IValidatorAdapter validationAdapter) {
		myValidationRoots.add(new ValidationRoot(root, validationAdapter));
		validate();
	}

	@Override
	public void removeRoot(EObject root, IValidatorAdapter validationAdapter) {
		for (final ValidationRoot r : myValidationRoots) {
			if (r.myRoot == root && r.myValidationAdapter == validationAdapter) {
				myValidationRoots.remove(r);
				r.dispose();
				break;
			}
		}
		validate();
	}

	@Override
	public List<IBindingMessage> getUnboundMessages() {
		return myUnboundMessagesUnmodifiable;
	}

	@Override
	public IObservableList getUnboundMessagesOL() {
		return myUnboundMessagesOLUnmodifiable;
	}

	private final Adapter myChangeAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(Notification notification) {
			if (notification.isTouch()) {
				return;
			}
			super.notifyChanged(notification);
			delayValidation();
		}
	};

	/**
	 * The time when the next validation will be performed.
	 */
	protected long myNextValidation = System.currentTimeMillis();

	@Override
	public void delayValidation() {
		if (validationPaused) {
			return;
		}
		/*
		 * If a new validation has just been scheduled, then ignore this change
		 */
		if (System.currentTimeMillis() < myNextValidation - theManager.getValidationDelay()
				+ theManager.getValidationDelayWindow()) {
			return;
		}
		myNextValidation = System.currentTimeMillis() + theManager.getValidationDelay();
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				Display.getDefault().timerExec(theManager.getValidationDelay(), myDelayRunnable);
			}
		});
	}

	protected Runnable myDelayRunnable = new Runnable() {
		@Override
		public void run() {
			if (myNextValidation > System.currentTimeMillis() + 50) {
				return;
			}
			validate();
		}
	};

	/**
	 * Whether validation is currently paused.
	 */
	private boolean validationPaused = false;

	@Override
	public void validate() {
		myNextValidation = System.currentTimeMillis();
		if (validationPaused) {
			return;
		}
		myChangedObjects.clear();
		myUnboundMessages.clear();
		for (final ValidationRoot r : myValidationRoots) {
			try {
				r.myValidationAdapter.validateObjectTree(r.myRoot, r.myFoundMessages);
			} catch (final Exception ex) {
				LogUtils.error(r.myValidationAdapter, ex);
			}
			myUnboundMessages.addAll(r.myFoundMessages);
		}
		if (Activator.getDefault().TRACE_VALIDATION_RESULT) {
			final StringBuilder sb = new StringBuilder();
			for (final IBindingMessage m : getUnboundMessages()) {
				sb.append("\n  " + m);
			}
			LogUtils.debug(this, "Results:" + sb);
		}
		if (myChangedObjects.size() > 0) {
			myObjects.clear();
			for (final IBindingMessage m : getUnboundMessages()) {
				for (final IBindingMessageTarget t : m.getTargets()) {
					myObjects.add(t.getModelObject());
				}
			}
			for (final Object l : getListeners()) {
				SafeRunner.run(new ISafeRunnable() {
					@Override
					public void run() throws Exception {
						((IValidationAdapterManagerChangeListener) l).affectedObjectsChanged(myChangeEvent);
					}

					@Override
					public void handleException(Throwable exception) {
					}
				});
			}
		}
	}

	@Override
	public void executeWithoutValidation(Runnable runnable) {
		try {
			validationPaused = true;
			runnable.run();
		} finally {
			validationPaused = false;
			delayValidation();
		}
	}

	/**
	 * Map with all created bound messages index by unbound message.
	 * <p>
	 * This way it is easy to remove the bound messages when the unbound message is removed.
	 */
	protected Map<IBindingMessage, List<IBindingMessage>> myBoundMessages = new HashMap<IBindingMessage, List<IBindingMessage>>();

	/**
	 * Mapping of value bindings to message decorators
	 */
	protected final List<IValidatorAdapterMessageDecorator> myDecorators = new ArrayList<IValidatorAdapterMessageDecorator>();

	/**
	 * Adds a new message to the manager, and populates all relevant decorators.
	 * 
	 * @param unboundMessage the new message
	 */
	protected void addUnboundMessage(IBindingMessage unboundMessage) {
		for (final IBindingMessageTarget t : unboundMessage.getTargets()) {
			myChangedObjects.add(t.getModelObject());
		}
		for (final IValidatorAdapterMessageDecorator decorator : myDecorators) {
			createMessageIfNeeded(unboundMessage, decorator);
		}
	}

	/**
	 * Removes an unbound existing message from the manager, and removes all messages from the relevant decorators.
	 * 
	 * @param unboundMessage the message to remove
	 */
	protected void removeUnboundMessage(IBindingMessage unboundMessage) {
		for (final IBindingMessageTarget t : unboundMessage.getTargets()) {
			myChangedObjects.add(t.getModelObject());
		}

		final List<IBindingMessage> messageList = myBoundMessages.get(unboundMessage);
		if (messageList == null) {
			return;
		}

		for (final IBindingMessage m : messageList) {
			final IValueBinding binding = m.getBinding();
			final IValidatorAdapterMessageDecorator decorator = binding
					.getService(IValidatorAdapterMessageDecorator.class);
			if (decorator == null) {
				continue;
			}
			decorator.removeMessage(m);
		}
		myBoundMessages.remove(unboundMessage);
	}

	@Override
	public void addDecorator(IValidatorAdapterMessageDecorator decorator) {
		myDecorators.add(decorator);
		for (final ValidationRoot r : myValidationRoots) {
			for (final Object o : r.myFoundMessages) {
				final IBindingMessage message = (IBindingMessage) o;
				createMessageIfNeeded(message, decorator);
			}
		}
	}

	@Override
	public void removeDecorator(IValidatorAdapterMessageDecorator decorator) {
		myDecorators.remove(decorator);
		final IValueBinding binding = decorator.getBinding();
		for (final List<IBindingMessage> messages : myBoundMessages.values()) {
			for (final IBindingMessage m : messages) {
				if (m.getBinding() == binding) {
					messages.remove(m);
					decorator.removeMessage(m);
					/*
					 * Each unbound message will only have one bound message for each decorator...
					 */
					break;
				}
			}
		}
	}

	@Override
	public void resetDecorator(IValidatorAdapterMessageDecorator decorator) {
		removeDecorator(decorator);
		addDecorator(decorator);
	}

	/**
	 * Matches the specified unbound message against the specified binding and creates a bound message in case of a
	 * match.
	 * <p>
	 * The new bound message is added to the decorator of the binding.
	 * 
	 * @param unboundMessage the message
	 * @param decorator the binding
	 */
	private void createMessageIfNeeded(final IBindingMessage unboundMessage, IValidatorAdapterMessageDecorator decorator) {
		if (!decorator.accept(unboundMessage)) {
			return;
		}
		final IValueBinding binding = decorator.getBinding();

		List<IBindingMessage> messageList = myBoundMessages.get(unboundMessage);
		if (messageList == null) {
			messageList = new ArrayList<IBindingMessage>();
			myBoundMessages.put(unboundMessage, messageList);
		}
		final IBindingMessage boundMessage = new BoundMessage(unboundMessage, binding);

		messageList.add(boundMessage);
		decorator.addMessage(boundMessage);
	}

	/**
	 * Change listener that see the changes in unbound messages.
	 */
	protected IListChangeListener myFoundMessageChangeListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(myUnboundMessageChangeVisitor);
		}
	};

	/**
	 * Set used to collect the object with an associated unbound message.
	 * <p>
	 * Only used in {@link #validate()}.
	 */
	protected final Set<EObject> myObjects = new HashSet<EObject>();
	/**
	 * Unmodifiable version of {@link #myObjects}.
	 */
	protected final Set<EObject> myObjectsUnmodifiable = Collections.unmodifiableSet(myObjects);

	/**
	 * Set used to collect the changed objects of the last validate.
	 * <p>
	 * Only used in {@link #validate()}.
	 */
	protected final Set<EObject> myChangedObjects = new HashSet<EObject>();

	/**
	 * Unmodifiable version of {@link #myChangedObjects}.
	 */
	protected final Set<EObject> myChangedObjectsUnmodifiable = Collections.unmodifiableSet(myChangedObjects);

	/**
	 * The list of unbound messages across all roots.
	 * <p>
	 * Here for optimization purposes...
	 */
	protected final List<IBindingMessage> myUnboundMessages = new ArrayList<IBindingMessage>();

	/**
	 * Unmodifiable version of {@link #myUnboundMessages}.
	 */
	protected final List<IBindingMessage> myUnboundMessagesUnmodifiable = Collections
			.unmodifiableList(myUnboundMessages);

	/**
	 * Observable version of {@link #myUnboundMessages}
	 * <p>
	 * Here for optimization purposes...
	 */
	protected final IObservableList myUnboundMessagesOL = WritableList
			.withElementType(IUIBindingsPackage.Literals.BINDING_MESSAGE);

	/**
	 * Unmodifiable version of {@link #myUnboundMessagesOL}.
	 */
	protected final IObservableList myUnboundMessagesOLUnmodifiable = Observables
			.unmodifiableObservableList(myUnboundMessagesOL);

	protected IValidationAdapterManagerChangeEvent myChangeEvent = new IValidationAdapterManagerChangeEvent() {
		@Override
		public Set<EObject> getCurrentObjects() {
			return myObjectsUnmodifiable;
		}

		@Override
		public Set<EObject> getChangedObjects() {
			return myChangedObjectsUnmodifiable;
		}
	};

	protected ListDiffVisitor myUnboundMessageChangeVisitor = new ListDiffVisitor() {
		@Override
		public void handleAdd(int index, Object element) {
			final IBindingMessage m = (IBindingMessage) element;
			myUnboundMessagesOL.add(m);
			addUnboundMessage(m);
		}

		@Override
		public void handleRemove(int index, Object element) {
			final IBindingMessage m = (IBindingMessage) element;
			myUnboundMessagesOL.remove(m);
			removeUnboundMessage(m);
		}
	};

	/**
	 * The record of one root added via {@link ValidatorAdapterManager#addRoot(EObject, IValidatorAdapter)}.
	 */
	protected class ValidationRoot implements IDisposable {
		public final EObject myRoot;

		public final IValidatorAdapter myValidationAdapter;

		public final IObservableList myFoundMessages = WritableList.withElementType(IBindingMessage.class);

		public ValidationRoot(EObject root, IValidatorAdapter validationAdapter) {
			Assert.isNotNull(root);
			Assert.isNotNull(validationAdapter);
			myRoot = root;
			myValidationAdapter = validationAdapter;
			myRoot.eAdapters().add(myChangeAdapter);
			myFoundMessages.addListChangeListener(myFoundMessageChangeListener);
		}

		@Override
		public void dispose() {
			myFoundMessages.clear();
			myFoundMessages.removeListChangeListener(myFoundMessageChangeListener);
			myRoot.eAdapters().remove(myChangeAdapter);
		}
	}

	@Override
	public int getObjectSeverity(EObject object) {
		int severity = IMessageProvider.NONE;
		for (final IBindingMessage message : getUnboundMessages()) {
			if (message.matches(object, null, null, FeatureMatchingAlgorithm.IGNORE)) {
				final int s = message.getMessageType();
				if (s > severity) {
					severity = s;
				}
			}
			if (severity == IMessageProvider.ERROR) {
				return severity;
			}
		}
		return severity;
	}

	private static class BoundMessage extends AbstractBindingMessage {

		private final IBindingMessage myParentMessage;

		public BoundMessage(IBindingMessage message, IValueBinding binding) {
			super(binding);
			myParentMessage = message;
		}

		@Override
		public int getCode() {
			return myParentMessage.getCode();
		}

		@Override
		public String getPrefix() {
			if (getTargets().size() != 1) {
				return null;
			}
			if (getTargets().get(0).getModelFeature() == null) {
				return null;
			}
			return super.getPrefix();
		}

		@Override
		public BindingMessageSeverity getSeverity() {
			return myParentMessage.getSeverity();
		}

		@Override
		public Object getData() {
			return myParentMessage.getData();
		}

		@Override
		public String getMessage() {
			return myParentMessage.getMessage();
		}

		@Override
		public boolean supersedes(IBindingMessage otherMessage) {
			if (otherMessage instanceof BoundMessage) {
				final BoundMessage m = (BoundMessage) otherMessage;
				if (myParentMessage.supersedes(m.myParentMessage)) {
					return true;
				}

			}
			return myParentMessage.supersedes(otherMessage);
		}

		@Override
		public boolean matches(EObject obj, EStructuralFeature feature, Object key, FeatureMatchingAlgorithm algorithm) {
			return myParentMessage.matches(obj, feature, key, algorithm);
		}

		@Override
		public EList<IBindingMessageTarget> getTargets() {
			return myParentMessage.getTargets();
		}
	}

}