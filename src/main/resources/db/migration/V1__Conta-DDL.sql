CREATE TABLE tb_conta (
  id 		       	SERIAL           NOT NULL PRIMARY KEY ,
  nome            	VARCHAR(150)     NOT NULL             ,
  data_vencimento  	TIMESTAMP		 NOT NULL             ,
  data_pagamento	TIMESTAMP		 NOT NULL             ,
  valor_original	NUMERIC          NOT NULL			  ,
  valor_corrigido	NUMERIC          NOT NULL			  ,
  qtde_dias_atraso	BIGINT           NOT NULL  			  ,
  multa            	VARCHAR(10)      NOT NULL             ,
  juros_dia         VARCHAR(10)      NOT NULL             ,
  ativo            	BOOLEAN          NOT NULL
)