-- public.batiment definition

-- Drop table

-- DROP TABLE public.batiment;

CREATE TABLE public.batiment (
	id_batiment int4 NOT NULL DEFAULT nextval('batiment_id_seq'::regclass),
	numero varchar(4) NULL,
	nom_batiment varchar(50) NULL,
	x1 numeric NULL,
	x2 numeric NULL,
	width numeric NULL,
	heigth numeric NULL,
	color varchar(255) NULL,
	allpoints _float4 NULL,
	CONSTRAINT batiment_pkey PRIMARY KEY (id_batiment)
);

INSERT INTO public.batiment (numero,nom_batiment,x1,x2,width,heigth,color,allpoints) VALUES
	 ('9','CDA',20.70,14.76,60.00,44.00,'FFFF00',NULL),
	 ('9','Commerce',25.00,14.76,84,44.00,'FFDAB9',NULL),
	 ('25','MaconMain',20.05,51.6,100,110,'C19A6B',NULL),
	 ('25','MaconDepot',NULL,NULL,NULL,NULL,'C19A6B','{399.0,678.0,500.0,679.0,500.0,730.0,399.0,730.0}'),
	 ('25','MaconDepotBis',NULL,NULL,NULL,NULL,'C19A6B','{180.0,680.0,318.0,710.0,306.0,774.0,180.0,774.0}'),
	 ('3','Accueil',NULL,NULL,NULL,NULL,'C19A6B','{1051.0,184.0,1083.0,169.0,1086.0,175.0,1099.0,175.0,1099.0,171.0,1119.0,171.0,1119.0,197.0,1110.0,197.0,1110.0,219.0,1119.0,219.0,1119.0,280.0,1105.0,280.0,1105.0,289.0,1072.0,289.0,1072.0,220.0,1050.0,220.0}'),
	 ('7','AES',43.75,8.25,42.5,149,'C19A6B',NULL);
