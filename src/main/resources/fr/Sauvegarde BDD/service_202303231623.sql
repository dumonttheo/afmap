-- public.service definition

-- Drop table

-- DROP TABLE public.service;

CREATE TABLE public.service (
	id_service int4 NOT NULL DEFAULT nextval('service_id_seq'::regclass),
	nom varchar(255) NULL,
	CONSTRAINT service_pkey PRIMARY KEY (id_service)
);

INSERT INTO public.service (nom) VALUES
	 ('Accueil'),
	 ('Self');
