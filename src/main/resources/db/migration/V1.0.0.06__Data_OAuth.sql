-- client name : spa-client
-- client secret : abcd
-- grant type : authorization code with PKCE
insert into oauth2_registered_client (
    id, client_id, client_id_issued_at, client_secret, client_secret_expires_at,
    client_name, client_authentication_methods, authorization_grant_types, 
    redirect_uris, 
    scopes, 
    client_settings, 
    token_settings
) values (
    '432f7b10-c3cd-4084-ac19-9ee068a7b435','spa-client', '2023-03-10 13:41:12.558667', '$2y$10$QmpDqxEmx0vdnZuvS8R3U.lCvLjwFGK44IFrbveDesIhgtvegC4im', null, 
    'spa-client', 'client_secret_basic', 'authorization_code,refresh_token',
    'http://127.0.0.1:10000/login/oauth2/code/messaging-client-oidc,http://127.0.0.1:10000/authorized',
    'openid,profile,message.read,message.write', 
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":true,"settings.client.require-authorization-consent":true}',
    '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000]}'
);