//
// Generated with ade-xjc - XML Schema binding compiler for CityGML ADEs, version 2.4.3+1
// ade-xjc is part of the citygml4j project, see https://github.com/citygml4j
// Any modifications to this file will be lost upon recompilation of the source
// Generated: Wed Oct 11 16:43:16 CEST 2017
//


package org.citygml.ade.dynamizer._1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import net.opengis.gml.AbstractFeatureType;


/**
 * <p>Java-Klasse für TimeseriesComponentType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TimeseriesComponentType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element name="repetitions" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="additionalGap" type="{http://www.w3.org/2001/XMLSchema}duration" minOccurs="0"/>
 *         &lt;element name="timeseries" type="{http://www.citygml.org/ade/dynamizer_ade/1.0}AbstractTimeseriesPropertyType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeseriesComponentType", propOrder = {
    "repetitions",
    "additionalGap",
    "timeseries"
})
public class TimeseriesComponentType
    extends AbstractFeatureType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger repetitions;
    protected Duration additionalGap;
    @XmlElement(required = true)
    protected AbstractTimeseriesPropertyType timeseries;

    /**
     * Ruft den Wert der repetitions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRepetitions() {
        return repetitions;
    }

    /**
     * Legt den Wert der repetitions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRepetitions(BigInteger value) {
        this.repetitions = value;
    }

    public boolean isSetRepetitions() {
        return (this.repetitions!= null);
    }

    /**
     * Ruft den Wert der additionalGap-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getAdditionalGap() {
        return additionalGap;
    }

    /**
     * Legt den Wert der additionalGap-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setAdditionalGap(Duration value) {
        this.additionalGap = value;
    }

    public boolean isSetAdditionalGap() {
        return (this.additionalGap!= null);
    }

    /**
     * Ruft den Wert der timeseries-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AbstractTimeseriesPropertyType }
     *     
     */
    public AbstractTimeseriesPropertyType getTimeseries() {
        return timeseries;
    }

    /**
     * Legt den Wert der timeseries-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AbstractTimeseriesPropertyType }
     *     
     */
    public void setTimeseries(AbstractTimeseriesPropertyType value) {
        this.timeseries = value;
    }

    public boolean isSetTimeseries() {
        return (this.timeseries!= null);
    }

}
