package com.avit.itdap.repository.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.avit.itdap.bean.system.Channel;

public interface ChannelRepository extends JpaRepository<Channel,Long>,JpaSpecificationExecutor<Channel>{
	public Page<Channel> findByChannelName(String name, Pageable page);
	
	@Query(value="from Channel where serviceId=?")
	public Channel findByServiceID(long serviceID);
	
	
	public Channel findChannelByChannelCode(String channelCode);
	
	@Query(value="from Channel where channelType=?")
    public List<Channel> findByChannelType(int type);

	
	public List<Channel> findChannelByChannelName(String channelName);
}
