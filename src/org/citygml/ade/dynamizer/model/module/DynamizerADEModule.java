package org.citygml.ade.dynamizer.model.module;


import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.namespace.QName;

import org.citygml.ade.dynamizer.DynamizerADEContext;
import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.ade.ADEModule;
import org.citygml4j.model.module.citygml.CityGMLVersion;

public class DynamizerADEModule extends ADEModule {
	public static final DynamizerADEModule v1_0 = new DynamizerADEModule();
	
	private HashMap<String, Class<? extends AbstractFeature>> features;
	private HashSet<String> featureProperties;

	private DynamizerADEModule() {
		super("http://www.citygml.org/ade/dynamizer_ade/1.0", 
				"Dynmaizer", 
				CityGMLVersion.v2_0_0);

		features = new HashMap<>();
		features.put("Dynamizer", Dynamizer.class);
		features.put("AbstractTimeseries", AbstractTimeseries.class);
		features.put("AtomicTimeseriesType", AtomicTimeseries.class);
		features.put("CompositeTimeseries", CompositeTimeseries.class);
		features.put("TimeseriesComponent", TimeseriesComponent.class);

		featureProperties = new HashSet<>();
		featureProperties.add("sensorLocation");
		featureProperties.add("linkToSensor");
		featureProperties.add("dynamicData");
		featureProperties.add("component");
		featureProperties.add("timeseries");
		featureProperties.add("dynamizers");
	}

	@Override
	public URL getSchemaResource() {
		return DynamizerADEContext.class.getResource("/org/citygml/ade/testade/bind/schema/CityGML-DynamizerADE-v1.xsd");
	}

	@Override
	public boolean hasFeatureProperty(String name) {
		return featureProperties.contains(name);
	}

	@Override
	public boolean hasFeature(String name) {
		return features.containsKey(name);
	}

	@Override
	public Class<? extends AbstractFeature> getFeatureClass(String name) {
		return features.get(name);
	}

	@Override
	public QName getFeatureName(Class<? extends AbstractFeature> featureClass) {
		Iterator<Entry<String, Class<? extends AbstractFeature>>> iter = features.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Class<? extends AbstractFeature>> entry = iter.next();
			if (entry.getValue() == featureClass)
				return new QName(getNamespaceURI(), entry.getKey());
		}

		return null;
	}

}
