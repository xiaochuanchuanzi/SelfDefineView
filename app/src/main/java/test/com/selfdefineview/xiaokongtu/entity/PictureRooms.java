package test.com.selfdefineview.xiaokongtu.entity;

/**
 * Created by zhangsixia on 18/4/27.
 */

public class PictureRooms {

    public int Id;
    public int Status;//1:白色(空闲)   2灰色(无数据,预约)   3红色(已出售)
    public boolean clickStatus = false;//true 已点击    false未点击
    public int Roomnum;
    public int Prepayprice;
    public int Housetypeid;
    public int Isshelves;
    public int Floor;
    public int Bedroom;//卧室
    public int LivingRoom;//客厅
    public int Bathroom;//卫生间

}
