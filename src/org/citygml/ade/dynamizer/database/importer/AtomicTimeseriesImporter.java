package org.citygml.ade.dynamizer.database.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.xml.transform.TransformerException;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.modules.citygml.importer.CityGMLImportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.util.DynamizerUtil;
import org.w3c.dom.Document;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

public class AtomicTimeseriesImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private PreparedStatement ps;
	private int batchCounter;

	public AtomicTimeseriesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.ATOMICTIMESERIES))).append(" ")
				.append("(id, dynamicDataTVP, dynamicDataDR, observationData) ")
				.append("values (?, ?, ?, ?)");
		ps = connection.prepareStatement(stmt.toString());
	}
	
	public void doImport(AtomicTimeseries atomicTimeseries, long objectId, AbstractObjectType<?> objectType) throws CityGMLImportException, SQLException {
		ps.setLong(1, objectId);
		
		//TODO invoke the doImport() function of the class TimeseriesImporter?
		
		if (atomicTimeseries.isSetDynamicDataTVP()) {
			Object dynamicDataTVP = atomicTimeseries.getDynamicDataTVP();
			ElementNSImpl elementNSImpl = (ElementNSImpl)dynamicDataTVP;
			Document document = elementNSImpl.getOwnerDocument();
			try {
				ps.setString(2, DynamizerUtil.convertXMLDocumentToString(document));
			} catch (TransformerException e) {
				throw new CityGMLImportException("Failed to import DynamicDataTVP data", e);
			}
		}
		else {
			ps.setNull(2, Types.CLOB);
		}
		
		if (atomicTimeseries.isSetDynamicDataDR()) {
			Object dynamicDataDR = atomicTimeseries.getDynamicDataDR();
			ElementNSImpl elementNSImpl = (ElementNSImpl)dynamicDataDR;
			Document document = elementNSImpl.getOwnerDocument();
			try {
				ps.setString(3, DynamizerUtil.convertXMLDocumentToString(document));
			} catch (TransformerException e) {
				throw new CityGMLImportException("Failed to import DynamicDataDR data", e);
			}
		}
		else {
			ps.setNull(3, Types.CLOB);
		}
		
		if (atomicTimeseries.isSetObservationData()) {
			Object observationData = atomicTimeseries.getDynamicDataDR();
			ElementNSImpl elementNSImpl = (ElementNSImpl)observationData;
			Document document = elementNSImpl.getOwnerDocument();
			try {
				ps.setString(3, DynamizerUtil.convertXMLDocumentToString(document));
			} catch (TransformerException e) {
				throw new CityGMLImportException("Failed to import SOS ObservationData data", e);
			}
		}
		else {
			ps.setNull(4, Types.CLOB);
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
