//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.4.3+1
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Wed Oct 11 16:43:16 CEST 2017
//


package org.citygml.ade.dynamizer._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import net.opengis.citygml._2.AbstractCityObjectType;


/**
 * <p>Java-Klasse für SensorConnectionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SensorConnectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sensorID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="linkToObservation" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="linkToSensorML" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="sensorLocation" type="{http://www.opengis.net/citygml/2.0}AbstractCityObjectType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SensorConnectionType", propOrder = {
    "sensorID",
    "serviceType",
    "linkToObservation",
    "linkToSensorML",
    "sensorLocation"
})
public class SensorConnectionType {

    @XmlElement(required = true)
    protected String sensorID;
    @XmlElement(required = true)
    protected String serviceType;
    @XmlSchemaType(name = "anyURI")
    protected String linkToObservation;
    @XmlSchemaType(name = "anyURI")
    protected String linkToSensorML;
    protected AbstractCityObjectType sensorLocation;

    /**
     * Ruft den Wert der sensorID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSensorID() {
        return sensorID;
    }

    /**
     * Legt den Wert der sensorID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSensorID(String value) {
        this.sensorID = value;
    }

    public boolean isSetSensorID() {
        return (this.sensorID!= null);
    }

    /**
     * Ruft den Wert der serviceType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Legt den Wert der serviceType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    public boolean isSetServiceType() {
        return (this.serviceType!= null);
    }

    /**
     * Ruft den Wert der linkToObservation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToObservation() {
        return linkToObservation;
    }

    /**
     * Legt den Wert der linkToObservation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToObservation(String value) {
        this.linkToObservation = value;
    }

    public boolean isSetLinkToObservation() {
        return (this.linkToObservation!= null);
    }

    /**
     * Ruft den Wert der linkToSensorML-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkToSensorML() {
        return linkToSensorML;
    }

    /**
     * Legt den Wert der linkToSensorML-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkToSensorML(String value) {
        this.linkToSensorML = value;
    }

    public boolean isSetLinkToSensorML() {
        return (this.linkToSensorML!= null);
    }

    /**
     * Ruft den Wert der sensorLocation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AbstractCityObjectType }
     *     
     */
    public AbstractCityObjectType getSensorLocation() {
        return sensorLocation;
    }

    /**
     * Legt den Wert der sensorLocation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractCityObjectType }
     *     
     */
    public void setSensorLocation(AbstractCityObjectType value) {
        this.sensorLocation = value;
    }

    public boolean isSetSensorLocation() {
        return (this.sensorLocation!= null);
    }

}
