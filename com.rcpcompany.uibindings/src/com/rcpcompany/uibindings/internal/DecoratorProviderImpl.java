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
package com.rcpcompany.uibindings.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.rcpcompany.uibindings.IDecoratorProvider;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IUIBindingDecorator;
import com.rcpcompany.uibindings.IUIBindingsPackage;
import com.rcpcompany.uibindings.ModelValueKind;
import com.rcpcompany.uibindings.UIBindingsUtils;
import com.rcpcompany.utils.logging.LogUtils;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Decorator Provider</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getManager <em>Manager</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getId <em>Id</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getType <em>Type</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getModelTypes <em>Model Types
 * </em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getUiTypes <em>Ui Types</em>}
 * </li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getProviderCE <em>Provider CE
 * </em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getChildCE <em>Child CE</em>}
 * </li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getDecorator <em>Decorator
 * </em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#isExactModelTypeMatch <em>
 * Exact Model Type Match</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.DecoratorProviderImpl#getModelValueKind <em>Model
 * Value Kind</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class DecoratorProviderImpl extends EObjectImpl implements IDecoratorProvider {
	@Override
	public abstract IUIBindingDecorator getDecorator();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isExactModelTypeMatch() {
		return exactModelTypeMatch;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setExactModelTypeMatch(boolean newExactModelTypeMatch) {
		final boolean oldExactModelTypeMatch = exactModelTypeMatch;
		exactModelTypeMatch = newExactModelTypeMatch;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					IUIBindingsPackage.DECORATOR_PROVIDER__EXACT_MODEL_TYPE_MATCH, oldExactModelTypeMatch,
					exactModelTypeMatch));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ModelValueKind getModelValueKind() {
		return modelValueKind;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setModelValueKind(ModelValueKind newModelValueKind) {
		final ModelValueKind oldModelValueKind = modelValueKind;
		modelValueKind = newModelValueKind == null ? MODEL_VALUE_KIND_EDEFAULT : newModelValueKind;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_VALUE_KIND, oldModelValueKind, modelValueKind));
		}
	}

	@Override
	public void providerReader(String id, IConfigurationElement providerCE, IConfigurationElement childCE) {
		setId(id);
		setProviderCE(providerCE);
		setChildCE(childCE);

		String attr;
		attr = providerCE.getAttribute(InternalConstants.TYPE_TAG);
		// No type is not OK, but an empty name is fine
		if (attr == null) {
			attr = ""; //$NON-NLS-1$
		}
		setType(attr);

		attr = providerCE.getAttribute(InternalConstants.MODEL_VALUE_KIND_TAG);
		if (attr == null || attr.length() == 0) {
			setModelValueKind(ModelValueKind.VALUE);
		} else {
			try {
				setModelValueKind(ModelValueKind.valueOf(attr));
			} catch (final Exception ex) {
				LogUtils.error(this, ex);
				setModelValueKind(ModelValueKind.VALUE);
			}
		}

		attr = providerCE.getAttribute(InternalConstants.EXACT_MODEL_TYPE_MATCH_TAG);
		setExactModelTypeMatch(attr != null && Boolean.valueOf(attr).booleanValue());

		final EList<String> modelTypes = getModelTypes();
		for (final IConfigurationElement child : providerCE.getChildren(InternalConstants.MODEL_TYPE_TAG)) {
			attr = child.getAttribute(InternalConstants.ALSO_PRIMITIVE_TAG);
			final boolean alsoPrimitive = attr == null || Boolean.valueOf(attr).booleanValue();
			attr = child.getAttribute(InternalConstants.CLASS_TAG);
			if (attr == null || attr.length() == 0) {
				LogUtils.error(providerCE, "Required attribute class is empty. Ignored."); //$NON-NLS-1$
				break;
			}
			if (modelTypes.contains(attr)) {
				LogUtils.error(child, "Duplicate model type: '" + attr + "'. Ignored."); //$NON-NLS-1$ //$NON-NLS-2$
			}
			modelTypes.add(attr);
			if (alsoPrimitive) {
				final String primitiveType = UIBindingsUtils.getBoxed2Primitive(attr);
				if (primitiveType != null) {
					modelTypes.add(primitiveType);
				}
			}
		}
		if (modelTypes.size() == 0 && getType().isEmpty()) {
			LogUtils.error(providerCE, "No Model Types supplied and no type specified. Ignored."); //$NON-NLS-1$
		}

		final EList<String> uiTypes = getUiTypes();
		for (final IConfigurationElement child : providerCE.getChildren(InternalConstants.UI_TYPE_TAG)) {
			attr = child.getAttribute(InternalConstants.ALSO_PRIMITIVE_TAG);
			final boolean alsoPrimitive = attr == null || Boolean.valueOf(attr).booleanValue();
			attr = child.getAttribute(InternalConstants.CLASS_TAG);
			if (attr == null || attr.length() == 0) {
				LogUtils.error(providerCE, "Required attribute class is empty. Ignored."); //$NON-NLS-1$
				break;
			}
			if (uiTypes.contains(attr)) {
				LogUtils.error(child, "Duplicate target type: '" + attr + "'. Ignored."); //$NON-NLS-1$ //$NON-NLS-2$
			}
			uiTypes.add(attr);
			if (alsoPrimitive) {
				final String primitiveType = UIBindingsUtils.getBoxed2Primitive(attr);
				if (primitiveType != null) {
					uiTypes.add(primitiveType);
				}
			}
		}
		if (uiTypes.size() == 0) {
			LogUtils.error(providerCE, "No UI Types supplied. Ignored."); //$NON-NLS-1$
		}
	}

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModelTypes() <em>Model Types</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> modelTypes;

	/**
	 * The cached value of the '{@link #getUiTypes() <em>Ui Types</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUiTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<String> uiTypes;

	/**
	 * The default value of the '{@link #getProviderCE() <em>Provider CE</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProviderCE()
	 * @generated
	 * @ordered
	 */
	protected static final IConfigurationElement PROVIDER_CE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProviderCE() <em>Provider CE</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProviderCE()
	 * @generated
	 * @ordered
	 */
	protected IConfigurationElement providerCE = PROVIDER_CE_EDEFAULT;

	/**
	 * The default value of the '{@link #getChildCE() <em>Child CE</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChildCE()
	 * @generated
	 * @ordered
	 */
	protected static final IConfigurationElement CHILD_CE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getChildCE() <em>Child CE</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChildCE()
	 * @generated
	 * @ordered
	 */
	protected IConfigurationElement childCE = CHILD_CE_EDEFAULT;

	/**
	 * The default value of the '{@link #isExactModelTypeMatch() <em>Exact Model Type Match</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isExactModelTypeMatch()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXACT_MODEL_TYPE_MATCH_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExactModelTypeMatch() <em>Exact Model Type Match</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isExactModelTypeMatch()
	 * @generated
	 * @ordered
	 */
	protected boolean exactModelTypeMatch = EXACT_MODEL_TYPE_MATCH_EDEFAULT;

	/**
	 * The default value of the '{@link #getModelValueKind() <em>Model Value Kind</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelValueKind()
	 * @generated
	 * @ordered
	 */
	protected static final ModelValueKind MODEL_VALUE_KIND_EDEFAULT = ModelValueKind.VALUE;

	/**
	 * The cached value of the '{@link #getModelValueKind() <em>Model Value Kind</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelValueKind()
	 * @generated
	 * @ordered
	 */
	protected ModelValueKind modelValueKind = MODEL_VALUE_KIND_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DecoratorProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IUIBindingsPackage.Literals.DECORATOR_PROVIDER;
	}

	private Map<String, Object> myDeclaredArguments = null;

	@Override
	public Map<String, Object> getArguments() {
		if (myDeclaredArguments == null) {
			myDeclaredArguments = new HashMap<String, Object>();
		}
		return myDeclaredArguments;
	}

	@Override
	public boolean hasArguments() {
		return myDeclaredArguments != null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IManager getManager() {
		if (eContainerFeatureID() != IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER) return null;
		return (IManager) eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetManager(IManager newManager, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newManager, IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setManager(IManager newManager) {
		if (newManager != eInternalContainer()
				|| (eContainerFeatureID() != IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER && newManager != null)) {
			if (EcoreUtil.isAncestor(this, newManager))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newManager != null) {
				msgs = ((InternalEObject) newManager).eInverseAdd(this, IUIBindingsPackage.MANAGER__PROVIDERS,
						IManager.class, msgs);
			}
			msgs = basicSetManager(newManager, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER,
					newManager, newManager));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		final String oldId = id;
		id = newId;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IUIBindingsPackage.DECORATOR_PROVIDER__ID, oldId, id));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		final String oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IUIBindingsPackage.DECORATOR_PROVIDER__TYPE, oldType,
					type));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getModelTypes() {
		if (modelTypes == null) {
			modelTypes = new EDataTypeUniqueEList<String>(String.class, this,
					IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_TYPES);
		}
		return modelTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<String> getUiTypes() {
		if (uiTypes == null) {
			uiTypes = new EDataTypeUniqueEList<String>(String.class, this,
					IUIBindingsPackage.DECORATOR_PROVIDER__UI_TYPES);
		}
		return uiTypes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IConfigurationElement getProviderCE() {
		return providerCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setProviderCE(IConfigurationElement newProviderCE) {
		final IConfigurationElement oldProviderCE = providerCE;
		providerCE = newProviderCE;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IUIBindingsPackage.DECORATOR_PROVIDER__PROVIDER_CE,
					oldProviderCE, providerCE));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IConfigurationElement getChildCE() {
		return childCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setChildCE(IConfigurationElement newChildCE) {
		final IConfigurationElement oldChildCE = childCE;
		childCE = newChildCE;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, IUIBindingsPackage.DECORATOR_PROVIDER__CHILD_CE,
					oldChildCE, childCE));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetManager((IManager) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER:
			return basicSetManager(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER:
			return eInternalContainer().eInverseRemove(this, IUIBindingsPackage.MANAGER__PROVIDERS, IManager.class,
					msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER:
			return getManager();
		case IUIBindingsPackage.DECORATOR_PROVIDER__ID:
			return getId();
		case IUIBindingsPackage.DECORATOR_PROVIDER__TYPE:
			return getType();
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_TYPES:
			return getModelTypes();
		case IUIBindingsPackage.DECORATOR_PROVIDER__UI_TYPES:
			return getUiTypes();
		case IUIBindingsPackage.DECORATOR_PROVIDER__PROVIDER_CE:
			return getProviderCE();
		case IUIBindingsPackage.DECORATOR_PROVIDER__CHILD_CE:
			return getChildCE();
		case IUIBindingsPackage.DECORATOR_PROVIDER__DECORATOR:
			return getDecorator();
		case IUIBindingsPackage.DECORATOR_PROVIDER__EXACT_MODEL_TYPE_MATCH:
			return isExactModelTypeMatch();
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_VALUE_KIND:
			return getModelValueKind();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER:
			setManager((IManager) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__ID:
			setId((String) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__TYPE:
			setType((String) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_TYPES:
			getModelTypes().clear();
			getModelTypes().addAll((Collection<? extends String>) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__UI_TYPES:
			getUiTypes().clear();
			getUiTypes().addAll((Collection<? extends String>) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__PROVIDER_CE:
			setProviderCE((IConfigurationElement) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__CHILD_CE:
			setChildCE((IConfigurationElement) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__EXACT_MODEL_TYPE_MATCH:
			setExactModelTypeMatch((Boolean) newValue);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_VALUE_KIND:
			setModelValueKind((ModelValueKind) newValue);
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
		case IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER:
			setManager((IManager) null);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__ID:
			setId(ID_EDEFAULT);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__TYPE:
			setType(TYPE_EDEFAULT);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_TYPES:
			getModelTypes().clear();
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__UI_TYPES:
			getUiTypes().clear();
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__PROVIDER_CE:
			setProviderCE(PROVIDER_CE_EDEFAULT);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__CHILD_CE:
			setChildCE(CHILD_CE_EDEFAULT);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__EXACT_MODEL_TYPE_MATCH:
			setExactModelTypeMatch(EXACT_MODEL_TYPE_MATCH_EDEFAULT);
			return;
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_VALUE_KIND:
			setModelValueKind(MODEL_VALUE_KIND_EDEFAULT);
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
		case IUIBindingsPackage.DECORATOR_PROVIDER__MANAGER:
			return getManager() != null;
		case IUIBindingsPackage.DECORATOR_PROVIDER__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case IUIBindingsPackage.DECORATOR_PROVIDER__TYPE:
			return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_TYPES:
			return modelTypes != null && !modelTypes.isEmpty();
		case IUIBindingsPackage.DECORATOR_PROVIDER__UI_TYPES:
			return uiTypes != null && !uiTypes.isEmpty();
		case IUIBindingsPackage.DECORATOR_PROVIDER__PROVIDER_CE:
			return PROVIDER_CE_EDEFAULT == null ? providerCE != null : !PROVIDER_CE_EDEFAULT.equals(providerCE);
		case IUIBindingsPackage.DECORATOR_PROVIDER__CHILD_CE:
			return CHILD_CE_EDEFAULT == null ? childCE != null : !CHILD_CE_EDEFAULT.equals(childCE);
		case IUIBindingsPackage.DECORATOR_PROVIDER__DECORATOR:
			return getDecorator() != null;
		case IUIBindingsPackage.DECORATOR_PROVIDER__EXACT_MODEL_TYPE_MATCH:
			return exactModelTypeMatch != EXACT_MODEL_TYPE_MATCH_EDEFAULT;
		case IUIBindingsPackage.DECORATOR_PROVIDER__MODEL_VALUE_KIND:
			return modelValueKind != MODEL_VALUE_KIND_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: "); //$NON-NLS-1$
		result.append(id);
		result.append(", type: "); //$NON-NLS-1$
		result.append(type);
		result.append(", modelTypes: "); //$NON-NLS-1$
		result.append(modelTypes);
		result.append(", uiTypes: "); //$NON-NLS-1$
		result.append(uiTypes);
		result.append(", providerCE: "); //$NON-NLS-1$
		result.append(providerCE);
		result.append(", childCE: "); //$NON-NLS-1$
		result.append(childCE);
		result.append(", exactModelTypeMatch: "); //$NON-NLS-1$
		result.append(exactModelTypeMatch);
		result.append(", modelValueKind: "); //$NON-NLS-1$
		result.append(modelValueKind);
		result.append(')');
		return result.toString();
	}

} // DecoratorProviderImpl
