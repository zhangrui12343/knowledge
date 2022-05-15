package com.zr.test.demo.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 *
 * @author lx
 * @date 2019/6/20 10:41
 */
@SuppressWarnings("unchecked")
@Component
public class LocalUtil implements CacheUtil {

	/**
	 * 默认值
	 */
	private static Logger log = LoggerFactory.getLogger(LocalUtil.class);

	/**
	 * 根据时间缓存
	 */
	private static Map<Long,Cache<String,Object>> cacheMap = new ConcurrentHashMap<>();

	/**
	 * 根据Key缓存
	 */
	private static Map<String,Cache<String,Object>> cacheKeyMap = new ConcurrentHashMap<>();

    /**
     * 队列管理
     */
    private static Map<String,BlockingQueue<Object>> queueMap = new ConcurrentHashMap<>();

    /**
     * hashMap存储结构
     */
    private static Map<String,Map<Object,Object>> hashMap = new ConcurrentHashMap<>();

	/**
	 * 有效时间
	 */
	private Long effectiveTime;

	public LocalUtil() {
	}

	public LocalUtil(Long effectiveTime){
		this.effectiveTime = effectiveTime;
	}

	/**
	 * 设置数据
	 */
	private boolean put(String key, Object value, Long time){

		if(time == null && this.effectiveTime == null) {
			log.error("缓存有效期设置错误");
			return false;
		}else{
            time = time != null ? time : this.effectiveTime;
		}

		Cache<String,Object> cache = null;
		if(cacheMap.containsKey(time)) {
			cache = cacheMap.get(time);
		} else{
			cache = Caffeine.newBuilder().expireAfterAccess(time, TimeUnit.SECONDS).maximumSize(10000).build();
		}
		try {
			cache.put(key,value);
			cacheMap.put(time,cache);
			cacheKeyMap.put(key,cache);
			return true;
		}catch (Exception e){
			log.error("本地缓存保存数据异常，key:"+key);
			return false;
		}
	}

	/**
	 * 设置数据,永久有效
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean set(String key, Object value) {
        long year = 31536000L;
		return put(key,value,year);
	}

	/**
	 * 获得数据
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key) {
        Cache<String,Object> cache = cacheKeyMap.get(key);
		if(cache != null) {
			return cache.getIfPresent(key);
		}else {
			return null;
		}
	}

	/**
	 * 设置缓存数据,带过期时间的
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	@Override
	public boolean set(String key, Object value, Long time) {
		return put(key,value,time);
	}

    /**
     * 删除数据
     * @param key
     */
	@Override
	public void delete(String key) {
		Cache<String,Object> cache = cacheKeyMap.get(key);
		if(cache != null) {
            cache.invalidate(key);
		}
	}
	/**
	 * 队列推送数据
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean push(String key, Object value) {
        BlockingQueue<Object> queue = this.getByKey(key);
        if(value instanceof List) {
            queue.addAll((List) value);
        }else {
            queue.add(value);
        }
        queueMap.put(key,queue);
        return true;
	}

    /**
     * 根据key得到队列
     * @param key
     * @return
     */
	private BlockingQueue<Object> getByKey(String key) {
        BlockingQueue<Object> queue = queueMap.get(key);
        if(queue == null) {
            queue = new LinkedBlockingQueue<>(100000);
        }
        return queue;
    }

	/**
	 * 队列推出数据
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object pop(String key) {
		BlockingQueue<Object> queue = this.getByKey(key);
		try {
			return queue.poll(5,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
            log.error("队列数据获取失败：",e);
		}
		return null;
	}

    /**
     * 队列推出指定数量数据
     *
     * @param key
     * @param size
     * @param wait
     * @return
     */
    @Override
    public List<Object> popList(String key, int size, int wait) {
        List<Object> list = new ArrayList<>();
        BlockingQueue<Object> queue = this.getByKey(key);
        int queueSize = queue.size();
        try {
            if (queueSize < size) {
                Thread.sleep(wait);
                queueSize = queue.size();
            }
            int dealSize = queueSize > size ? size : queueSize;
            if(dealSize > 0) {
                for(int i = 0; i < dealSize; i++) {
                    // 防止队列阻塞
                    if(queue.size() > 0) {
                        list.add(queue.take());
                    }else {
                        break;
                    }
                }
            }
        }catch(Exception e) {
            log.error("获取队列数据失败：",e);
        }
        return list;
    }

    /**
	 * 队列大小
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Long size(String key) {
        BlockingQueue<Object> queue = this.getByKey(key);
		return (long) queue.size();
	}

    /**
     * 根据key获取hashMap
     * @param key
     * @return
     */
	private Map<Object,Object> getHashMap(String key) {
        Map<Object,Object> map = hashMap.get(key);
        if(map == null) {
            map = new ConcurrentHashMap<>(200);
        }
        return map;
    }

	/**
	 * 添加Hash缓存
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 * @return
	 */
	@Override
	public boolean put(String key, Object hashKey, Object value) {
        Map<Object,Object> map = this.getHashMap(key);
        map.put(hashKey,value);
        hashMap.put(key,map);
		return false;
	}

    /**
     * 添加数据
     *
     * @param key
     * @param map
     * @return
     */
    @Override
    public boolean putAll(String key, Map<Object, Object> map) {
        if(map == null) {
            hashMap.remove(key);
        }else {
            hashMap.put(key,map);
        }
        return true;
    }

    /**
	 * 获取Hash缓存值
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	@Override
	public Object get(String key, Object hashKey) {
        Map<Object,Object> map = this.getHashMap(key);
		return map.get(hashKey);
	}

	@Override
	public Set getSetValue(String key) {
		return null;
	}

	/**
	 * 得到所有hash数据
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Map<Object, Object> entries(String key) {
		return hashMap.get(key);
	}

	/**
	 * 删除指定hash记录
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	@Override
	public boolean delete(String key, Object hashKey) {
        Map<Object, Object> map = this.getHashMap(key);
        map.remove(hashKey);
        hashMap.put(key, map);
        return true;
	}
}
