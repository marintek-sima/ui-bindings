/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.rcpcompany.uibindings.internal;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.rcpcompany.uibindings.IConstantTreeItem;
import com.rcpcompany.uibindings.ITreeItemDescriptor;
import com.rcpcompany.uibindings.IUIBindingsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Anon Cell Item</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.rcpcompany.uibindings.internal.ConstantTreeItemImpl#getDeclaredArguments <em>Declared Arguments</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.ConstantTreeItemImpl#getDescriptor <em>Descriptor</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.ConstantTreeItemImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ConstantTreeItemImpl extends EObjectImpl implements IConstantTreeItem {
	/**
	 * The cached value of the '{@link #getDeclaredArguments() <em>Declared Arguments</em>}' map. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getDeclaredArguments()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Object> declaredArguments;

	/**
	 * The cached value of the '{@link #getDescriptor() <em>Descriptor</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getDescriptor()
	 * @generated
	 * @ordered
	 */
	protected ITreeItemDescriptor descriptor;
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EObject target;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ConstantTreeItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IUIBindingsPackage.Literals.CONSTANT_TREE_ITEM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EMap<String, Object> getDeclaredArguments() {
		if (declaredArguments == null) {
			declaredArguments = new EcoreEMap<String, Object>(IUIBindingsPackage.Literals.STRING_TO_OBJECT_MAP_ENTRY,
					StringToObjectMapEntryImpl.class, this, IUIBindingsPackage.CONSTANT_TREE_ITEM__DECLARED_ARGUMENTS);
		}
		return declaredArguments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ITreeItemDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDescriptor(ITreeItemDescriptor newDescriptor) {
		final ITreeItemDescriptor oldDescriptor = descriptor;
		descriptor = newDescriptor;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IUIBindingsPackage.CONSTANT_TREE_ITEM__DESCRIPTOR,
					oldDescriptor, descriptor));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTarget(EObject newTarget) {
		final EObject oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IUIBindingsPackage.CONSTANT_TREE_ITEM__TARGET,
					oldTarget, target));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DECLARED_ARGUMENTS:
			return ((InternalEList<?>) getDeclaredArguments()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DECLARED_ARGUMENTS:
			if (coreType) {
				return getDeclaredArguments();
			} else {
				return getDeclaredArguments().map();
			}
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DESCRIPTOR:
			return getDescriptor();
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__TARGET:
			return getTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DECLARED_ARGUMENTS:
			((EStructuralFeature.Setting) getDeclaredArguments()).set(newValue);
			return;
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DESCRIPTOR:
			setDescriptor((ITreeItemDescriptor) newValue);
			return;
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__TARGET:
			setTarget((EObject) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DECLARED_ARGUMENTS:
			getDeclaredArguments().clear();
			return;
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DESCRIPTOR:
			setDescriptor((ITreeItemDescriptor) null);
			return;
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__TARGET:
			setTarget((EObject) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DECLARED_ARGUMENTS:
			return declaredArguments != null && !declaredArguments.isEmpty();
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__DESCRIPTOR:
			return descriptor != null;
		case IUIBindingsPackage.CONSTANT_TREE_ITEM__TARGET:
			return target != null;
		}
		return super.eIsSet(featureID);
	}

} // AnonCellItemImpl