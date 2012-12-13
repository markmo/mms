CREATE SEQUENCE security_role_id_seq_1 START 1;
ALTER TABLE security_role ALTER COLUMN id SET DEFAULT nextval('security_role_id_seq_1'::regclass);
ALTER SEQUENCE security_role_id_seq_1 OWNED BY security_role.id;
