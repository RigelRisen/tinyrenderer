package name.imatsko.tinyrenderer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by rigel92 on 03.03.15.
 */
public class ObjModelLoader {

    private ObjModelLoader() {}

    public static Model loadFromFile(String file) throws IOException {

        Model model = new Model();

        FileInputStream fstream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            if(!strLine.startsWith("#")) {
                String[] parts = strLine.split(" ",4);

                if(parts[0].equals("v")) {
                    double a = new Double(parts[1]);
                    double b = new Double(parts[2]);
                    double c = new Double(parts[3]);
                    model.addVert(new Model.Vert(a,b,c));
                } else if(parts[0].equals("f")) {
                    int a = new Integer(parts[1].split("/")[0]);
                    int b = new Integer(parts[2].split("/")[0]);
                    int c = new Integer(parts[3].split("/")[0]);
                    model.addFace(new Model.Face(a,b,c));
                }
            }
            // Print the content on the console
        }

//Close the input stream
        br.close();

        return model;
    }
}
