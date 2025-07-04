package com.ins1st.modules.shop.leavemsg.entity;

    import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
    import com.ins1st.core.Page;
    import com.baomidou.mybatisplus.annotation.TableId;

/**
* <p>
    * 
    * </p>
*
* @author ins1st
* @since 2020-02-21
*/
    public class LeaveMsg extends Page {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer senduserid;

    private Integer reciveuserid;

    private Integer goodid;

    private String content;

    private Date time;

    private Integer reply;

        public Integer getId() {
        return id;
        }

            public void setId(Integer id) {
        this.id = id;
        }
        public Integer getSenduserid() {
        return senduserid;
        }

            public void setSenduserid(Integer senduserid) {
        this.senduserid = senduserid;
        }
        public Integer getReciveuserid() {
        return reciveuserid;
        }

            public void setReciveuserid(Integer reciveuserid) {
        this.reciveuserid = reciveuserid;
        }
        public Integer getGoodid() {
        return goodid;
        }

            public void setGoodid(Integer goodid) {
        this.goodid = goodid;
        }
        public String getContent() {
        return content;
        }

            public void setContent(String content) {
        this.content = content;
        }
        public Date getTime() {
        return time;
        }

        public void setTime(Date time) {
        this.time = time;
        }
        public Integer getReply() {
        return reply;
        }

            public void setReply(Integer reply) {
        this.reply = reply;
        }

    @Override
    public String toString() {
    return "LeaveMsg{" +
            "id=" + id +
            ", senduserid=" + senduserid +
            ", reciveuserid=" + reciveuserid +
            ", goodid=" + goodid +
            ", content=" + content +
            ", time=" + time +
            ", reply=" + reply +
    "}";
    }
}
