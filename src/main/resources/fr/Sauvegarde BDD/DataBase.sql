--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-03-30 14:20:37

DROP DATABASE afmap;
--
-- TOC entry 3392 (class 1262 OID 16761)
-- Name: afmap; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE afmap WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'French_France.1252';


ALTER DATABASE afmap OWNER TO postgres;

\connect afmap

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 215 (class 1259 OID 16773)
-- Name: batiment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.batiment (
    id_batiment integer NOT NULL,
    numero character varying(4),
    nom_batiment character varying(50),
    color character varying(255),
    allpoints real[],
    is_formation boolean DEFAULT false NOT NULL
);


ALTER TABLE public.batiment OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16772)
-- Name: batiment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.batiment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.batiment_id_seq OWNER TO postgres;

--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 214
-- Name: batiment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.batiment_id_seq OWNED BY public.batiment.id_batiment;


--
-- TOC entry 225 (class 1259 OID 16849)
-- Name: batiment_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.batiment_service (
    id_batiment integer NOT NULL,
    id_service integer NOT NULL
);


ALTER TABLE public.batiment_service OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16791)
-- Name: formation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formation (
    id_formation integer NOT NULL,
    nom character varying(50)
);


ALTER TABLE public.formation OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16804)
-- Name: formation_batiment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formation_batiment (
    id_batiment integer NOT NULL,
    id_formation integer NOT NULL
);


ALTER TABLE public.formation_batiment OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16790)
-- Name: formation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.formation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.formation_id_seq OWNER TO postgres;

--
-- TOC entry 3395 (class 0 OID 0)
-- Dependencies: 218
-- Name: formation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.formation_id_seq OWNED BY public.formation.id_formation;


--
-- TOC entry 223 (class 1259 OID 16819)
-- Name: formation_personnel; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formation_personnel (
    id_personnel integer NOT NULL,
    id_formation integer NOT NULL
);


ALTER TABLE public.formation_personnel OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16782)
-- Name: personnel; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personnel (
    id_personnel integer NOT NULL,
    nom character varying(255),
    prenom character varying(255),
    numerotelephone character varying(255),
    mail character varying(255)
);


ALTER TABLE public.personnel OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16781)
-- Name: personnel_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personnel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.personnel_id_seq OWNER TO postgres;

--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 216
-- Name: personnel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personnel_id_seq OWNED BY public.personnel.id_personnel;


--
-- TOC entry 224 (class 1259 OID 16834)
-- Name: personnel_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personnel_service (
    id_personnel integer NOT NULL,
    id_service integer NOT NULL
);


ALTER TABLE public.personnel_service OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16798)
-- Name: service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.service (
    id_service integer NOT NULL,
    nom character varying(255)
);


ALTER TABLE public.service OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16797)
-- Name: service_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.service_id_seq OWNER TO postgres;

--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 220
-- Name: service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.service_id_seq OWNED BY public.service.id_service;


--
-- TOC entry 3204 (class 2604 OID 16776)
-- Name: batiment id_batiment; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batiment ALTER COLUMN id_batiment SET DEFAULT nextval('public.batiment_id_seq'::regclass);


--
-- TOC entry 3207 (class 2604 OID 16794)
-- Name: formation id_formation; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation ALTER COLUMN id_formation SET DEFAULT nextval('public.formation_id_seq'::regclass);


--
-- TOC entry 3206 (class 2604 OID 16785)
-- Name: personnel id_personnel; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnel ALTER COLUMN id_personnel SET DEFAULT nextval('public.personnel_id_seq'::regclass);


--
-- TOC entry 3208 (class 2604 OID 16801)
-- Name: service id_service; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service ALTER COLUMN id_service SET DEFAULT nextval('public.service_id_seq'::regclass);


--
-- TOC entry 3376 (class 0 OID 16773)
-- Dependencies: 215
-- Data for Name: batiment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.batiment VALUES (2, '9', 'Commerce', 'FFDAB9', '{{1055,41},{1102,41},{1102,7},{1055,72}}', true);
INSERT INTO public.batiment VALUES (3, '25', 'Batiment Principale Maçon', 'C19A6B', '{{408,525},{308,526},{308,634},{408,634}}', true);
INSERT INTO public.batiment VALUES (7, '16', 'Self', 'C19A6B', '{{1168,386},{1233,386},{1233,530},{1168,530}}', false);
INSERT INTO public.batiment VALUES (4, '25', 'Aire de Maçonnerie ', 'C19A6B', '{{399,678},{500,679},{500,730},{399,730}}', true);
INSERT INTO public.batiment VALUES (5, '25', 'MaconDepotBis', 'C19A6B', '{{180,680},{318,710},{306,774},{180,774}}', true);
INSERT INTO public.batiment VALUES (6, '3', 'Accueil', 'C19A6B', '{{1051,184},{1083,169},{1086,175},{1099,175},{1099,171},{1119,171},{1119,197},{1110,197},{1110,219},{1119,219},{1119,280},{1105,280},{1105,289},{1072,289},{1072,220},{1050,220}}', false);
INSERT INTO public.batiment VALUES (1, '9', 'CDA', 'B380B3', '{{318,150},{394,150},{394,195},{318,195}}', true);
INSERT INTO public.batiment VALUES (15, '25', 'Depot Maçonnerie', '800080', '{{134,805},{231,809},{231,839},{133,835}}', true);


--
-- TOC entry 3386 (class 0 OID 16849)
-- Dependencies: 225
-- Data for Name: batiment_service; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.batiment_service VALUES (6, 1);
INSERT INTO public.batiment_service VALUES (7, 2);


--
-- TOC entry 3380 (class 0 OID 16791)
-- Dependencies: 219
-- Data for Name: formation; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.formation VALUES (1, 'CDA');
INSERT INTO public.formation VALUES (2, 'Commerce');
INSERT INTO public.formation VALUES (3, 'Maçonnerie');


--
-- TOC entry 3383 (class 0 OID 16804)
-- Dependencies: 222
-- Data for Name: formation_batiment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.formation_batiment VALUES (1, 1);
INSERT INTO public.formation_batiment VALUES (2, 2);
INSERT INTO public.formation_batiment VALUES (3, 3);
INSERT INTO public.formation_batiment VALUES (4, 3);
INSERT INTO public.formation_batiment VALUES (5, 3);
INSERT INTO public.formation_batiment VALUES (15, 3);


--
-- TOC entry 3384 (class 0 OID 16819)
-- Dependencies: 223
-- Data for Name: formation_personnel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.formation_personnel VALUES (1, 1);
INSERT INTO public.formation_personnel VALUES (1, 2);
INSERT INTO public.formation_personnel VALUES (8, 3);


--
-- TOC entry 3378 (class 0 OID 16782)
-- Dependencies: 217
-- Data for Name: personnel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.personnel VALUES (1, 'Esperce', 'Ludo', '0123456789', 'ludo@gmail.com');
INSERT INTO public.personnel VALUES (8, 'Lopez', 'Antonin', '0123456789', 'antonin@gmail.com');


--
-- TOC entry 3385 (class 0 OID 16834)
-- Dependencies: 224
-- Data for Name: personnel_service; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3382 (class 0 OID 16798)
-- Dependencies: 221
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.service VALUES (1, 'Accueil');
INSERT INTO public.service VALUES (2, 'Self');


--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 214
-- Name: batiment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.batiment_id_seq', 15, true);


--
-- TOC entry 3399 (class 0 OID 0)
-- Dependencies: 218
-- Name: formation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.formation_id_seq', 15, true);


--
-- TOC entry 3400 (class 0 OID 0)
-- Dependencies: 216
-- Name: personnel_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personnel_id_seq', 9, true);


--
-- TOC entry 3401 (class 0 OID 0)
-- Dependencies: 220
-- Name: service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.service_id_seq', 5, true);


--
-- TOC entry 3210 (class 2606 OID 16780)
-- Name: batiment batiment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batiment
    ADD CONSTRAINT batiment_pkey PRIMARY KEY (id_batiment);


--
-- TOC entry 3224 (class 2606 OID 16853)
-- Name: batiment_service batiment_service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batiment_service
    ADD CONSTRAINT batiment_service_pkey PRIMARY KEY (id_batiment, id_service);


--
-- TOC entry 3218 (class 2606 OID 16808)
-- Name: formation_batiment formation_batiment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation_batiment
    ADD CONSTRAINT formation_batiment_pkey PRIMARY KEY (id_batiment, id_formation);


--
-- TOC entry 3220 (class 2606 OID 16823)
-- Name: formation_personnel formation_personnel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation_personnel
    ADD CONSTRAINT formation_personnel_pkey PRIMARY KEY (id_personnel, id_formation);


--
-- TOC entry 3214 (class 2606 OID 16796)
-- Name: formation formation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation
    ADD CONSTRAINT formation_pkey PRIMARY KEY (id_formation);


--
-- TOC entry 3212 (class 2606 OID 16789)
-- Name: personnel personnel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnel
    ADD CONSTRAINT personnel_pkey PRIMARY KEY (id_personnel);


--
-- TOC entry 3222 (class 2606 OID 16838)
-- Name: personnel_service personnel_service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnel_service
    ADD CONSTRAINT personnel_service_pkey PRIMARY KEY (id_personnel, id_service);


--
-- TOC entry 3216 (class 2606 OID 16803)
-- Name: service service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pkey PRIMARY KEY (id_service);


--
-- TOC entry 3231 (class 2606 OID 16854)
-- Name: batiment_service batiment_service_id_batiment_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batiment_service
    ADD CONSTRAINT batiment_service_id_batiment_fkey FOREIGN KEY (id_batiment) REFERENCES public.batiment(id_batiment);


--
-- TOC entry 3232 (class 2606 OID 16859)
-- Name: batiment_service batiment_service_id_service_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.batiment_service
    ADD CONSTRAINT batiment_service_id_service_fkey FOREIGN KEY (id_service) REFERENCES public.service(id_service);


--
-- TOC entry 3225 (class 2606 OID 16809)
-- Name: formation_batiment formation_batiment_id_batiment_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation_batiment
    ADD CONSTRAINT formation_batiment_id_batiment_fkey FOREIGN KEY (id_batiment) REFERENCES public.batiment(id_batiment);


--
-- TOC entry 3226 (class 2606 OID 16814)
-- Name: formation_batiment formation_batiment_id_formation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation_batiment
    ADD CONSTRAINT formation_batiment_id_formation_fkey FOREIGN KEY (id_formation) REFERENCES public.formation(id_formation);


--
-- TOC entry 3227 (class 2606 OID 16829)
-- Name: formation_personnel formation_personnel_id_formation_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation_personnel
    ADD CONSTRAINT formation_personnel_id_formation_fkey FOREIGN KEY (id_formation) REFERENCES public.formation(id_formation);


--
-- TOC entry 3228 (class 2606 OID 16824)
-- Name: formation_personnel formation_personnel_id_personnel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formation_personnel
    ADD CONSTRAINT formation_personnel_id_personnel_fkey FOREIGN KEY (id_personnel) REFERENCES public.personnel(id_personnel);


--
-- TOC entry 3229 (class 2606 OID 16839)
-- Name: personnel_service personnel_service_id_personnel_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnel_service
    ADD CONSTRAINT personnel_service_id_personnel_fkey FOREIGN KEY (id_personnel) REFERENCES public.personnel(id_personnel);


--
-- TOC entry 3230 (class 2606 OID 16844)
-- Name: personnel_service personnel_service_id_service_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personnel_service
    ADD CONSTRAINT personnel_service_id_service_fkey FOREIGN KEY (id_service) REFERENCES public.service(id_service);


-- Completed on 2023-03-30 14:20:38

--
-- PostgreSQL database dump complete
--

