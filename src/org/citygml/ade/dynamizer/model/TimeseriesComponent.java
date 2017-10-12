package org.citygml.ade.dynamizer.model;

import java.math.BigInteger;

import javax.xml.datatype.Duration;

import org.citygml4j.builder.copy.CopyBuilder;

public class TimeseriesComponent extends AbstractTimeseries {
	
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
	
}
