CREATE TABLE IF NOT EXISTS usuario (
  `id_usuario` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `nom_usuario` VARCHAR(30) NOT NULL,
  `senha` VARCHAR(20) NOT NULL
);

--ALTER TABLE `usuario` ADD  PRIMARY KEY (`id_usuario`);

CREATE TABLE IF NOT EXISTS mensagem (
  `id_msg` INTEGER PRIMARY KEY AUTO_INCREMENT,
  `usuario` VARCHAR(30) NOT NULL,
  `txt_msg` VARCHAR(100) NOT NULL,
  `datatime`  VARCHAR(15) NOT NULL
);

-- ALTER TABLE `mensagem` ADD  PRIMARY KEY (`id_msg`);
ALTER TABLE `mensagem` ADD  FOREIGN KEY (`usuario`);
