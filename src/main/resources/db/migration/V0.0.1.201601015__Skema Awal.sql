create table product (
    id varchar(32) primary key,
    kode varchar(10) not null unique,
    nama varchar(255) not null,
    harga decimal(19,2) not null
) Engine=InnoDB;