package cn.iocoder.yudao.framework.redis.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil() {
    }

    /**
     * 给某个 key 设置过期时间
     * @param key 键
     * @param time 时间(秒)
     * @return boolean
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取过期时间
     * @param key 键
     * @return 获取过期时间（秒）
     */
    public long getExpire(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @param key 键
     * @return 是否存在某个键
     */
    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception var3) {
            Exception e = var3;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除键
     * @param key 键
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(key[0]);
            } else {
                this.redisTemplate.delete(Arrays.asList(key));
            }
        }

    }

    /**
     * 获取缓存
     * @param key 键
     * @return 缓存对象
     */
    public Object get(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key 键
     * @param value 值
     * @return 是否设置成功
     */
    public boolean set(String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置指定 key 的值并设置过期时间
     * @param key
     * @param value
     * @param time 过期时间
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }

            return true;
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对指定 key 的值进行递增
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递增因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, delta);
        }
    }

    /**
     * 对指定 key 的值进行递减
     * @param key
     * @param delta
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递减因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, -delta);
        }
    }

    /**
     * 获取存储在哈希表中指定字段的值
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item) {
        return this.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取所有给定字段的值
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 存储在哈希表中的字段及其值，并设置过期时间
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张哈希表中放入数据,如果不存在将创建
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张哈希表中放入数据,如果不存在将创建
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张哈希表中放入数据,如果不存在将创建，并设置过期时间
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var7) {
            Exception e = var7;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除哈希表 key 中的一个或多个指定字段
     * @param key
     * @param item
     */
    public void hdel(String key, Object... item) {
        this.redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return this.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 为哈希表 key 中的指定字段的浮点数值加上增量 increment
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hincr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * 为哈希表 key 中的指定字段的浮点数值减去减量 decrement
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 获取集合中的所有成员
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception var3) {
            Exception e = var3;
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断 member 元素是否是集合 key 的成员
     * @param key
     * @param value
     * @return
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return this.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向集合添加一个或多个成员
     * @param key
     * @param values
     * @return
     */
    public long sSet(String key, Object... values) {
        try {
            return this.redisTemplate.opsForSet().add(key, values);
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 向集合添加一个或多个成员，并设置过期时间
     * @param key
     * @param time
     * @param values
     * @return
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = this.redisTemplate.opsForSet().add(key, values);
            if (time > 0L) {
                this.expire(key, time);
            }

            return count;
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取集合的成员数
     * @param key
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return this.redisTemplate.opsForSet().size(key);
        } catch (Exception var3) {
            Exception e = var3;
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除集合中一个或多个成员
     * @param key
     * @param values
     * @return
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = this.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取列表指定范围内的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception var7) {
            Exception e = var7;
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  获取列表的长度
     * @param key
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception var3) {
            Exception e = var3;
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引获取列表中的元素
     * @param key
     * @param index
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return this.redisTemplate.opsForList().index(key, index);
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在列表中添加一个或多个值
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在列表中添加一个或多个值，并设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在列表中添加一个或多个值
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception var4) {
            Exception e = var4;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在列表中添加一个或多个值，并设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过索引设置列表元素的值
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            this.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除列表元素
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = this.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception var6) {
            Exception e = var6;
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 根据前缀获取所有匹配的 key
     * @param keyPrefix
     * @return
     */
    private Set<String> keys(String keyPrefix) {
        // Define the pattern for the keys to search for by appending '*' to the prefix
        String pattern = keyPrefix + "*";
        Set<String> keysFound = new HashSet<>();

        try {
            // Use the RedisTemplate's execute method with a callback that performs the scan operation
            keysFound = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                Set<String> temporaryKeys = new HashSet<>();
                // Use the scanOptions to define the match pattern and build the options
                ScanOptions scanOptions = ScanOptions.scanOptions().match(pattern).build();
                // Perform the scan operation with the given options
                Cursor<byte[]> cursor = connection.scan(scanOptions);
                while (cursor.hasNext()) {
                    // Convert each found key from byte[] to String and add it to the temporaryKeys set
                    temporaryKeys.add(new String(cursor.next()));
                }
                return temporaryKeys;
            });
        } catch (Exception e) {
            // Log the exception if any occurs during the scan operation
            e.printStackTrace();
        }
        // Return the set of keys found
        return keysFound;
    }

    /**
     * 根据前缀删除所有匹配的 key
     * @param keyPrefix
     */
    public void removeAll(String keyPrefix) {
        try {
            Set<String> keys = this.keys(keyPrefix);
            this.redisTemplate.delete(keys);
        } catch (Throwable var3) {
            Throwable e = var3;
            e.printStackTrace();
        }

    }

}
