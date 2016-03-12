/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weiresearch.tool;

import com.weiresearch.entry.StarImpact;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GigaLiu
 */
public class FileTool {
    public static Map<Long, StarImpact> getStarInfo(String starPath) {
        Map<Long, StarImpact> starMap = null;
        BufferedReader br;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    starPath), "utf-8"));
            starMap = new HashMap<>();
            String lineStr;
            String[] values;
            String worksStr;
            int index;
            StarImpact si;
            long starId;
            while ((lineStr = br.readLine()) != null) {
                index = lineStr.indexOf(",\"[");
                if (index > 0) {
                    values = lineStr.substring(0, index).split(",");
                    worksStr = lineStr.substring(index + 2, lineStr.length() - 1);
                    if (values.length == 3) {
                        starId = Long.parseLong(values[1]);
                        si = new StarImpact(starId, values[2], 
                                Integer.parseInt(values[0]), worksStr);
                        if (!starMap.containsKey(starId)) {
                            starMap.put(starId, si);
                        }
                    }
                }
            }
            br.close();

        } catch (IOException ex) {
            Logger.getLogger(DataTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return starMap;
    }
    
}
