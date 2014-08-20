package com.example.jsonondevice;

import java.util.ArrayList;

/**
 * Created by bradl_000 on 2014/01/22.
 */
public class MainObject {
    public int intValue = 5;
    public String stringValue = "st<ri>\"ng\"Value<node1>test<node2>";
    public String[] stringArray;
    public ArrayList<ChildObject> childList = new ArrayList<ChildObject>();

    public void addChild(ChildObject co){
        childList.add(co);
    }

    public void populateStringArray(){
        stringArray = new String[2];
        stringArray[0] = "First";
        stringArray[1] = "Second";
    }

    public static MainObject createTestMainObject(){
        MainObject mo = new MainObject();
        mo.populateStringArray();
        mo.addChild(new ChildObject("Eve", 30));
        mo.addChild(new ChildObject("Adam",28));
        return mo;
    }

    public static String checkTestMainObject(MainObject mo){
        MainObject moCopy = createTestMainObject();
        if(!(mo.stringValue.equals(moCopy.stringValue))){
            return "String Values Do Not Match" + mo.stringValue;
        }

        if(mo.childList.size() != moCopy.childList.size()){
            return "Array List Size Does Not Match";
        }

        ChildObject firstChild = mo.childList.get(0);
        ChildObject firstChildCopy = moCopy.childList.get(0);

        if(!firstChild.Name.equals(firstChildCopy.Name))
        {
            return "First Child Name does not match";
        }

        return "Everything Matches";
    }
}
