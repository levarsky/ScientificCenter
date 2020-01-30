create table oauth_client_details (
                                      client_id VARCHAR(255) PRIMARY KEY,
                                      resource_ids VARCHAR(255),
                                      client_secret VARCHAR(255),
                                      scope VARCHAR(255),
                                      authorized_grant_types VARCHAR(255),
                                      web_server_redirect_uri VARCHAR(255),
                                      authorities VARCHAR(255),
                                      access_token_validity INTEGER,
                                      refresh_token_validity INTEGER,
                                      additional_information VARCHAR(4096),
                                      autoapprove VARCHAR(255)
);

create table oauth_client_token (
                                    token_id VARCHAR(255),
                                    token BLOB,
                                    authentication_id VARCHAR(255) PRIMARY KEY ,
                                    user_name VARCHAR(255),
                                    client_id VARCHAR(255)
);

create table oauth_access_token (
                                    token_id VARCHAR(255),
                                    token BLOB,
                                    authentication_id VARCHAR(255) PRIMARY KEY ,
                                    user_name VARCHAR(255),
                                    client_id VARCHAR(255),
                                    authentication BLOB,
                                    refresh_token VARCHAR(255)
);

create table oauth_refresh_token (
                                     token_id VARCHAR(255),
                                     token BLOB,
                                     authentication BLOB
);

create table oauth_code (
                            code VARCHAR(255), authentication BLOB
);

create table oauth_approvals (
                                 userId VARCHAR(256),
                                 clientId VARCHAR(256),
                                 scope VARCHAR(256),
                                 status VARCHAR(10),
                                 expiresAt TIMESTAMP,
                                 lastModifiedAt TIMESTAMP
);



INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri, authorities, access_token_validity,
 refresh_token_validity, additional_information, autoapprove)
VALUES
('fooClientIdPassword', 'secret', 'foo,read,write',
 'password,authorization_code,refresh_token,client_credentials', null, null, 36000, 36000, null, true);
