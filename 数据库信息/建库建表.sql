CREATE DATABASE IF NOT EXISTS music_player DEFAULT CHARACTER SET utf8;

USE music_player;

-- 用户
CREATE TABLE IF NOT EXISTS user(
		id INT AUTO_INCREMENT PRIMARY KEY,
		username VARCHAR(50) NOT NULL DEFAULT '' UNIQUE,
    password VARCHAR(200) NOT NULL DEFAULT '',
		sex ENUM('男','女') NOT NULL DEFAULT '男',
		birthday DATE NOT NULL DEFAULT '2000-01-01',
		signature TEXT,
		location TEXT,
		phone VARCHAR(20) NOT NULL DEFAULT '',
		email VARCHAR(20) NOT NULL DEFAULT '',
		img TEXT,
    authority VARCHAR(20) NOT NULL DEFAULT 'client',
		is_delete INT NOT NULL DEFAULT 0);

-- 歌手 
CREATE TABLE IF NOT EXISTS singer(
	  id INT AUTO_INCREMENT PRIMARY KEY,
    singer_name VARCHAR(50) NOT NULL DEFAULT '',
    sex ENUM('男','女') NOT NULL DEFAULT '男',
    birthday DATE NOT NULL DEFAULT '2000-01-01',
    location TEXT,
    intro TEXT,
    img TEXT,
    is_delete INT NOT NULL DEFAULT 0);

-- 歌曲        
CREATE TABLE IF NOT EXISTS song(
	  id INT AUTO_INCREMENT PRIMARY KEY,
    song_name VARCHAR(50) NOT NULL DEFAULT '',
    intro TEXT,
    lyric TEXT,
    img TEXT,
    url TEXT,
    singer_id INT NOT NULL,
    is_delete INT NOT NULL DEFAULT 0,
    CONSTRAINT `song_fk` FOREIGN KEY (`singer_id`) REFERENCES `singer`(`id`));

-- 歌单      
CREATE TABLE IF NOT EXISTS sheet(
	  id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    intro TEXT,
    style VARCHAR(50) NOT NULL DEFAULT '',
    img TEXT,
    is_delete INT NOT NULL DEFAULT 0);

-- 歌单与歌     
CREATE TABLE IF NOT EXISTS sheet_contain(
	  id INT AUTO_INCREMENT PRIMARY KEY,
    sheet_id INT NOT NULL,
    song_id INT NOT NULL,
    CONSTRAINT `sheet_contain_fk1` FOREIGN KEY (`song_id`) REFERENCES `song`(`id`),
    CONSTRAINT `sheet_contain_fk2` FOREIGN KEY (`sheet_id`) REFERENCES `sheet`(`id`));

-- 用户播放列表 + 收藏
CREATE TABLE IF NOT EXISTS my_list_contain(
	  id INT AUTO_INCREMENT PRIMARY KEY,
	  type INT NOT NULL DEFAULT -1,//1是喜欢
	  client_id INT NOT NULL,
	  song_id INT NOT NULL,
	  CONSTRAINT `my_list_contain_fk` FOREIGN KEY (`client_id`) REFERENCES `user`(`id`),
    CONSTRAINT `my_list_contain_fk2` FOREIGN KEY (`song_id`) REFERENCES `song`(`id`));	

-- 歌单评论		
CREATE TABLE IF NOT EXISTS sheet_comment(
	  id INT AUTO_INCREMENT PRIMARY KEY,
	  client_id INT NOT NULL,
	  sheet_id INT NOT NULL,
	  content TEXT,
	  stars INT NOT NULL DEFAULT 0,	
	  create_time	DATETIME NOT NULL DEFAULT '2000-01-01 00:00:00',
	  update_time	DATETIME NOT NULL DEFAULT '2000-01-01 00:00:00',
	  CONSTRAINT `comment_fk` FOREIGN KEY (`client_id`) REFERENCES `user`(`id`),
    CONSTRAINT `comment_fk2` FOREIGN KEY (`sheet_id`) REFERENCES `sheet`(`id`));

-- 歌单评分
CREATE TABLE IF NOT EXISTS sheet_mark(
	  id INT AUTO_INCREMENT PRIMARY KEY,
	  client_id INT NOT NULL,
	  sheet_id INT NOT NULL,
	  score ENUM('0', '1', '2', '3', '4', '5') NOT NULL DEFAULT '0',
	  CONSTRAINT `mark_fk` FOREIGN KEY (`client_id`) REFERENCES `user`(`id`),
    CONSTRAINT `mark_fk2` FOREIGN KEY (`sheet_id`) REFERENCES `sheet`(`id`));

-- 歌单收藏
CREATE TABLE IF NOT EXISTS my_sheet(
	  id INT AUTO_INCREMENT PRIMARY KEY,
	  client_id INT NOT NULL,
	  sheet_id INT NOT NULL,
	  CONSTRAINT `collect_fk` FOREIGN KEY (`client_id`) REFERENCES `user`(`id`),
    CONSTRAINT `collect_fk2` FOREIGN KEY (`sheet_id`) REFERENCES `sheet`(`id`));
    
-- 歌单收藏
CREATE TABLE IF NOT EXISTS banner(
	 id INT AUTO_INCREMENT PRIMARY KEY,
	 pic TEXT);
  
-- 创建逻辑删除触发器 
DELIMITER //
CREATE TRIGGER singer_trigger BEFORE UPDATE ON singer
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
      UPDATE song SET is_delete = 1 WHERE singer_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER my_list_contain_trigger1 BEFORE UPDATE ON user
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM my_list_contain WHERE client_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER my_list_contain_trigger2 BEFORE UPDATE ON song
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM my_list_contain WHERE song_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER my_sheet_trigger1 BEFORE UPDATE ON user
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM my_sheet WHERE client_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER my_sheet_trigger2 BEFORE UPDATE ON sheet
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM my_sheet WHERE sheet_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER sheet_comment_trigger1 BEFORE UPDATE ON sheet
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM sheet_comment WHERE sheet_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER sheet_comment_trigger2 BEFORE UPDATE ON user
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM sheet_comment WHERE client_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER sheet_mark_trigger1 BEFORE UPDATE ON sheet
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM sheet_mark WHERE sheet_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER sheet_mark_trigger2 BEFORE UPDATE ON user
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM sheet_mark WHERE client_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER sheet_contain_trigger1 BEFORE UPDATE ON song
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM sheet_contain WHERE song_id = OLD.id;
   END IF;
END;//

CREATE TRIGGER sheet_contain_trigger2 BEFORE UPDATE ON sheet
FOR EACH ROW
BEGIN
   IF !(NEW.is_delete <=> OLD.is_delete) THEN
			DELETE FROM sheet_contain WHERE sheet_id = OLD.id;
   END IF;
END;//
DELIMITER ;