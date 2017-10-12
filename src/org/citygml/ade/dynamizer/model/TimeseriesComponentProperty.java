package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.model.module.ade.ADEModule;

public class TimeseriesComponentProperty extends FeatureProperty<TimeseriesComponent> implements ADEModelObject {

	public TimeseriesComponentProperty() {
	}
	
	public TimeseriesComponentProperty(TimeseriesComponent timeseriesComponent) {
		super(timeseriesComponent);
	}
	
	public TimeseriesComponentProperty(String href) {
		super(href);
	}
	
	public TimeseriesComponent getTimeseriesComponent() {
		return super.getObject();
	}

	public boolean isSetTimeseriesComponent() {
		return super.isSetObject();
	}

	public void setTimeseriesComponent(TimeseriesComponent timeseriesComponent) {
		super.setObject(timeseriesComponent);
	}

	public void unsetTimeseriesComponent() {
		super.unsetObject();
	}

	@Override
	public Class<TimeseriesComponent> getAssociableClass() {
		return TimeseriesComponent.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new TimeseriesComponentProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		TimeseriesComponentProperty copy = (target == null) ? new TimeseriesComponentProperty() : (TimeseriesComponentProperty)target;
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}

}
