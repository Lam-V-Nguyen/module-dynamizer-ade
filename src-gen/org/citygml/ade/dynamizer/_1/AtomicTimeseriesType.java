//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.4.3+1
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Wed Oct 11 16:43:16 CEST 2017
//


package org.citygml.ade.dynamizer._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für AtomicTimeseriesType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="AtomicTimeseriesType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.citygml.org/ade/dynamizer_ade/1.0}AbstractTimeseriesType">
 *       &lt;sequence>
 *         &lt;element name="dynamicDataTVP" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="dynamicDataDR" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="observationData" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtomicTimeseriesType", propOrder = {
    "dynamicDataTVP",
    "dynamicDataDR",
    "observationData"
})
public class AtomicTimeseriesType
    extends AbstractTimeseriesType
{

    protected Object dynamicDataTVP;
    protected Object dynamicDataDR;
    protected Object observationData;

    /**
     * Ruft den Wert der dynamicDataTVP-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDynamicDataTVP() {
        return dynamicDataTVP;
    }

    /**
     * Legt den Wert der dynamicDataTVP-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDynamicDataTVP(Object value) {
        this.dynamicDataTVP = value;
    }

    public boolean isSetDynamicDataTVP() {
        return (this.dynamicDataTVP!= null);
    }

    /**
     * Ruft den Wert der dynamicDataDR-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDynamicDataDR() {
        return dynamicDataDR;
    }

    /**
     * Legt den Wert der dynamicDataDR-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDynamicDataDR(Object value) {
        this.dynamicDataDR = value;
    }

    public boolean isSetDynamicDataDR() {
        return (this.dynamicDataDR!= null);
    }

    /**
     * Ruft den Wert der observationData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getObservationData() {
        return observationData;
    }

    /**
     * Legt den Wert der observationData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setObservationData(Object value) {
        this.observationData = value;
    }

    public boolean isSetObservationData() {
        return (this.observationData!= null);
    }

}
