package com.wh.tms.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

/**
 * 
* @ClassName: IBaseDAO 
* @Description: TODO 
* @author Huqk
* @date 2018年1月20日 下午7:02:56 
* @param <T>
 */
public interface IBaseDAO<T> {
    /**
     * 	
     * 功能描述: <br>
     * 新增
     *
     * @param t
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int insert(T t);
    /**
     * 
     * 功能描述: <br>
     * 更新
     *
     * @param t
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int update(T t);
    /**
     * 
     * 功能描述: <br>
     * 根据id获取
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    T getById(Serializable id);
    /**
     * 
     * 功能描述: <br>
     * 获取列表
     *
     * @param obj
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<T> getByParam(Object obj);
    /**
     * 
     * 功能描述: <br>
     * 获取总数
     *
     * @param obj
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int getByCountParam(Object obj);
    /**
     * 
     * 功能描述: <br>
     * 删除
     *
     * @param id
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    void del(Serializable id);
    /**
     * 
     * 功能描述: <br>
     * 查询分页
     *
     * @param page
     * @param map page.condition
     * @param obj
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<T> getPage(Page<T> page, Map<String, Object> map);

}
