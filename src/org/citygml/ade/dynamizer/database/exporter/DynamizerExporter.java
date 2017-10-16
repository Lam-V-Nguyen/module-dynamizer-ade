package org.citygml.ade.dynamizer.database.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;

public class DynamizerExporter implements ADEExporter {
	private PreparedStatement ps;
	
	private TimeseriesExporter timeseriesExporter;

	public DynamizerExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		StringBuilder stmt = new StringBuilder("select id, dynamicData_ID, linkToSensor_ID, cityobject_dynamizers_ID, attributeRef ")
				.append("startTime, startTime_frame, startTime_calendarEraName, startTime_indeterminatePosit, ") 
				.append("endTime, endTime_frame, endTime_calendarEraName, endTime_indeterminatePositio ") 
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.DYNAMIZER))).append(" ")
				.append("where id = ?");
		ps = connection.prepareStatement(stmt.toString());
		
		timeseriesExporter = manager.getExporter(TimeseriesExporter.class);
	}

	public void doExport(long parentId) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long timeseries_id = rs.getLong(2);
				timeseriesExporter.doExport(timeseries_id);
				// TODO
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();
	}

}
