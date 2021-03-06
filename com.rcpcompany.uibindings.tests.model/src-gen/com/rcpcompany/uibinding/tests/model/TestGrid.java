/**
 */
package com.rcpcompany.uibinding.tests.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Test Grid</b></em>'. <!--
 * end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.rcpcompany.uibinding.tests.model.TestGrid#getColumns <em>Columns</em>}</li>
 * <li>{@link com.rcpcompany.uibinding.tests.model.TestGrid#getRows <em>Rows</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.rcpcompany.uibinding.tests.model.TestModelPackage#getTestGrid()
 * @generated
 */
public interface TestGrid extends EObject {
	/**
	 * Returns the value of the '<em><b>Columns</b></em>' reference list. The list contents are of
	 * type {@link com.rcpcompany.uibinding.tests.model.TestGridColumn}. It is bidirectional and its
	 * opposite is '{@link com.rcpcompany.uibinding.tests.model.TestGridColumn#getGrid
	 * <em>Grid</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Columns</em>' reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Columns</em>' reference list.
	 * @see com.rcpcompany.uibinding.tests.model.TestModelPackage#getTestGrid_Columns()
	 * @see com.rcpcompany.uibinding.tests.model.TestGridColumn#getGrid
	 * @generated
	 */
	EList<TestGridColumn> getColumns();

	/**
	 * Returns the value of the '<em><b>Rows</b></em>' reference list. The list contents are of type
	 * {@link com.rcpcompany.uibinding.tests.model.TestGridRow}. It is bidirectional and its
	 * opposite is '{@link com.rcpcompany.uibinding.tests.model.TestGridRow#getGrid <em>Grid</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rows</em>' reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rows</em>' reference list.
	 * @see com.rcpcompany.uibinding.tests.model.TestModelPackage#getTestGrid_Rows()
	 * @see com.rcpcompany.uibinding.tests.model.TestGridRow#getGrid
	 * @generated
	 */
	EList<TestGridRow> getRows();

} // TestGrid
