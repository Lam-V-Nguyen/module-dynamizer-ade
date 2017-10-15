package org.citygml.ade.dynamizer.database.importer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.citydb.ade.importer.ADEImporter;
import org.citydb.ade.importer.CityGMLImportHelper;
import org.citydb.ade.importer.ForeignKeys;
import org.citydb.api.geometry.GeometryObject;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.modules.citygml.importer.CityGMLImportException;
import org.citydb.modules.citygml.importer.util.AttributeValueJoiner;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml4j.model.citygml.core.Address;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.gml.basicTypes.Code;
import org.citygml4j.model.gml.geometry.aggregates.MultiCurveProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiSurfaceProperty;
import org.citygml4j.model.gml.geometry.primitives.SolidProperty;

public class DynamizerImporter implements ADEImporter {
	private final Connection connection;
	private final CityGMLImportHelper helper;

	private AttributeValueJoiner valueJoiner;
	private PreparedStatement ps;
	private int batchCounter;

	public DynamizerImporter(Connection connection, CityGMLImportHelper helper) throws CityGMLImportException, SQLException {
		this.connection = connection;
		this.helper = helper;
	}
	
	public void doImport(Dynamizer buildingUnit, long objectId, AbstractObjectType<?> objectType, ForeignKeys foreignKeys) throws CityGMLImportException, SQLException {}

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
