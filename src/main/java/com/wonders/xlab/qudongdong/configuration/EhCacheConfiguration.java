package com.wonders.xlab.qudongdong.configuration;

import com.wonders.xlab.qudongdong.service.cache.HCCache;
import com.wonders.xlab.qudongdong.service.cache.HCCacheProxy;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置。
 */
@Configuration
public class EhCacheConfiguration {

    @Bean
    public CacheManager ehcacheManager() {
        CacheManager cm = CacheManager.create();
        return cm;
    }

    @Bean
    public HCCache<?, ?> accessTokenCache(CacheManager ehcacheManager) {
        // 创建用户验证编码缓存
        Cache cache = new Cache(
                new CacheConfiguration(
                        "accessTokenCache", // 缓存名
                        5 // 缓存最大个数
                )
                        .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO) // 当缓存满时，使用先进先出清理内存
                        .eternal(false) // 对象是否永久有效
                        .timeToIdleSeconds(0) // 对象失效前允许的闲置时间， 0，闲置时间无穷大
                        .timeToLiveSeconds(120 * 60) // 对象的失效时间，这里设置失效时间 120分钟
                        .diskExpiryThreadIntervalSeconds(10) // 10秒间隔检测 idle 和 live状态
                        .persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)) // 当缓存满了，或者重启时，不持久化数据
        );
        ehcacheManager.addCache(cache); // 必须加入缓存，不要忘了

        return new HCCacheProxy<>(cache);
    }

}