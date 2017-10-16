package org.citygml.ade.dynamizer.database.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.modules.citygml.importer.CityGMLImportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;

public class TimeseriesComponentImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private PreparedStatement ps;
	private int batchCounter;

	public TimeseriesComponentImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.TIMESERIESCOMPONENT))).append(" ")
				.append("(id, CompositeTimese_component_ID, timeseries_ID, repetitions, additionalGap) ")
				.append("values (?, ?, ?, ?, ?)");
		ps = connection.prepareStatement(stmt.toString());
	}
	
	public void doImport(TimeseriesComponent timeseriesComponent, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
		long parentId = foreignKeys.get("parentId");		
		
		// insert primary key
		ps.setLong(1, objectId);
		
		// insert the parent CompositeTimeseires object ID
		if (parentId != 0)
			ps.setLong(2, parentId);
		else
			ps.setNull(2, Types.NULL);
		
		// insert the associated timeseries object ID
		long timeseriesId = 0;
		TimeseriesProperty timeSeriesProperty = timeseriesComponent.getTimeseries();
		if (timeSeriesProperty != null) {
			if (timeSeriesProperty.isSetTimeseries()) {
				timeseriesId = helper.importObject(timeSeriesProperty.getTimeseries(), ForeignKeys.create().with("parentId", objectId));
				timeSeriesProperty.unsetTimeseries();
			}
		}
		if (timeseriesId != 0)
			ps.setLong(3, timeseriesId);
		else
			ps.setNull(3, Types.NULL);
		
		// insert repetitions
		if (timeseriesComponent.isSetRepetitions())
			ps.setLong(4, timeseriesComponent.getRepetitions().longValue());
		else
			ps.setNull(4, Types.NULL);
		
		// insert additionalGap
		if (timeseriesComponent.isSetAdditionalGap())
			ps.setString(5, timeseriesComponent.getAdditionalGap().toString());
		else
			ps.setNull(5, Types.NULL);

		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(objectType);
	}

	@Override
	public void executeBatch() throws CityGMLImportException, SQLException {
		if (batchCounter > 0) {
			ps.executeBatch();
			batchCounter = 0;
		}
	}

	@Override
	public void close() throws CityGMLImportException, SQLException {
		ps.close();
	}

}
