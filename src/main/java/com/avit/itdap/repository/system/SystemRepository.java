package com.avit.itdap.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.avit.itdap.bean.system.SystemBean;

@Repository
public interface SystemRepository extends JpaRepository<SystemBean,Long>,JpaSpecificationExecutor<SystemBean>{
	
}
