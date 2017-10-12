package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEGenericApplicationProperty;
import org.citygml4j.model.module.ade.ADEModule;

public class DynamizersPropertyElement extends ADEGenericApplicationProperty<DynamizerProperty> {

	public DynamizersPropertyElement() {
	}
	
	public DynamizersPropertyElement(DynamizerProperty value) {
		super(value);
	}
	
	@Override
	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new DynamizersPropertyElement(), copyBuilder);
	}
	
	@Override
	public ADEModule getADEModule() {
		return DynamizerADEModule.v1_0;
	}
	
}
