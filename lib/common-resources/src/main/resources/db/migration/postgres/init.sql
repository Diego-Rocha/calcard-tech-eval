-- create schemas

DROP SCHEMA IF EXISTS diego_tech CASCADE;
CREATE SCHEMA IF NOT EXISTS diego_tech;

DROP SCHEMA IF EXISTS diego_tech_temporal CASCADE;
CREATE SCHEMA IF NOT EXISTS diego_tech_temporal;

ALTER ROLE diego_tech SET search_path TO diego_tech;