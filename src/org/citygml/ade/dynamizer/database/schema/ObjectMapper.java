package org.citygml.ade.dynamizer.database.schema;

import java.util.HashMap;
import java.util.Map.Entry;

import org.citydb.ade.ADEExtensionException;
import org.citydb.ade.ADEObjectMapper;
import org.citydb.database.schema.mapping.AbstractObjectType;
import org.citydb.database.schema.mapping.SchemaMapping;
import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml4j.model.gml.base.AbstractGML;
import org.citygml4j.model.module.citygml.CityGMLVersion;

public class ObjectMapper implements ADEObjectMapper {
	private HashMap<Class<? extends AbstractGML>, Integer> objectClassIds = new HashMap<>();

	public void populateObjectClassIds(SchemaMapping schemaMapping) throws ADEExtensionException {
		for (AbstractObjectType<?> type : schemaMapping.getAbstractObjectTypes()) {
			int objectClassId = type.getObjectClassId();
			
			switch (type.getPath()) {
			case "Dynamizer":
				objectClassIds.put(Dynamizer.class, objectClassId);
				break;
			case "AbstractTimeseries":
				objectClassIds.put(AbstractTimeseries.class, objectClassId);
				break;
			case "AtomicTimeseries":
				objectClassIds.put(AtomicTimeseries.class, objectClassId);
				break;
			case "CompositeTimeseries":
				objectClassIds.put(CompositeTimeseries.class, objectClassId);
				break;
			case "TimeseriesComponent":
				objectClassIds.put(TimeseriesComponent.class, objectClassId);
				break;
			}
		}
	}

	@Override
	public AbstractGML createObject(int objectClassId, CityGMLVersion version) {
		if (version == CityGMLVersion.v2_0_0) {
			for (Entry<Class<? extends AbstractGML>, Integer> entry : objectClassIds.entrySet()) {
				if (entry.getValue() == objectClassId) {
					try {
						return entry.getKey().newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						// 
					}
				}
			}
		}

		return null;
	}

	@Override
	public int getObjectClassId(Class<? extends AbstractGML> adeObjectClass) {
		Integer objectClassId = objectClassIds.get(adeObjectClass);
		return objectClassId != null ? objectClassId.intValue() : 0;
	}

}
