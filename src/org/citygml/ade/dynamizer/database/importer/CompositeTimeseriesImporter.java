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
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml.ade.dynamizer.model.TimeseriesComponentProperty;

public class CompositeTimeseriesImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;
	
	private PreparedStatement ps;
	private int batchCounter;

	public CompositeTimeseriesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.COMPOSITETIMESERIES))).append(" ")
				.append("(id) ")
				.append("values (?)");
		ps = connection.prepareStatement(stmt.toString());
	}
	
	public void doImport(CompositeTimeseries compositeTimeseries, long objectId, AbstractObjectType<?> objectType) throws CityGMLImportException, SQLException {
		ps.setLong(1, objectId);
		
		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(objectType);
		
		if (compositeTimeseries.isSetTimeseriesComponent()) {
			for (TimeseriesComponentProperty property : compositeTimeseries.getTimeseriesComponent()) {
				TimeseriesComponent component = property.getTimeseriesComponent();
				if (component != null) {
					helper.importObject(component, ForeignKeys.create().with("parentId", objectId));
					property.unsetTimeseriesComponent();;
				} else {
					String href = property.getHref();
					if (href != null && href.length() != 0)
						helper.logOrThrowUnsupportedXLinkMessage(compositeTimeseries, Dynamizer.class, href);
				}				
			}
		}
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
