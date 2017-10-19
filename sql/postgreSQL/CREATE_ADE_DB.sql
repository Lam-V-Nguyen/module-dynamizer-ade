-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2017-10-16 17:23:38 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- ***********************************  Create tables ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- dym_AtomicTimeseries 
-- -------------------------------------------------------------------- 
CREATE TABLE dym_AtomicTimeseries
(
    ID INTEGER NOT NULL,
    dynamicDataTVP TEXT,
    dynamicDataDR TEXT,
    observationData TEXT,
    PRIMARY KEY (ID)
);

-- -------------------------------------------------------------------- 
-- dym_CompositeTimeseries 
-- -------------------------------------------------------------------- 
CREATE TABLE dym_CompositeTimeseries
(
    ID INTEGER NOT NULL,
    PRIMARY KEY (ID)
);

-- -------------------------------------------------------------------- 
-- dym_Dynamizer 
-- -------------------------------------------------------------------- 
CREATE TABLE dym_Dynamizer
(
    ID INTEGER NOT NULL,
    dynamicData_ID INTEGER,
    linkToSensor_ID INTEGER,
    cityobject_dynamizers_ID INTEGER,
    startTime_frame VARCHAR(1000),
    startTime_calendarEraName VARCHAR(1000),
    startTime_indeterminatePosit VARCHAR(1000),
    startTime VARCHAR(1000),
    endTime_frame VARCHAR(1000),
    endTime_calendarEraName VARCHAR(1000),
    endTime_indeterminatePositio VARCHAR(1000),
    endTime VARCHAR(1000),
    attributeRef VARCHAR(1000),
    PRIMARY KEY (ID)
);

-- -------------------------------------------------------------------- 
-- dym_SensorConnection 
-- -------------------------------------------------------------------- 
CREATE TABLE dym_SensorConnection
(
    ID INTEGER NOT NULL,
    sensorLocation_ID INTEGER,
    sensorID VARCHAR(1000),
    serviceType VARCHAR(1000),
    linkToObservation VARCHAR(1000),
    linkToSensorML VARCHAR(1000),
    PRIMARY KEY (ID)
);

-- -------------------------------------------------------------------- 
-- dym_Timeseries 
-- -------------------------------------------------------------------- 
CREATE TABLE dym_Timeseries
(
    ID INTEGER NOT NULL,
    PRIMARY KEY (ID)
);

-- -------------------------------------------------------------------- 
-- dym_TimeseriesComponent 
-- -------------------------------------------------------------------- 
CREATE TABLE dym_TimeseriesComponent
(
    ID INTEGER NOT NULL,
    timeseries_ID INTEGER,
    CompositeTimese_component_ID INTEGER,
    repetitions INTEGER,
    additionalGap VARCHAR(1000),
    PRIMARY KEY (ID)
);

-- -------------------------------------------------------------------- 
-- dym_cityobject 
-- -------------------------------------------------------------------- 
CREATE TABLE dym_cityobject
(
    ID INTEGER NOT NULL,
    PRIMARY KEY (ID)
);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Create foreign keys  ******************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- dym_AtomicTimeseries 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_AtomicTimeseries
    ADD CONSTRAINT dym_AtomicTimeseries_FK FOREIGN KEY (ID) REFERENCES dym_Timeseries (ID);

-- -------------------------------------------------------------------- 
-- dym_CompositeTimeseries 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_CompositeTimeseries
    ADD CONSTRAINT dym_CompositeTimeseries_FK FOREIGN KEY (ID) REFERENCES dym_Timeseries (ID);

-- -------------------------------------------------------------------- 
-- dym_Dynamizer 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_Dynamizer
    ADD CONSTRAINT dym_Dynamizer_FK FOREIGN KEY (ID) REFERENCES cityobject (ID);

ALTER TABLE dym_Dynamizer
    ADD CONSTRAINT dym_Dynamizer_dynamicDa_FK FOREIGN KEY (dynamicData_ID) REFERENCES dym_Timeseries (ID);

ALTER TABLE dym_Dynamizer
    ADD CONSTRAINT dym_Dynamizer_linkToSen_FK FOREIGN KEY (linkToSensor_ID) REFERENCES dym_SensorConnection (ID);

ALTER TABLE dym_Dynamizer
    ADD CONSTRAINT dym_Dynami_cityob_dynam_FK FOREIGN KEY (cityobject_dynamizers_ID) REFERENCES dym_cityobject (ID);

-- -------------------------------------------------------------------- 
-- dym_SensorConnection 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_SensorConnection
    ADD CONSTRAINT dym_SensorCon_sensorLoc_FK FOREIGN KEY (sensorLocation_ID) REFERENCES cityobject (ID);

-- -------------------------------------------------------------------- 
-- dym_Timeseries 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_Timeseries
    ADD CONSTRAINT dym_Timeseries_FK FOREIGN KEY (ID) REFERENCES cityobject (ID);

-- -------------------------------------------------------------------- 
-- dym_TimeseriesComponent 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_TimeseriesComponent
    ADD CONSTRAINT dym_TimeseriesComponent_FK FOREIGN KEY (ID) REFERENCES cityobject (ID);

ALTER TABLE dym_TimeseriesComponent
    ADD CONSTRAINT dym_Timeserie_timeserie_FK FOREIGN KEY (timeseries_ID) REFERENCES dym_Timeseries (ID);

ALTER TABLE dym_TimeseriesComponent
    ADD CONSTRAINT dym_Timese_Compos_compo_FK FOREIGN KEY (CompositeTimese_component_ID) REFERENCES dym_CompositeTimeseries (ID);

-- -------------------------------------------------------------------- 
-- dym_cityobject 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_cityobject
    ADD CONSTRAINT dym_cityobject_FK FOREIGN KEY (ID) REFERENCES cityobject (ID);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Create Indexes  ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- dym_Dynamizer 
-- -------------------------------------------------------------------- 
CREATE INDEX dym_Dynamizer_dynamicD_FKX ON dym_Dynamizer
    USING btree
    (
      dynamicData_ID ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX dym_Dynamizer_linkToSe_FKX ON dym_Dynamizer
    USING btree
    (
      linkToSensor_ID ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX dym_Dynami_cityo_dynam_FKX ON dym_Dynamizer
    USING btree
    (
      cityobject_dynamizers_ID ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- dym_SensorConnection 
-- -------------------------------------------------------------------- 
CREATE INDEX dym_SensorCon_sensorLo_FKX ON dym_SensorConnection
    USING btree
    (
      sensorLocation_ID ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- -------------------------------------------------------------------- 
-- dym_TimeseriesComponent 
-- -------------------------------------------------------------------- 
CREATE INDEX dym_Timeserie_timeseri_FKX ON dym_TimeseriesComponent
    USING btree
    (
      timeseries_ID ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

CREATE INDEX dym_Timese_Compo_compo_FKX ON dym_TimeseriesComponent
    USING btree
    (
      CompositeTimese_component_ID ASC NULLS LAST
    )   WITH (FILLFACTOR = 90);

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Create Sequences  *********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

CREATE SEQUENCE dym_SensorConnection_SEQ
INCREMENT BY 1
MINVALUE 0
MAXVALUE 2147483647
START WITH 1
CACHE 1
NO CYCLE
OWNED BY NONE;


