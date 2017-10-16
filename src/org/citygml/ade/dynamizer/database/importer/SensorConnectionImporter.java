package org.citygml.ade.dynamizer.database.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.database.schema.mapping.ObjectType;
import org.citydb.modules.citygml.importer.CityGMLImportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml.ade.dynamizer.model.SensorConnection;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.gml.feature.FeatureProperty;

public class SensorConnectionImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private PreparedStatement ps;
	private int batchCounter;
	
	public SensorConnectionImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();
		
		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.SENSORCONNECTION))).append(" ")
				.append("(id, sensorLocation_ID, sensorID, serviceType, linkToObservation, linkToSensorML) ")
				.append("values (?, ?, ?, ?, ?, ?)");
		ps = connection.prepareStatement(stmt.toString());
		
		helper.getAttributeValueJoiner();
	}
	
	public void doImport(SensorConnection sensorConnection, long objectId) throws CityGMLImportException, SQLException {
			
		ps.setLong(1, objectId);
		
		FeatureProperty<AbstractCityObject> cityObjectProperty = sensorConnection.getSensorLocation();
		if (cityObjectProperty != null) {
			String href = cityObjectProperty.getHref();
			if (href != null && href.length() != 0) {
				ObjectType dummyObjectType = new ObjectType("dummy", "dummy", helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.SENSORCONNECTION)), 0, null, null);
				helper.propagateObjectXlink(dummyObjectType, objectId, href, "sensorLocation_ID");
			}
		}
		ps.setNull(2, Types.NULL);
		
		if (sensorConnection.isSetSensorId())
			ps.setString(3, sensorConnection.getSensorId());
		else
			ps.setNull(3, Types.VARCHAR);
		
		if (sensorConnection.isSetServiceType())
			ps.setString(4, sensorConnection.getServiceType());
		else
			ps.setNull(4, Types.VARCHAR);
		
		if (sensorConnection.isSetLinkToObservation())
			ps.setString(5, sensorConnection.getLinkToObservation());
		else
			ps.setNull(5, Types.VARCHAR);
		
		if (sensorConnection.isSetLinkToSensorML())
			ps.setString(6, sensorConnection.getLinkToSensorML());
		else
			ps.setNull(6, Types.VARCHAR);
		
		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(schemaMapper.getTableName(ADETables.SENSORCONNECTION));
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
