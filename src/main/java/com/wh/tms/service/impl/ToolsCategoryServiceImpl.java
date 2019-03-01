package com.wh.tms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.wh.tms.dao.tools.IToolsCategoryDAO;
import com.wh.tms.entity.po.ToolsCategory;
import com.wh.tms.result.Result;
import com.wh.tms.service.IToolsCategoryService;
import com.wh.tms.util.ResultUtils;

@Service("toolsCategoryService")
public class ToolsCategoryServiceImpl implements IToolsCategoryService {
	
	@Autowired
	private IToolsCategoryDAO toolsCategoryDao;

	@Override
	public Result getToolsCategoryList(Integer managerCategoryId) {
		
		Example ex = new Example(ToolsCategory.class);
		if (null != managerCategoryId) {
			ex.createCriteria().andEqualTo("managerCategoryId", managerCategoryId);
		}
		List<ToolsCategory> list = toolsCategoryDao.selectByExample(ex);
		long totalCount = 0;
		if (null != list) {
			totalCount = list.size();
		}
		return ResultUtils.success(totalCount, list);
	}

}
