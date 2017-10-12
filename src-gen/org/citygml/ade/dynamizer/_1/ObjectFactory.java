//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.4.3+1
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Wed Oct 11 16:43:16 CEST 2017
//


package org.citygml.ade.dynamizer._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.citygml.ade.dynamizer._1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TimeseriesComponent_QNAME = new QName("http://www.citygml.org/ade/dynamizer_ade/1.0", "TimeseriesComponent");
    private final static QName _SensorConnection_QNAME = new QName("http://www.citygml.org/ade/dynamizer_ade/1.0", "SensorConnection");
    private final static QName _Dynamizers_QNAME = new QName("http://www.citygml.org/ade/dynamizer_ade/1.0", "dynamizers");
    private final static QName _AbstractTimeseries_QNAME = new QName("http://www.citygml.org/ade/dynamizer_ade/1.0", "AbstractTimeseries");
    private final static QName _Dynamizer_QNAME = new QName("http://www.citygml.org/ade/dynamizer_ade/1.0", "Dynamizer");
    private final static QName _CompositeTimeseries_QNAME = new QName("http://www.citygml.org/ade/dynamizer_ade/1.0", "CompositeTimeseries");
    private final static QName _AtomicTimeseries_QNAME = new QName("http://www.citygml.org/ade/dynamizer_ade/1.0", "AtomicTimeseries");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.citygml.ade.dynamizer._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CompositeTimeseriesType }
     * 
     */
    public CompositeTimeseriesType createCompositeTimeseriesType() {
        return new CompositeTimeseriesType();
    }

    /**
     * Create an instance of {@link AbstractTimeseriesType }
     * 
     */
    public AbstractTimeseriesType createAbstractTimeseriesType() {
        return new AbstractTimeseriesType();
    }

    /**
     * Create an instance of {@link SensorConnectionType }
     * 
     */
    public SensorConnectionType createSensorConnectionType() {
        return new SensorConnectionType();
    }

    /**
     * Create an instance of {@link DynamizerType }
     * 
     */
    public DynamizerType createDynamizerType() {
        return new DynamizerType();
    }

    /**
     * Create an instance of {@link AtomicTimeseriesType }
     * 
     */
    public AtomicTimeseriesType createAtomicTimeseriesType() {
        return new AtomicTimeseriesType();
    }

    /**
     * Create an instance of {@link DynamizerPropertyType }
     * 
     */
    public DynamizerPropertyType createDynamizerPropertyType() {
        return new DynamizerPropertyType();
    }

    /**
     * Create an instance of {@link TimeseriesComponentType }
     * 
     */
    public TimeseriesComponentType createTimeseriesComponentType() {
        return new TimeseriesComponentType();
    }

    /**
     * Create an instance of {@link AtomicTimeseriesPropertyType }
     * 
     */
    public AtomicTimeseriesPropertyType createAtomicTimeseriesPropertyType() {
        return new AtomicTimeseriesPropertyType();
    }

    /**
     * Create an instance of {@link TimeseriesComponentPropertyType }
     * 
     */
    public TimeseriesComponentPropertyType createTimeseriesComponentPropertyType() {
        return new TimeseriesComponentPropertyType();
    }

    /**
     * Create an instance of {@link AbstractTimeseriesPropertyType }
     * 
     */
    public AbstractTimeseriesPropertyType createAbstractTimeseriesPropertyType() {
        return new AbstractTimeseriesPropertyType();
    }

    /**
     * Create an instance of {@link CompositeTimeseriesPropertyType }
     * 
     */
    public CompositeTimeseriesPropertyType createCompositeTimeseriesPropertyType() {
        return new CompositeTimeseriesPropertyType();
    }

    /**
     * Create an instance of {@link SensorConnectionPropertyType }
     * 
     */
    public SensorConnectionPropertyType createSensorConnectionPropertyType() {
        return new SensorConnectionPropertyType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeseriesComponentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", name = "TimeseriesComponent", substitutionHeadNamespace = "http://www.opengis.net/gml", substitutionHeadName = "_Feature")
    public JAXBElement<TimeseriesComponentType> createTimeseriesComponent(TimeseriesComponentType value) {
        return new JAXBElement<TimeseriesComponentType>(_TimeseriesComponent_QNAME, TimeseriesComponentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SensorConnectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", name = "SensorConnection", substitutionHeadNamespace = "http://www.opengis.net/gml", substitutionHeadName = "_Object")
    public JAXBElement<SensorConnectionType> createSensorConnection(SensorConnectionType value) {
        return new JAXBElement<SensorConnectionType>(_SensorConnection_QNAME, SensorConnectionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DynamizerPropertyType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", name = "dynamizers", substitutionHeadNamespace = "http://www.opengis.net/citygml/2.0", substitutionHeadName = "_GenericApplicationPropertyOfCityObject")
    public JAXBElement<DynamizerPropertyType> createDynamizers(DynamizerPropertyType value) {
        return new JAXBElement<DynamizerPropertyType>(_Dynamizers_QNAME, DynamizerPropertyType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbstractTimeseriesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", name = "AbstractTimeseries", substitutionHeadNamespace = "http://www.opengis.net/gml", substitutionHeadName = "_Feature")
    public JAXBElement<AbstractTimeseriesType> createAbstractTimeseries(AbstractTimeseriesType value) {
        return new JAXBElement<AbstractTimeseriesType>(_AbstractTimeseries_QNAME, AbstractTimeseriesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DynamizerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", name = "Dynamizer", substitutionHeadNamespace = "http://www.opengis.net/citygml/2.0", substitutionHeadName = "_CityObject")
    public JAXBElement<DynamizerType> createDynamizer(DynamizerType value) {
        return new JAXBElement<DynamizerType>(_Dynamizer_QNAME, DynamizerType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompositeTimeseriesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", name = "CompositeTimeseries", substitutionHeadNamespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", substitutionHeadName = "AbstractTimeseries")
    public JAXBElement<CompositeTimeseriesType> createCompositeTimeseries(CompositeTimeseriesType value) {
        return new JAXBElement<CompositeTimeseriesType>(_CompositeTimeseries_QNAME, CompositeTimeseriesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomicTimeseriesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", name = "AtomicTimeseries", substitutionHeadNamespace = "http://www.citygml.org/ade/dynamizer_ade/1.0", substitutionHeadName = "AbstractTimeseries")
    public JAXBElement<AtomicTimeseriesType> createAtomicTimeseries(AtomicTimeseriesType value) {
        return new JAXBElement<AtomicTimeseriesType>(_AtomicTimeseries_QNAME, AtomicTimeseriesType.class, null, value);
    }

}
