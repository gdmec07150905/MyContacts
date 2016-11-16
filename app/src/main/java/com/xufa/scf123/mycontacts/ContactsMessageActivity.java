package com.xufa.scf123.mycontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactsMessageActivity extends AppCompatActivity {
    private TextView nameEditText;
    private TextView mobileEditText;
    private TextView qqEditText;
    private TextView danweiEditText;
    private TextView addressEditText;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_message);
        setTitle("联系人信息");
        nameEditText= (TextView) findViewById(R.id.name);
        mobileEditText= (TextView) findViewById(R.id.mobile);
        danweiEditText= (TextView) findViewById(R.id.danwei);
        qqEditText= (TextView) findViewById(R.id.qq);
        addressEditText= (TextView) findViewById(R.id.address);
        Bundle localBundle=getIntent().getExtras();
        int id=localBundle.getInt("user_ID");
        ContactsTable ct=new ContactsTable(this);
        user=ct.getUserByID(id);
        //显示联系人信息
        nameEditText.setText("姓名："+user.getName());
        mobileEditText.setText("电话;"+user.getMobile());
        qqEditText.setText("qq："+user.getQq());
        danweiEditText.setText("单位："+user.getDanwei());
        addressEditText.setText("地址："+user.getAddress());
    }
    //创建选项菜单


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,1,Menu.NONE,"返回");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
