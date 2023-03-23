-- public.formation definition

-- Drop table

-- DROP TABLE public.formation;

CREATE TABLE formation (
	id_formation int4 NOT NULL DEFAULT nextval('formation_id_seq'::regclass),
	nom varchar(50) NULL,
	CONSTRAINT formation_pkey PRIMARY KEY (id_formation)
);

INSERT INTO public.formation (nom) VALUES
	 ('CDA'),
	 ('Commerce'),
	 ('Maçonnerie');
