# Puc Minas
Repositório dedicado ao projeto integrado para obtenção do titulo de Arquitetura de Software Puc Minas

<h2>Trabalho de conclusão do curso de Pós-graduação Lato Sensu em Arquitetura de Software Distribuído da PUC Minas</h2>

Para obtenção do título de Arquiteto de Software Distribuído, proponho o desenvolvimento de uma plataforma de palpites em partidas esportivas (iniciada pelo futebol como case de validação), que permita aos usuários realizarem palpites em campeonatos previamente cadastrados, realizar o acompanhamento das partidas e pontuação em tempo real, utilizando de gamificação para estimular a competição saudável entre os jogadores, fornecendo dados estatísticos para apoio a decisão no "pitaco", além de criação de grupos, exibição de rankings diferenciados entre campeonatos e dentro de cada grupo, entre outras funcionalidades.
O domínio da aplicação será <a>https://www.pitaco.com.br/</a> (ainda em desenvolvimento e com previsão de lançamento em outubro de 2022).

Apresentação inicial do projeto no link: <a>https://youtu.be/GDod6zBNGkk</a>

Apresentação final do projeto no link: <a>https://www.youtube.com/watch?v=teH5y3-bFkQ</a>

<h3>Descrição do projeto</h3>
O projeto foi construído utilizando o conceito de arquitetura em Cloud, e com componentes/módulos do framework Spring. O projeto está dividido em 10 aplicações:

<h4>:heavy_check_mark: pitaco-web</h4>

Aplicação web (SPA).

<h4>:heavy_check_mark: pitaco-gateway</h4>

Responsável por receber nossas requisições, fazer o load balance e redirecionar para o serviço solicitado pelo cliente.

<h4>:heavy_check_mark: pitaco-config-server</h4>

Responsável por carregar e entregar os arquivos .properties das nossas aplicações sempre que necessário.

<h4>:heavy_check_mark: pitaco-service-discovery</h4>

Responsável pelo nosso painel do eureka, é essa aplicação que será chamada para registrar os serviços/aplicações que estiverem rodando e que fazem referência para se inscreverem no eureka informando que estão up.

<h4>:heavy_check_mark: pitaco-crawler</h4>

Responsável por receber varrer páginas web em busca de partidas em andamento e obter as informações em tempo real de partidas pré cadastradas.

<h4>:heavy_check_mark: pitaco-api</h4>

API central da aplicação, responsável por atender requisições de partidas, palpites, ligas e grupos.

<h4>:heavy_check_mark: pitaco-autentication</h4>

API responsável por realizar a autenticação e permissionamento de logins de usuários, validar e renovar sessão de usuários ativos.

<h4>:heavy_check_mark: pitaco-details</h4>

API responsável por disponibilizar dados estatisticos das partidas, artilharia de campeonatos, ranking das competiçoes, maiores pontuadores em cada rodada.

<h4>:heavy_check_mark: pitaco-notification</h4>

API responsável por realizar a notificação de usuários.

<h4>:heavy_check_mark: pitaco-utilities</h4>

Serviço utilitário responsável por entregar beans comuns para os serviços, sendo necessário aos serviços apenas habilitar os beans que precisa e importar as dependencias especificas.

<h3>Ferramentas e tecnologias</h3>

:ballot_box_with_check: Angular 8+

:ballot_box_with_check: Java 11

:ballot_box_with_check: Spring Boot

:ballot_box_with_check: Spring Data JPA

:ballot_box_with_check: Hikari

:ballot_box_with_check: Banco de dados MySql

:ballot_box_with_check: Lombok

:ballot_box_with_check: Swagger

:ballot_box_with_check: Modelo arquitetural REST

:ballot_box_with_check: Spring Cloud

:ballot_box_with_check: Spring Actuator

:ballot_box_with_check: Spring Cloud Config Server

:ballot_box_with_check: Spring Gateway

:ballot_box_with_check: Spring Cache

:ballot_box_with_check: Netflix Eureka
