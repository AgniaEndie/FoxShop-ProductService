create table if not exists product(
    uuid varchar(36) primary key,
    title varchar(1000),
    description text,
    category varchar(36),
    count integer,
    price numeric,
    price_sale numeric,
    is_sale_active boolean
);

create index if not exists product_title_index on product(title)