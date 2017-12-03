SET SCHEMA 'diego_tech';
--

INSERT INTO credito(descricao,aprovado) values
  ('reprovado pela política de crédito',false),
  ('renda baixa',false),
  ('entre 100 - 500',true),
  ('entre 500 - 1000',true),
  ('entre 1000 - 1500',true),
  ('entre 1500 - 2000',true),
  ('superior 2000',true);

--

INSERT INTO estado_civil(descricao) values
  ('solteiro'),
  ('casado'),
  ('divorciado'),
  ('viuva');