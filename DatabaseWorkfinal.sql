-- Creating Tables and overall database
-- Camel case doesnt appear to show up in sql terminal

-- \c <database> <- This connects to a specific database that is created
-- \dt <- displays content of current database
-- SELECT * FROM <tablename>; <- displays contents of a specific table
-- \d <tablename> <- shows data type specifics of table
-- INSERT INTO <tablename> (att1, att2, ...) <-- This line and the next line are how tables are populated
-- VALUES (val_att1, val_att2,...)

-- https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-create-table/
-- https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-integer/

CREATE DATABASE revsgrill;
-- SERIAL automatically increments the number (used for primary keys) goes up to INT32_MAX
-- DECIMAL(5,2) allows for 5 digit number with decimal point before two digits Max: 999.99
CREATE TABLE menu (
  food_id SERIAL NOT NULL PRIMARY KEY,
  menuitem VARCHAR(50) NOT NULL,
  price DECIMAL(5,2) NOT NULL,
  ingredients VARCHAR(100) NOT NULL
);

CREATE TABLE orderHistory (
  order_id SERIAL NOT NULL PRIMARY KEY,
  time_stamp TIMESTAMP NOT NULL,
  pricetotal DECIMAL(6,2) NOT NULL,
);

CREATE TABLE test (
  test_id INT GENERATED ALWAYS AS IDENTITY,
  PRIMARY KEY(test_id)
);

CREATE TABLE orderDetails (
  order_id INT,
  food_id INT,
  FOREIGN KEY(order_id) REFERENCES orderhistory(order_id),
  FOREIGN KEY(food_id) REFERENCES menu(food_id),

);

CREATE TABLE inventory (
  item_id SERIAL NOT NULL PRIMARY KEY,
  itemname VARCHAR(50) NOT NULL,
  itemcount INT NOT NULL CHECK (itemCount >= 0),
  itemfcount INT NOT NULL (itemFCount > 0)
);

CREATE TABLE lowInventory (
  priority_id SERIAL PRIMARY KEY,
  item_id INT
);
-- FOREIGN KEY(item_id) REFERENCES inventory(item_id) (THIS WAS IN THE ABOVE CMD)

-- Filling Menu table NOTE: food_id is automatically filled in since it is a SERIAL data type
-- for ingredients if same item is used twice (double cheeseburger) simply list it twice
-- also list ingredient in abbrev form
-- could use switch statement inside of java code with abrv
-- NEED SOLUTION: When ordering a combo, customers get to pick from EITHER fries or kettle chips
-- NOT WRITING COMMANDS FOR COMBOS TILL THIS IS RESOLVED (besides first one)

-- BURGERS
INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Revs Burger', 5.59, 'bun,pty,acs,ges,pkl');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Revs Burger Combo', 7.49, 'bun,pty,acs,ges,pkl,rfd,frs,ktc');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Double Stack Cheese Burger', 8.79, 'bun,pty,pty,acs,acs,ges,pkl');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Classic Burger', 5.49, 'bun,pty,ltc,tmt,pkl,onn');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Bacon Cheeseburger', 6.99, 'bun,pty,bcn,acs');


-- BASKETS
INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Three Tender Basket', 6.79, 'ctr,ctr,ctr,frs,txt,gvy');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Four Steak Finger Basket', 7.29, 'sfr,sfr,sfr,sfr,frs,txt,gvy');


-- SANDWICHES
INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Gig Em Patty Melt', 6.29, 'txt,pty,ges,onn,asc');

-- ?? How many chicken tenders go onto this sandwich ??
INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Howdy Spicy Ranch Chicken Strip Sandwich', 6.99, 'bun,ctr,srs,pjc');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Classic Crispy/Grilled Chicken Tender Sandwich', 5.79, 'bun,ltc,tmt,pkl,onn');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Grilled Cheese', 3.49, 'txt,acs');


-- Extra sauces
INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Gig Em Sauce', .69, 'ges');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Buffalo Sauce', .69, 'bfs');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Ranch', .69, 'rnc');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('BBQ Sauce', .69, 'bqs');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Honey Mustard', .69, 'hnm');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Spicy Ranch', .69, 'srs');


-- Beverages 
INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Reg Fountain Drink', 2.25, 'rfd');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Lg Fountain Drink', 2.45, 'lfd');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Drip Coffee', 2.29, 'drc');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Cold Brew', 3.65, 'cbr');

--Desserts

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Double Scoop Ice Cream', 3.29, 'dbi');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Aggie Shake', 4.49, 'ash');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Cookie Ice Cream', 4.69, 'snd');


-- Sides NEED PRICES FOR THESE 
INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Seasoned Fries', 3.65, 'frs');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Tater Tots', 3.65, 'tts');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Onion Ring', 3.65, 'onr');

INSERT INTO menu (menuitem, price, ingredients)
VALUES ('Kettle Chips', 3.65, 'ktc');

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Buns', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Texas Toast', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Patty', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Black Bean Patty', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Bacon', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Chicken Tender', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Steak Finger', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Lettuce', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Pickle', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Tomato', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Onion', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('American Cheese', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Fries', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Tater Tots', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Onion Rings', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Kettle Chips', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Gig-em Sauce', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Spicy Ranch Sauce', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Gravy', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Buffalo Sauce', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Honey Mustard', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Ranch', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('BBQ Sauce', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Regular Fountain Drink', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Large Fountain Drink', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Aquafina 16 oz', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Aquafina 20 oz', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Double Scoop Ice Cream', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Aggie Shakes', 3500, 3500);

INSERT INTO inventory(itemname, itemcount, itemfcount)
VALUES('Cookie Ice Cream Sundae', 3500, 3500);



-- Updating the values of the count

testdb=# UPDATE inventory SET itemcount = 130 WHERE ID = 1;
testdb=# UPDATE inventory SET itemcount = 52 WHERE ID = 5;
testdb=# UPDATE inventory SET itemcount = 234 WHERE ID = 9;

-- Inserting item_id into lowinventory

INSERT INTO lowinventory (item_id) VALUES (1);
INSERT INTO lowinventory (item_id) VALUES (5);
INSERT INTO lowinventory (item_id) VALUES (9);

