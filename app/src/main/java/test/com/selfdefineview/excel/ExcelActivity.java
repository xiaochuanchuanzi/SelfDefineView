package test.com.selfdefineview.excel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;
import test.com.selfdefineview.R;

/**
 * Created by zhangsixia on 18/5/22.
 * 已修改的Excel表格功能
 */

public class ExcelActivity extends AppCompatActivity {


    /**
     * Excel保存路径
     */
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZzExcelCreator/";
    private String fileName = "我的领导";
    private String sheetName = "sheet01";
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.excel_layout);
        assignViews();
    }

    private void assignViews() {
        btnCreate = findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
                    } else {
                        new MyAsyncTask().execute();
                        new MyAsyncTask2().execute();
                        new MyAsyncTask3().execute();
                    }
                } else {
                    new MyAsyncTask().execute();
                    new MyAsyncTask2().execute();
                    new MyAsyncTask3().execute();
                }
            }
        });
    }

    /**
     * 动态获取权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x01:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new MyAsyncTask().execute();
                    new MyAsyncTask2().execute();
                    new MyAsyncTask3().execute();
                }
                break;
        }
    }

    /**
     * 创建表格
     */
    private class MyAsyncTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            try {
                ZzExcelCreator
                        .getInstance()
                        .createExcel(PATH, fileName)
                        .createSheet(sheetName)
                        .close();
                return 1;
            } catch (IOException | WriteException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    /**
     * 合并单元格
     */
    private class MyAsyncTask2 extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            List<MerginObject> lists = merge();//初始化
            //merge操作
            try {
                for (int i = 0; i < lists.size(); i++) {
                    final String co1l = lists.get(i).startColum;
                    final String row1 = lists.get(i).startRow;
                    final String co12 = lists.get(i).endColum;
                    final String row2 = lists.get(i).endRow;
                    ZzExcelCreator
                            .getInstance()
                            .openExcel(new File(PATH + fileName + ".xls"))
                            .openSheet(0) //打开第1个sheet
                            .merge(Integer.parseInt(co1l), Integer.parseInt(row1), Integer.parseInt(co12), Integer.parseInt(row2)) //合并
                            .close();
                }
                return 1;
            } catch (IOException | WriteException | BiffException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    /**
     * 向表格中插入数据
     */
    private class MyAsyncTask3 extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            List<ContentObject> strList = add();//初始化
            //插入内容
            try {
                WritableCellFormat format = ZzFormatCreator
                        .getInstance()
                        .createCellFont(WritableFont.ARIAL)
                        .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                        .setFontSize(14)
                        .setFontColor(Colour.DARK_GREEN)
                        .getCellFormat();
                for (int j = 0; j < strList.size(); j++) {
                    final String col = strList.get(j).col;
                    final String row = strList.get(j).row;
                    final String str = strList.get(j).str;
                    ZzExcelCreator
                            .getInstance()
                            .openExcel(new File(PATH + fileName + ".xls"))
                            .openSheet(0)   //打开第1个sheet
                            .setColumnWidth(Integer.parseInt(col), 25)
                            .setRowHeight(Integer.parseInt(row), 400)
                            .fillContent(Integer.parseInt(col), Integer.parseInt(row), str, format)
                            .close();
                }
                return 1;
            } catch (IOException | WriteException | BiffException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == 1) {
                Toast.makeText(ExcelActivity.this, "导出成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ExcelActivity.this, "导出失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 初始化需要插入的数据
     *
     * @return
     */
    public List<ContentObject> add() {
        List<ContentObject> strList = new ArrayList<>();
        String[] titleArray = new String[]{"日期", "时间", "时间地点", "参与领导", "事件名称", "责任单位"};
        //标题信息
        ContentObject contentObject = new ContentObject();
        contentObject.col = "0";
        contentObject.row = "0";
        contentObject.str = "燕山办事处领导工作事项安排";
        strList.add(contentObject);
        int position = 0;
        //导航小标题
        for (int i = 0; i < titleArray.length; i++) {
            if (i == 0) {
                position = i;
            } else {
                position = i + 1;
            }
            ContentObject contentObject1 = new ContentObject();
            contentObject1.row = "2";
            contentObject1.col = "" + position;
            contentObject1.str = titleArray[i];
            strList.add(contentObject1);
        }
        String[] detailArray = new String[]{"5月7日", "周一", "14:00", "体育中心", "张主席", "调研科主任的生日", "文位"};
        for (int w = 0; w < 5; w++) {
            for (int h = 3; h < 6; h++) {
                int rrooww = h + w * 3;
                //具体信息
                for (int i = 0; i < detailArray.length; i++) {
                    ContentObject contentObject1 = new ContentObject();
                    contentObject1.row = "" + rrooww;
                    contentObject1.col = "" + i;
                    contentObject1.str = detailArray[i];
                    strList.add(contentObject1);
                }
            }
        }
        return strList;
    }

    /**
     * 初始化merge表格的数据
     *
     * @return
     */
    public List<MerginObject> merge() {
        //第一行合并
        List<MerginObject> lists = new ArrayList<>();
        MerginObject merginObject = new MerginObject();
        merginObject.startColum = "0";
        merginObject.startRow = "0";
        merginObject.endColum = "6";
        merginObject.endRow = "1";
        //第二行合并
        MerginObject merginObject1 = new MerginObject();
        merginObject1.startColum = "0";
        merginObject1.startRow = "2";
        merginObject1.endColum = "1";
        merginObject1.endRow = "2";
        int startroow = 0;
        int endroow = 0;
        for (int j = 0; j < 5; j++) {
            startroow = j * 3 + 3;
            endroow = startroow + 2;
            //第三行合并
            MerginObject merginObject2 = new MerginObject();
            merginObject2.startColum = "0";
            merginObject2.endColum = "0";
            merginObject2.startRow = "" + startroow;
            merginObject2.endRow = "" + endroow;
            MerginObject merginObject3 = new MerginObject();
            merginObject3.startColum = "1";
            merginObject3.endColum = "1";
            merginObject3.startRow = "" + startroow;
            merginObject3.endRow = "" + endroow;
            lists.add(merginObject2);
            lists.add(merginObject3);
        }
        //添加顶部已合并的表格数据
        lists.add(merginObject);
        lists.add(merginObject1);
        return lists;
    }

    class MerginObject {
        public String startColum;
        public String startRow;
        public String endColum;
        public String endRow;
    }

    class ContentObject {
        public String col;
        public String row;
        public String str;
    }
}
