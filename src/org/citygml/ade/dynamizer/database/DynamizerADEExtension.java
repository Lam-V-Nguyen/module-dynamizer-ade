package org.citygml.ade.dynamizer.database;

import java.util.Arrays;
import java.util.List;

import org.citydb.ImpExp;
import org.citydb.ade.ADEExtension;
import org.citydb.ade.ADEExtensionException;
import org.citydb.ade.ADEObjectMapper;
import org.citydb.ade.exporter.ADEExportManager;
import org.citydb.ade.importer.ADEImportManager;
import org.citydb.database.schema.mapping.SchemaMapping;
import org.citygml.ade.dynamizer.DynamizerADEContext;
import org.citygml.ade.dynamizer.database.exporter.ExportManager;
import org.citygml.ade.dynamizer.database.importer.ImportManager;
import org.citygml.ade.dynamizer.database.schema.ObjectMapper;
import org.citygml.ade.dynamizer.database.schema.SchemaMapper;
import org.citygml4j.model.citygml.ade.binding.ADEContext;

public class DynamizerADEExtension extends ADEExtension {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final SchemaMapper schemaMapper = new SchemaMapper();
	private final DynamizerADEContext context = new DynamizerADEContext();

	public static void main(String[] args) throws Exception {
		new ImpExp().doMain(args, new DynamizerADEExtension());
	}
	
	@Override
	public void init(SchemaMapping schemaMapping) throws ADEExtensionException {
		objectMapper.populateObjectClassIds(schemaMapping);
		schemaMapper.populateSchemaNames(schemaMapping.getMetadata().getDBPrefix().toLowerCase());
	}

	@Override
	public List<ADEContext> getADEContexts() {
		return Arrays.asList(new ADEContext[]{context});
	}

	@Override
	public ADEObjectMapper getADEObjectMapper() {
		return objectMapper;
	}
	
	@Override
	public ADEImportManager createADEImportManager() {
		return new ImportManager(this, schemaMapper);
	}

	@Override
	public ADEExportManager createADEExportManager() {
		return new ExportManager(schemaMapper, objectMapper);
	}
	
	public SchemaMapper getSchemaMapper() {
		return schemaMapper;
	}
	
}
