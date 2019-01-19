INSERT INTO users(
            username, password, enabled)
    VALUES ('afif','$2a$10$3AY5EGKHoKLY/X8e5DRBWuMcybpKqIXd13UylRphEIWKGdnBzufFi', true);
    
    INSERT INTO user_roles(
            user_role_id, username, role)
    VALUES (1,'afif','ROLE_ADMIN');