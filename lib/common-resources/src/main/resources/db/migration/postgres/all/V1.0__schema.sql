SET SCHEMA 'diego_tech';
CREATE TABLE pessoa (
  id              BIGSERIAL,
  nome            VARCHAR(250)   NOT NULL,
  idade           SMALLINT       NOT NULL,
  sexo            CHAR(1)        NOT NULL DEFAULT 'M',
  estado_civil_id BIGINT         NOT NULL,
  estado          CHAR(2)        NOT NULL,
  dependentes     SMALLINT       NOT NULL DEFAULT 0,
  renda           NUMERIC(19, 2) NOT NULL,
  aprovado        BOOLEAN        NOT NULL DEFAULT FALSE,
  limite          VARCHAR(255)   NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_pessoa_nome UNIQUE (nome)
);

CREATE TABLE estado_civil (
  id        BIGSERIAL,
  descricao VARCHAR(250) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_estado_civil_descricao UNIQUE (descricao)
);