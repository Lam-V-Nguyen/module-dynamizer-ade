package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.model.module.ade.ADEModule;

public class TimeseriesProperty extends FeatureProperty<AbstractTimeseries> implements ADEModelObject {

	public TimeseriesProperty() {
	}
	
	public TimeseriesProperty(AbstractTimeseries abstractTimeseries) {
		super(abstractTimeseries);
	}
	
	public TimeseriesProperty(String href) {
		super(href);
	}
	
	public AbstractTimeseries getTimeseries() {
		return super.getObject();
	}

	public boolean isSetTimeseries() {
		return super.isSetObject();
	}

	public void setTimeseries(AbstractTimeseries timeseries) {
		super.setObject(timeseries);
	}

	public void unsetTimeseries() {
		super.unsetObject();
	}

	@Override
	public Class<AbstractTimeseries> getAssociableClass() {
		return AbstractTimeseries.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new TimeseriesProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		TimeseriesProperty copy = (target == null) ? new TimeseriesProperty() : (TimeseriesProperty)target;
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}

}
