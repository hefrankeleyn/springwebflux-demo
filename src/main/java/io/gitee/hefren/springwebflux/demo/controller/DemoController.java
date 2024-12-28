package io.gitee.hefren.springwebflux.demo.controller;

import com.google.common.base.Strings;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Date 2024/9/29
 * @Author lifei
 */
@RestController
@RequestMapping(value = "/demoController")
public class DemoController {

    /**
     * 矩阵变量的使用一：基本使用
     * @param color
     * @param year
     * @return
     */
    @GetMapping(value = "/matrixParams01/{carId}")
    public String matrixParams01(@MatrixVariable(pathVar = "carId", name = "color") String color,
                               @MatrixVariable(pathVar = "carId", name = "year") Integer year){
        return Strings.lenientFormat("Matrix Params: color: %s, year: %s", color, year);
    }

    /**
     * 矩阵变量的使用二：多个值，用逗号分隔，或者重复变量名指定多个值
     * @param colors
     * @param year
     * @return
     */
    @GetMapping(value = "/matrixParams02/{carId}")
    public String matrixParams02(@MatrixVariable(pathVar = "carId", name = "colors") List<String> colors,
                                 @MatrixVariable(pathVar = "carId", name = "year") Integer year){
        return Strings.lenientFormat("Matrix Params: colors: %s, year: %s", colors, year);
    }

    /**
     * 矩阵变量的使用三：接收路径段中的所有值
     * @param values
     * @return
     */
    @GetMapping(value = "/matrixParams03/{carId}/{userId}")
    public String matrixParams03(@MatrixVariable(pathVar = "carId") MultiValueMap<String, String> values){
        return Strings.lenientFormat("Matrix Params all values:  %s", values);
    }

    /**
     * 矩阵变量的使用四：接收所有路径段中的所有值
     * @param values
     * @return
     */
    @GetMapping(value = "/matrixParams04/{carId}/{userId}")
    public String matrixParams04(@MatrixVariable MultiValueMap<String, String> values){
        return Strings.lenientFormat("ALL Path. Matrix Params all values:  %s", values);
    }


    @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
    public String sayHello(@RequestParam("name") String name) {
        return "RequestMapping say Hello " + name;
    }

    @GetMapping(value = "/match/{name}/path", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String matchPath(@PathVariable("name") String name) {
        return name;
    }
}
