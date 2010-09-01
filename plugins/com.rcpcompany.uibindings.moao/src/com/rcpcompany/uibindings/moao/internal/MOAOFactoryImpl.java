/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.rcpcompany.uibindings.moao.internal;

import com.rcpcompany.uibindings.moao.*;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MOAOFactoryImpl extends EFactoryImpl implements IMOAOFactory {
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static IMOAOFactory init() {
    try {
      IMOAOFactory theMOAOFactory = (IMOAOFactory)EPackage.Registry.INSTANCE.getEFactory("http://com.rcpcompany/schemas/moao.ecore"); 
      if (theMOAOFactory != null) {
        return theMOAOFactory;
      }
    }
    catch (Exception exception) {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new MOAOFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MOAOFactoryImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass) {
    switch (eClass.getClassifierID()) {
      case IMOAOPackage.MOAO: return createMOAO();
      case IMOAOPackage.NAMED_OBJECT: return createNamedObject();
      case IMOAOPackage.MOAO_MESSAGE: return createMOAOMessage();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue) {
    switch (eDataType.getClassifierID()) {
      case IMOAOPackage.SEVERITY:
        return createSeverityFromString(eDataType, initialValue);
      case IMOAOPackage.DIAGNOSTIC_CHAIN:
        return createDiagnosticChainFromString(eDataType, initialValue);
      case IMOAOPackage.MAP:
        return createMapFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue) {
    switch (eDataType.getClassifierID()) {
      case IMOAOPackage.SEVERITY:
        return convertSeverityToString(eDataType, instanceValue);
      case IMOAOPackage.DIAGNOSTIC_CHAIN:
        return convertDiagnosticChainToString(eDataType, instanceValue);
      case IMOAOPackage.MAP:
        return convertMapToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IMOAO createMOAO() {
    MOAOImpl moao = new MOAOImpl();
    return moao;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public INamedObject createNamedObject() {
    NamedObjectImpl namedObject = new NamedObjectImpl();
    return namedObject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IMOAOMessage createMOAOMessage() {
    MOAOMessageImpl moaoMessage = new MOAOMessageImpl();
    return moaoMessage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Severity createSeverityFromString(EDataType eDataType, String initialValue) {
    Severity result = Severity.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertSeverityToString(EDataType eDataType, Object instanceValue) {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiagnosticChain createDiagnosticChainFromString(EDataType eDataType, String initialValue) {
    return (DiagnosticChain)super.createFromString(eDataType, initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDiagnosticChainToString(EDataType eDataType, Object instanceValue) {
    return super.convertToString(eDataType, instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Map<?, ?> createMapFromString(EDataType eDataType, String initialValue) {
    return (Map<?, ?>)super.createFromString(initialValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertMapToString(EDataType eDataType, Object instanceValue) {
    return super.convertToString(instanceValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public IMOAOPackage getMOAOPackage() {
    return (IMOAOPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static IMOAOPackage getPackage() {
    return IMOAOPackage.eINSTANCE;
  }

} //MOAOFactoryImpl