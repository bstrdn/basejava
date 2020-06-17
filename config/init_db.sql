create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table public.resume
    owner to postgres;



create table public.contact
(
    id          serial   not null
        constraint contact_pk
            primary key,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade,
    type        text     not null,
    value       text     not null
);

alter table public.contact
    owner to postgres;

create unique index contact_resume_uuid_type_uindex
    on contact (resume_uuid, type);
