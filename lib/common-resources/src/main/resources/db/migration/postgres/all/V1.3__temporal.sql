SET SCHEMA 'diego_tech_temporal';

DO $create_temporal_tables$
DECLARE
  v_table_name          VARCHAR(2000);
  v_root_schema         VARCHAR(2000) := 'diego_tech';
  v_temporal_schema     VARCHAR(2000) := 'diego_tech_temporal';
  v_add_column_stmt     VARCHAR(2000) := 'ALTER TABLE {{v_root_schema}}.{{v_table_name}} ADD COLUMN sys_period tstzrange NOT NULL;';
  v_copy_table_stmt     VARCHAR(2000) := 'CREATE TABLE {{v_temporal_schema}}.z_{{v_table_name}} (LIKE {{v_root_schema}}.{{v_table_name}});';
  v_create_trigger_stmt VARCHAR(2000) := 'CREATE TRIGGER z_{{v_table_name}}_trigger BEFORE INSERT OR UPDATE OR DELETE ON {{v_root_schema}}.{{v_table_name}} FOR EACH ROW EXECUTE PROCEDURE versioning(''sys_period'', ''{{v_temporal_schema}}.z_{{v_table_name}}'', TRUE);';
  v_tmp_stmt            VARCHAR(2000);
BEGIN
  IF NOT EXISTS(
      SELECT 1
      FROM pg_available_extensions
      WHERE NAME = 'temporal_tables'
  )
  THEN
    RETURN;
  END IF;

  DROP EXTENSION IF EXISTS temporal_tables;
  CREATE EXTENSION IF NOT EXISTS temporal_tables;

  FOR v_table_name IN (SELECT table_name
                       FROM information_schema.tables
                       WHERE
                         table_schema = v_root_schema
                         AND table_name <> 'schema_version') LOOP


    v_tmp_stmt := v_add_column_stmt;
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_root_schema}}', v_root_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_temporal_schema}}', v_temporal_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_table_name}}', v_table_name);
    EXECUTE v_tmp_stmt;

    v_tmp_stmt := v_copy_table_stmt;
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_root_schema}}', v_root_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_temporal_schema}}', v_temporal_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_table_name}}', v_table_name);
    EXECUTE v_tmp_stmt;

    v_tmp_stmt := v_create_trigger_stmt;
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_root_schema}}', v_root_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_temporal_schema}}', v_temporal_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_table_name}}', v_table_name);
    EXECUTE v_tmp_stmt;

  END LOOP;
END
$create_temporal_tables$ LANGUAGE plpgsql;