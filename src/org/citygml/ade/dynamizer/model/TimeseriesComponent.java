package org.citygml.ade.dynamizer.model;

import java.math.BigInteger;

import javax.xml.datatype.Duration;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.ade.ADEModule;

public class TimeseriesComponent extends AbstractFeature implements ADEModelObject {
	
	private BigInteger repetitions;
	private Duration additionalGap;
	private TimeseriesProperty timeseries;

	public BigInteger getRepetitions() {
		return repetitions;
	}
	
	public boolean isSetRepetitions() {
		return repetitions != null;
	}
	
	public void setRepetitions(BigInteger repetitions) {
		this.repetitions = repetitions;
	}
	
	public Duration getAdditionalGap() {
		return additionalGap;
	}
	
	public boolean isSetAdditionalGap() {
		return additionalGap != null;
	}
	
	public void setAdditionalGap(Duration additionalGap) {
		this.additionalGap = additionalGap;
	}
	
	public TimeseriesProperty getTimeseries() {
		return timeseries;
	}
	
	public boolean isSetTimeseries() {
		return timeseries != null;
	}

	public void setTimeseries(TimeseriesProperty timeseries) {
		this.timeseries = timeseries;
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new TimeseriesComponent(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		TimeseriesComponent copy = (target == null) ? new TimeseriesComponent() : (TimeseriesComponent)target;
		super.copyTo(copy, copyBuilder);
		 
		if (isSetRepetitions())
			copy.setRepetitions((BigInteger)copyBuilder.copy(repetitions));
		
		if (isSetAdditionalGap())
			copy.setAdditionalGap((Duration)copyBuilder.copy(additionalGap));
		
		if (isSetTimeseries()) 
			copy.setTimeseries((TimeseriesProperty)copyBuilder.copy(timeseries));
		
		return copy;
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
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}
	
}
