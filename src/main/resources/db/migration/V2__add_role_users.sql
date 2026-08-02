alter table if exists public.users
    add column if not exists role varchar(50);

update public.users
set role = 'CLIENT'
WHERE role IS NULL;

alter table if exists public.users
    alter column role set not null;
