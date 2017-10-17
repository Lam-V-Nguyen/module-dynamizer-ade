package org.citygml.ade.dynamizer.database.exporter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.citydb.ade.exporter.ADEExporter;
import org.citydb.ade.exporter.CityGMLExportHelper;
import org.citydb.database.schema.mapping.FeatureType;
import org.citydb.modules.citygml.exporter.CityGMLExportException;
import org.citygml.ade.dynamizer.database.schema.ADETables;
import org.citygml4j.model.citygml.core.AbstractCityObject;

public class CityObjectPropertiesExporter implements ADEExporter {
	private PreparedStatement ps;
	private DynamizerExporter dynamizerExporter;

	public CityObjectPropertiesExporter(Connection connection, CityGMLExportHelper helper, ExportManager manager) throws CityGMLExportException, SQLException {
		StringBuilder stmt = new StringBuilder("select id from ")
				.append(helper.getTableNameWithSchema(manager.getSchemaMapper().getTableName(ADETables.CITYOBJECT))).append(" ")
				.append("where id = ?");
		ps = connection.prepareStatement(stmt.toString());

		dynamizerExporter = manager.getExporter(DynamizerExporter.class);
	}

	public void doExport(AbstractCityObject parent, long parentId, FeatureType parentType) throws CityGMLExportException, SQLException {
		ps.setLong(1, parentId);

		try (ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
			//	dynamizerExporter.doExport(parent, parentId, parentType);
			}
		}
	}

	@Override
	public void close() throws CityGMLExportException, SQLException {
		ps.close();
	}

}
