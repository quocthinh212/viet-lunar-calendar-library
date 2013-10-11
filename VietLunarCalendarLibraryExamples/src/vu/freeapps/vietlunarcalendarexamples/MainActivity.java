/**
 * Copyright (C) 2013 Nguyễn Viết Nhật Vũ
 * Email: nguyenvietnhatvu@gmail.com
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vu.freeapps.vietlunarcalendarexamples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import vu.freeapps.vietlunarcalendarlib.LunarCalendarUtil;
import vu.freeapps.vietlunarcalendarlib.YMD;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String RESULT_FORMAT = "Kết quả: %d/%d/%d";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Spinner spConvertType = (Spinner) findViewById(R.id.spConvertType); 
        final EditText edFrom = (EditText) findViewById(R.id.edFrom);
        final TextView tvResult = (TextView) findViewById(R.id.tvResult);
        
        tvResult.setText("Kết quả:");
        
        Button btnConvert = (Button) findViewById(R.id.btnConvert);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d = sdf.parse(edFrom.getText().toString());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    float tz = TimeUnit.HOURS.convert(cal.getTimeZone().getRawOffset(), TimeUnit.MILLISECONDS);
                    int yy = cal.get(Calendar.YEAR);
                    int mm = cal.get(Calendar.MONTH) + 1;
                    int dd = cal.get(Calendar.DAY_OF_MONTH);
                    
                    YMD res;
                    if (spConvertType.getSelectedItemPosition() == 0) {
                        res = LunarCalendarUtil.convertSolar2Lunar(yy, mm, dd, tz);
                    } else {
                        res = LunarCalendarUtil.convertLunar2Solar(yy, mm, dd, false, tz);
                    }

                    tvResult.setText(String.format(RESULT_FORMAT, res.day, res.month, res.year));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
