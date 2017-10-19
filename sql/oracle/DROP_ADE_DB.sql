-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2017-10-16 17:23:38 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- ***********************************  Drop foreign keys ********************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- dym_AtomicTimeseries 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_AtomicTimeseries
    DROP CONSTRAINT dym_AtomicTimeseries_FK;

-- -------------------------------------------------------------------- 
-- dym_CompositeTimeseries 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_CompositeTimeseries
    DROP CONSTRAINT dym_CompositeTimeseries_FK;

-- -------------------------------------------------------------------- 
-- dym_Dynamizer 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_Dynamizer
    DROP CONSTRAINT dym_Dynamizer_FK;

ALTER TABLE dym_Dynamizer
    DROP CONSTRAINT dym_Dynamizer_dynamicDa_FK;

ALTER TABLE dym_Dynamizer
    DROP CONSTRAINT dym_Dynamizer_linkToSen_FK;

ALTER TABLE dym_Dynamizer
    DROP CONSTRAINT dym_Dynami_cityob_dynam_FK;

-- -------------------------------------------------------------------- 
-- dym_SensorConnection 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_SensorConnection
    DROP CONSTRAINT dym_SensorCon_sensorLoc_FK;

-- -------------------------------------------------------------------- 
-- dym_Timeseries 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_Timeseries
    DROP CONSTRAINT dym_Timeseries_FK;

-- -------------------------------------------------------------------- 
-- dym_TimeseriesComponent 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_TimeseriesComponent
    DROP CONSTRAINT dym_TimeseriesComponent_FK;

ALTER TABLE dym_TimeseriesComponent
    DROP CONSTRAINT dym_Timeserie_timeserie_FK;

ALTER TABLE dym_TimeseriesComponent
    DROP CONSTRAINT dym_Timese_Compos_compo_FK;

-- -------------------------------------------------------------------- 
-- dym_cityobject 
-- -------------------------------------------------------------------- 
ALTER TABLE dym_cityobject
    DROP CONSTRAINT dym_cityobject_FK;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- ***********************************  Drop tables  ************************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- -------------------------------------------------------------------- 
-- dym_AtomicTimeseries 
-- -------------------------------------------------------------------- 
DROP TABLE dym_AtomicTimeseries;

-- -------------------------------------------------------------------- 
-- dym_CompositeTimeseries 
-- -------------------------------------------------------------------- 
DROP TABLE dym_CompositeTimeseries;

-- -------------------------------------------------------------------- 
-- dym_Dynamizer 
-- -------------------------------------------------------------------- 
DROP TABLE dym_Dynamizer;

-- -------------------------------------------------------------------- 
-- dym_SensorConnection 
-- -------------------------------------------------------------------- 
DROP TABLE dym_SensorConnection;

-- -------------------------------------------------------------------- 
-- dym_Timeseries 
-- -------------------------------------------------------------------- 
DROP TABLE dym_Timeseries;

-- -------------------------------------------------------------------- 
-- dym_TimeseriesComponent 
-- -------------------------------------------------------------------- 
DROP TABLE dym_TimeseriesComponent;

-- -------------------------------------------------------------------- 
-- dym_cityobject 
-- -------------------------------------------------------------------- 
DROP TABLE dym_cityobject;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Drop Sequences  ************************************* 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

DROP SEQUENCE dym_SensorConnection_SEQ;

PURGE RECYCLEBIN;
