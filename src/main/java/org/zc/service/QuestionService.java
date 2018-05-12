package org.zc.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;

/**
 * 问题controller
 * Created by ceek on 2018-04-19 0:19.
 */
public interface QuestionService {
    /**
     * 根据任务流转记录id获取当前用户问题列表
     * @param taskRecordId     任务流转记录id
     * @param status           任务流转标识
     * @return
     */
    public Map<String, Object> getQuestionListByTaskRecordId(Integer taskRecordId, Integer status);
    
    /**
     * 问题提交
     * @param operateUserId			操作人id
     * @param taskRecordId			任务流转id
     * @param itemId				质检项id
     * @param itemTitle				质检项问题(当itemId为-1时，为自定义问题)
     * @param content				所填答案（自定义答案
     * @param chooseContent			针对选择题判断（选择的答案）
     * @param typeStr				题目类型	0为判断题，1为选择题，2为填空题 ，3为多选题
     * @param status				任务流转状态标识
     * @return
     */
    public Map<String, Object> commitQuestionList(Integer operateUserId, Integer taskRecordId, Integer itemId, 
    		String itemTitle, String content, String chooseContent, String type, Integer status);
    
    /**
     * 批量提交问题
     * @param platformList		问题答案封装json数组
     * @param taskRecordId		任务流转id
     * @param status			任务流转状态标识
     * @return
     */
    public Map<String, Object> batchCommitQuestionList(JSONArray platformList, Integer taskRecordId, Integer status);
}
