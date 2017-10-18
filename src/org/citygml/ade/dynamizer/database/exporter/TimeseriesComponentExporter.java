package org.citygml.ade.dynamizer.database.exporter;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.ObjectMapper;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml.ade.dynamizer.model.TimeseriesComponentProperty;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;

public class TimeseriesComponentExporter implements ADEExporter {
	private PreparedStatement ps;
	private TimeseriesExporter timeseriesExporter;
	private CityGMLExportHelper cityGMLExportHelper;
	private ObjectMapper objectMapper;
	private ExportManager manager;
	
	public TimeseriesComponentExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		
		StringBuilder stmt = new StringBuilder("select id, timeseries_ID, repetitions, additionalGap ")
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.TIMESERIESCOMPONENT))).append(" ")
				.append("where CompositeTimese_component_ID = ?");
		ps = connection.prepareStatement(stmt.toString());

		cityGMLExportHelper = helper;
		objectMapper = manager.getObjectMapper();
		this.manager = manager;
	}
	
	public void doExport(CompositeTimeseries parent, long parentId) throws CityGMLExportException, SQLException {
		timeseriesExporter = manager.getExporter(TimeseriesExporter.class);
		
		ps.setLong(1, parentId);		
		
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				long timeseriesComponentId = rs.getLong(1);
				
				int objectClassId = objectMapper.getObjectClassId(TimeseriesComponent.class);
				TimeseriesComponent timeseriesComponent = cityGMLExportHelper.createObjectStub(timeseriesComponentId, objectClassId, TimeseriesComponent.class);
				if (timeseriesComponent == null) {
					cityGMLExportHelper.logOrThrowErrorMessage("Failed to instantiate "
							+ cityGMLExportHelper.getObjectSignature(objectClassId, timeseriesComponentId)
							+ " as timeseries component object.");
					continue;
				}
				
				long timeseriesId = rs.getLong(2);
				
				if (!rs.wasNull()) {
					TimeseriesProperty timeseriesProperty = timeseriesExporter.doExport(timeseriesId);
					if (timeseriesProperty != null)
						timeseriesComponent.setTimeseries(timeseriesProperty);
				}
				
				int repetitions = rs.getInt(3);
				if (!rs.wasNull())
					timeseriesComponent.setRepetitions(BigInteger.valueOf(repetitions));
				
				String additionalGap = rs.getString(4);
				if (!rs.wasNull()) {
					try {
						timeseriesComponent.setAdditionalGap(DatatypeFactory.newInstance().newDuration(additionalGap));
					} catch (DatatypeConfigurationException e) {
						throw new CityGMLExportException("Failed to export the timeseries component object: " + timeseriesComponent.getId(), e);
					}	
				}										
					
				parent.addTimeseriesComponent(new TimeseriesComponentProperty(timeseriesComponent));
			}
		}		
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();
	}

}
