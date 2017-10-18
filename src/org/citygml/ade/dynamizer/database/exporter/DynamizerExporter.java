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
import org.citygml.ade.dynamizer.database.schema.ObjectMapper;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.DynamizerProperty;
import org.citygml.ade.dynamizer.model.DynamizersPropertyElement;
import org.citygml.ade.dynamizer.model.GMLTimePosition;
import org.citygml.ade.dynamizer.model.SensorConnectionProperty;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;
import org.citygml4j.model.citygml.core.AbstractCityObject;

import net.opengis.gml.TimeIndeterminateValueType;

public class DynamizerExporter implements ADEExporter {
	private PreparedStatement ps1;
	private PreparedStatement ps2;
	
	private TimeseriesExporter timeseriesExporter;
	private SensorConnectionExporter sensorConnectionExporter;
	private CityGMLExportHelper cityGMLExportHelper;
	private ObjectMapper objectMapper;
	
	public DynamizerExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		
		StringBuilder stmt1 = new StringBuilder("select id, dynamicData_ID, linkToSensor_ID, attributeRef, ")
				.append("startTime, startTime_frame, startTime_calendarEraName, startTime_indeterminatePosit, ") 
				.append("endTime, endTime_frame, endTime_calendarEraName, endTime_indeterminatePositio ") 
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.DYNAMIZER))).append(" ")
				.append("where id = ?");
		ps1 = connection.prepareStatement(stmt1.toString());
		
		StringBuilder stmt2 = new StringBuilder("select id ")
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.DYNAMIZER))).append(" ")
				.append("where cityobject_dynamizers_ID = ?");
		ps2 = connection.prepareStatement(stmt2.toString());

		timeseriesExporter = manager.getExporter(TimeseriesExporter.class);
		sensorConnectionExporter = manager.getExporter(SensorConnectionExporter.class);
		cityGMLExportHelper = helper;
		objectMapper = manager.getObjectMapper();
	}

	public void doExport(Dynamizer dynamizer, long parentId, AbstractObjectType<?> parentFeature) throws CityGMLExportException, SQLException {
		ps1.setLong(1, parentId);
		
		try (ResultSet rs = ps1.executeQuery()) {
			while (rs.next()) {
				long timeseriesId = rs.getLong(2);
				
				if (!rs.wasNull()) {
					TimeseriesProperty timeseriesProperty = timeseriesExporter.doExport(timeseriesId);
					if (timeseriesProperty != null)
						dynamizer.setDynamicData(timeseriesProperty);
				}
		
				long sensorConnectionId = rs.getLong(3);
				if (!rs.wasNull()) {
					SensorConnectionProperty sensorConnectionProperty = sensorConnectionExporter.doExport(sensorConnectionId);
					if (sensorConnectionProperty != null)
						dynamizer.setLinkToSensor(sensorConnectionProperty);
				}				
				
				String attributeRef = rs.getString(4);
				if (!rs.wasNull()) {
					dynamizer.setAttributeRef(attributeRef);
				}
				
				GMLTimePosition startTime = new GMLTimePosition();
				String startTimeValue = rs.getString(5);
				if (!rs.wasNull()) {
					startTime.setValue(startTimeValue);
				}
				
				String startTimeFrame = rs.getString(6);
				if (!rs.wasNull()) {
					startTime.setFrame(startTimeFrame);
				}
				
				String startTimeCalendarEraName = rs.getString(7);
				if (!rs.wasNull()) {
					startTime.setCalendarEraName(startTimeCalendarEraName);
				}
				
				String startTimeIndeterminatePosition = rs.getString(8);
				if (!rs.wasNull()) {
					startTime.setIndeterminatePosition(TimeIndeterminateValueType.fromValue(startTimeIndeterminatePosition));
				}
				dynamizer.setStartTime(startTime);
				
				GMLTimePosition endTime = new GMLTimePosition();
				String endTimeValue = rs.getString(9);
				if (!rs.wasNull()) {
					endTime.setValue(endTimeValue);
				}
				
				String endTimeFrame = rs.getString(10);
				if (!rs.wasNull()) {
					endTime.setFrame(endTimeFrame);
				}
				
				String endTimeCalendarEraName = rs.getString(11);
				if (!rs.wasNull()) {
					endTime.setCalendarEraName(endTimeCalendarEraName);
				}
				
				String endTimeIndeterminatePosition = rs.getString(12);
				if (!rs.wasNull()) {
					endTime.setIndeterminatePosition(TimeIndeterminateValueType.fromValue(endTimeIndeterminatePosition));
				}
				
				dynamizer.setEndTime(endTime);
			}
		}
	}
	
	public void doExport(AbstractCityObject parent, long parentId, AbstractObjectType<?> parentFeature) throws CityGMLExportException, SQLException {
		ps2.setLong(1, parentId);		

		try (ResultSet rs = ps2.executeQuery()) {
			while (rs.next()) {
				long dynamizerId = rs.getLong(1);
				
				int objectClassId = objectMapper.getObjectClassId(Dynamizer.class);
				Dynamizer dynamizer = cityGMLExportHelper.createObjectStub(dynamizerId, objectClassId, Dynamizer.class);
				DynamizerProperty property = new DynamizerProperty();
				property.setHref(dynamizer.getId());
				DynamizersPropertyElement propertyElement = new DynamizersPropertyElement(property);
				parent.addGenericApplicationPropertyOfCityObject(propertyElement);
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps1.close();
		ps2.close();
	}

}
