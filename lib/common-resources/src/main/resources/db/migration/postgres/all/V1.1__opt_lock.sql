SET SCHEMA 'diego_tech';

DO $create_opt_lock_column$
DECLARE
  v_table_name      VARCHAR(2000);
  v_root_schema     VARCHAR(2000) := 'diego_tech';
  v_add_column_stmt VARCHAR(2000) := 'ALTER TABLE {{v_root_schema}}.{{v_table_name}} ADD version BIGINT NOT NULL DEFAULT 0';
  v_tmp_stmt        VARCHAR(2000);
BEGIN
  FOR v_table_name IN (SELECT table_name
                       FROM information_schema.tables
                       WHERE
                         table_schema = v_root_schema
                         AND table_name NOT IN ('schema_version')) LOOP

    v_tmp_stmt := v_add_column_stmt;
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_root_schema}}', v_root_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_table_name}}', v_table_name);
    EXECUTE v_tmp_stmt;

  END LOOP;
END;
$create_opt_lock_column$ LANGUAGE plpgsql;