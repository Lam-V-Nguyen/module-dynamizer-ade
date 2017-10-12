
package org.citygml.ade.dynamizer.model;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.common.base.ModelObject;
import org.citygml4j.model.common.child.Child;
import org.citygml4j.model.common.copy.Copyable;
import org.citygml4j.model.gml.GML;
import org.citygml4j.model.gml.GMLClass;

import net.opengis.gml.TimeIndeterminateValueType;

public class GMLTimePosition implements GML, Child, Copyable {
	private String value;
	private String frame;
	private String calendarEraName;
	private TimeIndeterminateValueType indeterminatePosition; 
	
	private ModelObject parent;
	
	public GMLClass getGMLClass() {
		return GMLClass.TIME;
	}
	
	public String getValue() {
		return value;
	}

	public boolean isSetValue() {
		return value != null;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getFrame() {
		return frame;
	}
	
	public boolean isSetFrame() {
		return frame != null;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}
	
	public String getCalendarEraName() {
		return calendarEraName;
	}
	
	public boolean isSetCalendarEraName() {
		return calendarEraName != null;
	}

	public void setCalendarEraName(String calendarEraName) {
		this.calendarEraName = calendarEraName;
	}
	
	public TimeIndeterminateValueType getIndeterminatePosition() {
		return indeterminatePosition;
	}
	
	public boolean isSetIndeterminatePosition() {
		return indeterminatePosition != null;
	}

	public void setIndeterminatePosition(TimeIndeterminateValueType indeterminatePosition) {
		this.indeterminatePosition = indeterminatePosition;
	}

	public ModelObject getParent() {
		return parent;
	}

	public void setParent(ModelObject parent) {
		this.parent = parent;
	}

	public boolean isSetParent() {
		return parent != null;
	}

	public void unsetParent() {
		parent = null;
	}

	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		GMLTimePosition copy = (target == null) ? new GMLTimePosition() : (GMLTimePosition)target;
		
		if (isSetValue())
			copy.setValue((String)copyBuilder.copy(value));
		
		if (isSetFrame())
			copy.setFrame(copyBuilder.copy(frame));
		
		if (isSetCalendarEraName())
			copy.setCalendarEraName(copyBuilder.copy(calendarEraName));
		
		if (isSetIndeterminatePosition())
			copy.setIndeterminatePosition((TimeIndeterminateValueType)copyBuilder.copy(indeterminatePosition));	
		
		copy.unsetParent();
		
		return copy;
	}

	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new GMLTimePosition(), copyBuilder);
	}
	
}
