package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;
import org.citygml4j.model.module.ade.ADEModule;

public class Dynamizer extends AbstractCityObject implements ADEModelObject {
	private String attributeRef;
	private GMLTimePosition startTime;
	private GMLTimePosition endTime;
	private SensorConnectionProperty linkToSensor;
	private TimeseriesProperty dynamicData;
	
	public String getAttributeRef() {
		return attributeRef;
	}
	
	public boolean isSetAttributeRef() {
		return attributeRef != null;
	}

	public void setAttributeRef(String attributeRef) {
		this.attributeRef = attributeRef;
	}
	
	public GMLTimePosition getStartTime() {
		return startTime;
	}
	
	public boolean isSetStartTime() {
		return startTime != null;
	}

	public void setStartTime(GMLTimePosition startTime) {
		this.startTime = startTime;
	}
	
	public GMLTimePosition getEndTime() {
		return endTime;
	}
	
	public boolean isSetEndTime() {
		return endTime != null;
	}

	public void setEndTime(GMLTimePosition endTime) {
		this.endTime = endTime;
	}
	
	public SensorConnectionProperty getLinkToSensor() {
		return linkToSensor;
	}
	
	public boolean isSetLinkToSensor() {
		return linkToSensor != null;
	}

	public void setLinkToSensor(SensorConnectionProperty linkToSensor) {
		this.linkToSensor = linkToSensor;
	}
	
	public TimeseriesProperty getDynamicData() {
		return dynamicData;
	}
	
	public boolean isSetDynamicData() {
		return dynamicData != null;
	}

	public void setDynamicData(TimeseriesProperty dynamicData) {
		this.dynamicData = dynamicData;
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new Dynamizer(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		Dynamizer copy = (target == null) ? new Dynamizer() : (Dynamizer)target;
		
		if (isSetAttributeRef())
			copy.setAttributeRef(copyBuilder.copy(attributeRef));
		
		if (isSetStartTime())
			copy.setStartTime((GMLTimePosition)copyBuilder.copy(startTime));
		
		if (isSetEndTime())
			copy.setEndTime((GMLTimePosition)copyBuilder.copy(endTime));
		
		if (isSetLinkToSensor())
			copy.setLinkToSensor((SensorConnectionProperty)copyBuilder.copy(linkToSensor));
		
		if (isSetDynamicData())
			copy.setDynamicData((TimeseriesProperty)copyBuilder.copy(dynamicData));
		
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public void accept(FeatureVisitor visitor) {
		visitor.visit((ADEModelObject)this);
	}

	@Override
	public <T> T accept(FeatureFunctor<T> visitor) {
		return visitor.apply((ADEModelObject)this);
	}

	@Override
	public void accept(GMLVisitor visitor) {
		visitor.visit((ADEModelObject)this);
	}

	@Override
	public <T> T accept(GMLFunctor<T> visitor) {
		return visitor.apply((ADEModelObject)this);
	}

	@Override
	public CityGMLClass getCityGMLClass() {
		return CityGMLClass.ADE_COMPONENT;
	}
	
	@Override
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}

}
