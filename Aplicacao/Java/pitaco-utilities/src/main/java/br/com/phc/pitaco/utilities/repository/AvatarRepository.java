package br.com.phc.pitaco.utilities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phc.pitaco.utilities.model.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

	public Avatar findByIdAvatar(Long idAvatar);
	
	public void deleteByIdAvatar(Long idAvatar);
}
