/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.ee;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * DOCUMENT ME!
 *
 * @author   spuhl
 * @version  $Revision$, $Date$
 */
//ToDo Testcases
public class EJBAccessor<T> {

    //~ Static fields/initializers ---------------------------------------------

    private static final String ORB_INITIAL_HOST = "org.omg.CORBA.ORBInitialHost";
    private static final String ORB_INITIAL_PORT = "org.omg.CORBA.ORBInitialPort";
    private static final Logger log = org.apache.log4j.Logger.getLogger(EJBAccessor.class);

    //~ Instance fields --------------------------------------------------------

    private Properties initalContextProperties;
    private T ejbInterface;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EJBAccessor object.
     *
     * @param  initalContextProperties  DOCUMENT ME!
     */
    public EJBAccessor(final Properties initalContextProperties) {
        log.info("EJBACCESSOR: server: " + initalContextProperties.getProperty(ORB_INITIAL_HOST));
        log.info("EJBACCESSOR: orbPort: " + initalContextProperties.getProperty(ORB_INITIAL_PORT));
        if (initalContextProperties == null) {
            this.initalContextProperties = getDefaultProperties();
        } else {
            this.initalContextProperties = initalContextProperties;
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * ToDo reflect generic/method.
     *
     * @param   type  DOCUMENT ME!
     *
     * @throws  NamingException  DOCUMENT ME!
     */
    public void initEJBAccessor(final Class<T> type) throws NamingException {
        final InitialContext ic = new InitialContext(initalContextProperties);
        ejbInterface = (T)ic.lookup(type.getName());
    }

    /**
     * DOCUMENT ME!
     *
     * @param   <E>      DOCUMENT ME!
     * @param   host     DOCUMENT ME!
     * @param   orbPort  DOCUMENT ME!
     * @param   type     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  NamingException  DOCUMENT ME!
     */
    public static <E> EJBAccessor<E> createEJBAccessor(final String host, final String orbPort, final Class<E> type)
            throws NamingException {
        final Properties props = new Properties();
        props.setProperty(ORB_INITIAL_HOST, host);
        props.setProperty(ORB_INITIAL_PORT, orbPort);
        final EJBAccessor<E> ejbAccess = new EJBAccessor<E>(props);
        ejbAccess.initEJBAccessor(type);
        return ejbAccess;
    }

    /**
     * ToDo reflect generic/method public static <T> EJBAccessor<T> createEJBAccessor(final String host, final String
     * orbPort) throws NamingException { final Properties props = new Properties(); props.setProperty(ORB_INITIAL_HOST,
     * host); props.setProperty(ORB_INITIAL_PORT, orbPort); final EJBAccessor<T> ejbAccess = new EJBAccessor<T>(props);
     * ejbAccess.initEJBAccessor(T); return ejbAccess; }.
     *
     * @param   <E>   DOCUMENT ME!
     * @param   type  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  NamingException  DOCUMENT ME!
     */
    public static <E> EJBAccessor<E> createEJBAccessor(final Class<E> type) throws NamingException {
        final EJBAccessor ejbAccessor = new EJBAccessor<E>(getDefaultProperties());
        ejbAccessor.initEJBAccessor(type);
        return ejbAccessor;
    }

    /**
     * ToDo reflect generic/method public static <E> EJBAccessor<E> createEJBAccessor() throws NamingException { final
     * EJBAccessor ejbAccessor = new EJBAccessor<E>(getDefaultProperties()); ejbAccessor.initEJBAccessor(); return
     * ejbAccessor; }.
     *
     * @return  DOCUMENT ME!
     */
    public static Properties getDefaultProperties() {
        final Properties props = new Properties();
        props.setProperty(ORB_INITIAL_HOST, "localhost");
        props.setProperty(ORB_INITIAL_PORT, "3700");
        return props;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Properties getInitalContextProperties() {
        return initalContextProperties;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  initalContextProperties  DOCUMENT ME!
     */
    public void setInitalContextProperties(final Properties initalContextProperties) {
        this.initalContextProperties = initalContextProperties;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public T getEjbInterface() {
        return ejbInterface;
    }

    // ToDo reflect generic/method
// private String getEJBInterfaceClassName() {
// try {
// Field ejbInterfaceField = getClass().getDeclaredField("ejbInterface");
// ejbInterfaceField.setAccessible(true);
// System.out.println("type: " + getClass().getDeclaredField("ejbInterface").getType());
// System.out.println("type name: " + getClass().getDeclaredField("ejbInterface").getType().getName());
// return getClass().getField("ejbInterface").getType().getName();
// } catch (NoSuchFieldException ex) {
// return null;
// }
// }
}
