package com.example.barscanv01.Setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.barscanv01.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.setting_not_add_good)
    CheckBox addCheckBox;
    @BindView(R.id.setting_not_remove_good)
    CheckBox removeCheckBox;

    private boolean addResult;
    private boolean removeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        intial();
        setListener(addCheckBox);
        setListener(removeCheckBox);
    }

    private void intial() {
        if (SettingSingletone.getInstance(this).getAddResult()) {
            addCheckBox.setChecked(true);
        }
        if (SettingSingletone.getInstance(this).getRemoveResult()) {
            removeCheckBox.setChecked(true);
        }
    }


    private void setListener(final CheckBox checkBox) {
        final int id = checkBox.getId();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    if (id == R.id.setting_not_add_good) {
                        SettingSingletone.getInstance(SettingActivity.this).setAddResult(true);
                        if (SettingSingletone.getInstance(SettingActivity.this).getAddResult()) {
                            Toast.makeText(SettingActivity.this, "已设置可加货", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        SettingSingletone.getInstance(SettingActivity.this).setRemoveResult(true);
                        if (SettingSingletone.getInstance(SettingActivity.this).getRemoveResult()) {
                            Toast.makeText(SettingActivity.this, "已设置可减货", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (id == R.id.setting_not_add_good) {
                        SettingSingletone.getInstance(SettingActivity.this).setAddResult(false);
                        if (!SettingSingletone.getInstance(SettingActivity.this).getAddResult()) {
                            Toast.makeText(SettingActivity.this, "已设置不可加货", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        SettingSingletone.getInstance(SettingActivity.this).setRemoveResult(false);
                        if (!SettingSingletone.getInstance(SettingActivity.this).getRemoveResult()) {
                            Toast.makeText(SettingActivity.this, "已设置不可减货", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
