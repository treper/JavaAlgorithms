package ataosky.example.hive.udtf;

import json.JSONArray;
import json.JSONException;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ataosky on 14-8-22.
 */
@Description(name = "tokenize",
        value = "_FUNC_(doc) - emits (token, 1) for each token in the input document")
public class MD5TagMapCount extends GenericUDTF {

    @Override
    public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {

        //check input type
        if (args.length != 1) {
            throw new UDFArgumentException("tokenize() takes exactly one argument");
        }

        if (args[0].getCategory() != ObjectInspector.Category.PRIMITIVE
                && ((PrimitiveObjectInspector) args[0]).getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("tokenize() takes a string as a parameter");
        }

        //define fileds and type
        List<String> filedNames = new ArrayList<String>();
        List<ObjectInspector> filedOIs = new ArrayList<ObjectInspector>();

        filedNames.add("tagId");
        filedNames.add("count");

        filedOIs.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
        filedOIs.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(filedNames, filedOIs);
    }

    @Override
    public void process(Object[] objects) throws HiveException {
        String line = objects[0].toString();
        if (line == null) {
            return;
        }

        if (line.length() > 7) {
            line = line.substring(8, line.length() - 1);
            // System.out.println(labelLists);
            JSONArray jsonItem = null;
            JSONArray jsonLabel = null;
            try {
                jsonItem = new JSONArray(line);
            } catch (JSONException e) {
                return;
            }

            for (int i = 0; i < jsonItem.length(); i++) {
                try {
                    jsonLabel = jsonItem.getJSONArray(i);
                    Integer tagId = jsonLabel.getInt(0);
                    Integer count = jsonLabel.getInt(2);
                    forward(new Object[]{tagId, count});
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    @Override
    public void close() throws HiveException {

    }
}
