package com.avit.itdap.repository.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.avit.itdap.bean.system.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> 
{
	public Page<Menu> findByName(String name, Pageable page);
	
	
	/*	@Query(value="select pid from t_nms_menu t1 where t1.url = :url )")//原生sql语句
		public List<Menu> findCommonMenus(@Param("url")String url);*/
	
	//查找菜单 
	public List<Menu> findByPidOrderBySortAsc(long  pid);
	
	
}
