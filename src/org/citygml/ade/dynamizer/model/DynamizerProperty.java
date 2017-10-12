package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.model.module.ade.ADEModule;

public class DynamizerProperty extends FeatureProperty<Dynamizer> implements ADEModelObject {

	public DynamizerProperty() {
	}
	
	public DynamizerProperty(Dynamizer dynamizer) {
		super(dynamizer);
	}
	
	public DynamizerProperty(String href) {
		super(href);
	}
	
	public Dynamizer getDynamizer() {
		return super.getObject();
	}

	public boolean isSetDynamizer() {
		return super.isSetObject();
	}

	public void setDynamizer(Dynamizer dynamizer) {
		super.setObject(dynamizer);
	}

	public void unsetDynamizer() {
		super.unsetObject();
	}

	@Override
	public Class<Dynamizer> getAssociableClass() {
		return Dynamizer.class;
	}

	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new DynamizerProperty(), copyBuilder);
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		DynamizerProperty copy = (target == null) ? new DynamizerProperty() : (DynamizerProperty)target;
		return super.copyTo(copy, copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}
}
