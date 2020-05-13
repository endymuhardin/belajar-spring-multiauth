create table oauth_access_token (
    token_id          varchar(256) DEFAULT NULL,
    token             blob,
    authentication_id varchar(256) DEFAULT NULL,
    user_name         varchar(256) DEFAULT NULL,
    client_id         varchar(256) DEFAULT NULL,
    authentication    blob,
    refresh_token     varchar(256) DEFAULT NULL
) ;

create table oauth_client_details (
    client_id               varchar(256) NOT NULL,
    resource_ids            varchar(256)  DEFAULT NULL,
    client_secret           varchar(256)  DEFAULT NULL,
    scope                   varchar(256)  DEFAULT NULL,
    authorized_grant_types  varchar(256)  DEFAULT NULL,
    web_server_redirect_uri varchar(256)  DEFAULT NULL,
    authorities             varchar(256)  DEFAULT NULL,
    access_token_validity   int(11) DEFAULT NULL,
    refresh_token_validity  int(11) DEFAULT NULL,
    additional_information  varchar(4096) DEFAULT NULL,
    autoapprove             varchar(256)  DEFAULT NULL,
    PRIMARY KEY (client_id)
) ;

create table oauth_refresh_token(
    token_id       varchar(256) DEFAULT NULL,
    token          blob,
    authentication blob
) ;

create table oauth_client_token (
    token_id          varchar(256) DEFAULT NULL,
    token             blob,
    authentication_id varchar(256) DEFAULT NULL,
    user_name         varchar(256) DEFAULT NULL,
    client_id         varchar(256) DEFAULT NULL
) ;
