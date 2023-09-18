-- Insert sample users
INSERT INTO users (email, password, registered_at, invalidate_token_before)
VALUES ('user1@example.com', '$2a$12$5TTdnuaWaeYf2NHthlFKSOz8ObtgMQWJ1uiqM2N2LlUcqVkcoXGM.', '2023-09-19 10:00:00', '2023-09-20 10:00:00'),
       ('user2@example.com', '$2a$12$BwjgK1f6OG9PdY0rKP8K7OQqcbpD7PEYEIYBiVmLOLvlj.LDj8Aue', '2023-09-19 11:00:00', '2023-09-20 11:00:00'),
       ('user3@example.com', '$2a$12$mLUDtoUVHVZ19IC7m.Q03eN/mFZXxOzcvfojqMTE7tpwNIgx5SKLu', '2023-09-19 12:00:00', '2023-09-20 12:00:00');

-- Insert sample user roles
INSERT INTO user_role (user_id, role)
VALUES (1, 'admin'),
       (2, 'user'),
       (3, 'user');

-- Insert sample user profiles
INSERT INTO user_profile (id, first_name, last_name, sur_name, company_name, position, telegram_chat_id, time_zone)
VALUES (1, 'John', 'Doe', 'Sr.', 'Acme Inc.', 'Software Engineer', 123456789, 'America/New_York'),
       (2, 'Jane', 'Smith', NULL, 'Tech Corp.', 'Product Manager', 987654321, 'Europe/London'),
       (3, 'Alice', 'Johnson', 'Jr.', 'Data Solutions', 'Data Analyst', NULL, 'Asia/Tokyo');

-- Insert sample calendars
INSERT INTO calendar (creator_id, title, color)
VALUES (1, 'Personal Calendar', 'blue'),
       (1, 'Work Calendar', 'green'),
       (2, 'Team Calendar', 'red');

-- Insert sample calendar events
INSERT INTO calendar_event (calendar_id, creator_id, title, description, time_from, time_to, notification_time, is_blocking)
VALUES (1, 1, 'Meeting', 'Discuss project updates', '2023-09-20 14:00:00', '2023-09-20 15:00:00', '2023-09-20 13:30:00', true),
       (2, 1, 'Conference Call', 'Client call', '2023-09-21 10:00:00', '2023-09-21 11:30:00', '2023-09-21 09:30:00', false),
       (3, 2, 'Team Meeting', 'Weekly team meeting', '2023-09-22 09:00:00', '2023-09-22 10:30:00', '2023-09-22 08:30:00', true);

-- Insert sample calendar users
INSERT INTO calendar_user (user_id, calendar_id, role)
VALUES (1, 1, 'admin'),
       (1, 2, 'admin'),
       (2, 3, 'user'),
       (3, 2, 'user');

-- Insert sample attachments
INSERT INTO attachment (calendar_event_id, title, url)
VALUES (1, 'Agenda', 'https://example.com/agenda.pdf'),
       (2, 'Notes', 'https://example.com/notes.txt'),
       (3, 'Presentation', 'https://example.com/presentation.pptx');

-- Insert sample calendar event users
INSERT INTO calendar_event_user (user_id, calendar_event_id)
VALUES (1, 1),
       (2, 1),
       (1, 2),
       (3, 3);

-- Insert sample refresh tokens
INSERT INTO refresh_token (user_id, token, expires_at)
VALUES (1, 'refresh_token_1', '2023-09-30 00:00:00'),
       (2, 'refresh_token_2', '2023-09-30 00:00:00'),
       (3, 'refresh_token_3', '2023-09-30 00:00:00');