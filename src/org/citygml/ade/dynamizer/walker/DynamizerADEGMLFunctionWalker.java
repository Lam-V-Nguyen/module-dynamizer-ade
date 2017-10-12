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
import org.citygml4j.util.walker.GMLFunctionWalker;

public class DynamizerADEGMLFunctionWalker<T> implements ADEWalker<GMLFunctionWalker<T>> {
	private GMLFunctionWalker<T> walker;

	@Override
	public void setParentWalker(GMLFunctionWalker<T> walker) {
		this.walker = walker;
	}

	public T apply(Dynamizer dynamizer) {
		T object = walker.apply((AbstractCityObject)dynamizer);
		if (object != null)
			return object;
		
		if (dynamizer.isSetDynamicData()) {
			TimeseriesProperty property = dynamizer.getDynamicData();
			object = walker.apply((FeatureProperty<?>)property);
			if (object != null)
				return object;
		}
		
		return null;
	}
	
	public T apply(AbstractTimeseries abstractTimeseries) {
		return walker.apply((AbstractFeature)abstractTimeseries);
	}
	
	public T apply(TimeseriesComponent timeseriesComponent) {
		T object = walker.apply((AbstractFeature)timeseriesComponent);
		if (object != null)
			return object;
		
		if (timeseriesComponent.isSetTimeseries()) {
			TimeseriesProperty property = timeseriesComponent.getTimeseries();
			object = walker.apply((FeatureProperty<?>)property);
			if (object != null)
				return object;
		}
		
		return null;
	}
	
	public T apply(AtomicTimeseries atomicTimeseries) {
		return apply((AbstractTimeseries)atomicTimeseries);
	}
	
	public T apply(CompositeTimeseries compositeTimeseries) {
		T object = apply((AbstractTimeseries)compositeTimeseries);
		if (object != null)
			return object;
		
		if (compositeTimeseries.isSetTimeseriesComponent()) {
			for (TimeseriesComponentProperty property : new ArrayList<TimeseriesComponentProperty>(compositeTimeseries.getTimeseriesComponent())) {
				object = walker.apply((FeatureProperty<?>)property);
				if (object != null)
					return object;
			}
		}
		
		return null;
	}
	
	public T apply(DynamizersPropertyElement dynamizersPropertyElement) {
		return walker.apply((FeatureProperty<?>)dynamizersPropertyElement.getValue());
	}
}
