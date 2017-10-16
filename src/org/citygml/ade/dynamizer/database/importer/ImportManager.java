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
import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.core.AbstractCityObject;
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
		if (object instanceof Dynamizer)
			getImporter(DynamizerImporter.class).doImport((Dynamizer)object, objectId, objectType, foreignKeys);
		else if (object instanceof AbstractTimeseries)
			getImporter(TimeseriesImporter.class).doImport((AbstractTimeseries)object, objectId, objectType, foreignKeys);
		else if (object instanceof AtomicTimeseries)
			getImporter(AtomicTimeseriesImporter.class).doImport((AtomicTimeseries)object, objectId, objectType);
		else if (object instanceof CompositeTimeseries)
			getImporter(CompositeTimeseriesImporter.class).doImport((CompositeTimeseries)object, objectId, objectType, foreignKeys);
		else if (object instanceof TimeseriesComponent)
			getImporter(TimeseriesComponentImporter.class).doImport((TimeseriesComponent)object, objectId, objectType, foreignKeys);
	}

	@Override
	public void importGenericApplicationProperties(ADEPropertyCollection properties, AbstractFeature parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
		if (parent instanceof AbstractCityObject)
			getImporter(CityObjectPropertiesImporter.class).doImport(properties, (AbstractCityObject)parent, parentId, parentType);
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
		
		if (importer == null) {
			if (type == DynamizerImporter.class)
				importer = new DynamizerImporter(connection, helper, this);
			else if (type == TimeseriesImporter.class)
				importer = new TimeseriesImporter(connection, helper, this);
			else if (type == AtomicTimeseriesImporter.class)
				importer = new AtomicTimeseriesImporter(connection, helper, this);
			else if (type == CityObjectPropertiesImporter.class)
				importer = new CityObjectPropertiesImporter(connection, helper, this);
			else if (type == CompositeTimeseriesImporter.class)
				importer = new CompositeTimeseriesImporter(connection, helper, this);
			else if (type == SensorConnectionImporter.class)
				importer = new SensorConnectionImporter(connection, helper, this);
			else if (type == TimeseriesComponentImporter.class)
				importer = new TimeseriesComponentImporter(connection, helper, this);
			
			if (importer == null)
				throw new SQLException("Failed to build ADE importer of type " + type.getName() + ".");
			
			importers.put(type, importer);
		}
		
		return type.cast(importer);
	}

}
