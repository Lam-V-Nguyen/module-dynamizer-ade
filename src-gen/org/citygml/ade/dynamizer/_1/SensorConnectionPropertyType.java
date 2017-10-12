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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für SensorConnectionPropertyType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SensorConnectionPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.citygml.org/ade/dynamizer_ade/1.0}SensorConnection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SensorConnectionPropertyType", propOrder = {
    "sensorConnection"
})
public class SensorConnectionPropertyType {

    @XmlElement(name = "SensorConnection", required = true)
    protected SensorConnectionType sensorConnection;

    /**
     * Ruft den Wert der sensorConnection-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SensorConnectionType }
     *     
     */
    public SensorConnectionType getSensorConnection() {
        return sensorConnection;
    }

    /**
     * Legt den Wert der sensorConnection-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorConnectionType }
     *     
     */
    public void setSensorConnection(SensorConnectionType value) {
        this.sensorConnection = value;
    }

    public boolean isSetSensorConnection() {
        return (this.sensorConnection!= null);
    }

}
