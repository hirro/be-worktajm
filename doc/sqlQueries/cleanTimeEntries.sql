update tps.person set active_time_entry=null where active_time_entry!=null;
truncate table tps.time_entry;
commit;
