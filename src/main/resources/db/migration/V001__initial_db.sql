create table if not exists product(
    uuid varchar(36) primary key,
    title varchar(1000),
    description text,
    category varchar(36),
    count integer,
    price numeric,
    priceSale numeric,
    isSaleActive boolean
);

create index if not exists product_title_index on product(title)