import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class csv_read {
    public static ArrayList<ArrayList<Integer>> matrix=new ArrayList<ArrayList<Integer>>();
    public String path;
    csv_read(String filepath){
        path=filepath;
    }
    public ArrayList<ArrayList<Integer>> getdata() {
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String newline = br.readLine();
            while ((newline = br.readLine()) != null) {
                String[] temp=newline.split(",");
                ArrayList<Integer> line=new ArrayList<Integer>();
                for(int j=0;j<temp.length;j++)
                    line.add(Integer.parseInt(temp[j]));
                matrix.add(line);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;

    }
}
