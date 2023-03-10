create table s_roles (
    id   varchar(36),
    name varchar(100) not null,
    primary key (id),
    unique (name)
);

create table s_users (
    id       varchar(36),
    username varchar(100) not null,
    active   boolean      not null,
    id_role  varchar(36)  not null,
    primary key (id),
    unique (username),
    foreign key (id_role) references s_roles (id)
);

create table s_users_passwords (
    id_user  varchar(36),
    user_password varchar(255) not null,
    primary key (id_user),
    foreign key (id_user) references s_users (id)
);

create table s_permissions (
    id    varchar(36),
    permission_label varchar(100) not null,
    permission_value varchar(100) not null,
    primary key (id),
    unique (permission_value)
);

create table s_roles_permissions (
    id_role       varchar(36),
    id_permission varchar(36),
    primary key (id_role, id_permission),
    foreign key (id_role) references s_roles (id),
    foreign key (id_permission) references s_permissions (id)
);

