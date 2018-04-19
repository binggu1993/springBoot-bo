package com.avit.itdap.repository.system;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.avit.itdap.bean.system.ChannelEPG;

@Repository
public interface ChannelEPGRepository extends JpaRepository<ChannelEPG,Long>,JpaSpecificationExecutor<ChannelEPG>
{
   @Modifying
   @Query("delete from ChannelEPG where startTime >= ?1 and startTime<=?2")
   public void deleteByStartTimeBetween(Date startTime,Date endTime);
}
