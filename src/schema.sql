create table Usr (
    id  serial not null primary key,
    age int4 check (age>=0),
    email varchar(255),
    name varchar(30)
)
