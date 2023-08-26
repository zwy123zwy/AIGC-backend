package com.example.springbootinit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootinit.model.entity.Chart;

import java.util.List;
import java.util.Map;

/**
* @author Zhangwenye
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2023-08-02 16:46:24
* @Entity generator.domain.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    List<Map<String, Object>> queryChartData(String querySql);
}




