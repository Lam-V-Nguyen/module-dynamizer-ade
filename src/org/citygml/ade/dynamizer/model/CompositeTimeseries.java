package org.citygml.ade.dynamizer.model;

import java.util.List;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.common.child.ChildList;

public class CompositeTimeseries extends AbstractTimeseries {
	
	private List<TimeseriesComponentProperty> component;

	public void addTimeseriesComponent(TimeseriesComponentProperty component) {
		if (this.component == null)
			this.component = new ChildList<TimeseriesComponentProperty>(this);

		this.component.add(component);
	}
	
	public List<TimeseriesComponentProperty> getTimeseriesComponent() {
		if (component == null)
			component = new ChildList<TimeseriesComponentProperty>(this);

		return component;
	}
	
	public boolean isSetTimeseriesComponent() {
		return component != null && !component.isEmpty();
	}

	public void setTimeseriesComponent(List<TimeseriesComponentProperty> component) {
		this.component = new ChildList<TimeseriesComponentProperty>(this, component);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new CompositeTimeseries(), copyBuilder);
	}
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		CompositeTimeseries copy = (target == null) ? new CompositeTimeseries() : (CompositeTimeseries)target;
		super.copyTo(copy, copyBuilder);
		
		if (isSetTimeseriesComponent()) {
			for (TimeseriesComponentProperty part : component) {
				TimeseriesComponentProperty copyPart = (TimeseriesComponentProperty)copyBuilder.copy(part);
				copy.addTimeseriesComponent(copyPart);
				
				if (part != null && copyPart == part)
					part.setParent(this);
			}
		}
		
		return copy;
	}
	
}
