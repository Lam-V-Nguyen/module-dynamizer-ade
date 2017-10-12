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
import net.opengis.gml.TimePositionType;


/**
 * <p>Java-Klasse für DynamizerType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DynamizerType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/citygml/2.0}AbstractCityObjectType">
 *       &lt;sequence>
 *         &lt;element name="attributeRef" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="startTime" type="{http://www.opengis.net/gml}TimePositionType"/>
 *         &lt;element name="endTime" type="{http://www.opengis.net/gml}TimePositionType"/>
 *         &lt;element name="dynamicData" type="{http://www.citygml.org/ade/dynamizer_ade/1.0}AbstractTimeseriesPropertyType" minOccurs="0"/>
 *         &lt;element name="linkToSensor" type="{http://www.citygml.org/ade/dynamizer_ade/1.0}SensorConnectionPropertyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DynamizerType", propOrder = {
    "attributeRef",
    "startTime",
    "endTime",
    "dynamicData",
    "linkToSensor"
})
public class DynamizerType
    extends AbstractCityObjectType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String attributeRef;
    @XmlElement(required = true)
    protected TimePositionType startTime;
    @XmlElement(required = true)
    protected TimePositionType endTime;
    protected AbstractTimeseriesPropertyType dynamicData;
    protected SensorConnectionPropertyType linkToSensor;

    /**
     * Ruft den Wert der attributeRef-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeRef() {
        return attributeRef;
    }

    /**
     * Legt den Wert der attributeRef-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeRef(String value) {
        this.attributeRef = value;
    }

    public boolean isSetAttributeRef() {
        return (this.attributeRef!= null);
    }

    /**
     * Ruft den Wert der startTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TimePositionType }
     *     
     */
    public TimePositionType getStartTime() {
        return startTime;
    }

    /**
     * Legt den Wert der startTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePositionType }
     *     
     */
    public void setStartTime(TimePositionType value) {
        this.startTime = value;
    }

    public boolean isSetStartTime() {
        return (this.startTime!= null);
    }

    /**
     * Ruft den Wert der endTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TimePositionType }
     *     
     */
    public TimePositionType getEndTime() {
        return endTime;
    }

    /**
     * Legt den Wert der endTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePositionType }
     *     
     */
    public void setEndTime(TimePositionType value) {
        this.endTime = value;
    }

    public boolean isSetEndTime() {
        return (this.endTime!= null);
    }

    /**
     * Ruft den Wert der dynamicData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AbstractTimeseriesPropertyType }
     *     
     */
    public AbstractTimeseriesPropertyType getDynamicData() {
        return dynamicData;
    }

    /**
     * Legt den Wert der dynamicData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractTimeseriesPropertyType }
     *     
     */
    public void setDynamicData(AbstractTimeseriesPropertyType value) {
        this.dynamicData = value;
    }

    public boolean isSetDynamicData() {
        return (this.dynamicData!= null);
    }

    /**
     * Ruft den Wert der linkToSensor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SensorConnectionPropertyType }
     *     
     */
    public SensorConnectionPropertyType getLinkToSensor() {
        return linkToSensor;
    }

    /**
     * Legt den Wert der linkToSensor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorConnectionPropertyType }
     *     
     */
    public void setLinkToSensor(SensorConnectionPropertyType value) {
        this.linkToSensor = value;
    }

    public boolean isSetLinkToSensor() {
        return (this.linkToSensor!= null);
    }

}
