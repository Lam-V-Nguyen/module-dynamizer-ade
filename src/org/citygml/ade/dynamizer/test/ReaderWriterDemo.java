package org.citygml.ade.dynamizer.test;

import java.io.File;
import java.util.ServiceLoader;

import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.ade.binding.ADEContext;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.util.walker.FeatureWalker;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;
import org.citygml4j.xml.io.writer.CityGMLWriter;

public class ReaderWriterDemo {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting TestADE sample program.");

		// create a citygml4j context and register the TestADE context
		CityGMLContext context = new CityGMLContext();

		// load ade contexts
		for (ADEContext adeContext : ServiceLoader.load(ADEContext.class))
			context.registerADEContext(adeContext);
		
		CityGMLBuilder builder = context.createCityGMLBuilder();

		System.out.println("Reading the CityGML ADE dataset");

		CityGMLInputFactory in = builder.createCityGMLInputFactory();
		CityGMLReader reader = in.createCityGMLReader(new File("datasets/CityGML_QEOP_LinkToSensor_Dynamizer.gml"));

		// unmarshal dataset into a CityModel
		CityModel cityModel = (CityModel)reader.nextFeature();
		reader.close();

		// to demonstrate that we have successfully read all ADE content,
		// we use a FeatureWalker to iterate through the hierarchy and
		// simple print every feature it comes across to the console
		// (citygml4j features + ADE features)

		// note that we have to make the ADE context known to the 
		// feature walker. Otherwise the ADE features will not be found.		
		FeatureWalker walker = new FeatureWalker() {
			public void visit(AbstractFeature abstractFeature) {
				System.out.println(abstractFeature + ":" + abstractFeature.getId());
				if (abstractFeature instanceof Dynamizer) {
					Dynamizer dynamizer = (Dynamizer) abstractFeature;
					dynamizer.setAttributeRef("new XPath referencing another generic attribute");
				}
				super.visit(abstractFeature);
			}
		}.useADEContexts(context.getADEContexts());

		System.out.println("The dataset contains the following features:");
		cityModel.accept(walker);

		System.out.println("Writing the TestADE feature hierachy to 'test.gml'...");

		// write CityGML dataset
		CityGMLOutputFactory out = builder.createCityGMLOutputFactory(CityGMLVersion.v2_0_0);
		CityGMLWriter writer = out.createCityGMLWriter(new File("output/test.gml"));

		writer.setIndentString("  ");
		writer.setPrefixes(CityGMLVersion.v2_0_0);
		writer.setPrefixes(context.getADEContexts());
		writer.setSchemaLocations(CityGMLVersion.v2_0_0);

		// we provide the schema location to the CityGML-TestADE.xsd manually here.
		// If the schema would be available on the Internet, the schema location could be
		// hard-coded in the ADEModule of the TestADEContext.
		writer.setSchemaLocation("http://www.citygml.org/ade/TestADE/1.0", "CityGML-DynamizerADE.xsd");

		writer.write(cityModel);
		writer.close();
		System.out.println("Sample program finished.");
	}

}
