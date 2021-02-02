
-- Identity(1,1) refers to start with 1 and increment with 1 value and it's primary key

CREATE TABLE dbo.JerseyTransport (
    id int Identity(1,1) primary key,
    source varchar(50) NOT NULL,
    destination varchar(50) not null,
    units varchar(50),
);