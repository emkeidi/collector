use collector;

-- proceed carefully!
-- commented out to hopefully reduce running accidental queries
-- drop table app_user;
-- drop table app_role;
-- drop table app_user_role;

create table app_user (
    user_id bigint generated always as identity primary key,
    username varchar(50) unique,
    password_hash varchar(2048),
    email varchar(255) not null unique,
    provider varchar(50),
    provider_id varchar(255),
    enabled boolean not null default(true),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    last_login timestamp,
    failed_login_attempts int default 0
);

create table app_role (
    app_role_id int generated always as identity primary key,
    name varchar(50) not null unique,
    description varchar(255)
);

create table app_user_role (
    app_user_id bigint not null,
    app_role_id int not null,
    created_at timestamp default current_timestamp,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id)
);




