INSERT INTO rent_prices (id, external_id, day_of_week, price, car_id, created_at, updated_at)
VALUES (1, '0c098634-c3da-4f97-9eea-eda6a4monday', 0, 10, (SELECT id FROM cars WHERE external_id = 'c50614de-9b12-4414-8267-9515b6cf4111'), '2023-01-01 12:00:00', '2023-01-01 12:00:00'),
       (2, '0c098634-c3da-4f97-9eea-eda64tuesday', 1, 10, (SELECT id FROM cars WHERE external_id = 'c50614de-9b12-4414-8267-9515b6cf4111'), '2023-01-02 12:00:00', '2023-01-02 12:00:00'),
       (3, '0c098634-c3da-4f97-9eea-ed1wednesday', 2, 10, (SELECT id FROM cars WHERE external_id = 'c50614de-9b12-4414-8267-9515b6cf4111'), '2023-01-03 12:00:00', '2023-01-03 12:00:00'),
       (4, '0c098634-c3da-4f97-9eea-ed12thursday', 3, 15, (SELECT id FROM cars WHERE external_id = 'c50614de-9b12-4414-8267-9515b6cf4111'), '2023-01-04 12:00:00', '2023-01-04 12:00:00'),
       (5, '0c098634-c3da-4f97-9eea-ed12t1friday', 4, 20, (SELECT id FROM cars WHERE external_id = 'c50614de-9b12-4414-8267-9515b6cf4111'), '2023-01-05 12:00:00', '2023-01-05 12:00:00'),
       (6, '0c098634-c3da-4f97-9eea-e2t1saturday', 5, 25, (SELECT id FROM cars WHERE external_id = 'c50614de-9b12-4414-8267-9515b6cf4111'), '2023-01-06 12:00:00', '2023-01-06 12:00:00'),
       (7, '0c098634-c3da-4f97-9eea-e2t141sunday', 6, 30, (SELECT id FROM cars WHERE external_id = 'c50614de-9b12-4414-8267-9515b6cf4111'), '2023-01-07 12:00:00', '2023-01-07 12:00:00');
