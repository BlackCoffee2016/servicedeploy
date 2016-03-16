package com.kongzhong.common.servicedeploy.controller;

import com.kongzhong.common.servicedeploy.service.LiveDeployService;
import com.kongzhong.common.servicedeploy.service.StageDeployService;
import com.kongzhong.common.servicedeploy.service.StageWebDeployService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xxg on 15-8-19.
 */
@Controller
public class IndexController {

    @Autowired
    private LiveDeployService liveDeployService;

    @Autowired
    private StageDeployService stageDeployService;
    
    @Autowired
    private StageWebDeployService stageWebDeployService;

    /**
     * 列表展示页面
     */
    @RequestMapping("/")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("liveList", liveDeployService.getList());
        mv.addObject("stageList", stageDeployService.getList());
        mv.addObject("stageWebList", stageWebDeployService.getList());
        return mv;
    }

}
