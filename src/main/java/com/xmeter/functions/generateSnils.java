package com.xmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import static com.xmeter.utils.customFunctionUtils.alignmentStr;
import static com.xmeter.utils.customFunctionUtils.randomFunc;


public class generateSnils extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter

    private static final String MyFunctionName = "__c_generateSnils"; //function name
    static {
        desc.add("Format string for SNILS (use 'x' for numbers) (optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }

    @Override
    public String execute(SampleResult arg0, Sampler arg1) {
        String resultFormatString = ((CompoundVariable) values[0]).execute().trim();
        resultFormatString = resultFormatString.replace("%", "%%");
        for (int i = 0; i < 11; i++)
            resultFormatString = resultFormatString.replaceFirst("(?<!\\\\)x", "%s");
        resultFormatString = resultFormatString.replace("\\x", "x");

        String number = alignmentStr(randomFunc(0, 999999999), 9);
        String[] numMas = number.split("");

        List<Integer> sumMas = new LinkedList<>();
        for (int i = 0; i < numMas.length; i++)
            sumMas.add(Integer.parseInt(numMas[i]) * (9 - i));

        int sum = 0;
        for (int a: sumMas) sum += a;
        if (sum > 101) sum %= 101;
        String checkSum = sum == 100 || sum == 101 ? "00" : alignmentStr(sum, 2);
        String result = number+checkSum;
        if (!resultFormatString.isEmpty())
            result = String.format(resultFormatString, result.charAt(0),
                                                        result.charAt(1),
                                                        result.charAt(2),
                                                        result.charAt(3),
                                                        result.charAt(4),
                                                        result.charAt(5),
                                                        result.charAt(6),
                                                        result.charAt(7),
                                                        result.charAt(8),
                                                        result.charAt(9),
                                                        result.charAt(10));

        String inputVar = ((CompoundVariable) values[1]).execute().trim();
        if (!inputVar.isEmpty())
            getVariables().put(inputVar, result);
        return  result;

    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) {
        values = parameters.toArray();
    }
}