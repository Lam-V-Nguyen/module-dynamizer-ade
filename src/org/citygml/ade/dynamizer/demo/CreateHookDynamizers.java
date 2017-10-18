package org.citygml.ade.dynamizer.demo;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import org.citygml.ade.dynamizer.DynamizerADEContext;
import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.DynamizerProperty;
import org.citygml.ade.dynamizer.model.DynamizersPropertyElement;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml.ade.dynamizer.model.TimeseriesComponentProperty;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;
import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.builder.copy.ShallowCopyBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.building.Building;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;
import org.citygml4j.xml.io.reader.FeatureReadMode;
import org.citygml4j.xml.io.writer.CityGMLWriter;

public class CreateHookDynamizers {

	public static void main(String[] args) throws Exception {
		CityGMLContext ctx = new CityGMLContext();
		System.out.println("Start generating dataset");
		// register the ADE context into the citygml4j context
		ctx.registerADEContext(new DynamizerADEContext());
		
		CityGMLBuilder citygmlBuilder = ctx.createCityGMLBuilder();
		CityGMLInputFactory citygmlInputFactory = citygmlBuilder.createCityGMLInputFactory();
		citygmlInputFactory.setProperty(CityGMLInputFactory.FEATURE_READ_MODE, FeatureReadMode.SPLIT_PER_COLLECTION_MEMBER);			
		
		CityModel cityModel = new CityModel();
		CopyBuilder copyBuilder = new ShallowCopyBuilder();

		// Read geometry information from another CityGML file
		CityGMLReader citygmlReader = citygmlInputFactory.createCityGMLReader(new File("datasets/AtomicTimeseries_DynamicDataTVP.gml"));
		
		List<Dynamizer> dynamizers = new ArrayList<Dynamizer>();
		while (citygmlReader.hasNext()) {
			CityGML feature = citygmlReader.nextFeature();
			
			if (feature instanceof Dynamizer) {
				Dynamizer dynamizer = (Dynamizer)feature;	
				dynamizers.add(dynamizer);
				AbstractTimeseries timeseries1 = dynamizer.getDynamicData().getTimeseries();
				
				Dynamizer dynamizer2 = (Dynamizer) dynamizer.copy(copyBuilder);
				dynamizer2.setId(dynamizer.getId() + "2");
				dynamizers.add(dynamizer2);
				AbstractTimeseries timeseries2 = dynamizer2.getDynamicData().getTimeseries();
				
				TimeseriesComponent component1 = new TimeseriesComponent();
				component1.setRepetitions(BigInteger.valueOf(10));
				component1.setAdditionalGap(DatatypeFactory.newInstance().newDuration("P5Y2M10D"));
				component1.setTimeseries(new TimeseriesProperty(timeseries1));
				
				TimeseriesComponent component2 = new TimeseriesComponent();
				component2.setRepetitions(BigInteger.valueOf(20));
				component2.setAdditionalGap(DatatypeFactory.newInstance().newDuration("P6Y3M10D"));
				component2.setTimeseries(new TimeseriesProperty(timeseries2));
				
				CompositeTimeseries compositeTimeseries = new CompositeTimeseries();
				compositeTimeseries.getTimeseriesComponent().add(new TimeseriesComponentProperty(component1));
				compositeTimeseries.getTimeseriesComponent().add(new TimeseriesComponentProperty(component2));
				
				dynamizer2.setDynamicData(new TimeseriesProperty(compositeTimeseries));
				
				System.out.println(dynamizer.getId());	
			}
		}
		
		citygmlReader = citygmlInputFactory.createCityGMLReader(new File("datasets/AtomicTimeseries_DynamicDataTVP.gml"));
		while (citygmlReader.hasNext()) {
			CityGML feature = citygmlReader.nextFeature();
			
			if (feature.getCityGMLClass() == CityGMLClass.BUILDING) {
				Building building = (Building)feature;
				Iterator<Dynamizer> iter = dynamizers.iterator();
				while (iter.hasNext()) {
					DynamizersPropertyElement adeProperty = new DynamizersPropertyElement(new DynamizerProperty(iter.next()));
					building.addGenericApplicationPropertyOfBuilding(adeProperty);
				}					
				
				cityModel.addCityObjectMember(new CityObjectMember(building));		
			}
		}

		citygmlReader.close();

		// write CityGML dataset
		citygmlBuilder = ctx.createCityGMLBuilder();
		CityGMLOutputFactory out = citygmlBuilder.createCityGMLOutputFactory(CityGMLVersion.v2_0_0);
		CityGMLWriter writer = out.createCityGMLWriter(new File("test.gml"));

		writer.setIndentString("  ");
		writer.setPrefixes(CityGMLVersion.v2_0_0);
		writer.setPrefixes(ctx.getADEContexts());
		writer.setSchemaLocations(CityGMLVersion.v2_0_0);
		
		// we provide the schema location to the CityGML-TestADE.xsd manually here.
		// If the schema would be available on the Internet, the schema location could be
		// hard-coded in the ADEModule of the TestADEContext.
		writer.setSchemaLocation("http://www.citygml.org/ade/dynamizer_ade/1.0", "CityGML-DynamizerADE.xsd");

		writer.write(cityModel);
		writer.close();
		
		System.out.println("Finished");
	}

}
