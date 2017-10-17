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
import org.citygml4j.model.citygml.core.AbstractCityObject;

public class DynamizerExporter implements ADEExporter {
	private PreparedStatement ps1;
	private PreparedStatement ps2;
	private TimeseriesExporter timeseriesExporter;
	public DynamizerExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		
		StringBuilder stmt1 = new StringBuilder("select id, dynamicData_ID, linkToSensor_ID, cityobject_dynamizers_ID, attributeRef ")
				.append("startTime, startTime_frame, startTime_calendarEraName, startTime_indeterminatePosit, ") 
				.append("endTime, endTime_frame, endTime_calendarEraName, endTime_indeterminatePositio ") 
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.DYNAMIZER))).append(" ")
				.append("where id = ?");
		ps1 = connection.prepareStatement(stmt1.toString());
		
		StringBuilder stmt2 = new StringBuilder("select id, dynamicData_ID, linkToSensor_ID, cityobject_dynamizers_ID, attributeRef ")
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
				timeseriesExporter.doExport(timeseries_id);
				
				String attributeRef = rs.getString(5);
				if (!rs.wasNull()) {
					dynamizer.setAttributeRef(attributeRef);
				}
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
