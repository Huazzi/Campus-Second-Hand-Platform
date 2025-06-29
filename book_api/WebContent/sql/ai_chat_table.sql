-- AI聊天记录表
CREATE TABLE ai_chat (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  question TEXT,
  answer TEXT,
  time TIMESTAMP NOT NULL,
  is_ai BOOLEAN NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
); 
