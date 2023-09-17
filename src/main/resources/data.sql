DROP TABLE IF EXISTS attachment CASCADE;
DROP TABLE IF EXISTS calendar_event_user CASCADE;
DROP TABLE IF EXISTS calendar_user_role CASCADE;
DROP TABLE IF EXISTS calendar_event CASCADE;
DROP TABLE IF EXISTS calendar CASCADE;
DROP TABLE IF EXISTS user_profile CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Insert test data into the 'users' table
INSERT INTO users (login, email, password)
VALUES
    ('admin', 'admin@admin.com', 'admin'),
    ('user2', 'user2@example.com', 'password'),
    ('user3', 'user3@example.com', 'password3');

INSERT INTO user_role (user_id, role)
VALUES
    (1, 'ADMIN'), -- user1 is an Admin
    (2, 'USER'), -- user2 is an User
    (3, 'USER'); -- user3 is a User

-- Insert test data into the 'user_profile' table
INSERT INTO user_profile (id, first_name, last_name, sur_name, company_name, position)
VALUES
    (1, 'John', 'Doe', 'Jr.', 'ACME Corp', 'Manager'),
    (2, 'Alice', 'Smith', 'Sr.', 'XYZ Inc', 'Developer'),
    (3, 'Bob', 'Johnson', NULL, NULL, 'Designer');

-- Insert test data into the 'calendar' table
INSERT INTO calendar (creator_id, title, color)
VALUES
    (1, 'Work Calendar', 'Blue'),
    (2, 'Personal Calendar', 'Green');

-- Insert test data into the 'calendar_event' table
INSERT INTO calendar_event (calendar_id, creator_id, title, description, time_from, time_to, notification_time, is_blocking)
VALUES
    (1, 1, 'Meeting', 'Team meeting', '2023-09-20', '2023-09-20', '2023-09-19', true),
    (1, 2, 'Training', 'Product training', '2023-09-22', '2023-09-22', '2023-09-21', false),
    (2, 2, 'Dinner', 'Dinner with friends', '2023-09-25', '2023-09-25', '2023-09-24', false);

-- Insert test data into the 'calendar_user_role' table
INSERT INTO calendar_user_role (user_id, calendar_id, role)
VALUES
    (1, 1, 'Admin'),
    (2, 1, 'Editor'),
    (3, 1, 'User'),
    (2, 2, 'Admin'),
    (1, 2, 'Editor');

-- Insert test data into the 'calendar_event_user' table
INSERT INTO calendar_event_user (user_id, calendar_event_id)
VALUES
    (1, 1),
    (2, 1),
    (2, 2),
    (3, 3);

-- Insert test data into the 'attachment' table
INSERT INTO attachment (calendar_event_id, url)
VALUES
    (1, 'https://example.com/attachment1'),
    (2, 'https://example.com/attachment2'),
    (3, 'https://example.com/attachment3');