package com.zr.test.demo.config.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lx
 * @date 2019/6/20 11:09
 */
public interface CacheUtil {

	/**
	 * 设置缓存数据,默认1年有效
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, Object value);

	/**
	 * 得到缓存数据
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 设置缓存数据
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	boolean set(String key, Object value, Long time);

	/**
	 *删除某个key
	 * @param key
	 * @return
	 */
	void delete(String key);

	/**
	 * 队列推送数据
	 * @param key
	 * @param value
	 * @return
	 */
	boolean push(String key, Object value);

	/**
	 * 队列推出数据
	 * @param key
	 * @return
	 */
	Object pop(String key);

	/**
	 * 队列推出指定数量数据
	 * @param key
	 * @param size
	 * @param wait
	 * @return
	 */
	List<Object> popList(String key, int size, int wait);

	/**
	 * 队列大小
	 * @param key
	 * @return
	 */
	Long size(String key);

	/**
	 * 添加Hash缓存
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	boolean put(String key, Object hashKey, Object value);

    /**
     * 添加数据
     * @param key
     * @param map
     * @return
     */
    boolean putAll(String key, Map<Object, Object> map);

	/**
	 * 获取Hash缓存值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	Object get(String key, Object hashKey);
	/**
	 * 获取set缓存值
	 * @param key
	 * @return
	 */
	Set getSetValue(String key);
	/**
	 * 得到所有hash数据
	 * @param key
     * @return
	 */
	Map entries(String key);

	/**
	 * 删除指定hash记录
	 * @param key
	 * @param hashKey
	 * @return
	 */
	boolean delete(String key, Object hashKey);
}
