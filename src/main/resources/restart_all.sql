TRUNCATE TABLE diplom_schema.products cascade;
TRUNCATE TABLE diplom_schema.markets cascade;

ALTER SEQUENCE diplom_schema.sales_data_column_1_seq restart with 1;
ALTER SEQUENCE diplom_schema.energies_id_seq restart with 1;
ALTER SEQUENCE diplom_schema.markets_id_seq restart with 1;
ALTER SEQUENCE diplom_schema.nutritionals_id_seq restart with 1;
ALTER SEQUENCE diplom_schema.products_id_seq restart with 1;
ALTER SEQUENCE diplom_schema.assortment_data_id_seq restart with 1;