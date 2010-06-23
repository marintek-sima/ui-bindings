/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package com.rcpcompany.uibindings.navigator.internal;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.rcpcompany.uibindings.navigator.IEditorModelType;
import com.rcpcompany.uibindings.navigator.IEditorPartDescriptor;
import com.rcpcompany.uibindings.navigator.INavigatorManager;
import com.rcpcompany.uibindings.navigator.INavigatorModelFactory;
import com.rcpcompany.uibindings.navigator.INavigatorModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class NavigatorModelFactoryImpl extends EFactoryImpl implements INavigatorModelFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static INavigatorModelFactory init() {
		try {
			final INavigatorModelFactory theNavigatorModelFactory = (INavigatorModelFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://rcpcompany.com/schemas/uibindings/navigator");
			if (theNavigatorModelFactory != null) { return theNavigatorModelFactory; }
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NavigatorModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NavigatorModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case INavigatorModelPackage.NAVIGATOR_MANAGER:
			return createNavigatorManager();
		case INavigatorModelPackage.EDITOR_MODEL_TYPE:
			return createEditorModelType();
		case INavigatorModelPackage.EDITOR_PART_DESCRIPTOR:
			return createEditorPartDescriptor();
		case INavigatorModelPackage.STRING_TO_MODEL_TYPE_MAP_ENTRY:
			return (EObject) createStringToModelTypeMapEntry();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	private INavigatorManager theManager = null;

	@Override
	public INavigatorManager getManager() {
		if (theManager == null) {
			theManager = createNavigatorManager();
		}
		return theManager;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public INavigatorManager createNavigatorManager() {
		final NavigatorManagerImpl navigatorManager = new NavigatorManagerImpl();
		return navigatorManager;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IEditorModelType createEditorModelType() {
		final EditorModelTypeImpl editorModelType = new EditorModelTypeImpl();
		return editorModelType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IEditorPartDescriptor createEditorPartDescriptor() {
		final EditorPartDescriptorImpl editorPartDescriptor = new EditorPartDescriptorImpl();
		return editorPartDescriptor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, IEditorModelType> createStringToModelTypeMapEntry() {
		final StringToModelTypeMapEntryImpl stringToModelTypeMapEntry = new StringToModelTypeMapEntryImpl();
		return stringToModelTypeMapEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public INavigatorModelPackage getNavigatorModelPackage() {
		return (INavigatorModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static INavigatorModelPackage getPackage() {
		return INavigatorModelPackage.eINSTANCE;
	}

} // NavigatorModelFactoryImpl