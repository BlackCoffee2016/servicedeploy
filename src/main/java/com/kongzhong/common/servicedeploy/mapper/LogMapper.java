package com.kongzhong.common.servicedeploy.mapper;

import com.kongzhong.common.servicedeploy.domain.LogInfo;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by xxg on 15-8-19.
 */
public interface LogMapper {

    @Insert("insert into operation_log (uuid,type,username,time) values (#{uuid},#{type},#{username},now())")
    void insert(LogInfo liveLogInfo);

}
