create table pengguna (
    id       varchar(36),
    username varchar(100) not null,
    nama     varchar(100) not null,
    email    varchar(100) not null,
    primary key (id),
    unique (username),
    unique (email)
);

create table transaksi (
    id              varchar(36),
    id_pengguna     varchar(36)    not null,
    waktu_transaksi timestamp      not null,
    keterangan      varchar(255),
    nilai           decimal(19, 2) not null,
    primary key (id),
    foreign key (id_pengguna) references pengguna (id)
);
