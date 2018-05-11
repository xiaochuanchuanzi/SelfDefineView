package test.com.selfdefineview.xiaokongtu.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsixia on 18/4/27.
 */

public class PctureHouseTypes {
    public int Id;
    public String HouseType;
    public int BedRoomNum;
    public int BathRoomNum;
    public int LivingRoomNum;
    public float Area;
    public List<PictureRooms> Rooms = new ArrayList<>();

}
