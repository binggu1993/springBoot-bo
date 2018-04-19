package com.avit.itdap.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.avit.itdap.bean.system.AssetInfo;

public interface AssetInfoRepository extends JpaRepository<AssetInfo,Long>,JpaSpecificationExecutor<AssetInfo> {
	/**
	 * 返回assetId对应的媒资信息，返回结果应当唯一，唯一性由数据库中uniquen索引约束
	 * @param assetId
	 * @return
	 */
	@Query("from AssetInfo where asset_id=?")
	public AssetInfo findUniquenByAssetId(String assetId);
}
