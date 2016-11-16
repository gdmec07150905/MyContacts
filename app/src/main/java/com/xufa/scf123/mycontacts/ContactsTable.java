package com.xufa.scf123.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Vector;

/**
 * Created by DELL on 2016/10/26.
 */
public class ContactsTable {
    private final static String TABLENAME="contactsTable";
    private MyDB db;
    public ContactsTable(Context context){
        //创建MyDB(context)很重要
        db=new MyDB(context);
        //如果数据表不存在就新建数据表
        if(!db.isTableExits(TABLENAME)){
            String createTableSql="CREATE TABLE IF NOT EXITS"+TABLENAME+"(id_DB integer"+
                    "primary key AUTOINCREMENT,"+
                    User.NAME+"VARCHAR,"+
                    User.MOBILE+"VARCHAR,"+
                    User.QQ+"VARCHAR,"+
                    User.DANWEI+"VARCHAR,"+
                    User.ADDRESS+"VARCHAR)";
            db.createTable(createTableSql);
        }
    }
    //增加数据
    public boolean addData(User user){
        //创建ContentValues对象用于保存数据
        ContentValues values=new ContentValues();
       // ContentValues赋值
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.DANWEI,user.getDanwei());
        values.put(User.QQ,user.getQq());
        values.put(User.ADDRESS,user.getAddress());
        //
        return db.save(TABLENAME,values);
    }

    //step2
    //获取全部联系人数据
    public User[] getAllUser() {
        Vector<User>v=new Vector<User>();
        Cursor cursor=null;
        try{
            cursor=db.find("select * from"+TABLENAME,null);
            while (cursor.moveToNext()) {
                User temp = new User();
                temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
                temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
                temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
                temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
                temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
                temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
                v.add(temp);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if(v.size()>0){
            return v.toArray(new User[]{});

        }else {
            User[] users=new User[1];
            User user=new User();
            user.setName("木有结果");
            users[0]=user;
            return users;
        }
    }

    //用id查找联系人
    public User getUserByID(int id){
        Cursor cursor=null;
        User temp=new User();
        try {
            cursor = db.find("select*from " + TABLENAME + "where" + "id_DB=?", new String[]{id + ""});
            cursor.moveToNext();
            temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
            temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
            temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
            temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
            temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
            temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
            Log.d("aa",temp.getName());
            return temp;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return null;
    }
    //更新联系人信息
    public boolean updateUser(User user){

        ContentValues values=new ContentValues();
        values.put(User.NAME,user.getName());
        values.put(User.MOBILE,user.getMobile());
        values.put(User.QQ,user.getQq());
        values.put(User.ADDRESS,user.getAddress());
        return db.update(TABLENAME,values,"id_DB?",new String[]{
                user.getId_DB()+""
        });
    }

    //模糊查询联系人
    public User[] findUserByKey(String key) {
        Vector<User>v=new Vector<User>();
        Cursor cursor=null;
        try {
            cursor = db.find("select*from " + TABLENAME +
                    "where" + User.NAME+"like'%"+key+"%'"+
                    "or" + User.MOBILE+"like'%"+key+"%'"+
                    "or" + User.QQ+"like'%"+key+"%'",null);
           while (cursor.moveToNext()) {
               User temp=new User();
               temp.setId_DB(cursor.getInt(cursor.getColumnIndex("id_DB")));
               temp.setName(cursor.getString(cursor.getColumnIndex(User.NAME)));
               temp.setMobile(cursor.getString(cursor.getColumnIndex(User.MOBILE)));
               temp.setDanwei(cursor.getString(cursor.getColumnIndex(User.DANWEI)));
               temp.setQq(cursor.getString(cursor.getColumnIndex(User.QQ)));
               temp.setAddress(cursor.getString(cursor.getColumnIndex(User.ADDRESS)));
               v.add(temp);
           }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.closeConnection();
        }
        if(v.size()>0){
            //将Vector中的数据，强制以User数组形式返回
            return v.toArray(new User[]{});
        }else{
            User[] users=new User[1];
            User user=new User();
            user.setName("无结果");
            users[0]=user;
            return users;
        }
    }
    //删除
    public boolean deleteByUser(User user){
        return db.delete(TABLENAME,"id_DB=?",new String[]{user.getId_DB()+""});
    }

}
