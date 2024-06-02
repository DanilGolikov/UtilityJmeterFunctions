/*
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;


public class testFunc extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__testFunc"; //function name

    public int randomFunc(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }

    static {
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult arg0, Sampler currentSampler) {




        InputStream test = Thread.currentThread().getContextClassLoader().getResourceAsStream("mail_indexes.json");


        JSONParser parser = new JSONParser();
        JSONObject data;
        try {
            assert test != null;
            data = (JSONObject) parser.parse(
                    new InputStreamReader(test, StandardCharsets.UTF_8)
            );
            test.close();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return data.get("test1").toString();






    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters){
        values = parameters.toArray();
    }
}*/
