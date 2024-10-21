# CREATE 피드 테이블
CREATE TABLE IF NOT EXISTS feeds
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY comment '피드 테이블의 PK',
    content VARCHAR(4000)  NOT NULL comment '피드 내용',
    likes BIGIINT comment '좋아요수',
    created_at DATETIME  NOT NULL comment '피드 최초 작성일',
    updated_at DATETIME  NOT NULL comment '피드 수정일',
    user_id BIGINT comment '피드 테이블의 FK; 유저 테이블의 PK'
    FOREIGN KEY (user_id) REFERENCES users(id)
);