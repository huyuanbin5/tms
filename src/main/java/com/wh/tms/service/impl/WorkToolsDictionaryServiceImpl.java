package com.wh.tms.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.wh.tms.dao.wt.IWorkToolsDictionaryDAO;
import com.wh.tms.entity.po.WorkToolsDictionary;
import com.wh.tms.result.Result;
import com.wh.tms.service.IWorkToolsDictionaryService;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("workToolsDictionaryService")
public class WorkToolsDictionaryServiceImpl implements IWorkToolsDictionaryService {
	
	@Autowired
	private IWorkToolsDictionaryDAO workToolsDictionaryDao;

	@Override
	public Result addDictionary(Integer type, String name) {
		if (null == type) {
			return ResultUtils.paramsEmpty("类型不能为空！");
		}
		if (StringUtils.isBlank(name)) {
			return ResultUtils.paramsEmpty("名称不能为空！");
		}
		try {
			name = EncodeUtils.urlDecode(name);
		} catch (Exception e) {
			return ResultUtils.paramsEmpty("名称编码错误！");
		}
		WorkToolsDictionary wtd = new WorkToolsDictionary();
		wtd.setType(type);
		wtd.setName(name);
		int saveCount = workToolsDictionaryDao.insertSelective(wtd);
		if (saveCount > 0) {
			return ResultUtils.success(wtd);
		}
		return ResultUtils.failure("添加失败！");
	}

	@Override
	public Result getDictionaryList(Integer type) {
		Example ex = new Example(WorkToolsDictionary.class);
		Example.Criteria criteria = ex.createCriteria();
		if (null != type) {
			criteria.andEqualTo("type", type);
		}
		List<WorkToolsDictionary> list = workToolsDictionaryDao.selectByExample(ex);
		long totalCount = 0;
		if (null != list && list.size() > 0) {
			totalCount = list.size();
		}
		return ResultUtils.success(totalCount, list);
	}

	@Override
	public Result updateDictionary(Integer id, Integer type, String name) {
		if (null == id) {
			return ResultUtils.paramsEmpty("id不能为空！");
		}
		WorkToolsDictionary wtd = workToolsDictionaryDao.selectByPrimaryKey(id);
		if (null == wtd) {
			return ResultUtils.failure("数据不存在！");
		}
		if (null != type) {
			wtd.setType(type);
		}
		if (StringUtils.isNotBlank(name)) {
			try {
				name = EncodeUtils.urlDecode(name);
			} catch (Exception e) {
				return ResultUtils.paramsEmpty("名称编码错误！");
			}
			wtd.setName(name);
		}
		int updateCount = workToolsDictionaryDao.updateByPrimaryKeySelective(wtd);
		if (updateCount > 0) {
			return ResultUtils.success(1, wtd);
		}
		return ResultUtils.failure("更新失败!");
	}

	@Override
	public Result deleteDictionary(Integer id) {
		if (null == id) {
			return ResultUtils.paramsEmpty("id不能为空！");
		}
		WorkToolsDictionary wtd = workToolsDictionaryDao.selectByPrimaryKey(id);
		if (null == wtd) {
			return ResultUtils.failure("数据不存在！");
		}
		int delCount = workToolsDictionaryDao.delete(wtd);
		if (delCount > 0) {
			return ResultUtils.success(1, null, "删除成功！");
		}
		return ResultUtils.failure("删除失败!");
	}

}
