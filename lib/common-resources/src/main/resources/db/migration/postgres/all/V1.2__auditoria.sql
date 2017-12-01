SET SCHEMA 'diego_tech';

CREATE TABLE auditoria (
  id          BIGSERIAL    NOT NULL,
  username    VARCHAR(255) NOT NULL,
  entry_point VARCHAR(255) NOT NULL,
  ip          VARCHAR(255) NOT NULL,
  CONSTRAINT pk_auditoria PRIMARY KEY (id)
);

DO $create_auditoria_relation$
DECLARE
  v_table_name      VARCHAR(2000);
  v_root_schema     VARCHAR(2000) := 'diego_tech';
  v_add_column_stmt VARCHAR(2000) := 'ALTER TABLE {{v_root_schema}}.{{v_table_name}} ADD auditoria_id BIGINT NOT NULL';
  v_add_fk_stmt     VARCHAR(2000) := 'ALTER TABLE {{v_root_schema}}.{{v_table_name}} ADD CONSTRAINT fk_{{v_table_name}}_aud FOREIGN KEY (auditoria_id) REFERENCES {{v_root_schema}}.auditoria (id)';
  v_tmp_stmt        VARCHAR(2000);
BEGIN
  FOR v_table_name IN (SELECT table_name
                       FROM information_schema.tables
                       WHERE
                         table_schema = v_root_schema
                         AND table_name NOT IN ('schema_version', 'auditoria')) LOOP


    v_tmp_stmt := v_add_column_stmt;
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_root_schema}}', v_root_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_table_name}}', v_table_name);
    EXECUTE v_tmp_stmt;

    v_tmp_stmt := v_add_fk_stmt;
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_root_schema}}', v_root_schema);
    v_tmp_stmt := replace(v_tmp_stmt, '{{v_table_name}}', v_table_name);
    EXECUTE v_tmp_stmt;

  END LOOP;
END;
$create_auditoria_relation$ LANGUAGE plpgsql;