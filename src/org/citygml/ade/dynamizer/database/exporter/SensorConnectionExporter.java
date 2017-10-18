package org.citygml.ade.dynamizer.database.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.database.TableEnum;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.model.SensorConnection;
import org.citygml.ade.dynamizer.model.SensorConnectionProperty;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.gml.feature.FeatureProperty;

public class SensorConnectionExporter implements ADEExporter {
	private PreparedStatement ps;
	public SensorConnectionExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {
		StringBuilder stmt = new StringBuilder("select co.gmlid, sc.sensorID, sc.serviceType, sc.linkToObservation, sc.linkToSensorML from ")
				.append(helper.getTableNameWithSchema(TableEnum.CITYOBJECT.getName())).append(" co, ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.SENSORCONNECTION))).append(" sc ")
				.append("where sc.id = ? and co.id = sc.id");
		ps = connection.prepareStatement(stmt.toString());
	}

	public SensorConnectionProperty doExport(long sensorConnectionId) throws CityGMLExportException, SQLException {
		ps.setLong(1, sensorConnectionId);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {				
				SensorConnection sensorConnection = new SensorConnection();
				
				String gmlId = rs.getString(1);
				if (!rs.wasNull()) {
					FeatureProperty<AbstractCityObject> property = new FeatureProperty<AbstractCityObject>();
					property.setHref(gmlId);
					sensorConnection.setSensorLocation(property);
				}				

				String sensorId = rs.getString(2);
				if (!rs.wasNull())
					sensorConnection.setSensorId(sensorId);
				
				String serviceType = rs.getString(3);
				if (!rs.wasNull())
					sensorConnection.setServiceType(serviceType);
				
				String linkToObservation = rs.getString(4);
				if (!rs.wasNull())
					sensorConnection.setLinkToObservation(linkToObservation);
				
				String linkToSensorML = rs.getString(5);
				if (!rs.wasNull())
					sensorConnection.setLinkToSensorML(linkToSensorML);
				
				return new SensorConnectionProperty(sensorConnection);
			}
		}
		
		return null;
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();	
	}

}
