package org.citygml.ade.dynamizer.database.schema;

import org.citydb.ade.importer.ADEImporter;
import org.citygml.ade.dynamizer.database.importer.AtomicTimeseriesImporter;
import org.citygml.ade.dynamizer.database.importer.CityObjectPropertiesImporter;
import org.citygml.ade.dynamizer.database.importer.CompositeTimeseriesImporter;
import org.citygml.ade.dynamizer.database.importer.DynamizerImporter;
import org.citygml.ade.dynamizer.database.importer.SensorConnectionImporter;
import org.citygml.ade.dynamizer.database.importer.TimeseriesImporter;

public enum ADETables {
	DYNAMIZER(DynamizerImporter.class),
	TIMESERIES(TimeseriesImporter.class),
	ATOMICTIMESERIES(AtomicTimeseriesImporter.class),
	TIMESERIESCOMPONENT(AtomicTimeseriesImporter.class),
	CITYOBJECT(CityObjectPropertiesImporter.class),
	SENSORCONNECTION(SensorConnectionImporter.class),
	COMPOSITETIMESERIES(CompositeTimeseriesImporter.class);
	
	private Class<? extends ADEImporter> importerClass;
	
	private ADETables(Class<? extends ADEImporter> importerClass) {
		this.importerClass = importerClass;
	}
	
	public Class<? extends ADEImporter> getImporterClass() {
		return importerClass;
	}
	
}
