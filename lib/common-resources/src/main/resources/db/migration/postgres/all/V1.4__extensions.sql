SET SCHEMA 'diego_tech';
DO $create_extensions$
BEGIN
  IF NOT EXISTS(
      SELECT 1
      FROM pg_available_extensions
      WHERE NAME = 'unaccent'
  )
  THEN
    RAISE EXCEPTION 'unaccent não disponivel';
  END IF;
  DROP EXTENSION IF EXISTS unaccent;
  CREATE EXTENSION IF NOT EXISTS unaccent;
END
$create_extensions$ LANGUAGE plpgsql;