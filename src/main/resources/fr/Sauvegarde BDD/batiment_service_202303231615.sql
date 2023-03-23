-- public.batiment_service definition

-- Drop table

-- DROP TABLE public.batiment_service;

CREATE TABLE public.batiment_service (
	id_batiment int4 NOT NULL,
	id_service int4 NOT NULL,
	CONSTRAINT batiment_service_pkey PRIMARY KEY (id_batiment, id_service)
);


-- public.batiment_service foreign keys

ALTER TABLE public.batiment_service ADD CONSTRAINT batiment_service_id_batiment_fkey FOREIGN KEY (id_batiment) REFERENCES public.batiment(id_batiment);
ALTER TABLE public.batiment_service ADD CONSTRAINT batiment_service_id_service_fkey FOREIGN KEY (id_service) REFERENCES public.service(id_service);

INSERT INTO public.batiment_service (id_batiment,id_service) VALUES
	 (6,1),
	 (7,2);
