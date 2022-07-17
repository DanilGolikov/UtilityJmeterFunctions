package com.xmeter.functions;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;


public class generatePHONE_NUMBER extends AbstractFunction{
    private static final List<String> desc = new LinkedList<>();
    private Object[] values; // The value of the passed parameter
    private static final String MyFunctionName = "__generatePHONE_NUMBER"; //function name

    public int randomFunc(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max+1);
    }
    public long randomFunc(long min, long max)
    {
        return ThreadLocalRandom.current().nextLong(min, max+1);
    }
    public static String alignmentStr(String str, int len) {
        String result = str;
        while (result.length() != len) result = "0" + result;
        return result;
    }

    static {
        desc.add("Country codes (use | as separator)");
        desc.add("Format string for PHONE (use 'x' for numbers) (optional)");
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }



    @Override
    public String execute(SampleResult arg0, Sampler arg1) {

        String[] countryCode = ((CompoundVariable) values[0]).execute().trim().split("\\|");
        for (String var : countryCode) Integer.parseInt(var);
        if (Objects.equals(countryCode[0], ""))
            throw new InputMismatchException("Impossible to generate phone number without a country code");

        String resultFormatString = ((CompoundVariable) values[1]).execute();
        if (!resultFormatString.equals("")) {
            resultFormatString = resultFormatString.replace("%", "%%");
            for (int i = 0; i < 10; i++)
                resultFormatString = resultFormatString.replaceFirst("(?<!\\\\)x", "%s");
            resultFormatString = resultFormatString.replace("\\x", "x");
        }

        String countryCodeStr = countryCode[randomFunc(0, countryCode.length-1)];

        String otherNumbers = alignmentStr(Long.toString(randomFunc(0, Long.parseLong("9999999999")+1)), 10);

        String result = countryCodeStr + otherNumbers;

        if (!resultFormatString.equals(""))
            result = countryCodeStr +
                    String.format(resultFormatString, otherNumbers.charAt(0),
                                                        otherNumbers.charAt(1),
                                                        otherNumbers.charAt(2),
                                                        otherNumbers.charAt(3),
                                                        otherNumbers.charAt(4),
                                                        otherNumbers.charAt(5),
                                                        otherNumbers.charAt(6),
                                                        otherNumbers.charAt(7),
                                                        otherNumbers.charAt(8),
                                                        otherNumbers.charAt(9));



        String inputVar = ((CompoundVariable) values[2]).execute().trim();
        if (!inputVar.equals(""))
        {
            JMeterVariables vars = getVariables();
            vars.put(inputVar, result);
        }

        return  result;
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
