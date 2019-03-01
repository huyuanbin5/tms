package com.wh.tms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wh.tms.dao.tools.IToolsTimeLimitDAO;
import com.wh.tms.entity.po.ToolsTimeLimit;
import com.wh.tms.result.Result;
import com.wh.tms.service.IToolsTimeLimitService;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("toolsTimeLimitService")
public class ToolsTimeLimitServiceImpl implements IToolsTimeLimitService {
	
	@Autowired
	private IToolsTimeLimitDAO toolsTimeLimitDao;
	

	@Override
	public Result getToolsTimeLimitList(Integer id,Integer categoryType) {
		List<ToolsTimeLimit> list = null;
	
		Example ex = new Example(ToolsTimeLimit.class);
		Example.Criteria criteria = ex.createCriteria();
		if (null != id) {
			criteria.andEqualTo("id", id);
		}
		if (null != categoryType) {
			criteria.andEqualTo("categoryType", categoryType);
		}
		list = toolsTimeLimitDao.selectByExample(ex);
		
		return ResultUtils.success(list.size(),list,"获取列表成功！");
	}

	@Override
	@Transactional
	public Result updateTimelimit(String timelimits) {
		if (StringUtils.isBlank(timelimits)) {
			return ResultUtils.paramsEmpty("时限不能为空!");
		}
		try {
			timelimits = EncodeUtils.urlDecode(timelimits);
		} catch (Exception e) {
			return ResultUtils.paramsEmpty("请检查编码!");
		}
		JSONArray array = null;
		try {
			array = JSONArray.parseArray(timelimits);
		} catch (Exception e) {
			return ResultUtils.paramsEmpty("时限参数结构错误，请检查!");
		}
		if (array.isEmpty()) {
			return ResultUtils.paramsEmpty("时限不能为空!");
		}
		int size = array.size();
		for (int i = 0; i < size; i++) {
			JSONObject obj = array.getJSONObject(i);
			if (null == obj) {
				return ResultUtils.paramsEmpty("参数不能为空!");
			}
			Integer id = obj.getInteger("id");
			if (null == id) {
				return ResultUtils.paramsEmpty("id不能为空!");
			}
			Integer limit = obj.getInteger("limit");
			if (null == limit) {
				return ResultUtils.paramsEmpty("时限不能为空!");
			}
			ToolsTimeLimit ttl = toolsTimeLimitDao.selectByPrimaryKey(id);
			if (null == ttl) {
				return ResultUtils.failure("未找到对应作业，请检查id参数!");
			}
		}
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			JSONObject obj = array.getJSONObject(i);
			Integer id = obj.getInteger("id");
			Integer limit = obj.getInteger("limit");
			ToolsTimeLimit ttl = toolsTimeLimitDao.selectByPrimaryKey(id);
			ttl.setLimitTime(limit);
			int updateCount = toolsTimeLimitDao.updateByPrimaryKeySelective(ttl);
			if (updateCount > 0) {
				ids.add(id);
			}
		}
		Example ex = new Example(ToolsTimeLimit.class);
		ex.createCriteria().andIn("id", ids);
		
		long count = 0;
		List<ToolsTimeLimit> list = toolsTimeLimitDao.selectByExample(ex);
		if (null != list && list.size() > 0) {
			count = list.size();
		}
		return ResultUtils.success(count, list, "更新成功!");
	}

}
