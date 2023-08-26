package com.example.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.springbootinit.mapper.ChartMapper;
import com.example.springbootinit.model.entity.Chart;
import com.example.springbootinit.service.ChartService;
import org.springframework.stereotype.Service;

/**
* @author Zhangwenye
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-08-02 16:46:24
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

}




