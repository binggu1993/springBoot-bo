package com.avit.itdap.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.avit.itdap.bean.system.Area;

public interface AreaRepository extends JpaRepository<Area,Long>,JpaSpecificationExecutor<Area> {

}
