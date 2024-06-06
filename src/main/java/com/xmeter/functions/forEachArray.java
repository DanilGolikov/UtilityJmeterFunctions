package com.xmeter.functions;

import com.xmeter.utils.arrayIndex;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class forEachArray extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter
    private static final HashMap<String, arrayIndex> indexes = new HashMap<>();

    private static final String MyFunctionName = "__c_forEachArray"; //function name
    static {
        desc.add("Array or variable");
        desc.add("End value (default null) (optional)");
        desc.add("For all threads (default false) (optional)");
        desc.add("Command (clear, delete) (optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String arrayOrVar = ((CompoundVariable) values[0]).execute().trim();

        if (arrayOrVar.isEmpty())
            throw new IllegalArgumentException("Array or variable is empty");

        String var = getVariables().get(arrayOrVar);
        if (var != null)
            arrayOrVar = var;

        boolean forAllThreads = ((CompoundVariable) values[2]).execute().trim().equals("true");
        String threadSuffix = forAllThreads ? "" : Thread.currentThread().getName();
        String oldArrayOrVar = arrayOrVar + threadSuffix;
        String command = ((CompoundVariable) values[3]).execute().trim();
        if (!command.isEmpty()) {
            switch (command) {
                case "clear":
                    indexes.clear();
                    return "OK";
                case "delete":
                    indexes.remove(oldArrayOrVar);
                    return "OK";
            }
        }

        if (!indexes.containsKey(oldArrayOrVar)) {
            if (arrayOrVar.startsWith("[") && arrayOrVar.endsWith("]"))
                arrayOrVar = arrayOrVar.substring(1, arrayOrVar.length() - 1);
            String[] arr = arrayOrVar.split(",");
            indexes.put(oldArrayOrVar, new arrayIndex(arr));
        }

        String result;
        if (forAllThreads)  result = indexes.get(oldArrayOrVar).getNextSync();
        else                result = indexes.get(oldArrayOrVar).getNext();

        if (result == null) {
            String endValue = ((CompoundVariable) values[1]).execute().trim();
            if (endValue.isEmpty())
                endValue = "null";
            result = endValue;
        }

        String inputVar = ((CompoundVariable) values[4]).execute().trim();
        if (!inputVar.isEmpty())
            getVariables().put(inputVar, result);
        return result;
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