-- Insert restaurants with embedded address fields
INSERT INTO restaurants (
    id, name, email,
    street, number, postal_code, city, country,
    res_picture_url, manual_opening, cuisine, default_prep_time, has_scheduled_publish, owner
) VALUES
      ('11111111-1111-1111-1111-111111111111', 'La Bella Italia', 'contact@labellaitalia.com',
       'Main Street', 12, '2000', 'Antwerp', 'Belgium',
       'https://i.etsystatic.com/16895977/r/il/dbd742/5846514509/il_fullxfull.5846514509_ijhv.jpg', 'AUTO', 'Italian', 20, FALSE, '82d63058-20bc-4ab2-8fb7-36b352c6d67c'),

      ('22222222-2222-2222-2222-222222222222', 'Sushi World', 'hello@sushiworld.com',
       'Harbor Road', 45, '9000', 'Ghent', 'Belgium',
       'https://sc04.alicdn.com/kf/Hf37dfa2b7cd24aeab36b7f37d4ea000dM/Japanese-Restaurant-Delicious-Sushi-Cuisine-Background-Photo-Wallpaper.jpg', 'AUTO', 'JAPANESE', 25, FALSE, '509d68fe-e415-4dd9-b1aa-ab6ffcabbc90'),

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

-- All dishes as draft-only
INSERT INTO dishes (id, is_in_stock, scheduled_time, restaurant_id)
VALUES
    ('aaaa1111-aaaa-1111-aaaa-111111111111', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('bbbb2222-bbbb-2222-bbbb-222222222222', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('cccc3333-cccc-3333-cccc-333333333333', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('dddd4444-dddd-4444-dddd-444444444444', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('eeee5555-eeee-5555-eeee-555555555555', false, NULL, '11111111-1111-1111-1111-111111111111');

-- Draft data for all dishes
INSERT INTO dishes_drafts (id, name, dish_type, description, price, picture_url)
VALUES
    ('aaaa1111-aaaa-1111-aaaa-111111111111', 'Margherita Pizza', 'MAIN', 'Classic tomato, mozzarella & basil', 9.99, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3FiSRjRmljQ7LbKE6_XNJitNqqzngEQ6r4WO_IgBGErDjTiexFeFF86y66jtJC5UGRWHCBuANmbwA4QoF4TgApwtgtMsEvXEu9Oj2a3pM'),
    ('bbbb2222-bbbb-2222-bbbb-222222222222', 'Sushi Platter', 'MAIN', 'Mixed nigiri, sashimi & maki rolls', 18.99, 'https://www.wasabiko.com/wp-content/uploads/2023/02/Wasabiko-Sushi-Poke-Manassas-Virginia-Web-Menu-Header-Platter.jpg'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'Spicy Chicken Taco', 'MAIN', 'Taco with chicken, lettuce & salsa', 6.99, 'https://ortega.com/wp-content/uploads/recipe-spicycrispychickentacos-1-scaled.jpg'),
    ('dddd4444-dddd-4444-dddd-444444444444', 'Vegan Burger', 'MAIN', 'Plant-based patty with vegan cheese', 11.20, 'https://www.seriouseats.com/thmb/_c-xbP-tch4dpSTxKE1zY16sHo8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/20231204-SEA-VeganBurger-FredHardy-00-dbf603c78b694bfd99489b85ab44f4c4.jpg'),
    ('eeee5555-eeee-5555-eeee-555555555555', 'Ribeye Steak', 'MAIN', 'Grilled ribeye with herb butter', 24.50, 'https://diethood.com/wp-content/uploads/2021/02/ribeye-steak-5.jpg');

-- Draft food tags
INSERT INTO dish_draft_food_tags (dish_draft_id, food_tags)
VALUES
    ('aaaa1111-aaaa-1111-aaaa-111111111111', 'VEGETARIAN'),
    ('aaaa1111-aaaa-1111-aaaa-111111111111', 'CHEESY'),
    ('bbbb2222-bbbb-2222-bbbb-222222222222', 'SEAFOOD'),
    ('bbbb2222-bbbb-2222-bbbb-222222222222', 'SHARING'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'POULTRY'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'SPICY'),
    ('dddd4444-dddd-4444-dddd-444444444444', 'VEGAN'),
    ('dddd4444-dddd-4444-dddd-444444444444', 'GLUTEN_FREE'),
    ('eeee5555-eeee-5555-eeee-555555555555', 'BEEF'),
    ('eeee5555-eeee-5555-eeee-555555555555', 'GRILL');
