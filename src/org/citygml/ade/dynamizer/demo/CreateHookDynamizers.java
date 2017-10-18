package org.citygml.ade.dynamizer.demo;

import java.io.File;
import org.citygml.ade.dynamizer.DynamizerADEContext;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.DynamizerProperty;
import org.citygml.ade.dynamizer.model.DynamizersPropertyElement;
import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
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

		// Read geometry information from another CityGML file
		CityGMLReader citygmlReader = citygmlInputFactory.createCityGMLReader(new File("datasets/AtomicTimeseries_DynamicDataTVP.gml"));
		
		Dynamizer dynamizer = new Dynamizer();
		while (citygmlReader.hasNext()) {
			CityGML feature = citygmlReader.nextFeature();
			
			if (feature instanceof Dynamizer) {
				dynamizer = (Dynamizer)feature;	
				System.out.println(dynamizer.getId());
				cityModel.addCityObjectMember(new CityObjectMember(dynamizer));	
			}
		}
		
		citygmlReader = citygmlInputFactory.createCityGMLReader(new File("datasets/AtomicTimeseries_DynamicDataTVP.gml"));
		while (citygmlReader.hasNext()) {
			CityGML feature = citygmlReader.nextFeature();
			
			if (feature.getCityGMLClass() == CityGMLClass.BUILDING) {
				Building building = (Building)feature;	
				DynamizersPropertyElement adeProperty = new DynamizersPropertyElement(new DynamizerProperty(dynamizer.getId()));
				building.addGenericApplicationPropertyOfBuilding(adeProperty);
				cityModel.addCityObjectMember(new CityObjectMember(building));	
				cityModel.addCityObjectMember(new CityObjectMember(building.co));	
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
