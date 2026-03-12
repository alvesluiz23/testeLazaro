BEGIN;

DROP TABLE IF EXISTS user_profile;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profile;

CREATE TABLE profile (
    id          INT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT profile_description_min_length CHECK (length(trim(description)) >= 5),
    CONSTRAINT profile_description_unique UNIQUE (description)
);

CREATE TABLE users (
    id   UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT users_name_min_length CHECK (length(trim(name)) >= 10)
);

CREATE TABLE user_profile (
    user_id    UUID NOT NULL,
    profile_id INT  NOT NULL,
    PRIMARY KEY (user_id, profile_id),
    CONSTRAINT fk_user_profile_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_profile_profile FOREIGN KEY (profile_id) REFERENCES profile (id) ON DELETE CASCADE
);

CREATE INDEX idx_user_profile_user_id ON user_profile (user_id);
CREATE INDEX idx_user_profile_profile_id ON user_profile (profile_id);

INSERT INTO profile (id, description) VALUES
  (2, 'Usuário'),
  (3, 'Convidado'),
  (4, 'Moderador'),
  (5, 'Desenvolv');

INSERT INTO users (id, name) VALUES
  ('550e8400-e29b-41d4-a716-446655440000', 'FernandoSilva'),
  ('550e8400-e29b-41d4-a716-446655440001', 'MariaOliveira'),
  ('550e8400-e29b-41d4-a716-446655440002', 'JoaoPereira');

INSERT INTO user_profile (user_id, profile_id) VALUES
  ('550e8400-e29b-41d4-a716-446655440000', 5),
  ('550e8400-e29b-41d4-a716-446655440001', 2),
  ('550e8400-e29b-41d4-a716-446655440002', 3);

COMMIT;
