package org.citygml.ade.dynamizer.bind;

import javax.xml.bind.JAXBElement;

import org.citygml.ade.dynamizer._1.AbstractTimeseriesPropertyType;
import org.citygml.ade.dynamizer._1.AbstractTimeseriesType;
import org.citygml.ade.dynamizer._1.AtomicTimeseriesType;
import org.citygml.ade.dynamizer._1.CompositeTimeseriesType;
import org.citygml.ade.dynamizer._1.DynamizerPropertyType;
import org.citygml.ade.dynamizer._1.DynamizerType;
import org.citygml.ade.dynamizer._1.ObjectFactory;
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
import org.citygml4j.builder.jaxb.marshal.citygml.ade.ADEMarshallerHelper;
import org.citygml4j.model.citygml.ade.binding.ADEMarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEModelObject;
import org.citygml4j.util.jaxb.JAXBMapper;
import org.w3._1999.xlink.ActuateType;
import org.w3._1999.xlink.ShowType;
import org.w3._1999.xlink.TypeType;

import net.opengis.gml.TimePositionType;

public class DynamizerADEMarshaller implements ADEMarshaller {
	private final ObjectFactory factory = new ObjectFactory();
	private final net.opengis.gml.ObjectFactory gmlFactory = new net.opengis.gml.ObjectFactory();
	private final JAXBMapper<JAXBElement<?>> elementMapper;
	private final JAXBMapper<Object> typeMapper;

	private ADEMarshallerHelper helper;

	public DynamizerADEMarshaller() {
		elementMapper = JAXBMapper.<JAXBElement<?>>create()
				.with(DynamizersPropertyElement.class, this::createDynamizersProperty)
				.with(Dynamizer.class, this::createDynamizer)
				.with(SensorConnection.class, this::createSensorConnection)
				.with(TimeseriesComponent.class, this::createTimeseriesComponent)
				.with(AtomicTimeseries.class, this::createAtomicTimeseries)
				.with(CompositeTimeseries.class, this::createCompositeTimeseries);
		
		typeMapper = JAXBMapper.create()
				.with(TimeseriesProperty.class, this::marshalTimeseriesProperty)
				.with(AtomicTimeseries.class, this::marshalAtomicTimeseries)
				.with(CompositeTimeseries.class, this::marshalCompositeTimeseries)
				.with(GMLTimePosition.class, this::marshalGMLTimePostiion)	
				.with(Dynamizer.class, this::marshalDynamizer)
				.with(DynamizerProperty.class, this::marshalDynamizerProperty)
				.with(SensorConnection.class, this::marshalSensorConnection)
				.with(SensorConnectionProperty.class, this::marshalSensorConnectionProperty)
				.with(TimeseriesComponent.class, this::marshalTimeseriesComponent)
				.with(TimeseriesComponentProperty.class, this::marshalTimeseriesComponentProperty);
	}

	@Override
	public void setADEMarshallerHelper(ADEMarshallerHelper helper) {
		this.helper = helper;
	}

	@Override
	public JAXBElement<?> marshalJAXBElement(ADEModelObject src) {
		return elementMapper.apply(src);
	}

	@Override
	public Object marshal(ADEModelObject src) {
		return typeMapper.apply(src);
	}

	public void marshalAbstractTimeseries(AbstractTimeseries src, AbstractTimeseriesType dest) {
		helper.getGMLMarshaller().marshalAbstractFeature(src, dest);
	}
	
	@SuppressWarnings("unchecked")
	public AbstractTimeseriesPropertyType marshalTimeseriesProperty(TimeseriesProperty src) {
		AbstractTimeseriesPropertyType dest = factory.createAbstractTimeseriesPropertyType();

		if (src.isSetTimeseries()) {
			JAXBElement<?> elem = helper.getJAXBMarshaller().marshalJAXBElement(src.getTimeseries());
			if (elem != null && elem.getValue() instanceof AbstractTimeseriesType)
				dest.setAbstractTimeseries((JAXBElement<? extends AbstractTimeseriesType>)elem);
		}

		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(TypeType.fromValue(src.getType().getValue()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(ShowType.fromValue(src.getShow().getValue()));

		if (src.isSetActuate())
			dest.setActuate(ActuateType.fromValue(src.getActuate().getValue()));

		return dest;		
	}
	
	public AtomicTimeseriesType marshalAtomicTimeseries(AtomicTimeseries src) {
		AtomicTimeseriesType dest = factory.createAtomicTimeseriesType();
		marshalAbstractTimeseries(src, dest);
		
		if (src.isSetDynamicDataDR()) 
			dest.setDynamicDataDR(src.getDynamicDataDR());
		
		if (src.isSetDynamicDataTVP())
			dest.setDynamicDataTVP(src.getDynamicDataTVP());
		
		if (src.isSetObservationData())
			dest.setObservationData(src.getObservationData());
		
		return dest;
	}
	
	public CompositeTimeseriesType marshalCompositeTimeseries(CompositeTimeseries src) {
		CompositeTimeseriesType dest = factory.createCompositeTimeseriesType();
		marshalAbstractTimeseries(src, dest);
		
		if (src.isSetTimeseriesComponent()) {
			for (TimeseriesComponentProperty timeseriesComponentProperty : src.getTimeseriesComponent())
				dest.getComponent().add(marshalTimeseriesComponentProperty(timeseriesComponentProperty));
		}			
		
		return dest;
	}
	
	public TimePositionType marshalGMLTimePostiion(GMLTimePosition src) {
		TimePositionType dest = gmlFactory.createTimePositionType();
		
		if (src.isSetValue())
			dest.getValue().add(src.getValue());
		
		if (src.isSetFrame())
			dest.setFrame(src.getFrame());
		
		if (src.isSetCalendarEraName())
			dest.setCalendarEraName(src.getCalendarEraName());
		
		if (src.isSetIndeterminatePosition())
			dest.setIndeterminatePosition(src.getIndeterminatePosition());
		
		return dest;
	}
	
	public DynamizerType marshalDynamizer(Dynamizer src) {
		DynamizerType dest = factory.createDynamizerType();
		helper.getCore200Marshaller().marshalAbstractCityObject(src, dest);
		
		if (src.isSetAttributeRef())
			dest.setAttributeRef(src.getAttributeRef());
		
		if (src.isSetStartTime()) {
			dest.setStartTime(marshalGMLTimePostiion(src.getStartTime()));
		}
		
		if (src.isSetEndTime()) {
			dest.setEndTime(marshalGMLTimePostiion(src.getEndTime()));
		}
		
		if (src.isSetLinkToSensor())
			dest.setLinkToSensor(marshalSensorConnectionProperty(src.getLinkToSensor()));
		
		if (src.isSetDynamicData())
			dest.setDynamicData(marshalTimeseriesProperty(src.getDynamicData()));
		
		return dest;
	}
	
	public DynamizerPropertyType marshalDynamizerProperty(DynamizerProperty src) {
		DynamizerPropertyType dest = factory.createDynamizerPropertyType();

		if (src.isSetDynamizer())
			dest.setDynamizer(marshalDynamizer(src.getDynamizer()));

		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(TypeType.fromValue(src.getType().getValue()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(ShowType.fromValue(src.getShow().getValue()));

		if (src.isSetActuate())
			dest.setActuate(ActuateType.fromValue(src.getActuate().getValue()));

		return dest;	
	}
	
	public SensorConnectionType marshalSensorConnection(SensorConnection src) {
		SensorConnectionType dest = factory.createSensorConnectionType();
		
		if (src.isSetSensorId())
			dest.setSensorID(src.getSensorId());
		
		if (src.isSetServiceType())
			dest.setServiceType(src.getServiceType());
		
		if (src.isSetLinkToObservation())
			dest.setLinkToObservation(src.getLinkToObservation());
		
		if (src.isSetLinkToSensorML())
			dest.setLinkToSensorML(src.getLinkToSensorML());

		return dest;
	}
	
	public SensorConnectionPropertyType marshalSensorConnectionProperty(SensorConnectionProperty src) {
		SensorConnectionPropertyType dest = factory.createSensorConnectionPropertyType();

		if (src.isSetSensorConnection())
			dest.setSensorConnection(marshalSensorConnection(src.getSensorConnection()));

		return dest;
	}
	
	public TimeseriesComponentType marshalTimeseriesComponent(TimeseriesComponent src) {
		TimeseriesComponentType dest = factory.createTimeseriesComponentType();
		helper.getGMLMarshaller().marshalAbstractFeature(src, dest);
		
		if (src.isSetRepetitions())
			dest.setRepetitions(src.getRepetitions());
		
		if (src.isSetAdditionalGap())
			dest.setAdditionalGap(src.getAdditionalGap());
		
		if (src.isSetTimeseries())
			dest.setTimeseries(marshalTimeseriesProperty(src.getTimeseries()));
		
		return dest;
	}
	
	public TimeseriesComponentPropertyType marshalTimeseriesComponentProperty(TimeseriesComponentProperty src) {
		TimeseriesComponentPropertyType dest = factory.createTimeseriesComponentPropertyType();

		if (src.isSetTimeseriesComponent()) 
			dest.setTimeseriesComponent(marshalTimeseriesComponent(src.getTimeseriesComponent()));

		if (src.isSetRemoteSchema())
			dest.setRemoteSchema(src.getRemoteSchema());

		if (src.isSetType())
			dest.setType(TypeType.fromValue(src.getType().getValue()));

		if (src.isSetHref())
			dest.setHref(src.getHref());

		if (src.isSetRole())
			dest.setRole(src.getRole());

		if (src.isSetArcrole())
			dest.setArcrole(src.getArcrole());

		if (src.isSetTitle())
			dest.setTitle(src.getTitle());

		if (src.isSetShow())
			dest.setShow(ShowType.fromValue(src.getShow().getValue()));

		if (src.isSetActuate())
			dest.setActuate(ActuateType.fromValue(src.getActuate().getValue()));

		return dest;		
	}
	
	public JAXBElement<?> createDynamizersProperty(DynamizersPropertyElement src) {
		return factory.createDynamizers(marshalDynamizerProperty(src.getValue()));
	}
	
	public JAXBElement<?> createDynamizer(Dynamizer src) {
		return factory.createDynamizer(marshalDynamizer(src));
	}
	
	public JAXBElement<?> createSensorConnection(SensorConnection src) {
		return factory.createSensorConnection(marshalSensorConnection(src));
	}
	
	public JAXBElement<?> createTimeseriesComponent(TimeseriesComponent src) {
		return factory.createTimeseriesComponent(marshalTimeseriesComponent(src));
	}
	
	public JAXBElement<?> createAtomicTimeseries(AtomicTimeseries src) {
		return factory.createAtomicTimeseries(marshalAtomicTimeseries(src));
	}
	
	public JAXBElement<?> createCompositeTimeseries(CompositeTimeseries src) {
		return factory.createCompositeTimeseries(marshalCompositeTimeseries(src));
	}
}
