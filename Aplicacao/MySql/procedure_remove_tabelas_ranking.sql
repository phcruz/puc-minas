DELIMITER |

CREATE PROCEDURE deleta_tabela_temporaria_ranking()

BEGIN
	DROP TABLE IF EXISTS tb_ranking_ordenado;
	DROP TABLE IF EXISTS tb_ranking;
END |

DELIMITER ;
