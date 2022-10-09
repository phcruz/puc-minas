show tables;

CREATE USER 'pitaco_application'@'localhost' IDENTIFIED BY 'ccc36a1d-8593-4ffe-afc4-fec1bbaf55f3';

GRANT ALL PRIVILEGES ON *.* TO 'pitaco_application'@'localhost';

drop database if exists pitaco;

create database if not exists pitaco;

use pitaco;


/*tabela de avatar*/
create table tb_avatar(
id_avatar			int UNSIGNED auto_increment not null,
descricao			varchar(255),
url_imagem			varchar(255),
data_cadastro		timestamp null,
ativo				bool,

INDEX(id_avatar),
primary key(id_avatar)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*cria a tabela usuario*/
create table tb_usuario(
id_usuario		int UNSIGNED auto_increment not null,
nome			varchar(40) not null,
email			varchar(50) not null unique,
senha			varchar(255) not null,
data_nascimento	timestamp null,
telefone		varchar(15),
local_cadastro	varchar(15),
perfil_acesso	tinyint(1),
ativo			bool,
data_cadastro	timestamp null,
time_coracao	varchar(50),
id_avatar		int UNSIGNED not null,

INDEX(id_usuario),
primary key(id_usuario),
foreign key (id_avatar) references tb_avatar(id_avatar)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*cria a tabela cidade*/
create table tb_log_token(
id_log_token	int UNSIGNED auto_increment not null,
id_usuario		int UNSIGNED not null,
token			varchar(500) not null,
email			varchar(40) not null,
data_criacao	timestamp null,
data_expiracao	timestamp null,

INDEX(id_log_token),
primary key(id_log_token),
foreign key (id_usuario) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*cria a tabela liga*/
create table tb_liga(
id_liga			int UNSIGNED auto_increment not null,
nome_liga		varchar(30) not null,
pais_liga		varchar(30) not null,
descricao		varchar(50),
temporada		varchar(10) not null,
url_imagem		varchar(255) not null,
paga			bool,
valor			float,
data_inicio		timestamp null,
data_fim		timestamp null,
ativa			bool,

INDEX(id_liga),
primary key(id_liga)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*cria a tabela indicacao de liga*/
create table tb_indicacao_liga(
id_indicacao_liga	int UNSIGNED auto_increment not null,
id_usuario			int UNSIGNED not null,
nome_liga			varchar(30) not null,
pais_liga			varchar(30) not null,
observacao			varchar(100) ,
data_indicacao		timestamp null,
ativa				bool,

INDEX(id_indicacao_liga),
primary key(id_indicacao_liga),
foreign key (id_usuario) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*ligas do usuario*/
create table tb_liga_usuario(
id_liga_usuario		int UNSIGNED auto_increment not null,
id_liga				int UNSIGNED not null,
id_usuario			int UNSIGNED not null,
data_cadastro		timestamp null,
data_alteracao		timestamp null,
ativo				bool,

INDEX(id_liga_usuario),
primary key(id_liga_usuario),
foreign key (id_liga) references tb_liga(id_liga),
foreign key (id_usuario) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*cria a tabela grupo*/
create table tb_grupo(
id_grupo				bigint UNSIGNED auto_increment not null,
id_liga					int UNSIGNED not null,
id_usuario				int UNSIGNED not null,
nome_grupo				varchar(30),
descricao_grupo			varchar(100),
descricao_premio_grupo	varchar(100),
url_imagem				varchar(255) not null,
data_criacao			timestamp null,
data_alteracao			timestamp null,
fechado					bool,
pago					bool,
valor					float,

INDEX(id_grupo),
primary key(id_grupo),
foreign key (id_liga) references tb_liga(id_liga),
foreign key (id_usuario) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*grupos do usuario*/
create table tb_grupo_usuario(
id_grupo_usuario	bigint UNSIGNED auto_increment not null,
id_grupo			bigint UNSIGNED not null,
id_liga				int UNSIGNED not null,
id_usuario			int UNSIGNED not null,
data_cadastro		timestamp null,
data_alteracao		timestamp null,
ativo				bool,

INDEX(id_grupo_usuario),
primary key(id_grupo_usuario),
foreign key (id_grupo) references tb_grupo(id_grupo),
foreign key (id_liga) references tb_liga(id_liga),
foreign key (id_usuario) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*tabela de solicitacao em grupos fechados*/
create table tb_solicitacao_grupo_usuario(
id_solicitacao_grupo_usuario	bigint UNSIGNED auto_increment not null,
id_grupo						bigint UNSIGNED not null,
id_liga							int UNSIGNED not null,
id_usuario_admin				int UNSIGNED not null,
id_usuario_solicitante			int UNSIGNED not null,
data_solicitacao				timestamp null,
data_resposta					timestamp null,
permite_participar				bool,
ativo							bool not null,

INDEX(id_solicitacao_grupo_usuario),
primary key(id_solicitacao_grupo_usuario),
foreign key (id_grupo) references tb_grupo(id_grupo),
foreign key (id_liga) references tb_liga(id_liga),
foreign key (id_usuario_admin) references tb_usuario(id_usuario),
foreign key (id_usuario_solicitante) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*tabela de equipe*/
create table tb_equipe(
id_equipe			int UNSIGNED auto_increment not null,
nome				varchar(30) not null,
sigla_equipe		varchar(3) not null,
url_imagem_equipe	varchar(255) not null,
pais				varchar(30),
sigla_pais			varchar(3),
url_imagem_pais		varchar(255),
data_cadastro		timestamp null,
id_externo_equipe	int UNSIGNED not null,

INDEX(id_equipe),
primary key(id_equipe)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table tb_palpite;
drop table tb_partida;
/*tabela de partida*/
create table tb_partida(
id_partida							int UNSIGNED auto_increment not null,
id_liga								int UNSIGNED not null,
id_equipe_casa						int UNSIGNED not null,
id_equipe_visitante					int UNSIGNED not null,
placar_equipe_casa					int UNSIGNED,
placar_equipe_visitante				int UNSIGNED,
gols_equipe_casa					varchar(255),
gols_equipe_visitante				varchar(255),
fase								varchar(30),
tag_grupo							varchar(10),
local_jogo							varchar(30),
placar_extendido_equipe_casa		varchar(20),
placar_extendido_equipe_visitante	varchar(20),
data_hora_jogo						timestamp null,
tempo_partida 						varchar(50),
id_externo_partida					int UNSIGNED not null,

INDEX(id_partida),
primary key(id_partida),
foreign key (id_liga) references tb_liga(id_liga),
foreign key (id_equipe_casa) references tb_equipe(id_equipe),
foreign key (id_equipe_visitante) references tb_equipe(id_equipe)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*tabela de palpite*/
create table tb_palpite(
id_palpite					bigint UNSIGNED auto_increment not null,
id_partida					int UNSIGNED not null,
id_liga						int UNSIGNED not null,
id_usuario					int UNSIGNED not null,
placar_equipe_casa			int UNSIGNED not null,
placar_equipe_visitante		int UNSIGNED not null,
pontos						int UNSIGNED,
data_cadastro				timestamp null,
data_alteracao				timestamp null,

INDEX(id_palpite),
primary key(id_palpite),
foreign key (id_partida) references tb_partida(id_partida),
foreign key (id_usuario) references tb_usuario(id_usuario),
foreign key (id_liga) references tb_liga(id_liga)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*tabela de notificacao*/
create table tb_notificacao(
id_notificacao			int UNSIGNED auto_increment not null,
titulo					varchar(255),
mensagem				varchar(255),
data_cadastro			timestamp null,
id_usuario				int UNSIGNED,
toque_notificacao		tinyint(1),

INDEX(id_notificacao),
primary key(id_notificacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*tabela de notificacao usuario*/
create table tb_notificacao_usuario(
id_notificacao_usuario		int UNSIGNED auto_increment not null,
id_notificacao				int UNSIGNED,
id_usuario					int UNSIGNED,
data_recebimento			timestamp null,

INDEX(id_notificacao_usuario),
primary key(id_notificacao_usuario),
foreign key (id_notificacao) references tb_notificacao(id_notificacao),
foreign key (id_usuario) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*tabela de planoFundo*/
create table tb_plano_fundo(
id_plano_fundo			int UNSIGNED auto_increment not null,
nome					varchar(50),
url_imagem				varchar(255),

INDEX(id_plano_fundo),
primary key(id_plano_fundo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*tabela de acesso*/
create table tb_acesso(
id_acesso		int UNSIGNED auto_increment not null,
id_usuario		int UNSIGNED not null,
plataforma		varchar(30) not null,
quantidade		int UNSIGNED not null,
ultimo_acesso	timestamp null,

INDEX(id_acesso),
primary key(id_acesso),
foreign key (id_usuario) references tb_usuario(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table tb_acesso_dados;
/*tabela de acesso*/
create table tb_acesso_dados(
id_acesso_dados		int UNSIGNED auto_increment not null,
id_acesso			int UNSIGNED not null,
sistema 			varchar(255) DEFAULT NULL,
dispositivo 		varchar(255) DEFAULT NULL,
detalhe	 			varchar(255) DEFAULT NULL,
descricao 			varchar(255) DEFAULT NULL,
user_ip 			varchar(100) DEFAULT NULL,
user_agent 			varchar(255) DEFAULT NULL,
data_acesso	timestamp null,

INDEX(id_acesso),
primary key(id_acesso_dados),
foreign key (id_acesso) references tb_acesso(id_acesso)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table tb_compra_liga(
id_compra_liga			int UNSIGNED auto_increment not null,
id_usuario				int UNSIGNED not null,
id_liga					int UNSIGNED not null,
id_produto		 		varchar(100),
status_pagamento		varchar(100),
id_pagamento			varchar(255),
id_transacao			varchar(255),
descricao_compra		varchar(100),
data_compra				timestamp null,

INDEX(id_compra_liga),
primary key(id_compra_liga),
foreign key (id_usuario) references tb_usuario(id_usuario),
foreign key (id_liga) references tb_liga(id_liga)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*scripts de insert*/
select * from tb_plano_fundo;
select * from tb_avatar;
select * from tb_liga;
select * from tb_liga_usuario;
select * from tb_equipe;
select * from tb_partida;
select * from tb_palpite;
select * from tb_usuario;
select * from tb_log_token;
select * from tb_acesso;
select * from tb_acesso_dados;



SELECT NOW();

-- truncate tb_partida;
select count(*) from tb_partida as p 
where p.data_hora_jogo between date_sub(sysdate(), INTERVAL '3' DAY_HOUR) and sysdate() 
and ifnull(p.tempo_partida, 'Vazio') != 'Encerrado';



select * from tb_liga where id_liga = 2;
select * from tb_partida where id_liga = 2;
select * from tb_indicacao_liga;
select * from tb_grupo;
select * from tb_grupo_usuario;
select * from tb_usuario;


select u.id_usuario, u.nome, a.url_imagem
from tb_usuario as u 
inner join tb_avatar as a on u.id_avatar = a.id_avatar
where LOWER(u.nome) like LOWER('natalia') 
and u.id_usuario not in 
(select gu.id_usuario from tb_grupo_usuario as gu where id_grupo = 0 and ativo = 1) 
order by u.nome asc limit 1, 30

