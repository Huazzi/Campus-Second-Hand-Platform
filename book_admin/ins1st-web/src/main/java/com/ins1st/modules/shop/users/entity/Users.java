package com.ins1st.modules.shop.users.entity;

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
    public class Users extends Page {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String nickname;

    private String head;

    private String mobilephone;

    private String address;

            /**
            * 1男 0女
            */
    private Integer sex;

    private String college;

    private String openid;

        public Integer getId() {
        return id;
        }

            public void setId(Integer id) {
        this.id = id;
        }
        public String getUsername() {
        return username;
        }

            public void setUsername(String username) {
        this.username = username;
        }
        public String getNickname() {
        return nickname;
        }

            public void setNickname(String nickname) {
        this.nickname = nickname;
        }
        public String getHead() {
        return head;
        }

            public void setHead(String head) {
        this.head = head;
        }
        public String getMobilephone() {
        return mobilephone;
        }

            public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
        }
        public String getAddress() {
        return address;
        }

            public void setAddress(String address) {
        this.address = address;
        }
        public Integer getSex() {
        return sex;
        }

            public void setSex(Integer sex) {
        this.sex = sex;
        }
        public String getCollege() {
        return college;
        }

            public void setCollege(String college) {
        this.college = college;
        }
        public String getOpenid() {
        return openid;
        }

            public void setOpenid(String openid) {
        this.openid = openid;
        }

    @Override
    public String toString() {
    return "Users{" +
            "id=" + id +
            ", username=" + username +
            ", nickname=" + nickname +
            ", head=" + head +
            ", mobilephone=" + mobilephone +
            ", address=" + address +
            ", sex=" + sex +
            ", college=" + college +
            ", openid=" + openid +
    "}";
    }
}
