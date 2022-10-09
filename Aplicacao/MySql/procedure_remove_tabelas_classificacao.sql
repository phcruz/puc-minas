DELIMITER |

CREATE PROCEDURE deleta_tabela_temporaria_classificacao_geral()

BEGIN
	DROP TABLE IF EXISTS tb_classificacao_geral_ordenado;
	DROP TABLE IF EXISTS tb_classificacao_geral;
END |

DELIMITER ;
