import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;


public class globalCounter extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private static Object[] values; // The value of the passed parameter

    private static int counter = 0;

    private static globalCounter INSTANCE;

    public globalCounter() {

    }

    public static globalCounter getInstance() {
        if (INSTANCE == null) {
            synchronized (globalCounter.class) {
                if (INSTANCE == null) {
                    INSTANCE = new globalCounter();
                }
            }
        }
        return INSTANCE;
    }

    private static final String MyFunctionName = "__globalCounter"; //function name



    {
//        desc.add("PIN-code length");
//        desc.add("The minimum value allowed for a range of values (optional)");
//        desc.add("The maximum value allowed for a range of values (optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }


    public String getCounter() {
        return String.valueOf(counter);
    }

    @Override
    public synchronized String execute(SampleResult arg0, Sampler arg1) {
        counter++;

        return String.valueOf(counter);
    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters){
        values = parameters.toArray();
    }


}