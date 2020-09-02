drop table if exists layer1;
create table layer1(id int, name varchar);
insert into layer1 values(1,'A'),(2,'B');

drop table if exists layer2;
create table layer2(id int, count_ int, name varchar);
insert into layer2 values(10,2,'x'),(20,4,'y');


-- //@DELIMITER |
create or replace function test(layer_name anyelement, field_name text, object_id text)
   returns setof anyelement
   language plpgsql
as $function$
   begin 
       return query execute format('
           select 
            *
           from
            %s
           where
            %s = cast($1 as int4)'
    , pg_typeof(layer_name), field_name)
    using object_id;
end;
$function$
|
-- //@DELIMITER ;
