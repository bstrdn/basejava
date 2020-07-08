create table resume
(
    uuid      varchar not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

create table public.contact
(
    id          serial   not null
        constraint contact_pk
            primary key,
    resume_uuid varchar not null
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_resume_uuid_type_uindex
    on contact (resume_uuid, type);


create table text_section
(
    id serial not null
        constraint text_section_pk
            primary key,
    resume_uuid varchar not null
        constraint text_section_resume_uuid_fk
            references resume
            on delete cascade,
    type text not null,
    value text not null
);

create unique index text_section_id_uindex
    on text_section (id);

