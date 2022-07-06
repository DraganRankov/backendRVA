
drop table if exists proizvodjac cascade;
drop table if exists racun cascade;
drop table if exists proizvod cascade;
drop table if exists stavka_racuna cascade;
drop sequence if exists proizvod_seq;
drop sequence if exists proizvodjac_seq;
drop sequence if exists racun_seq;
drop sequence if exists stavka_racuna_seq;
create table proizvodjac (
    id integer not null,
    naziv  varchar(50),
    adresa varchar(200),
    kontakt varchar(100)
    );
    
create table racun (
    id integer not null,
    datum date,
    nacin_placanja varchar(200)
    );
    
    
create table proizvod (
    id integer not null,
    naziv varchar(50),
    proizvodjac integer not null
    );
    
create table stavka_racuna (
    id integer not null,
    redni_broj integer not null,
    kolicina numeric,
    jedinica_mere varchar(50),
    cena numeric,
    racun integer not null,
    proizvod integer not null
    );
    
    alter table proizvodjac
    add constraint pk_proizvodjac primary key (id);
    
    alter table racun
    add constraint pk_racun primary key (id);
    
    alter table proizvod
    add constraint pk_proizvod primary key (id);
    
    alter table stavka_racuna
    add constraint pk_stavka_racuna primary key (id);
    
    alter table proizvod
    add constraint fk_proizvod_proizvodjac foreign key  (proizvodjac)
    references proizvodjac(id);
    
    alter table stavka_racuna
    add constraint fk_stavka_racuna_racun foreign key  (racun)
    references racun(id);
    
    alter table stavka_racuna
    add constraint fk_stavka_racuna_proizvod foreign key  (proizvod)
    references proizvod(id);
    
    
    create index idx_pk_proizvod on proizvod(id);
    create index idx_pk_racun on racun(id);
    create index idx_pk_proizvodjac on proizvodjac(id);
    create index idx_pk_stavka_racuna on stavka_racuna(id);
    
    create index idx_fk_proizvod_proizvodjac on proizvod(proizvodjac);
    create index idx_fk_stavka_racuna_racun on stavka_racuna(racun);
    create index idx_fk_stavka_racuna_proizvod on stavka_racuna(proizvod);
    
    create sequence if not exists proizvodjac_seq increment 1 start 1;
    create sequence if not exists proizvod_seq increment 1 start 1;
    create sequence if not exists racun_seq increment 1 start 1;
    create sequence if not exists stavka_racuna_seq increment 1 start 1;
 