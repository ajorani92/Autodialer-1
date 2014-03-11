package com.autodialer;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private final ArrayList<Contact> contact_list = new ArrayList<Contact>();

    public DatabaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
		+ KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT" + ")";
	db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

	// Create tables again
	onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_Contact(Contact contact) {
	SQLiteDatabase db = this.getWritableDatabase();
	
	ContentValues values = new ContentValues();
	values.put(KEY_NAME, contact.getName()); // Contact Name
	values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
	values.put(KEY_EMAIL, contact.getEmail()); // Contact Email
	// Inserting Row
	db.insert(TABLE_CONTACTS, null, values);
	db.close(); // Closing database connection
    }
    public void removeAll()
    {
    	 String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

 	    SQLiteDatabase db = this.getWritableDatabase();
 	    Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor != null && cursor.getCount()>0){
    		   cursor.moveToFirst();
    		   //do your action
    		   //Fetch your data
    		  // SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
    	        db.delete(DatabaseHandler.TABLE_CONTACTS, null, null);

    		}
    		else {
    	//	 Toast.makeText(getBaseContext(), "No records yet!", Toast.LENGTH_SHORT).show();
    		    return;
    		}  
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
       
      //  db.delete(DatabaseHandler.TAB_USERS_GROUP, null, null);
    }
    
    public void delete()
    
    {
    	// String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

  	    SQLiteDatabase db = this.getWritableDatabase();
  	  Cursor cur = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='contacts'", null);
  	if(cur != null && cur.getCount()>0){
  	   cur.moveToFirst();
  	   //do your action
  	   //Fetch your data

  	
  	          //  db.execSQL (catInsertArray[i]);
  	        	String DELETEPASSCODE_DETAIL = "DELETE FROM "+TABLE_CONTACTS;
                db.execSQL(DELETEPASSCODE_DETAIL); 
  	        }
  	    
  	
     		   //do your action
     		   //Fetch your data
     		  // SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
     		    

     		
     		else {
     			
     	//	 Toast.makeText(getBaseContext(), "No records yet!", Toast.LENGTH_SHORT).show();
     		}
  	}
         
    
    // Getting single contact
    Contact Get_Contact(int id) {
	SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
		KEY_NAME, KEY_PH_NO, KEY_EMAIL }, KEY_ID + "=?",
		new String[] { String.valueOf(id) }, null, null, null, null);
	if (cursor != null)
	    cursor.moveToFirst();

	Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
		cursor.getString(1), cursor.getString(2), cursor.getString(3));
	// return contact
	cursor.close();
	db.close();

	return contact;
    }

    // Getting All Contacts
    public ArrayList<Contact> Get_Contacts() {
	try {
	    contact_list.clear();

	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);

	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
		do {
		    Contact contact = new Contact();
		    contact.setID(Integer.parseInt(cursor.getString(0)));
		    contact.setName(cursor.getString(1));
		    contact.setPhoneNumber(cursor.getString(2));
		    contact.setEmail(cursor.getString(3));
		    // Adding contact to list
		    contact_list.add(contact);
		} while (cursor.moveToNext());
	    }

	    // return contact list
	    cursor.close();
	    db.close();
	    return contact_list;
	} catch (Exception e) {
	    // TODO: handle exception
	    Log.e("all_contact", "" + e);
	}

	return contact_list;
    }

    // Updating single contact
    public int Update_Contact(Contact contact) {
	SQLiteDatabase db = this.getWritableDatabase();

	ContentValues values = new ContentValues();
	values.put(KEY_NAME, contact.getName());
	values.put(KEY_PH_NO, contact.getPhoneNumber());
	values.put(KEY_EMAIL, contact.getEmail());

	// updating row
	return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
		new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void Delete_Contact(int id) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
		new String[] { String.valueOf(id) });
	db.close();
    }

    // Getting contacts Count
    public int Get_Total_Contacts() {
	String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();

	// return count
	return cursor.getCount();
    }

}