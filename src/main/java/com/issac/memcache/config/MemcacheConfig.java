/**
 * Copyright (c) 2022 issac.rajan@gmail.com. All rights reserved.
 */
package com.issac.memcache.config;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AbstractSSMConfiguration;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.providers.xmemcached.XMemcachedConfiguration;
import com.google.code.ssm.spring.ExtendedSSMCacheManager;
import com.google.code.ssm.spring.SSMCache;

import jakarta.annotation.PostConstruct;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * @author issac
 *
 */
@Configuration
@EnableCaching
public class MemcacheConfig extends AbstractSSMConfiguration {

	@Override
	@Bean
	public CacheFactory defaultMemcachedClient() {
//		String serverString = System.getenv("MEMCACHIER_SERVERS").replace(",", " "); 
		String serverString = "127.0.0.1:11211";
		List<InetSocketAddress> servers = AddrUtil.getAddresses(serverString);
		AuthInfo authInfo = null;
//		AuthInfo.plain(System.getenv("MEMCACHIER_USERNAME"), System.getenv("MEMCACHIER_PASSWORD"));
		Map<InetSocketAddress, AuthInfo> authInfoMap = new HashMap<InetSocketAddress, AuthInfo>();
		for (InetSocketAddress server : servers) {
			authInfoMap.put(server, authInfo);
		}

		System.out.println("serverString " + serverString);
		Thread.dumpStack();
		final XMemcachedConfiguration conf = new XMemcachedConfiguration();
		conf.setUseBinaryProtocol(true);
		conf.setAuthInfoMap(authInfoMap);

		final CacheFactory cf = new CacheFactory();
		cf.setCacheClientFactory(new MemcacheClientFactoryImpl());
		cf.setAddressProvider(new DefaultAddressProvider(serverString));
		cf.setConfiguration(conf);
		return cf;
	}

	@Bean
	public CacheManager cacheManager() throws Exception {
		System.out.println("cache manager call");
		// Use SSMCacheManager instead of ExtendedSSMCacheManager if you do not
		// need to set per key expiration
		ExtendedSSMCacheManager cacheManager = new ExtendedSSMCacheManager();
		Cache cache = defaultMemcachedClient().getObject();
		// SSMCache(cache, 0, false) creates a cache with default key expiration
		// of 0 (no expiration) and flushing disabled (allowClear = false)
		cacheManager.setCaches(Arrays.asList(new SSMCache(cache, 0, false)));
		return cacheManager;
	}

	@PostConstruct
	public void init() {
		System.out.println("Spring Config initialized");
	}
}
