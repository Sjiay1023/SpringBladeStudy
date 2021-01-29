package org.sjiay.demo.controller;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


	@GetMapping("/show/{actId}")
	@ApiOperation(value = "查询活动和奖项信息接口")
	public String actShow(@PathVariable String actId) {
		return "1234";
	}


	@GetMapping("/info")
	public String info(String name ){
		return "Hello, My Name Is:" + name;
	}
}
