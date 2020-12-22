package com.in.springboot_euroland.demo.controller;

import com.in.springboot_euroland.demo.model.table;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class Homeclass {
    List<Map> list = new ArrayList<Map>();
    ArrayList arry=new ArrayList();
    Map<String, List<HashMap>> map = new HashMap();
    table obj = null;

   @Value("${file.directory}")
   private String fileDirectory;

    @RequestMapping("/show")
    public String showdata() {
        List<HashMap> arrayList = new ArrayList<HashMap>();
        List<table> liTable= new ArrayList<table>();
        try {
            System.out.println(fileDirectory);
            File file = new File(fileDirectory);
            if (file.exists()) {

                String name1 = null;
                String classname1 = null;
                String batch1 = null;
                int ro= 0;
                int i=0;
                int colo=0;
                Document doc = Jsoup.parse(file, "utf-8");
                for (Element table : doc.select("table")) {
                  i++;
                      Elements column = table.select("thead tr");
                      Elements rows = table.select("tbody tr");
                      if (!rows.isEmpty() && !column.isEmpty()) {
                          System.out.println("Table columns: " + column.size());
                          System.out.println("Table rows : " + rows.size());
                          arry.add(column.size());
                          arry.add(rows.size());
                      }
                      //rows.remove(rows.first()); // Removing first row: "Total->3640"
                      for (Element col : column) {
                          Elements co = col.select("th:not([rowspan])");
                          System.out.println(co.get(0).text() + " " + co.get(1).text() + " " + co.get(2).text());
                      }
                      for (Element row : rows) {
                          HashMap<String, String> stringMap = new HashMap();

                          Elements tds = row.select("td:not([rowspan])");
                          for (Element e : tds) {
                              obj = new table();
                              obj.setValue(e.text());
                              obj.setRows(ro);
                              obj.setCoulmn(colo);
                              liTable.add(obj);
                              colo++;
                          }
                          name1 = tds.get(0).text();
                          classname1 = tds.get(1).text();
                          batch1 = tds.get(2).text();
                          if (name1 != null) {
                              stringMap.put("Name", name1);
                              stringMap.put("ClassName", classname1);
                              stringMap.put("Batch", batch1);
                              arrayList.add(stringMap);
                          }
                          System.out.println(tds.get(0).text() + " " + tds.get(1).text() + " " + tds.get(2).text());
                          ro++;
                      }
                      if(i==1) {
                          System.out.println("Table List : " + arrayList);
                          int r= obj.getRows()+1;
                          int c= obj.getCoulmn()+1;
                          //System.out.println(r +"  "+c);
                          System.out.println("Totalcoulmns :"+Math.round(c/r));
                          System.out.println("TotalRows: "+r);
                          System.out.println(liTable);
                          map.put("allstudents", arrayList);
                      }

                }

            } else {
                System.out.println("file not found this location...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Data Saved Successfully...";
    }

    @RequestMapping("/showall")
    public ResponseEntity<?> getall() {
        return ResponseEntity.ok(map.get("allstudents"));
    }

    /*@RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> savedetails(@RequestBody student stu) {
        student stud = studentrepo.findByName(stu.getName());
        if (stud == null) {
            stud = stuservi
            ce.savestudents(stu);
        } else {
            return ResponseEntity.ok("User Name is exits");
        }
        return ResponseEntity.ok(stud);
    }*/

}