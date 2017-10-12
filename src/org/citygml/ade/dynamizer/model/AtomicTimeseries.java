package org.citygml.ade.dynamizer.model;

import org.citygml4j.builder.copy.CopyBuilder;

public class AtomicTimeseries extends AbstractTimeseries {
	
	private Object dynamicDataTVP;
	private Object dynamicDataDR;
	private Object observationData;

	public Object getDynamicDataTVP() {
		return dynamicDataTVP;
	}
	
	public boolean isSetDynamicDataTVP() {
		return dynamicDataTVP != null;
	}
	
	public void setDynamicDataTVP(Object dynamicDataTVP) {
		this.dynamicDataTVP = dynamicDataTVP;
	}
	
	public Object getDynamicDataDR() {
		return dynamicDataDR;
	}
	
	public boolean isSetDynamicDataDR() {
		return dynamicDataDR != null;
	}
	
	public void setDynamicDataDR(Object dynamicDataDR) {
		this.dynamicDataDR = dynamicDataDR;
	}
	
	public Object getObservationData() {
		return observationData;
	}
	
	public boolean isSetObservationData() {
		return observationData != null;
	}
	
	public void setObservationData(Object observationData) {
		this.observationData = observationData;
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new AtomicTimeseries(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		AtomicTimeseries copy = (target == null) ? new AtomicTimeseries() : (AtomicTimeseries)target;
		super.copyTo(copy, copyBuilder);
		
		if (isSetDynamicDataTVP())
			copy.setDynamicDataTVP(copyBuilder.copy(dynamicDataTVP));
		
		if (isSetDynamicDataDR())
			copy.setDynamicDataDR(copyBuilder.copy(dynamicDataDR));
		
		if (isSetObservationData())
			copy.setObservationData(copyBuilder.copy(observationData));
		
		return copy;
	}
	
}
