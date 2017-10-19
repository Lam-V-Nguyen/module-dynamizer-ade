-- This document was automatically created by the ADE-Manager tool of 3DCityDB (https://www.3dcitydb.org) on 2017-10-16 17:23:38 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
-- *********************************  Disable Versioning  *********************************** 
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

exec DBMS_WM.DisableVersioning('dym_AtomicTimeseries,dym_CompositeTimeseries,dym_Dynamizer,dym_SensorConnection,dym_Timeseries,dym_TimeseriesComponent,dym_cityobject',true, true);
