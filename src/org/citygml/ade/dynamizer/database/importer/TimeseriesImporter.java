package org.citygml.ade.dynamizer.database.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.modules.citygml.importer.CityGMLImportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;

public class TimeseriesImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private PreparedStatement ps;
	private int batchCounter;
	
	private AtomicTimeseriesImporter atomicTimeseriesImporter;
	private CompositeTimeseriesImporter compositeTimeseriesImporter;

	public TimeseriesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.TIMESERIES))).append(" ")
				.append("(id) ")
				.append("values (?)");
		ps = connection.prepareStatement(stmt.toString());
		
		atomicTimeseriesImporter = manager.getImporter(AtomicTimeseriesImporter.class);
		compositeTimeseriesImporter = manager.getImporter(CompositeTimeseriesImporter.class);
	}
	
	public void doImport(AbstractTimeseries timeseries, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {
		ps.setLong(1, objectId);
		
		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(objectType);
		
		if (timeseries instanceof AtomicTimeseries) 
			atomicTimeseriesImporter.doImport((AtomicTimeseries) timeseries, objectId, objectType);
		
		if (timeseries instanceof CompositeTimeseries)
			compositeTimeseriesImporter.doImport((CompositeTimeseries) timeseries, objectId, objectType);
			
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
