package com.avit.itdap.repository.system;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.avit.itdap.bean.system.ChannelGroupRef;

public interface ChannelGroupRefRepository extends JpaRepository<ChannelGroupRef,Long>{
	@Modifying
	@Transactional
	@Query(value="delete from ChannelGroupRef where parentId=?")
	public void deleteByParentId(Long parentId);
	
	@Modifying
    @Transactional
    @Query(value="delete from ChannelGroupRef where sub.id=?")
    public void deleteBySubId(long id);
	
}
