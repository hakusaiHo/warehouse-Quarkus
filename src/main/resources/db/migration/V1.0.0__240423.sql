
    create table IF NOT EXISTS KTST_AREA_INFO (
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        BRANCH varchar(12) not null,
        AREA varchar(15) not null,
        AREA_NAME varchar(30),
        primary key (BRANCH, AREA)
    )


    create table IF NOT EXISTS KTST_AREA_LOCATION (
        IS_VIRTUAL boolean,
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        BRANCH varchar(12) not null,
        AREA varchar(15),
        LOCATION_DESC varchar(15),
        LOCATION_ID varchar(20) not null,
        primary key (BRANCH, LOCATION_ID)
    )


    create table IF NOT EXISTS KTST_ITEM_AREA (
        ITEM_ID integer not null,
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        BRANCH varchar(12) not null,
        PREFER_SAVE_AREA varchar(15),
        primary key (ITEM_ID, BRANCH)
    )


    create table IF NOT EXISTS KTST_ITEM_BRANCH (
        ITEM_ID integer not null,
        LOT_CONTROL varchar(1),
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        BRANCH varchar(12) not null,
        ITEM_NUMBER varchar(25),
        primary key (ITEM_ID, BRANCH)
    )


    create table IF NOT EXISTS KTST_ITEM_MASTER (
        ALLERGEN_MARK boolean,
        MUSLIM_MARK boolean,
        SALES_CATEGORY_CODE varchar(3),
        item_id integer not null,
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        item_number varchar(25),
        description1 varchar(30),
        description2 varchar(30),
        primary key (item_id)
    )


    create table IF NOT EXISTS KTST_LOCATION_MASTER (
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        BRANCH varchar(12) not null,
        USER_RESERVED_REFERENCE varchar(15),
        LOCATION_ID varchar(20) not null,
        primary key (BRANCH, LOCATION_ID)
    )


    create table IF NOT EXISTS KTST_LOT_MASTER (
        ITEM_ID integer not null,
        EXPIRED_DATE timestamp(6),
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        SUPPLIER_NUMBER bigint,
        BRANCH varchar(12) not null,
        ITEM_NUMBER varchar(25),
        LOT_NUMBER varchar(30) not null,
        primary key (ITEM_ID, BRANCH, LOT_NUMBER)
    )


    create table IF NOT EXISTS KTST_ORDER_HEADER (
        ORDER_SUFFIX varchar(3) not null,
        ORDER_TYPE varchar(2) not null,
        ORDER_COMPANY varchar(5) not null,
        ARRIVAL_DATE timestamp(6),
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        ORDER_NUMBER bigint not null,
        SHIP_TO bigint,
        SUPPLIER_NUMBER bigint,
        ORDER_BY varchar(10),
        BRANCH varchar(12),
        primary key (ORDER_SUFFIX, ORDER_TYPE, ORDER_COMPANY, ORDER_NUMBER)
    )


    create table IF NOT EXISTS KTST_VEHICLE_DETAIL (
        ITEM_ID integer not null,
        STORE_QUANTITY numeric(15,4),
        STORE_UOM varchar(2),
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        LOT_NUMBER varchar(30) not null,
        VEHICLE_ID varchar(30) not null,
        primary key (ITEM_ID, LOT_NUMBER, VEHICLE_ID)
    )


    create table IF NOT EXISTS KTST_VEHICLE_MASTER (
        PAUSE_CODE varchar(2),
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        BRANCH varchar(12),
        VEHICLE_ID varchar(30) not null,
        STORE_LOCATION varchar(255),
        VEHICLE_TYPE_ID varchar(255),
        primary key (VEHICLE_ID)
    )


    create table IF NOT EXISTS KTST_VEHICLE_TYPE (
        ITEM_ID integer,
        MODIFIED_DATE timestamp(6),
        MODIFIED_USER bigint,
        VEHICLE_TYPE_ID varchar(10) not null,
        VEHICLE_TYPE_SPEC varchar(30),
        primary key (VEHICLE_TYPE_ID)
    )