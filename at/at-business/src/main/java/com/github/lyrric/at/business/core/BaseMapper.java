package com.github.lyrric.at.business.core;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created on 2018/6/7.
 * 这个mapper不能被扫描到
 * @author wangxiaodong
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
