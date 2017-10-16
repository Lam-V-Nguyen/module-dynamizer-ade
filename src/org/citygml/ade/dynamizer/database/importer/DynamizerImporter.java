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
import org.citygml.ade.dynamizer.database.schema.ADESequences;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.GMLTimePosition;
import org.citygml.ade.dynamizer.model.SensorConnection;
import org.citygml.ade.dynamizer.model.SensorConnectionProperty;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;

public class DynamizerImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private SensorConnectionImporter sensorConnectionImporter;

	private PreparedStatement ps;
	private int batchCounter;

	public DynamizerImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.DYNAMIZER))).append(" ")
				.append("(id, cityobject_dynamizers_ID, dynamicData_ID, linkToSensor_ID, ")
				.append("attributeRef, startTime, startTime_frame, startTime_calendarEraName, startTime_indeterminatePosit, ")
				.append("endTime, endTime_frame, endTime_calendarEraName, endTime_indeterminatePositio) ")
				.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		ps = connection.prepareStatement(stmt.toString());

		sensorConnectionImporter = manager.getImporter(SensorConnectionImporter.class);
	}
	
	public void doImport(Dynamizer dynamizer, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
		long parentId = foreignKeys.get("parentId");
		
		ps.setLong(1, objectId);

		if (parentId != 0)
			ps.setLong(2,  parentId);
		else
			ps.setNull(2, Types.NULL);
		
		if (dynamizer.isSetAttributeRef())
			ps.setString(3, dynamizer.getAttributeRef());
		else
			ps.setNull(3, Types.VARCHAR);

		long timeseriesId = 0;
		TimeseriesProperty timeSeriesProperty = dynamizer.getDynamicData();
		if (timeSeriesProperty != null) {
			AbstractTimeseries timeseries = timeSeriesProperty.getTimeseries();
			if (timeseries != null) {
				timeseriesId = helper.importObject(timeseries);
				timeSeriesProperty.unsetTimeseries();
			} else {
				String href = timeSeriesProperty.getHref();
				if (href != null && href.length() != 0)
					helper.logOrThrowUnsupportedXLinkMessage(dynamizer, AbstractTimeseries.class, href);
			}
		}
		if (timeseriesId != 0)
			ps.setLong(4, timeseriesId);
		else
			ps.setNull(4, Types.NULL);
		
		long linkToSensorId = 0;
		SensorConnectionProperty sensorConnectionProperty = dynamizer.getLinkToSensor();
		if (sensorConnectionProperty != null) {
			SensorConnection sensorConnection = sensorConnectionProperty.getSensorConnection();
			if (sensorConnection != null) {
				linkToSensorId = helper.getNextSequenceValue(schemaMapper.getSequenceName(ADESequences.SENSORCONNECTION_SEQ));	
				sensorConnectionImporter.doImport(sensorConnection, linkToSensorId);
				sensorConnectionProperty.unsetSensorConnection();
			} 
		}
		if (linkToSensorId != 0)
			ps.setLong(5, linkToSensorId);
		else
			ps.setNull(5, Types.NULL);
		
		GMLTimePosition startTime = dynamizer.getStartTime();
		if (startTime.isSetValue()) {
			ps.setString(6, startTime.getValue());
		} else {
			ps.setNull(6, Types.VARCHAR);
		}
		
		if (startTime.isSetFrame()) {
			ps.setString(7, startTime.getFrame());
		} else {
			ps.setNull(7, Types.VARCHAR);
		}
		
		if (startTime.isSetCalendarEraName()) {
			ps.setString(8, startTime.getCalendarEraName());
		} else {
			ps.setNull(8, Types.VARCHAR);
		}
		
		if (startTime.isSetIndeterminatePosition()) {
			ps.setString(9, startTime.getIndeterminatePosition().value());
		} else {
			ps.setNull(9, Types.VARCHAR);
		}
		
		GMLTimePosition endTime = dynamizer.getEndTime();
		if (endTime.isSetValue()) {
			ps.setString(10, endTime.getValue());
		} else {
			ps.setNull(10, Types.VARCHAR);
		}
		
		if (endTime.isSetFrame()) {
			ps.setString(11, endTime.getFrame());
		} else {
			ps.setNull(11, Types.VARCHAR);
		}
		
		if (endTime.isSetCalendarEraName()) {
			ps.setString(12, endTime.getCalendarEraName());
		} else {
			ps.setNull(12, Types.VARCHAR);
		}
		
		if (endTime.isSetIndeterminatePosition()) {
			ps.setString(13, endTime.getIndeterminatePosition().value());
		} else {
			ps.setNull(13, Types.VARCHAR);
		}
		
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
