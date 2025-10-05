INSERT INTO owners (id, email, first_name, last_name, password, phone_number, username)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 'alice.smith@example.com', 'Alice', 'Smith', 'hashed_pwd_1', '+32475111222', 'alice_s'),
    ('6fa459ea-ee8a-3ca4-894e-db77e160355e', 'bob.jones@example.com', 'Bob', 'Jones', 'hashed_pwd_2', '+32475222333', 'bobby_j'),
    ('123e4567-e89b-12d3-a456-426614174000', 'charlie.brown@example.com', 'Charlie', 'Brown', 'hashed_pwd_3', '+32475333444', 'charlie_b'),
    ('9b2cf3a2-1e11-11ee-be56-0242ac120002', 'diana.white@example.com', 'Diana', 'White', 'hashed_pwd_4', '+32475444555', 'diana_w'),
    ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'edward.green@example.com', 'Edward', 'Green', 'hashed_pwd_5', '+32475555666', 'ed_green');


-- Insert restaurants with embedded address fields
INSERT INTO restaurants (
    id, name, email,
    street, number, postal_code, city, country,
    res_picture_url, manual_opening, cuisine, default_prep_time, has_scheduled_publish, owner
) VALUES
      ('11111111-1111-1111-1111-111111111111', 'La Bella Italia', 'contact@labellaitalia.com',
       'Main Street', 12, '2000', 'Antwerp', 'Belgium',
       'https://example.com/pics/italia.jpg', 'AUTO', 'Italian', 20, FALSE, '550e8400-e29b-41d4-a716-446655440000'),

      ('22222222-2222-2222-2222-222222222222', 'Sushi World', 'hello@sushiworld.com',
       'Harbor Road', 45, '9000', 'Ghent', 'Belgium',
       'https://example.com/pics/sushi.jpg', 'AUTO', 'JAPANESE', 25, FALSE, '6fa459ea-ee8a-3ca4-894e-db77e160355e'),

      ('33333333-3333-3333-3333-333333333333', 'Taco Fiesta', 'info@tacofiesta.com',
       'Market Square', 7, '1000', 'Brussels', 'Belgium',
       'https://example.com/pics/taco.jpg', 'AUTO', 'Mexican', 15, FALSE, '123e4567-e89b-12d3-a456-426614174000'),

      ('44444444-4444-4444-4444-444444444444', 'Chez Diana', 'contact@chezdiana.be',
       'Grand Place', 19, '3000', 'Leuven', 'Belgium',
       'https://example.com/pics/diana.jpg', 'AUTO', 'French', 30, FALSE, '9b2cf3a2-1e11-11ee-be56-0242ac120002'),

      ('55555555-5555-5555-5555-555555555555', 'Edward’s Grill', 'info@edgrill.com',
       'Park Avenue', 88, '3500', 'Hasselt', 'Belgium',
       'https://example.com/pics/grill.jpg', 'AUTO', 'Steakhouse', 35, FALSE, 'f47ac10b-58cc-4372-a567-0e02b2c3d479');


-- Insert opening hours for all restaurants
INSERT INTO restaurant_opening_hours (restaurants_id, day_of_week, open, close) VALUES
                                                                                    -- La Bella Italia
                                                                                    ('11111111-1111-1111-1111-111111111111', 'Monday', '10:00', '22:00'),
                                                                                    ('11111111-1111-1111-1111-111111111111', 'Tuesday', '10:00', '22:00'),
                                                                                    ('11111111-1111-1111-1111-111111111111', 'Wednesday', '10:00', '22:00'),
                                                                                    ('11111111-1111-1111-1111-111111111111', 'Thursday', '10:00', '22:00'),
                                                                                    ('11111111-1111-1111-1111-111111111111', 'Friday', '10:00', '23:00'),
                                                                                    ('11111111-1111-1111-1111-111111111111', 'Saturday', '12:00', '23:00'),
                                                                                    ('11111111-1111-1111-1111-111111111111', 'Sunday', '12:00', '22:00'),

                                                                                    -- Sushi World
                                                                                    ('22222222-2222-2222-2222-222222222222', 'Monday', '11:00', '21:00'),
                                                                                    ('22222222-2222-2222-2222-222222222222', 'Tuesday', '11:00', '21:00'),
                                                                                    ('22222222-2222-2222-2222-222222222222', 'Wednesday', '11:00', '21:00'),
                                                                                    ('22222222-2222-2222-2222-222222222222', 'Thursday', '11:00', '22:00'),
                                                                                    ('22222222-2222-2222-2222-222222222222', 'Friday', '11:00', '22:00'),
                                                                                    ('22222222-2222-2222-2222-222222222222', 'Saturday', '12:00', '23:00'),
                                                                                    ('22222222-2222-2222-2222-222222222222', 'Sunday', '12:00', '21:00'),

                                                                                    -- Taco Fiesta
                                                                                    ('33333333-3333-3333-3333-333333333333', 'Monday', '10:00', '21:00'),
                                                                                    ('33333333-3333-3333-3333-333333333333', 'Tuesday', '10:00', '21:00'),
                                                                                    ('33333333-3333-3333-3333-333333333333', 'Wednesday', '10:00', '21:00'),
                                                                                    ('33333333-3333-3333-3333-333333333333', 'Thursday', '10:00', '22:00'),
                                                                                    ('33333333-3333-3333-3333-333333333333', 'Friday', '10:00', '23:00'),
                                                                                    ('33333333-3333-3333-3333-333333333333', 'Saturday', '12:00', '23:00'),
                                                                                    ('33333333-3333-3333-3333-333333333333', 'Sunday', '12:00', '22:00'),

                                                                                    -- Chez Diana
                                                                                    ('44444444-4444-4444-4444-444444444444', 'Monday', '12:00', '22:00'),
                                                                                    ('44444444-4444-4444-4444-444444444444', 'Tuesday', '12:00', '22:00'),
                                                                                    ('44444444-4444-4444-4444-444444444444', 'Wednesday', '12:00', '22:00'),
                                                                                    ('44444444-4444-4444-4444-444444444444', 'Thursday', '12:00', '23:00'),
                                                                                    ('44444444-4444-4444-4444-444444444444', 'Friday', '12:00', '23:00'),
                                                                                    ('44444444-4444-4444-4444-444444444444', 'Saturday', '12:00', '23:30'),
                                                                                    ('44444444-4444-4444-4444-444444444444', 'Sunday', '12:00', '22:00'),

                                                                                    -- Edward’s Grill
                                                                                    ('55555555-5555-5555-5555-555555555555', 'Monday', '11:00', '23:00'),
                                                                                    ('55555555-5555-5555-5555-555555555555', 'Tuesday', '11:00', '23:00'),
                                                                                    ('55555555-5555-5555-5555-555555555555', 'Wednesday', '11:00', '23:00'),
                                                                                    ('55555555-5555-5555-5555-555555555555', 'Thursday', '11:00', '23:00'),
                                                                                    ('55555555-5555-5555-5555-555555555555', 'Friday', '11:00', '23:30'),
                                                                                    ('55555555-5555-5555-5555-555555555555', 'Saturday', '12:00', '23:30'),
                                                                                    ('55555555-5555-5555-5555-555555555555', 'Sunday', '12:00', '22:30');


INSERT INTO dishes (id, is_in_stock, scheduled_time, restaurant)
VALUES
    -- Draft-only dish
    ('aaaa1111-aaaa-1111-aaaa-111111111111', true, '2025-10-05 12:00:00', '11111111-1111-1111-1111-111111111111'),
    -- Live-only dish
    ('bbbb2222-bbbb-2222-bbbb-222222222222', true, NULL, '11111111-1111-1111-1111-111111111111'),
    -- Dish with live + draft (pending update)
    ('cccc3333-cccc-3333-cccc-333333333333', true, NULL, '11111111-1111-1111-1111-111111111111'),
    -- Another draft-only dish
    ('dddd4444-dddd-4444-dddd-444444444444', false, NULL, '11111111-1111-1111-1111-111111111111'),
    -- Another live-only dish
    ('eeee5555-eeee-5555-eeee-555555555555', true, NULL, '11111111-1111-1111-1111-111111111111');

INSERT INTO dishes_drafts (id, name, dish_type, description, price, picture_url)
VALUES
    ('aaaa1111-aaaa-1111-aaaa-111111111111', 'Margherita Pizza', 'MAIN', 'Classic tomato, mozzarella & basil', 9.99, 'https://example.com/pics/margherita.jpg'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'Spicy Chicken Taco (New)', 'MAIN', 'Updated with extra jalapeños 🌶️', 7.50, 'https://example.com/pics/taco_spicy.jpg'),
    ('dddd4444-dddd-4444-dddd-444444444444', 'Vegan Burger', 'MAIN', 'Plant-based patty with vegan cheese', 11.20, 'https://example.com/pics/vegan_burger.jpg');

-- Draft food tags
INSERT INTO dish_draft_food_tags (dish_draft_id, food_tags)
VALUES
    ('aaaa1111-aaaa-1111-aaaa-111111111111', 'VEGETARIAN'),
    ('aaaa1111-aaaa-1111-aaaa-111111111111', 'CHEESY'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'SPICY'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'POULTRY'),
    ('dddd4444-dddd-4444-dddd-444444444444', 'VEGAN'),
    ('dddd4444-dddd-4444-dddd-444444444444', 'GLUTEN_FREE');

INSERT INTO dishes_lives (id, name, dish_type, description, price, picture_url)
VALUES
    ('bbbb2222-bbbb-2222-bbbb-222222222222', 'Sushi Platter', 'MAIN', 'Mixed nigiri, sashimi & maki rolls', 18.99, 'https://example.com/pics/sushi.jpg'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'Spicy Chicken Taco', 'MAIN', 'Taco with chicken, lettuce & salsa', 6.99, 'https://example.com/pics/taco.jpg'),
    ('eeee5555-eeee-5555-eeee-555555555555', 'Ribeye Steak', 'MAIN', 'Grilled ribeye with herb butter', 24.50, 'https://example.com/pics/ribeye.jpg');

-- Live food tags
INSERT INTO dish_live_food_tags (dish_live_id, food_tags)
VALUES
    ('bbbb2222-bbbb-2222-bbbb-222222222222', 'SEAFOOD'),
    ('bbbb2222-bbbb-2222-bbbb-222222222222', 'SHARING'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'POULTRY'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'SPICY'),
    ('eeee5555-eeee-5555-eeee-555555555555', 'BEEF'),
    ('eeee5555-eeee-5555-eeee-555555555555', 'GRILL');
