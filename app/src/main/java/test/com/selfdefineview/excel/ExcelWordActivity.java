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
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

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
 * Created by zhangsixia on 18/5/22.'
 * 未进行  功能上的修改的原库
 * https://www.jianshu.com/p/750991eb3585文档地址
 */

public class ExcelWordActivity extends AppCompatActivity {

    /**
     * Excel保存路径
     */
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZzExcelCreator/";

    private EditText etFileName;
    private EditText etSheetName;
    private Button btnCreate;
    private EditText etSheetNameAdd;
    private Button btnAddSheet;
    private EditText etRow;
    private EditText etCol;
    private EditText etString;
    private Button btnAddString;
    private EditText etRow1;
    private EditText etCol1;
    private EditText etNumber;
    private Button btnAddNumber;
    private EditText etStartRow;
    private EditText etStartCol;
    private EditText etEndRow;
    private EditText etEndCol;
    private Button btnMerge;
    private EditText etRowRead;
    private EditText etColRead;
    private Button btnGetCellContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.excel_layout);

        assignViews();

    }

    private void assignViews() {
        etFileName = (EditText) findViewById(R.id.et_file_name);
        etSheetName = (EditText) findViewById(R.id.et_sheet_name);
        btnCreate = (Button) findViewById(R.id.btn_create);
        etSheetNameAdd = (EditText) findViewById(R.id.et_sheet_name_add);
        btnAddSheet = (Button) findViewById(R.id.btn_add_sheet);
        etRow = (EditText) findViewById(R.id.et_row);
        etCol = (EditText) findViewById(R.id.et_col);
        etString = (EditText) findViewById(R.id.et_string);
        btnAddString = (Button) findViewById(R.id.btn_add_string);
        etRow1 = (EditText) findViewById(R.id.et_row1);
        etCol1 = (EditText) findViewById(R.id.et_col1);
        etNumber = (EditText) findViewById(R.id.et_number);
        btnAddNumber = (Button) findViewById(R.id.btn_add_number);
        etStartRow = (EditText) findViewById(R.id.et_start_row);
        etStartCol = (EditText) findViewById(R.id.et_start_col);
        etEndRow = (EditText) findViewById(R.id.et_end_row);
        etEndCol = (EditText) findViewById(R.id.et_end_col);
        btnMerge = (Button) findViewById(R.id.btn_merge);
        etRowRead = (EditText) findViewById(R.id.et_row_read);
        etColRead = (EditText) findViewById(R.id.et_col_read);
        btnGetCellContent = (Button) findViewById(R.id.btn_get_cell_content);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
                    } else {
                        createExcel();
                    }
                } else {
                    createExcel();
                }

            }
        });

        btnAddSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
                    } else {
                        addSheet();
                    }
                } else {
                    addSheet();
                }
            }
        });

        btnMerge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fileName = etFileName.getText().toString().trim();

                final String co1l = etStartCol.getText().toString().trim();
                final String row1 = etStartRow.getText().toString().trim();
                final String co12 = etEndCol.getText().toString().trim();
                final String row2 = etEndRow.getText().toString().trim();

                new AsyncTask<String, Void, Integer>() {

                    @Override
                    protected Integer doInBackground(String... params) {
                        try {
                            ZzExcelCreator
                                    .getInstance()
                                    .openExcel(new File(PATH + fileName + ".xls"))
                                    .openSheet(0) //打开第1个sheet
                                    .merge(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3])) //合并
                                    .close();
                            return 1;
                        } catch (IOException | WriteException | BiffException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }

                    @Override
                    protected void onPostExecute(Integer aVoid) {
                        super.onPostExecute(aVoid);
                        if (aVoid == 1) {
                            Toast.makeText(ExcelWordActivity.this, "合并成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ExcelWordActivity.this, "合并失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(co1l, row1, co12, row2);
            }
        });

        btnAddNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fileName = etFileName.getText().toString().trim();

                final String col = etCol1.getText().toString().trim();
                final String row = etRow1.getText().toString().trim();
                final String str = etNumber.getText().toString().trim();

                new AsyncTask<String, Void, Integer>() {

                    @Override
                    protected Integer doInBackground(String... params) {

                        try {

                            WritableCellFormat format = ZzFormatCreator
                                    .getInstance()
                                    .createCellFont(WritableFont.ARIAL)
                                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                                    .setFontSize(14)
                                    .setFontColor(Colour.ROSE)
                                    .getCellFormat();

                            ZzExcelCreator
                                    .getInstance()
                                    .openExcel(new File(PATH + fileName + ".xls"))
                                    .openSheet(0)   //打开第1个sheet
                                    .fillNumber(Integer.parseInt(col), Integer.parseInt(row), Double.parseDouble(str), format)
                                    .close();
                            return 1;
                        } catch (IOException | WriteException | BiffException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }

                    @Override
                    protected void onPostExecute(Integer aVoid) {
                        super.onPostExecute(aVoid);
                        if (aVoid == 1) {
                            Toast.makeText(ExcelWordActivity.this, "插入成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ExcelWordActivity.this, "插入失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(col, row, str);
            }
        });


        btnAddString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fileName = etFileName.getText().toString().trim();

                final String col = etCol.getText().toString().trim();
                final String row = etRow.getText().toString().trim();
                final String str = etString.getText().toString().trim();

                new AsyncTask<String, Void, Integer>() {

                    @Override
                    protected Integer doInBackground(String... params) {
                        try {
                            WritableCellFormat format = ZzFormatCreator
                                    .getInstance()
                                    .createCellFont(WritableFont.ARIAL)
                                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                                    .setFontSize(14)
                                    .setFontColor(Colour.DARK_GREEN)
                                    .getCellFormat();
                            ZzExcelCreator
                                    .getInstance()
                                    .openExcel(new File(PATH + fileName + ".xls"))
                                    .openSheet(0)   //打开第1个sheet
                                    .setColumnWidth(Integer.parseInt(col), 25)
                                    .setRowHeight(Integer.parseInt(row), 400)
                                    .fillContent(Integer.parseInt(col), Integer.parseInt(row), str, format)
                                    .close();
                            return 1;
                        } catch (IOException | WriteException | BiffException e) {
                            e.printStackTrace();
                            return 0;
                        }
                    }

                    @Override
                    protected void onPostExecute(Integer aVoid) {
                        super.onPostExecute(aVoid);
                        if (aVoid == 1) {
                            Toast.makeText(ExcelWordActivity.this, "插入成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ExcelWordActivity.this, "插入失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(col, row, str);
            }
        });


        btnGetCellContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fileName = etFileName.getText().toString().trim();

                final String col = etColRead.getText().toString().trim();
                final String row = etRowRead.getText().toString().trim();

                new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            ZzExcelCreator zzExcelCreator = ZzExcelCreator
                                    .getInstance()
                                    .openExcel(new File(PATH + fileName + ".xls"))
                                    .openSheet(0);   //打开第1个sheet
                            String content = zzExcelCreator.getCellContent(Integer.parseInt(col), Integer.parseInt(row));
                            zzExcelCreator.close();
                            return content;
                        } catch (IOException | BiffException | WriteException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(String aVoid) {
                        super.onPostExecute(aVoid);
                        if (aVoid == null) {
                            Toast.makeText(ExcelWordActivity.this, "读取失败！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ExcelWordActivity.this, "读取成功！内容是：" + aVoid, Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(col, row);
            }
        });
    }


    private void createExcel() {
        String fileName = etFileName.getText().toString().trim();
        String sheetName = etSheetName.getText().toString().trim();

        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                    ZzExcelCreator
                            .getInstance()
                            .createExcel(PATH, params[0])
                            .createSheet(params[1])
                            .close();
                    return 1;
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid == 1) {
                    Toast.makeText(ExcelWordActivity.this, "表格创建成功！请到" + PATH + "路径下查看~", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExcelWordActivity.this, "表格创建失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(fileName, sheetName);
    }

    /**
     * 新增Sheet
     */
    private void addSheet() {
        final String fileName = etFileName.getText().toString().trim();
        String sheetName = etSheetNameAdd.getText().toString().trim();

        if (sheetName.length() == 0) {
            Toast.makeText(ExcelWordActivity.this, "Sheet名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                    ZzExcelCreator
                            .getInstance()
                            .openExcel(new File(PATH + params[0] + ".xls"))  //如果不想覆盖文件，注意是openExcel
                            .createSheet(params[1])
                            .close();
                    return 1;
                } catch (IOException | WriteException e) {
                    e.printStackTrace();
                    return 0;
                } catch (BiffException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                if (aVoid == 1) {
                    Toast.makeText(ExcelWordActivity.this, "表格创建成功！请到" + PATH + "路径下查看~", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExcelWordActivity.this, "表格创建失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(fileName, sheetName);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x01:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createExcel();
                }
                break;
        }
    }

}
