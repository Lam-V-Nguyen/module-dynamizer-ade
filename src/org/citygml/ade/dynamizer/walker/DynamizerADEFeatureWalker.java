package org.citygml.ade.dynamizer.walker;

import java.util.ArrayList;

import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.DynamizersPropertyElement;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml.ade.dynamizer.model.TimeseriesComponentProperty;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.util.walker.FeatureWalker;

public class DynamizerADEFeatureWalker implements ADEWalker<FeatureWalker> {
	private FeatureWalker walker;

	@Override
	public void setParentWalker(FeatureWalker walker) {
		this.walker = walker;
	}

	public void visit(Dynamizer dynamizer) {
		walker.visit((AbstractCityObject)dynamizer);
		
		if (dynamizer.isSetDynamicData()) {
			TimeseriesProperty property = dynamizer.getDynamicData();
			walker.visit((FeatureProperty<?>)property);
		}
	}
	
	public void visit(AbstractTimeseries abstractTimeseries) {
		walker.visit((AbstractFeature)abstractTimeseries);
	}
	
	public void visit(TimeseriesComponent timeseriesComponent) {
		walker.visit((AbstractFeature)timeseriesComponent);
		
		if (timeseriesComponent.isSetTimeseries()) {
			TimeseriesProperty property = timeseriesComponent.getTimeseries();
			walker.visit((FeatureProperty<?>)property);
		}
	}
	
	public void visit(AtomicTimeseries atomicTimeseries) {
		visit((AbstractTimeseries)atomicTimeseries);
	}
	
	public void visit(CompositeTimeseries compositeTimeseries) {
		visit((AbstractTimeseries)compositeTimeseries);
		
		if (compositeTimeseries.isSetTimeseriesComponent()) {
			for (TimeseriesComponentProperty property : new ArrayList<TimeseriesComponentProperty>(compositeTimeseries.getTimeseriesComponent()))
				walker.visit((FeatureProperty<?>)property);
		}
	}
	
	public void visit(DynamizersPropertyElement dynamizersPropertyElement) {
		walker.visit((FeatureProperty<?>)dynamizersPropertyElement.getValue());
	}
}
