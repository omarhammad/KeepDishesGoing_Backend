-- Insert restaurants with embedded address fields
INSERT INTO restaurants (
    id, name, email,
    street, number, postal_code, city, country,
    res_picture_url, manual_opening, cuisine, default_prep_time, has_scheduled_publish, owner
) VALUES
      ('11111111-1111-1111-1111-111111111111', 'La Bella Italia', 'contact@labellaitalia.com',
       'Volkstraat', 44, '2000', 'Antwerpen', 'Belgium',
       'https://i.etsystatic.com/16895977/r/il/dbd742/5846514509/il_fullxfull.5846514509_ijhv.jpg', 'OPEN', 'Italian', 20, FALSE, '82d63058-20bc-4ab2-8fb7-36b352c6d67c'),

      ('22222222-2222-2222-2222-222222222222', 'Sushi World', 'hello@sushiworld.com',
       'Harbor Road', 45, '9000', 'Ghent', 'Belgium',
       'https://sc04.alicdn.com/kf/Hf37dfa2b7cd24aeab36b7f37d4ea000dM/Japanese-Restaurant-Delicious-Sushi-Cuisine-Background-Photo-Wallpaper.jpg', 'AUTO', 'JAPANESE', 25, FALSE, '509d68fe-e415-4dd9-b1aa-ab6ffcabbc90'),

      ('33333333-3333-3333-3333-333333333333', 'Taco Fiesta', 'info@tacofiesta.com',
       'Market Square', 7, '1000', 'Brussels', 'Belgium',
       'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/12/84/15/7b/specialities-tacos-cocktails.jpg?w=900&h=500&s=1', 'AUTO', 'Mexican', 15, FALSE, '2c05cc82-a1b6-46b6-b0eb-ed8b80d648ab'),

      ('44444444-4444-4444-4444-444444444444', 'Chez Diana', 'contact@chezdiana.be',
       'Grand Place', 19, '3000', 'Leuven', 'Belgium',
       'https://www.restaurantdiana.be/wp-content/uploads/2019/09/resataurant-diana-08.jpg', 'AUTO', 'French', 30, FALSE, '85d5782d-ddab-4707-a600-49b9d1f6f6a1'),

      ('55555555-5555-5555-5555-555555555555', 'Edward’s Grill', 'info@edgrill.com',
       'Park Avenue', 88, '3500', 'Hasselt', 'Belgium',
       'https://static.wixstatic.com/media/d8f204_001d58dd12b544d1a1d02d137bca1112.jpg/v1/fill/w_818,h_545,al_c,q_85,usm_0.66_1.00_0.01,enc_avif,quality_auto/d8f204_001d58dd12b544d1a1d02d137bca1112.jpg', 'AUTO', 'Steakhouse', 35, FALSE, '0f4a6c39-a2f9-4bdf-8247-e89866529b68');


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
INSERT INTO dishes (id, is_in_stock, scheduled_time, restaurant_id)
VALUES
    ('aaaa1111-aaaa-1111-aaaa-111111111111', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('bbbb2222-bbbb-2222-bbbb-222222222222', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('cccc3333-cccc-3333-cccc-333333333333', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('dddd4444-dddd-4444-dddd-444444444444', false, NULL, '11111111-1111-1111-1111-111111111111'),
    ('eeee5555-eeee-5555-eeee-555555555555', false, NULL, '11111111-1111-1111-1111-111111111111');

INSERT INTO dishes_drafts (id, name, dish_type, description, price, picture_url)
VALUES
    ('aaaa1111-aaaa-1111-aaaa-111111111111', 'Margherita Pizza', 'MAIN', 'Classic tomato, mozzarella & basil', 9.99, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3FiSRjRmljQ7LbKE6_XNJitNqqzngEQ6r4WO_IgBGErDjTiexFeFF86y66jtJC5UGRWHCBuANmbwA4QoF4TgApwtgtMsEvXEu9Oj2a3pM'),
    ('bbbb2222-bbbb-2222-bbbb-222222222222', 'Sushi Platter', 'MAIN', 'Mixed nigiri, sashimi & maki rolls', 18.99, 'https://www.wasabiko.com/wp-content/uploads/2023/02/Wasabiko-Sushi-Poke-Manassas-Virginia-Web-Menu-Header-Platter.jpg'),
    ('cccc3333-cccc-3333-cccc-333333333333', 'Spicy Chicken Taco', 'MAIN', 'Taco with chicken, lettuce & salsa', 6.99, 'https://ortega.com/wp-content/uploads/recipe-spicycrispychickentacos-1-scaled.jpg'),
    ('dddd4444-dddd-4444-dddd-444444444444', 'Vegan Burger', 'MAIN', 'Plant-based patty with vegan cheese', 11.20, 'https://www.seriouseats.com/thmb/_c-xbP-tch4dpSTxKE1zY16sHo8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/20231204-SEA-VeganBurger-FredHardy-00-dbf603c78b694bfd99489b85ab44f4c4.jpg'),
    ('eeee5555-eeee-5555-eeee-555555555555', 'Ribeye Steak', 'MAIN', 'Grilled ribeye with herb butter', 24.50, 'https://diethood.com/wp-content/uploads/2021/02/ribeye-steak-5.jpg');

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



-- Second res
INSERT INTO dishes (id, is_in_stock, scheduled_time, restaurant_id)
VALUES
    ('f66f6666-6fff-4666-af6f-666666666666', false, NULL, '22222222-2222-2222-2222-222222222222'),
    ('a77a7777-7aaa-4777-b777-777777777777', false, NULL, '22222222-2222-2222-2222-222222222222'),
    ('b88b8888-8bbb-4888-b888-888888888888', false, NULL, '22222222-2222-2222-2222-222222222222'),
    ('c99c9999-9ccc-4999-b999-999999999999', false, NULL, '22222222-2222-2222-2222-222222222222'),
    ('d00d0000-0ddd-4000-b000-000000000000', false, NULL, '22222222-2222-2222-2222-222222222222');

INSERT INTO dishes_drafts (id, name, dish_type, description, price, picture_url)
VALUES
    ('f66f6666-6fff-4666-af6f-666666666666', 'Salmon Nigiri', 'MAIN', 'Fresh salmon over seasoned rice', 8.50, 'https://aisforappleau.com/wp-content/uploads/2023/07/how-to-make-sushi-salmon-nigiri-6.jpg'),
    ('a77a7777-7aaa-4777-b777-777777777777', 'Tempura Udon', 'MAIN', 'Thick noodles in broth with shrimp tempura', 12.99, 'https://sudachirecipes.com/wp-content/uploads/2023/10/ebiten-udon-thumbnail.jpg'),
    ('b88b8888-8bbb-4888-b888-888888888888', 'Dragon Roll', 'MAIN', 'Eel, avocado, cucumber, and spicy mayo', 14.75, 'https://www.justonecookbook.com/wp-content/uploads/2020/06/Dragon-Roll-0286-I.jpg'),
    ('c99c9999-9ccc-4999-b999-999999999999', 'Miso Soup', 'STARTER', 'Tofu, seaweed, and scallions in miso broth', 4.50, 'https://www.allrecipes.com/thmb/foXNoD8pki-R_UTcVIpw_WwN4Ko=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/AR-RM-13107-miso-soup-ddmfs-1x2-8dd87e98ca4344f7815857f75dc4928e.jpg'),
    ('d00d0000-0ddd-4000-b000-000000000000', 'Mochi Ice Cream', 'DESSERT', 'Sweet rice cake filled with ice cream', 6.00, 'https://kirbiecravings.com/wp-content/uploads/2016/09/mochi-ice-cream-033.jpg');

INSERT INTO public.dish_draft_food_tags (dish_draft_id, food_tags)
VALUES
    ('f66f6666-6fff-4666-af6f-666666666666', 'SEAFOOD'),
    ('a77a7777-7aaa-4777-b777-777777777777', 'SOUP'),
    ('b88b8888-8bbb-4888-b888-888888888888', 'SPICY'),
    ('c99c9999-9ccc-4999-b999-999999999999', 'VEGETARIAN'),
    ('d00d0000-0ddd-4000-b000-000000000000', 'SWEET');
