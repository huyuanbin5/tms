package com.wh.tms.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wh.tms.constans.SystemCons;
import com.wh.tms.dao.sys.IDeptDAO;
import com.wh.tms.dao.user.IMemberDAO;
import com.wh.tms.entity.po.Dept;
import com.wh.tms.entity.po.Member;
import com.wh.tms.result.Result;
import com.wh.tms.service.IMemberService;
import com.wh.tms.util.EncodeUtils;
import com.wh.tms.util.ResultUtils;

@Service("memberService")
public class MemberServiceImpl implements IMemberService {
	
	private static final Log log = LogFactory.getLog(MemberServiceImpl.class);
	
	@Autowired
	private IMemberDAO memberDao;
	@Autowired
	private IDeptDAO deptDao;

	@Override
	public PageInfo<Member> getMemberList(Integer uid,Integer deptid, String uname,
			Integer page, Integer pageSize) {
		
		PageHelper.startPage(page, pageSize);
		Example example = new Example(Member.class);
		Criteria criteria = example.createCriteria();
		if (null != uid) {
			criteria.andEqualTo("id", uid);
		}
		if (null != deptid) {
			criteria.andEqualTo("deptId", deptid);
		}
		if (StringUtils.isNotBlank(uname)) {
			criteria.andLike("name", uname);
		}
		example.orderBy(" id ").desc();
		List<Member> list = null;
		try {
			list = memberDao.selectByExample(example);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return new PageInfo<Member>(list);
	}

	@Override
	public Result updateMember(Integer uid,Integer deptId,Integer teamId,String mName,Integer sex) {
		if (null == uid) {
			return ResultUtils.error(SystemCons.ERROR_EMPTY_PARAMS, "员工id不能为空！");
		}
		Member user = memberDao.selectByPrimaryKey(uid);
		if (null == user) {
			return ResultUtils.error(SystemCons.FAILURE, "员工不存在！");
		}
		if (StringUtils.isNotBlank(mName)) {
			try {
				mName = EncodeUtils.urlDecode(mName);
			} catch (Exception e) {
				return ResultUtils.paramsEmpty("请假差员工名称编码!");
			}
			user.setName(mName);
		}
		//TODO 查询部门
		if (null != deptId) {
			Dept dept = deptDao.selectByPrimaryKey(deptId);
			if (null == dept) {
				return ResultUtils.paramsEmpty("部门不存在!");
			}
			user.setDeptId(deptId);
			user.setDeptName(dept.getDeptName());
		}
		
		if (null != teamId) {
			Dept team = deptDao.selectByPrimaryKey(teamId);
			if (null == team) {
				return ResultUtils.paramsEmpty("工作队不存在!");
			}
			user.setTeamId(teamId);
			user.setTeamName(team.getDeptName());
		}
		if (null != sex) {
			if (sex.intValue() < 1 || sex.intValue() > 2) {
				return ResultUtils.error(SystemCons.FAILURE, "未知性别！");
			}
		}
		int updateCount = 0;
		try {
			updateCount = memberDao.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if (updateCount < 1) {
			return ResultUtils.error(SystemCons.FAILURE, "更新失败！");
		}
		return ResultUtils.success(updateCount, user);
	}

	@Override
	public Result deleteMember(Integer uid) {
		if (null == uid) {
			return ResultUtils.paramsEmpty("员工id不能为空!");
		}
		Member member = memberDao.selectByPrimaryKey(uid);
		if (null == member) {
			return ResultUtils.failure("员工不存在!");
		}
		int delCount = memberDao.delete(member);
		if (delCount > 0) {
			return ResultUtils.success(1, null, "删除成功!");
		}
		return ResultUtils.failure("删除失败!");
	}

	@Override
	public Result addMember(Integer deptId, Integer teamId,String mName, Integer sex) {
		if (StringUtils.isBlank(mName)) {
			return ResultUtils.paramsEmpty("员工名称不能为空!");
		}
		if (null == deptId) {
			return ResultUtils.paramsEmpty("部门id不能为空!");
		}
		if (null == sex) {
			return ResultUtils.paramsEmpty("性别不能为空!");
		}
		if (sex.intValue() < 1 || sex.intValue() > 2) {
			return ResultUtils.error(SystemCons.FAILURE, "未知性别！");
		}
		try {
			mName = EncodeUtils.urlDecode(mName);
		} catch (Exception e) {
			return ResultUtils.paramsEmpty("员工名称编码错误!");
		}
		Member member = new Member();
		member.setInsertTime(new Date());
		member.setName(mName);
		member.setSex(sex);
		Dept dept = deptDao.selectByPrimaryKey(deptId);
		if (null == dept) {
			return ResultUtils.paramsEmpty("部门不存在!");
		}
		member.setDeptId(deptId);
		member.setDeptName(dept.getDeptName());
		if (null != teamId) {
			Dept team = deptDao.selectByPrimaryKey(teamId);
			if (null == team) {
				return ResultUtils.paramsEmpty("工作队不存在!");
			}
			member.setTeamId(teamId);
			member.setTeamName(team.getDeptName());
		}
		
		int insertCount = memberDao.insertSelective(member);
		if (insertCount > 0) {
			return ResultUtils.success(1, member);
		}
		return ResultUtils.failure("添加失败!");
	}

}
