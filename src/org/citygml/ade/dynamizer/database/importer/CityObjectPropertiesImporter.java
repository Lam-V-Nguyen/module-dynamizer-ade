package org.citygml.ade.dynamizer.database.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.ADEPropertyCollection;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.database.schema.mapping.FeatureType;
import org.citydb.modules.citygml.importer.CityGMLImportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.DynamizersPropertyElement;
import org.citygml4j.model.citygml.core.AbstractCityObject;

public class CityObjectPropertiesImporter implements ADEImporter {
	private final CityGMLImportHelper helper;
	private final SchemaMapper schemaMapper;

	private PreparedStatement ps;
	private int batchCounter;

	public CityObjectPropertiesImporter(Connection connection, CityGMLImportHelper helper, ImportManager manager) throws CityGMLImportException, SQLException {
		this.helper = helper;
		this.schemaMapper = manager.getSchemaMapper();

		StringBuilder stmt = new StringBuilder("insert into ")
				.append(helper.getTableNameWithSchema(schemaMapper.getTableName(ADETables.CITYOBJECT))).append(" ")
				.append("(id) ")
				.append("values (?)");
		ps = connection.prepareStatement(stmt.toString());
	}

	public void doImport(ADEPropertyCollection properties, AbstractCityObject parent, long parentId, FeatureType parentType) throws CityGMLImportException, SQLException {
		ps.setLong(1, parentId);

		ps.addBatch();
		if (++batchCounter == helper.getDatabaseAdapter().getMaxBatchSize())
			helper.executeBatch(schemaMapper.getTableName(ADETables.CITYOBJECT));

		if (properties.contains(DynamizersPropertyElement.class)) {
			for (DynamizersPropertyElement propertyElement : properties.getAll(DynamizersPropertyElement.class)) {
				Dynamizer dynamizer = propertyElement.getValue().getDynamizer();
				if (dynamizer != null) {
					helper.importObject(dynamizer, ForeignKeys.create().with("parentId", parentId));
					propertyElement.getValue().unsetDynamizer();
				} else {
					String href = propertyElement.getValue().getHref();
					if (href != null && href.length() != 0)
						helper.logOrThrowUnsupportedXLinkMessage(parent, Dynamizer.class, href);
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
