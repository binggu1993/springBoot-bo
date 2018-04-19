package com.avit.itdap.service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.avit.itdap.bean.system.Channel;
import com.avit.itdap.bean.system.ChannelGroupRef;
import com.avit.itdap.dto.system.ChannelDto;
import com.avit.itdap.repository.system.ChannelRepository;

@Service
public class ChannelService {

	@Autowired
	ChannelRepository channelRepository;

	/**
	 * 多条件查询
	 * 
	 * @param
	 * @return
	 */
	public Page<Channel> findAllByConditions(final ChannelDto dto,
			Pageable pageable) {

		Page<Channel> result = channelRepository.findAll(
				new Specification<Channel>() {
					@Override
					public Predicate toPredicate(Root<Channel> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						List<Predicate> list = new ArrayList<Predicate>();

						if (!StringUtils.isEmpty(dto.getChannelName())) {
							list.add(cb.like(
									root.get("channelName").as(String.class),
									"%" + dto.getChannelName() + "%"));
						}

						if (!StringUtils.isEmpty(dto.getChannelCode())) {
							list.add(cb.like(
									root.get("channelCode").as(String.class),
									"%" + dto.getChannelCode() + "%"));
						}
						list.add(cb.equal(
								root.get("channelType").as(Integer.class),
								dto.getChannelType()));

						Predicate[] p = new Predicate[list.size()];
						return cb.and(list.toArray(p));
					}
				}, pageable);
		return result;
	}
	
	
	
	public List<Channel>  getChannelsByType(Integer channelType)
	{
		List<Channel> list= channelRepository.findByChannelType(channelType);
		if(list!=null&&list.size()>0)
		{
			for(Channel channel:list)
			{
				if(channel.getSubChannels()!=null)
				{
					String subServiceId="";
					for(ChannelGroupRef group:channel.getSubChannels())
					{
						subServiceId+=group.getSub().getServiceId()+",";
					}
					if(subServiceId.endsWith(","))
					{
						subServiceId=subServiceId.substring(0,subServiceId.lastIndexOf(","));
					}
					channel.setSubServiceIds(subServiceId);
				}
			}
		}
		return list;
	}
	
	
	

	public  void importFile(String fileName, MultipartFile file,
			String destPath, String destName) throws Exception {
		File uploadDir = new File(destPath);
		if (!uploadDir.exists())
			uploadDir.mkdirs();
		// 新建一个文件
		File tempFile = new File(destPath + destName);
		try {
			// 将上传的文件写入新建的文件中
			file.transferTo(tempFile);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	public String pareseChannel(String fileName) throws Exception {
		String result="";
		FileReader fr = null;
		BufferedReader br = null;
		try {
			File f = new File(fileName);
			fr  = new FileReader(f);
			br = new BufferedReader(fr);
			String tmp = "";
			int i = 0;
			while((tmp=br.readLine())!=null){
				String[] tmpArray = tmp.split(",");
				i++;
				if(tmpArray.length!=3||!validateArray(tmpArray)){
					result = result+i+",";
					continue;
				}else{
					Channel channel = new Channel();
					channel.setChannelCode(tmpArray[0]);
					channel.setChannelName(tmpArray[2]);
					channel.setChannelType(1);
					channel.setServiceId(Long.parseLong(tmpArray[1]));
					channelRepository.save(channel);
				}
				
			}			
			br.close();
			br = null;
			fr.close();
			fr = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if(br!=null){
				br.close();
				br = null;
			}
			if (fr != null) {
				fr.close();
				fr = null;
			}
		}
		return result;
	}
	private boolean validateArray(String[] array){
		for(String tmp:array){
			if(StringUtils.isEmpty(tmp.trim())){
				return false;				
			}
		}
		try{
			long serviceID = Long.parseLong(array[1]);
			if(serviceID==0)
				return false;
			Channel channel = channelRepository.findByServiceID(serviceID);
			if(channel!=null)
				return false;
		}catch(Exception e){
			return false;
		}
		return true;
	}



    public List<Channel> getChannelsByGroupId(long id)
    {
        List<Channel> channelsList=new ArrayList<Channel>();
        Channel groupChannel =  channelRepository.findOne(id);
        if(groupChannel!=null&&groupChannel.getSubChannels()!=null){
            for(ChannelGroupRef ref:groupChannel.getSubChannels()){
                channelsList.add(ref.getSub());
            }
        }
        return channelsList;
    }

}
