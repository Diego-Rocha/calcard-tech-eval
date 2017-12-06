SET SCHEMA 'diego_tech';

CREATE TABLE estado_civil (
  id        BIGSERIAL,
  descricao VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_estado_civil UNIQUE (descricao)
);

CREATE TABLE credito (
  id        BIGSERIAL,
  descricao VARCHAR(255) NOT NULL,
  aprovado  BOOLEAN      NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  CONSTRAINT uk_nivel_limite_credito UNIQUE (descricao)
);

CREATE TABLE pessoa (
  id              BIGSERIAL,
  nome            VARCHAR(255)                                                    NOT NULL,
  cpf             VARCHAR(11)                                                     NOT NULL,
  idade           INTEGER CHECK (idade BETWEEN 1 AND 200)                         NOT NULL,
  sexo            CHAR(1) CHECK (sexo IN ('M', 'F'))                              NOT NULL DEFAULT 'M',
  estado_civil_id BIGINT                                                          NOT NULL,
  estado          VARCHAR(2)                                                      NOT NULL,
  dependentes     INTEGER                                                         NOT NULL DEFAULT 0,
  renda           NUMERIC(19, 2)                                                  NOT NULL,
  credito_id      BIGINT                                                          NOT NULL,
  limite_credito  NUMERIC(19, 2)                                                  NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  CONSTRAINT uk_pessoa UNIQUE (cpf),
  CONSTRAINT fk_pessoa_estado_civil FOREIGN KEY (estado_civil_id) REFERENCES estado_civil (id),
  CONSTRAINT fk_pessoa_credito FOREIGN KEY (credito_id) REFERENCES credito (id)
);