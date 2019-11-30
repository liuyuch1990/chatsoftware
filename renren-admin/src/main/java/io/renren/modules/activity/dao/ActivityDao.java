package io.renren.modules.activity.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.activity.entity.ActivityEntity;
import io.renren.modules.activity.entity.CommentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-25 15:35:24
 */
public interface ActivityDao extends BaseMapper<ActivityEntity> {

    List<Map<String, Object>> queryBarginLog(String id);

    List<Map<String, Object>> queryList(String id);

    int updateActivityState(ActivityEntity activityEntity);

    int updateLikeState(ActivityEntity activityEntity);

    int insertComment(CommentEntity commentEntity);

    int insertLikeState(ActivityEntity activityEntity);

    int deleteComment(CommentEntity commentEntity);

    List<CommentEntity> queryCommentByActivityId (CommentEntity commentEntity);

    List<ActivityEntity> queryActivityByUserId (ActivityEntity activityEntity);

    CommentEntity queryCommentById (CommentEntity commentEntity);

    Map<String,Object> queryLikeByUserIdAndActivityId(ActivityEntity activityEntity);

    List<ActivityEntity> queryAllTemplate(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("list") List<String> list);
}
