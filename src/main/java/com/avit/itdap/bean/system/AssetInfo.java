package com.avit.itdap.bean.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_asset_info")
public class AssetInfo {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "asset_name")
	private String assetName;
	
	@Column(name = "asset_id")
	private String assetId;
	
	@Column(name = "asset_length")
	private long assetLength;
	
	@Column(name = "is_package")
	private int isPackage;
	
	@Column(name = "prarent_id")
	private String parentId;
	
	@Column(name = "is_hd")
	private int isHd;
	
	@Column(name = "sp_code")
	private String spCode;
	
	@Column(name = "asset_state")
	private int assetState;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public long getAssetLength() {
		return assetLength;
	}

	public void setAssetLength(long assetLength) {
		this.assetLength = assetLength;
	}

	public int getIsPackage() {
		return isPackage;
	}

	public void setIsPackage(int isPackage) {
		this.isPackage = isPackage;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getIsHd() {
		return isHd;
	}

	public void setIsHd(int isHd) {
		this.isHd = isHd;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public int getAssetState() {
		return assetState;
	}

	public void setAssetState(int assetState) {
		this.assetState = assetState;
	}

	@Override
	public String toString() {
		return "AssetInfo [id=" + id + ", assetName=" + assetName + ", assetId=" + assetId + ", assetLength="
				+ assetLength + ", isPackage=" + isPackage + ", parentId=" + parentId + ", isHd=" + isHd + ", spCode="
				+ spCode + ", assetState=" + assetState + "]";
	}
	
	
}
