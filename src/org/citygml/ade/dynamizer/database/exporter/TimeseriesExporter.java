package org.citygml.ade.dynamizer.database.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;

public class TimeseriesExporter implements ADEExporter {
	private PreparedStatement ps;

	public TimeseriesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
		
		StringBuilder stmt = new StringBuilder()
				.append("select ts.id as TS_ID, ")
				.append("at.ID as AT_ID, ct.id as CT_ID, ")
				.append("at.dynamicDataDR, at.dynamicDataTVP, at.observationData ")
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.TIMESERIES))).append(" ts ")
				.append("left join ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.ATOMICTIMESERIES)))
				.append(" at on at.ID=ts.ID ")
				.append("left join ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.COMPOSITETIMESERIES)))
				.append(" ct on at.ID=ct.ID where ts.ID = ?");
		ps = connection.prepareStatement(stmt.toString());
	}

	public void doExport(long parentId) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				// TODO
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();	
	}

}
