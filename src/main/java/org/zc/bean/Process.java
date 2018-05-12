package org.zc.bean;

import java.util.ArrayList;

public class Process {
    public int id;
    public int vehicle_info_id;// 车辆信息ID
    public int task_id;// 任务ID
    public ArrayList<processItem> processlist;// 流程列表
    public Long create_time;
    public Long update_time;
    public int is_deleted;// 是否删除

    public static class processItem {
        public ProcessProject processProject;// 入厂鉴定 自检 护检 专检 监造
        public ArrayList<Workshop> workshop;// 车间List 自检时使用
        public station none_station_project;
    }

    // 车间类
    public static class Workshop {
        public int id;// 车间id
        public int code;// 产线编码
        public String name;
        public ArrayList<Work_area> work_area;
        public int index = -1;// 选中的产线 -1时没有选择过产线
    }

    // 产线类
    public static class Work_area {
        public int id;// 产线id
        public int code;// 产线编码
        public String name;
        public ArrayList<station> station;

    }

    // 工位类
    public static class station {
        public boolean isDone;
        public int id;// 工位id
        public int code;// 工位编码
        public String name;
    }

    // 入厂鉴定 自检 护检 专检 监造
    public enum ProcessProject {
        enter, // 入厂鉴定
        self, // 自检
        each, // 互检
        special, // 专检
        supervise,// 监造
        dispatch//调度


    }

    /**
     * 输入车间，找到工位最多的产线 如果产线工位并列最多，返回第一个
     *
     * @param Workshop 车间
     * @return 最长产线的index
     */
    public static int findLenMaxListIndex(Workshop Workshop) {
        int max = 0;
        int index = 0;
        for (int i = 0; i < Workshop.work_area.size(); i++) {
            if (Workshop.work_area.get(i).station.size() > max) {
                max = Workshop.work_area.get(i).station.size();
                index = i;
            }
        }
        return index;
    }
}