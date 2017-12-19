package modconfig;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CoroAI.c_CoroAIUtil;

import net.minecraftforge.common.Configuration;

public class ModConfigData {
	public String configID;
	public Class configClass;
	public IConfigCategory configInstance;
	
	public HashMap<String, String> valsString = new HashMap<String, String>();
	public HashMap<String, Integer> valsInteger = new HashMap<String, Integer>();
	public HashMap<String, Double> valsDouble = new HashMap<String, Double>();
	public HashMap<String, Boolean> valsBoolean = new HashMap<String, Boolean>();

	//Client data
	public List<ConfigEntryInfo> configData = new ArrayList<ConfigEntryInfo>();	
	
    public Configuration preInitConfig;
    public File saveFilePath;
	
	public ModConfigData(File savePath, String parStr, Class parClass, IConfigCategory parConfig) {
		configID = parStr;
		configClass = parClass;
		configInstance = parConfig;
		saveFilePath = savePath;
	}
	
	public void updateHashMaps() {
    	Field[] fields = configClass.getDeclaredFields();
    	
    	for (int i = 0; i < fields.length; i++) {
    		Field field = fields[i];
    		String name = field.getName();
    		processField(name);
    	}
    }
	
	public void initData() {
    	valsString.clear();
    	valsInteger.clear();
    	valsDouble.clear();
    	valsBoolean.clear();
    	
    	updateHashMaps();
    }
	
	public boolean updateField(String name, Object obj) {
    	if (setFieldBasedOnType(name, obj)) {
        	//writeHashMapsToFile();
    		writeConfigFile(true);
        	return true;
    	}
    	return false;
    }
    
    public boolean setFieldBasedOnType(String name, Object obj) {
    	try {
    		if (valsString.containsKey(name)) {
    			c_CoroAIUtil.setPrivateValue(configClass, configInstance, name, (String)obj);
    			inputField(name, (String)obj);
    		} else if (valsInteger.containsKey(name)) {
    			c_CoroAIUtil.setPrivateValue(configClass, configInstance, name, Integer.valueOf(obj.toString()));
    			inputField(name, Integer.valueOf(obj.toString()));
    		} else if (valsDouble.containsKey(name)) {
    			c_CoroAIUtil.setPrivateValue(configClass, configInstance, name, Double.valueOf(obj.toString()));
    			inputField(name, Double.valueOf(obj.toString()));
    		} else if (valsBoolean.containsKey(name)) {
    			c_CoroAIUtil.setPrivateValue(configClass, configInstance, name, Boolean.valueOf(obj.toString()));
    			inputField(name, Boolean.valueOf(obj.toString()));
    		} else {
    			return false;
    		}
    		
    		configInstance.hookUpdatedValues();
    		
    		return true;
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return false;
    }
    
    /*public void writeHashMapsToFile() {
    	Iterator it = valsString.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        String name = (String)pairs.getKey();
	        Object val = pairs.getValue();
	    }
    }*/
    
    private void processField(String fieldName) {
    	try {
	    	Object obj = ConfigMod.getField(configID, fieldName);
	    	if (obj instanceof String) {
	    		valsString.put(fieldName, (String)obj);
	    	} else if (obj instanceof Integer) {
	    		valsInteger.put(fieldName, (Integer)obj);
	    	} else if (obj instanceof Double) {
	    		valsDouble.put(fieldName, (Double)obj);
	    	} else if (obj instanceof Boolean) {
	    		valsBoolean.put(fieldName, (Boolean)obj);
	    	} else {
	    		//dbg("unhandled datatype, update initField");
	    	}
    	} catch (Exception ex) { ex.printStackTrace(); }
    }
    
    private void inputField(String fieldName, Object obj) {
    	if (obj instanceof String) {
    		valsString.put(fieldName, (String)obj);
    	} else if (obj instanceof Integer) {
    		valsInteger.put(fieldName, (Integer)obj);
    	} else if (obj instanceof Double) {
    		valsDouble.put(fieldName, (Double)obj);
    	} else if (obj instanceof Boolean) {
    		valsBoolean.put(fieldName, (Boolean)obj);
    	} else {
    		
    	}
    }
    
    public void writeConfigFile(boolean resetConfig) {
        if (resetConfig) if (saveFilePath.exists()) saveFilePath.delete();
    	preInitConfig = new Configuration(saveFilePath);
    	preInitConfig.load();
    	
    	Field[] fields = configClass.getDeclaredFields();
    	
    	for (int i = 0; i < fields.length; i++) {
    		Field field = fields[i];
    		String name = field.getName();
    		addToConfig(name);
    	}
    	
    	preInitConfig.save();
    }
    
    /*  */
    private void addToConfig(String name) {
    	
    	//System.out.println("registering config field: " + name);
    	
    	Object obj = ConfigMod.getField(configID, name);
    	if (obj instanceof String) {
    		obj = preInitConfig.get(configInstance.getCategory(), name, (String)obj).getString();
    	} else if (obj instanceof Integer) {
    		obj = preInitConfig.get(configInstance.getCategory(), name, (Integer)obj).getInt((Integer)obj);
    	} else if (obj instanceof Double) {
    		obj = preInitConfig.get(configInstance.getCategory(), name, (Double)obj).getDouble((Double)obj);
    	} else if (obj instanceof Boolean) {
    		obj = preInitConfig.get(configInstance.getCategory(), name, (Boolean)obj).getBoolean((Boolean)obj);
    	} else {
    		//dbg("unhandled datatype, update initField");
    	}
    	setFieldBasedOnType(name, obj);
    }
}
