CREATE TABLE profile (
    id INT PRIMARY KEY,
    description VARCHAR NOT NULL
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL  
);

CREATE TABLE user_profile (
    user_id UUID NOT NULL,
    profile_id INT NOT NULL,
    PRIMARY KEY (user_id, profile_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (profile_id) REFERENCES profile(id)
);

INSERT INTO users (id, name) VALUES
  ('550e8400-e29b-41d4-a716-446655440000', 'FernandoSilva'),
  ('550e8400-e29b-41d4-a716-446655440001', 'MariaOliveira'),
  ('550e8400-e29b-41d4-a716-446655440002', 'JoaoPereira');



INSERT INTO profile (id, description) VALUES (1, 'Admin');        
INSERT INTO profile (id, description) VALUES (2, 'Usuário');    
INSERT INTO profile (id, description) VALUES (3, 'Convidado');    
INSERT INTO profile (id, description) VALUES (4, 'Moderador');    
INSERT INTO profile (id, description) VALUES (5, 'Desenvolv'); 

INSERT INTO user_profile (user_id, profile_id) VALUES
  ('550e8400-e29b-41d4-a716-446655440000', 1), 
  ('550e8400-e29b-41d4-a716-446655440000', 5), 
  ('550e8400-e29b-41d4-a716-446655440001', 2), 
  ('550e8400-e29b-41d4-a716-446655440002', 3); 
