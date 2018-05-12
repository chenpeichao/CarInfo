package org.zc.vo;

/**
 * Description:	登录用户
 * Create by @author cpc
 * 2018年5月7日 下午9:44:51
 */
public class LoginUserVO {
    private Integer userId;     //用户id
    private String username;    //用户名
    private String name;        //员工名称
    private Integer haveSaveVehicle;        //判断用户是否有入厂鉴定权限---用于前台是否显示录入车辆--0:没有；1:有

    public LoginUserVO() {
    }

    public LoginUserVO(Integer userId, String username, String name) {
        this.userId = userId;
        this.username = username;
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHaveSaveVehicle() {
        return haveSaveVehicle;
    }

    public void setHaveSaveVehicle(Integer haveSaveVehicle) {
        this.haveSaveVehicle = haveSaveVehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginUserVO that = (LoginUserVO) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return haveSaveVehicle != null ? haveSaveVehicle.equals(that.haveSaveVehicle) : that.haveSaveVehicle == null;

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (haveSaveVehicle != null ? haveSaveVehicle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginUserVO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", haveSaveVehicle=" + haveSaveVehicle +
                '}';
    }
}
