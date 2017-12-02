SET SCHEMA 'diego_tech';

INSERT into auditoria(id, username, entry_point, ip) VALUES
(0,'SYSTEM','SQL','127.0.0.1');

--

INSERT INTO credito(descricao,aprovado,auditoria_id) values
  ('reprovado pela política de crédito',false,0),
  ('renda baixa',false,0),
  ('entre 500 - 1000',true,0),
  ('entre 1000 - 1500',true,0),
  ('superior 2000',true,0);

--

INSERT INTO estado_civil(descricao,auditoria_id) values
  ('solteiro',0),
  ('casado',0),
  ('divorciado',0),
  ('viuva',0);