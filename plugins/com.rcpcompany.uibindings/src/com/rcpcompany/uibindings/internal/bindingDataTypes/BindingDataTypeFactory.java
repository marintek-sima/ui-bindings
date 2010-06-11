package com.rcpcompany.uibindings.internal.bindingDataTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EPackage.Registry;

import com.rcpcompany.uibindings.IBindingDataType;
import com.rcpcompany.utils.logging.LogUtils;

/**
 * Factory for {@link IBindingDataType} objects.
 * 
 * @author Tonny Madsen, The RCP Company
 */

public class BindingDataTypeFactory {
	private final static Map<Object, IBindingDataType> dataTypeMapping = new HashMap<Object, IBindingDataType>();

	/**
	 * Creates and returns a new {@link IBindingDataType binding data type} appropriate for the specified element.
	 * <p>
	 * The result is cached and reused.
	 * 
	 * @param element the element to return a data type for
	 * @return the data type object or <code>null</code>
	 */
	public static IBindingDataType create(Object element) {
		if (dataTypeMapping.containsKey(element)) {
			return dataTypeMapping.get(element);
		}

		IBindingDataType dt = null;
		if (element instanceof EClassifier) {
			dt = new EClassifierBindingDataType((EClassifier) element);
		} else if (element instanceof EStructuralFeature) {
			dt = new EStructuralFeatureBindingDataType((EStructuralFeature) element);
		} else if (element instanceof EEnumLiteral) {
			dt = new EEnumLiteralBindingDataType((EEnumLiteral) element);
		} else if (element instanceof Class<?>) {
			/*
			 * Try to look up the instance class to find a proper EClassifier
			 */
			final EClassifier classifier = IBindingDataType.Factory.convertToClassifier((Class<?>) element);
			if (classifier != null) {
				dt = create(classifier);
			} else {
				dt = new JavaClassBindingDataType((Class<?>) element);
			}
		} else if (element == null) {
			dt = create(EcorePackage.Literals.EJAVA_OBJECT);
		} else {
			LogUtils.error(element, "No IBindingDataType for " + element); //$NON-NLS-1$
			dt = null;
		}
		dataTypeMapping.put(element, dt);
		return dt;
	}

	/**
	 * Tries to convert a Java class to the corresponding {@link EClassifier}.
	 * <p>
	 * All registered EMF packages are searched.
	 * 
	 * @param cls the class to convert
	 * @return the corresponding classifier or <code>null</code> if not found
	 */
	public static EClassifier convertToClassifier(Class<?> cls) {
		/*
		 * The interface class that corresponds to clss - if any exist
		 */
		Class<?> ifCls = null;
		if (!cls.isInterface()) {
			final Class<?>[] interfaces = cls.getInterfaces();
			if (interfaces.length > 0) {
				ifCls = interfaces[0];
			}
		}

		/*
		 * To check the ecore package first! Otherwise the XML Type package [http://www.eclipse.org/emf/2003/XMLType]
		 * will overshadow the basic Java types.
		 */
		for (final EClassifier c : EcorePackage.eINSTANCE.getEClassifiers()) {
			if (c.getInstanceClass() == cls) {
				return c;
			}
			if (ifCls != null && c.getInstanceClass() == ifCls) {
				return c;
			}
		}

		/*
		 * Now try all the packages
		 */
		final Registry registry = EPackage.Registry.INSTANCE;
		for (final Object v : registry.values()) {
			if (v == EcorePackage.eINSTANCE) {
				continue;
			}
			if (!(v instanceof EPackage)) {
				continue;
			}
			final EPackage ep = (EPackage) v;

			for (final EClassifier c : ep.getEClassifiers()) {
				if (c.getInstanceClass() == cls) {
					return c;
				}
				if (ifCls != null && c.getInstanceClass() == ifCls) {
					return c;
				}
			}
		}

		return null;
	}

	/**
	 * Mapping from class to the set of super classes as defined by {@link IAdapterManager#computeClassOrder(Class)}.
	 */
	private final static Map<IBindingDataType, IBindingDataType[]> superTypeMapping = new HashMap<IBindingDataType, IBindingDataType[]>();

	/**
	 * Returns a list of the {@link IBindingDataType} objects that defines all the super types of the specified data
	 * type.
	 * <p>
	 * If not already calculated, then do that by creating an array with
	 * <ul>
	 * <li>IBDTs for all super types (ECore classes)</li>
	 * <li>IBDTs for all super classes (Java classes) not already added from their Ecore counterparts</li>
	 * </ul>
	 * 
	 * @param dt the data type to test
	 * @return the super types
	 */
	public static IBindingDataType[] getSuperTypes(IBindingDataType dt) {
		IBindingDataType[] dts = superTypeMapping.get(dt);
		if (dts == null) {
			final List<IBindingDataType> dtList = new ArrayList<IBindingDataType>();
			final EClassifier classifier = dt.getEType();
			if (classifier != null) {
				dtList.add(BindingDataTypeFactory.create(classifier));
				if (classifier instanceof EClass) {
					final EClass ec = (EClass) classifier;
					final EList<EClass> allSuperTypes = ec.getEAllSuperTypes();
					for (final EClass e : allSuperTypes) {
						dtList.add(BindingDataTypeFactory.create(e));
					}
				}
			}
			final Class<?>[] superClasses = Platform.getAdapterManager().computeClassOrder(dt.getDataType());
			for (final Class<?> c : superClasses) {
				boolean drop = false;
				for (final IBindingDataType d : dtList) {
					if (d.getDataType() == c) {
						drop = true;
						break;
					}
				}
				if (drop) {
					continue;
				}
				dtList.add(BindingDataTypeFactory.create(c));
			}
			dts = dtList.toArray(new IBindingDataType[0]);
			superTypeMapping.put(dt, dts);
		}
		return dts;
	}
}