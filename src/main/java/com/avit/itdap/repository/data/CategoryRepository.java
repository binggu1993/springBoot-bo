package com.avit.itdap.repository.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.avit.itdap.bean.xml.CategoryInfo;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryInfo,Long>,JpaSpecificationExecutor<CategoryInfo>{
	@Modifying
	@Transactional
	@Query("delete from CategoryInfo where spCode=?")
	public void deleteCategoryBySpCode(String spCode);
	@Query("from CategoryInfo where categoryType=4 and spCode=?")
	public List<CategoryInfo> findCategoryBySpCode(String spCode);
}
