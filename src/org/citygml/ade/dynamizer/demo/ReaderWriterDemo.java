package org.citygml.ade.dynamizer.demo;

import java.io.File;
import java.util.ServiceLoader;

import javax.xml.transform.TransformerException;

import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.GMLTimePosition;
import org.citygml.ade.dynamizer.util.DynamizerUtil;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

public class ReaderWriterDemo {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting program.");

		// create a citygml4j context and register the ADE context
		CityGMLContext context = new CityGMLContext();

		// load ade contexts
		for (ADEContext adeContext : ServiceLoader.load(ADEContext.class))
			context.registerADEContext(adeContext);
		
		CityGMLBuilder builder = context.createCityGMLBuilder();

		System.out.println("Reading the CityGML ADE dataset");

		CityGMLInputFactory in = builder.createCityGMLInputFactory();
		CityGMLReader reader = in.createCityGMLReader(new File("datasets/Dynamizer_AtomicTimeseries_DynamicDataTVP.gml"));

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
				System.out.println(abstractFeature + " --> " + abstractFeature.getId());
				if (abstractFeature instanceof Dynamizer) {
					Dynamizer dynamizer = (Dynamizer) abstractFeature;
					
					// make some changes to the original ADE data
					GMLTimePosition startTime = dynamizer.getStartTime();
					startTime.setValue("2000");
					GMLTimePosition endTime = dynamizer.getEndTime();
					endTime.setValue("2020");
					dynamizer.setAttributeRef("new XPath referencing another generic attribute");
					dynamizer.setStartTime(startTime);
					dynamizer.setEndTime(endTime);
					
					// print out the kvp entries retrieved from the TimeseriesML XML elements
					AbstractTimeseries timeseries = dynamizer.getDynamicData().getTimeseries();
					if (timeseries != null && timeseries instanceof AtomicTimeseries) {
						Object dynamicDataTVP = ((AtomicTimeseries)timeseries).getDynamicDataTVP();
						ElementNSImpl elementNSImpl = (ElementNSImpl)dynamicDataTVP;
						Document document = elementNSImpl.getOwnerDocument();	
						
						try {
							System.out.println(DynamizerUtil.convertXMLDocumentToString(document));
						} catch (TransformerException e) {
							e.printStackTrace();
						}
						
						System.out.println("Timeseries data of the dynamizer '" + dynamizer.getId() + "':");
						NodeList kvps = document.getElementsByTagNameNS("http://www.opengis.net/tsml/1.0", "point");
						for (int i = 0; i < kvps.getLength(); i++) {
							Node meaureTimeNode = document.getElementsByTagNameNS("http://www.opengis.net/tsml/1.0", "time").item(i);
							String measureTime = meaureTimeNode.getFirstChild().getNodeValue();							
							Node measureValueNode = document.getElementsByTagNameNS("http://www.opengis.net/tsml/1.0", "value").item(i);
							String measureValue = measureValueNode.getFirstChild().getNodeValue();
							System.out.println(measureTime + " : " + measureValue);
							
							meaureTimeNode.setTextContent(measureTime + " [visited]");
						}
					}
				}
				super.visit(abstractFeature);
			}
		}.useADEContexts(context.getADEContexts());

		System.out.println("The dataset contains the following features:");
		cityModel.accept(walker);

		System.out.println("Writing the ADE feature hierachy to 'test.gml'...");

		// write CityGML dataset
		CityGMLOutputFactory out = builder.createCityGMLOutputFactory(CityGMLVersion.v2_0_0);
		CityGMLWriter writer = out.createCityGMLWriter(new File("test.gml"));

		writer.setIndentString("  ");
		writer.setPrefixes(CityGMLVersion.v2_0_0);
		writer.setPrefixes(context.getADEContexts());
		writer.setSchemaLocations(CityGMLVersion.v2_0_0);

		// we provide the schema location to the ADE schema manually here.
		// If the schema would be available on the Internet, the schema location could be
		// hard-coded in the ADEModule of the TestADEContext.
		writer.setSchemaLocation("http://www.citygml.org/ade/dynamizer_ade/1.0", "CityGML-DynamizerADE.xsd");

		writer.write(cityModel);
		writer.close();
		System.out.println("Sample program finished.");
	}

}
