/**
 * Copyright (c) 2022 issac.rajan@gmail.com. All rights reserved.
 */
package com.issac.memcache.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author issac
 *
 */
@Service
public class TestCache {

	// @ReadThroughSingleCache(namespace = "default", expiration = 0)
	@Cacheable(value = "default", key = "#inputVal")
	// @ParameterValueKeyProvider
	public String compute(String inputVal) {
		System.out.println("Calling compute method...");
		return "10";
	}

	@CachePut(value = "default", key = "#inputVal")
	public String setCompute(String inputVal) {
		System.out.println("Calling setCompute method...");
		return "10";
	}
}
