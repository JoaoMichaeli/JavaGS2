CREATE TABLE tbl_cadastros_ev(
id_cadastro_ev INTEGER GENERATED BY DEFAULT AS IDENTITY,
email VARCHAR(100) ,
senha VARCHAR(16) ,
situacao NUMBER(1) DEFAULT 1,

CONSTRAINT tbl_cadastroev_pk PRIMARY KEY (id_cadastro_ev),
CONSTRAINT tbl_cadastroev_un UNIQUE (email)
);

CREATE TABLE tbl_placas_ev(
id_cadastro_ev INTEGER,
id_placa INTEGER GENERATED BY DEFAULT AS IDENTITY,
modelo VARCHAR(50),
potencia_nominal DECIMAL(10,2),
cidade VARCHAR(100),
situacao NUMBER(1) DEFAULT 1,

CONSTRAINT tbl_placasev_pk PRIMARY KEY (id_placa),
CONSTRAINT tbl_placas_fk FOREIGN KEY (id_cadastro_ev)
    REFERENCES tbl_cadastros_ev(id_cadastro_ev)
);

