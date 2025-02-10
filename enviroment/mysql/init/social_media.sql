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
    method varchar(20) NOT NULL,
    attributes varchar(20) not null comment "any, own",
    is_active boolean default true
);



create table if not exists `social_media`.`posts` (
    id BIGINT auto_increment not null primary key,
    content varchar(255) comment "HTML scripts",
    files json ,
    total_like BIGINT default 0,
    total_comment BIGINT default 0,
    total_share BIGINT default 0,
    user_id Bigint not null,
    is_active boolean default true,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    constraint fk_post_user_id foreign key (user_id) references users(id)
);

create index idc_post_user_id ON `social_media`.`posts`(user_id, created_at);

create table if not exists `social_media`.`post_comments` (
    id BIGINT not null primary key auto_increment,
    user_id BIGINT not null,
    post_id BIGINT not null,
    content varchar(255) not null,
    parent_id BIGINT,
    total_like BIGINT default 0,
    isActive boolean default true,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX  idx_post_user_id ON `social_media`.`post_comments` (post_id, created_at);


create table if not exists `social_media`.`post_likes` (
    id BIGINT not null primary key auto_increment,
    user_id BIGINT not null,
    post_id BIGINT not null,
    created_at TIMESTAMP default CURRENT_TIMESTAMP
);
create index idx_post_likes_post_id ON `social_media`.`post_likes`(post_id, created_at);
create  index idx_post_likes_user_id on `social_media`.`post_likes`(user_id);


create table if not exists `social_media`.`comment_likes` (
                                                           id BIGINT not null primary key auto_increment,
                                                           user_id BIGINT not null,
                                                           comment_id BIGINT not null,
                                                           created_at TIMESTAMP default CURRENT_TIMESTAMP
);
create index idx_comment_likes_post_id ON `social_media`.`comment_likes`(comment_id, created_at);
create  index idx_comment_likes_user_id on `social_media`.`comment_likes`(user_id);


drop table `social_media`.`stories`;

create table if not exists `social_media`.`stories` (
    id BIGINT not null primary key auto_increment,
    files json not null,
    permission varchar(50) comment "private only public",
    users_accepts json,
    is_active boolean default true,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


drop table `social_media`.`stories_interact`;

create table if not exists `social_media`.`stories_interact`(
    id BIGINT primary key not null auto_increment,
    story_id BIGINT not null,
    user_id BIGINT not null,
    emotions json,
    total BIGINT,
    created_at Timestamp default CURRENT_TIMESTAMP,
    constraint fk_interact_story_id foreign key (story_id) references `social_media`.`stories`(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;






