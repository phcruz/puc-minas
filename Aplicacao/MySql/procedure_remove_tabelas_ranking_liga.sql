DELIMITER |

CREATE PROCEDURE deleta_tabela_temporaria_ranking()

BEGIN
	DROP TABLE IF EXISTS tb_ranking_ordenado_liga;
	DROP TABLE IF EXISTS tb_ranking_liga;
END |

DELIMITER ;
