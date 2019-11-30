package io.renren.modules.activity.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.activity.dao.ActivityDao;
import io.renren.modules.activity.entity.ActivityEntity;
import io.renren.modules.activity.entity.CommentEntity;
import io.renren.modules.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, ActivityEntity> implements ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ActivityEntity> page = this.selectPage(
                new Query<ActivityEntity>(params).getPage(),
                new EntityWrapper<ActivityEntity>()
        );

        return new PageUtils(page);
    }

    public List<Map<String, Object>> queryList(String id) {
        return activityDao.queryList(id);
    }

    public int updateActivityState(ActivityEntity activityEntity) {
        return activityDao.updateActivityState(activityEntity);
    }

    public int updateLikeState(ActivityEntity activityEntity) {
        return activityDao.updateLikeState(activityEntity);
    }

    public int insertLikeState(ActivityEntity activityEntity) {
        return activityDao.insertLikeState(activityEntity);
    }


    public int insertComment(CommentEntity commentEntity){
        return activityDao.insertComment(commentEntity);
    }

    public int deleteComment(CommentEntity commentEntity){
        return activityDao.deleteComment(commentEntity);
    }

    public Map<String, Object> queryLikeByUserIdAndActivityId(ActivityEntity activityEntity) {
        return activityDao.queryLikeByUserIdAndActivityId(activityEntity);
    }

    public CommentEntity queryCommentById (CommentEntity commentEntity) {
        return activityDao.queryCommentById(commentEntity);
    }

    public List<CommentEntity> queryCommentByActivityId (CommentEntity commentEntity){
        return activityDao.queryCommentByActivityId(commentEntity);
    }

    public List<ActivityEntity> queryActivityByUserId(ActivityEntity activityEntity){
        return activityDao.queryActivityByUserId(activityEntity);
    }

    public List<ActivityEntity> queryAllTemplate(Map<String, Object> params, List<String> list) {
        int pageNum = Integer.parseInt(params.get("pageNum").toString());
        int pageSize = Integer.parseInt(params.get("pageSize").toString());
        return activityDao.queryAllTemplate(pageNum, pageSize, list);
    }

}
