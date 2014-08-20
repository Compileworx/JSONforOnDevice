package com.example.jsonondevice;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GSONTester {
    private static String tag = "GSONTester";
    private Context context;

    public GSONTester(Context context){
        this.context = context;
    }

    public String testJSON(){
        MainObject mo = MainObject.createTestMainObject();
        Gson gson = new Gson();
        String jsonString = gson.toJson(mo);
        MainObject mo1 = gson.fromJson(jsonString,MainObject.class);
        String ResponseCheck = MainObject.checkTestMainObject(mo1);

        //test context and shared resources
        SharedPreferences sp = this.context.getSharedPreferences("myprefs",Context.MODE_PRIVATE);
        //original
        //SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("json",jsonString);
        spe.commit();
        return ResponseCheck;
    }

    public String testEscapeCharactersInPreferences(){
        String testString = "<node1>blahhh</node2>";

        SharedPreferences sp = this.context.getSharedPreferences("myprefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("test",testString);
        spe.commit();

        String SavedString = sp.getString("test",null);
        if(SavedString == null)
        {
            Log.d(tag,"No Saved String");
            return "No Saved String";
        }

        Log.d(tag,SavedString);
        if(SavedString.equals(testString))
        {
            Log.d(tag, "Saved string properly match");
            return "Saved String Properly Match";
        }

        Log.d(tag,"They do not match");
        return "The Strings do not match";
    }

    private SharedPreferences getSharedPreferences()
    {
        SharedPreferences sp
                = MyApplication.s_applicationContent.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        return sp;
    }

    public String storeJSON()
    {
        MainObject mo = new MainObject();
        Gson gson = new Gson();

        String jsonString = gson.toJson(mo);
        Log.d(tag,jsonString);

        MainObject mo1 = gson.fromJson(jsonString, MainObject.class);
        Log.i(tag,jsonString);

        SharedPreferences sp = this.context.getSharedPreferences("myprefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("json",jsonString);
        spe.commit();

        return jsonString;
    }

    public String retrieveJSON(){
        SharedPreferences sp = this.context.getSharedPreferences("myprefs",Context.MODE_PRIVATE);
        String jsonString = sp.getString("json",null);

        if(jsonString == null){
            Log.i(tag, "Not able to read the preference");
            return "Not able to read the preference";
        }

        Gson gson = new Gson();
        MainObject mo = gson.fromJson(jsonString, MainObject.class);
        Log.i(tag,"Object successfully retrieved");
        String compareResult = MainObject.checkTestMainObject(mo);
        if(compareResult != null){
            Log.i(tag,compareResult);
            return compareResult;
        }

        Log.i(tag, "Retrieved object matches");
        return ("retrived object matches");
    }

    public String saveJsonToPrivateStorage(){
        String json = createJSON();
        SaveToInternalFile(json);
        String retrievedString = this.readFromInternalFile();

        Gson gson = new Gson();
        MainObject mo = gson.fromJson(retrievedString, MainObject.class);
        MainObject srcObject = MainObject.createTestMainObject();
        String compareResult = mo.checkTestMainObject(srcObject);
        Log.i(tag,compareResult);

        return compareResult;
    }

    private String createJSON(){
        MainObject mo = MainObject.createTestMainObject();
        Gson gson = new Gson();

        String jSonString = gson.toJson(mo);
        return jSonString;
    }

    private String readFromInternalFile(){
        FileInputStream fis = null;
        try{
            Context appContext = MyApplication.s_applicationContent;
            fis = appContext.openFileInput("datastore-json.txt");
            String jsonString = readStringAsStream(fis);
            return jsonString;
        }
        catch(IOException x){
            Log.d(tag, "Cannot create or read file");
            return null;
        }
        finally{
            closeStreamSilently(fis);
        }
    }

    private void SaveToInternalFile(String json){
        FileOutputStream fos = null;

        try
        {
            Context context = MyApplication.s_applicationContent;
            fos = context.openFileOutput("datastore-json.txt", Context.MODE_PRIVATE);
            fos.write(json.getBytes());

        }catch(IOException io){
            Log.d(tag,"Cannot write or create file");
        }
        finally {
            closeStreamSilently(fos);
        }
    }

    private void copy(InputStream reader, OutputStream writer)throws IOException{
        byte byteArray[] = new byte[4092];
        while(true){
            int numOfBytesRead = reader.read(byteArray,0,4092);
            if(numOfBytesRead == -1){
                break;
            }
            else{
                writer.write(byteArray, 0, numOfBytesRead);
            }
        }
        return;
    }

    private String readStringAsStream(InputStream is)throws FileNotFoundException, IOException{
        ByteArrayOutputStream baos = null;
        try{
            baos = new ByteArrayOutputStream();
            copy(is, baos);
            return baos.toString();
        }
        finally{
            if(baos != null){
                closeStreamSilently(baos);
            }
        }
    }

    private void closeStreamSilently(OutputStream os){
        if(os == null){
            return;
        }

        try{
            os.close();
        }
        catch(IOException x){
            throw new RuntimeException("This should not happen exception closing file");
        }
    }

    private void closeStreamSilently(InputStream os){
        if(os == null){
            return;
        }

        try{
            os.close();
        }
        catch(IOException x){
            throw new RuntimeException("This should not happen exception closing file");
        }
    }
}
