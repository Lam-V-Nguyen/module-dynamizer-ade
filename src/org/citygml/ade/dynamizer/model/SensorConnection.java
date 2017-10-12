package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.common.association.Associable;
import org.citygml4j.model.common.base.ModelObject;
import org.citygml4j.model.module.ade.ADEModule;

public class SensorConnection implements Associable, ADEModelObject {

	private String sensorId;
	private String serviceType;
	private String linkToObservation;
	private String linkToSensorML;
	private AbstractCityObject sensorLocation;
	private ModelObject parent;

	public String getSensorId() {
		return sensorId;
	}
	
	public boolean isSetSensorId() {
		return sensorId != null;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	
	public boolean isSetServiceType() {
		return serviceType != null;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	public String getLinkToObservation() {
		return linkToObservation;
	}
	
	public boolean isSetLinkToObservation() {
		return linkToObservation != null;
	}

	public void setLinkToObservation(String linkToObservation) {
		this.linkToObservation = linkToObservation;
	}
	
	public String getLinkToSensorML() {
		return linkToSensorML;
	}
	
	public boolean isSetLinkToSensorML() {
		return linkToSensorML != null;
	}

	public void setLinkToSensorML(String linkToSensorML) {
		this.linkToSensorML = linkToSensorML;
	}
	
	public AbstractCityObject getSensorLocation() {
		return sensorLocation;
	}
	
	public boolean isSetSensorLocation() {
		return sensorLocation != null;
	}

	public void setSensorLocation(AbstractCityObject sensorLocation) {
		this.sensorLocation = sensorLocation;
	}

	@Override
	public ModelObject getParent() {
		return parent;
	}

	@Override
	public void setParent(ModelObject parent) {
		this.parent = parent;
	}

	@Override
	public boolean isSetParent() {
		return parent != null;
	}

	@Override
	public void unsetParent() {
		parent = null;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new SensorConnection(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		SensorConnection copy = (target == null) ? new SensorConnection() : (SensorConnection)target;
	
		if (isSetSensorId())
			copy.setSensorId(copyBuilder.copy(sensorId));
		
		if (isSetServiceType())
			copy.setServiceType(copyBuilder.copy(serviceType));
		
		if (isSetLinkToObservation())
			copy.setLinkToObservation(copyBuilder.copy(linkToObservation));
		
		if (isSetLinkToSensorML())
			copy.setLinkToSensorML(copyBuilder.copy(linkToSensorML));
		
		if (isSetSensorLocation())
			copy.setSensorLocation((AbstractCityObject)copyBuilder.copy(sensorLocation));
		
		copy.unsetParent();
		return copy;
	}
	
	@Override
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}

}
