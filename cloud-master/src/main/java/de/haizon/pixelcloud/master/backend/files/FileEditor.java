package de.haizon.pixelcloud.master.backend.files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc this file!
 * Created: 02.11.2022
 *
 * @author Haizoooon (maxhewa@gmail.com)
 */
public class FileEditor {

    private final File file;
    private final List<String> lines;

    public FileEditor(File file){
        this.file = file;
        lines = new ArrayList<>();
        readFile();
    }

    public void readFile(){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public void replaceLine(String line, String value){
        for(int i = 0; i < lines.size(); i++){
            String string = lines.get(i);
            if(string.equalsIgnoreCase(line)){
                lines.remove(i);
                lines.set(i, value);
                return;
            }
        }
    }

    public void save(){
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));

            for(String line : lines){
                fileWriter.write(line + System.lineSeparator());
            }

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
