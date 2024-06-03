package com.xmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import static com.xmeter.utils.customFunctionUtils.alignmentStr;
import static com.xmeter.utils.customFunctionUtils.randomFunc;


public class generatePinCode extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_generatePinCode"; //function name
    static {
        desc.add("PIN-code length");
        desc.add("The minimum value allowed for a range of values (optional)");
        desc.add("The maximum value allowed for a range of values (optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult arg0, Sampler arg1) {

        int lenPIN = Integer.parseInt(((CompoundVariable) values[0]).execute().trim());
        String minPIN_check = ((CompoundVariable) values[1]).execute().trim();
        String maxPIN_check = ((CompoundVariable) values[2]).execute().trim();




        int minPIN = 0;
        //long maxPIN = Long.parseLong(new String(new char[lenPIN]).replace("\0", "9"));
        int maxPIN = Integer.parseInt(new String(new char[lenPIN]).replace("\0", "9"));

        if (!minPIN_check.isEmpty()) minPIN = Integer.parseInt(minPIN_check);
        if (!maxPIN_check.isEmpty()) maxPIN = Integer.parseInt(maxPIN_check);

        if(minPIN_check.length() > lenPIN || maxPIN_check.length() > lenPIN)
            throw new InputMismatchException("The length of the PIN code cannot be less than the length of the maximum or minimum number");
        if (minPIN < 0 || maxPIN < 0)
            throw new InputMismatchException("Numbers cannot be less than 0");

        String result = alignmentStr(randomFunc(minPIN, maxPIN), lenPIN);

        String inputVar = ((CompoundVariable) values[3]).execute().trim();
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