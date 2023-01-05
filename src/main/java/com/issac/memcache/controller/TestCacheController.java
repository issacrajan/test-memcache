/**
 * Copyright (c) 2022 issac.rajan@gmail.com. All rights reserved.
 */
package com.issac.memcache.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issac.memcache.service.TestCache;

/**
 * @author issac
 *
 */
@RestController
public class TestCacheController {

	private TestCache testService;

	public TestCacheController(TestCache testService) {
		this.testService = testService;
	}

	@GetMapping("/testput")
	public String putAmt() {
		return testService.setCompute("11");
	}

	@GetMapping("/test")
	public String getAmt() {
		return testService.compute("11");
	}
}
