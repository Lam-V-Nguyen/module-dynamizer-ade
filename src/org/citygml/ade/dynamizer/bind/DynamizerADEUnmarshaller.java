package org.citygml.ade.dynamizer.bind;

import javax.xml.bind.JAXBElement;

import org.citygml.ade.dynamizer._1.AbstractTimeseriesPropertyType;
import org.citygml.ade.dynamizer._1.AbstractTimeseriesType;
import org.citygml.ade.dynamizer._1.AtomicTimeseriesType;
import org.citygml.ade.dynamizer._1.CompositeTimeseriesType;
import org.citygml.ade.dynamizer._1.DynamizerPropertyType;
import org.citygml.ade.dynamizer._1.DynamizerType;
import org.citygml.ade.dynamizer._1.SensorConnectionPropertyType;
import org.citygml.ade.dynamizer._1.SensorConnectionType;
import org.citygml.ade.dynamizer._1.TimeseriesComponentPropertyType;
import org.citygml.ade.dynamizer._1.TimeseriesComponentType;
import org.citygml.ade.dynamizer.model.AbstractTimeseries;
import org.citygml.ade.dynamizer.model.AtomicTimeseries;
import org.citygml.ade.dynamizer.model.CompositeTimeseries;
import org.citygml.ade.dynamizer.model.Dynamizer;
import org.citygml.ade.dynamizer.model.DynamizerProperty;
import org.citygml.ade.dynamizer.model.DynamizersPropertyElement;
import org.citygml.ade.dynamizer.model.GMLTimePosition;
import org.citygml.ade.dynamizer.model.SensorConnection;
import org.citygml.ade.dynamizer.model.SensorConnectionProperty;
import org.citygml.ade.dynamizer.model.TimeseriesComponent;
import org.citygml.ade.dynamizer.model.TimeseriesComponentProperty;
import org.citygml.ade.dynamizer.model.TimeseriesProperty;
import org.citygml4j.builder.jaxb.unmarshal.citygml.ade.ADEUnmarshallerHelper;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.model.citygml.ade.binding.ADEUnmarshaller;
import org.citygml4j.model.common.base.ModelObject;
import org.citygml4j.model.gml.xlink.XLinkActuate;
import org.citygml4j.model.gml.xlink.XLinkShow;
import org.citygml4j.model.gml.xlink.XLinkType;
import org.citygml4j.util.jaxb.JAXBCheckedMapper;
import org.citygml4j.xml.io.reader.MissingADESchemaException;

import net.opengis.gml.TimePositionType;

public class DynamizerADEUnmarshaller implements ADEUnmarshaller {
	private final JAXBCheckedMapper<ADEModelObject> typeMapper;
	private ADEUnmarshallerHelper helper;

	public DynamizerADEUnmarshaller() {
		typeMapper = JAXBCheckedMapper.<ADEModelObject>create()
				.with(AbstractTimeseriesPropertyType.class, this::unmarshalTimeseriesProperty)
				.with(AtomicTimeseriesType.class, this::unmarshalAtomicTimeseries)
				.with(CompositeTimeseriesType.class, this::unmarshalCompositeTimeseries)
				.with(DynamizerType.class, this::unmarshalDynamizer)
				.with(DynamizerPropertyType.class, this::unmarshalDynamizerProperty)
				.with(TimeseriesComponentType.class, this::unmarshalTimeseriesComponent)
				.with(TimeseriesComponentPropertyType.class, this::unmarshalTimeseriesComponentProperty)
				.with(SensorConnectionType.class, this::unmarshalSensorConnection)
				.with(SensorConnectionPropertyType.class, this::unmarshalSensorConnectionProperty)
				.with(JAXBElement.class, this::unmarshal);
	}

	@Override
	public void setADEUnmarshallerHelper(ADEUnmarshallerHelper helper) {
		this.helper = helper;
	}

	@Override
	public ADEModelObject unmarshal(JAXBElement<?> src) throws MissingADESchemaException {
		final Object value = src.getValue();
		
		if (src.getName().getLocalPart().equals("dynamizers"))
			return new DynamizersPropertyElement(unmarshalDynamizerProperty((DynamizerPropertyType)value));
		
		// all other types
		return unmarshal(value);
	}

	@Override
	public ADEModelObject unmarshal(Object src) throws MissingADESchemaException {
		return typeMapper.apply(src);
	}

	public void unmarshalAbstractTimeseries(AbstractTimeseriesType src, AbstractTimeseries dest) throws MissingADESchemaException {
		helper.getGMLUnmarshaller().unmarshalAbstractFeature(src, dest);
	}
	
	public TimeseriesProperty unmarshalTimeseriesProperty(AbstractTimeseriesPropertyType src) throws MissingADESchemaException {
		TimeseriesProperty dest = new TimeseriesProperty();
		
		if (src.isSetAbstractTimeseries()) {
			ModelObject object = helper.getJAXBUnmarshaller().unmarshal(src.getAbstractTimeseries());
			if (object instanceof AbstractTimeseries)
				dest.setTimeseries((AbstractTimeseries)object);
		}
		
		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(XLinkType.fromValue(src.getType().value()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(XLinkShow.fromValue(src.getShow().value()));

		if (src.isSetActuate())
			dest.setActuate(XLinkActuate.fromValue(src.getActuate().value()));
		
		return dest;
	}
	
	public AtomicTimeseries unmarshalAtomicTimeseries(AtomicTimeseriesType src) throws MissingADESchemaException {
		AtomicTimeseries dest = new AtomicTimeseries();
		unmarshalAbstractTimeseries(src, dest);
		
		if (src.isSetDynamicDataDR())
			dest.setDynamicDataDR(src.getDynamicDataDR());
		
		if (src.isSetDynamicDataTVP())
			dest.setDynamicDataTVP(src.getDynamicDataTVP());
		
		if (src.isSetObservationData())
			dest.setObservationData(src.getObservationData());
		
		return dest;
	}
	
	public CompositeTimeseries unmarshalCompositeTimeseries(CompositeTimeseriesType src) throws MissingADESchemaException {
		CompositeTimeseries dest = new CompositeTimeseries();
		unmarshalAbstractTimeseries(src, dest);
		
		if (src.isSetComponent()) {
			for (TimeseriesComponentPropertyType property : src.getComponent())
				dest.addTimeseriesComponent(unmarshalTimeseriesComponentProperty(property));
		}
		
		return dest;
	}
	
	public Dynamizer unmarshalDynamizer(DynamizerType src) throws MissingADESchemaException {
		Dynamizer dest = new Dynamizer();
		helper.getCore200Unmarshaller().unmarshalAbstractCityObject(src, dest);
		
		if (src.isSetAttributeRef())
			dest.setAttributeRef(src.getAttributeRef());
		
		if (src.isSetStartTime())
			dest.setStartTime(unmarshalGMLTimePosition(src.getStartTime()));
		
		if (src.isSetEndTime())
			dest.setEndTime(unmarshalGMLTimePosition(src.getEndTime()));
		
		if (src.isSetDynamicData())
			dest.setDynamicData(unmarshalTimeseriesProperty(src.getDynamicData()));
		
		return dest;
	}
	
	public DynamizerProperty unmarshalDynamizerProperty(DynamizerPropertyType src) throws MissingADESchemaException {
		DynamizerProperty dest = new DynamizerProperty();
		
		if (src.isSetDynamizer()) {
			ModelObject object = helper.getJAXBUnmarshaller().unmarshal(src.getDynamizer());
			if (object instanceof Dynamizer)
				dest.setDynamizer((Dynamizer)object);
		}
		
		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(XLinkType.fromValue(src.getType().value()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(XLinkShow.fromValue(src.getShow().value()));

		if (src.isSetActuate())
			dest.setActuate(XLinkActuate.fromValue(src.getActuate().value()));
		
		return dest;
	}
	
	public GMLTimePosition unmarshalGMLTimePosition(TimePositionType src) throws MissingADESchemaException {
		GMLTimePosition dest = new GMLTimePosition();
		
		if (src.isSetValue()) {
			dest.setValue(src.getValue().get(0));
		}
		
		if (src.isSetFrame())
			dest.setFrame(src.getFrame());
		
		if (src.isSetCalendarEraName())
			dest.setCalendarEraName(src.getCalendarEraName());
		
		if (src.isSetIndeterminatePosition())
			dest.setIndeterminatePosition(src.getIndeterminatePosition());
		
		return dest;
	}
	
	public TimeseriesComponent unmarshalTimeseriesComponent(TimeseriesComponentType src) throws MissingADESchemaException {
		TimeseriesComponent dest = new TimeseriesComponent();
		helper.getGMLUnmarshaller().unmarshalAbstractFeature(src, dest);
		
		if (src.isSetRepetitions())
			dest.setRepetitions(src.getRepetitions());
		
		if (src.isSetAdditionalGap())
			dest.setAdditionalGap(src.getAdditionalGap());
		
		if (src.isSetTimeseries())
			dest.setTimeseries(unmarshalTimeseriesProperty(src.getTimeseries()));
		
		return dest;
	}
	
	public TimeseriesComponentProperty unmarshalTimeseriesComponentProperty(TimeseriesComponentPropertyType src) throws MissingADESchemaException {
		TimeseriesComponentProperty dest = new TimeseriesComponentProperty();
		
		if (src.isSetTimeseriesComponent()) {
			ModelObject object = helper.getJAXBUnmarshaller().unmarshal(src.getTimeseriesComponent());
			if (object instanceof TimeseriesComponent)
				dest.setTimeseriesComponent((TimeseriesComponent)object);
		}
		
		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());
	
		if (src.isSetType())
			dest.setType(XLinkType.fromValue(src.getType().value()));
	
		if (src.isSetHref())
			dest.setHref(src.getHref());
	
		if (src.isSetRole())
			dest.setRole(src.getRole());
	
		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());
	
		if (src.isSetTitle())
			dest.setTitle(src.getTitle());
	
		if (src.isSetShow())
			dest.setShow(XLinkShow.fromValue(src.getShow().value()));
	
		if (src.isSetActuate())
			dest.setActuate(XLinkActuate.fromValue(src.getActuate().value()));
		
		return dest;
	}

	public SensorConnection unmarshalSensorConnection(SensorConnectionType src) throws MissingADESchemaException {
		SensorConnection dest = new SensorConnection();
		
		if (src.isSetSensorID())
			dest.setSensorId(src.getSensorID());
		
		if (src.isSetServiceType())
			dest.setServiceType(src.getServiceType());
		
		if (src.isSetLinkToObservation())
			dest.setLinkToObservation(src.getLinkToObservation());
		
		if (src.isSetLinkToSensorML())
			dest.setLinkToSensorML(src.getLinkToSensorML());
		
		return dest;
	}
	
	public SensorConnectionProperty unmarshalSensorConnectionProperty(SensorConnectionPropertyType src) throws MissingADESchemaException {
		SensorConnectionProperty dest = new SensorConnectionProperty();
		
		if (src.isSetSensorConnection())
			dest.setSensorConnection(unmarshalSensorConnection(src.getSensorConnection()));
		
		return dest;
	}
}
