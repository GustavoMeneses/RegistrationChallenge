-- Database: registration

-- DROP DATABASE registration;

CREATE DATABASE registration
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.tb_profile

-- DROP TABLE public.tb_profile;

CREATE TABLE public.tb_profile
(
    id bigint NOT NULL,
    profile character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tb_profile_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_profile
    OWNER to postgres;

-- Table: public.tb_user

-- DROP TABLE public.tb_user;

CREATE TABLE public.tb_user
(
    id bigint NOT NULL,
    login character varying(200) COLLATE pg_catalog."default" NOT NULL,
    password character varying(200) COLLATE pg_catalog."default" NOT NULL,
    id_profile bigint NOT NULL,
    CONSTRAINT tb_usuario_pkey PRIMARY KEY (id),
    CONSTRAINT fk_profile FOREIGN KEY (id_profile)
        REFERENCES public.tb_profile (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.tb_user
    OWNER to postgres;

-- Table: public.tb_address

-- DROP TABLE public.tb_address;

CREATE TABLE public.tb_address
(
    id bigint NOT NULL,
    zip_code character varying COLLATE pg_catalog."default" NOT NULL,
    public_place character varying COLLATE pg_catalog."default" NOT NULL,
    neighborhood character varying COLLATE pg_catalog."default" NOT NULL,
    city character varying COLLATE pg_catalog."default" NOT NULL,
    fu character varying COLLATE pg_catalog."default" NOT NULL,
    complement character varying COLLATE pg_catalog."default",
    CONSTRAINT tb_address_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_address
    OWNER to postgres;

-- Table: public.tb_email

-- DROP TABLE public.tb_email;

CREATE TABLE public.tb_email
(
    id bigint NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tb_email_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_email
    OWNER to postgres;

-- Table: public.tb_phone

-- DROP TABLE public.tb_phone;

CREATE TABLE public.tb_phone
(
    id bigint NOT NULL,
    phone character varying COLLATE pg_catalog."default" NOT NULL,
    phone_type bigint NOT NULL,
    CONSTRAINT tb_phone_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.tb_phone
    OWNER to postgres;

-- Table: public.tb_person

-- DROP TABLE public.tb_person;

CREATE TABLE public.tb_person
(
    id bigint NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    social_security_number character varying COLLATE pg_catalog."default" NOT NULL,
    id_address bigint NOT NULL,
    CONSTRAINT tb_person_pkey PRIMARY KEY (id),
    CONSTRAINT fk_address FOREIGN KEY (id_address)
        REFERENCES public.tb_address (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.tb_person
    OWNER to postgres;

-- Table: public.tb_person_email

-- DROP TABLE public.tb_person_email;

CREATE TABLE public.tb_person_email
(
    id bigint NOT NULL,
    id_person bigint NOT NULL,
    id_email bigint NOT NULL,
    CONSTRAINT tb_person_email_pkey PRIMARY KEY (id),
    CONSTRAINT fk_email FOREIGN KEY (id_email)
        REFERENCES public.tb_email (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_person FOREIGN KEY (id_person)
        REFERENCES public.tb_person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.tb_person_email
    OWNER to postgres;

-- Table: public.tb_person_phone

-- DROP TABLE public.tb_person_phone;

CREATE TABLE public.tb_person_phone
(
    id bigint NOT NULL,
    id_person bigint NOT NULL,
    id_phone bigint NOT NULL,
    CONSTRAINT tb_person_phone_pkey PRIMARY KEY (id),
    CONSTRAINT fk_person FOREIGN KEY (id_person)
        REFERENCES public.tb_person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_phone FOREIGN KEY (id_phone)
        REFERENCES public.tb_phone (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.tb_person_phone
    OWNER to postgres;

-- SEQUENCE: public.address_seq

-- DROP SEQUENCE public.address_seq;

CREATE SEQUENCE public.address_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.address_seq
    OWNER TO postgres;

-- SEQUENCE: public.email_seq

-- DROP SEQUENCE public.email_seq;

CREATE SEQUENCE public.email_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.email_seq
    OWNER TO postgres;

-- SEQUENCE: public.person_email_seq

-- DROP SEQUENCE public.person_email_seq;

CREATE SEQUENCE public.person_email_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.person_email_seq
    OWNER TO postgres;

-- SEQUENCE: public.person_phone_seq

-- DROP SEQUENCE public.person_phone_seq;

CREATE SEQUENCE public.person_phone_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.person_phone_seq
    OWNER TO postgres;

-- SEQUENCE: public.person_seq

-- DROP SEQUENCE public.person_seq;

CREATE SEQUENCE public.person_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.person_seq
    OWNER TO postgres;

-- SEQUENCE: public.phone_seq

-- DROP SEQUENCE public.phone_seq;

CREATE SEQUENCE public.phone_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.phone_seq
    OWNER TO postgres;

INSERT INTO public.tb_profile(
    id, profile)
    VALUES (1, 'admin');

INSERT INTO public.tb_profile(
    id, profile)
    VALUES (2, 'comum');

INSERT INTO public.tb_user(
    id, login, password, id_profile)
    VALUES (1, 'admin', '7c4a8d09ca3762af61e59520943dc26494f8941b', 1);

INSERT INTO public.tb_user(
    id, login, password, id_profile)
    VALUES (2, 'comum', '7c4a8d09ca3762af61e59520943dc26494f8941b', 2);