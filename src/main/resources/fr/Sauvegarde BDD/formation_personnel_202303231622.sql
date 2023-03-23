-- public.formation_personnel definition

-- Drop table

-- DROP TABLE public.formation_personnel;

CREATE TABLE public.formation_personnel (
	id_personnel int4 NOT NULL,
	id_formation int4 NOT NULL,
	CONSTRAINT formation_personnel_pkey PRIMARY KEY (id_personnel, id_formation)
);


-- public.formation_personnel foreign keys

ALTER TABLE public.formation_personnel ADD CONSTRAINT formation_personnel_id_formation_fkey FOREIGN KEY (id_formation) REFERENCES public.formation(id_formation);
ALTER TABLE public.formation_personnel ADD CONSTRAINT formation_personnel_id_personnel_fkey FOREIGN KEY (id_personnel) REFERENCES public.personnel(id_personnel);

INSERT INTO public.formation_personnel (id_personnel,id_formation) VALUES
	 (1,1),
	 (1,2),
	 (1,3);
