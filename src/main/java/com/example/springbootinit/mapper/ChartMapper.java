package com.example.springbootinit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootinit.model.entity.Chart;

import java.util.List;
import java.util.Map;

/**

*/
public interface ChartMapper extends BaseMapper<Chart> {
    List<Map<String, Object>> queryChartData(String querySql);
}




