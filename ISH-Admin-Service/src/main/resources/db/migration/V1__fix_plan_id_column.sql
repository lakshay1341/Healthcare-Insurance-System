-- First, check if the table exists
SET @table_exists = (SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'plan_master' AND table_schema = DATABASE());

-- If the table exists, modify the plan_id column
SET @sql = IF(@table_exists > 0, 
    'ALTER TABLE plan_master MODIFY COLUMN plan_id INT AUTO_INCREMENT PRIMARY KEY', 
    'SELECT "Table does not exist"');

-- Execute the dynamic SQL
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- If the table doesn't have any records yet, reset the auto_increment
SET @has_records = (SELECT COUNT(*) FROM plan_master);
SET @reset_sql = IF(@has_records = 0, 
    'ALTER TABLE plan_master AUTO_INCREMENT = 1', 
    'SELECT "Table has records, not resetting auto_increment"');

-- Execute the reset SQL
PREPARE reset_stmt FROM @reset_sql;
EXECUTE reset_stmt;
DEALLOCATE PREPARE reset_stmt;
