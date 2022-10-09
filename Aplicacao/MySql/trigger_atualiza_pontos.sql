delimiter |

CREATE TRIGGER trigger_atualiza_pontos_partida AFTER UPDATE ON tb_partida
  FOR EACH ROW
  BEGIN
	DECLARE done INT DEFAULT FALSE;
    DECLARE id BIGINT;
    DECLARE placarCasa, placarVisitante BIGINT;
    DECLARE cursorPartidas CURSOR FOR (
	SELECT p.id_palpite, p.placar_equipe_casa, p.placar_equipe_visitante FROM tb_palpite as p where p.id_partida = OLD.id_partida
	);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    IF NEW.placar_equipe_casa <> OLD.placar_equipe_casa  OR NEW.placar_equipe_visitante <> OLD.placar_equipe_visitante THEN
    
		OPEN cursorPartidas;
		read_loop: LOOP
		FETCH cursorPartidas INTO id, placarCasa, placarVisitante;
			IF done THEN
				LEAVE read_loop;
			END IF;
			
			IF placarCasa = NEW.placar_equipe_casa and placarVisitante = NEW.placar_equipe_visitante THEN
			  -- vale 5 pontos
			  UPDATE tb_palpite set pontos = 5 where id_palpite = id;
			ELSEIF placarCasa > placarVisitante and NEW.placar_equipe_casa > NEW.placar_equipe_visitante THEN
				-- time casa venceu
				IF (placarCasa - placarVisitante) = (NEW.placar_equipe_casa - NEW.placar_equipe_visitante) THEN
					UPDATE tb_palpite set pontos = 3 where id_palpite = id;
				ELSE
					UPDATE tb_palpite set pontos = 1 where id_palpite = id;
				END IF;
			ELSEIF placarCasa < placarVisitante and NEW.placar_equipe_casa < NEW.placar_equipe_visitante THEN
				-- time visitante venceu
				IF (placarVisitante - placarCasa) = (NEW.placar_equipe_visitante - NEW.placar_equipe_casa) THEN
					UPDATE tb_palpite set pontos = 3 where id_palpite = id;
				ELSE
					UPDATE tb_palpite set pontos = 1 where id_palpite = id;
				END IF;
			ELSEIF placarCasa = placarVisitante and NEW.placar_equipe_casa = NEW.placar_equipe_visitante THEN
				UPDATE tb_palpite set pontos = 3 where id_palpite = id;
			  ELSE
				UPDATE tb_palpite set pontos = 0 where id_palpite = id;
			END IF;
			
		END LOOP;
		CLOSE cursorPartidas;

    END IF;
  END;
|

delimiter ;



-- DROP TRIGGER trigger_atualiza_pontos_partida;