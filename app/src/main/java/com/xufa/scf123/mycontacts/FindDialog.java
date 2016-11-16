package com.xufa.scf123.mycontacts;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by DELL on 2016/11/2.
 */
public class FindDialog extends Dialog {
    private Context l_context;
    public FindDialog(Context context){
        super(context);
        l_context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        setTitle("联系人查询");
        final Button find= (Button) findViewById(R.id.find);
        Button cancel= (Button) findViewById(R.id.cancel);
        find.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText value= (EditText) findViewById(R.id.value);
                ContactsTable ct = new ContactsTable(l_context);
                //模糊查询联系人
                User[] users=ct.findUserByKey(value.getText().toString());
                for(int i=0;i<users.length;i++){
                    System.out.println("姓名是"+users[i].getName()+"，电话是"+users[i].getMobile());
                }
                //主界面的数据更新
                ((MainActivity)l_context).setUsers(users);
                ((MainActivity)l_context).getListViewAdapter().notifyDataSetChanged();
                ((MainActivity)l_context).setSelectItem(0);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
