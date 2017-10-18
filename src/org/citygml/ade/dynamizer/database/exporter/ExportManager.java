package org.citygml.ade.dynamizer.database.exporter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.citydb.ade.exporter.ADEExportManager;
import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.database.schema.mapping.FeatureType;
import org.citydb.feature.filter.projection.ProjectionFilter;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.ObjectMapper;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.gml.feature.AbstractFeature;

public class ExportManager implements ADEExportManager {
	private final Map<Class<? extends ADEExporter>, ADEExporter> exporters;
	private final SchemaMapper schemaMapper;
	private final ObjectMapper objectMapper;
	
	private Connection connection;
	private CityGMLExportHelper helper;
	
	public ExportManager(SchemaMapper schemaMapper, ObjectMapper objectMapper) {
		this.schemaMapper = schemaMapper;
		this.objectMapper = objectMapper;
		exporters = new HashMap<>();
	}
	
	@Override
	public void init(Connection connection, CityGMLExportHelper helper) throws CityGMLExportException, SQLException {
		this.connection = connection;
		this.helper = helper;
	}

	@Override
	public void exportObject(ADEModelObject object, long objectId, AbstractObjectType<?> objectType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		if (object instanceof Dynamizer)
			getExporter(DynamizerExporter.class).doExport((Dynamizer)object, objectId, objectType);
	}
	
	@Override
	public void exportGenericApplicationProperties(String adeHookTable, AbstractFeature parent, long parentId, FeatureType parentType, ProjectionFilter projectionFilter) throws CityGMLExportException, SQLException {
		if (adeHookTable.equals(schemaMapper.getTableName(ADETables.CITYOBJECT)) && parent instanceof AbstractCityObject) {
			CityObjectPropertiesExporter exporter = getExporter(CityObjectPropertiesExporter.class);
			exporter.doExport((AbstractCityObject)parent, parentId, parentType);
		}			
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		for (ADEExporter exporter : exporters.values())
			exporter.close();
	}
	
	protected SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}
	
	protected ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	protected <T extends ADEExporter> T getExporter(Class<T> type) throws CityGMLExportException, SQLException {
		ADEExporter exporter = exporters.get(type);

		if (exporter == null) {
			if (type == DynamizerExporter.class)
				exporter = new DynamizerExporter(connection, helper, this);
			else if (type == TimeseriesExporter.class)
				exporter = new TimeseriesExporter(connection, helper, this);
			else if (type == SensorConnectionExporter.class)
				exporter = new SensorConnectionExporter(connection, helper, this);
			else if (type == TimeseriesComponentExporter.class)
				exporter = new TimeseriesComponentExporter(connection, helper, this);
			else if (type == CityObjectPropertiesExporter.class)
				exporter = new CityObjectPropertiesExporter(connection, helper, this);

			if (exporter == null)
				throw new SQLException("Failed to build ADE exporter of type " + type.getName() + ".");
			
			exporters.put(type, exporter);
		}
		
		return type.cast(exporter);
	}

}
