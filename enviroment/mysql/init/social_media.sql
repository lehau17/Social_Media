CREATE DATABASE IF NOT EXISTS social_media
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `social_media`.`users` (
                       id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username      VARCHAR(50) UNIQUE NOT NULL,
                       email         VARCHAR(100) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       full_name     VARCHAR(100),
                       phone_number  VARCHAR(20) UNIQUE,
                       avatar_url    VARCHAR(255),
                       bio           TEXT,
                       created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       is_active     BOOLEAN DEFAULT TRUE,   -- Tài khoản có bị khóa không
                       is_verified   BOOLEAN DEFAULT FALSE,  -- Email đã xác minh chưa
                       is_2fa_enabled BOOLEAN DEFAULT FALSE, -- Bật/tắt 2FA
                       two_fa_secret  VARCHAR(255),          -- Khóa bí mật 2FA (Google Authenticator, OTP)
                       two_fa_method  ENUM('OTP', 'TOTP') DEFAULT NULL  -- Phương thức 2FA
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'user table';

drop table if exists `social_media`.`roles`;

CREATE TABLE IF NOT EXISTS `social_media`.`roles` (
    id bigint auto_increment primary key ,
    role_name varchar(50) not null unique,
    is_active boolean default true,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

insert into `social_media`.`roles` (id, role_name, is_active)
values (1, 'USER', 1),
       (2, 'ADMIN', 1),
       (3, 'MANAGER', 1);


drop table if exists `social_media`.`resources`;

create table if not exists `social_media`.`resources` (
    id bigint auto_increment primary key,
    resource_name varchar(30) not null,
    resource_url varchar(50) not null,
    is_active boolean default true,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

insert into `social_media`.`resources` (id, resource_name, resource_url)
values (1, 'Authentication', '/auth/*'),
       (2, 'Admin manager', '/admin/*');


drop table if exists `social_media`.`role_resources`;


create table if not exists `social_media`.`role_resources` (
    role_id Bigint not null,
    resource_id BIGINT not null,
    is_active boolean default true,
    type_access varchar(20) # * CRUD
);

insert into `social_media`.`role_resources` (role_id, resource_id, is_active, type_access)
values (1, 1, 1, '*'),
       (2, 1, 1, '*'),
       (3, 1, 1, '*'),
       (2, 2, 1, '*'),
       (1, 2, 1, '_')
# * : all
# _ : deny
# ___D