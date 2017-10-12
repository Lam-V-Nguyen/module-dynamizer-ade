package org.citygml.ade.dynamizer.model;

import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.ade.ADEModule;

public abstract class AbstractTimeseries extends AbstractFeature implements ADEModelObject{
	
	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		if (target == null)
			throw new IllegalArgumentException("Target argument must not be null for abstract copyable classes.");
		
		AbstractTimeseries copy = (AbstractTimeseries)target;
		super.copyTo(copy, copyBuilder);
		
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
