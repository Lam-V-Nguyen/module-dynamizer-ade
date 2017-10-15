package org.citygml.ade.dynamizer.database.importer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.citydb.ade.ADEExtension;
import org.citydb.ade.importer.ADEImportManager;
import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.ADEPropertyCollection;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.database.schema.mapping.FeatureType;
import org.citydb.modules.citygml.importer.CityGMLImportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.gml.feature.AbstractFeature;

public class ImportManager implements ADEImportManager {
	private final ADEExtension adeExtension;
	private final Map<Class<? extends ADEImporter>, ADEImporter> importers;
	private final SchemaMapper schemaMapper;
	
	private Connection connection;
	private CityGMLImportHelper helper;
	
	public ImportManager(ADEExtension adeExtension, SchemaMapper schemaMapper) {
		this.adeExtension = adeExtension;
		this.schemaMapper = schemaMapper;
		importers = new HashMap<>();
	}
	
	@Override
	public void init(Connection connection, CityGMLImportHelper helper) throws CityGMLImportException, SQLException {
		this.connection = connection;
		this.helper = helper;
	}

	@Override
	public void importObject(ADEModelObject object, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
		
	}

	@Override
	public void importGenericApplicationProperties(ADEPropertyCollection properties, AbstractFeature parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
		//TODO
	}

	@Override
	public void executeBatch(String tableName) throws CityGMLImportException, SQLException {
		ADETables adeTable = schemaMapper.fromTableName(tableName);
		if (adeTable != null) {
			ADEImporter importer = importers.get(adeTable.getImporterClass());
			if (importer != null)
				importer.executeBatch();
		} else
			throw new CityGMLImportException("The table " + tableName + " not managed by the ADE extension for '" + adeExtension.getMetadata().getIdentifier() + "'.");
	}

	@Override
	public void close() throws CityGMLImportException, SQLException {
		for (ADEImporter importer : importers.values())
			importer.close();
	}
	
	protected SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}
	
	protected <T extends ADEImporter> T getImporter(Class<T> type) throws CityGMLImportException, SQLException {
		ADEImporter importer = importers.get(type);
		
		//TODO
		
		return type.cast(importer);
	}

}
