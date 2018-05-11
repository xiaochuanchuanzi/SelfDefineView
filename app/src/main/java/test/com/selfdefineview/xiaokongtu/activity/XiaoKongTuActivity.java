package test.com.selfdefineview.xiaokongtu.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.com.selfdefineview.R;
import test.com.selfdefineview.xiaokongtu.LeftAndTop;
import test.com.selfdefineview.xiaokongtu.LeftScrollView;
import test.com.selfdefineview.xiaokongtu.MapsCature;
import test.com.selfdefineview.xiaokongtu.SelfGroup;
import test.com.selfdefineview.xiaokongtu.SelfTextView;
import test.com.selfdefineview.xiaokongtu.TopScrollView;
import test.com.selfdefineview.xiaokongtu.entity.PctureHouseTypes;
import test.com.selfdefineview.xiaokongtu.entity.PictureBuild;
import test.com.selfdefineview.xiaokongtu.entity.PictureHouse;
import test.com.selfdefineview.xiaokongtu.entity.PictureRooms;

/**
 * Created by zhangsixia on 18/5/12.
 */

public class XiaoKongTuActivity extends AppCompatActivity {

    private TopScrollView mTopScrollView;
    private LeftScrollView mLeftScrollView;
    private LeftAndTop mLeftAndTop;
    private MapsCature mMapsCature;
    private SelfGroup mSelfGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaokongtu_layout);
        initWidget();
        moniData();
        setData(picture_Build);
    }

    void initWidget() {
        mLeftAndTop = (LeftAndTop) findViewById(R.id.left_and_top);
        mLeftScrollView = (LeftScrollView) findViewById(R.id.left);
        mTopScrollView = (TopScrollView) findViewById(R.id.top);
        mMapsCature = (MapsCature) findViewById(R.id.fature_map);
        mSelfGroup = (SelfGroup) findViewById(R.id.body_group);
        mLeftScrollView.addListener(mSelfGroup);
        mTopScrollView.addListener(mSelfGroup);
        mMapsCature.setListener(mSelfGroup);
        mSelfGroup.setmOnItemsClickListener(new SelfGroup.OnItemsClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void OnitemListener(View view, int position) {
                for (int i = 0; i < mSelfGroup.getChildCount(); i++) {
                    SelfTextView mTextView = (SelfTextView) mSelfGroup.getChildAt(i);
                    if(i == position){
                        if (mTextView.isSelected()) {
                            mTextView.setSelected(false);
                            mTextView.setColors(getResources().getColor(R.color.white));
                            //ID = "";
                        } else {
                            mTextView.setSelected(true);
                            mTextView.setColors(getResources().getColor(R.color.brown_qb));
                            //ID = String.valueOf(strListRoom.get(position).Id);
                        }
                    }else{
                        if(strListRoom.get(i).Status == 1){
                            mTextView.setSelected(false);
                            mTextView.setColors(getResources().getColor(R.color.white));
                        }
                    }
                }
            }
        });
    }

    private PictureBuild picture_Build = new PictureBuild();
    private void moniData(){
        picture_Build.Maxfloor = 15;
        picture_Build.status = 0;
        for(int k= 0;k < 10;k++){//10个大单元
            PictureHouse pictureHouse = new PictureHouse();
            int unitname = k+1;
            pictureHouse.UnitName = String.valueOf(unitname);
            for(int i= 0;i < 3;i++){//每个单元内的户型
                PctureHouseTypes pctureHouseTypes = new PctureHouseTypes();
                pctureHouseTypes.Id = 21;
                pctureHouseTypes.HouseType = (i + 1) * (k + 1) + "户型";
                pctureHouseTypes.BedRoomNum = 1;
                pctureHouseTypes.BathRoomNum = 3;
                pctureHouseTypes.LivingRoomNum = 2;
                pctureHouseTypes.Area = 120+(i + 1) * (k + 1);
                for(int j= 0;j < 35;j++){//每个户型下面的房屋数量
                    PictureRooms pictureRooms = new PictureRooms();
                    if(j % 3 == 0){
                        pictureRooms.Id = 0;
                    }else{
                        pictureRooms.Id = j;
                    }
                    if(j % 2 == 0){
                        pictureRooms.Status = 1;
                    }else if(j % 5 == 0){
                        pictureRooms.Status = 2;
                    }else{
                        pictureRooms.Status = 3;
                    }
                    pictureRooms.Roomnum = (15 - j)* 100 + i;
                    pictureRooms.Prepayprice = 0;
                    pictureRooms.Housetypeid = 0;
                    pictureRooms.Isshelves = 0;
                    pictureRooms.Floor = 0;
                    pctureHouseTypes.Rooms.add(pictureRooms);
                }
                pictureHouse.HouseTypes.add(pctureHouseTypes);
            }
            picture_Build.house.add(pictureHouse);
        }
    }

    //顶部栏的数据
    List<String> strList = new ArrayList<>();//存储单元的数量
    List<List<String>> strListArea = new ArrayList<>();//存储所有单元中表头第二行的所有面积数据
    List<List<String>> strListType = new ArrayList<>();//存储所有单元中表头第三行的所有户型数据
    List<PictureRooms> strListRoom = new ArrayList<>();//房屋号码的总个数
    int floorCount = 0;
    void setData(PictureBuild pictureBuild) {
        floorCount = pictureBuild.Maxfloor;
        for (int i = 0; i < pictureBuild.house.size(); i++) {//循环遍历出每一个大单元的数据----------横向结构
            PictureHouse unitHome = pictureBuild.house.get(i);
            strList.add(unitHome.UnitName);

            List<String> area = new ArrayList<>();
            List<String> type = new ArrayList<>();
            for (int j = 0; j < unitHome.HouseTypes.size(); j++) {//循环出某个单元中的每一个户型所对应的房屋-------横向结构
                PctureHouseTypes houseType = unitHome.HouseTypes.get(j);
                area.add(String.valueOf(houseType.Area));
                //    Bedroom;//卧室  LivingRoom;//客厅    Bathroom;//卫生间
                type.add(houseType.BedRoomNum+"室"+houseType.LivingRoomNum+"厅"+houseType.BathRoomNum+"卫");

                for (int m = 0; m < houseType.Rooms.size(); m++) {//从每一个户型中循环出每一个room房间----------竖向结构
                    PictureRooms rooms = houseType.Rooms.get(m);
                    strListRoom.add(rooms);
                }
            }
            strListArea.add(area);
            strListType.add(type);
        }
        mLeftScrollView.setData(floorCount);
        mTopScrollView.setData(3, strList, strListArea, strListType);
        mSelfGroup.setData(strList.size(), floorCount, strListRoom, strListType);
        mMapsCature.setData(strList.size(), floorCount, strListType);
    }
}
