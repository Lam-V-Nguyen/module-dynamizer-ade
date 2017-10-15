package org.citygml.ade.dynamizer.database.schema;

import org.citydb.ade.importer.ADEImporter;
import org.citygml.ade.dynamizer.database.importer.DynamizerImporter;

public enum ADETables {
	DYNAMIZER(DynamizerImporter.class);
	
	private Class<? extends ADEImporter> importerClass;
	
	private ADETables(Class<? extends ADEImporter> importerClass) {
		this.importerClass = importerClass;
	}
	
	public Class<? extends ADEImporter> getImporterClass() {
		return importerClass;
	}
	
}
