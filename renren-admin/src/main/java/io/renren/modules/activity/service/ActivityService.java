package io.renren.modules.activity.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.activity.entity.ActivityEntity;
import io.renren.modules.activity.entity.CommentEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-25 15:35:24
 */
public interface ActivityService extends IService<ActivityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<Map<String, Object>> queryList (String id);

    int updateActivityState(ActivityEntity activityEntity);

    int updateLikeState(ActivityEntity activityEntity);

    int insertComment(CommentEntity commentEntity);

    int deleteComment(CommentEntity commentEntity);

    int insertLikeState(ActivityEntity activityEntity);

    Map<String,Object> queryLikeByUserIdAndActivityId(ActivityEntity activityEntity);

    List<CommentEntity> queryCommentByActivityId (CommentEntity commentEntity);

    List<ActivityEntity> queryActivityByUserId (ActivityEntity activityEntity);

    CommentEntity queryCommentById (CommentEntity commentEntity);

    List<ActivityEntity> queryAllTemplate (Map<String, Object> params,List<String> list);

}

