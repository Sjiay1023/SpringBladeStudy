package org.sjiay.demo.controller;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: SpringBlade
 * @description
 * @author: shenjiayu
 * @create: 2020-06-17 10:45
 **/
@RestController
@RequestMapping("sjiayApi")
public class SjiayDemoController {

	@GetMapping("/info")
	public String info(String name ){
		return "Hello, My Name Is:" + name;
	}
}
