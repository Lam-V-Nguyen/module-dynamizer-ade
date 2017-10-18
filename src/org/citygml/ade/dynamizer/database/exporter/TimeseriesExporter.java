package org.citygml.ade.dynamizer.database.exporter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.ObjectMapper;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;
import org.citygml.ade.dynamizer.util.DynamizerUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class TimeseriesExporter implements ADEExporter {
	private PreparedStatement ps;
	private ObjectMapper objectMapper;
	private CityGMLExportHelper cityGMLExportHelper;
	
	private TimeseriesComponentExporter timeseriesComponentExporter;
	
	public TimeseriesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws SQLException {		
		StringBuilder stmt = new StringBuilder()
				.append("select at.ID as AT_ID, ct.id as CT_ID, ")
				.append("at.dynamicDataDR, at.dynamicDataTVP, at.observationData ")
				.append("from ").append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.TIMESERIES))).append(" ts ")
				.append("left join ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.ATOMICTIMESERIES)))
				.append(" at on at.ID=ts.ID ")
				.append("left join ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.COMPOSITETIMESERIES)))
				.append(" ct on at.ID=ct.ID where ts.ID = ?");
		ps = connection.prepareStatement(stmt.toString());
		
		objectMapper = manager.getObjectMapper();
		cityGMLExportHelper = helper;
	}

	public TimeseriesProperty doExport(long parentId) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				long atomicTimeseriesId = rs.getLong(1);
				long compositeTimeseriesId = rs.getLong(2);
				if (atomicTimeseriesId > 0) {
					int objectClassId = objectMapper.getObjectClassId(AtomicTimeseries.class);					
					AtomicTimeseries atomicTimeseries = cityGMLExportHelper.createObjectStub(atomicTimeseriesId, objectClassId, AtomicTimeseries.class);
					
					if (atomicTimeseries == null) {
						cityGMLExportHelper.logOrThrowErrorMessage("Failed to instantiate " + cityGMLExportHelper.getObjectSignature(objectClassId, atomicTimeseriesId) + " as AtomicTimeseries object.");
						continue;
					}
					else {
						Document document = null;
						
						String dynamicDataDR = rs.getString(3);
						if (!rs.wasNull()) {
							try {
								document = DynamizerUtil.convertStringToDocument(dynamicDataDR);	
								atomicTimeseries.setDynamicDataDR(document.getDocumentElement());
							} catch (SAXException | IOException | ParserConfigurationException e) {
								throw new CityGMLExportException("Failed to export the dynamicDataDR data of the timeseries object: " + atomicTimeseries.getId(), e);
							}
						}
						
						String dynamicDataTVP = rs.getString(4);
						if (!rs.wasNull()) {
							try {
								document = DynamizerUtil.convertStringToDocument(dynamicDataTVP);	
								atomicTimeseries.setDynamicDataTVP(document.getDocumentElement());
							} catch (SAXException | IOException | ParserConfigurationException e) {
								throw new CityGMLExportException("Failed to export the dynamicDataTVP data of the timeseries object: " + atomicTimeseries.getId(), e);
							}
						}

						String observationData = rs.getString(5);
						if (!rs.wasNull()) {
							try {
								document = DynamizerUtil.convertStringToDocument(observationData);	
								atomicTimeseries.setObservationData(document.getDocumentElement());
							} catch (SAXException | IOException | ParserConfigurationException e) {
								throw new CityGMLExportException("Failed to export the SOS observationData data of the timeseries object: " + atomicTimeseries.getId(), e);
							}
						}
													
						if (document != null) 
							
						return new TimeseriesProperty(atomicTimeseries);
					}
				}
				else if (compositeTimeseriesId > 0) {
					int objectClassId = objectMapper.getObjectClassId(CompositeTimeseries.class);					
					CompositeTimeseries compositeTimeseries = cityGMLExportHelper.createObjectStub(compositeTimeseriesId, objectClassId, CompositeTimeseries.class);
					
					if (compositeTimeseries == null) {
						cityGMLExportHelper.logOrThrowErrorMessage("Failed to instantiate " + cityGMLExportHelper.getObjectSignature(objectClassId, compositeTimeseriesId) + " as CompositeTimeseries object.");
						continue;
					}
					
					timeseriesComponentExporter.doExport(compositeTimeseries, compositeTimeseriesId);
				}
			}
		}
		
		return null;
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();	
	}

}
