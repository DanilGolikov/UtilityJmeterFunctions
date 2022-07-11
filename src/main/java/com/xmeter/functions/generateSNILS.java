package com.xmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;



public class generateSNILS extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__generateSNILS"; //function name

    Random rd = new Random();
    public int randomFunc(int min, int max)
    {
        return rd.nextInt((max-min) + 1) + min;
    }


    static {
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    public static String alignmentStr(String str, int len) {
        String result = str;
        while (result.length() != len) result = "0" + result;
        return result;
    }


    @Override
    public String execute(SampleResult arg0, Sampler arg1) {

        int rnd = randomFunc(0, 999999999);
        String number = alignmentStr(""+rnd, 9);
        String[] numMas = number.split("");

        List<Integer> sumMas = new LinkedList<>();
        for (int i = 0; i < numMas.length; i++) sumMas.add(Integer.parseInt(numMas[i]) * (9 - i));

        int sum = 0;
        for (int a: sumMas) sum += a;

        if (sum > 101) sum %= 101;

        String checkSum = sum == 100 || sum == 101 ? "00" : alignmentStr(""+sum, 2);

        String result = number+checkSum;

        if (values.length > 0)
        {
            JMeterVariables vars = getVariables();
            vars.put(((CompoundVariable) values[0]).execute().trim(), result);
        }
        return result;
    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        values = parameters.toArray();
    }
}