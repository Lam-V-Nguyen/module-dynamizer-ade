package org.citygml.ade.dynamizer.database.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.GMLTimePosition;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;
import org.citygml4j.model.citygml.core.AbstractCityObject;

import net.opengis.gml.TimeIndeterminateValueType;

public class DynamizerExporter implements ADEExporter {
	private PreparedStatement ps1;
	private PreparedStatement ps2;
	private TimeseriesExporter timeseriesExporter;
	
	public DynamizerExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		
		StringBuilder stmt1 = new StringBuilder("select id, dynamicData_ID, linkToSensor_ID, cityobject_dynamizers_ID, attributeRef, ")
				.append("startTime, startTime_frame, startTime_calendarEraName, startTime_indeterminatePosit, ") 
				.append("endTime, endTime_frame, endTime_calendarEraName, endTime_indeterminatePositio ") 
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.DYNAMIZER))).append(" ")
				.append("where id = ?");
		ps1 = connection.prepareStatement(stmt1.toString());
		
		StringBuilder stmt2 = new StringBuilder("select id, dynamicData_ID, linkToSensor_ID, cityobject_dynamizers_ID, attributeRef, ")
				.append("startTime, startTime_frame, startTime_calendarEraName, startTime_indeterminatePosit, ") 
				.append("endTime, endTime_frame, endTime_calendarEraName, endTime_indeterminatePositio ") 
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.DYNAMIZER))).append(" ")
				.append("where cityobject_dynamizers_ID = ?");
		ps2 = connection.prepareStatement(stmt2.toString());

		timeseriesExporter = manager.getExporter(TimeseriesExporter.class);
	}

	public void doExport(Dynamizer dynamizer, long parentId, AbstractObjectType<?> parentFeature) throws CityGMLExportException, SQLException {
		ps1.setLong(1, parentId);
		
		try (ResultSet rs = ps1.executeQuery()) {
			while (rs.next()) {
				long timeseries_id = rs.getLong(2);
				
				TimeseriesProperty timeseriesProperty = timeseriesExporter.doExport(timeseries_id);
				if (timeseriesProperty != null)
					dynamizer.setDynamicData(timeseriesProperty);
				
				String attributeRef = rs.getString(5);
				if (attributeRef != null) {
					dynamizer.setAttributeRef(attributeRef);
				}
				
				GMLTimePosition startTime = new GMLTimePosition();
				String startTimeValue = rs.getString(6);
				if (startTimeValue != null) {
					startTime.setValue(startTimeValue);
				}
				
				String startTimeFrame = rs.getString(7);
				if (startTimeFrame != null) {
					startTime.setFrame(startTimeFrame);
				}
				
				String startTimeCalendarEraName = rs.getString(8);
				if (startTimeCalendarEraName != null) {
					startTime.setCalendarEraName(startTimeCalendarEraName);
				}
				
				String startTimeIndeterminatePosition = rs.getString(9);
				if (startTimeIndeterminatePosition != null) {
					startTime.setIndeterminatePosition(TimeIndeterminateValueType.fromValue(startTimeIndeterminatePosition));
				}
				dynamizer.setStartTime(startTime);
				
				GMLTimePosition endTime = new GMLTimePosition();
				String endTimeValue = rs.getString(10);
				if (endTimeValue != null) {
					endTime.setValue(endTimeValue);
				}
				
				String endTimeFrame = rs.getString(11);
				if (endTimeFrame != null) {
					endTime.setFrame(endTimeFrame);
				}
				
				String endTimeCalendarEraName = rs.getString(12);
				if (endTimeCalendarEraName != null) {
					endTime.setCalendarEraName(endTimeCalendarEraName);
				}
				
				String endTimeIndeterminatePosition = rs.getString(13);
				if (endTimeIndeterminatePosition != null) {
					endTime.setIndeterminatePosition(TimeIndeterminateValueType.fromValue(endTimeIndeterminatePosition));
				}
				
				dynamizer.setEndTime(endTime);
			}
		}
	}
	
	public void doExport(AbstractCityObject parent, long parentId, AbstractObjectType<?> parentFeature) throws CityGMLExportException, SQLException {
		ps1.setLong(1, parentId);		
	//	AbstractGML test = helper.exportObject(parentId, parentFeature.getObjectClassId());
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps1.close();
		ps2.close();
	}

}
