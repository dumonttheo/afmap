-- public.personnel definition

-- Drop table

-- DROP TABLE public.personnel;

CREATE TABLE public.personnel (
	id_personnel int4 NOT NULL DEFAULT nextval('personnel_id_seq'::regclass),
	nom varchar(255) NULL,
	prenom varchar(255) NULL,
	numerotelephone varchar(255) NULL,
	mail varchar(255) NULL,
	CONSTRAINT personnel_pkey PRIMARY KEY (id_personnel)
);

INSERT INTO public.personnel (nom,prenom,numerotelephone,mail) VALUES
	 ('Esperce','Ludo','0612345678','ludo@gmail.com'),
	 ('Micheline','Micheline','0123456789','micheline.micheline@gmail.com');
