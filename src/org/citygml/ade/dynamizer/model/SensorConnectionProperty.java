package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.base.AssociationByRep;
import org.citygml4j.model.module.ade.ADEModule;

public class SensorConnectionProperty extends AssociationByRep<SensorConnection> implements ADEModelObject {

	public SensorConnectionProperty() {
	}
	
	public SensorConnectionProperty(SensorConnection sensorConnection) {
		super(sensorConnection);
	}
	
	public SensorConnection getSensorConnection() {
		return super.getObject();
	}

	public boolean isSetSensorConnection() {
		return super.isSetObject();
	}

	public void setSensorConnection(SensorConnection sensorConnection) {
		super.setObject(sensorConnection);
	}

	public void unsetSensorConnection() {
		super.unsetObject();
	}
	
	@Override
	public Class<SensorConnection> getAssociableClass() {
		return SensorConnection.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new SensorConnectionProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		SensorConnectionProperty copy = (target == null) ? new SensorConnectionProperty() : (SensorConnectionProperty)target;
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}

}
