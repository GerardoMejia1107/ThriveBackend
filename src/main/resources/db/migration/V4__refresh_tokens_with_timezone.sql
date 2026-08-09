alter table refresh_tokens
    alter column created_at type timestamptz(6),
    alter column revoked_at type timestamptz(6),
    alter column expires_at type timestamptz(6);