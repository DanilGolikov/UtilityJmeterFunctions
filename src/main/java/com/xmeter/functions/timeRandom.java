package com.xmeter.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.xmeter.utils.customFunctionUtils.*;

public class timeRandom extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_timeRandom"; //function name
    static {
        desc.add("Start time");
        desc.add("End time");
        desc.add("Time format (default timestamp)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String startTimeInput = ((CompoundVariable) values[0]).execute().trim();
        String endTimeInput = ((CompoundVariable) values[1]).execute().trim();
        String dateFormatInput = ((CompoundVariable) values[2]).execute().trim();
        String result;

        Long startTimeTimestamp = convertRelativeTime(startTimeInput);
        Long endTimeTimestamp = convertRelativeTime(endTimeInput);

        if (startTimeTimestamp == null)
            startTimeTimestamp = convertToTimestamp(startTimeInput);
        if (endTimeTimestamp == null)
            endTimeTimestamp = convertToTimestamp(endTimeInput);

        long randomTimeTimestamp = randomFunc(startTimeTimestamp, endTimeTimestamp);

        if (dateFormatInput.isEmpty())
            result = Long.toString(randomTimeTimestamp);
        else result = new SimpleDateFormat(dateFormatInput).format(randomTimeTimestamp);

        String inputVar = ((CompoundVariable) values[3]).execute().trim();
        if (!inputVar.isEmpty()) {
            getVariables().put(inputVar, result);
        }
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