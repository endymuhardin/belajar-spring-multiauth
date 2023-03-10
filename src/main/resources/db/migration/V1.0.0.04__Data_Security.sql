insert into s_roles (id, name)
values ('r001', 'staff');

insert into s_roles (id, name)
values ('r002', 'manager');

insert into s_users (id, username, active, id_role)
values ('u001', 'user001', true, 'r001');

insert into s_users (id, username, active, id_role)
values ('u002', 'user002', true, 'r002');

-- password : teststaff
insert into s_users_passwords (id_user, user_password)
values ('u001', '$2a$10$8AfV.EkFEPh2OpqInI6r9.FT73nYeKe1bU6Lh.iLqOGnvNxDgXgGS');

-- password : testmanager
insert into s_users_passwords (id_user, user_password)
values ('u002', '$2a$10$RPB/8RrHOPBbUj0iYRy7hu7K2fMKEFIR5Cqb2oGyeKcRFY/sH0.Mi');

insert into s_permissions (id, permission_label, permission_value)
values ('p001', 'Lihat Data Transaksi', 'VIEW_TRANSAKSI');

insert into s_permissions (id, permission_label, permission_value)
values ('p002', 'Edit Data Transaksi', 'EDIT_TRANSAKSI');

insert into s_roles_permissions (id_role, id_permission)
values ('r001', 'p001');

insert into s_roles_permissions (id_role, id_permission)
values ('r002', 'p001');

insert into s_roles_permissions (id_role, id_permission)
values ('r002', 'p002');
