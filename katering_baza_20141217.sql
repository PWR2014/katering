--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: jednostka_miary; Type: DOMAIN; Schema: public; Owner: katering
--

CREATE DOMAIN jednostka_miary AS character varying(4)
	CONSTRAINT jednostka_miary_check CHECK (((VALUE)::text = ANY ((ARRAY['szt'::character varying, 'kg'::character varying, 'm'::character varying, 'l'::character varying])::text[])));


ALTER DOMAIN public.jednostka_miary OWNER TO katering;

--
-- Name: kod_pocztowy; Type: DOMAIN; Schema: public; Owner: katering
--

CREATE DOMAIN kod_pocztowy AS character(6)
	CONSTRAINT kod_pocztowy_check CHECK ((VALUE ~ similar_escape('[0-9][0-9]-[0-9][0-9][0-9]'::text, NULL::text)));


ALTER DOMAIN public.kod_pocztowy OWNER TO katering;

--
-- Name: pesel; Type: DOMAIN; Schema: public; Owner: katering
--

CREATE DOMAIN pesel AS character(11)
	CONSTRAINT pesel_check CHECK ((VALUE ~ similar_escape('[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'::text, NULL::text)));


ALTER DOMAIN public.pesel OWNER TO katering;

--
-- Name: uprawnienie; Type: DOMAIN; Schema: public; Owner: katering
--

CREATE DOMAIN uprawnienie AS character varying(10)
	CONSTRAINT uprawnienie_check CHECK (((VALUE)::text = ANY ((ARRAY['Manager'::character varying, 'Kucharz'::character varying, 'Obsługa'::character varying, 'Dostawca'::character varying])::text[])));


ALTER DOMAIN public.uprawnienie OWNER TO katering;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: Adresy; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "Adresy" (
    id bigint NOT NULL,
    "miejscowość" character varying(30),
    kod_pocztowy kod_pocztowy,
    ulica character varying(50),
    nr_domu character varying(5),
    nr_lokalu character varying(5)
);


ALTER TABLE public."Adresy" OWNER TO katering;

--
-- Name: Adresy_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Adresy_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Adresy_id_seq" OWNER TO katering;

--
-- Name: Adresy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Adresy_id_seq" OWNED BY "Adresy".id;


--
-- Name: StanSurowca; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "StanSurowca" (
    id bigint NOT NULL,
    id_surowca bigint NOT NULL,
    zmiana_stanu double precision NOT NULL,
    czas_zmiany timestamp without time zone DEFAULT now()
);


ALTER TABLE public."StanSurowca" OWNER TO katering;

--
-- Name: BierzącyStanSurowców; Type: VIEW; Schema: public; Owner: katering
--

CREATE VIEW "BierzącyStanSurowców" AS
 SELECT "StanSurowca".id_surowca,
    sum("StanSurowca".zmiana_stanu) AS "bierzący_stan"
   FROM "StanSurowca"
  GROUP BY "StanSurowca".id_surowca;


ALTER TABLE public."BierzącyStanSurowców" OWNER TO katering;

--
-- Name: Dostawy; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "Dostawy" (
    id bigint NOT NULL,
    "id_zamówienia" bigint,
    id_kierowcy bigint,
    termin_wyjazdu timestamp without time zone,
    termin_powrotu timestamp without time zone,
    zrealizowano boolean DEFAULT false
);


ALTER TABLE public."Dostawy" OWNER TO katering;

--
-- Name: Dostawy_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Dostawy_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Dostawy_id_seq" OWNER TO katering;

--
-- Name: Dostawy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Dostawy_id_seq" OWNED BY "Dostawy".id;


--
-- Name: Klienci; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "Klienci" (
    id bigint NOT NULL,
    nazwa character varying(100),
    "imię" character varying(20) NOT NULL,
    nazwisko character varying(40) NOT NULL,
    telefon character varying(20) NOT NULL,
    adres bigint
);


ALTER TABLE public."Klienci" OWNER TO katering;

--
-- Name: Klienci_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Klienci_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Klienci_id_seq" OWNER TO katering;

--
-- Name: Klienci_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Klienci_id_seq" OWNED BY "Klienci".id;


--
-- Name: PozycjeZamówień; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "PozycjeZamówień" (
    id bigint NOT NULL,
    "id_zamówienia" bigint,
    id_produktu bigint,
    "ilość" integer NOT NULL,
    "wartość_netto" double precision,
    "wartość_vat" double precision,
    "wartość_brutto" double precision,
    CONSTRAINT "PozycjeZamówień_ilość_check" CHECK (("ilość" >= 0))
);


ALTER TABLE public."PozycjeZamówień" OWNER TO katering;

--
-- Name: PozycjeZamówień_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "PozycjeZamówień_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."PozycjeZamówień_id_seq" OWNER TO katering;

--
-- Name: PozycjeZamówień_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "PozycjeZamówień_id_seq" OWNED BY "PozycjeZamówień".id;


--
-- Name: Pracownicy; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "Pracownicy" (
    id integer NOT NULL,
    login character varying(30) NOT NULL,
    haslo character varying(40) NOT NULL,
    "imię" character varying(20) NOT NULL,
    nazwisko character varying(40) NOT NULL,
    pesel pesel NOT NULL,
    telefon character varying(20),
    CONSTRAINT "Pracownicy_hasło_check" CHECK ((character_length((haslo)::text) >= 8))
);


ALTER TABLE public."Pracownicy" OWNER TO katering;

--
-- Name: Pracownicy_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Pracownicy_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Pracownicy_id_seq" OWNER TO katering;

--
-- Name: Pracownicy_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Pracownicy_id_seq" OWNED BY "Pracownicy".id;


--
-- Name: produkty; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE produkty (
    id bigint NOT NULL,
    nazwa_produktu character varying(100) NOT NULL,
    cena double precision NOT NULL,
    opis text
);


ALTER TABLE public.produkty OWNER TO katering;

--
-- Name: Produkty_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Produkty_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Produkty_id_seq" OWNER TO katering;

--
-- Name: Produkty_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Produkty_id_seq" OWNED BY produkty.id;


--
-- Name: Składniki; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "Składniki" (
    id bigint NOT NULL,
    id_produktu bigint,
    id_surowca bigint,
    ilosc double precision NOT NULL
);


ALTER TABLE public."Składniki" OWNER TO katering;

--
-- Name: Składniki_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Składniki_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Składniki_id_seq" OWNER TO katering;

--
-- Name: Składniki_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Składniki_id_seq" OWNED BY "Składniki".id;


--
-- Name: StanProduktów; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "StanProduktów" (
    id bigint,
    nazwa_produktu character varying(100),
    opis text,
    "ilość" double precision
);


ALTER TABLE public."StanProduktów" OWNER TO katering;

--
-- Name: StanSurowca_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "StanSurowca_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."StanSurowca_id_seq" OWNER TO katering;

--
-- Name: StanSurowca_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "StanSurowca_id_seq" OWNED BY "StanSurowca".id;


--
-- Name: Surowce; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "Surowce" (
    id bigint NOT NULL,
    nazwa_surowca character varying(100) NOT NULL,
    jednostka_miary jednostka_miary NOT NULL
);


ALTER TABLE public."Surowce" OWNER TO katering;

--
-- Name: Surowce_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Surowce_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Surowce_id_seq" OWNER TO katering;

--
-- Name: Surowce_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Surowce_id_seq" OWNED BY "Surowce".id;


--
-- Name: Uprawnienie; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE "Uprawnienie" (
    id bigint NOT NULL,
    id_pracownika integer NOT NULL,
    typ_uprawnienia uprawnienie NOT NULL
);


ALTER TABLE public."Uprawnienie" OWNER TO katering;

--
-- Name: Uprawnienie_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Uprawnienie_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Uprawnienie_id_seq" OWNER TO katering;

--
-- Name: Uprawnienie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Uprawnienie_id_seq" OWNED BY "Uprawnienie".id;


--
-- Name: zamowienia; Type: TABLE; Schema: public; Owner: katering; Tablespace: 
--

CREATE TABLE zamowienia (
    id bigint NOT NULL,
    data_zamowienia date NOT NULL,
    termin_realizacji timestamp without time zone NOT NULL,
    przyjal bigint DEFAULT 1,
    klient bigint,
    ilosc integer,
    imie text,
    nazwisko text,
    adres text,
    id_produktu integer
);


ALTER TABLE public.zamowienia OWNER TO katering;

--
-- Name: Zamówienia_id_seq; Type: SEQUENCE; Schema: public; Owner: katering
--

CREATE SEQUENCE "Zamówienia_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Zamówienia_id_seq" OWNER TO katering;

--
-- Name: Zamówienia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: katering
--

ALTER SEQUENCE "Zamówienia_id_seq" OWNED BY zamowienia.id;


--
-- Name: cena; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW cena AS
 SELECT (p.cena * (z.ilosc)::double precision) AS suma_brutto,
    z.id_produktu
   FROM (produkty p
     JOIN zamowienia z ON ((p.id = z.id_produktu)));


ALTER TABLE public.cena OWNER TO postgres;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Adresy" ALTER COLUMN id SET DEFAULT nextval('"Adresy_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Dostawy" ALTER COLUMN id SET DEFAULT nextval('"Dostawy_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Klienci" ALTER COLUMN id SET DEFAULT nextval('"Klienci_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "PozycjeZamówień" ALTER COLUMN id SET DEFAULT nextval('"PozycjeZamówień_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Pracownicy" ALTER COLUMN id SET DEFAULT nextval('"Pracownicy_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Składniki" ALTER COLUMN id SET DEFAULT nextval('"Składniki_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "StanSurowca" ALTER COLUMN id SET DEFAULT nextval('"StanSurowca_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Surowce" ALTER COLUMN id SET DEFAULT nextval('"Surowce_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Uprawnienie" ALTER COLUMN id SET DEFAULT nextval('"Uprawnienie_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY produkty ALTER COLUMN id SET DEFAULT nextval('"Produkty_id_seq"'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: katering
--

ALTER TABLE ONLY zamowienia ALTER COLUMN id SET DEFAULT nextval('"Zamówienia_id_seq"'::regclass);


--
-- Name: Adresy_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Adresy"
    ADD CONSTRAINT "Adresy_pkey" PRIMARY KEY (id);


--
-- Name: Dostawy_id_zamówienia_key; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Dostawy"
    ADD CONSTRAINT "Dostawy_id_zamówienia_key" UNIQUE ("id_zamówienia");


--
-- Name: Dostawy_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Dostawy"
    ADD CONSTRAINT "Dostawy_pkey" PRIMARY KEY (id);


--
-- Name: Klienci_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Klienci"
    ADD CONSTRAINT "Klienci_pkey" PRIMARY KEY (id);


--
-- Name: PozycjeZamówień_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "PozycjeZamówień"
    ADD CONSTRAINT "PozycjeZamówień_pkey" PRIMARY KEY (id);


--
-- Name: Pracownicy_login_key; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Pracownicy"
    ADD CONSTRAINT "Pracownicy_login_key" UNIQUE (login);


--
-- Name: Pracownicy_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Pracownicy"
    ADD CONSTRAINT "Pracownicy_pkey" PRIMARY KEY (id);


--
-- Name: Produkty_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY produkty
    ADD CONSTRAINT "Produkty_pkey" PRIMARY KEY (id);


--
-- Name: Składniki_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Składniki"
    ADD CONSTRAINT "Składniki_pkey" PRIMARY KEY (id);


--
-- Name: StanSurowca_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "StanSurowca"
    ADD CONSTRAINT "StanSurowca_pkey" PRIMARY KEY (id);


--
-- Name: Surowce_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Surowce"
    ADD CONSTRAINT "Surowce_pkey" PRIMARY KEY (id);


--
-- Name: Uprawnienie_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Uprawnienie"
    ADD CONSTRAINT "Uprawnienie_pkey" PRIMARY KEY (id);


--
-- Name: Zamówienia_pkey; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY zamowienia
    ADD CONSTRAINT "Zamówienia_pkey" PRIMARY KEY (id);


--
-- Name: unikalnysurowiec; Type: CONSTRAINT; Schema: public; Owner: katering; Tablespace: 
--

ALTER TABLE ONLY "Surowce"
    ADD CONSTRAINT unikalnysurowiec UNIQUE (nazwa_surowca, jednostka_miary);


--
-- Name: _RETURN; Type: RULE; Schema: public; Owner: katering
--

CREATE RULE "_RETURN" AS
    ON SELECT TO "StanProduktów" DO INSTEAD  SELECT produkty.id,
    produkty.nazwa_produktu,
    produkty.opis,
    min((COALESCE("BierzącyStanSurowców"."bierzący_stan", (0)::double precision) / "Składniki".ilosc)) AS "ilość"
   FROM ((("Składniki"
     JOIN "Surowce" ON (("Składniki".id_surowca = "Surowce".id)))
     JOIN produkty ON (("Składniki".id_produktu = produkty.id)))
     LEFT JOIN "BierzącyStanSurowców" ON (("Składniki".id_surowca = "BierzącyStanSurowców".id_surowca)))
  GROUP BY produkty.id;


--
-- Name: Dostawy_id_kierowcy_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Dostawy"
    ADD CONSTRAINT "Dostawy_id_kierowcy_fkey" FOREIGN KEY (id_kierowcy) REFERENCES "Pracownicy"(id);


--
-- Name: Dostawy_id_zamówienia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Dostawy"
    ADD CONSTRAINT "Dostawy_id_zamówienia_fkey" FOREIGN KEY ("id_zamówienia") REFERENCES zamowienia(id);


--
-- Name: Klienci_adres_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Klienci"
    ADD CONSTRAINT "Klienci_adres_fkey" FOREIGN KEY (adres) REFERENCES "Adresy"(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: PozycjeZamówień_id_produktu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "PozycjeZamówień"
    ADD CONSTRAINT "PozycjeZamówień_id_produktu_fkey" FOREIGN KEY (id_produktu) REFERENCES produkty(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: PozycjeZamówień_id_zamówienia_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "PozycjeZamówień"
    ADD CONSTRAINT "PozycjeZamówień_id_zamówienia_fkey" FOREIGN KEY ("id_zamówienia") REFERENCES zamowienia(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Składniki_id_produktu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Składniki"
    ADD CONSTRAINT "Składniki_id_produktu_fkey" FOREIGN KEY (id_produktu) REFERENCES produkty(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: Składniki_id_surowca_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Składniki"
    ADD CONSTRAINT "Składniki_id_surowca_fkey" FOREIGN KEY (id_surowca) REFERENCES "Surowce"(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: StanSurowca_id_surowca_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "StanSurowca"
    ADD CONSTRAINT "StanSurowca_id_surowca_fkey" FOREIGN KEY (id_surowca) REFERENCES "Surowce"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Uprawnienie_id_pracownika_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY "Uprawnienie"
    ADD CONSTRAINT "Uprawnienie_id_pracownika_fkey" FOREIGN KEY (id_pracownika) REFERENCES "Pracownicy"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: Zamówienia_klient_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY zamowienia
    ADD CONSTRAINT "Zamówienia_klient_fkey" FOREIGN KEY (klient) REFERENCES "Klienci"(id) ON UPDATE CASCADE;


--
-- Name: Zamówienia_przyjął_fkey; Type: FK CONSTRAINT; Schema: public; Owner: katering
--

ALTER TABLE ONLY zamowienia
    ADD CONSTRAINT "Zamówienia_przyjął_fkey" FOREIGN KEY (przyjal) REFERENCES "Pracownicy"(id) ON UPDATE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

