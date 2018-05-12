package org.zc.bean;

import java.util.ArrayList;

public class CommitQuestionBean {
    public int user;
    public String v;
    public String o;
    public Long time;
    public int task_id;
    public ArrayList<item> questions;

    public class item {
    	public int quality_item_id;//质检项ID
        public String title;//题
        public ArrayList standard_list;//答案集
        public String choose_content;//选择题选择的答案
        public String content;//填空题填写的答案
    }

}
