CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    role ENUM('ROLE_ADMIN','ROLE_USUARIO') NOT NULL,
    data_criacao DATETIME,
    data_modificacao DATETIME,
    criado_por VARCHAR(255),
    modificado_por VARCHAR(255)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;