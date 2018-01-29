package raunak.com.jodhpurquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {
	static String name = "Imagestore.sqlite";
	static String path = "";
	static ArrayList<Suitcase> a;
	static ArrayList<Suitcasemedium> b;
	static ArrayList<Suitcaseexpert> c;
	static SQLiteDatabase sdb;

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	private DBAdapter(Context v) {
		super(v, name, null, 1);
		path = "/data/data/" + v.getApplicationContext().getPackageName()
				+ "/databases";
	}

	public boolean checkDatabase() {
		SQLiteDatabase db = null;
		try {
			db = SQLiteDatabase.openDatabase(path + "/" + name, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (Exception e) {

		}
		if (db == null) {

			return false;
		} else {
			db.close();
			return true;
		}
	}

	public static synchronized DBAdapter getDBAdapter(Context v) {
		return (new DBAdapter(v));
	}

	public void createDatabase(Context v) {
		this.getReadableDatabase();
		try {
			InputStream myInput = v.getAssets().open(name);
			// Path to the just created empty db
			String outFileName = path + "/" + name;
			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);
			// transfer bytes from the inputfile to the outputfile
			byte[] bytes = new byte[1024];
			int length;
			while ((length = myInput.read(bytes)) > 0) {
				myOutput.write(bytes, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
			
		/*	
			
			
			
			InputStream is = v.getAssets().open("quiz.sqlite");
			// System.out.println(is.available());
			System.out.println(new File(path + "/" + name).getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(path + "/" + name);
			int num = 0;
			while ((num = is.read()) > 0) {
				fos.write((byte) num);
			}
			fos.close();
			is.close();*/
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void openDatabase() {
		try {
			sdb = SQLiteDatabase.openDatabase(path + "/" + name, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void insertDB(String que, String opt1, String opt2, String opt3, String opt4, String ans) {
		ContentValues cv = new ContentValues();
		cv.put("Question", que);
		cv.put("Option1", opt1);
		cv.put("Option2", opt2);
		cv.put("Option3", opt3);
		cv.put("Option4", opt4);
		cv.put("Answer", ans);
		sdb.insert("QuestionTable", null, cv);
	}

	public ArrayList<Suitcase> getData() {

		Cursor c1 = sdb.rawQuery("select * from JDPQuiz order by random()", null);

		a = new ArrayList<Suitcase>();
		while (c1.moveToNext()) {
			Suitcase q1 = new Suitcase();
			q1.setQuest(c1.getString(1));
			q1.setO1(c1.getString(2));
			q1.setO2(c1.getString(3));
			q1.setO3(c1.getString(4));
			q1.setO4(c1.getString(5));
			q1.setAns(c1.getInt(6));
			q1.setScore(c1.getInt(7));

			//q1.setAnswer(c1.getString(5));
			a.add(q1);
		}
		return a;
	}

	public ArrayList<Suitcasemedium> getDatamed() {

		Cursor c2 = sdb.rawQuery("select * from Mediumlevel order by random()", null);
		b = new ArrayList<Suitcasemedium>();
		while (c2.moveToNext()) {
			Suitcasemedium q2 = new Suitcasemedium();
			q2.setQuest(c2.getString(1));
			q2.setO1(c2.getString(2));
			q2.setO2(c2.getString(3));
			q2.setO3(c2.getString(4));
			q2.setO4(c2.getString(5));
			q2.setAns(c2.getInt(6));
			q2.setScore(c2.getInt(7));

			//q1.setAnswer(c1.getString(5));
			b.add(q2);
		}
		return b;
	}

	public ArrayList<Suitcaseexpert> getDataexp() {

		Cursor c3 = sdb.rawQuery("select * from Expertlevel order by random()", null);
		c = new ArrayList<Suitcaseexpert>();
		while (c3.moveToNext()) {
			Suitcaseexpert q3 = new Suitcaseexpert();
			q3.setQuest(c3.getString(1));
			q3.setO1(c3.getString(2));
			q3.setO2(c3.getString(3));
			q3.setO3(c3.getString(4));
			q3.setO4(c3.getString(5));
			q3.setAns(c3.getInt(6));
			q3.setScore(c3.getInt(7));

			//q1.setAnswer(c1.getString(5));
			c.add(q3);
		}

		return c;
	}

	public void insertimg(byte[] a){

		ContentValues cv = new ContentValues();
		cv.put("Image", a);

		sdb.insert("Imagedb", null, cv);


	}

	public byte[] getimg() {

		Cursor c3 = sdb.rawQuery("select * from Imagedb", null);
		c3.moveToFirst();
		byte[] b =c3.getBlob(c3.getColumnIndex("Image"));
		c3.close();
		return b;
	}


}

