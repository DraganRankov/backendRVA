-- Proizvodjac podaci
insert into proizvodjac(id ,naziv, adresa, kontakt) values
(nextval('proizvodjac_seq'), 'Logitech','Logitech Corp','logitech@yahoo.com'),
(nextval('proizvodjac_seq'), 'Razer', 'Razer Corp', 'razer@gmail.com'),
(nextval('proizvodjac_seq'), 'Samsung', 'Samsung Corp', 'samsung@gmail.com'),
(nextval('proizvodjac_seq'), 'Sony', 'Sony Corp', 'sonycustomer@yahoo.com'),
(nextval('proizvodjac_seq'), 'Huawei', 'Huawei Corp', 'huawei@gmail.com');

-- proizvod podaci

insert into proizvod(id,naziv,proizvodjac) values
(nextval('proizvod_seq'), 'Galaxy S21',3),
(nextval('proizvod_seq'), 'Slusalice',1),
(nextval('proizvod_seq'), 'Smart TV',4),
(nextval('proizvod_seq'), 'Gaming stolica',2),
(nextval('proizvod_seq'), 'Smart watch',5);

-- racun podaci

insert into racun(id, datum, nacin_placanja) values
(nextval('racun_seq'),to_date('01.03.2022.','dd.mm.yyyy.'),'gotovina'),
(nextval('racun_seq'),to_date('11.02.2022.','dd.mm.yyyy.'),'kartica'),
(nextval('racun_seq'),to_date('11.03.2021.','dd.mm.yyyy.'),'gotovina'),
(nextval('racun_seq'),to_date('01.01.2022.','dd.mm.yyyy.'),'kartica'),
(nextval('racun_seq'),to_date('31.12.2021.','dd.mm.yyyy.'),'gotovina');
 
-- stavka_racuna
 
insert into stavka_racuna(id,redni_broj,kolicina,jedinica_mere,cena,racun,proizvod) values
 (nextval('stavka_racuna_seq'),2,3,'komad',23000,1,2),
 (nextval('stavka_racuna_seq'),1,1,'komad',73000,1,1),
 (nextval('stavka_racuna_seq'),3,1,'komad',123000,1,3),
 (nextval('stavka_racuna_seq'),1,2,'komad',33000,3,5),
 (nextval('stavka_racuna_seq'),1,5,'komad',15000,4,4);
 
 
 select * from stavka_racuna