package com.sql_to_csv_conversion.sql_to_csv_conversion.User;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.catalina.User;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("check_connection")
    public String tester(){
        return "Yeah !! you're connected :)";
    }

    @GetMapping("/users")
    public List<UserStructure> getAllData(){
        return userRepo.findAll();
    }

    @GetMapping("export_to_csv")
    public ResponseEntity<Resource> exportToCSVFile() throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\CODE\\JAVA\\JAVA WEB SERVICE\\sql_to_csv_conversion\\src\\main\\resources\\data\\user_data.csv");
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        CSVWriter writer = new CSVWriter(osw);
        String[] header = {"id", "name", "email_id"};
        writer.writeNext(header);

        List<UserStructure> list = userRepo.findAll();

        for(int i = 0; i < list.size(); i ++){
            String userArray[] = new String[3];
            userArray[0] = Long.toString(list.get(i).getId());
            userArray[1] = list.get(i).getName();
            userArray[2] = list.get(i).getEmail_id();

            writer.writeNext(userArray);

            System.out.print(list.get(i).getId() + " ");
            System.out.print(list.get(i).getName() + " ");
            System.out.println(list.get(i).getEmail_id());
        }

        writer.close();
        osw.close();
        fos.close();

        //DOWNLAOD THE CSV FILE

        File filePath = new File("D:\\CODE\\JAVA\\JAVA WEB SERVICE\\sql_to_csv_conversion\\src\\main\\resources\\data\\user_data.csv");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=user_data.csv");

        Path path = Paths.get(filePath.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @PostMapping("export_from_csv")
    public String exportFromCSVFile(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

        fileReader.readLine();
        String eachLine = "";
        while(true){
            UserStructure userStructure = new UserStructure();
            eachLine = fileReader.readLine();
            if(eachLine == null)
                break;

            int dataCheckPoint = 0;
            for(int i = 0; i < eachLine.length(); i ++){
                String data = "";
                if(eachLine.charAt(i) == '"'){
                    i ++;
                    while(eachLine.charAt(i) != '"'){
                        data += eachLine.charAt(i);
                        i ++;
                    }
                    dataCheckPoint ++;
                    i ++;
                }

                if(i < eachLine.length() && eachLine.charAt(i) == ','){
                    eachLine.charAt(i);
                }

                if(dataCheckPoint == 1){
                    userStructure.setId(Long.parseLong(data));
                    System.out.println("Id " + Long.parseLong(data));
                }
                if(dataCheckPoint == 2){
                    userStructure.setName(data);
                    System.out.println("Name " + data);
                }
                if(dataCheckPoint == 3){
                    userStructure.setEmail_id(data);
                    System.out.println("Email-Id " + data);
                }

            }
            System.out.println("data :-> " + userStructure.getId() + " " + userStructure.getName() + " " + userStructure.getEmail_id() + " ");

            if(!userRepo.existsById(userStructure.getId())){
                userRepo.save(userStructure);
            }
        }
        return "demo run !!";
    }

    @PostMapping("add_users")
    public String addUsers(@RequestBody UserStructure newUser){
        userRepo.save(newUser);
        return "user " + newUser + "\n successfully added !! 😇";
    }

}
