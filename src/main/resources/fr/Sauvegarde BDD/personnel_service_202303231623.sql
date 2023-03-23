-- public.personnel_service definition

-- Drop table

-- DROP TABLE public.personnel_service;

CREATE TABLE public.personnel_service (
	id_personnel int4 NOT NULL,
	id_service int4 NOT NULL,
	CONSTRAINT personnel_service_pkey PRIMARY KEY (id_personnel, id_service)
);


-- public.personnel_service foreign keys

ALTER TABLE public.personnel_service ADD CONSTRAINT personnel_service_id_personnel_fkey FOREIGN KEY (id_personnel) REFERENCES public.personnel(id_personnel);
ALTER TABLE public.personnel_service ADD CONSTRAINT personnel_service_id_service_fkey FOREIGN KEY (id_service) REFERENCES public.service(id_service);

INSERT INTO public.personnel_service (id_personnel,id_service) VALUES
	 (2,1),
	 (2,2);
