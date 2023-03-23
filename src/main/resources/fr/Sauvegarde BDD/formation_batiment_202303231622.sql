-- public.formation_batiment definition

-- Drop table

-- DROP TABLE public.formation_batiment;

CREATE TABLE public.formation_batiment (
	id_batiment int4 NOT NULL,
	id_formation int4 NOT NULL,
	CONSTRAINT formation_batiment_pkey PRIMARY KEY (id_batiment, id_formation)
);


-- public.formation_batiment foreign keys

ALTER TABLE public.formation_batiment ADD CONSTRAINT formation_batiment_id_batiment_fkey FOREIGN KEY (id_batiment) REFERENCES public.batiment(id_batiment);
ALTER TABLE public.formation_batiment ADD CONSTRAINT formation_batiment_id_formation_fkey FOREIGN KEY (id_formation) REFERENCES public.formation(id_formation);

INSERT INTO public.formation_batiment (id_batiment,id_formation) VALUES
	 (1,1),
	 (2,2),
	 (3,3),
	 (4,3),
	 (5,3);
