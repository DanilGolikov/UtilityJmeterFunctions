package dg.jmeter.plugins.functions.func;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dg.jmeter.plugins.functions.utils.customCounter;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;


public class globalCounter extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values;

    private static final HashMap<String, customCounter> counters = new HashMap<>();

    private static final class InstanceHolder {
        static final globalCounter INSTANCE = new globalCounter();
    }

    public static globalCounter getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final String MyFunctionName = "__c_globalCounter"; //function name
    static {
        desc.add("Counter name");
        desc.add("Command type");
        desc.add("Start value (optional) (default 0)");
        desc.add("End value (optional)");
        desc.add("Implement value (optional) (default 1)");
        desc.add("Number length (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    private Long paramToLong(String checkVar, Long defaultValue) {
        if (checkVar.isEmpty())
            return defaultValue;
        return Long.parseLong(checkVar);

    }

    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String counterName = ((CompoundVariable) values[0]).execute().trim();
        String commandType = ((CompoundVariable) values[1]).execute().trim();
        String implementValue = ((CompoundVariable) values[4]).execute().trim();
        String numberLengthValue = ((CompoundVariable) values[5]).execute().trim();
        String startValue, endValue;
        String numberFormat = "%d";
        if (!numberLengthValue.isEmpty())
            numberFormat = "%0" + numberLengthValue + "d";

        if (counterName.isEmpty()) throw new IllegalArgumentException("Missing counter name");
        if (!counters.containsKey(counterName)) {
            startValue = ((CompoundVariable) values[2]).execute().trim();
            endValue = ((CompoundVariable) values[3]).execute().trim();
            counters.put(counterName, new customCounter(
                    paramToLong(startValue, 0L),
                    paramToLong(endValue, null))
            );
        }
        customCounter counter = counters.get(counterName);
        switch (commandType) {
            case "addAndGet":
                return String.format(numberFormat,
                        counter.addAndGet(paramToLong(implementValue,1L)));
            case "getAndAdd":
                return String.format(numberFormat,
                        counter.getAndAdd(paramToLong(implementValue,1L)));
            case "set":
                startValue = ((CompoundVariable) values[2]).execute().trim();
                endValue = ((CompoundVariable) values[3]).execute().trim();
                return String.format(numberFormat,
                        counter.setAndGet(
                                paramToLong(startValue,0L),
                                paramToLong(endValue,null)));
            case "get":
                return String.format(numberFormat, counter.get());
            case "delete":
                counters.remove(counterName);
                return "OK";
            default: throw new IllegalArgumentException("Missing command");
        }
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